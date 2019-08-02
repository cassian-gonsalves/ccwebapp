package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.ResetPassword;
import com.neu.ccwebapp.domain.User;
import com.neu.ccwebapp.exceptions.UserExistsException;
import com.neu.ccwebapp.exceptions.UserNotFoundException;

public interface UserService
{
    void registerUser(User user) throws UserExistsException;

    void resetPassword(ResetPassword username) throws UserNotFoundException;
}
