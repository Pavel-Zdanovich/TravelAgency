package com.zdanovich.web.search;

import com.zdanovich.core.entity.AbstractEntity;
import com.zdanovich.core.entity.Country;
import com.zdanovich.core.entity.Feature;
import com.zdanovich.core.entity.Hotel;
import com.zdanovich.core.entity.Review;
import com.zdanovich.core.entity.Tour;
import com.zdanovich.core.entity.User;
import org.hibernate.search.annotations.Resolution;
import org.hibernate.search.cfg.Environment;
import org.hibernate.search.cfg.SearchMapping;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.ContextStartedEvent;
import org.springframework.orm.jpa.AbstractEntityManagerFactoryBean;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.lang.annotation.ElementType;
import java.util.Map;

@Configuration
public class HibernateSearch implements BeanPostProcessor { //ApplicationContextAware, InitializingBean {

    @Component
    public class HibernateSearchListener implements ApplicationListener<ContextStartedEvent> { //TODO choose more suitable event

        @PersistenceContext(unitName = "entityManagerFactory")
        private EntityManager entityManager;

        @Override
        @Transactional
        public void onApplicationEvent(ContextStartedEvent event) {
            FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(this.entityManager);
            try {
                fullTextEntityManager.createIndexer(
                        //AbstractEntity.class
                        //Country.class,
                        //Feature.class,
                        //Hotel.class,
                        //Review.class,
                        //Tour.class,
                        //User.class
                ).startAndWait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        if (bean instanceof AbstractEntityManagerFactoryBean) {
            AbstractEntityManagerFactoryBean entityManagerFactoryBean = (AbstractEntityManagerFactoryBean) bean;
            Map<String, Object> jpaPropertyMap = entityManagerFactoryBean.getJpaPropertyMap();
            jpaPropertyMap.put(Environment.MODEL_MAPPING, this.searchMapping());
            jpaPropertyMap.put("hibernate.search.default.directory_provider", "filesystem");
            jpaPropertyMap.put("hibernate.search.default.indexBase", "C:\\Users\\Zdan\\IdeaProjects\\travelagency\\web\\src\\test\\resources");
            jpaPropertyMap.put("hibernate.search.default.indexName", "index");
        }
        return bean;
    }

    public SearchMapping searchMapping() {
        SearchMapping searchMapping = new SearchMapping();
        searchMapping
                .entity(AbstractEntity.class).indexed()
                .property("id", ElementType.FIELD).documentId()
                .entity(Country.class).indexed()
                .property("name", ElementType.FIELD).field()
                .entity(Feature.class).indexed()
                .property("name", ElementType.FIELD).field()
                .entity(Hotel.class).indexed()
                .property("name", ElementType.FIELD).field()
                .property("stars", ElementType.FIELD).field().numericField()
                .property("website", ElementType.FIELD).field()
                .property("latitude", ElementType.FIELD).latitude()
                .property("longitude", ElementType.FIELD).longitude()
                .entity(Review.class).indexed()
                .property("reviewDate", ElementType.FIELD).dateBridge(Resolution.DAY)
                .property("reviewText", ElementType.FIELD).field()
                .entity(Tour.class).indexed()
                //.property("photoPath", ElementType.FIELD)
                .property("startDate", ElementType.FIELD).dateBridge(Resolution.DAY)
                .property("endDate", ElementType.FIELD).dateBridge(Resolution.DAY)
                .property("description", ElementType.FIELD).field()
                .property("cost", ElementType.FIELD).bridge(BigDecimalNumericFieldBridge.class)
                .property("tourType", ElementType.FIELD).field()
                .entity(User.class).indexed()
                .property("login", ElementType.FIELD).field()
        //.property("password", ElementType.FIELD)
        //.property("userRole", ElementType.FIELD)
        ;
        return searchMapping;
    }
}
