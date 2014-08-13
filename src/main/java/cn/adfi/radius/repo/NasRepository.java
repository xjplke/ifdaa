/**
 * 
 */
package cn.adfi.radius.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.adfi.radius.model.Nas;

/**
 * @author shaojunwu  --sjw
 * @date 2014-6-1
 */
public interface NasRepository extends PagingAndSortingRepository<Nas, Long> {
	@Query("select a from Nas a where a.server like :ip%") 
	public Page<Nas> findNasByServer(@Param("ip")String ip,Pageable pageable);
	
	@Query("select a from Nas a where a.nasname like %:nasname%") 
	public Page<Nas> findNasByNasname(@Param("nasname")String nasname,Pageable pageable);
	
	@Query("select a from Nas a where a.type like %:type%") 
	public Page<Nas> findNasByType(@Param("type")String type,Pageable pageable);
}
