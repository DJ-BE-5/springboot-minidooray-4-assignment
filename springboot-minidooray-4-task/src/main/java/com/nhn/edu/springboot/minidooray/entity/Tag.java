package com.nhn.edu.springboot.minidooray.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@EqualsAndHashCode
@Entity
public class Tag {
    @Id
    private Long id;
    private String title;

    public Tag(){
    }
    public Tag(Long id, String title){
        this.id = id;
        this.title = title;
    }
}
