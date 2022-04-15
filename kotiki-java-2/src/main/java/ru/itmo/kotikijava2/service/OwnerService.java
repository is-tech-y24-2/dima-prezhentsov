package ru.itmo.kotikijava2.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotikijava2.dao.model.CatsEntity;
import ru.itmo.kotikijava2.dao.model.OwnersEntity;
import ru.itmo.kotikijava2.dao.repository.CatRepository;
import ru.itmo.kotikijava2.dao.repository.FriendsRepository;
import ru.itmo.kotikijava2.dao.repository.OwnerRepository;

import java.sql.Date;
import java.util.List;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public void add(String name, Date birthday) {
        OwnersEntity ownersEntity = new OwnersEntity(name, birthday);
        ownerRepository.save(ownersEntity);
    }

    public void update(OwnersEntity ownersEntity) {
        ownerRepository.save(ownersEntity);
    }

    public void delete(OwnersEntity ownersEntity) {
        ownerRepository.delete(ownersEntity);
    }

    public OwnersEntity getById(int id) {
        return ownerRepository.getById(id);
    }

    public List<CatsEntity> getCats(OwnersEntity ownersEntity) {
        return ownersEntity.getCats();
    }

    public List<OwnersEntity> getAll() {
        return ownerRepository.findAll();
    }
}
