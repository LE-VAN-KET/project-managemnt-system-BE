package com.dut.team92.organizationservice.services.mapper;

import com.dut.team92.common.domain.mapper.BaseMapper;
import com.dut.team92.organizationservice.domain.dto.BoardDto;
import com.dut.team92.organizationservice.domain.entity.Board;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class BoardDataMapper extends BaseMapper<Board, BoardDto> {

    public BoardDataMapper() {
    }

    @Override
    public Board convertToEntity(BoardDto dto, Object... args) {
        Board board = new Board();
        if (dto != null) {
            BeanUtils.copyProperties(dto, board);
        }
        return board;
    }

    @Override
    public BoardDto convertToDto(Board entity, Object... args) {
        BoardDto boardDto = new BoardDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, boardDto);
        }
        return boardDto;
    }
}
