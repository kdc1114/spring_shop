package com.shop.spring_study.vo;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "member")
public class MemberVo {
	@Id
	private String id;   
	private String pw;             
	private String phone;          
	private String email;          
	private String name;           
	private String address;
	@Column(name ="address_detail")
	private String addressDetail;
	
	@Column(name="signup_time",nullable = true,insertable = false,updatable=false)
	private	Date signupTime;
	
	// new SimpleDateFormat("yyyy-MM-dd").parse("1986-05-24")
	@DateTimeFormat(pattern = "yyyy-MM-dd")
	private Date birth;
	private byte grade;
	
	@OneToMany(fetch = FetchType.LAZY)
	@JoinColumn(name = "member_id",insertable = false,updatable = false)
	private List <ItemBasketVo> itemBasket; // OneToMany 라서 List로 받아야한다.
	
	// JPQL이아닌 네이티브로 해서 DB테이블 이름으로 item_basket
	@Formula(value = "(SELECT COUNT(b.count) FROM item_basket b WHERE b.member_id=id)")// SubQuery
	Integer basketCount;
	
	@Formula(value = "(SELECT SUM(b.count) FROM item_basket b WHERE b.member_id=id)")
	Integer basketCountSum;
	
	public Integer getBasketCountSum() {
		return basketCountSum;
	}
	public void setBasketCountSum(Integer basketCountSum) {
		this.basketCountSum = basketCountSum;
	}
	public Integer getBasketCount() {
		return basketCount;
	}
	public void setBasketCount(Integer basketCount) {
		this.basketCount = basketCount;
	}
	public List<ItemBasketVo> getItemBasket() {
		return itemBasket;
	}
	public void setItemBasket(List<ItemBasketVo> itemBasket) {
		this.itemBasket = itemBasket;
	}
	public String getAddressDetail() {
		return addressDetail;
	}
	public void setAddressDetail(String addressDetail) {
		this.addressDetail = addressDetail;
	}
	public Date getSignupTime() {
		return signupTime;
	}
	public void setSignupTime(Date signupTime) {
		this.signupTime = signupTime;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getPw() {
		return pw;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
	public Date getBirth() {
		return birth;
	}
	public void setBirth(Date birth) {
		this.birth = birth;
	}
	public byte getGrade() {
		return grade;
	}
	public void setGrade(byte grade) {
		this.grade = grade;
	}
	@Override
	public String toString() {
		return "MemberVo={\"id\": \"" + id + "\", \"pw\": \"" + pw
				+ "\", \"phone\": \"" + phone + "\", \"email\": \"" + email
				+ "\", \"name\": \"" + name + "\", \"address\": \"" + address
				+ "\", \"address_detail\": \"" + addressDetail
				+ "\", \"signup_time\": \"" + signupTime + "\", \"birth\": \""
				+ birth + "\", \"grade\": \"" + grade + "\"}";
	}          
	
}
