package com.nhn.edu.springboot.minidooray;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="server", ignoreUnknownFields = true)
public class ServerProperties {
    private Integer port;
}