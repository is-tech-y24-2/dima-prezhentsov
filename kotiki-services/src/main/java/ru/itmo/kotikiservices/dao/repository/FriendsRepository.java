package ru.itmo.kotikiservices.dao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.itmo.kotikiservices.dao.model.FriendsEntity;

@Repository
public interface FriendsRepository extends JpaRepository<FriendsEntity, Integer> {
}
