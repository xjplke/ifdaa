/**
 * 
 */
package cn.adfi.radius.controller;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.domain.Sort.Order;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
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
@RequestMapping("/rest/nas")
@Transactional
public class NasController {
	Sort sort = new Sort(new Order(Direction.DESC,"id"));
	
	@Autowired
	NasRepository nasRepository;
	
	@RequiresPermissions("nas:edit")
	@RequestMapping(method=RequestMethod.POST)
	public @ResponseBody Nas addNas(@RequestBody Nas nas){
		return nasRepository.save(nas);
	}
	
	@RequiresPermissions("nas:view")
	@RequestMapping(method=RequestMethod.GET)
	public @ResponseBody Page<Nas> getNas(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10") int size) {
		Pageable pageable = new PageRequest(page, size, Direction.DESC, "id");
		return nasRepository.findAll(pageable);
	}
	
	@RequiresPermissions("nas:view")
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	public @ResponseBody Nas getNas(@PathVariable("id") Long id) throws NasNotFoundException {
		Nas find = nasRepository.findOne(id);
		if(null == find){
			throw new NasNotFoundException("Nas not find for id:"+id);
		}
		return find;
	}
	
	@RequiresPermissions("nas:edit")
	@RequestMapping(value="/{id}",method=RequestMethod.PUT)
	public Nas updateNas(@PathVariable("id") Long id,@RequestBody Nas nas) throws NasNotFoundException {
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
	
	@RequiresPermissions("nas:edit")
	@RequestMapping(value="/{id}",method=RequestMethod.DELETE)
	public void deleteNas(@PathVariable("id") Long id) {
		nasRepository.delete(id);
	}
	
	@RequiresPermissions("nas:view")
	@RequestMapping(value="/server/{key}",method=RequestMethod.GET)
	public Page<Nas> findNasByServer(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10") int size,
			@PathVariable("key") String server){
		return nasRepository.findNasByServer(server, new PageRequest(page, size, Direction.DESC, "id"));
	}
	
	@RequiresPermissions("nas:view")
	@RequestMapping(value="/type/{key}",method=RequestMethod.GET)
	public Page<Nas> findNasByType(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10") int size,
			@PathVariable("key") String type){
		return nasRepository.findNasByType(type, new PageRequest(page, size, Direction.DESC, "id"));
	}
	
	@RequiresPermissions("nas:view")
	@RequestMapping(value="/nasname/{key}",method=RequestMethod.GET)
	public Page<Nas> findNasByNasname(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10") int size,
			@PathVariable("key") String nasname){
		return nasRepository.findNasByNasname(nasname, new PageRequest(page, size, Direction.DESC, "id"));
	}
	
	
}
