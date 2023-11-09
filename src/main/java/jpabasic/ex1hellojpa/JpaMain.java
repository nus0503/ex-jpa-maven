package jpabasic.ex1hellojpa;

import jpabasic.ex1hellojpa.domain.Address;
import jpabasic.ex1hellojpa.domain.Member;
import jpabasic.ex1hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.util.List;
import java.util.Set;


public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        //영속성 컨텍스트(persistence context)에는 1차 캐시와 2차 캐시가 있는데
        //1차 캐쉬는 데이터베이스 한 트랙잭션 안에서만 사용이 가능하다.
        //2차 캐쉬는 애플리케이션 전체에서 공유하는 캐시다.
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setName("member1");
            member.setHomeAddress(new Address("city1", "street", "10000"));

            member.getFavoriteFoods().add("치킨");
            member.getFavoriteFoods().add("족발");
            member.getFavoriteFoods().add("피자");

            member.getAddressHistory().add(new Address("old1", "street", "10000"));
            member.getAddressHistory().add(new Address("old2", "street", "10000"));
            em.persist(member);

            em.flush();
            em.clear();
            System.out.println("====================start==================");
            Member findMember = em.find(Member.class, member.getId()); // select하면 member 테이블에 있는 컬럼만 가져온다. 즉 favoriteFoods필드나 addressHistory필드 같은
            // 컬렉션들은 다 지연로딩이다.

            List<Address> addressHistory = findMember.getAddressHistory();
            addressHistory.forEach(e -> System.out.println(e.getCity()));

            Set<String> favoriteFoods = findMember.getFavoriteFoods();
            favoriteFoods.forEach(e -> System.out.println(e));
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }finally {
            em.close();
        }
        emf.close();
    }
}
