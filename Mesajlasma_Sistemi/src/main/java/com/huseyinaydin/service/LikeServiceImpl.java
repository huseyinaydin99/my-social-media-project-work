package com.huseyinaydin.service;

import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.huseyinaydin.dao.LikeDAO;
import com.huseyinaydin.model.Likex;
import com.huseyinaydin.model.Share;

@Service
@ManagedBean(name = "likeService")
@RequestScoped
public class LikeServiceImpl implements LikeService {

	@Autowired
	@Qualifier("likeDAO")
	private LikeDAO likeDAO;
	
	@Transactional
	@Override
	public void saveLike(Share share) {
		this.likeDAO.saveLike(share);
	}

	public LikeDAO getLikeDAO() {
		return likeDAO;
	}

	public void setLikeDAO(LikeDAO likeDAO) {
		this.likeDAO = likeDAO;
	}

	@Transactional
	@Override
	public void gorulmeDurumuUpdateLike(Likex likex) {
		this.likeDAO.gorulmeDurumuUpdateLike(likex);
	}

	@Transactional
	@Override
	public List<Likex> getShareLikes(Share share) {
		return this.likeDAO.getShareLikes(share);
	}

}
