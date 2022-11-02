package com.huseyinaydin.service;

import java.util.List;

import com.huseyinaydin.model.Likex;
import com.huseyinaydin.model.Share;

public interface LikeService {
	public void saveLike(Share share);
	public void gorulmeDurumuUpdateLike(Likex likex);
	public List<Likex> getShareLikes(Share share);
}
