package com.thiranya.angularspringredditclone.model;

import lombok.Data;

@Data
public class NotificationEmail {
    private String subject;
    private String recipient;
    private String body;
}