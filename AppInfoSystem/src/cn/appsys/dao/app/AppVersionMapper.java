package cn.appsys.dao.app;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import cn.appsys.pojo.AppVersion;

/**
 * APP�汾�ӿ�
 * @author DELL
 *
 */
public interface AppVersionMapper {
	//id��ѯAPP��Ϣ����ʷ�汾
	public List<AppVersion> getAppVersionListByid(@Param("vid")Integer vid,@Param("aid")Integer aid);
	//�����汾
	public int addAppVersion(AppVersion version);
	//�޸İ汾��Ϣ
	public int modifyVersionsave(AppVersion version);
    //ɾ����ʷ�汾��Ϣ
	public int deleteAppVersion(@Param("aid")Integer aid);
}
