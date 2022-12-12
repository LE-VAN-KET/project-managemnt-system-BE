package com.dut.team92.organizationservice.domain.dto.request;

import com.dut.team92.organizationservice.domain.dto.BoardDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class IssuesTransferRequest {
    List<BoardDto> oldBoardList;
    List<BoardDto> newBoardList;
}
