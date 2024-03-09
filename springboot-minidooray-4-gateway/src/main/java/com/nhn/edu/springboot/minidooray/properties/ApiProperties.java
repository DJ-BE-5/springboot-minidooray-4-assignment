package com.nhn.edu.springboot.minidooray.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "api.url")
public class ApiProperties {

    private String accountEndPoint;
    private String taskEndPoint;

    public String getAccountEndPoint() {
        return accountEndPoint;
    }

    public void setAccountEndPoint(String accountEndPoint) {
        this.accountEndPoint = accountEndPoint;
    }

    public String getTaskEndPoint() {
        return taskEndPoint;
    }

    public void setTaskEndPoint(String taskEndPoint) {
        this.taskEndPoint = taskEndPoint;
    }
}