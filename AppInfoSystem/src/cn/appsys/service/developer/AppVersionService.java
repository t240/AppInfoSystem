package cn.appsys.service.developer;

import java.util.List;
import cn.appsys.pojo.AppVersion;

/**
 * APP�汾service�ӿ�
 * @author DELL
 *
 */
public interface AppVersionService {
	//id��ѯAPP��Ϣ����ʷ�汾
	public List<AppVersion> getAppVersionListByid(Integer vid,Integer aid);
	//�����汾
	public boolean addAppVersion(AppVersion version);
	//�޸İ汾��Ϣ
	public boolean modifyVersionsave(AppVersion version);
	//ɾ����ʷ�汾��Ϣ
	public boolean deleteAppVersion(Integer aid);

}
