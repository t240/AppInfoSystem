package cn.appsys.service.developer.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.appsys.dao.app.AppInfoMapper;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.service.developer.AppInfoService;
import cn.appsys.tools.IsexectisNull;
/**
 * App业务实现类
 * @author DELL
 *
 */
@Service("appInfoService")
public class AppInfoServiceImpl implements AppInfoService {

	@Autowired
	private AppInfoMapper appInfoMapper;
	/**
	 * 查询所有App状态
	 */
	@Override
	public List<DataDictionary> getDataList(String typeCode) {
		List<DataDictionary> dataList = appInfoMapper.getDataList(typeCode);
		if(IsexectisNull.isBlank(typeCode)) {
			for (int i = 0; i < dataList.size(); i++) {
				DataDictionary data = dataList.get(i);
				if(data.getTypeCode().equals(typeCode)) {
					dataList.add(dataList.get(i));
					break;
				} 
			}
		}
		return dataList;
	}
	/**
	 * 查询三级分类
	 */
	@Override
	public List<AppCategory> getclassfiy(String categoryLevel) {
		return appInfoMapper.getclassfiy(categoryLevel);
	}
	/**
	 *查询app信息
	 */
	@Override
	public List<AppInfo> getappinfo(AppInfo info,Integer currentPageNo,Integer showPageCount) {
		List<AppInfo> infoList = null;
		try {
			//控制当前页
			Integer pageIndex = 0;
			if(currentPageNo > 1 && currentPageNo != 0) {
				pageIndex = (currentPageNo-1)*showPageCount;
			} else {
				pageIndex = currentPageNo -1;
			}
			infoList = appInfoMapper.getappinfo(info.getSoftwareName(),info.getStatus(),info.getFlatformId(),info.getCategoryLevel1()
					                        ,info.getCategoryLevel2(),info.getCategoryLevel3(),pageIndex,showPageCount);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return infoList;
	}

	/**
	 * 查询出app信息总记录数
	 */
	@Override
	public int getcount(AppInfo info) {
		return appInfoMapper.getcount(info);
	}
	/**
	 * 判断APK名称唯一
	 */
	@Override
	public boolean getAppInfoByAPK(String APKName) {
		boolean flag = false;
		if(appInfoMapper.getAppInfoByAPK(APKName) != null) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 添加APP信息
	 */
	@Override
	public boolean addAppInfo(AppInfo info) {
		boolean flag = false;
		if(appInfoMapper.addAppInfo(info) == 1) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 修改APP信息
	 */
	@Override
	public boolean modifyAppInfosave(AppInfo info) {
		boolean flag = false;
		if(appInfoMapper.modifyAppInfosave(info) == 1) {
			flag = true;
		}
		return flag;
	}
	/**
	 * id查询修改的app信息
	 */
	@Override
	public AppInfo modifyAppInfo(Integer id) {
		return appInfoMapper.modifyAppInfo(id);
	}

	/**
	 * 更新APP信息的版本id
	 */
	@Override
	public boolean updateAppInfo(Integer versionId, Integer id) {
		boolean flag = false;
		if(appInfoMapper.updateAppInfo(versionId, id) == 1) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 查看APP信息
	 */
	@Override
	public AppInfo viewapp(Integer id) {
		return appInfoMapper.viewapp(id);
	}
	/**
	 * 删除app信息
	 */
	@Override
	public boolean deleteAppInfo(Integer id) {
		boolean flag = false;
		if(appInfoMapper.deleteAppInfo(id) == 1) {
			flag = true;
		}
		return flag;
	}
	/**
	 * 修改APP信息状态
	 */
	@Override
	public boolean updateAppInfoBystatuc(Integer statucid, Integer id) {
		boolean flag = false;
		if(appInfoMapper.updateAppInfoBystatuc(statucid, id) == 1) {
			flag = true;
		}
		return flag;
	}


}
