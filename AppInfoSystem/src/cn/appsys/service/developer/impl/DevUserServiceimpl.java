package cn.appsys.service.developer.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.devUser.DevUserMapper;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.developer.DevUserService;
import cn.appsys.tools.IsexectisNull;

@Service("devuserService")
public class DevUserServiceimpl implements DevUserService {

	@Autowired
	private DevUserMapper devuserMapper;
	/**
	 * µÇÂ¼·½·¨
	 */
	@Override
	public DevUser getlogin(DevUser devuser) {
		DevUser user = devuserMapper.getlogin(devuser);
		return user;
	}

}
