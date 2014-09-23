package cn.adfi.radius.sms.impl;

import cn.adfi.radius.sms.SMS;
import cn.adfi.radius.utils.DBProperties;

import java.io.IOException;
import java.util.Properties;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;



public class SMSwhjxt implements SMS {
	// Õ¯÷∑°¢∂Àø⁄°¢–≠“È
	private String httpAddressUrl;

	// –≈œ¢∑¢ÀÕµÿ÷∑
	private String msgSendUrl;

	// ”‡∂Ó≤È—Øµÿ÷∑
	private String msgQueryUrl;

	// ”√ªß—È÷§
	private String msgCheckUrl;

	// ∂Ã–≈∂® ±∑¢ÀÕ
	private String msgScheSendUrl;

	// ∂Ã–≈Ω” ’µÿ÷∑
	private String msgRegUrl;
	// ∏¸∏ƒ√‹¬Îµÿ÷∑
	private String msgModifyUrl;
	private String username;
	private String password;

	public String getMsgModifyUrl() {
		return msgModifyUrl;
	}

	public void setMsgModifyUrl(String msgModifyUrl) {
		this.msgModifyUrl = msgModifyUrl;
	}

	public String getMsgCheckUrl() {
		return msgCheckUrl;
	}

	public void setMsgCheckUrl(String msgCheckUrl) {
		this.msgCheckUrl = msgCheckUrl;
	}

	public String getMsgScheSendUrl() {
		return msgScheSendUrl;
	}

	public void setMsgScheSendUrl(String msgScheUrl) {
		this.msgScheSendUrl = msgScheUrl;
	}

	public String getHttpAddressUrl() {
		return httpAddressUrl;
	}

	public void setHttpAddressUrl(String httpAddressUrl) {
		this.httpAddressUrl = httpAddressUrl;
	}

	public String getMsgQueryUrl() {
		return msgQueryUrl;
	}

	public void setMsgQueryUrl(String msgQueryUrl) {
		this.msgQueryUrl = msgQueryUrl;
	}

	public String getMsgSendUrl() {
		return msgSendUrl;
	}

	public void setMsgSendUrl(String msgSendUrl) {
		this.msgSendUrl = msgSendUrl;
	}
	
	
	
	public String getMsgRegUrl() {
		return msgRegUrl;
	}

