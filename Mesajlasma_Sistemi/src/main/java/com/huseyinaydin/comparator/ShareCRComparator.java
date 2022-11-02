package com.huseyinaydin.comparator;

import java.util.Comparator;

import com.huseyinaydin.model.Message;
import com.huseyinaydin.model.Share;
import com.huseyinaydin.model.ShareCR;
import com.huseyinaydin.model.ShareCommit;

public class ShareCRComparator implements Comparator<ShareCR> {

	@Override
	public int compare(ShareCR a, ShareCR b) {
		Long long1 = a.getShareCR_Id();
		Long long2 = b.getShareCR_Id();
		return long1.compareTo(long2);
	}

}
