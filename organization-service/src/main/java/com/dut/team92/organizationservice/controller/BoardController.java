package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.domain.dto.response.CheckBoardExistResponse;
import com.dut.team92.organizationservice.services.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@RestController
@RequestMapping("/api/boards")
public class BoardController {
    @Autowired
    private BoardService boardService;

    @GetMapping("check/{board_id}")
    @ResponseStatus(HttpStatus.OK)
    public CheckBoardExistResponse existBoardByBoardId(@PathVariable("board_id") @NotNull String boardId) {
        boolean existBoard =  boardService.existBoardByBoardId(UUID.fromString(boardId));
        return CheckBoardExistResponse.builder().isExistBoard(existBoard).build();
    }
}
