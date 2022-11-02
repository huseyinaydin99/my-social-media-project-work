package com.huseyinaydin.service;

import com.huseyinaydin.model.Commit;

public interface CommitService {
	public void sendMessage(Commit message);
	public void deleteCommit(Commit commit);
}
