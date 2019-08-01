package com.neu.ccwebapp.domain;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class ResetPassword
{
    @NotNull(message = "Username not provided")
    @Pattern(regexp = "^[_A-Za-z0-9-+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$",message="Please provide a valid email address")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
