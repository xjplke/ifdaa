package cn.adfi.radius.utils;

import java.util.Scanner;

public class MacAddress {
	private Byte[] mac;
	
	public MacAddress(String macstr){
		if(macstr.length() != 17){
			return;
		}
		
		String delimiter = macstr.substring(2, 3);
		if(!delimiter.equals("-") && !delimiter.equals(":")){
			return;
		}
		
		
		Byte[] macx = new Byte[6];
		Scanner sc = new Scanner(macstr);
		sc.useDelimiter(delimiter);
		for(int i=0;i<6;i++){
			try{
				String s =sc.next();
				if(s.length()!=2){
					sc.close();
					return;
				}
				macx[i] = Byte.parseByte(s, 16);
			}catch(Exception e){
				sc.close();
				return;
			}
			
			
		}
		sc.close();
		
		this.mac = macx;
	}
	
	public MacAddress(Byte[] mac){
		if(mac.length!=6){
			return;
		}
		this.mac = mac;
	}
	
	public String macstr(char delimiter){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < mac.length; i++) {
			sb.append(String.format("%02X%c", mac[i], (i < mac.length - 1) ? delimiter : '\0'));		
		}
		
		return sb.toString();
	}
	
	public static boolean isMacAddress(String macstr){
		return new MacAddress(macstr).mac!=null;
	}
	
	public boolean isMac(){
		return this.mac != null;
	}
	public boolean equals(MacAddress mac){
		return this.mac!=null&&mac.mac!=null&mac.mac.equals(this.mac);
	} 
	/*
	public static void main(String []args){
		MacAddress m1 = new MacAddress("11:22:33:44:55:66");
		MacAddress m2 = new MacAddress(new MacAddress("22-33-44-55-66-77").mac);
		MacAddress m3 = new MacAddress(m1.mac);
		System.out.println("m1 = "+m1.macstr('-'));
		System.out.println("m2 = "+m2.macstr(':'));
		System.out.println("m3 = "+m3.macstr(':'));
		System.out.println("m3.equals(m1) is "+ m3.equals(m1));
		System.out.println("m3.equals(m2) is "+ m3.equals(m2));
	}*/
}
