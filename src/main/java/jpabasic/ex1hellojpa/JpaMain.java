package jpabasic.ex1hellojpa;

import jpabasic.ex1hellojpa.domain.Address;
import jpabasic.ex1hellojpa.domain.AddressEntity;
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

            member.getAddressHistory().add(new AddressEntity("old1", "street", "10000"));
            member.getAddressHistory().add(new AddressEntity("old2", "street", "10000"));
            em.persist(member);

            em.flush();
            em.clear();
            System.out.println("====================start==================");
            Member findMember = em.find(Member.class, member.getId());
            // homeCity -> newCity
            // findMember.getHomeAddress().setCity("newCity");  X!!!!
//            Address a = findMember.getHomeAddress();
//            findMember.setHomeAddress(new Address("newCity", a.getStreet(), a.getZipcode())); // member 엔티티 기준 객체 Address는 값 타입으로 쓰고 있다. 하지만 인스턴스이기에 새로운 인스턴스로 갈아 끼워야한다.

            // 치킨 -> 한식
//            findMember.getFavoriteFoods().remove("치킨");
//            findMember.getFavoriteFoods().add("한식");


//            findMember.getAddressHistory().remove(new Address("old1", "street", "10000"));// 값 타입 컬렉션에 변경 사항이 발생하면,
            //주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
            //값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본키를 구성해야함 : null 입력 X, 중복 저장 X
//            findMember.getAddressHistory().add(new Address("newCity1", "street", "10000"));
            System.out.println("--------------------------------------------------");
            findMember.getAddressHistory().remove(new AddressEntity("old1", "street", "10000"));
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
