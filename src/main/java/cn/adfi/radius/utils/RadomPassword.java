package cn.adfi.radius.utils;

import java.util.Date;
import java.util.Random;

import org.springframework.stereotype.Component;

@Component
public class RadomPassword {
	Random rd1 = new Random((new Date()).getTime());
	public String radPass(){
		StringBuffer s = new StringBuffer();
		for(int i=0;i<6;i++){
			int x = rd1.nextInt();
			x = (x>0?x:-x)%10;
			char y = (char)(x+'0');
			s.append(y);
		}
		return s.toString();
	}
	
	
	public static void main(String[] argv){
		RadomPassword rp = new RadomPassword();
		for(int i=0;i<100;i++){
			System.out.println(rp.radPass());
		}
	}
}
