package cn.appsys.service.developer;

import java.util.List;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;

/**
 * App业务接口
 * @author DELL
 *
 */
public interface AppInfoService {
	//查询App状态
	public List<DataDictionary> getDataList(String typeCode);
	//三级分类查询
	public List<AppCategory> getclassfiy(String categoryLevel);
	//查询app信息
	public List<AppInfo> getappinfo(AppInfo info,Integer currentPageNo,Integer showPageCount);
	//查询出总记录数
    public int getcount(AppInfo info);
    //查询APK名称是否唯一
    public boolean getAppInfoByAPK(String APKName);
    //添加app信息
    public boolean addAppInfo(AppInfo info);
    //id查询要修改的app信息
    public AppInfo modifyAppInfo(Integer id);
    //修改app信息
    public boolean modifyAppInfosave(AppInfo info);
    //更新app信息的版本id
    public boolean updateAppInfo(Integer versionId,Integer id);
    //查看APP信息
    public AppInfo viewapp(Integer id);
    //删除APP信息
    public boolean deleteAppInfo(Integer id);
    //修改上/下架状态
    public boolean updateAppInfoBystatuc(Integer statucid,Integer id);
}
