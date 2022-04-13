package kotiki.dao.dao;

import kotiki.dao.model.CatsEntity;
import org.hibernate.Session;

import java.util.List;

public class CatDao extends AbstractDao<CatsEntity>{

    public CatDao() {
        super(CatsEntity.class);
    }
}
