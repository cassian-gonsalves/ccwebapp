package com.neu.ccwebapp.service;

import com.neu.ccwebapp.domain.ResetPassword;
import com.neu.ccwebapp.domain.User;
import com.neu.ccwebapp.exceptions.UserExistsException;
import com.neu.ccwebapp.exceptions.UserNotFoundException;
import com.neu.ccwebapp.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.CreateTopicResponse;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.PublishResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService, UserDetailsService
{
    @Autowired
    UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private final SnsClient snsClient = SnsClient.create();
    private final CreateTopicResponse passwordResetTopic = snsClient.createTopic(CreateTopicRequest.builder().name("password-reset").build());

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public void registerUser(User user) throws UserExistsException {
        Optional<User> existingUser = userRepository.findById(user.getUsername());
        if(existingUser.isPresent()) {
            throw new UserExistsException("A user with username "+user.getUsername() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
    }

    @Override
    public void resetPassword(ResetPassword user) throws UserNotFoundException {
        Optional<User> existingUser = userRepository.findById(user.getUsername());
        if(existingUser.isEmpty())
        {
            throw new UserNotFoundException(user.getUsername());
        }
        PublishRequest publishRequest = PublishRequest.builder().message(user.getUsername()).topicArn(passwordResetTopic.topicArn()).build();
        PublishResponse publishResponse = snsClient.publish(publishRequest);
        logger.info("Message published to SNS : "+publishResponse.messageId());
    }

    @Override
    public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException
    {
        Optional<User> user = userRepository.findById(userName);
        if(user.isEmpty())
        {
            throw new UsernameNotFoundException("No user found with the username : "+userName);
        }
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_USER"));
        return new org.springframework.security.core.userdetails.User(user.get().getUsername(), user.get().getPassword(), authorities);
    }
}
