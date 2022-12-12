package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.dto.BoardDto;
import com.dut.team92.organizationservice.domain.entity.Board;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface BoardService {
    List<Board> createDefaultBoard(UUID sprintId);
    boolean existBoardByBoardId(UUID boardId);
    List<BoardDto> getAllBoardBySprintId(UUID sprintId);
}
