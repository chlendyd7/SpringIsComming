package jpabook.jpashop.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class MemberRepository {

    @PersistenceContext
    private EntityManager em;

//    @PersistenceUnit 직접 주입 받을 수 도 있다
//    private EntityManagerFactory emf

    public void save(Member member) {
        em.persist(member);
    }

    public Member findOne(Long id) {
        return em.find(Member.class, id); //Member을 찾아서 반환해줌
    }

    //SQL은 테이블을 대상으로 쿼리 JPA는 Entity 객체를 통해서 쿼리
    public List<Member> findAll() { //전부 찾기 위해서는 쿼리를 써야함
        return em.createQuery("select m from Member m", Member.class)
                .getResultList();
    }

    public List<Member> findByName(String name) {
        return em.createQuery("select m from Member m where m.name = :name", Member.class) //:은 파라미터 바인딩//
                .setParameter("name", name)
                .getResultList();
    }
}
