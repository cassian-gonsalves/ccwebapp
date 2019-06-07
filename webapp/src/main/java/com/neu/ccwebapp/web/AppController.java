package com.neu.ccwebapp.web;

import com.neu.ccwebapp.domain.CurrentTime;
import com.neu.ccwebapp.domain.User;
import com.neu.ccwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AppController
{
    @Autowired
    private UserService userService;

    @PostMapping("/user/register")
    public void registerUser(@Valid @RequestBody User user)
    {
        userService.registerUser(user);
    }

    @GetMapping("/")
    public CurrentTime getCurrentTime()
    {
        return new CurrentTime();
    }
}
