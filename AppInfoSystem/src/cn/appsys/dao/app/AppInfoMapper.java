package cn.appsys.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;

/*
 * App信息接口
 * @author DELL
 *
 */
public interface AppInfoMapper {
	//下拉列表查询
	public List<DataDictionary> getDataList(@Param("typeCode")String typeCode);
    //三级分类查询
	public List<AppCategory> getclassfiy(@Param("parentId")String categoryLevel);
	//查询app信息
    public List<AppInfo> getappinfo(@Param("softwareName")String softwareName,
    		                        @Param("status")Integer status,
    		                        @Param("flatformId")Integer flatformId,
    		                        @Param("categoryLevel1") Integer categoryLevel1,
    		                        @Param("categoryLevel2")Integer categoryLevel2,
    		                        @Param("categoryLevel3")Integer categoryLevel3,
    		                        @Param("currentPageNo")Integer currentPageNo,
    		                        @Param("showPageCount")Integer showPageCount);
    //查询出总记录数
    public int getcount(AppInfo info);
    //查询APK名称是否唯一
    public AppInfo getAppInfoByAPK(@Param("APKName")String APKName);
    //添加app信息
    public int addAppInfo(AppInfo info);
    //id查询要修改的app信息
    public AppInfo modifyAppInfo(Integer id);
    //修改app信息
    public int modifyAppInfosave(AppInfo info);
    //更新app信息的版本id
    public int updateAppInfo(@Param("versionId")Integer versionId,@Param("id")Integer id);
}
