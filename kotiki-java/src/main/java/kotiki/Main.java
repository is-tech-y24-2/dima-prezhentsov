package kotiki;

import kotiki.dao.dao.CatDao;
import kotiki.dao.dao.OwnerDao;
import kotiki.dao.model.CatsEntity;
import kotiki.dao.model.OwnersEntity;
import kotiki.service.CatsService;

import java.sql.Date;

public class Main {
    public static void main(String[] args) {
        CatsService catsService = new CatsService();
        CatsEntity cat = catsService.getById(6);
        CatsEntity friend = catsService.getById(5);
//        System.out.println(cat.getName() + " " + friend.getName());
        catsService.addFriendToCat(cat, friend);
    }
}
