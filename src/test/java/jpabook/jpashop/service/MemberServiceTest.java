package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    EntityManager em;

    @Test
    @Rollback(value = false)
    public void 회원가입() throws Exception {
        //given(조건: 조건이 주어졌을 때)
        Member member = new Member();
        member.setName("kim");
        //when(실행: 이렇게 하면)
        Long savedId = memberService.join(member);

        //then(결과: 결과가 이렇게 나온다.)
//        em.flush();
        assertEquals(member, memberRepository.findOne(savedId));
    }

    @Test
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("kim");

        Member member2 = new Member();
        member2.setName("kim");

        //when
        memberService.join(member1);
        /*try {
            memberService.join(member2); //예외가 발생해야 한다!!
        } catch (IllegalStateException e) {
            return;
        }*/
        assertThrows(IllegalStateException.class, () -> {
            memberService.join(member2);//예외가 발생해야 한다!!
        });

        //then
//        fail("예외가 발생해야 한다.");
    }
}