package cn.adfi.radius.sms;

import java.net.ConnectException;

import org.springframework.web.client.RestTemplate;

public class SMS {
	private static String BaseURL = "http://127.0.0.1:8010/sms/send";
	private static RestTemplate restTemplate = new RestTemplate();
	
	public static SMSResoult sendmsg(String phone, String password) {
		String url = BaseURL +"?"+"phone="+phone+"&"+"password="+password;
		
		SMSResoult  rst;
		try{
			rst = restTemplate.getForObject(url, SMSResoult.class);//reply proxy object which can't serilize to json
		}catch(Exception e){
			System.out.println(e);
			rst = new SMSResoult();
			rst.setStatus("failed");
			rst.setMsg("SMS adapter rest call failed!");
		}
		return rst;
	}
}
