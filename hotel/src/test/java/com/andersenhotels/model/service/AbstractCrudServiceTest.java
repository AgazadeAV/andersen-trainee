package com.andersenhotels.model.service;

import jakarta.persistence.*;
import lombok.Getter;
import org.junit.jupiter.api.*;
import org.mockito.*;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbstractCrudServiceTest {

    @Mock
    private AbstractCrudService<TestEntity, Integer> service;

    @Mock
    private EntityManagerFactory entityManagerFactory;

    @Mock
    private EntityManager entityManager;

    @Mock
    private EntityTransaction transaction;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(entityManagerFactory.createEntityManager()).thenReturn(entityManager);
        when(entityManager.getTransaction()).thenReturn(transaction);
        doNothing().when(transaction).rollback();
    }

    @Test
    void createEntity_Success_WithMockedService() {
        TestEntity entity = new TestEntity(1, "Test");
        when(service.create(entity)).thenReturn(entity);

        TestEntity result = service.create(entity);

        assertEquals(entity, result);
        verify(service).create(entity);
    }

    @Test
    void createEntity_Failure_WithMockedService() {
        TestEntity entity = new TestEntity(1, "Test");
        doThrow(PersistenceException.class).when(service).create(entity);

        assertThrows(PersistenceException.class, () -> service.create(entity));
        verify(service).create(entity);
    }

    @Test
    void readEntity_Success_WithMockedService() {
        TestEntity entity = new TestEntity(1, "Test");
        when(service.read(1)).thenReturn(Optional.of(entity));

        Optional<TestEntity> result = service.read(1);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(service).read(1);
    }

    @Test
    void readEntity_Failure_WithMockedService() {
        when(service.read(1)).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, () -> service.read(1));
        verify(service).read(1);
    }

    @Test
    void updateEntity_Success_WithMockedService() {
        TestEntity entity = new TestEntity(1, "Updated");
        doNothing().when(service).update(entity);

        service.update(entity);

        verify(service).update(entity);
    }

    @Test
    void updateEntity_Failure_WithMockedService() {
        TestEntity entity = new TestEntity(1, "Updated");
        doThrow(PersistenceException.class).when(service).update(entity);

        assertThrows(PersistenceException.class, () -> service.update(entity));
        verify(service).update(entity);
    }

    @Test
    void deleteEntity_Success_WithMockedService() {
        doNothing().when(service).delete(1);

        service.delete(1);

        verify(service).delete(1);
    }

    @Test
    void deleteEntity_Failure_WithMockedService() {
        doThrow(PersistenceException.class).when(service).delete(1);

        assertThrows(PersistenceException.class, () -> service.delete(1));
        verify(service).delete(1);
    }

    @Test
    void findAllEntities_Success_WithMockedService() {
        List<TestEntity> entities = List.of(new TestEntity(1, "Entity1"), new TestEntity(2, "Entity2"));
        when(service.findAll()).thenReturn(entities);

        List<TestEntity> result = service.findAll();

        assertEquals(entities, result);
        verify(service).findAll();
    }

    @Test
    void findAllEntities_Failure_WithMockedService() {
        when(service.findAll()).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, service::findAll);
        verify(service).findAll();
    }

    @Test
    void existsById_Success_WithMockedService() {
        when(service.existsById(1)).thenReturn(true);

        boolean exists = service.existsById(1);

        assertTrue(exists);
        verify(service).existsById(1);
    }

    @Test
    void existsById_Failure_WithMockedService() {
        when(service.existsById(1)).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, () -> service.existsById(1));
        verify(service).existsById(1);
    }

    @Getter
    static class TestEntity {
        private final Integer id;
        private final String name;

        public TestEntity(Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }
}
