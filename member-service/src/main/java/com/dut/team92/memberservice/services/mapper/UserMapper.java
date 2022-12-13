package com.dut.team92.memberservice.services.mapper;

import com.dut.team92.common.domain.mapper.BaseMapper;
import com.dut.team92.memberservice.domain.dto.UserDto;
import com.dut.team92.memberservice.domain.entity.User;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

@Component
public class UserMapper extends BaseMapper<User, UserDto> {
    @Override
    public User convertToEntity(UserDto dto, Object... args) {
        return null;
    }

    @Override
    public UserDto convertToDto(User entity, Object... args) {
        UserDto userDto = new UserDto();
        if (entity != null) {
            BeanUtils.copyProperties(entity, userDto);
        }
        return userDto;
    }
}
