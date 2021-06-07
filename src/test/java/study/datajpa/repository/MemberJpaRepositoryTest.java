package study.datajpa.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import study.datajpa.entity.Member;
import study.datajpa.entity.Team;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
@Rollback(value = false)
class MemberJpaRepositoryTest {

    @Autowired MemberJpaRepository memberJpaRepository;

    @Test
    public void testMember() throws Exception {
        // given
        Member member = new Member("memberA");

        // when
        Member saveMember = memberJpaRepository.save(member);
        Member findMember = memberJpaRepository.find(saveMember.getId());

        // then
        assertThat(findMember.getId()).isEqualTo(member.getId());
        assertThat(findMember.getUsername()).isEqualTo(member.getUsername());
        assertThat(findMember).isEqualTo(member);
    }

    @Test
    public void 기본_CRUD() throws Exception {
        // given
        Member member1 = new Member("member1");
        Member member2 = new Member("member2");

        // when
        memberJpaRepository.save(member1);
        memberJpaRepository.save(member2);
        Member findMember1 = memberJpaRepository.findById(member1.getId()).get();
        Member findMember2 = memberJpaRepository.findById(member2.getId()).get();

        // then
        assertThat(findMember1).isEqualTo(member1);
        assertThat(findMember2).isEqualTo(member2);
        assertThat(memberJpaRepository.count()).isEqualTo(2);

        memberJpaRepository.delete(member1);
        memberJpaRepository.delete(member2);
        assertThat(memberJpaRepository.count()).isEqualTo(0);
    }

    @Test
    public void 멤버의_팀_수정() throws Exception {
        // given
        Member member = new Member("member");
        Team team = new Team("첫부서");
        member.setTeam(team);

        // when
        memberJpaRepository.save(member);

        // then
        Team newTeam = new Team("새부서");
        member.changeTeam(newTeam);
        assertThat(member.getTeam().getName()).isEqualTo("새부서");
    }
}