package cn.appsys.service.developer;

import java.util.List;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.DataDictionary;

/**
 * Appҵ��ӿ�
 * @author DELL
 *
 */
public interface AppService {
	//��ѯApp״̬
	public List<DataDictionary> getDataList(String typeCode);
	//���������ѯ
	public List<AppCategory> getclassfiy(String categoryLevel);
	//��ѯapp��Ϣ
	public List<AppInfo> getappinfo(AppInfo info,Integer currentPageNo,Integer showPageCount);
	 //��ѯ���ܼ�¼��
    public int getcount(AppInfo info);
}
