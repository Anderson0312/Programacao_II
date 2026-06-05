package br.edu.prog2.util;

import jakarta.persistence.EntityManager;

public final class JpaUtil {

    private static final ThreadLocal<EntityManager> ENTITY_MANAGER = new ThreadLocal<>();

    private JpaUtil() {
    }

    public static void setEntityManager(EntityManager entityManager) {
        ENTITY_MANAGER.set(entityManager);
    }

    public static EntityManager getEntityManager() {
        EntityManager entityManager = ENTITY_MANAGER.get();
        if (entityManager == null || !entityManager.isOpen()) {
            throw new IllegalStateException("EntityManager indisponivel para a requisicao atual.");
        }
        return entityManager;
    }

    public static void clear() {
        ENTITY_MANAGER.remove();
    }
}
