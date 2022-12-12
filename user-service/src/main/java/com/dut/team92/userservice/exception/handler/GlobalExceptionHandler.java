package com.dut.team92.userservice.exception.handler;

import com.dut.team92.common.exception.handler.CommonExceptionHandler;
import com.dut.team92.common.exception.model.CommonErrorResponse;
import com.dut.team92.common.util.WebUtil;
import com.dut.team92.userservice.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler extends CommonExceptionHandler {
    @Autowired
    private WebUtil webUtil;

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UsernameAlreadyExistsException.class)
    public CommonErrorResponse handleUsernameAlreadyExistException(UsernameAlreadyExistsException exception) {
        return handleBadRequestException(exception, "UsernameAlreadyExistsException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public CommonErrorResponse handleEmailAlreadyExistException(EmailAlreadyExistsException exception) {
        return handleBadRequestException(exception, "EmailAlreadyExistsException");
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidAccessTokenException.class)
    public CommonErrorResponse handleInvalidAccessTokenException(InvalidAccessTokenException exception) {
        addErrorLog(HttpStatus.UNAUTHORIZED, exception.getMessage(), "InvalidAccessTokenException");
        return new CommonErrorResponse(
                webUtil.getRequestId(),
                HttpStatus.UNAUTHORIZED,
                exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(InvalidRefreshTokenException.class)
    public CommonErrorResponse handleInvalidRefreshTokenException(InvalidRefreshTokenException exception) {
        addErrorLog(HttpStatus.UNAUTHORIZED, exception.getMessage(), "handleInvalidAccessTokenException");
        return new CommonErrorResponse(
                webUtil.getRequestId(),
                HttpStatus.UNAUTHORIZED,
                exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(SaveUserFailedException.class)
    public CommonErrorResponse handleSaveUserFailedException(SaveUserFailedException exception) {
        addErrorLog(HttpStatus.BAD_REQUEST, exception.getMessage(), "SaveUserFailedException");
        return new CommonErrorResponse(
                webUtil.getRequestId(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotFoundException.class)
    public CommonErrorResponse handleUserNotFoundException(UserNotFoundException exception) {
        addErrorLog(HttpStatus.BAD_REQUEST, exception.getMessage(), "UserNotFoundException");
        return new CommonErrorResponse(
                webUtil.getRequestId(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(UserNotEnabledException.class)
    public CommonErrorResponse handleUserNotFoundException(UserNotEnabledException exception) {
        addErrorLog(HttpStatus.BAD_REQUEST, exception.getMessage(), "UserNotEnabledException");
        return new CommonErrorResponse(
                webUtil.getRequestId(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(MissingRequestHeaderException.class)
    public CommonErrorResponse handleUserNotFoundException(MissingRequestHeaderException exception) {
        addErrorLog(HttpStatus.BAD_REQUEST, exception.getMessage(), "MissingRequestHeaderException");
        return new CommonErrorResponse(
                webUtil.getRequestId(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestExceptionOrganization.class)
    public CommonErrorResponse handleBadRequestOrganizationServiceException(BadRequestExceptionOrganization exception) {
        addErrorLog(HttpStatus.BAD_REQUEST, exception.getMessage(), "BadRequestExceptionOrganization");
        return new CommonErrorResponse(
                webUtil.getRequestId(),
                HttpStatus.BAD_REQUEST,
                exception.getMessage());
    }
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FailedReadDataFileCSV.class)
    public CommonErrorResponse handleUsernameAlreadyExistException(FailedReadDataFileCSV exception) {
        return handleServerErrorException(exception, "FailedReadDataFileCSV");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(OrganizationNotFoundException.class)
    public CommonErrorResponse handleOrganizationNotFoundException(OrganizationNotFoundException exception) {
        return handleBadRequestException(exception, "OrganizationNotFoundException");
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(RoleNotFoundException.class)
    public CommonErrorResponse handleRoleNotFoundException(RoleNotFoundException exception) {
        return handleBadRequestException(exception, "RoleNotFoundException");
    }

}
