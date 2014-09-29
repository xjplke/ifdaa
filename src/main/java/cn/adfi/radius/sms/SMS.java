package cn.adfi.radius.sms;

import org.springframework.web.client.RestTemplate;

public class SMS {
	private static String BaseURL = "http://127.0.0.1:8010/sms/send";
	private static RestTemplate restTemplate = new RestTemplate();
	
	public static SMSResoult sendmsg(String phone, String password) {
		String url = BaseURL +"?"+"phone="+phone+"&"+"password="+password;
		
		SMSResoult  rst = new SMSResoult();
		SMSResoult  rstx = restTemplate.getForObject(url, SMSResoult.class);//reply proxy object which can't serilize to json
		rst.setStatus(rstx.getStatus());
		rst.setMsg(rstx.getMsg());
		return rst;
	}
}
