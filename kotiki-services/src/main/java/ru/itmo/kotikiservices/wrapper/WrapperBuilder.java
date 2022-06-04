package ru.itmo.kotikiservices.wrapper;

import ru.itmo.kotikiservices.dao.model.CatsEntity;
import ru.itmo.kotikiservices.dao.model.OwnersEntity;

public class WrapperBuilder {

    public static CatsEntityWrapper getCatEntityWrapper(CatsEntity cat) {
        return new CatsEntityWrapper(
                cat.getName(),
                cat.getBreed(),
                cat.getDateOfBirth(),
                cat.getColor(),
                cat.getCatId(),
                cat.getOwner().getOwnerId()
        );
    }

    public static OwnerEntityWrapper getOwnerWrapper(OwnersEntity owner) {
        return new OwnerEntityWrapper(
                owner.getOwnerId(),
                owner.getName(),
                owner.getDayOfBirth()
        );
    }
}
