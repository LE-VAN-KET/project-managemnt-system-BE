package com.dut.team92.organizationservice.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api/organizations")
@Slf4j
public class TestController {

    @GetMapping
    public ResponseEntity<?> get(HttpServletRequest request) {
        log.info(request.getHeader("X-Username"));
        log.info(request.getHeader("Userinfo"));
        return ResponseEntity.ok("oke nha");
    }
}
