package com.huseyinaydin.dao;

import java.util.List;

import javax.servlet.http.Part;

import com.huseyinaydin.model.Message;

public interface MessageDAO {
	public void sendMessage(Message message,Part uploadedFile,String fileName);
	public List<Message> mesajlariAl();
	public List<Message> mesajlariVeIcerikleriAl();
	public long mesajlarToplaminiAl();
	public void updateMessageAndCommitGorulmeDurumu(Message message);
	public void deleteMessage(Message message);
}
