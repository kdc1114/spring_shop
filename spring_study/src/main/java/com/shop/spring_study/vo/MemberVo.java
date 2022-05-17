package com.shop.spring_study.vo;

public class MemberVo {
	String id;
	String pw;
	byte state;
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
	public byte getState() {
		return state;
	}
	public void setState(byte state) {
		this.state = state;
	}
	@Override
	public String toString() {
		return "MemberVo [id=" + id + ", pw=" + pw + ", state=" + state + "]";
	}
}
