package cn.adfi.radius.sms;

import cn.adfi.radius.model.User;

public class RegisterRsoult {
	private User user;
	private String status;
	private String msg;
	
	//private RegResoult smsResoult;
	public User getUser() {
		return user;
	}
	public void setUser(User user) {
		this.user = user;
	}
//	public RegResoult getSmsResoult() {
//		return smsResoult;
//	}
//	public void setSmsResoult(RegResoult smsResoult) {
//		this.smsResoult = smsResoult;
//	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
}
