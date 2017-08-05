package org.tyaa.prostogae.entity;

import javax.persistence.Id;

public class PageData {
	
	@Id
	private Long id;
	//�������� ������� �����, ��� "����"
	private String section;
	//��������� ������� ��� �����������
	private String title;
	//���������� �������� �������
	private String content;
	
	public PageData() { }
	
	public PageData(
			String _section
			, String _title
			, String _content
		) {
		
		section = _section;
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

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}
}
