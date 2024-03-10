package com.nhn.edu.springboot.minidooray.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Project {
    public enum Status {
        ACTIVATED, DORMANCY, TERMINATED
    };

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long projectId;
    private Status status;
    private String title;

    @OneToMany
    private List<Task> tasks;
}
