package ru.itmo.kotikijava2.service;

import org.springframework.web.bind.annotation.RequestBody;
import ru.itmo.kotikijava2.dao.model.CatsEntity;
import ru.itmo.kotikijava2.dao.model.Color;
import ru.itmo.kotikijava2.dao.model.FriendsEntity;
import ru.itmo.kotikijava2.dao.model.OwnersEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.itmo.kotikijava2.dao.repository.CatRepository;
import ru.itmo.kotikijava2.dao.repository.FriendsRepository;
import ru.itmo.kotikijava2.dao.repository.OwnerRepository;
import ru.itmo.kotikijava2.wrapper.CatsEntityWrapper;

import java.sql.Date;
import java.util.List;

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

    public CatsEntity getById(int id) {
        return catRepository.getById(id);
    }

    public CatsEntity getByColor(String color) {
        return catRepository.findByColor(color);
    }

    public List<CatsEntity> getAll() {
        return catRepository.findAll();
    }

    public List<CatsEntity> getCatFriend(CatsEntity cat) {
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


}
