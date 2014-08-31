package cn.adfi.radius.security;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;

public class RestFormAuthenticationFilter extends FormAuthenticationFilter {
	@Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
		
		return true;
	}
}
