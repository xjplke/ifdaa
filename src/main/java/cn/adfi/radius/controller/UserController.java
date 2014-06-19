package cn.adfi.radius.controller;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.adfi.radius.model.User;
import cn.adfi.radius.repo.UserRepository;
import cn.adfi.radius.util.exceptions.UserNotFoundException;


@EnableTransactionManagement
@RestController
@EnableAutoConfiguration
@RequestMapping("/user")
public class UserController {
	
	Sort sort = new Sort(new Order(Direction.DESC,"id"));
	
	@Autowired
	UserRepository userRepository;
	
	@RequestMapping(method=RequestMethod.POST)
	public User addUser(@RequestBody User user){
		user.setCreatedDate(new Date());
		user.setLastAccessed(new Date());
		return userRepository.save(user);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public User getUser(@PathVariable("id") Long id) throws Exception {
		User user = userRepository.findOne(id);
		if(null==user){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		return user;
	}
	
	public User updateUser(User user,User upto){
		user.setEmail(upto.getEmail());
		user.setExpire(upto.getExpire());
		user.setLastAccessed(new Date());
		user.setUsername(upto.getUsername());
		user.setWeibo(upto.getWeibo());
		user.setWeixin(upto.getWeixin());
		user.setQq(upto.getQq());
		return userRepository.save(user);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public User updateUser(@PathVariable("id") Long id,@RequestBody User user) throws Exception{
		User find = userRepository.findOne(id);
		if(null==find){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		return updateUser(find,user);
	}
	
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteUser(@PathVariable("id") Long id) throws Exception{
		userRepository.delete(id);
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public Page<User> getUsers(@RequestParam("page")int page,
					@RequestParam("size") int size){
		Pageable pageable = new PageRequest(page, size, Direction.DESC, "id");
		return userRepository.findAll(pageable);
	}
	
	@RequestMapping(value="/account/{account}",method=RequestMethod.GET)
	public Page<User> findUsersByAccount(@RequestParam("page")int page,
			@RequestParam("size") int size,@PathVariable("account")String account){
		Pageable pageable = new PageRequest(page, size, Direction.DESC, "id");
		return userRepository.findByAccount(account,pageable);
	}
	
	public void userEnable(User user) {
		user.setIsActive(true);
		userRepository.save(user);
	}
	
	@RequestMapping(value="/{id}/enable",method=RequestMethod.GET)
	public void userEnable(@PathVariable("id")Long id) throws Exception{
		User find = userRepository.findOne(id);
		if(null==find){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		
		userEnable(find);
		return;
	}
	
	public void userDisable(User user) {
		user.setIsActive(false);
		userRepository.save(user);
	}
	
	@RequestMapping(value="/{id}/disable",method=RequestMethod.GET)
	public void userDisable(@PathVariable("id")Long id) throws Exception{
		User find = userRepository.findOne(id);
		if(null==find){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		
		userDisable(find);
		return;
	}
	
	@RequestMapping(value="/{id}/passowrd",method=RequestMethod.POST)
	public void userChangePassword(@PathVariable("id")Long id,
									String oldpassword,
									String newpassword ) throws Exception 
	{
		User find = userRepository.findOne(id);
		if(null==find){
			throw new UserNotFoundException("id for "+id+" not found!");
		}
		//check oldpassword;
		//modify to new password;
	}
}
