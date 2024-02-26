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
        // selectOne : 조회 결과가 1개 일때 ( Member.login 처리 후 memberDTO로 반환한다. )
        return sql.selectOne("Member.login",memberDTO);
    }


    public List<MemberDTO> findAll() {
        // 전달하는 매개 변수가 없으므로 그냥 명령만 실행한다.
        // 대소문자가 정확 해야 함.
        return sql.selectList("Member.findAll");
    }
}
