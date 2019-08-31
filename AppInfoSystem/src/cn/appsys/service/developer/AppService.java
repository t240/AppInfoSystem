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
public interface AppService {
	//查询App状态
	public List<DataDictionary> getDataList(String typeCode);
	//三级分类查询
	public List<AppCategory> getclassfiy(String categoryLevel);
	//查询app信息
	public List<AppInfo> getappinfo(AppInfo info,Integer currentPageNo,Integer showPageCount);
	 //查询出总记录数
    public int getcount(AppInfo info);
}
