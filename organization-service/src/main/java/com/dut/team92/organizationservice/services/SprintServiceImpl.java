package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.SprintDto;
import com.dut.team92.organizationservice.domain.dto.request.CreateSprintCommand;
import com.dut.team92.organizationservice.domain.entity.Sprint;
import com.dut.team92.organizationservice.exception.ProjectIdNotFound;
import com.dut.team92.organizationservice.repository.ProjectRepository;
import com.dut.team92.organizationservice.repository.SprintRepository;
import com.dut.team92.organizationservice.services.mapper.SprintDataMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    void validateCreateSprint(CreateSprintCommand command) {
        boolean isExistProject = projectRepository.existsById(command.getProjectId());
        if (!isExistProject) {
            throw new ProjectIdNotFound("Project not found with id: " + command.getProjectId());
        }
    }
}
