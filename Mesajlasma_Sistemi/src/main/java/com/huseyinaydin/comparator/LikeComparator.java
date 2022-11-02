package com.huseyinaydin.comparator;

import java.util.Comparator;

import com.huseyinaydin.model.Commit;
import com.huseyinaydin.model.Likex;
import com.huseyinaydin.model.Message;

public class LikeComparator implements Comparator<Likex> {

	@Override
	public int compare(Likex a, Likex b) {
		Long long1 = a.getLike_Id();
		Long long2 = b.getLike_Id();
		return long1.compareTo(long2);
	}

}
