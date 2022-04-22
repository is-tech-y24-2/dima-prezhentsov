package ru.itmo.kotikijava2.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotikijava2.dao.model.CatsEntity;
import ru.itmo.kotikijava2.dao.model.Color;
import ru.itmo.kotikijava2.dao.model.OwnersEntity;

@Repository
public interface CatRepository extends JpaRepository<CatsEntity, Integer> {
    public CatsEntity findByColor(String color);
}
