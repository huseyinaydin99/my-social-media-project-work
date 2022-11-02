package com.huseyinaydin.dao;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.Part;

import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareImage;

public interface ShareDAO {
	public void saveShare(String shareText,Part uploadedFiles) throws ServletException, IOException;
	public List<Share> getMyShare();
	public List<Share> getOtherShare();
	public Share getShare(long shareId);
	public Share getShare();
	public List<Share> getShares();
	public List<Share> getProfileShare();
	public List<ShareImage> getShareImageList();
}
