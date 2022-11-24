package com.dut.team92.organizationservice.domain.dto;

import com.dut.team92.organizationservice.domain.entity.SprintStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.OffsetDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class SprintDto {
    private UUID id;

    private UUID projectId;
    private String name;
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Calendar startDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm a z")
    private Calendar endDate;

    private SprintStatus status;
    private Boolean isDelete;
    private int position;
}
