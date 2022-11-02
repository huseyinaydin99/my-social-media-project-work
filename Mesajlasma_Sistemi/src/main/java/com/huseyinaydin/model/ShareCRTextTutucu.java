package com.huseyinaydin.model;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

@ManagedBean(name = "shareCRTextTutucu")
@RequestScoped
public class ShareCRTextTutucu {
	private String text;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
}
