/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.myappserver.persistence.base;

import com.mycompany.myappserver.business.base.BaseEntity;
import com.mycompany.myappserver.exceptions.InfraestructureException;
import com.mycompany.myappserver.utils.logger.Log;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.TransactionRequiredException;
import org.hibernate.Session;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.service.jdbc.connections.spi.ConnectionProvider;

/**
 *
 * @author Alexandra
 */
public abstract class BaseDao<T extends BaseEntity> implements Serializable {

    /**
     * The Constant DEFAULT_PERSISTENCE_UNIT_NAME.
     */
    private static final String DEFAULT_PERSISTENCE_UNIT_NAME = "MyAppBD-pu";

    /**
     * The persistence_unit.
     */
    private String persistence_unit = null;

    /**
     * The entity manager factory.
     */
    private EntityManagerFactory entityManagerFactory;

    /**
     * The entity manager factory map.
     */
    private static Map<String, EntityManagerFactory> entityManagerFactoryMap = new HashMap<String, EntityManagerFactory>();

    /**
     * The lock1.
     */
    private static Object lock1 = new Object();

    /**
     * Constructor.
     */
    public BaseDao() {

    }

    /**
     * Constructor.
     *
     * @param persistenceUnitName the persistence unit name
     */
    public BaseDao(String persistenceUnitName) {
        super();
        synchronized (lock1) {
            this.persistence_unit = persistenceUnitName;
            if (entityManagerFactoryMap.get(persistence_unit) == null) {
                Log.info("didn't found the manager factory for: "
                        + persistenceUnitName + " so create a new one");
                entityManagerFactoryMap.put(persistenceUnitName,
                        Persistence.createEntityManagerFactory(persistenceUnitName));
            }
        }
    }

    /**
     * Gets the entity manager.
     *
     * @return EntityManager
     */
    public EntityManager getEntityManager() {
        // DeclaraciÃ³n del objeto EntityManager
        EntityManager entityManager = null;

        // Si la unidad de persistencia no existe 
        if (persistence_unit != null) {
            // Se obtiene el entityManagerFactory del Map, el cual si fue inicializado en el Constructor se creo con la unidad
            // de persistencia por defecto y final instanciada en esta clase
            entityManagerFactory = entityManagerFactoryMap.get(persistence_unit);
            if (entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory(persistence_unit);
                entityManagerFactoryMap.put(persistence_unit, entityManagerFactory);
            }
            entityManager = entityManagerFactory.createEntityManager();
        } else {
            entityManagerFactory = entityManagerFactoryMap.get(DEFAULT_PERSISTENCE_UNIT_NAME);
            if (entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory(DEFAULT_PERSISTENCE_UNIT_NAME);
                entityManagerFactoryMap.put(DEFAULT_PERSISTENCE_UNIT_NAME, entityManagerFactory);
            }
            entityManager = entityManagerFactory.createEntityManager();
        }

        return entityManager;
    }

    /**
     * Gets the entity manager.
     *
     * @param persistenceUnitName the persistence unit name
     * @return Entity Manager
     */
    public EntityManager getEntityManager(String persistenceUnitName) {

        EntityManager entityManager = null;

        if (persistenceUnitName != null) {
            entityManagerFactory = entityManagerFactoryMap.get(persistenceUnitName);
            if (entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
                entityManagerFactoryMap.put(persistence_unit, entityManagerFactory);
            }
            entityManager = entityManagerFactory.createEntityManager();
        } else {
            entityManagerFactory = entityManagerFactoryMap.get(DEFAULT_PERSISTENCE_UNIT_NAME);
            if (entityManagerFactory == null) {
                entityManagerFactory = Persistence.createEntityManagerFactory(DEFAULT_PERSISTENCE_UNIT_NAME);
                entityManagerFactoryMap.put(DEFAULT_PERSISTENCE_UNIT_NAME, entityManagerFactory);
            }
            entityManager = entityManagerFactory.createEntityManager();
        }
        return entityManager;
    }

    /**
     * Gets the connection.
     *
     * @return the connection
     * @throws InfraestructureException the persistence exception
     */
    public Connection getConnection() throws InfraestructureException {
        return this.getConnection(this.getEntityManager());
    }

