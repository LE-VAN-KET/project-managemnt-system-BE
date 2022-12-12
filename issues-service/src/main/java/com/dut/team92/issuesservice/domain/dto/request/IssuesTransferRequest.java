package com.dut.team92.issuesservice.domain.dto.request;

import com.dut.team92.issuesservice.domain.dto.BoardDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class IssuesTransferRequest {
    List<BoardDto> oldBoardList;
    List<BoardDto> newBoardList;
}
