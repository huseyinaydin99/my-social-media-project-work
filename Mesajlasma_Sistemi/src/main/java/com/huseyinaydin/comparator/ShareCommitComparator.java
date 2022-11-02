package com.huseyinaydin.comparator;

import java.util.Comparator;

import com.huseyinaydin.model.Message;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareCommit;

public class ShareCommitComparator implements Comparator<ShareCommit> {

	@Override
	public int compare(ShareCommit a, ShareCommit b) {
		Long long1 = a.getShareCommit_Id();
		Long long2 = b.getShareCommit_Id();
		return long1.compareTo(long2);
	}

}
