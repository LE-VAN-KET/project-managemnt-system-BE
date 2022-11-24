package com.dut.team92.organizationservice.controller;

import com.dut.team92.organizationservice.services.SprintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/sprints")
public class SprintController {
    @Autowired
    private SprintService sprintService;

}