    /**
     * Gets the connection.
     *
     * @param entityManager the entity manager
     * @return the connection
     * @throws InfraestructureException the persistence exception
     */
    public Connection getConnection(EntityManager entityManager) throws InfraestructureException {
        Connection con = null;
        try {
            Session session = (Session) entityManager.getDelegate();
            SessionFactoryImplementor sfi = (SessionFactoryImplementor) session.getSessionFactory();
            ConnectionProvider cp = sfi.getConnectionProvider();
            con = cp.getConnection();
        } catch (SQLException sqle) {
            new InfraestructureException("BaseDao.getConnection: SQLException: " + sqle.getMessage(), sqle);
        } catch (Exception e) {
            new InfraestructureException("BaseDao.getConnection: Exception: " + e.getMessage(), e);
        }
        return con;

    }

    /**
     * Close db resources.
     *
     * @param rs the rs
     * @param st the st
     * @param con the con
     */
    public void closeDBResources(ResultSet rs, Statement st, Connection con) {
        // Close result set
        if (rs != null) {
            try {
                rs.close();
            } catch (SQLException e) {
                //getLogger().warn(e.getMessage());
            }
        }
        // Close statement
        if (st != null) {
            try {
                st.close();
            } catch (SQLException e) {
                //getLogger().warn(e.getMessage());
            }
        }
        // Close DB connection
        if (con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                //getLogger().warn(e.getMessage());
            }
        }
    }

    /**
     * Read entity.
     *
     * @param entity the entity
     * @param id the id
     * @return the t
     * @throws InfraestructureException the jPA exception
     */
    @SuppressWarnings("unchecked")
    public T readEntity(T entity, Object id) throws InfraestructureException {
        EntityManager entityManager = null;
        try {
            entityManager = getEntityManager();
            return entityManager.find((Class<T>) entity.getClass(), id);
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
            throw new InfraestructureException("BaseDao.readEntity: IllegalStateException: " + ise.getMessage(), ise);
        } catch (TransactionRequiredException te) {
            te.printStackTrace();
            throw new InfraestructureException("BaseDao.readEntity: TransactionRequiredException: " + te.getMessage(), te);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InfraestructureException("BaseDao.readEntity: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Delete entity.
     *
     * @param entity the entity
     * @param id the id
     * @throws InfraestructureException the jPA exception
     */
    @SuppressWarnings("unchecked")
    public void deleteEntity(T entity, Object id) throws InfraestructureException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.remove(entityManager.find((Class<T>) entity.getClass(), id));
            entityManager.flush();
            entityTransaction.commit();
        } catch (IllegalStateException ise) {
            entityTransaction.rollback();
            ise.printStackTrace();
            throw new InfraestructureException("BaseDao.deleteEntity: IllegalStateException: " + ise.getMessage(), ise);
        } catch (TransactionRequiredException te) {
            entityTransaction.rollback();
            te.printStackTrace();
            throw new InfraestructureException("BaseDao.deleteEntity: TransactionRequiredException: " + te.getMessage(), te);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
            throw new InfraestructureException("BaseDao.deleteEntity: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }
    
    /**
     * Creates the entity.
     *
     * @param entity the entity
     * @throws InfraestructureException the jPA exception
     */
    public void createEntity(T entity) throws InfraestructureException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entityManager.persist(entity);
            entityManager.flush();
            entityTransaction.commit();
        } catch (EntityExistsException ee) {
            entityTransaction.rollback();
            ee.printStackTrace();
            throw new InfraestructureException("BaseDao.createEntity: EntityExistsException: " + ee.getMessage(), ee);
        } catch (IllegalStateException ise) {
            entityTransaction.rollback();
            ise.printStackTrace();
            throw new InfraestructureException("BaseDao.createEntity: IllegalStateException: " + ise.getMessage(), ise);
        } catch (TransactionRequiredException te) {
            entityTransaction.rollback();
            te.printStackTrace();
            throw new InfraestructureException("BaseDao.createEntity: TransactionRequiredException: " + te.getMessage(), te);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
            throw new InfraestructureException("BaseDao.createEntity: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Update entity.
     *
     * @param entity the entity
     * @return the t
     * @throws InfraestructureException the jPA exception
     */
    public T updateEntity(T entity) throws InfraestructureException {
        EntityManager entityManager = getEntityManager();
        EntityTransaction entityTransaction = entityManager.getTransaction();
        entityTransaction.begin();
        try {
            entity = entityManager.merge(entity);
            entityManager.flush();
            entityTransaction.commit();
            return entity;
        } catch (IllegalStateException ise) {
            entityTransaction.rollback();
            ise.printStackTrace();
            throw new InfraestructureException("BaseDao.updateEntity: IllegalStateException: " + ise.getMessage(), ise);
        } catch (TransactionRequiredException te) {
            entityTransaction.rollback();
            te.printStackTrace();
            throw new InfraestructureException("BaseDao.updateEntity: TransactionRequiredException: " + te.getMessage(), te);
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
            throw new InfraestructureException("BaseDao.updateEntity: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }

    }

    /**
     * Read entities list.
     *
     * @param entity the entity
     * @return List<T>
     * @throws InfraestructureException the jPA exception
     */
    @SuppressWarnings("unchecked")
    public Collection<T> readEntitiesList(T entity) throws InfraestructureException {
        EntityManager entityManager = getEntityManager();
        try {
            return entityManager.createQuery("SELECT x from " + entity.getClass().getSimpleName() + " x ").getResultList();
        } catch (NoResultException nre) {
            return null;
        } catch (IllegalStateException ise) {
            throw new InfraestructureException("BaseDao.readListEntities: IllegalStateException: " + ise.getMessage(), ise);
        } catch (Exception e) {
            throw new InfraestructureException("BaseDao.readListEntities: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Read enable entities list.
     *
     * @param entity the entity
     * @return the collection
     * @throws InfraestructureException the jPA exception
     */
    @SuppressWarnings("unchecked")
    public Collection<T> readEnableEntitiesList(T entity) throws InfraestructureException {
        String query = "SELECT x from " + entity.getClass().getSimpleName() + " x";
        EntityManager entityManager = getEntityManager();
        try {
            if (entity instanceof BaseEntity) {
                query += " WHERE x.statusId = 1";
            }
            return entityManager.createQuery(query).getResultList();
        } catch (IllegalStateException ise) {
            throw new InfraestructureException("BaseDao.readEnableEntitiesList: IllegalStateException: " + ise.getMessage(), ise);
        } catch (Exception e) {
            throw new InfraestructureException("BaseDao.readEnableEntitiesList: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Gets the last id.
     *
     * @param nombreEntidad the nombre entidad
     * @param nombreCampo the nombre campo
     * @return the last id
     * @throws InfraestructureException the jPA exception
     */
    public long getLastId(String nombreEntidad, String nombreCampo) throws InfraestructureException {
        EntityManager entityManager = getEntityManager();
        try {
            long maxId = 0;
            Object o = entityManager.createQuery("SELECT MAX (x." + nombreCampo + ") from " + nombreEntidad + " x").getSingleResult();
            if (o != null) {
                maxId = (Long) o;
            }
            return maxId;
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
            throw new InfraestructureException("BaseDao.getLastId: IllegalStateException: " + ise.getMessage(), ise);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InfraestructureException("BaseDao.getLastId: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Gets the next id sequence.
     *
     * @param sequencename the sequencename
     * @return the next id sequence
     * @throws InfraestructureException the jPA exception
     */
    public BigDecimal getNextIdSequence(String sequencename) throws InfraestructureException {
        EntityManager entityManager = getEntityManager();
        try {
            BigDecimal currVal = null;
            Object o = entityManager.createNativeQuery("select last_number from user_sequences where sequence_name='" + sequencename + "'").getSingleResult();
            if (o != null) {
                currVal = (BigDecimal) o;
            }
            return currVal;
        } catch (IllegalStateException ise) {
            ise.printStackTrace();
            throw new InfraestructureException("BaseDao.getNextIdSequence: IllegalStateException: " + ise.getMessage(), ise);
        } catch (Exception e) {
            e.printStackTrace();
            throw new InfraestructureException("BaseDao.getNextIdSequence: Exception: " + e.getMessage(), e);
        } finally {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();
                entityManager = null;
            }
        }
    }

    /**
     * Dispose.
     */
    public void dispose() {
        for (Map.Entry<String, EntityManagerFactory> entry : entityManagerFactoryMap.entrySet()) {
            EntityManagerFactory value = (EntityManagerFactory) entry.getValue();
            value.close();
        }
    }

}
