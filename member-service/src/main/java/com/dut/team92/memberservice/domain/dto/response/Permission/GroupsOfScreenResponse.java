package com.dut.team92.memberservice.domain.dto.response.Permission;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class GroupsOfScreenResponse {
    private int id;

    private String groupName;

    /// list of screen exist on this groups
    private List<ScreenResponse> screens;

    public GroupsOfScreenResponse(int id, String groupName){
        this.id = id;
        this.groupName = groupName;
    }
}
