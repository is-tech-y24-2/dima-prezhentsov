package ru.itmo.kotikiservices.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotikiservices.dao.model.CatsEntity;

@Repository
public interface CatRepository extends JpaRepository<CatsEntity, Integer> {
    public CatsEntity findByColor(String color);
}
