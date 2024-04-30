package com.example.pushuptrecker.service;
import com.example.pushuptrecker.dao.UserDAOImpl;
import com.example.pushuptrecker.models.*;

import java.util.Date;
import java.util.List;
public class UserService {
    private UserDAOImpl usersDao = new UserDAOImpl();

    public UserService() {
    }

    public User findUser(int id) {
        return usersDao.findById(id);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public PushUp findPushUpById(UserTrainingID id)  {
        return usersDao.findPushUpById(id);
    }
    public static void main(String[] args){
        UserService userService = new UserService();
        User user = new User("er@ya.ru", "232", "sse", new Date(2004, 8, 29), "man", "R", "T");
        userService.saveUser(user);
        /*Auto ferrari = new Auto("Ferrari", "red");
        ferrari.setUser(user);
        user.addAuto(ferrari);
        Auto ford = new Auto("Ford", "black");
        ford.setUser(user);
        user.addAuto(ford);
        userService.updateUser(user);*/
    }
}
