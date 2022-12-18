package com.dut.team92.issuesservice.repository;

import com.dut.team92.issuesservice.domain.entity.Issues;

import java.util.List;

public interface IssuesHibernate {
    void updateAttributeIssues(List<Issues> issuesList);
}
