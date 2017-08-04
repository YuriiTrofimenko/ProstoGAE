package org.tyaa.prostogae.entity;

import javax.persistence.Id;

public class PageData {
	
	@Id
	private Long id;
	private String title;
	private String content;
	
	public PageData() { }
	
	public PageData(String _title, String _content) {
		
		title = _title;
		content = _content;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Long getId() {
		
		return id;
	}
}
