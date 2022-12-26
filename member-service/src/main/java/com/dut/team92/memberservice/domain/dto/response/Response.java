package com.dut.team92.memberservice.domain.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Response {
    private String RspCode;
    private String Message;
    private Object data;
}
