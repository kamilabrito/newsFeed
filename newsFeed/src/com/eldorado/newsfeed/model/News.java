package com.eldorado.newsfeed.model;

public class News {
	
	private String title;
	private String category;
	private String hour;
	
	public News(String title, String category, String hour) {
		this.title = title;
		this.category = category;
		this.hour = hour;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getHour() {
		return hour;
	}
	public void setHour(String hour) {
		this.hour = hour;
	}
	
}
