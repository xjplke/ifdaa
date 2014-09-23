package cn.adfi.radius.utils;

public class RadiusService {
	public static Object restart(){
		return ExecLinuxCMD.exec("service radiusd restart");
	}
	public static String state(){
		
		String xx = (String)ExecLinuxCMD.exec("ps -ef | grep radius | grep -v grep |wc -l");
		
		if(xx.equals("1")){
			return "running";
		}
		
		return "stop";
	}
}
