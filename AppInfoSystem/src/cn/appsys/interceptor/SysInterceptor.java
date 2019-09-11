package cn.appsys.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import cn.appsys.pojo.BackendUser;
import cn.appsys.pojo.DevUser;

public class SysInterceptor extends HandlerInterceptorAdapter {
	
	private Logger logger = Logger.getLogger(SysInterceptor.class);
	
	public boolean preHandle(HttpServletRequest request,HttpServletResponse response,Object handler) throws Exception{
		logger.debug("SysInterceptor preHandle ==========================");
		HttpSession session = request.getSession();
		/*从session里面获取user对象  如果user对象为空  就跳转到401.jsp页面  并返回false*/
		DevUser user = (DevUser)session.getAttribute("devUserSession");
		BackendUser backendUser= (BackendUser) session.getAttribute("userSession");
		if(null == user||null==backendUser){
			System.out.println("进入");
			if(user!=null) {
				return true;
			}
			if(backendUser!=null) {
				return true;
			}
			response.sendRedirect(request.getContextPath()+"/dev/403");
			return false;
		}
		return true;
	}
}
