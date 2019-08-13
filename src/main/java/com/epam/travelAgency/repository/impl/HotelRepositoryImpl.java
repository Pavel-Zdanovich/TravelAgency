package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.config.JDBCConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.TransactionConfig;
import com.epam.travelAgency.entity.Feature;
import com.epam.travelAgency.entity.Hotel;
import com.epam.travelAgency.repository.HotelRepository;
import com.epam.travelAgency.specification.AddSpecification;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.RemoveSpecification;
import com.epam.travelAgency.specification.UpdateSpecification;
import com.epam.travelAgency.specification.impl.hotel.AddHotelSpecification;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.net.URL;
import java.util.Collection;

@org.springframework.stereotype.Repository(value = "hotelRepository")
@Transactional(transactionManager = "jpaTransactionManager")
public class HotelRepositoryImpl implements HotelRepository {

    /*@Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(value = "sessionFactory")
    private SessionFactory sessionFactory;*/
    @PersistenceContext(unitName = "travelAgency", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public HotelRepositoryImpl() {
    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Transactional
    @Override
    public void add(AddSpecification<Hotel> specification) {
        Hotel hotel = specification.getEntity();
        if (hotel != null && hotel.getHotelId() == 0) {
            entityManager.persist(hotel);
            entityManager.flush();
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

    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("dev", "test", "real_dataSource");
        applicationContext.register(JDBCConfig.class, TransactionConfig.class, RepositoryConfig.class);
        applicationContext.refresh();
        HotelRepository hotelRepository = applicationContext.getBean(HotelRepository.class);
        Hotel hotel = new Hotel();
        hotel.setHotelId(1L);
        hotel.setStars(5);
        hotel.setName("Marriott");
        hotel.setWebsite(new URL("https://www.marriot.com"));
        hotel.setFeatures(new Feature[]{Feature.AIR_CONDITIONER, Feature.CAR_RENTAL});
        AddHotelSpecification addHotelSpecification = new AddHotelSpecification(hotel);
        hotelRepository.add(addHotelSpecification);

    }

}
