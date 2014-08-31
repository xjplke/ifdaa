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
import cn.adfi.radius.model.Permission;
import cn.adfi.radius.model.Role;
import cn.adfi.radius.model.User;
import cn.adfi.radius.repo.PermissionRepository;
import cn.adfi.radius.repo.RoleRepository;
import cn.adfi.radius.repo.UserRepository;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class UserTest {
	@Autowired
	UserRepository userRepo;
	@Autowired
	RoleRepository roleRepo;
	@Autowired
	PermissionRepository permissionRepo;
	
	
	
	@Before
    public void setUp() {
		userRepo.deleteAll();
		roleRepo.deleteAll();
		permissionRepo.deleteAll();

		Permission permission = new Permission();
		permission.setAvailable(Boolean.TRUE);
		permission.setPermission("user:create");
		permission.setDescription("create user");
		permission = permissionRepo.save(permission);
		
		Set<Permission> pset = new HashSet<Permission>();
		pset.add(permission);
		
		Role role = new Role();
		role.setAvailable(Boolean.TRUE);
		role.setDescription("admin test");
		role.setRole("admin");
		role.setPermissions(pset);
		role = roleRepo.save(role);
		
		Set<Role> rset = new HashSet<Role>();
		rset.add(role);
		
		User user = new User();
		user.setUsername("123qwe");
		user.setActive(true);
		user.setCreatedDate(new Date());
		user.setUsername("123qweasdf");
		Calendar ca = Calendar.getInstance();
		ca.add(Calendar.DATE,30);
		user.setExpire(ca.getTime());
		user.setLastAccessed(new Date());
		user.setPassword("123qwe");
		user.setRoles(rset);
		
		userRepo.save(user);
		
	}
	
	@Test
	//@Transactional(readOnly=true)
	public void test(){
//		List<User> lst = userRepo.findByAccount("123qwe");
//		Assert.assertEquals("has one user",1, lst.size());
//		
//		User user = lst.get(0);
//		for(Role role:user.getRoles()){
//			role.getPermissions();
//		}
//		Assert.assertEquals("user has one role",1, user.getRolesStringSet().size());
//		Assert.assertEquals("user has one permission",1,user.getPermissionStringSet().size());
	}
	
	@After
	public void tearDown(){
		//userRepo.deleteAll();
		//roleRepo.deleteAll();
		//permissionRepo.deleteAll();
	}
	
}
