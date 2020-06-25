package com.teste.client;

import java.util.Date;

public class Person {
	private Long id;
	private String name;
	private Date birthDate;
	private int age;
	private String genre;
	private String address;

	public Person(Long id, String name, Date birthDate, int age, String genre, String address) {
		super();
		this.id = id;
		this.name = name;
		this.birthDate = birthDate;
		this.age = age;
		this.genre = genre;
		this.address = address;
	}
	
	public Person() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

}
