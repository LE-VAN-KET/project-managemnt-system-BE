package com.dut.team92.userservice.util;

import com.dut.team92.common.constant.CommonConstant;
import com.dut.team92.userservice.exception.InvalidAccessTokenException;
import org.apache.commons.lang3.StringUtils;

public class TokenUtil {

    public static String extractToken(String authorizationHeader) {
        validateAuthorizationHeader(authorizationHeader);
        return authorizationHeader.replace(CommonConstant.HttpAttribute.BEARER, "").trim();
    }

    public static void validateAuthorizationHeader(String authorizationHeader) {
        if (!authorizationHeader.startsWith(CommonConstant.HttpAttribute.BEARER) ||
                StringUtils.isBlank(authorizationHeader.replace(CommonConstant.HttpAttribute.BEARER, "")))
            throw new InvalidAccessTokenException();
    }
}
