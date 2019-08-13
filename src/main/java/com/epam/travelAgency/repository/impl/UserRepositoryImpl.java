package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.repository.UserRepository;
import com.epam.travelAgency.specification.AddSpecification;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.RemoveSpecification;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

@org.springframework.stereotype.Repository(value = "userRepository")
@Transactional(transactionManager = "jpaTransactionManager")
public class UserRepositoryImpl implements UserRepository {

    /*@Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(value = "sessionFactory")
    private SessionFactory sessionFactory;*/
    @PersistenceContext(unitName = "travelAgency", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public UserRepositoryImpl() {
    }

    @Transactional
    @Override
    public void add(AddSpecification<User> specification) {
        User user = specification.getEntity();
        if (user != null && user.getUserId() == 0) {
            entityManager.persist(user);
            entityManager.flush();
        } else {
            entityManager.merge(user);
        }
        /*Session session = sessionFactory.openSession();
        session.merge(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public void update(UpdateSpecification<User> specification) {
        entityManager.merge(specification.getEntity());
        /*Session session = sessionFactory.openSession();
        session.update(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public void remove(RemoveSpecification<User> specification) {
        User user = specification.getEntity();
        if (entityManager.contains(user)) {
            entityManager.remove(specification.getEntity());
        } else {
            entityManager.merge(user);
        }
        /*Session session = sessionFactory.openSession();
        session.remove(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public Collection<User> query(FindSpecification<User, ? extends Object> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> criteriaQuery = specification.getCriteriaQuery(criteriaBuilder);
        TypedQuery<User> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
        /*Session session = sessionFactory.openSession();
        org.hibernate.query.Query<User> query = session.createQuery(specification.toCriteriaQuery(session));
        Collection<User> users = query.stream().collect(Collectors.toCollection(ArrayList::new));
        session.close();
        return users;*/
        //return jdbcTemplate.query(specification.getSQLQuery(), new UserService());//TODO DI
    }

}
