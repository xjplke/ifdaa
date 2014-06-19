/**
 * 
 */
package cn.adfi.radius.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.adfi.radius.model.Radacct;
import cn.adfi.radius.repo.RadacctRepository;

/**
 * @author shaojunwu  --sjw
 * @date 2014-6-2
 */
@EnableTransactionManagement
@RestController
@EnableAutoConfiguration
@RequestMapping("/record")
public class RadacctController {
	@Autowired
	RadacctRepository radacctRepository;
	
	@RequestMapping(method=RequestMethod.GET)
	public Page<Radacct> getRadaccts(int page,int size){
		return radacctRepository.findAll(new PageRequest(page, size, Direction.DESC, "id"));
	}
	
	@RequestMapping(value="/account/{account}",method=RequestMethod.GET)
	public Page<Radacct> getRadaccts(@RequestParam("page")int page,
			@RequestParam("size")int size,@PathVariable("account")String account){
		return radacctRepository.findByAccount(account,new PageRequest(page, size, Direction.DESC, "id"));
	}
}
