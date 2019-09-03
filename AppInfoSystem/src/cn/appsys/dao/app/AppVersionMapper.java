package cn.appsys.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

/**
 * APP版本接口
 * @author DELL
 *
 */
public interface AppVersionMapper {
	//id查询APP信息的历史版本
	public List<AppVersion> getAppVersionListByid(@Param("vid")Integer vid,@Param("aid")Integer aid);
	//新增版本
	public int addAppVersion(AppVersion version);
	//修改版本信息
	public int modifyVersionsave(AppVersion version);
    //删除历史版本信息
	public int deleteAppVersion(@Param("aid")Integer aid);
}
