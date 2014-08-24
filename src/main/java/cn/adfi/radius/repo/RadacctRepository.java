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
	@Query("select a from Radacct a where a.username like %:username%") 
	public Page<Radacct> findByAccount(@Param("username")String account,Pageable pageable);
	
	@Query("select a from Radacct a where a.callingstationid like %:usermac%") 
	public Page<Radacct> findByUsermac(@Param("usermac")String usermac,Pageable pageable);
	
	@Query("select a from Radacct a where a.calledstationid like %:apmacssid%") 
	public Page<Radacct> findByApmacssid(@Param("apmacssid")String apmacssid,Pageable pageable);
	
	@Query("select a from Radacct a where a.framedipaddress like :userip%") 
	public Page<Radacct> findByUserip(@Param("userip")String userip,Pageable pageable);
	
	@Query("select a from Radacct a where a.nasipaddress like :nasip%") 
	public Page<Radacct> findByNasip(@Param("nasip")String nasip,Pageable pageable);
	
}
