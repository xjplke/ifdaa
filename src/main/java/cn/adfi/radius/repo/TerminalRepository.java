package cn.adfi.radius.repo;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import cn.adfi.radius.model.Terminal;


public interface TerminalRepository extends PagingAndSortingRepository<Terminal, Long> {
	@Query("select a from Terminal a where a.type = :mac") 
	public List<Terminal> findTerminalByMac(@Param("mac")String mac);
}
