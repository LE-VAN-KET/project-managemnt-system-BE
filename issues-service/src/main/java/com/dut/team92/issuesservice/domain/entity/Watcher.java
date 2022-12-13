package com.dut.team92.issuesservice.domain.entity;

import com.dut.team92.common.enums.WatcherType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "watcher")
@Getter
@Setter
@NoArgsConstructor
public class Watcher {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "watcher_id", unique = true, nullable = false, columnDefinition = "BINARY(16)")
    private UUID id;

    @Enumerated(EnumType.ORDINAL)
    private WatcherType watcherType;

    @Column(columnDefinition = "BINARY(16)")
    private UUID userId;
    @Column(columnDefinition = "BINARY(16)")
    private UUID issuesId;
}
