package com.codingrecipe.member.repository;

import com.codingrecipe.member.dto.MemberDTO;
import org.springframework.stereotype.Repository;

@Repository
public class MemberRepository {

    public int save(MemberDTO memberDTO) {
        System.out.println(memberDTO);
        return 0;
    }
}
