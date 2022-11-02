package com.huseyinaydin.service;

import java.util.List;

import javax.servlet.http.Part;

import com.huseyinaydin.model.Message;

public interface MessageService {
	public void sendMessage(Message message,Part part,String fileNamex);
	public List<Message> mesajlariAl();
	public List<Message> mesajlariVeIcerikleriAl();
	public long mesajlarToplaminiAl();
	public void updateMessageAndCommitGorulmeDurumu(Message message);
	public void deleteMessage(Message message);
}
