package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.repository.Repository;
import com.epam.travelAgency.specification.AddSpecification;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.RemoveSpecification;
import com.epam.travelAgency.specification.UpdateSpecification;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Collection;

@org.springframework.stereotype.Repository
@Transactional(transactionManager = "jpaTransactionManager")
public class ReviewRepository implements Repository<Review> {

    /*@Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(value = "sessionFactory")
    private SessionFactory sessionFactory;*/
    @PersistenceContext(unitName = "travelAgency")
    private EntityManager entityManager;

    public ReviewRepository() {
    }

    @Transactional
    @Override
    public void add(AddSpecification<Review> specification) {
        Review review = specification.getEntity();
        if (review.getReviewId() == 0) {
            entityManager.persist(review);
        } else {
            entityManager.merge(review);
        }
        /*Session session = sessionFactory.openSession();
        session.merge(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public void update(UpdateSpecification<Review> specification) {
        entityManager.merge(specification.getEntity());
        /*Session session = sessionFactory.openSession();
        session.update(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public void remove(RemoveSpecification<Review> specification) {
        Review review = specification.getEntity();
        if (entityManager.contains(review)) {
            entityManager.remove(review);
        } else {
            entityManager.merge(review);
        }
        /*Session session = sessionFactory.openSession();
        session.remove(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public Collection<Review> query(FindSpecification<Review, ? extends Object> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Review> criteriaQuery = specification.getCriteriaQuery(criteriaBuilder);
        TypedQuery<Review> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
        /*Session session = sessionFactory.openSession();
        org.hibernate.query.Query<Review> query = session.createQuery(specification.toCriteriaQuery(session));
        Collection<Review> reviews = query.stream().collect(Collectors.toCollection(ArrayList::new));
        session.close();
        return reviews;*/
        //return jdbcTemplate.query(specification.getSQLQuery(), new ReviewService());//TODO DI
    }

}
