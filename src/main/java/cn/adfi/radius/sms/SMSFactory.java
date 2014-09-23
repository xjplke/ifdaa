package cn.adfi.radius.sms;

import cn.adfi.radius.sms.impl.SMSwhjxt;
import cn.adfi.radius.utils.DBProperties;

public class SMSFactory {
	private static SMS sms = null;
	private static DBProperties dbProperties = null;
	
	public static void init(DBProperties dbProperties){
		SMSFactory.dbProperties = dbProperties;
	}
	
	public static SMS getSMS(){
		if(null!=sms){
			return sms;
		}
		
		String smstype = (String)dbProperties.get("sms.smstype");
		
		if(smstype.equals("whjxt")){
			sms =  new SMSwhjxt(dbProperties);
		}
	
		return sms;
	}
}
