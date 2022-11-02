package com.huseyinaydin.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "shareCommitTextTutucu")
@RequestScoped
public class ShareCommitTextTutucu {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
