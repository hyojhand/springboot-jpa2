package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class) // JUnit 실행시 스프링이랑 엮어서 실행
@SpringBootTest // 스프링 부트를 띄운상태로 , 컨테이너 안에서 테스트
@Transactional // 기본이 롤백
public class MemberServiceTest {

    @Autowired
    MemberService memberService;
    @Autowired
    MemberRepository memberRepository;

//    @Autowired EntityManager em;

    @Test
    public void 회원가입() throws Exception {
        //given
        Member member = new Member();
        member.setName("son");

        // when
        Long savedId = memberService.join(member);

        // then
//        em.flush(); // DB로 쿼리를 수행하기위해 롤백하기전 flush 시켜주면 insert쿼리 확인가능
        assertEquals(member,memberRepository.findOne(savedId));
    }

    @Test(expected = IllegalStateException.class) // IllegalStateException이 나와야한다
    public void 중복_회원_예외() throws Exception {
        //given
        Member member1 = new Member();
        member1.setName("son");

        Member member2 = new Member();
        member2.setName("son");

        // when
        memberService.join(member1);
        memberService.join(member2); // 예외발생해야한다

        // then
        fail("예외가 발생해야 한다."); // fail까지 오면안된다
    }
}