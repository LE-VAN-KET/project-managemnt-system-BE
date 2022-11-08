package com.dut.team92.organizationservice.exception.handler;

import com.dut.team92.common.exception.handler.CommonExceptionHandler;
import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.organizationservice.exception.OrganizationNameExisted;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException extends CommonExceptionHandler {

    @ExceptionHandler(OrganizationNameExisted.class)
    public CommonErrorResponse handleOrganizationNameExist(OrganizationNameExisted ex) {
        return handleBadRequestException(ex, "OrganizationNameExisted");
    }
}
