package com.recording.core.model;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "settings")

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Settings implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "default_value")
    private String defaultValue;

    @Column(name = "value")
    private String value;

}
