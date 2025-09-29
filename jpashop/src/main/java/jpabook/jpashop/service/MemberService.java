package jpabook.jpashop.service;

import jpabook.jpashop.domain.Member;
import jpabook.jpashop.repository.MemberRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly=true)
//@AllArgsConstructor
@RequiredArgsConstructor //파이널만 가지고 생성자 만들어줌
public class MemberService {

    // @Autowired
    private final MemberRepository memberRepository;

    //생성자가 하나만 있는 경우 자동으로 Autowired
//    @Autowired //생성자 injection이 좋다
//    public MemberService(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

//    @Autowired //Setter Injection은 객체가 생성된 이후에도 의존성 변경 가능, 객체 완전 초기화 보장x
//    public void setMemberRepository(MemberRepository memberRepository) {
//        this.memberRepository = memberRepository;
//    }

    //회원 가입
    @Transactional
    public Long join(Member member) {
        validateDuplicateMember(member);
        memberRepository.save(member);
        return member.getId();
    }

    //동시성 문제가 발생 할 수 있기에 DB에 설정 필요
    private void validateDuplicateMember(Member member) {
        List<Member> findMEmbers = memberRepository.findByName(member.getName());
        if (!findMEmbers.isEmpty()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }

    //회원 전체 조회
    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Member findOne(Long memberId) {
        return memberRepository.findOne(memberId);
    }

    @Transactional
    public void update(Long id, String name) {
        Member member = memberRepository.findOne(id);
        member.setName(name);
    }
}
