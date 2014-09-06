package springtest;

import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import cn.adfi.radius.Application;
import cn.adfi.radius.model.Manager;
import cn.adfi.radius.model.Role;
import cn.adfi.radius.model.User;
import cn.adfi.radius.repo.ManagerRepository;
import cn.adfi.radius.repo.RoleRepository;
import cn.adfi.radius.repo.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
@Transactional
public class UserTest {
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	
	
	@Autowired
	ManagerRepository managerRepo;
	
	
	
	@Before
    public void setUp() {
		//userRepo.deleteAll();
		//roleRepo.deleteAll();
		//permissionRepo.deleteAll();

//		Permission permission = new Permission();
//		permission.setAvailable(Boolean.TRUE);
//		permission.setPermission("user:create");
//		permission.setDescription("create user");
//		permission = permissionRepo.save(permission);
		
		//Set<Permission> pset = new HashSet<Permission>();
		//pset.add(permission);
		
//		Role role = new Role();
//		role.setAvailable(Boolean.TRUE);
//		role.setDescription("admin test");
//		role.setRole("admin");
//		//role.setPermissions(pset);
//		role.addPermission("nas:edit");
//		role.addPermission("nas:view");
//		role = roleRepo.save(role);
//		
//		Set<Role> rset = new HashSet<Role>();
//		rset.add(role);
//		
//		User user = new User();
//		user.setUsername("123qwe");
//		user.setIsActive(true);
//		user.setCreatedDate(new Date());
//		user.setFullname("123qweasdf");
//		Calendar ca = Calendar.getInstance();
//		ca.add(Calendar.DATE,30);
//		user.setExpire(ca.getTime());
//		user.setLastAccessed(new Date());
//		user.setPassword("123qwe");
//		user.setRoles(rset);
//		
//		userRepo.save(user);
//		
//		
//		Manager manager = new Manager();
//		manager.setUsername("admin");
//		manager.setIsActive(true);
//		manager.setCreatedDate(new Date());
//		manager.setFullname("administrator");
//		user.setExpire(ca.getTime());
//		user.setLastAccessed(new Date());
//		user.setPassword("123qwe");
//		manager.setRoles(rset);
//		managerRepo.save(manager);
		
		
		
	}
	
	@Test
	
	public void test(){
//		List<User> lst = userRepo.findByUsername("123qwe");
//		Assert.assertEquals("has one user",1, lst.size());
//		
//		User user = lst.get(0);
//		Assert.assertEquals("user has one role",1, user.getRolesStringSet().size());
//		Assert.assertEquals("user has one permission",2,user.getPermissionStringSet().size());
//		
//		List<Manager> mlst = (List<Manager>) managerRepo.findAll();
//		Assert.assertEquals("has only one manager",1,mlst.size());
	}
	
	@After
	public void tearDown(){
		userRepo.deleteAll();
		roleRepo.deleteAll();
		
	}
	
}
