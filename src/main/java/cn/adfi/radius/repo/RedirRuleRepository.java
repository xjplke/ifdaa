package cn.adfi.radius.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;


import cn.adfi.radius.model.RedirRule;

public interface RedirRuleRepository extends PagingAndSortingRepository<RedirRule, Long> {
	@Query("select a from RedirRule a where a.ssid like %:ssid%") 
	public Page<RedirRule> findRuleBySsid(@Param("ssid")String ssid,Pageable pageable);
	
	@Query("select a from RedirRule a where a.nasid like %:nasid%") 
	public Page<RedirRule> findRuleByNasid(@Param("nasid")String nasid,Pageable pageable);
	
	@Query("select a from RedirRule a where a.os like %:os%") 
	public Page<RedirRule> findRuleByOs(@Param("os")String os,Pageable pageable);
	
	@Query("select a from RedirRule a where a.devtype like %:devtype%") 
	public Page<RedirRule> findRuleByDevtype(@Param("devtype")String devtype,Pageable pageable);
}
