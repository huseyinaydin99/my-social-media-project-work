package com.huseyinaydin.service;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.CommitDAO;
import com.huseyinaydin.model.Commit;

@Service
@ManagedBean(name = "commitService")
@RequestScoped
public class CommitServiceImpl implements CommitService {

	@Autowired
	@Qualifier("commitDAO")
	private CommitDAO commitDAO;
	@Transactional
	@Override
	public void sendMessage(Commit message) {
		this.commitDAO.sendMessage(message);
	}
	public CommitDAO getCommitDAO() {
		return commitDAO;
	}
	public void setCommitDAO(CommitDAO commitDAO) {
		this.commitDAO = commitDAO;
	}
	
	@Transactional
	@Override
	public void deleteCommit(Commit commit) {
		this.commitDAO.deleteCommit(commit);
		FacesMessage message = new FacesMessage("Mesaj silindi!");
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

}
