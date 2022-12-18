package com.dut.team92.organizationservice.services.mapper;

import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.organizationservice.domain.dto.response.ProjectResponse;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
public class ObjectDataMapper {

    public List<ProjectResponse> convertObjectToProjectId(Object o) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            if (o instanceof List) {
                return mapper.readValue(mapper.writeValueAsString(o),
                        new TypeReference<List<ProjectResponse>>(){});
            } else {
                CommonErrorResponse error = mapper.readValue(mapper.writeValueAsString(o),
                        CommonErrorResponse.class);
                throw new RuntimeException(error.getMessage());
            }
        } catch (JsonProcessingException exception) {
            throw new RuntimeException(exception);
        }
    }
}
