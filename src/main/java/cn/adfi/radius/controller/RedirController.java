package cn.adfi.radius.controller;

import java.io.IOException;
import java.util.List;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import cn.adfi.radius.model.RedirRule;
import cn.adfi.radius.model.Terminal;
import cn.adfi.radius.repo.RedirRuleRepository;
import cn.adfi.radius.repo.TerminalRepository;
import cn.adfi.radius.utils.MacAddress;
import eu.bitwalker.useragentutils.UserAgent;

@EnableTransactionManagement
@RestController
@EnableAutoConfiguration
@Transactional
@RequestMapping("/aaa")
public class RedirController {
	@Autowired
	TerminalRepository terminalRepo;//write terminal when do redirect
	
	@Autowired
	RedirRuleRepository redirRuleRepo;//get redirect rule!
	
	
	private boolean RuleKeyCmp(String pa,String rulekey){
		if( rulekey == null || rulekey.equals("")){
			return true;
		}
		if(pa!=null ){
			return pa.equals(rulekey);
		}
		return false;
	}
	@RequestMapping(value="/redirect",method=RequestMethod.GET)
	public void urlRedirect(
			@RequestParam(value="ssid",required = false, defaultValue="") String ssid,
			@RequestParam(value="nasid",required = false, defaultValue="") String nasid,
			HttpServletRequest request,HttpServletResponse response) throws Exception{
		//get terminal for last-update,if last-update is less than ten day, do not do user-agent.
		String url = null;
		UserAgent ua = new UserAgent(request.getHeader("user-agent"));
		String query = request.getQueryString();
		Iterable<RedirRule> lr = redirRuleRepo.findAll();
		for(RedirRule rule : lr){
			if(null == url){
				url = rule.getUrl();//if not found set to first!
			}
			if(RuleKeyCmp(ssid,rule.getSsid())
					&& RuleKeyCmp(nasid,rule.getNasid())
					&& RuleKeyCmp(ua.getOperatingSystem().getDeviceType().getName(),rule.getDevtype())
					&& RuleKeyCmp(ua.getOperatingSystem().getGroup().getName(),rule.getOs()))
			{
				url = rule.getUrl();
				break;
			}		
		}
		String redirurl = url;
		if (url.indexOf('?') < 0&&query!=null && !query.equals("")) {
			redirurl = url.concat("?"+query);
		}else if(url.indexOf('?')>=0 &&query!=null && !query.equals("")){
			redirurl = url.concat("&" + query);
		}
		response.sendRedirect(redirurl);
		/*{}
		MacAddress macaddr = new MacAddress(usermac);
		if( macaddr.isMac() ){
			List<Terminal> lt = terminalRepo.findTerminalByMac(macaddr.macstr('-'));
			Terminal terminal;
			Calendar ca = Calendar.getInstance();
			ca.add(Calendar.DATE,-30);//if last update is befor 30 days, do user-agent pause and save terminal
			if(lt.size() == 0){
				UserAgent ua = new UserAgent(request.getHeader("user-agent"));
				terminal = new Terminal(macaddr.macstr('-'), 
									ua.getOperatingSystem().getDeviceType().getName(), 
									ua.getOperatingSystem().getGroup().getName(), 
									ua.getOperatingSystem().getName(), 
									ua.getBrowser().getName());
				terminal.setCreateAt(new Date());
				terminal.setLastUpdate(new Date());
				terminalRepo.save(terminal);
				
			}else {
				terminal = lt.get(0);
				if(terminal.getLastUpdate().before(ca.getTime())){
					UserAgent ua = new UserAgent(request.getHeader("user-agent"));
					terminal.setOs(ua.getOperatingSystem().getGroup().getName());
					terminal.setOsver(ua.getOperatingSystem().getName());
					terminal.setBrowser(ua.getBrowser().getName());
					terminal.setLastUpdate(new Date());
					terminalRepo.save(terminal);
				}
			}
			
			
		}*/
		
		
		//if terminal not exist or terminal'lastupdate is more than ten day, do user-agent.
		
		//traverse rule until to get match redirect rule;
		
		//response.
	}
	
	
	//below methods are crud for redir rule
	@RequiresPermissions("redirrule:view")
	@RequestMapping(value="/redirrule",method=RequestMethod.GET)
	public Page<RedirRule> getRedirRules(@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10") int size) {
		Pageable pageable = new PageRequest(page, size, Direction.ASC, "id");
		return redirRuleRepo.findAll(pageable);
	}
	
	@RequiresPermissions("redirrule:edit")
	@RequestMapping(value="/redirrule",method=RequestMethod.POST)
	public RedirRule createRedirRule(@RequestBody RedirRule rule){
		return redirRuleRepo.save(rule);
	}
	
	@RequiresPermissions("redirrule:view")
	@RequestMapping(value="/redirrule/{id}",method=RequestMethod.GET)
	public RedirRule getRedirRule(@PathVariable("id")Long id){
		return redirRuleRepo.findOne(id);
	}
	
	@RequiresPermissions("redirrule:edit")
	@RequestMapping(value="/redirrule/{id}",method=RequestMethod.PUT)
	public RedirRule updateRedirRule(@PathVariable("id")Long id,@RequestBody RedirRule rule) throws Exception{
		RedirRule find = redirRuleRepo.findOne(id);
		if(find == null){
			throw new Exception("rule for id " + id +" not find!");
		}
		find.setDevtype(rule.getDevtype());
		find.setNasid(rule.getNasid());
		find.setOs(rule.getOs());
		find.setSsid(rule.getSsid());
		find.setUrl(rule.getUrl());
		return redirRuleRepo.save(find);
	}
	
	@RequiresPermissions("redirrule:edit")
	@RequestMapping(value="/redirrule/{id}",method=RequestMethod.DELETE)
	public void delRedirRuel(@PathVariable("id")Long id){
		redirRuleRepo.delete(id);
	}
	
	@RequiresPermissions("redirrule:view")
	@RequestMapping(value="/redirrule/ssid/{ssid}",method=RequestMethod.GET)
	public Page<RedirRule> findRedirRuleBySsid(@PathVariable("ssid")String ssid,
			@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size){
		return redirRuleRepo.findRuleBySsid(ssid, new PageRequest(page, size, Direction.DESC, "id"));
	}
	
	@RequiresPermissions("redirrule:view")
	@RequestMapping(value="/redirrule/nasid/{nasid}",method=RequestMethod.GET)
	public Page<RedirRule> findRedirRuleByNasid(@PathVariable("nasid")String nasid,
			@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size){
		return redirRuleRepo.findRuleByNasid(nasid, new PageRequest(page, size, Direction.DESC, "id"));
	}
	
	@RequiresPermissions("redirrule:view")
	@RequestMapping(value="/redirrule/devtype/{devtype}",method=RequestMethod.GET)
	public Page<RedirRule> findRedirRuleByDevtype(@PathVariable("devtype")String devtype,
			@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size){
		return redirRuleRepo.findRuleByDevtype(devtype, new PageRequest(page, size, Direction.DESC, "id"));
	}
	
	@RequiresPermissions("redirrule:view")
	@RequestMapping(value="/redirrule/os/{os}",method=RequestMethod.GET)
	public Page<RedirRule> findRedirRuleByOs(@PathVariable("os")String os,
			@RequestParam(value="page",required = false, defaultValue="0")int page,
			@RequestParam(value="size",required = false, defaultValue="10")int size){
		return redirRuleRepo.findRuleByOs(os, new PageRequest(page, size, Direction.DESC, "id"));
	}
}
