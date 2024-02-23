package com.codingrecipe.member.repository;

import com.codingrecipe.member.dto.MemberDTO;
import lombok.RequiredArgsConstructor;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Repository;

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
        // selectOne : 조회 결과가 1개 일때
        return sql.selectOne("Member.login",memberDTO);
    }
}
