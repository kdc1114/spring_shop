package com.shop.spring_study.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.shop.spring_study.vo.MemberVo;

// CrudRepository를 상속하는 인터페이스를 생성해야 JPA가 query를 작성할 수 있다.
public interface MemberRepository extends CrudRepository<MemberVo, String>{
	// 함수명이 질의로 작성가능 (질의를 하고싶으면 findBy 아니면findAllBy)
	// return type이 Collection 이면 복수의 select 
	public List<MemberVo> findAllByOrderByNameDesc();
	public List<MemberVo> findAllByOrderBySignupTimeDesc();
	
	public MemberVo findByIdAndPw(String id,String pw);
	public Optional<MemberVo> findByPhone(String phone);
	public Optional<MemberVo> findByEmail(String email);
	
	// JPQL은 vo Entity를 이용해서 쿼리를 작성한다.(DB마다 쿼리생성을 따로 한다.)
	@Query(value = "SELECT mem FROM MemberVo mem WHERE mem.email=:email")
	public Optional<MemberVo> selectJPQLByEmail(@Param(value = "email") String param_email);
	// nativeQuery 비권장(어쩔 수 없을 때 = JPQL로 할 수 없을 때,특정DB에서만 동작)
	@Query(value = "SELECT * FROM MEMBER WHERE EMAIL=?1",nativeQuery = true)
	public Optional<MemberVo> selectByEmail(String email);
}
