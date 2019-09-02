package cn.appsys.controller.developer;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.IsexectisNull;

/**
 * �����߿�����
 * @author DELL
 *
 */
@Controller
@RequestMapping(value="/dev")
public class DevUserController {
	private Logger logger = Logger.getLogger(DevUserController.class);
	
	@Autowired
	private DevUserService devuserService;
	
	/**
	 * �򿪵�¼ҳ��
	 * @return
	 */
	@RequestMapping(value="/login")
	public String login() {
		return "devlogin";
	}
	
	/**
	 * ��¼����
	 * @param devuser
	 * @param session
	 * @return
	 */
	@RequestMapping("/dologin")
	public String dologin(DevUser devuser,HttpSession session) {
		DevUser user = devuserService.getlogin(devuser);
		if(IsexectisNull.isnull(user)) {
			session.setAttribute("devUserSession", user);
			session.setMaxInactiveInterval(60);
			return "developer/main";
		} 
		return  "redirect:/dev/login";
	}

	/**
	 * ע��
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/logout")
	public String logout(HttpSession session) {
		session.setAttribute("devUserSession", null);
		return "redirect:/dev/login";
	}
	
	
}
