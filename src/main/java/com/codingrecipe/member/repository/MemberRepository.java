package com.codingrecipe.member.repository;

import com.codingrecipe.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
// Mysql을 사용하기 위한 의존성 주입
@RequiredArgsConstructor
public class MemberRepository {
    // MyBatis을 도움을 받아 의존성을 주입받아야 하므로 SqlSessionTemplate 사용
    private final SqlSessionTemplate sql;

    public int save(MemberDTO memberDTO) {
        System.out.println("memberDTO = " + memberDTO);
        return sql.insert("Member.save",memberDTO);
    }

    // memberMapper.xml 쿼리를 읽어 MemberService에 MemberDTO객체로 반환함
    public MemberDTO login(MemberDTO memberDTO) {
        return sql.selectOne("Member.login", memberDTO);
    }

    public List<MemberDTO> findAll() {
        // 전달하는 매개 변수가 없으므로 그냥 명령만 실행한다.
        // 대소문자가 정확 해야 함.
        return sql.selectList("Member.findAll");
    }

    // 상세조회 : RequestParam이용 방법
    public MemberDTO findById(Long id) {
        return sql.selectOne("Member.findById", id);
    }

//    // 상세조회 : memberDTO 이용 방법
//    public MemberDTO findById(MemberDTO memberDTO) {
//        // @RequestParam으로 전달받은 자료를 가지고 memberMapper.xml에서
//        // resultType="member"의 형태로 쿼리를 해서 넘겨준다.
//        return sql.selectOne("Member.findById", memberDTO);
//    }

    // 삭제 처리
    public int delete(Long id) {
        // id를 넘겨서 처리 한다. int로 받아오는 값은 삭제된 행의 수를 나타낸다
        return sql.delete("Member.delete",id);
    }

    public MemberDTO findByEmail(String loginEmail) {
        // 넘겨받은 loginEmail을 이용하여 조회하고 MemberDTO로 넘겨 받는다.
        return sql.selectOne("Member.findByEmail", loginEmail);
    }


    public int update(MemberDTO memberDTO) {
        return sql.update("Member.update", memberDTO);
    }
}
