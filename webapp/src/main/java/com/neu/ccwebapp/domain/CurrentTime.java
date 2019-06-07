package com.neu.ccwebapp.domain;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CurrentTime
{
    private String currentTime;

    public CurrentTime()
    {
        LocalDateTime localDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        this.currentTime = "The current time is : "+localDateTime.format(formatter);
    }

    public String getCurrentTime() {
        return currentTime;
    }

    public void setCurrentTime(String currentTime) {
        this.currentTime = currentTime;
    }
}
