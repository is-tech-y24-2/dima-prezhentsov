package ru.itmo.kotikijava2.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itmo.kotikijava2.dao.model.FriendsEntity;

public interface FriendsRepository extends JpaRepository<FriendsEntity, Integer> {
}
