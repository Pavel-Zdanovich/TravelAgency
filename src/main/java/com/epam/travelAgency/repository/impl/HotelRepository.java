package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.entity.Hotel;
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
public class HotelRepository implements Repository<Hotel> {

    /*@Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(value = "sessionFactory")
    private SessionFactory sessionFactory;*/
    @PersistenceContext(unitName = "travelAgency")
    private EntityManager entityManager;

    public HotelRepository() {
    }

    @Transactional
    @Override
    public void add(AddSpecification<Hotel> specification) {
        Hotel hotel = specification.getEntity();
        if (hotel.getHotelId() == 0) {
            entityManager.persist(hotel);
        } else {
            entityManager.merge(hotel);
        }
        /*Session session = sessionFactory.openSession();
        session.merge(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public void update(UpdateSpecification<Hotel> specification) {
        entityManager.merge(specification.getEntity());
        /*Session session = sessionFactory.openSession();
        session.update(specification.getEntity());
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public void remove(RemoveSpecification<Hotel> specification) {
        Hotel hotel = specification.getEntity();
        if (entityManager.contains(hotel)) {
            entityManager.remove(specification.getEntity());
        } else {
            entityManager.merge(hotel);
        }
        /*Session session = sessionFactory.openSession();
        session.remove(specification.getEntity());//Hibernate execute DELETE FROM hotels WHERE hotel_id = ?
        session.close();*/
        //jdbcTemplate.update(specification.getSQLQuery(), specification);
    }

    @Transactional
    @Override
    public Collection<Hotel> query(FindSpecification<Hotel, ? extends Object> specification) {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Hotel> criteriaQuery = specification.getCriteriaQuery(criteriaBuilder);
        TypedQuery<Hotel> typedQuery = entityManager.createQuery(criteriaQuery);
        return typedQuery.getResultList();
        /*Session session = sessionFactory.openSession();
        org.hibernate.query.Query<Hotel> query = session.createQuery(specification.toCriteriaQuery(session));
        Collection<Hotel> hotels = query.stream().collect(Collectors.toCollection(ArrayList::new));
        session.close();
        return hotels;*/
        //return jdbcTemplate.query(specification.getSQLQuery(), new HotelService());//TODO DI
    }

}
