package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.MoveIssuesType;
import com.dut.team92.organizationservice.domain.dto.SprintDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;
import com.dut.team92.organizationservice.domain.entity.Sprint;
import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import com.dut.team92.organizationservice.exception.ProjectIdNotFound;
import com.dut.team92.organizationservice.exception.SprintNotFoundException;
import com.dut.team92.organizationservice.repository.ProjectRepository;
import com.dut.team92.organizationservice.repository.SprintRepository;
import com.dut.team92.organizationservice.services.mapper.SprintDataMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class SprintServiceImpl implements SprintService{
    private SprintRepository sprintRepository;
    private SprintDataMapper sprintDataMapper;
    private ProjectRepository projectRepository;
    private BoardService boardService;

    @Autowired
    public SprintServiceImpl(SprintRepository sprintRepository,
                             SprintDataMapper sprintDataMapper,
                             ProjectRepository projectRepository,
                             BoardService boardService) {
        this.sprintRepository = sprintRepository;
        this.sprintDataMapper = sprintDataMapper;
        this.projectRepository = projectRepository;
        this.boardService = boardService;
    }

    @Override
    public SprintDto createSprint(CreateSprintCommand command) {
        validateCreateSprint(command);
        Sprint sprint = sprintDataMapper.createSprintCommandToSprint(command);
        sprint.setIsDelete(false);
        Sprint savedSprint = sprintRepository.save(sprint);
        // create default 3 board to sprint
        boardService.createDefaultBoard(savedSprint.getId());
        return sprintDataMapper.convertToDto(savedSprint);
    }

    @Override
    public SprintDto updateSprint(SprintDto dto) {
        Sprint existSprint = getOneSprint(dto.getId());
        BeanUtils.copyProperties(dto, existSprint);
        return sprintDataMapper.convertToDto(sprintRepository.save(existSprint));
    }

    @Override
    @Transactional
    public void completedSprint(UUID sprintId, MoveIssuesType moveIssuesType) {
        // update sprint old
        Sprint existSprint = getOneSprint(sprintId);
        existSprint.setStatus(SprintStatus.COMPLETED);
        sprintRepository.save(existSprint);

        String projectKey = projectRepository.findProjectKeyByProjectId(existSprint.getProjectId())
                .orElseThrow(() -> new ProjectIdNotFound("Project not found with id = "
                        + existSprint.getProjectId()));
        long countSprintInProject = sprintRepository.countByProjectId(existSprint.getProjectId());
        // create new sprint unstart
        Sprint sprint = createSprintAuto(projectKey, countSprintInProject, existSprint.getProjectId());
        switch (moveIssuesType) {
            case NEW_SPRINT:

                // mai doing
        }
    }

    void validateCreateSprint(CreateSprintCommand command) {
        boolean isExistProject = projectRepository.existsById(command.getProjectId());
        if (!isExistProject) {
            throw new ProjectIdNotFound("Project not found with id: " + command.getProjectId());
        }
    }

    private Sprint getOneSprint(UUID sprintId) {
        return sprintRepository.findById(sprintId).orElseThrow(() ->
                new SprintNotFoundException("Sprint not found with id = " + sprintId));
    }

    private Sprint createSprintAuto(String projectKey, long countSprintInProject, UUID projectId) {
        Sprint sprint = new Sprint();
        sprint.setStatus(SprintStatus.UNSTART);
        sprint.setName(StringUtils.join(projectKey, "Sprint", countSprintInProject + 1, " "));
        sprint.setProjectId(projectId);
        sprint.setIsDelete(false);
        sprint.setPosition(sprintRepository.maxPosition() + 1);
        return sprintRepository.save(sprint); // save sprint
    }
}
