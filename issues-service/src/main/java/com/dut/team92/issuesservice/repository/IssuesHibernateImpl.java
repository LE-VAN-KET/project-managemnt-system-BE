package com.dut.team92.issuesservice.repository;

import com.dut.team92.issuesservice.domain.entity.Issues;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class IssuesHibernateImpl extends HibernateRepositoryImpl<Issues> implements IssuesHibernate {
    @PersistenceContext
    private EntityManager entityManager;
    @Override
    public void updateAttributeIssues(List<Issues> issuesList) {
        for(Issues entity : issuesList) {
            updateAttributeIssues(entity);
        }
        entityManager.flush();
    }

    public void updateAttributeIssues(Issues issues) {
        entityManager.createQuery("update Issues iss set iss.position = :position, iss.boardId = :boardId" +
                        " where iss.id = :id").setParameter("position", issues.getPosition())
                .setParameter("boardId", issues.getBoardId())
                .setParameter("id", issues.getId()).executeUpdate();
    }
}
