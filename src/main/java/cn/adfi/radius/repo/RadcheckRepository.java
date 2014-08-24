/**
 * 
 */
package cn.adfi.radius.repo;


import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import cn.adfi.radius.model.Radcheck;

/**
 * @author shaojunwu  --sjw
 * @date 2014-6-1
 */
public interface RadcheckRepository extends CrudRepository<Radcheck, Long> {
	
	@Modifying(clearAutomatically = true)
	@Query("delete from Radcheck a where a.username = :username") 
	public void deleteRadcheckByUsername(@Param("username")String account);
	
}
