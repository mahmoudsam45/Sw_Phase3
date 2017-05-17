package com.sw.controller;

import com.sw.model.Account;

public final class User {
	public static Account account = new Account();

	private User() {
	}

	public static Account getAccount() {
		return account;
	}

	public static void setAccount(Account account) {
		User.account = account;
	}

	public static Account GetAccount() {
		return account;
	}
}
