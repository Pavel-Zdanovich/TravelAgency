package com.epam.core.unit.repository;

import com.epam.core.repository.GeneralRepository;
import com.epam.core.util.GeneralCriterion;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.Serializable;

@RunWith(MockitoJUnitRunner.class)
public class GeneralRepositoryJPA2Test {

    @Mock
    private GeneralRepository generalRepository;

    @Test
    public void save() {
        Serializable entity = Mockito.mock(Serializable.class);
        Mockito.when(generalRepository.save(entity)).thenReturn(entity);
        Assert.assertEquals(generalRepository.save(entity), entity);
    };

    @Test
    public void saveAll() {
        Iterable<Serializable> iterable = Mockito.mock(Iterable.class);
        Mockito.when(generalRepository.saveAll(iterable)).thenReturn(iterable);
        Assert.assertEquals(generalRepository.saveAll(iterable), iterable);
    };

    @Test
    public void findBy() {
        GeneralCriterion<Serializable> generalCriterion = Mockito.mock(GeneralCriterion.class);
        Iterable<Serializable> iterable = Mockito.mock(Iterable.class);
        Mockito.when(generalRepository.findBy(Serializable.class, generalCriterion)).thenReturn(iterable);
        Assert.assertEquals(generalRepository.findBy(Serializable.class, generalCriterion), iterable);
    };

    @Test
    public void findAll() {
        Iterable<Serializable> iterable = Mockito.mock(Iterable.class);
        Mockito.when(generalRepository.findAll(Serializable.class)).thenReturn(iterable);
        Assert.assertEquals(generalRepository.findAll(Serializable.class), iterable);
    };

    @Test
    public void update() {
        Serializable entity = Mockito.mock(Serializable.class);
        Mockito.when(generalRepository.update(entity)).thenReturn(entity);
        Assert.assertEquals(generalRepository.update(entity), entity);
    };

    @Test
    public void updateBy() {
        GeneralCriterion<Serializable> generalCriterion = Mockito.mock(GeneralCriterion.class);
        Iterable<Serializable> iterable = Mockito.mock(Iterable.class);
        Mockito.when(generalRepository.updateBy(Serializable.class, generalCriterion)).thenReturn(iterable);
        Assert.assertEquals(generalRepository.updateBy(Serializable.class, generalCriterion), iterable);
    };

    @Test
    public void updateAll() {
        Iterable<Serializable> iterable = Mockito.mock(Iterable.class);
        Mockito.when(generalRepository.updateAll(iterable)).thenReturn(iterable);
        Assert.assertEquals(generalRepository.updateAll(iterable), iterable);
    };

    @Test
    public void delete() {
        Serializable entity = Mockito.mock(Serializable.class);
        generalRepository.delete(entity);
    };

    @Test
    public void deleteBy() {
        GeneralCriterion<Serializable> generalCriterion = Mockito.mock(GeneralCriterion.class);
        generalRepository.deleteBy(Serializable.class, generalCriterion);
    };

    @Test
    public void deleteAll() {
        Iterable<Serializable> iterable = Mockito.mock(Iterable.class);
        generalRepository.deleteAll(iterable);
    };

}
