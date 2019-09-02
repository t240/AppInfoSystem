package cn.appsys.controller.developer;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.math.RandomUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.pojo.pages;
import cn.appsys.service.developer.AppInfoService;
import cn.appsys.tools.IsexectisNull;
import cn.appsys.service.developer.AppVersionService;
/**
 * App信息控制器
 * @author DELL
 *
 */
@Controller
@RequestMapping("/dev/flatform/app")
public class AppInfoController {

	private Logger logger = Logger.getLogger(AppInfoController.class);
	@Autowired
	private AppInfoService appInfoService;
	@Autowired
	private AppVersionService appVersionService;
	
	/**
	 * 打开APP信息管理维护
	 * @param model
	 * @return
	 */
	@RequestMapping("/list")
	public String openapplist(Model model,
			                  @RequestParam(value="querySoftwareName",required=false)String querySoftwareName,
			                  @RequestParam(value="queryStatus",required=false)Integer queryStatus,
			                  @RequestParam(value="queryFlatformId",required=false)Integer queryFlatformId,
			                  @RequestParam(value="queryCategoryLevel1",required=false)Integer queryCategoryLevel1,
			                  @RequestParam(value="queryCategoryLevel2",required=false)Integer queryCategoryLevel2,
			                  @RequestParam(value="queryCategoryLevel3",required=false)Integer queryCategoryLevel3,
			                  @RequestParam(value="pageIndex",required=false)String pageIndex) {
		AppInfo info = new AppInfo();
		info.setSoftwareName(querySoftwareName);
		info.setStatus(queryStatus);
		info.setFlatformId(queryFlatformId);
		info.setCategoryLevel1(queryCategoryLevel1);
		info.setCategoryLevel2(queryCategoryLevel2);
		info.setCategoryLevel3(queryCategoryLevel3);
		Integer pagecurrNo = 1;
		if(IsexectisNull.isBlank(pageIndex)) {
			pagecurrNo = Integer.parseInt(pageIndex);
		}
		model.addAttribute("statusList", appInfoService.getDataList("APP_STATUS"));    //APP状态下拉列表
		model.addAttribute("flatFormList", appInfoService.getDataList("APP_FLATFORM"));  //所属平台下拉列表
		model.addAttribute("categoryLevel1List", appInfoService.getclassfiy(null));  //一级分类
		//分页类
		pages page = new pages();
		page.setShowPageCount(5);
		page.setTotalCount(appInfoService.getcount(info));
		//控制分页首尾
		if(pagecurrNo < 0) {
			pagecurrNo = 1;
		} else if(pagecurrNo > page.getTotalPageCount()) {
			pagecurrNo = page.getTotalPageCount();
		}
		page.setCurrentPageNo(pagecurrNo);
		model.addAttribute("pages", page);
		model.addAttribute("appInfoList", appInfoService.getappinfo(info,page.getCurrentPageNo(),page.getShowPageCount()));
		return "developer/appinfolist"; 
	}
	
