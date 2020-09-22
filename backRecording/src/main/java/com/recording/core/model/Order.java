package com.recording.core.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.recording.core.serializers.DateToTimeStringSerializer;
import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "orders")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Order implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "slot_id")
    private long slotId;

    @Column(name = "day")
    @Temporal(TemporalType.DATE)
    private Date day;

    @Column(name = "start_period")
    @Temporal(TemporalType.TIME)
    @JsonSerialize(using = DateToTimeStringSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT+3")
    private Date startPeriod;

    @Column(name = "end_period")
    @Temporal(TemporalType.TIME)
    @JsonSerialize(using = DateToTimeStringSerializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "GMT+3")
    private Date endPeriod;

    @Column(name = "status")
    private String status;

    public Order(long slotId, Date day, Date startPeriod, Date endPeriod, String status) {
        this.slotId = slotId;
        this.day = day;
        this.startPeriod = startPeriod;
        this.endPeriod = endPeriod;
        this.status = status;
    }
}
