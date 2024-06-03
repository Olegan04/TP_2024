package com.example.pushuptrecker.dao;

import com.example.pushuptrecker.models.PushUp;
import com.example.pushuptrecker.models.User;
import org.hibernate.Session;
import org.hibernate.Transaction;

import com.example.pushuptrecker.models.UserTrainingID;
import com.example.pushuptrecker.utils.HibernateSessionFactoryUtil;
import java.util.List;
import java.util.List;
import javax.naming.Referenceable;
public class UserDAOImpl implements UserDAO{
    @Override
    public User findById(int id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
    }

    @Override
    public void save(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }
    @Override
    public void update(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    @Override
    public void delete(User user) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    @Override
    public PushUp findPushUpById(UserTrainingID id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(PushUp.class, id);
    }
}
