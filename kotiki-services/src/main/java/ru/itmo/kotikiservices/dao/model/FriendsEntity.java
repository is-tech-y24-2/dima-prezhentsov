package ru.itmo.kotikiservices.dao.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "friends", schema = "public", catalog = "postgres")
public class FriendsEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    @Basic
    @Column(name = "first_cat")
    private int firstCat;
    @Basic
    @Column(name = "second_cat")
    private int secondCat;

    public FriendsEntity() {}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFirstCat() {
        return firstCat;
    }

    public void setFirstCat(int firstCat) {
        this.firstCat = firstCat;
    }

    public int getSecondCat() {
        return secondCat;
    }

    public void setSecondCat(int secondCat) {
        this.secondCat = secondCat;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FriendsEntity friends = (FriendsEntity) o;
        return id == friends.id && firstCat == friends.firstCat && secondCat == friends.secondCat;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstCat, secondCat);
    }
}
