package cn.adfi.radius.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.adfi.radius.model.User;

//more message find on http://spring.io/docs

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	 
	 @Query("select a from User a where a.username like %:username%") 
	 public Page<User> findByUsername(@Param("username")String username,Pageable pageable); 
	 
	 @Query("select a from User a where a.fullname like %:fullname%") 
	 public Page<User> findByFullname(@Param("fullname")String fullname,Pageable pageable); 
	
	 
	 @Query("select a from User a where a.username = :username") 
	 public List<User> findByUsername(@Param("username")String username); 
	 
}
