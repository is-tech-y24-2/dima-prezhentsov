package ru.itmo.kotikijava2.dao.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;
import java.util.*;

@Entity
@Table(name = "owners", schema = "public", catalog = "postgres")
public class OwnersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "owner_id")
    private int ownerId;
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "day_of_birth")
    private Date dayOfBirth;



    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CatsEntity> cats;

    @Getter
    @Setter
    @OneToOne(orphanRemoval = true)
    @JoinColumn(name = "user_entity_id")
    private UserEntity userEntity;

    public UserEntity getUserEntity() {
        return userEntity;
    }

    public void setUserEntity(UserEntity userEntity) {
        this.userEntity = userEntity;
    }

    public OwnersEntity(String name, Date dayOfBirth, UserEntity user) {
        this.name = name;
        this.dayOfBirth = dayOfBirth;
        this.userEntity = user;
        cats = new HashSet<>();
    }

    public OwnersEntity() {

    }

    public Set<CatsEntity> getCats(){
        return cats;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(Date dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }


    public void setCats(Set<CatsEntity> cats) {
        this.cats = cats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnersEntity that = (OwnersEntity) o;
        return ownerId == that.ownerId && Objects.equals(name, that.name) && Objects.equals(dayOfBirth, that.dayOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, name, dayOfBirth);
    }
}