	public void setMsgRegUrl(String msgRegUrl) {
		this.msgRegUrl = msgRegUrl;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public RestTemplate getRestTemplate() {
		return restTemplate;
	}

	public void setRestTemplate(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public SMSwhjxt(Properties properties){
		this.msgScheSendUrl = "http://csdk.zzwhxx.com:8002/submitsmsseq.aspx";//dbProperties.getProperty("sms.whjxt.msgScheSendUrl");
		this.username =  properties.getProperty("sms.whjxt.accountname");
		this.password =  properties.getProperty("sms.whjxt.accountpwd");
		
		//restTemplate = new RestTemplate();
		
	}

	private RestTemplate restTemplate = new RestTemplate();
	
	// ∑¢ÀÕ–≈œ¢
//	public PostMethod getSend(String userName, String password, String mobile,
//			String content) {
//		// System.out.println("sms send method");
//		StringBuilder url = new StringBuilder();
//		url.append("accountname=");
//		url.append(userName);
//		url.append("&accountpwd=");
//		url.append(password);
//		url.append("&mobilecodes=");
//		url.append(mobile);
//		url.append("&msgcontent=");
//		url.append(content);
//		// System.out.println(url);
//		PostMethod post = new PostMethod(msgSendUrl);
//		post.setRequestHeader("Content-type", "text/xml; charset=gbk");
//		post.setRequestBody(url.toString());
//		return post;
//	}
//
//	// ∂® ±∑¢ÀÕ–≈œ¢
//	public PostMethod getScheSend(String userName, String password,
//			String mobile, String content, String scheTime) {
//		StringBuilder url = new StringBuilder();
//		url.append("accountname=");
//		url.append(userName);
//		url.append("&accountpwd=");
//		url.append(password);
//		url.append("&mobilecodes=");
//		url.append(mobile);
//		url.append("&attime=");
//		url.append(scheTime);
//		url.append("&msgcontent=");
//		url.append(content);
//		// System.out.println(url);
//		PostMethod post = new PostMethod(msgScheSendUrl);
//		post.setRequestHeader("Content-type", "text/xml; charset=gbk");
//		post.setRequestBody(url.toString());
//		return post;
//	}
//
//	// ”‡∂Ó≤È—Ø
//	public PostMethod getQuery(String userName, String password) {
//		StringBuilder url = new StringBuilder();
//		url.append("accountname=");
//		url.append(userName);
//		url.append("&accountpwd=");
//		url.append(password);
//		// System.out.println(url.toString());
//		PostMethod post = new PostMethod(msgQueryUrl);
//		post.setRequestHeader("Content-type", "text/xml; charset=gbk");
//		post.setRequestBody(url.toString());
//		return post;
//	}
//
//	// ∏¸∏ƒ√‹¬Î
//	public PostMethod modifyPwd(String userName, String password,
//			String newpassword) {
//		StringBuilder url = new StringBuilder();
//		url.append("accountname=");
//		url.append(userName);
//		url.append("&accountpwd=");
//		url.append(password);
//		url.append("&accountnewpwd=");
//		url.append(newpassword);
//		// System.out.println(url.toString());
//		PostMethod post = new PostMethod(msgModifyUrl);
//		post.setRequestHeader("Content-type", "text/xml; charset=utf8");
//		post.setRequestBody(url.toString());
//		return post;
//	}
//
//	public String getMsgRegUrl() {
//		return msgRegUrl;
//	}
//
//	public void setMsgRegUrl(String msgRegUrl) {
//		this.msgRegUrl = msgRegUrl;
//	}
//
//	public String smsOperation(HttpMethod method) throws IOException {
//		HttpClient client = new HttpClient();
//		client.getHostConfiguration().setHost("csdk.zzwhxx.com", 8002, "http");
//		client.executeMethod(method);
//
//		System.out.println("∑˛ŒÒ∆˜∑µªÿµƒ◊¥Ã¨£∫" + method.getStatusLine());
//
//		String value = method.getResponseBodyAsString();
//		method.releaseConnection();
//		return value;
//	}
//
//	public static void main(String str[]) throws Exception {
//		SdkTest msgHttp = new SdkTest();
//		msgHttp.setMsgSendUrl("submitsms.aspx");
//		msgHttp.setMsgQueryUrl("getbalance.aspx");
//		msgHttp.setMsgScheSendUrl("submitschsms.aspx");
//		msgHttp.setMsgModifyUrl("changepwd.aspx");
//		// ≤‚ ‘∑¢ÀÕ∂Ã–≈
//		String value = msgHttp.smsOperation(msgHttp.getSend("’ ∫≈", "√‹¬Î",
//				"ƒø±Í ÷ª˙∫≈", "∑¢ÀÕ≤‚ ‘"));
//
//		System.out.println(value);
//		// ≤‚ ‘∂® ±∂Ã–≈
//		String timeVal = msgHttp.smsOperation(msgHttp.getScheSend("’ ∫≈", "√‹¬Î",
//				"ƒø±Í ÷ª˙∫≈", "≤‚3 ‘ƒ⁄»›eegggg£°", "2012-2-21 13:33:00"));
//		System.out.println(timeVal);
//		// ≤‚ ‘ªÒ»°”‡∂Ó
//		String moneyVal = msgHttp.smsOperation(msgHttp.getQuery("’ ∫≈", "√‹¬Î"));
//		System.out.println(moneyVal);
//		// ∏¸∏ƒ√‹¬Î
//		String modifyVal = msgHttp.smsOperation(msgHttp.modifyPwd("’ ∫≈", "‘≠√‹¬Î",
//				"–¬√‹¬Î"));
//		System.out.println(modifyVal);
//
//	}
	@Override
	public void sendmsg(String phone, String msg) {
		// TODO Auto-generated method stub
		MultiValueMap<String, String> map = new LinkedMultiValueMap<String, String>();
		map.add("accountname", username);
		map.add("accountpwd", password);
		map.add("mobilecodes",phone);
		map.add("msgcontent", msg);
		
//		HttpHeaders headers = new HttpHeaders();
//		headers.set("Content-type", "text/xml; charset=utf8");
//		
//		
//		HttpEntity<String> entity = new HttpEntity<String>(headers,map);
//		restTemplate.
		String sn = restTemplate.postForObject(msgScheSendUrl, map, String.class);
		System.out.println("sn:"+sn);
	}
	/*
	public static void main(String[] argv){
		Properties properties = new Properties();
		properties.put("sms.whjxt.accountname", "6SDK-EMY-6688-KDULR");
		properties.put("sms.whjxt.accountpwd", "027716");
		SMSwhjxt sms = new SMSwhjxt(properties);
		
		sms.sendmsg("13426347659", "test");
	}*/

}
