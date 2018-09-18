package fr.istic.gm.weplan.infra.repository.impl;

import fr.istic.gm.weplan.domain.model.entities.BaseEntity;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.Optional;

@AllArgsConstructor
public abstract class BaseRepository<T extends BaseEntity> {

    protected Class<T> clazz;

    protected String table;

    protected EntityManager entityManager;

    public Optional<T> findById(Long id) {
        return Optional.of(entityManager.find(clazz, id));
    }

    public Page<T> findAll(Pageable pageable) {
        return new PageImpl<>(new ArrayList<>());
    }

    public T save(T object) {
        entityManager.persist(object);
        return object;
    }
}
