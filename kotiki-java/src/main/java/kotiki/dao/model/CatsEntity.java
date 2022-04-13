package kotiki.dao.model;

import javax.persistence.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "cats", schema = "public", catalog = "postgres")
public class CatsEntity {
    @Basic
    @Column(name = "name")
    private String name;
    @Basic
    @Column(name = "breed")
    private String breed;
    @Basic
    @Column(name = "date_of_birth")
    private Date dateOfBirth;
    @Basic
    @Column(name = "color")
    private String color;
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "cat_id")
    private int catId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id")
    private OwnersEntity owner;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "friends",
            joinColumns = @JoinColumn(name = "first_cat"),
            inverseJoinColumns = @JoinColumn(name = "second_cat")
    )
    private List<CatsEntity> friends;

    public CatsEntity(String name, Date dateOfBirth, String breed, String color, OwnersEntity owner) {
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.color = color;
        this.owner = owner;
        this.breed = breed;
        friends = new ArrayList<>();
    }

    public CatsEntity() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Date getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(Date dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getCatId() {
        return catId;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public OwnersEntity getOwner() {
        return owner;
    }

    public void setOwnerId(OwnersEntity owner) {
        this.owner = owner;
    }

    public void addFriend(CatsEntity friend) {
        friends.add(friend);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CatsEntity that = (CatsEntity) o;
        return catId == that.catId && owner == that.owner && Objects.equals(name, that.name) && Objects.equals(breed, that.breed) && Objects.equals(dateOfBirth, that.dateOfBirth) && Objects.equals(color, that.color);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, breed, dateOfBirth, color, catId, owner);
    }

    public List<CatsEntity> getFriends() {
        return friends;
    }
}
