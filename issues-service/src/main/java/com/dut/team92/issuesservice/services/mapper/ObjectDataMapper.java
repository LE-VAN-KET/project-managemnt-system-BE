package com.dut.team92.issuesservice.services.mapper;

import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.issuesservice.domain.dto.SprintDto;
import com.dut.team92.issuesservice.exception.ProjectNotFoundException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ObjectDataMapper {

    public List<SprintDto> convertObjectToSprintDto(Object o) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (o instanceof List) {
                return mapper.readValue(mapper.writeValueAsString(o),
                        new TypeReference<List<SprintDto>>(){});
            } else {
                CommonErrorResponse error = mapper.readValue(mapper.writeValueAsString(o),
                        CommonErrorResponse.class);
                throw new ProjectNotFoundException(error.getMessage());
            }
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
