package com.sw.model;

public class Comment {
	String Gname, Cname, Comment,ToEmail,FromEmail;
	int Seen;
	public Comment(String gname, String cname, String comment, String toEmail, String fromEmail, int seen) {
		super();
		Gname = gname;
		Cname = cname;
		Comment = comment;
		ToEmail = toEmail;
		FromEmail = fromEmail;
		Seen = seen;
	}
	public String getGname() {
		return Gname;
	}
	public void setGname(String gname) {
		Gname = gname;
	}
	public String getCname() {
		return Cname;
	}
	public void setCname(String cname) {
		Cname = cname;
	}
	public String getComment() {
		return Comment;
	}
	public void setComment(String comment) {
		Comment = comment;
	}
	public String getToEmail() {
		return ToEmail;
	}
	public void setToEmail(String toEmail) {
		ToEmail = toEmail;
	}
	public String getFromEmail() {
		return FromEmail;
	}
	public void setFromEmail(String fromEmail) {
		FromEmail = fromEmail;
	}
	public int getSeen() {
		return Seen;
	}
	public void setSeen(int seen) {
		Seen = seen;
	}
}