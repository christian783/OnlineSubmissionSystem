package com.app.submission.onlinesubmissionsystem_.test;
import org.hibernate.Session;
import com.app.submission.onlinesubmissionsystem_.util.HibernateUtil;

public class HibernateTest {
    public static void main(String[] args) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        System.out.println("Hibernate connection successful!");
        session.close();
    }
}
