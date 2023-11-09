package jpabasic.ex1hellojpa.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
public class Member extends BaseEntity{
    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(name = "username")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Team team;

    @Embedded
    private Address homeAddress;

    @ElementCollection // (@ElementCollection, @CollectionTable) 값 타입을 하나 이상 저장할 때 사용한다.
    // DB에는 컬렉션을 같은 테이블에 저장할 순 없다. 그래서 컬렉션을 저장하기 위해서 별도의 테이블이 필요함
    @CollectionTable(name = "favorite_food", joinColumns = @JoinColumn(name = "member_id"))
    @Column(name = "food_name")
    private Set<String> favoriteFoods = new HashSet<>();

    //    @ElementCollection
//    @CollectionTable(name = "address", joinColumns = @JoinColumn(name = "member_id"))
//    private List<Address> addressHistory = new ArrayList<>();
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "member_id")
    private List<AddressEntity> addressHistory = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<Order> orders = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }

    public Set<String> getFavoriteFoods() {
        return favoriteFoods;
    }

    public void setFavoriteFoods(Set<String> favoriteFoods) {
        this.favoriteFoods = favoriteFoods;
    }

    public List<AddressEntity> getAddressHistory() {
        return addressHistory;
    }

    public void setAddressHistory(List<AddressEntity> addressHistory) {
        this.addressHistory = addressHistory;
    }
}
