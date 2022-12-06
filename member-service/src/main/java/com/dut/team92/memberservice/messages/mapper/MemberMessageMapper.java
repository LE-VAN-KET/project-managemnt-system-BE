package com.dut.team92.memberservice.messages.mapper;

import com.dut.team92.memberservice.domain.entity.Members;
import com.dut.team92.memberservice.domain.entity.MembersRoles;
import com.dut.team92.memberservice.domain.entity.Roles;
import com.dut.team92.memberservice.domain.entity.User;
import org.springframework.stereotype.Component;

@Component
public class MemberMessageMapper {

    public Members userToMembers(User user, Roles role) {
        Members members = new Members();
        MembersRoles membersRoles = new MembersRoles();
        membersRoles.setRole(role);
        membersRoles.setMember(members);
        members.setMembersRoles(membersRoles);
        members.setUser(user);
        members.setMailNotification(user.getMailNotification());
        return members;
    }
}
