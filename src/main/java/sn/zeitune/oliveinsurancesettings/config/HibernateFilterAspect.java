package sn.zeitune.oliveinsurancesettings.config;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.springframework.stereotype.Component;

@Component
@Aspect
public class HibernateFilterAspect {

    @PersistenceContext
    private EntityManager entityManager;

    @Before("execution(* sn.zeitune..repository.*.*(..))")
    public void enableFilterForRepositories() {
        Session session = entityManager.unwrap(Session.class);
        if (session.getEnabledFilter("deletedFilter") == null) {
            session.enableFilter("deletedFilter").setParameter("isDeleted", false);
        }
    }
}
