package com.nhn.edu.springboot.minidooray.entity;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
    private Long projectId;
    private Status status;
/*
    @OneToMany
    private List<Member> members;
*/
    @OneToMany
    private List<Task> tasks;

    @OneToMany
    private List<Tag> tags;
}
