package jpabasic.ex1hellojpa;

import jpabasic.ex1hellojpa.domain.Member;
import jpabasic.ex1hellojpa.domain.Team;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;


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
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);


            Member member1 = new Member();
            member1.setName("member1");
            member1.setTeam(team);
            em.persist(member1);





            em.flush();
            em.clear();

            Member m = em.find(Member.class, member1.getId());
            System.out.println("m = " + m.getTeam().getClass());

            System.out.println("==================");
            m.getTeam().getName(); //<-  fetch = FetchType.LAZY일 시 프록시 객체엔 필드의 값들이 없기 때문에 getName()같은 메소드로 값들을 이용하기 위해 동작할 때 초기화가 일어난다.
            System.out.println("==================");

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
