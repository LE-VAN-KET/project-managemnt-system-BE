package com.dut.team92.issuesservice.exception.handler;

import com.dut.team92.common.exception.handler.CommonExceptionHandler;
import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.issuesservice.exception.*;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends CommonExceptionHandler {
    @ExceptionHandler(IssuesTypeNotFoundException.class)
    public CommonErrorResponse handleIssuesTypeNotFoundException(IssuesTypeNotFoundException ex) {
        return handleBadRequestException(ex, "IssuesTypeNotFoundException");
    }

    @ExceptionHandler(IssuesStatusNotFoundException.class)
    public CommonErrorResponse handleIssuesStatusNotFoundException(IssuesStatusNotFoundException ex) {
        return handleBadRequestException(ex, "IssuesStatusNotFoundException");
    }
    @ExceptionHandler(IssuesNotFoundException.class)
    public CommonErrorResponse handleIssuesNotFoundException(IssuesNotFoundException ex) {
        return handleBadRequestException(ex, "IssuesNotFoundException");
    }
    @ExceptionHandler(MemberNotFoundException.class)
    public CommonErrorResponse handleMemberNotFoundException(MemberNotFoundException ex) {
        return handleBadRequestException(ex, "MemberNotFoundException");
    }
    @ExceptionHandler(ProjectNotFoundException.class)
    public CommonErrorResponse handleProjectNotFoundException(ProjectNotFoundException ex) {
        return handleBadRequestException(ex, "ProjectNotFoundException");
    }
    @ExceptionHandler(SaveIssuesFailedException.class)
    public CommonErrorResponse handleSaveIssuesFailedException(SaveIssuesFailedException ex) {
        return handleServerErrorException(ex, "SaveIssuesFailedException");
    }
    @ExceptionHandler(BoardNotFoundException.class)
    public CommonErrorResponse handleBoardNotFoundException(BoardNotFoundException ex) {
        return handleServerErrorException(ex, "BoardNotFoundException");
    }
}
