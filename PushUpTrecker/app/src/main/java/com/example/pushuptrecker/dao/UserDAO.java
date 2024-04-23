package com.example.pushuptrecker.dao;

import com.example.pushuptrecker.models.*;
import java.util.List;

public interface UserDAO {

    public User findById(int id);

    public void save(User user);

    public void update(User user);

    public void delete(User user);

    public PushUp findPushUpById(UserTrainingID id);

    public List<User> findAll();
}