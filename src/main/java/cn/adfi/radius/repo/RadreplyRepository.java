package cn.adfi.radius.repo;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cn.adfi.radius.model.Radreply;

public interface RadreplyRepository extends CrudRepository<Radreply, Long> {
	@Modifying(clearAutomatically = true)
	@Query("delete from Radreply a where a.username = :username") 
	public void deleteRadreplyByUsername(@Param("username")String account);
}
