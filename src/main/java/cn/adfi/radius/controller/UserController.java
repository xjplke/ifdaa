package cn.adfi.radius.controller;

import java.util.Date;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.adfi.radius.controller.helper.UserControllerHelper;
import cn.adfi.radius.model.User;
import cn.adfi.radius.repo.UserRepository;
import cn.adfi.radius.util.exceptions.UserNotFoundException;
import cn.adfi.radius.util.exceptions.UserPasswordErrorException;


@EnableTransactionManagement
@RestController
@EnableAutoConfiguration
@RequestMapping("/rest/user")
@Transactional
public class UserController {
	
	Sort sort = new Sort(new Order(Direction.DESC,"id"));
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	UserControllerHelper userControllerHelper;
	
	@RequiresPermissions("user:edit")
	@RequestMapping(method=RequestMethod.POST)
	public User addUser(@RequestBody User user){
		user.setCreatedDate(new Date());
		user.setLastAccessed(new Date());
		userEnable(user);
		return userRepository.save(user);
	}
	
	@RequiresPermissions("user:view")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public User getUser(@PathVariable("id") Long id) throws Exception {
		User user = userRepository.findOne(id);
		if(null==user){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		return user;
	}
	
	public User updateUser(User user,User upto){
		if(user.getIsActive()){
			userDisable(user);
		}
		
		user.setEmail(upto.getEmail());
		user.setStarttime(upto.getStarttime());
		user.setExpire(upto.getExpire());
		user.setLastAccessed(new Date());
		user.setUsername(upto.getUsername());
		user.setWeibo(upto.getWeibo());
		user.setWeixin(upto.getWeixin());
		user.setQq(upto.getQq());
		user.setPassword(upto.getPassword());
		//return userRepository.save(user);
		
		if( upto.getIsActive()){
			userEnable(user);
		}
		return userRepository.save(user);
	}
	
	@RequiresPermissions("user:edit")
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public User updateUser(@PathVariable("id") Long id,@RequestBody User user) throws Exception{
		if(user.getExpire()!=null && user.getStarttime()!=null && user.getExpire().before(user.getStarttime())){
			throw new Exception("Starttime should before expire time!");
		}
		
		User find = userRepository.findOne(id);
		if(null==find){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		
		return updateUser(find,user);
	}
	
	@RequiresPermissions("user:edit")
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteUser(@PathVariable("id") Long id) throws Exception{
		userRepository.delete(id);
	}
	
	@RequiresPermissions("user:view")
	@RequestMapping(method=RequestMethod.GET)
	public Page<User> getUsers(@RequestParam("page")int page,
					@RequestParam("size") int size){
		Pageable pageable = new PageRequest(page, size, Direction.DESC, "id");
		return userRepository.findAll(pageable);
	}
	
	@RequiresPermissions("user:view")
	@RequestMapping(value="/fullname/{fullname}",method=RequestMethod.GET)
	public Page<User> findUsersByFullname(@PathVariable("fullname")String fullname,
			@RequestParam("page")int page, @RequestParam("size") int size){
		Pageable pageable = new PageRequest(page, size, Direction.DESC, "id");
		return userRepository.findByFullname (fullname,pageable);
	}
	
	@RequiresPermissions("user:view")
	@RequestMapping(value="/username/{username}",method=RequestMethod.GET)
	public Page<User> findUsersByUsername(@PathVariable("username")String username,
			@RequestParam("page")int page, @RequestParam("size") int size){
		Pageable pageable = new PageRequest(page, size, Direction.DESC, "id");
		return userRepository.findByUsername(username,pageable);
	}
	
	//for aop
	public void userEnable(User user) {
		//user.setIsActive(true);
		//userRepository.save(user);
		userControllerHelper.userEnable(user);
	}
	
	@RequiresPermissions("user:edit")
	@RequestMapping(value="/{id}/active",method=RequestMethod.POST)
	public void userEnable(@PathVariable("id")Long id) throws Exception{
		User find = userRepository.findOne(id);
		if(null==find){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		
		userEnable(find);
		userRepository.save(find);
		return;
	}
	
	//for aop
	public void userDisable(User user) {
		//user.setIsActive(false);
		//userRepository.save(user);
		userControllerHelper.userDisable(user);
	}
	
	@RequiresPermissions("user:edit")
	@RequestMapping(value="/{id}/active",method=RequestMethod.DELETE)
	public void userDisable(@PathVariable("id")Long id) throws Exception{
		User find = userRepository.findOne(id);
		if(null==find){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		
		userDisable(find);
		userRepository.save(find);
		return;
	}
	
	//for aop
	public void userChangePassword(User user,String newpassword){
		user.setPassword(newpassword);
	}
	
	@RequiresPermissions("user:edit")
	@RequestMapping(value="/{id}/passowrd",method=RequestMethod.POST)
	public void userChangePassword(@PathVariable("id")Long id,
							@RequestParam("oldpassword") String oldpassword,
							@RequestParam("newpassword") String newpassword ) throws Exception 
	{
		User find = userRepository.findOne(id);
		if(null==find){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		//check oldpassword;
		if(!oldpassword.equals(find.getPassword())){
			throw new UserPasswordErrorException("Old Password error!");
		}
		//modify to new password;
		userChangePassword(find,newpassword);
		userRepository.save(find);
	}
}
