package cn.adfi.radius.controller.helper;

import org.springframework.stereotype.Component;

import cn.adfi.radius.model.User;

@Component
public class UserControllerHelper {
	public void userEnable(User user){
		user.setIsActive(true);
	}
	
	public void userDisable(User user){
		user.setIsActive(false);
	}
}
