package cn.adfi.radius.controller;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@EnableTransactionManagement
@RestController
@EnableAutoConfiguration
@RequestMapping("/rest/terminal")
@Transactional
public class TerminalController {

}
