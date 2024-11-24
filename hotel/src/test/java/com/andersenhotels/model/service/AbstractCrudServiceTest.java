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
        service = new TestCrudService(entityManagerFactory, TestEntity.class);
    }

    @AfterEach
    void tearDown() {
        Mockito.reset(entityManager, transaction, entityManagerFactory);
    }

    @Test
    void createEntity_Success() {
        TestEntity entity = new TestEntity(1, "Test");
        doNothing().when(entityManager).persist(entity);

        TestEntity result = service.create(entity);

        assertEquals(entity, result);
        verify(entityManager).persist(entity);
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void createEntity_Failure() {
        TestEntity entity = new TestEntity(1, "Test");
        doThrow(PersistenceException.class).when(entityManager).persist(entity);

        assertThrows(PersistenceException.class, () -> service.create(entity));
        verify(transaction).begin();
        verify(transaction).rollback();
    }

    @Test
    void readEntity_Success() {
        TestEntity entity = new TestEntity(1, "Test");
        when(entityManager.find(TestEntity.class, 1)).thenReturn(entity);

        Optional<TestEntity> result = service.read(1);

        assertTrue(result.isPresent());
        assertEquals(entity, result.get());
        verify(entityManager).find(TestEntity.class, 1);
    }

    @Test
    void readEntity_Failure() {
        when(entityManager.find(TestEntity.class, 1)).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, () -> service.read(1));
        verify(entityManager).find(TestEntity.class, 1);
    }

    @Test
    void updateEntity_Success() {
        TestEntity entity = new TestEntity(1, "Updated");
        when(entityManager.merge(entity)).thenReturn(entity);

        service.update(entity);

        verify(entityManager).merge(entity);
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void updateEntity_Failure() {
        TestEntity entity = new TestEntity(1, "Updated");
        doThrow(PersistenceException.class).when(entityManager).merge(entity);

        assertThrows(PersistenceException.class, () -> service.update(entity));
        verify(transaction).begin();
        verify(transaction).rollback();
    }

    @Test
    void deleteEntity_Success() {
        TestEntity entity = new TestEntity(1, "Test");
        when(entityManager.find(TestEntity.class, 1)).thenReturn(entity);
        doNothing().when(entityManager).remove(entity);

        service.delete(1);

        verify(entityManager).find(TestEntity.class, 1);
        verify(entityManager).remove(entity);
        verify(transaction).begin();
        verify(transaction).commit();
    }

    @Test
    void deleteEntity_Failure() {
        when(entityManager.find(TestEntity.class, 1)).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, () -> service.delete(1));
        verify(transaction).begin();
        verify(transaction).rollback();
    }

    @Test
    void findAllEntities_Success() {
        List<TestEntity> entities = List.of(new TestEntity(1, "Entity1"), new TestEntity(2, "Entity2"));
        TypedQuery<TestEntity> queryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(TestEntity.class))).thenReturn(queryMock);
        when(queryMock.getResultList()).thenReturn(entities);

        List<TestEntity> result = service.findAll();

        assertEquals(entities, result);
        verify(entityManager).createQuery(anyString(), eq(TestEntity.class));
    }

    @Test
    void findAllEntities_Failure() {
        when(entityManager.createQuery(anyString(), eq(TestEntity.class))).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, service::findAll);
    }

    @Test
    void existsById_Success() {
        TypedQuery<Integer> queryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Integer.class))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("id"), eq(1))).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenReturn(1);

        boolean exists = service.existsById(1);

        assertTrue(exists);
        verify(entityManager).createQuery(anyString(), eq(Integer.class));
        verify(queryMock).setParameter(eq("id"), eq(1));
        verify(queryMock).getSingleResult();
    }

    @Test
    void existsById_Failure() {
        TypedQuery<Integer> queryMock = mock(TypedQuery.class);
        when(entityManager.createQuery(anyString(), eq(Integer.class))).thenReturn(queryMock);
        when(queryMock.setParameter(eq("id"), eq(1))).thenReturn(queryMock);
        when(queryMock.getSingleResult()).thenThrow(PersistenceException.class);

        assertThrows(PersistenceException.class, () -> service.existsById(1));
    }

    @Getter
    static class TestEntity {
        private Integer id;
        private String name;

        public TestEntity(Integer id, String name) {
            this.id = id;
            this.name = name;
        }

    }

    static class TestCrudService extends AbstractCrudService<TestEntity, Integer> {

        private final EntityManagerFactory entityManagerFactory;

        protected TestCrudService(EntityManagerFactory entityManagerFactory, Class<TestEntity> entityClass) {
            super(entityClass);
            this.entityManagerFactory = entityManagerFactory;
        }

        @Override
        protected EntityManager getEntityManager() {
            return entityManagerFactory.createEntityManager();
        }
    }
}
