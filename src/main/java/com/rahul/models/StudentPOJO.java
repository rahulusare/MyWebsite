package com.rahul.models;

public class StudentPOJO{
	int id;
	String name;
	int age;
	String email;
	
	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public StudentPOJO(int id, String name, int age, String gender) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.email = gender;
	}
	public StudentPOJO( String name, int age, String gender) {
		super();
		this.name = name;
		this.age = age;
		this.email = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String gender) {
		this.email = gender;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "StudentPOJO [id=" + id + ", name=" + name + ", age=" + age + ", email=" + email + "]";
	}
	
	
}


