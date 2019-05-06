package model.service;

import model.entity.Mail;
import model.entity.User;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TemporalType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by amir on 7/21/17.
 */
@Repository
@Transactional
public class MailManager {
    @PersistenceContext(type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    private static Logger logger = Logger.getLogger(MailManager.class.getName());

    public boolean addMail(Mail mail){
        try {
            User sender = new User();
            User receiver = new User();

            sender.setUsername(mail.getSender());
            receiver.setUsername(mail.getReceiver());

            sender = getUser(sender);
            receiver = getUser(receiver);

            if (sender.getMailsSent() != null && sender.getMailsSent().size() > 0){
                sender.getMailsSent().add(mail);
            }else {
                ArrayList<Mail> mails = new ArrayList<>();
                mails.add(mail);
                sender.setMailsSent(mails);
            }
            if (receiver.getMailsReceive() != null && receiver.getMailsReceive().size() > 0){
                receiver.getMailsReceive().add(mail);
            }else {
                ArrayList<Mail> mails = new ArrayList<>();
                mails.add(mail);
                receiver.setMailsReceive(mails);
            }

            entityManager.persist(sender);
            entityManager.persist(receiver);


            return true;
        }catch (Exception e){
            logger.error("Cant persist", e);
            return false;
        }
    }

    private User getUser(User user){
        try {
            return (User) entityManager.createQuery("select p from User p where p.username=:x")
                    .setParameter("x", user.getUsername())
                    .getSingleResult();
        }catch (Exception e){
            logger.error(e);
            return null;
        }
    }

    public boolean deleteMail(Mail mail){
        try{
            entityManager.createQuery("delete from Mail p where p.id=:y")
                    .setParameter("y", mail.getId()).executeUpdate();

            return true;
        }catch (Exception e){
            logger.error("cant remove", e);
            return false;
        }
    }

    public Mail getMail(Mail mail){
        try {
            return (Mail) entityManager.createQuery("select p from Mail p where p.id=:x")
                    .setParameter("x", mail.getId())
                    .getSingleResult();
        }catch (Exception e){
            return null;
        }
    }

    public List<Mail> getBySubject(Mail mail){
        try {
            if (mail.getSender() != null){
                return entityManager.createQuery("select p from Mail p where p.sender=:x and p.subject=:y")
                        .setParameter("x", mail.getSender())
                        .setParameter("y", mail.getSubject())
                        .getResultList();
            }else {
                return entityManager.createQuery("select p from Mail p where p.receiver=:x and p.subject=:y")
                        .setParameter("x", mail.getReceiver())
                        .setParameter("y", mail.getSubject())
                        .getResultList();
            }
        }catch (Exception e){
            return null;
        }
    }

    public List<Mail> getByDate(Mail mail, Date begin, Date end){
        try {
            if (mail.getSender() != null){
                return entityManager.createQuery("select p from Mail p where p.sender=:x and p.date between :s1 and :s2")
                        .setParameter("x", mail.getSender())
                        .setParameter("s1", begin, TemporalType.DATE)
                        .setParameter("s2", end, TemporalType.DATE)
                        .getResultList();
            }else {
                return entityManager.createQuery("select p from Mail p where p.receiver=:x and p.date between :s1 and :s2")
                        .setParameter("x", mail.getReceiver())
                        .setParameter("s1", begin, TemporalType.DATE)
                        .setParameter("s2", end, TemporalType.DATE)
                        .getResultList();
            }
        }catch (Exception e){
            return null;
        }
    }

    public List<Mail> getByDateSubject(Mail mail, Date begin, Date end){
        try {
            if (mail.getSender() != null){
                return entityManager.createQuery("select p from Mail p where p.sender=:x and p.subject=:y and p.date between :s1 and :s2")
                        .setParameter("x", mail.getSender())
                        .setParameter("y", mail.getSubject())
                        .setParameter("s1", begin, TemporalType.DATE)
                        .setParameter("s2", end, TemporalType.DATE)
                        .getResultList();
            }else {
                return entityManager.createQuery("select p from Mail p where p.receiver=:x and p.subject=:y and p.date between :s1 and :s2")
                        .setParameter("x", mail.getReceiver())
                        .setParameter("y", mail.getSubject())
                        .setParameter("s1", begin, TemporalType.DATE)
                        .setParameter("s2", end, TemporalType.DATE)
                        .getResultList();
            }
        }catch (Exception e){
            return null;
        }
    }

    public List<Mail> getUnseened(Mail mail){
        try{
            return entityManager.createQuery("select p from Mail p where p.receiver=:x and p.seen=false ")
                    .setParameter("x", mail.getReceiver())
                    .getResultList();
        }catch (Exception e){
            return null;
        }
    }

    public List<Mail> getMails(Mail mail){
        try {
            if (mail.getSender() != null){
                return entityManager.createQuery("select p from Mail p where p.sender=:x")
                        .setParameter("x", mail.getSender())
                        .getResultList();
            }else {
                return entityManager.createQuery("select p from Mail p where p.receiver=:x")
                        .setParameter("x", mail.getReceiver())
                        .getResultList();
            }
        }catch (Exception e){
            return null;
        }
    }

    public String setSeen(Mail mail, String username){
        mail = getMail(mail);
        if (mail != null && mail.getReceiver().equals(username)){
            mail.setSeen(true);
            try {
                entityManager.persist(mail);
            }catch (Exception e){
                logger.error("cant persist", e);
                return "server error";
            }
            return "done";
        }
        return mail != null? "receiver error" : "mail error";
    }
}
