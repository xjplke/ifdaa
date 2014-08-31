/**
 * 
 */
package cn.adfi.radius.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
@RequestMapping("/rest/record")
public class RadacctController {
	@Autowired
	RadacctRepository radacctRepository;
	
	@RequiresPermissions("radacct:view")
	@RequestMapping(method=RequestMethod.GET)
	public Page<Radacct> getRadaccts(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size){
		return radacctRepository.findAll(new PageRequest(page, size, Direction.DESC, "radacctid"));
	}
	
	@RequiresPermissions("radacct:view")
	@RequestMapping(value="/account/{account}",method=RequestMethod.GET)
	public Page<Radacct> getRadacctsByAccount(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size,
			@PathVariable("account")String account){
		return radacctRepository.findByAccount(account,new PageRequest(page, size, Direction.DESC, "radacctid"));
	}
	
	@RequiresPermissions("radacct:view")
	@RequestMapping(value="/usermac/{usermac}",method=RequestMethod.GET)
	public Page<Radacct> getRadacctsByUsermac(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size,
			@PathVariable("usermac")String usermac){
		return radacctRepository.findByUsermac(usermac,new PageRequest(page, size, Direction.DESC, "radacctid"));
	}
	// because spring @PathVariable has a bug when the string has ".";
	private String _todot(String ip){
		return ip.replace('_', '.');
	}
	
	@RequiresPermissions("radacct:view")
	@RequestMapping(value="/userip/{userip}",method=RequestMethod.GET)
	public Page<Radacct> getRadacctsByUserip(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size,
			@PathVariable("userip")String userip ){
		String tmp = _todot(userip);
  		return radacctRepository.findByUserip(_todot(userip),new PageRequest(page, size, Direction.DESC, "radacctid"));
	}
	
	@RequiresPermissions("radacct:view")
	@RequestMapping(value="/nasip/{nasip}",method=RequestMethod.GET)
	public Page<Radacct> getRadacctsBynasip(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size,
			@PathVariable("nasip")String nasip){
		return radacctRepository.findByNasip(_todot(nasip),new PageRequest(page, size, Direction.DESC, "radacctid"));
	}
	
	@RequiresPermissions("radacct:view")
	@RequestMapping(value="/apmacssid/{apmacssid}",method=RequestMethod.GET)
	public Page<Radacct> getRadacctsByapmacssid(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size,
			@PathVariable("apmacssid")String apmacssid){
		return radacctRepository.findByApmacssid(apmacssid,new PageRequest(page, size, Direction.DESC, "radacctid"));
	}
}
