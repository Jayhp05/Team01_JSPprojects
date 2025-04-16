package com.jspstudy.ch03.vo;

// 학생 한 명의 정보를 저장하는 VO(Value Object) 클래스
public class Student {
	
	private String name;
	private int age;
	private String gender;
	
	public Student() { }
	
	public Student(String name, int age, String gender) {
		this.name = name;
		this.age = age;
		this.gender = gender;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
}
