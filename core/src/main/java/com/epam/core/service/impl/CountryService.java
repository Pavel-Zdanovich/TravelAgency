package com.epam.core.service.impl;

import com.epam.core.config.DataSourceConfig;
import com.epam.core.config.PersistenceConfig;
import com.epam.core.entity.Country;
import com.epam.core.repository.CountryRepository;
import com.epam.core.service.AbstractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Profile(value = "dev")
public class CountryService extends AbstractService<Country, Long, CountryRepository> {

    @Autowired
    public CountryService(CountryRepository repository) {
        super(repository);
    }

    public Optional<Country> findByName(String name) {
        return repository.findByName(name);
    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvironment().setActiveProfiles("dev", "oracle");
        context.register(DataSourceConfig.class, PersistenceConfig.class,
                CountryRepository.class, CountryService.class);
        context.refresh();
        CountryService countryService = context.getBean(CountryService.class);
        Country country = new Country();
        country.setId(2L);
        country.setName("Brazil");
//        countryService.save(country);
//        countryService.delete(country);
//        System.out.println(countryService.count());
        Country foundCountry = countryService.findByName(country.getName()).orElse(null);
        System.out.println("Country : " + foundCountry);

    }

}
