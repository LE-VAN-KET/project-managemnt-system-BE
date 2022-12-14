package com.dut.team92.issuesservice.services;

import com.dut.team92.common.enums.IssuesAssignStatus;
import com.dut.team92.common.security.TokenKey;
import com.dut.team92.common.security.TokenProvider;
import com.dut.team92.issuesservice.domain.dto.BoardDto;
import com.dut.team92.issuesservice.domain.dto.IssuesDto;
import com.dut.team92.issuesservice.domain.dto.SprintDto;
import com.dut.team92.issuesservice.domain.dto.request.CreateIssuesBacklogCommand;
import com.dut.team92.issuesservice.domain.dto.request.IssuesMovedRequest;
import com.dut.team92.issuesservice.domain.dto.request.MoveIssuesCommand;
import com.dut.team92.issuesservice.domain.dto.response.*;
import com.dut.team92.issuesservice.domain.entity.Issues;
import com.dut.team92.issuesservice.domain.entity.IssuesAssign;
import com.dut.team92.issuesservice.domain.entity.IssuesStatus;
import com.dut.team92.issuesservice.domain.entity.IssuesType;
import com.dut.team92.issuesservice.exception.*;
import com.dut.team92.issuesservice.proxy.MemberServiceProxy;
import com.dut.team92.issuesservice.proxy.OrganizationServiceProxy;
import com.dut.team92.issuesservice.repository.IssuesAssignRepository;
import com.dut.team92.issuesservice.repository.IssuesRepository;
import com.dut.team92.issuesservice.repository.IssuesStatusRepository;
import com.dut.team92.issuesservice.repository.IssuesTypeRepository;
import com.dut.team92.issuesservice.services.mapper.IssuesMapper;
import com.dut.team92.issuesservice.services.mapper.ObjectDataMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@CacheConfig(cacheNames = "issuesCache")
public class IssuesServiceImpl implements IssuesService{
    private final IssuesRepository issuesRepository;
    private final IssuesTypeService issuesTypeService;
    private final OrganizationServiceProxy organizationServiceProxy;
    private final MemberServiceProxy memberServiceProxy;
    private final IssuesMapper issuesMapper;
    private final IssuesStatusRepository issuesStatusRepository;
    private final TokenProvider tokenProvider;
    private final IssuesAssignRepository issuesAssignRepository;
    private final ObjectDataMapper objectDataMapper;

    @Autowired
    private HttpServletRequest request;

    @CacheEvict(cacheNames = "issues", allEntries = true)
    @Override
    @Transactional
    public IssuesDto createIssues(CreateIssuesBacklogCommand command) {
        Issues issues = issuesMapper.createIssuesBacklogCommandToIssues(command);
        UUID issuesId = UUID.randomUUID();
        issues.setId(issuesId);
        long countIssuesOfProject = issuesRepository.maxIssuesKeyByProjectId(command.getProjectId());
        ProjectKeyResponse projectKey = organizationServiceProxy.getProjectKey(
                command.getProjectId().toString(),
                command.getOrganizationId().toString(),
                request.getHeader(HttpHeaders.AUTHORIZATION));
        if (projectKey.getKey() == null) {
            throw new ProjectKeyNotFoundException("Project key not found with project id = "
                    + command.getProjectId());
        }

        issues.setIssuesKey(StringUtils.join(projectKey.getKey(), "-", countIssuesOfProject + 1));
        return createOrUpdateIssues(issues, command);
    }

    @Cacheable(cacheNames = "issues", key = "#issuesId", unless = "#result == null")
    @Override
    @Transactional(readOnly = true)
    public IssuesDto get(UUID issuesId) {
        Issues issues = issuesRepository.findById(issuesId, IssuesAssignStatus.ACTIVE).orElseThrow(() ->
                new IssuesNotFoundException("Issues not exist with id equals " + issuesId));
        return issuesMapper.convertToDto(issues);
    }

//    @Cacheable(cacheNames = "issues_backlogs", key = "#projectId", unless = "#result.size() == 0")
    @Override
    @Transactional(readOnly = true)
    public List<IssuesDto> getAllIssuesBacklogByProjectId(UUID projectId) {
        List<Issues> issues = issuesRepository.findAllByProjectIdAndBoardIdIsNull(projectId,
                IssuesAssignStatus.ACTIVE);
        return issues.isEmpty() ? Collections.emptyList()
                :issuesMapper.convertToDtoList(issues);
    }

