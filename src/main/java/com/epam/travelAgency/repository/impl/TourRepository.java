package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Tour;
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
public class TourRepository implements Repository<Tour> {

    /*@Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(value = "sessionFactory")
    private SessionFactory sessionFactory;*/
    @PersistenceContext(unitName = "travelAgency")
    private EntityManager entityManager;

    public TourRepository() {
    }

    @Transactional
    @Override
    public void add(AddSpecification<Tour> specification) {
        Tour tour = specification.getEntity();
        if (tour.getTourId() == 0) {
            entityManager.persist(tour);
        } else {
            entityManager.merge(tour);
        }
        /*Session session = sessionFactory.openSession();
        session.merge(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public void update(UpdateSpecification<Tour> specification) {
        entityManager.merge(specification.getEntity());
        /*Session session = sessionFactory.openSession();
        session.update(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public void remove(RemoveSpecification<Tour> specification) {
        Tour tour = specification.getEntity();
        if (entityManager.contains(tour)) {
            entityManager.remove(tour);
        } else {
            entityManager.merge(tour);
        }
        /*Session session = sessionFactory.openSession();
        session.remove(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public Collection<Tour> query(FindSpecification<Tour, ? extends Object> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Tour> criteriaQuery = specification.getCriteriaQuery(criteriaBuilder);
        TypedQuery<Tour> tourTypedQuery = entityManager.createQuery(criteriaQuery);
        return tourTypedQuery.getResultList();
        /*Session session = sessionFactory.openSession();
        org.hibernate.query.Query<Tour> query = session.createQuery(specification.toCriteriaQuery(session));
        Collection<Tour> tours = query.stream().collect(Collectors.toCollection(ArrayList::new));
        session.close();
        return tours;*/
        //return jdbcTemplate.query(specification.getSQLQuery(), new TourService());//TODO DI
    }

}
