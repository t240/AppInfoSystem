package cn.appsys.dao.devUser;

import cn.appsys.pojo.DevUser;
/**
 * 开发者接口
 * @author DELL
 *
 */
public interface DevUserMapper {
	//登录方法
	public DevUser getlogin(DevUser devuser);

}
