package com.dut.team92.issuesservice.services;

import com.dut.team92.issuesservice.domain.entity.IssuesAssign;
import com.dut.team92.issuesservice.repository.IssuesAssignRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class IssuesAssignServiceImpl implements IssuesAssignService {
    private final IssuesAssignRepository issuesAssignRepository;

    @Override
    public List<IssuesAssign> getAll() {
        return null;
    }

    @Override
    public IssuesAssign get(Long id) {
        return null;
    }

    @Override
    public IssuesAssign create(IssuesAssign entity) {
        return null;
    }

    @Override
    public IssuesAssign update(IssuesAssign entity) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