	/**
	 * 查询三级分类
	 * @param pid
	 * @return
	 */
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> categorylist(@RequestParam String pid) {
		if("".equals(pid)) {
			pid = null;
		}
		return appInfoService.getclassfiy(pid);
	}
	/**
	 * 打开APP信息添加页面
	 * @return
	 */
	@RequestMapping(value="/appinfoadd")
	public String appinfoadd() {
		return "developer/appinfoadd";
	}
	/**
	 * 加载添加页面获取所属平列表
	 * @param tcode
	 * @return
	 */
	@RequestMapping(value="/datadictionarylist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<DataDictionary> loaddatadictionarylist(String tcode){
		return appInfoService.getDataList(tcode);
	}
	@RequestMapping(value="/apkexist.json")
	@ResponseBody
	public Object apkexist(String APKName) {
		HashMap<String, String> APKmap = new HashMap<String,String>();
		if(APKName == null) {
			APKmap.put("APKName", "empty");
		} else if(appInfoService.getAppInfoByAPK(APKName)){
			APKmap.put("APKName", "exist");
		} else {
			APKmap.put("APKName", "noexist");
		}
		return APKmap;
	}
	/**
	 * 添加APP信息
	 */
	@RequestMapping(value="/appinfoaddsave",method=RequestMethod.POST)
	public String addAppInfosave(AppInfo info,HttpServletRequest request,HttpSession session,MultipartFile a_logoPicPath) {
		String logoLocPath = null;
		String logoPicPath = null;
		//判断文件是否为空
		if(!a_logoPicPath.isEmpty()) {
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			String oldFileName = a_logoPicPath.getOriginalFilename();  //原文件名
			String prefix = FilenameUtils.getExtension(oldFileName); //后缀名
			int filesize = 500000;  //文件大小
			if(a_logoPicPath.getSize() > filesize) {
				request.setAttribute("fileUploadError", "*上传文件不得超过500KB");
			} else if(prefix.equalsIgnoreCase("jpg")
					 || prefix.equalsIgnoreCase("png")
					 || prefix.equalsIgnoreCase("jpeg")
					 || prefix.equalsIgnoreCase("jpeg")
					 || prefix.equalsIgnoreCase("pneg")) {  //上传文件格式不正确
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(1000000) + "_Personal.jpg";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//保存
				try {
					a_logoPicPath.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "*上传失败");
					return "appinfoadd";
				}
				logoLocPath = path + File.separator + fileName;
				logoPicPath = logoLocPath.substring(logoLocPath.indexOf("AppInfoSystem")-1).replace("\\", "/");
			} else {
				request.setAttribute("fileUploadError", "*上传图片格式不正确");
				return "appinfoadd";
			}
		}
		info.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		info.setCreationDate(new Date());
		info.setLogoLocPath(logoLocPath);
		info.setLogoPicPath(logoPicPath);
		if(appInfoService.addAppInfo(info)) {
			return "redirect:/dev/flatform/app/list";
		}
		return "appinfoadd";
	}
     /**
	 * 修改保存
	 * @return
	 */
	@RequestMapping(value="/appinfomodifysave")
	public String modifyAppInfosave(AppInfo info,HttpServletRequest request,HttpSession session,MultipartFile attach) {
		String logoLocPath = null;
		String logoPicPath = null;
		if(!attach.isEmpty()) {
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			String oldFileName = attach.getOriginalFilename();  //原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);  //后缀名
			int filesize = 500000;
			if(attach.getSize() > filesize) {
				request.setAttribute("fileUploadError", "*上传文件不得超过500KB");
			} else if(prefix.equalsIgnoreCase("jpg")
					 || prefix.equalsIgnoreCase("jpeg")
					 || prefix.equalsIgnoreCase("png")
					 || prefix.equalsIgnoreCase("pneg")) {  //文件格式
				String fileName = System.currentTimeMillis() + RandomUtils.nextInt(10000000) + "_Personal.jpg";  //组装文件名
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
				}
				//保存
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "*上传失败");
				} 
				logoLocPath = path + File.separator + fileName;  //文件本地路径
				logoPicPath = logoLocPath.substring(logoLocPath.indexOf("uploadfiles") - 1).replace("\\", "/");
			}
			info.setLogoLocPath(logoLocPath);
			info.setLogoPicPath(logoPicPath);
		}
		info.setModifyBy(((DevUser)session.getAttribute("devUserSession")).getId());
		info.setModifyDate(new Date());
		if(appInfoService.modifyAppInfosave(info)) {
			return "redirect:/dev/flatform/app/list";
		}
		return "redirect:/dev/flatform/app/appinfomodify";
	}
	/**
	 * id查询修改的app信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/appinfomodify")
	public String appinfomodify(String id,Model model) {
		if(IsexectisNull.isBlank(id)) {
		   model.addAttribute("appInfo", appInfoService.modifyAppInfo(Integer.parseInt(id)));
		}
		return "/developer/appinfomodify";
	}
	/**
	 * 打开添加APP版本信息页面
	 * @param id
	 * @param model
	 * @return
	 */
	@RequestMapping(value="/appversionadd")
	public String openappversionadd(String id,Model model) {
	    if(IsexectisNull.isBlank(id)) {
	    	model.addAttribute("appVersionList", appVersionService.getAppVersionListByid(Integer.parseInt(id)));
	    	model.addAttribute("appId", id);
	    }
	    return "/developer/appversionadd";
	}
	/**
	 * 添加APP版本信息
	 * @param version
	 * @param request
	 * @param session
	 * @param a_downloadLink
	 * @return
	 */
	@RequestMapping(value="/addversionsave")
	public String addversionsave(AppVersion version,HttpServletRequest request,HttpSession session,MultipartFile a_downloadLink) {
		String apkLocPath = null;
		String downloadLink = null;
		if(!a_downloadLink.isEmpty()) {
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			String oldFileName = a_downloadLink.getOriginalFilename(); //获得原文件名
			String prefix = FilenameUtils.getExtension(oldFileName);  //后缀名
			int filesize = 5120000;
			if(a_downloadLink.getSize() > filesize) {
				request.setAttribute("fileUploadError", "*上传文件不得超过500MB");
				return "redirect:/dev/flatform/appversionadd";
			} else if(prefix.equalsIgnoreCase("apk")) {
				String fileName = System.currentTimeMillis() + oldFileName + ".apk";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					a_downloadLink.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
					request.setAttribute("fileUploadError", "*上传失败");
				}
				apkLocPath = path + File.separator + fileName;
				downloadLink = apkLocPath.substring(apkLocPath.indexOf("AppInfoSystem") - 1);
			}
			version.setApkFileName(oldFileName);
		}
		version.setCreatedBy(((DevUser)session.getAttribute("devUserSession")).getId());
		version.setCreationDate(new Date());
		version.setApkLocPath(apkLocPath);
		version.setDownloadLink(downloadLink);
		if(appVersionService.addAppVersion(version)) {
			Integer versionId = version.getId();
			Integer id = version.getAppId();
			if(appInfoService.updateAppInfo(versionId, id)) {
				return "redirect:/dev/flatform/app/list";
			}
		}
		return "redirect:/dev/flatform/app/appversionadd";
	}
}
