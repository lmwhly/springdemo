package com.luoo.study.json.bo;

public class Book {
	private String id;
	private String name;

	public static String remark = "333";

	public String getRemark(int a,String b,boolean c){
		if(c)	return a+b;
		return "ok";
	}


	public Book() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}