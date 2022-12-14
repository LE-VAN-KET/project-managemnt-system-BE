package com.dut.team92.organizationservice.domain.entity;

import com.dut.team92.organizationservice.domain.vo.ProjectTrackerId;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "project_tracker")
@Getter
@Setter
@NoArgsConstructor
public class ProjectTracker {
    @EmbeddedId
    private ProjectTrackerId projectTrackerId;
    private int position;
}
