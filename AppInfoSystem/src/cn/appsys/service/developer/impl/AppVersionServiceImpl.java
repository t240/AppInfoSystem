package cn.appsys.service.developer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.app.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.developer.AppVersionService;
/**
 * APP版本service实现
 * @author DELL
 *
 */
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

	@Autowired
	private AppVersionMapper appVersionMapper;
	/**
	 * 查询APP历史版本信息
	 */
	@Override
	public List<AppVersion> getAppVersionListByid(Integer vid,Integer aid) {
		return appVersionMapper.getAppVersionListByid(vid,aid);
	}
	/**
	 * 新增版本信息
	 */
	@Override
	public boolean addAppVersion(AppVersion version) {
		boolean flag = false;
		if(appVersionMapper.addAppVersion(version) == 1) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 修改版本信息
	 */
	@Override
	public boolean modifyVersionsave(AppVersion version) {
		boolean flag = false;
		if(appVersionMapper.modifyVersionsave(version) == 1) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 删除版本信息
	 */
	@Override
	public boolean deleteAppVersion(Integer aid) {
		boolean flag = false;
		if(appVersionMapper.deleteAppVersion(aid) > 0) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 查询版本信息
	 */
	@Override
	public AppVersion viewAppVersion(Integer id) {
		return appVersionMapper.viewAppVersion(id);
	}

}
