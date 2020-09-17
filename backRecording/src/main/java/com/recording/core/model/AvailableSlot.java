package com.recording.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.recording.core.serializers.DateToTimeStringSerializer;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "available_slot")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class AvailableSlot {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "user_id")
    private long userId;

    @Column(name = "day")
    @Temporal(TemporalType.DATE)
    private Date day;

    @Column(name = "start_period")
    @Temporal(TemporalType.TIME)
    @JsonSerialize(using = DateToTimeStringSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm", timezone="GMT+3")
    private Date startPeriod;

    @Column(name = "end_period")
    @Temporal(TemporalType.TIME)
    @JsonSerialize(using = DateToTimeStringSerializer.class)
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern="HH:mm", timezone="GMT+3")
    private Date endPeriod;

    @Column(name = "active")
    private boolean active;

    @Column(name = "reserved")
    private boolean reserved;
}
