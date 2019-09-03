package cn.appsys.service.developer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.app.AppVersionMapper;
import cn.appsys.pojo.AppVersion;
import cn.appsys.service.developer.AppVersionService;
/**
 * APP�汾serviceʵ��
 * @author DELL
 *
 */
@Service("appVersionService")
public class AppVersionServiceImpl implements AppVersionService {

	@Autowired
	private AppVersionMapper appVersionMapper;
	/**
	 * ��ѯAPP��ʷ�汾��Ϣ
	 */
	@Override
	public List<AppVersion> getAppVersionListByid(Integer vid,Integer aid) {
		return appVersionMapper.getAppVersionListByid(vid,aid);
	}
	/**
	 * �����汾��Ϣ
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
	 * �޸İ汾��Ϣ
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
	 * ɾ���汾��Ϣ
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
	 * ��ѯ�汾��Ϣ
	 */
	@Override
	public AppVersion viewAppVersion(Integer id) {
		return appVersionMapper.viewAppVersion(id);
	}

}
