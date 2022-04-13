package kotiki.service;

import kotiki.dao.dao.CatDao;
import kotiki.dao.dao.FriendsDao;
import kotiki.dao.dao.OwnerDao;
import kotiki.dao.model.CatsEntity;
import kotiki.dao.model.FriendsEntity;
import kotiki.dao.model.OwnersEntity;

import java.util.List;

public class CatsService {
    private CatDao catDao;
    private FriendsDao friendsDao;
    private OwnerDao ownerDao;

    public CatsService() {
        catDao = new CatDao();
        friendsDao = new FriendsDao();
        ownerDao = new OwnerDao();
    }

    public void addCat(CatsEntity cat) {
        catDao.save(cat);
    }

    public void update(CatsEntity cat) {
        catDao.update(cat);
    }

    public void delete(CatsEntity cat) {
        catDao.delete(cat);
    }

    public CatsEntity getById(int id) {
        return catDao.findById(id);
    }

    public OwnersEntity getOwnerById(int id) {
        return ownerDao.findById(id);
    }

    public void addOwner(OwnersEntity owner) {
        ownerDao.save(owner);
    }

    public List<CatsEntity> getCatFriend(CatsEntity cat) {
        return cat.getFriends();
    }

    public void addFriendToCat(CatsEntity cat, CatsEntity friend) {
        if (cat.getFriends().stream().anyMatch(catsEntity -> catsEntity.getCatId() == friend.getCatId())) {
            return;
        }
        FriendsEntity friends1 = new FriendsEntity();
        friends1.setFirstCat(cat.getCatId());
        friends1.setSecondCat(friend.getCatId());
        cat.addFriend(friend);
        friend.addFriend(cat);
        friendsDao.save(friends1);
        FriendsEntity friends2 = new FriendsEntity();
        friends2.setFirstCat(friend.getCatId());
        friends2.setSecondCat(cat.getCatId());
        friendsDao.save(friends2);
    }


}
