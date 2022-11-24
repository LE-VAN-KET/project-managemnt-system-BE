package com.dut.team92.userservice.repository;

import com.dut.team92.userservice.domain.entity.UserInformation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, UUID> {
}
