package ru.itmo.kotikijava2.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotikijava2.dao.model.FriendsEntity;

@Repository
public interface FriendsRepository extends JpaRepository<FriendsEntity, Integer> {
}
