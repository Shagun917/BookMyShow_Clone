package com.bookmyshow.services;

import com.bookmyshow.models.User.User;
import com.bookmyshow.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void saveUser(User user) { userRepository.save(user); }

    public List<User> getAll() { return userRepository.findAll(); }

    public Optional<User> findById(Long id) { return userRepository.findById(id); }

    public void deleteById(Long id) { userRepository.deleteById(id); }

    public User findByUserName(String username) { return userRepository.findByUsername(username); }


}
