/**
 * 
 */
package cn.adfi.radius.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.adfi.radius.model.Radacct;

/**
 * @author shaojunwu  --sjw
 * @date 2014-6-1
 */
public interface RadacctRepository extends PagingAndSortingRepository<Radacct, Long> {
	@Query("select a from Radacct a where a.username = :username") 
	public Page<Radacct> findByAccount(@Param("username")String account,Pageable pageable);
}
