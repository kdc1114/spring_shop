package com.shop.spring_study.vo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "category")
public class CategoryVo {
	@Id
	@Column(name = "cate_num")
	private int cateNum;
	private String name;
	private Integer sub=null;
	
	public int getCateNum() {
		return cateNum;
	}
	public void setCateNum(int cateNum) {
		this.cateNum = cateNum;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getSub() {
		return sub;
	}
	public void setSub(Integer sub) {
		this.sub = sub;
	}
	@Override
	public String toString() {
		return "CategoryVo [cateNum=" + cateNum + ", name=" + name + ", sub=" + sub + "]";
	}
	
}
