package com.shop.spring_study.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.shop.spring_study.vo.ItemVo;

public interface ItemRepository extends CrudRepository<ItemVo, Integer>{
	@Query(value = "SELECT item,mem,cate FROM ItemVo item LEFT JOIN MemberVo mem"
			+ " ON item.memberId=mem.id LEFT JOIN CategoryVo cate ON item.cateNum="
			+ "cate.cateNum")
	public Iterable<Object[]> findAllWithCategoryWithMember();
	// ItemVo.member <= 쿼리로 JOIN시 사용되지 않는다.
	// Iterable<Object[ItemVo,MemberVo,CategoryVo]>
	
	// sub query Join 이 아니라 진짜 Join 을 하고 싶다면 @EntityGraph 를 작성
	@EntityGraph(attributePaths = {"member","cate"})
	public Iterable<ItemVo> findAllByOrderByPostTime();
	
	// Page<ItemVo> Page를 쓰려면 Pageable 를 사용해야한다.
	public Page<ItemVo> findAll(Pageable pageable);
	//public Iterable<ItemVo> findAllByOrderByItemNum(Pageable pageable);
}