package com.huseyinaydin.comparator;

import java.util.Comparator;

import com.huseyinaydin.model.Commit;
import com.huseyinaydin.model.Message;

public class CommitComparator implements Comparator<Commit> {

	@Override
	public int compare(Commit a, Commit b) {
		Long long1 = a.getCommit_Id();
		Long long2 = b.getCommit_Id();
		return long1.compareTo(long2);
	}

}
