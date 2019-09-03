package cn.appsys.service.developer;

import java.util.List;
import cn.appsys.pojo.AppVersion;

/**
 * APP版本service接口
 * @author DELL
 *
 */
public interface AppVersionService {
	//id查询APP信息的历史版本
	public List<AppVersion> getAppVersionListByid(Integer vid,Integer aid);
	//新增版本
	public boolean addAppVersion(AppVersion version);
	//修改版本信息
	public boolean modifyVersionsave(AppVersion version);
	//删除历史版本信息
	public boolean deleteAppVersion(Integer aid);

}
