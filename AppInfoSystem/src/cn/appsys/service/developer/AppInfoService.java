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
public interface AppInfoService {
	//��ѯApp״̬
	public List<DataDictionary> getDataList(String typeCode);
	//���������ѯ
	public List<AppCategory> getclassfiy(String categoryLevel);
	//��ѯapp��Ϣ
	public List<AppInfo> getappinfo(AppInfo info,Integer currentPageNo,Integer showPageCount);
	//��ѯ���ܼ�¼��
    public int getcount(AppInfo info);
    //��ѯAPK�����Ƿ�Ψһ
    public boolean getAppInfoByAPK(String APKName);
    //���app��Ϣ
    public boolean addAppInfo(AppInfo info);
    //id��ѯҪ�޸ĵ�app��Ϣ
    public AppInfo modifyAppInfo(Integer id);
    //�޸�app��Ϣ
    public boolean modifyAppInfosave(AppInfo info);
    //����app��Ϣ�İ汾id
    public boolean updateAppInfo(Integer versionId,Integer id);
    //�鿴APP��Ϣ
    public AppInfo viewapp(Integer id);
    //ɾ��APP��Ϣ
    public boolean deleteAppInfo(Integer id);
    //�޸���/�¼�״̬
    public boolean updateAppInfoBystatuc(Integer statucid,Integer id);
}
