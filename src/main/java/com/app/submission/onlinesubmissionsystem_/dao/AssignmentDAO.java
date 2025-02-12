package com.app.submission.onlinesubmissionsystem_.dao;

import com.app.submission.onlinesubmissionsystem_.model.Assignment;
import com.app.submission.onlinesubmissionsystem_.model.User;
import org.hibernate.Session;
import org.hibernate.Transaction;
import com.app.submission.onlinesubmissionsystem_.util.HibernateUtil;

import java.util.List;

public class AssignmentDAO {

    // Save a new assignment to the database
    public void saveAssignment(Assignment assignment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.persist(assignment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Retrieve an assignment by ID
    public Assignment getAssignmentById(Long id) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.get(Assignment.class, id);
        }
    }

    // Get all assignments created by a specific instructor
    public List<Assignment> getAssignmentsByInstructor(User instructor) {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Assignment WHERE createdBy = :instructor", Assignment.class)
                    .setParameter("instructor", instructor)
                    .list();
        }
    }

    // Get all assignments available to students
    public List<Assignment> getAllAssignments() {
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            return session.createQuery("FROM Assignment", Assignment.class).list();
        }
    }

    // Update an existing assignment
    public void updateAssignment(Assignment assignment) {
        Transaction transaction = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            transaction = session.beginTransaction();
            session.merge(assignment);
            transaction.commit();
        } catch (Exception e) {
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
        }
    }

    // Delete an assignment
    public void deleteAssignment(Assignment assignment) {
        Transaction tx = null;
        try (Session session = HibernateUtil.getSessionFactory().openSession()) {
            tx = session.beginTransaction();
            session.remove(assignment);
            tx.commit();
        } catch (Exception e) {
            if (tx != null) tx.rollback();
            e.printStackTrace();
        }
    }

}
