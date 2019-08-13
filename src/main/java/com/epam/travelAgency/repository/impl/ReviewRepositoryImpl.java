package com.epam.travelAgency.repository.impl;

import com.epam.travelAgency.config.JDBCConfig;
import com.epam.travelAgency.config.RepositoryConfig;
import com.epam.travelAgency.config.TransactionConfig;
import com.epam.travelAgency.entity.Review;
import com.epam.travelAgency.entity.Tour;
import com.epam.travelAgency.entity.User;
import com.epam.travelAgency.repository.ReviewRepository;
import com.epam.travelAgency.specification.AddSpecification;
import com.epam.travelAgency.specification.FindSpecification;
import com.epam.travelAgency.specification.RemoveSpecification;
import com.epam.travelAgency.specification.UpdateSpecification;
import com.epam.travelAgency.specification.impl.review.AddReviewSpecification;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.sql.Timestamp;
import java.util.Collection;

@org.springframework.stereotype.Repository(value = "reviewRepository")
@Transactional(transactionManager = "jpaTransactionManager")
public class ReviewRepositoryImpl implements ReviewRepository {

    /*@Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    @Qualifier(value = "sessionFactory")
    private SessionFactory sessionFactory;*/
    @PersistenceContext(unitName = "travelAgency", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public ReviewRepositoryImpl() {
    }

    @Transactional
    @Override
    public void add(AddSpecification<Review> specification) {
        Review review = specification.getEntity();
        if (review != null && review.getReviewId() == 0) {
            entityManager.persist(review);
            entityManager.flush();
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

    public static void main(String[] args) throws Exception {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.getEnvironment().setActiveProfiles("dev", "test", "real_dataSource");
        applicationContext.register(JDBCConfig.class, TransactionConfig.class, RepositoryConfig.class);
        applicationContext.refresh();
        ReviewRepository reviewRepository = applicationContext.getBean(ReviewRepository.class);
        Review review = new Review();
        review.setReviewId(1L);
        review.setText("awesome");
        review.setDate(new Timestamp(System.currentTimeMillis()));
        Tour tour = new Tour();
        tour.setTourId(1L);
        review.setTour(tour);
        User user = new User();
        user.setUserId(1L);
        review.setUser(user);
        AddReviewSpecification addReviewSpecification = new AddReviewSpecification(review);
        reviewRepository.add(addReviewSpecification);

    }

}
