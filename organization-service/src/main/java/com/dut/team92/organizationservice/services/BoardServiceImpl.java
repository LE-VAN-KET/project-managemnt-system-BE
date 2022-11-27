package com.dut.team92.organizationservice.services;

import com.dut.team92.organizationservice.domain.entity.Board;
import com.dut.team92.organizationservice.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardServiceImpl implements BoardService{
    private final BoardRepository boardRepository;

    @Override
    public void createDefaultBoard(UUID sprintId) {
        List<Board> boards = new ArrayList<Board>();
        boards.add(initializationBoard("TO DO", 1, sprintId));
        boards.add(initializationBoard("IN PROGRESS", 2, sprintId));
        boards.add(initializationBoard("DONE", 3, sprintId));

        boardRepository.saveAll(boards);
    }

    @Override
    public boolean existBoardByBoardId(UUID boardId) {
        return boardRepository.existsById(boardId);
    }

    private Board initializationBoard(String name, int position, UUID sprintId) {
        Board board = new Board();
        board.setName(name);
        board.setPosition(position);
        board.setSprintId(sprintId);
        return board;
    }
}
