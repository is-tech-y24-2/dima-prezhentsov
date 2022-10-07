package ru.itmo.kotikiservices.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import ru.itmo.kotikiservices.dao.model.CatsEntity;
import ru.itmo.kotikiservices.dao.model.Color;
import ru.itmo.kotikiservices.dao.model.FriendsEntity;
import ru.itmo.kotikiservices.dao.repository.CatRepository;
import ru.itmo.kotikiservices.dao.repository.FriendsRepository;
import ru.itmo.kotikiservices.dao.repository.OwnerRepository;
import ru.itmo.kotikiservices.wrapper.CatsEntityWrapper;
import ru.itmo.kotikiservices.wrapper.UtilJSON;
import ru.itmo.kotikiservices.wrapper.WrapperBuilder;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CatsService {

    @Autowired
    private CatRepository catRepository;
    @Autowired
    private FriendsRepository friendsRepository;
    @Autowired
    private OwnerRepository ownerRepository;


    public void add(String name,
                    Date dateOfBirth,
                    String breed,
                    Color color,
                    int ownerId
    ) {
        CatsEntity cat = new CatsEntity(
                name,
                dateOfBirth,
                breed,
                color,
                ownerRepository.getById(ownerId)
        );
        catRepository.save(cat);
    }

    public void update(CatsEntity cat) {
        catRepository.save(cat);
    }

    public void delete(int id) {
        CatsEntity cat = catRepository.getById(id);
        catRepository.delete(cat);
    }

    public CatsEntity getById(Integer id) {
        return catRepository.getById(id);
    }

    public CatsEntity getByColor(String color) {
        return catRepository.findByColor(color);
    }

    public List<CatsEntity> getAll() {
        return catRepository.findAll();
    }

    public Set<CatsEntity> getCatFriend(CatsEntity cat) {
        return cat.getFriends();
    }

    public void addFriendToCat(int first, int second) {
        CatsEntity cat = catRepository.getById(first);
        CatsEntity friend = catRepository.getById(second);

        if (cat == null || friend == null) {
            throw new NullPointerException("cat not found");
        }
        if (cat.getFriends().stream().anyMatch(catsEntity -> catsEntity.getCatId() == friend.getCatId())) {
            throw new RuntimeException("friends already made");
        }
        cat.addFriend(friend);
        friend.addFriend(cat);
        FriendsEntity friends1 = new FriendsEntity();
        friends1.setFirstCat(cat.getCatId());
        friends1.setSecondCat(friend.getCatId());
        friendsRepository.save(friends1);
//        FriendsEntity friends2 = new FriendsEntity();
//        friends2.setFirstCat(friend.getCatId());
//        friends2.setSecondCat(cat.getCatId());
//        friendsRepository.save(friends2);
    }

    @KafkaListener(id = "addCat", topics = {"cat.AddCat"}, containerFactory = "singleFactory")
    public void addCat(String message) throws ParseException {
        HashMap<String, String> props = new HashMap<>();
        props = UtilJSON.deserializeFromJson(message, props.getClass());
        add(
                props.get("name"),
                new Date(new SimpleDateFormat("dd.MM.yyyy").parse(props.get("dateOfBirth")).getTime()),
                props.get("breed"),
                Color.fromString(props.get("color")),
                Integer.parseInt(props.get("ownerId"))
        );
    }

    @KafkaListener(id = "makeFriends", topics = {"cat.MakeFriends"}, containerFactory = "singleFactory")
    public void makeFriends(String message) {
        HashMap<String, String> props = new HashMap<>();
        props = UtilJSON.deserializeFromJson(message, props.getClass());
        addFriendToCat(
                Integer.parseInt(props.get("first")),
                Integer.parseInt(props.get("second"))
        );
    }

    @KafkaListener(id = "getById", topics = {"cat.GetBy"}, containerFactory = "singleFactory")
    public void getCatById(String message) {
        HashMap<String, String> props = new HashMap<>();
        props = UtilJSON.deserializeFromJson(message, props.getClass());
        if (props.containsKey("id")) {
            CatsEntity cat = getById(Integer.parseInt(props.get("id")));
            System.out.println(cat);
            return;
        }
        if (props.containsKey("color")) {
            CatsEntity cat = getByColor(props.get("color"));
            System.out.println(cat);
            return;
        }
        List<CatsEntity> cats = getAll();
        System.out.println(cats);
    }

    @KafkaListener(id = "getCatFriends", topics = {"cat.getCatFriends"}, containerFactory = "singleFactory")
    public void getFriends(String message) {
        HashMap<String, String> props = new HashMap<>();
        props = UtilJSON.deserializeFromJson(message, props.getClass());
        Set<CatsEntity> cats = getCatFriend(getById(Integer.parseInt(props.get("id"))));
        System.out.println(UtilJSON.serializeToJson(cats));

    }

    @KafkaListener(id = "deleteCat", topics = {"cat.DeleteCat"}, containerFactory = "singleFactory")
    public void deleteCat(String message) {
        HashMap<String, String> props = new HashMap<>();
        props = UtilJSON.deserializeFromJson(message, props.getClass());
        delete(Integer.parseInt(props.get("id")));
    }

}
