package com.huseyinaydin.service;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

import org.primefaces.model.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.MessageDAO;
import com.huseyinaydin.model.Message;

@Service
@ManagedBean(name = "messageService")
@RequestScoped
public class MessageServiceImpl implements MessageService {

	@Autowired
	@Qualifier("messageDAO")
	private MessageDAO messageDAO;

	@Transactional
	@Override
	public void sendMessage(Message message, Part part, String fileNamex) {
		System.out.println("send messageye servise girdi");
		this.messageDAO.sendMessage(message,part,fileNamex);
	}

	public MessageDAO getMessageDAO() {
		return messageDAO;
	}

	public void setMessageDAO(MessageDAO messageDAO) {
		this.messageDAO = messageDAO;
	}

	@Transactional
	@Override
	public List<Message> mesajlariAl() {
		// TODO Auto-generated method stub
		return this.messageDAO.mesajlariAl();
	}

	@Transactional
	@Override
	public List<Message> mesajlariVeIcerikleriAl() {
		return this.messageDAO.mesajlariVeIcerikleriAl();
	}

	@Transactional
	@Override
	public long mesajlarToplaminiAl() {
		// TODO Auto-generated method stub
		return this.messageDAO.mesajlarToplaminiAl();
	}

	@Transactional
	@Override
	public void updateMessageAndCommitGorulmeDurumu(Message message) {
		this.messageDAO.updateMessageAndCommitGorulmeDurumu(message);
	}

	@Transactional
	@Override
	public void deleteMessage(Message message) {
		this.messageDAO.deleteMessage(message);
		FacesMessage messagef = new FacesMessage("Mesaj silindi!");
		FacesContext.getCurrentInstance().addMessage(null, messagef);
	}
}
