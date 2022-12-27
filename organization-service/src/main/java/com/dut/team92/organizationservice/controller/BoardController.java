package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.domain.dto.response.CheckBoardExistResponse;
import com.dut.team92.organizationservice.domain.dto.response.Response;
import com.dut.team92.organizationservice.proxy.MemberServiceProxy;
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

    @Autowired
    private MemberServiceProxy memberServiceProxy;

    @GetMapping("check/{board_id}")
    @ResponseStatus(HttpStatus.OK)
    public Object existBoardByBoardId(@PathVariable("board_id") @NotNull String boardId,
                                      @RequestHeader(value="PROJECT-ID", required = false) String projectId,
                                      @RequestHeader(value = "Authorization", required = true) String authorizationHeader) {
        Response res = memberServiceProxy.checkPermission("CHECK_EXIST_BOARD" , authorizationHeader , projectId);
        if(!res.getRspCode().equals("200"))
        {
            return res;
        }
        boolean existBoard =  boardService.existBoardByBoardId(UUID.fromString(boardId));
        return CheckBoardExistResponse.builder().isExistBoard(existBoard).build();
    }

}