    @CacheEvict(cacheNames = "issues", key = "#issuesId")
    @Override
    @Transactional
    public IssuesDto updateIssues(CreateIssuesBacklogCommand command, UUID issuesId) {
        Issues issuesExist = issuesRepository.findById(issuesId).orElseThrow(() ->
                new IssuesNotFoundException("Issues not found incorrect id = " + issuesId));

        assert command != null;
        BeanUtils.copyProperties(command, issuesExist, getNullPropertyNames(command));
        return createOrUpdateIssues(issuesExist, command);
    }

    @Caching(evict = { @CacheEvict(cacheNames = "issues", key = "#issuesId"),
            @CacheEvict(cacheNames = "issues", allEntries = true) })
    @Override
    @Transactional
    public void deleteIssues(UUID issuesId) {
        issuesRepository.deleteById(issuesId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<IssuesDto> getAllIssuesByBoardIdIn(List<UUID> boardIs) {
        List<Issues> issues = issuesRepository.findAllByBoardIdIn(boardIs, IssuesAssignStatus.ACTIVE);
        return issues.isEmpty() ? Collections.emptyList() : issuesMapper.convertToDtoList(issues);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(cacheNames = "issues_in_project", key = "#projectId")
    public List<IssuesDto> getAllIssuesInProject(UUID projectId) {
        List<Issues> issuesList = issuesRepository.findAllByProjectId(projectId, IssuesAssignStatus.ACTIVE);
        return issuesList.isEmpty() ? Collections.emptyList() : issuesMapper.convertToDtoList(issuesList);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public MoveIssuesResponse moveIssues(MoveIssuesCommand command) {
        List<UUID> issuesIds = new ArrayList<>();
        Map<UUID, IssuesMovedRequest> issuesMovedRequestMap = new HashMap<>();
        issuesIds.addAll(getListIssuesIdBacklogChange(command, issuesMovedRequestMap));
        issuesIds.addAll(getListIssuesIdSprintChange(command, issuesMovedRequestMap));
//        List<Issues> issuesList = issuesRepository.findAllById(issuesIds);
        if (!issuesIds.isEmpty()) {

            List<Issues> issuesListUpdate = issuesMovedRequestMap.values().stream().map(iss -> {
//                IssuesMovedRequest issuesMove = issuesMovedRequestMap.get(iss.getId());
                Issues entity = new Issues();
                entity.setId(iss.getId());
                entity.setPosition(iss.getPosition());
                entity.setBoardId(iss.getBoardId());
                return entity;
            }).collect(Collectors.toList());

//            issuesRepository.saveAllAndFlush(issuesListUpdate);
            issuesRepository.updateAttributeIssues(issuesListUpdate);
        }
        return MoveIssuesResponse.builder().code(200).message("You are moved issues successfully!").build();
    }

    @Override
    @Transactional(readOnly = true)
//    @Cacheable(cacheNames = "issues_boards", key = "#projectId", unless = "#result.size() == 0")
    public List<SprintDto> getAllIssuesInBoardOfSprintStatusRunningByProjectId(UUID projectId) {
        Object response = organizationServiceProxy.getAllSprintRunning(projectId.toString(),
                request.getHeader(HttpHeaders.AUTHORIZATION));
        List<SprintDto> sprintDtos = objectDataMapper.convertObjectToSprintDto(response);
        Map<UUID, BoardDto> boardDtoMap = new HashMap<>();
        Map<UUID, SprintDto> sprintDtoMap = new HashMap<>();
        List<UUID> boardIds = new ArrayList<>();
        sprintDtos.forEach(sprint -> {
            sprintDtoMap.put(sprint.getId(), sprint);
            List<UUID> boardIdListInSprint = sprint.getBoardDtoList().stream().map(BoardDto::getId)
                    .collect(Collectors.toList());
            boardIds.addAll(boardIdListInSprint);
            sprint.getBoardDtoList().forEach(board -> boardDtoMap.put(board.getId(), board));
        });
        // get all issues in list boardId
        // after that, add issues to board dto
        getAllIssuesByBoardIdIn(boardIds).forEach(issuesDto -> {
            List<IssuesDto> issuesDtoList = boardDtoMap.get(issuesDto.getBoardId()).getIssuesDtoList();
            if (Objects.isNull(issuesDtoList)) {
                boardDtoMap.get(issuesDto.getBoardId()).setIssuesDtoList(new ArrayList<>());
            }
            boardDtoMap.get(issuesDto.getBoardId()).getIssuesDtoList().add(issuesDto);
        });

        boardDtoMap.values().forEach(board -> {
            sprintDtoMap.get(board.getSprintId()).getBoardDtoList().stream()
                    .filter(boardDto -> boardDto.getId().equals(board.getId()))
                    .findFirst().ifPresent(b -> b.setIssuesDtoList(board.getIssuesDtoList()));
        });
        return new ArrayList<>(sprintDtoMap.values());
    }
    @Override
    @Transactional
    public void updateIssuesToSPrint(List<BoardDto> oldBoardList, List<BoardDto> newBoardList) {
        IssuesStatus issuesStatus = issuesStatusRepository.findByName("DONE")
                .orElseThrow(() -> new IssuesStatusNotFoundException("Issues status not found by name DONE"));
        Map<String, BoardDto> boardDtoMap = new HashMap<>();
        oldBoardList.forEach(board -> boardDtoMap.put(board.getName(), board));
        newBoardList.forEach(board -> {
            if (boardDtoMap.containsKey(board.getName())) {
                issuesRepository.updateIssuesToOtherSprint(board.getId(), boardDtoMap.get(board.getName()).getId(),
                        issuesStatus.getId());
            }
        });
    }

    @Override
    @Transactional
    public void updateIssuesToBacklog(List<UUID> boardIdList) {
        IssuesStatus issuesStatus = issuesStatusRepository.findByName("DONE")
                .orElseThrow(() -> new IssuesStatusNotFoundException("Issues status not found by name DONE"));
        issuesRepository.updateIssuesToBacklog(boardIdList, issuesStatus.getId());
    }

    private List<UUID> getListIssuesIdBacklogChange(MoveIssuesCommand command,
                                                    Map<UUID, IssuesMovedRequest> issuesMovedRequestMap) {
        if (command.getBacklogs() != null && !command.getBacklogs().isEmpty()) {
            return command.getBacklogs().stream().map(iss -> {
                issuesMovedRequestMap.put(iss.getId(), iss);
                return iss.getId();
            }).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    private List<UUID> getListIssuesIdSprintChange(MoveIssuesCommand command,
                                                   Map<UUID, IssuesMovedRequest> issuesMovedRequestMap) {
        if (command.getSprints() != null && !command.getSprints().isEmpty()) {
            List<UUID> issuesIds = new ArrayList<>();
            command.getSprints().stream().filter(sp -> !sp.getIssuesList().isEmpty()).forEach(sp -> {
                issuesIds.addAll(sp.getIssuesList().stream().map(iss -> {
                    issuesMovedRequestMap.put(iss.getId(), iss);
                    return iss.getId();
                }).collect(Collectors.toList()));
            });
            return issuesIds;
        }
        return Collections.emptyList();
    }

    @Async("threadPoolTaskExecutor2")
    public CompletableFuture<IssuesAssign> assignIssuesToMember(UUID memberId, UUID issuesId) {
        Optional<IssuesAssign> existIssuesAssign = issuesAssignRepository.findByIssuesIdAndStatus(issuesId,
                IssuesAssignStatus.ACTIVE);
        if (existIssuesAssign.isPresent()) {
            IssuesAssign oldIssuesAssign = existIssuesAssign.get();
            if (oldIssuesAssign.getMemberId().equals(memberId)) {
                return CompletableFuture.completedFuture(oldIssuesAssign);
            }
            oldIssuesAssign.setStatus(IssuesAssignStatus.BLOCKED);
            issuesAssignRepository.save(oldIssuesAssign);
        }
        IssuesAssign issuesAssign = new IssuesAssign();
        issuesAssign.setIssuesId(issuesId);
        issuesAssign.setMemberId(memberId);
        issuesAssign.setStatus(IssuesAssignStatus.ACTIVE);
        return CompletableFuture.completedFuture(issuesAssignRepository.save(issuesAssign));
    }

    @Async("threadPoolTaskExecutor1")
    public CompletableFuture<Issues> saveIssues(Issues issues) {
        return CompletableFuture.completedFuture(issuesRepository.saveAndFlush(issues));
    }

    private void validateProjectAndAssignMember(CreateIssuesBacklogCommand command) {
        CompletableFuture<Boolean> existProject = validateExistProject(command);
        CompletableFuture<Boolean> existMember = validateAssignMember(command);
        CompletableFuture<Boolean> existBoard = validateExistBoard(command);
        CompletableFuture.allOf(existProject, existMember, existBoard).join();
    }

    @Async("threadPoolTaskExecutor3")
    public CompletableFuture<Boolean> validateExistBoard(CreateIssuesBacklogCommand command) {
        if (command.getBoardId() != null) {
            CheckBoardExistResponse existBoard = organizationServiceProxy.existBoardByBoardId(
                    command.getBoardId().toString(),
                    request.getHeader(HttpHeaders.AUTHORIZATION));
            if (!existBoard.getIsExistBoard()) {
                log.warn("Call Organization Service ERROR Status: {}, Message: {}", existBoard.getCode(),
                        existBoard.getMessage());
                throw new BoardNotFoundException("Board not exist with board id equal " + command.getBoardId());
            }
        }

        return CompletableFuture.completedFuture(true);
    }

    @Async("threadPoolTaskExecutor1")
    public CompletableFuture<Boolean> validateExistProject(CreateIssuesBacklogCommand command) {
        CheckProjectExistResponse existProject = organizationServiceProxy.existProjectByIdAndOrganizationId(
                command.getOrganizationId().toString(), command.getProjectId().toString());
        if (!existProject.getIsExistProject()) {
            log.warn("Call Organization Service ERROR Status: {}, Message: {}", existProject.getCode(),
                    existProject.getMessage());
            throw new ProjectNotFoundException("Project not exist by id: " + command.getProjectId());
        }
        return CompletableFuture.completedFuture(true);
    }

    @Async("threadPoolTaskExecutor2")
    public CompletableFuture<Boolean> validateAssignMember(CreateIssuesBacklogCommand command) {
        if (command.getAssignMemberId() != null) {
            CheckExistMemberResponse existMemberInProject = memberServiceProxy.checkExistMemberInProject(
                    command.getAssignMemberId().toString(),
                    command.getProjectId().toString()
            );

            if (!existMemberInProject.getIsExistMember()) {
                log.warn("Call Member Service Error status: {}, message: {}", existMemberInProject.getCode(),
                        existMemberInProject.getMessage());
                throw new MemberNotFoundException("Assign Member not found with memberId: "
                        + command.getAssignMemberId());
            }
        }
        return CompletableFuture.completedFuture(true);
    }

    private IssuesDto createOrUpdateIssues(Issues issues, CreateIssuesBacklogCommand command) {
        try {
            if (command.getIssueTypeId() != null) {
                IssuesType existIssuesType = issuesTypeService.get(command.getIssueTypeId());
                issues.setIssuesType(existIssuesType);
            }
            Issues parent = null;
            if (command.getParentId() != null) {
                parent = issuesRepository.findById(command.getParentId()).orElseThrow(() ->
                        new IssuesTypeNotFoundException("Issues not found with id equal " + command.getParentId()));
            }

            IssuesStatus issuesStatus = issuesStatusRepository.findById(command.getIssuesStatusId())
                    .orElseThrow(() -> new IssuesStatusNotFoundException("Issues status not found with id equal "
                            + command.getIssuesStatusId()));

            validateProjectAndAssignMember(command);
            issues.setIssuesStatus(issuesStatus);
            issues.setParent(parent);
            String authorId = tokenProvider.extractClaim(tokenProvider.parseJwt(request))
                    .get(TokenKey.SUB_ID, String.class);
            issues.setAuthorId(UUID.fromString(authorId));
            issues.setPosition(calculationPositionIssues(command));

            CompletableFuture<Issues> savedIssues = saveIssues(issues);
            if (command.getAssignMemberId() != null) {
                CompletableFuture<IssuesAssign> assignedMember = assignIssuesToMember(command.getAssignMemberId(),
                        issues.getId());
                CompletableFuture.allOf(savedIssues, assignedMember).join();
            }

            return issuesMapper.convertToDto(savedIssues.get());
        } catch (ExecutionException e) {
            throw new SaveIssuesFailedException("ExecutionException: " + e.getMessage());
        } catch (InterruptedException interruptedException) {
            Thread.currentThread().interrupt();
            throw new SaveIssuesFailedException("InterruptedException: " + interruptedException.getMessage());
        }
    }

    private int calculationPositionIssues(CreateIssuesBacklogCommand command) {
        if (command.getPosition() > 0) {
            return command.getPosition();
        } else if (command.getBoardId() != null){
            int maxPositionSprint = issuesRepository.maxPositionByProjectIdAndBoardId(command.getProjectId(),
                    command.getBoardId());
            return maxPositionSprint + 1;
        } else {
            int maxPositionBacklog = issuesRepository.maxPositionByProjectIdAndBoardIdIsNull(command.getProjectId());
            return maxPositionBacklog + 1;
        }
    }

    private String[] getNullPropertyNames(Object source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<String>();
        for(java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

}
