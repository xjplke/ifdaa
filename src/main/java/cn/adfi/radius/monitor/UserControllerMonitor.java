/**
 * 
 */
package cn.adfi.radius.monitor;

import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

import cn.adfi.radius.model.User;
import cn.adfi.radius.controller.UserController;

/**
 * @author shaojunwu  --sjw
 * @date 2014-6-1
 */
@Aspect
@Component
public class UserControllerMonitor {

	//for aop test
	@AfterReturning(value="execution(* cn.adfi.radius.controller.UserController.getUser(Long))",returning = "returnValue")
	public void logControllerAccessa(JoinPoint joinPoint,Object returnValue) {
		Object[] paramValues = joinPoint.getArgs();
		Long id = (Long)paramValues[0];
		System.out.println("get User for id: " + id);
		User user = (User)returnValue;
		System.out.println("User for id  account: " + user.getAccount());
		//UserController controller = (UserController)joinPoint.getTarget();
	}
	
	// this is example  add two function to one joinPoint
	@AfterReturning("execution(* cn.adfi.radius.controller.UserController.getUser(Long))")
	public void logControllerAccessb(JoinPoint joinPoint) {
		System.out.println("Completed:bbbb " + joinPoint);
	}

	//question!!!!
	@AfterReturning(value="execution(* cn.adfi.radius.controller.UserController.enableUser(cn.adfi.radius.model.User))",
					returning = "returnValue")
	public void enableUserMonitor(JoinPoint joinPoint,Object returnValue){
		//TODO:add user to radcheck and group check;
		
	}
	
	@AfterReturning(value="execution(* cn.adfi.radius.controller.UserController.disableUser(cn.adfi.radius.model.User))",
					returning = "returnValue")
	public void disableUserMonitor(JoinPoint joinPoint,Object returnValue){
		//TODO:del user info from radcheck and group check
	}
	
	@Before(value="execution(* cn.adfi.radius.controller.UserController.deleteUser(..))")
	public void deleteUserMonitor(JoinPoint joinPoint) throws Exception {
		UserController controller = (UserController)joinPoint.getTarget();
		Object[] paramValues = joinPoint.getArgs();
		Long id = (Long)paramValues[0];
		controller.userDisable(id);
	}
}



