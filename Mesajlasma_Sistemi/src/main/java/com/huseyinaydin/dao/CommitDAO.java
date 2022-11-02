package com.huseyinaydin.dao;

import com.huseyinaydin.model.Commit;

public interface CommitDAO {
	public void sendMessage(Commit message);
	public void deleteCommit(Commit commit);
}
