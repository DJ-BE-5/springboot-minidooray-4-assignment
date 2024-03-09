package com.nhn.edu.springboot.homework.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
    private Long id;
    private Status status;

    @OneToMany
    private List<Member> members;

    @OneToMany
    private List<Task> tasks;
}
