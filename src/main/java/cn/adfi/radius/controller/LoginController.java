package cn.adfi.radius.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.subject.support.WebDelegatingSubject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.adfi.angularshiro.AngularShiroAuthc;
import cn.adfi.angularshiro.AngularShiroAuthz;
import cn.adfi.angularshiro.AngularShiroCredentials;
import cn.adfi.angularshiro.AngularShiroInfo;
import cn.adfi.angularshiro.AngularShiroLoginResponse;
import cn.adfi.angularshiro.AngularShiroPrincipal;
import cn.adfi.angularshiro.TokenWarpper;
import cn.adfi.radius.model.User;
import cn.adfi.radius.repo.UserRepository;



@RestController
@RequestMapping("/aaa")
public class LoginController {
	@Autowired
	UserRepository userRepo;
	
	
	private User loginInner(String username,String password){
		Subject subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(username,password));
		if(subject.isAuthenticated()){
			List<User> lst = userRepo.findByUsername(username);
			Session session = subject.getSession();
			session.setAttribute("user", lst.get(0));
			return lst.get(0);
		}
		return null;
	
	}
	@RequestMapping(value="login",method=RequestMethod.POST)
	public User login(@RequestBody User user){
		return loginInner(user.getUsername(),user.getPassword());
	}
	
	
	
	@RequestMapping(value="login",method=RequestMethod.GET)
	public User login(@RequestParam("username")String username,@RequestParam("password")String password){
		return loginInner(username,password);
	}

	@RequestMapping(value="me",method=RequestMethod.GET)
	public User getMe(){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession();
	
		return (User)session.getAttribute("user");
	}

	@RequestMapping(value="logout",method=RequestMethod.GET)
	public void logout(){
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}
	
	@RequestMapping(value="authenticate",method=RequestMethod.POST)
	public AngularShiroLoginResponse shiroLogin(@RequestBody TokenWarpper tokenWarpper) throws Exception{
		Subject subject = SecurityUtils.getSubject();
		subject.login(new UsernamePasswordToken(tokenWarpper.getToken().getPrincipal(),
						tokenWarpper.getToken().getCredentials()));
		
		User user;
		if(subject.isAuthenticated()){
			List<User> lst = userRepo.findByUsername(tokenWarpper.getToken().getPrincipal());
			Session session = subject.getSession();
			session.setAttribute("user", lst.get(0));
			user = lst.get(0);
		}else{
			throw new Exception("Username or Password error!");
		}
		
		
		
		
		AngularShiroAuthc authc = new AngularShiroAuthc();
		AngularShiroPrincipal principal = new AngularShiroPrincipal();
		principal.setLogin(user.getUsername());
		principal.setName(user.getFullname());
		principal.setEmail(user.getEmail());
		authc.setPrincipal( principal );
		
		AngularShiroCredentials credentials = new AngularShiroCredentials();
		credentials.setLogin(user.getUsername());
		credentials.setName(user.getFullname());
		credentials.setEmail(user.getEmail());
		authc.setCredentials(credentials);
		
		
		
		AngularShiroAuthz authz = new AngularShiroAuthz();
		authz.setRoles(user.getRolesStringSet());
		authz.setPermissions(user.getPermissionStringSet());
		
		AngularShiroInfo info = new AngularShiroInfo();
		info.setAuthc(authc);
		info.setAuthz(authz);
		AngularShiroLoginResponse resp = new AngularShiroLoginResponse();
		resp.setInfo(info);
		return resp;
	}
	
	
}
