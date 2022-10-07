package ru.itmo.kotikiservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.itmo.kotikiservices.dao.model.CatsEntity;
import ru.itmo.kotikiservices.dao.model.OwnersEntity;
import ru.itmo.kotikiservices.dao.repository.OwnerRepository;
import ru.itmo.kotikiservices.wrapper.UtilJSON;
import ru.itmo.kotikiservices.wrapper.WrapperBuilder;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

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

    public Set<CatsEntity> getCats(OwnersEntity ownersEntity) {
        return ownersEntity.getCats();
    }

    public List<OwnersEntity> getAll() {
        return ownerRepository.findAll();
    }
    @KafkaListener(id = "addOwner", topics = {"owner.AddOwner"}, containerFactory = "singleFactory")
    public void addOwner(String message) throws ParseException {
        HashMap<String, String> props = new HashMap<>();
        props = UtilJSON.deserializeFromJson(message, props.getClass());
        add(props.get("name"), new Date(new SimpleDateFormat("dd.MM.yyyy").parse(props.get("birthDay")).getTime()));
    }

    @KafkaListener(id = "getOwnerById", topics = {"owner.GetOwnerById"}, containerFactory = "singleFactory")
    public void getById(String message) {
        HashMap<String, String> props = new HashMap<>();
        props = UtilJSON.deserializeFromJson(message, props.getClass());
        OwnersEntity owner = getById(Integer.parseInt(props.get("id")));
        System.out.println(owner);
    }
}
