package cn.adfi.radius.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import cn.adfi.radius.config.PermissionDetect;
import cn.adfi.radius.config.PermissionDetect.Resource;


@RestController
@EnableAutoConfiguration
@RequestMapping("/rest/resoureactions")
public class PermissionDetectController {
	@Autowired
	PermissionDetect permissionDetect;
	
	
	@RequestMapping(method=RequestMethod.GET)
	List<Resource> getResources(){
		return permissionDetect.getResources();
	}
}
