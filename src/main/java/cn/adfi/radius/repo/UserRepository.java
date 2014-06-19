package cn.adfi.radius.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.adfi.radius.model.User;

//more message find on http://spring.io/docs

public interface UserRepository extends PagingAndSortingRepository<User, Long> {
	 
	 @Query("select a from User a where a.account = :account") 
	 public Page<User> findByAccount(@Param("account")String account,Pageable pageable); 
}
