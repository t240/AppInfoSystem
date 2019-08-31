package cn.appsys.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;

/**
 * App�ӿ�
 * @author DELL
 *
 */
public interface AppMapper {
	//�����б���ѯ
	public List<DataDictionary> getDataList(@Param("typeCode")String typeCode);
    //���������ѯ
	public List<AppCategory> getclassfiy(@Param("parentId")String categoryLevel);
	//��ѯapp��Ϣ
    public List<AppInfo> getappinfo(@Param("softwareName")String softwareName,
    		                        @Param("status")Integer status,
    		                        @Param("flatformId")Integer flatformId,
    		                        @Param("categoryLevel1") Integer categoryLevel1,
    		                        @Param("categoryLevel2")Integer categoryLevel2,
    		                        @Param("categoryLevel3")Integer categoryLevel3,
    		                        @Param("currentPageNo")Integer currentPageNo,
    		                        @Param("showPageCount")Integer showPageCount);
    //��ѯ���ܼ�¼��
    public int getcount(AppInfo info);
}