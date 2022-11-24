package com.dut.team92.organizationservice.services;

import java.util.UUID;

public interface BoardService {
    void createDefaultBoard(UUID sprintId);
    boolean existBoardByBoardId(UUID boardId);
}
