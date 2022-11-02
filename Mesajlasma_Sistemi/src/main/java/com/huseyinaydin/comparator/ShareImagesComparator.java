package com.huseyinaydin.comparator;

import java.util.Comparator;

import com.huseyinaydin.model.ShareImage;

public class ShareImagesComparator implements Comparator<ShareImage> {

	@Override
	public int compare(ShareImage arg0, ShareImage arg1) {
		Long l1 = arg0.getShareImage_Id();
		Long l2 = arg1.getShareImage_Id();
		return l1.compareTo(l2);
	}

}
