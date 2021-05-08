package com.company.enroller.persistence;

import java.util.Collection;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.springframework.stereotype.Component;

import com.company.enroller.model.Meeting;
import com.company.enroller.model.Participant;

@Component("meetingService")
public class MeetingService {

    Session session;

    public MeetingService() {
        session = DatabaseConnector.getInstance().getSession();
    }

    public Collection<Meeting> getAll() {
        String hql = "FROM Meeting";
        Query query = session.createQuery(hql);
        return query.list();
    }

    public Meeting findById(long id) {
        return (Meeting) session.get(Meeting.class, id);
    }

    public void add(Meeting meeting) {
        Transaction transaction = this.session.beginTransaction();
        session.save(meeting);
        transaction.commit();
    }

    public void delete(Meeting meeting) {
        Transaction transaction = this.session.beginTransaction();
        session.delete(meeting);
        transaction.commit();
    }

    public void update(Meeting foundMeeting) {
        Transaction transaction = this.session.beginTransaction();
        session.merge(foundMeeting);
        transaction.commit();

    }

    public Collection<Meeting> sortMeetingsByTitle() {
        String hql = "FROM Meeting m ORDER BY m.title";
        Query query = session.createQuery(hql);
        return query.list();
    }

    public Meeting findByTitleDescription(String title, String description) {
        String hql = "FROM Meeting m WHERE title = :title AND description = :description";
        Query query = session.createQuery(hql);
        query.setString("title", title);
        query.setString("description", description);
        return (Meeting) query.uniqueResult();
    }

    public Collection<Meeting> findByParticipant(String participantLogin) {
        String hql = "FROM Meeting m LEFT JOIN m.participants p WHERE p.login = :participantLogin";
        Query query = session.createQuery(hql);
        query.setString("participantLogin", participantLogin);
        return query.list();
    }

}
