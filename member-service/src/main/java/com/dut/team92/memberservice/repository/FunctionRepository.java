package com.dut.team92.memberservice.repository;

import com.dut.team92.common.repository.IJpaRepository;
import com.dut.team92.memberservice.domain.dto.response.Permission.FunctionModel;
import com.dut.team92.memberservice.domain.entity.Function;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FunctionRepository extends IJpaRepository<Function, UUID> {
    @Query("select new com.dut.team92.memberservice.domain.dto.response.Permission.FunctionModel(fs.functionId, f.isRequired, fs.screenId) FROM Function AS f INNER JOIN FunctionOfScreen AS fs ON f.id = fs.functionId")
    List<FunctionModel> getFunctionOfScreen();
}
