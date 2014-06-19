/**
 * 
 */
package cn.adfi.radius.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import cn.adfi.radius.model.Nas;
import cn.adfi.radius.repo.NasRepository;
import cn.adfi.radius.util.exceptions.NasNotFoundException;

/**
 * @author shaojunwu  --sjw
 * @date 2014-6-2
 */
@EnableTransactionManagement
@RestController
@EnableAutoConfiguration
@RequestMapping("/nas")
public class NasController {
	Sort sort = new Sort(new Order(Direction.DESC,"id"));
	
	@Autowired
	NasRepository nasRepository;
	
	public Nas addNas(Nas nas){
		return nasRepository.save(nas);
	}
	
	public Nas getNas(Long id) throws NasNotFoundException {
		Nas find = nasRepository.findOne(id);
		if(null == find){
			throw new NasNotFoundException("Nas not find for id:"+id);
		}
		return find;
	}
	
	public Nas updateNas(Long id,Nas nas) throws NasNotFoundException {
		Nas find = nasRepository.findOne(id);
		if (null == find) {
			throw new NasNotFoundException("Nas not find for id:"+id);
		}
		find.setNasname(nas.getNasname());
		find.setDescription(nas.getDescription());
		find.setCommunity(nas.getCommunity());
		find.setPorts(nas.getPorts());
		find.setSecret(nas.getSecret());
		find.setServer(nas.getServer());
		find.setShortname(nas.getShortname());
		find.setType(nas.getType());
		
		return nasRepository.save(nas);
	}
	
	public void deleteNas(Long id) {
		nasRepository.delete(id);
	}
	
	public Page<Nas> findNasByIp(int page,int size,String ip){
		return nasRepository.findNasByIp(ip, new PageRequest(page, size, Direction.DESC, "id"));
	}
	
	
}
