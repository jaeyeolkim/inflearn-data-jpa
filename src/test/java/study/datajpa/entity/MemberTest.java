package study.datajpa.entity;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Rollback(value = false)
@Transactional
@SpringBootTest
class MemberTest {

    @PersistenceContext
    EntityManager em;

    @Test
    public void testEntity() throws Exception {
        // given
        Team teamA = new Team("teamA");
        Team teamB = new Team("teamB");
        em.persist(teamA);
        em.persist(teamB);

        em.persist(new Member("member1", 10, teamA));
        em.persist(new Member("member2", 20, teamA));
        em.persist(new Member("member3", 30, teamB));
        em.persist(new Member("member4", 40, teamB));

        // when
        em.flush();
        em.clear();

        // then
        List<Member> members = em.createQuery("select m from Member m join fetch m.team", Member.class).getResultList();
        System.out.println("=================");
        for (Member member : members) {
            System.out.println("member=" + member);
            System.out.println("team=" + member.getTeam());
        }
    }
}