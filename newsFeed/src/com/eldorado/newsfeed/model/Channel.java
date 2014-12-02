package com.eldorado.newsfeed.model;

/**
 * Class to determinate channel object
 * @author kamilabrito
 *
 */

public class Channel {
	
	private News item;
	private Long Id;
	private String name;

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public News getItem() {
		return item;
	}

	public void setItem(News item) {
		this.item = item;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
