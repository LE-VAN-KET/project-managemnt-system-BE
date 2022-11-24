package com.dut.team92.organizationservice.exception.handler;

import com.dut.team92.common.exception.handler.CommonExceptionHandler;
import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.organizationservice.exception.OrganizationIdNotFound;
import com.dut.team92.organizationservice.exception.OrganizationNameExisted;
import com.dut.team92.organizationservice.exception.ProjectDomainExisted;
import com.dut.team92.organizationservice.exception.ProjectIdNotFound;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalHandlerException extends CommonExceptionHandler {

    @ExceptionHandler(OrganizationNameExisted.class)
    public CommonErrorResponse handleOrganizationNameExist(OrganizationNameExisted ex) {
        return handleBadRequestException(ex, "OrganizationNameExisted");
    }
    @ExceptionHandler(ProjectDomainExisted.class)
    public CommonErrorResponse handleOrganizationNameExist(ProjectDomainExisted ex) {
        return handleBadRequestException(ex, "ProjectDomainExisted");
    }

    @ExceptionHandler(OrganizationIdNotFound.class)
    public CommonErrorResponse handleOrganizationIdNotFoundException(OrganizationIdNotFound ex) {
        return handleBadRequestException(ex, "OrganizationIdNotFound");
    }

    @ExceptionHandler(ProjectIdNotFound.class)
    public CommonErrorResponse handleProjectIdNotFoundException(ProjectIdNotFound ex) {
        return handleBadRequestException(ex, "ProjectIdNotFound");
    }
}
