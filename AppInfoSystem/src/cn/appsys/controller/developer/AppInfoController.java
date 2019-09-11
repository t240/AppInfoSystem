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
import org.springframework.web.bind.annotation.PathVariable;
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
import cn.appsys.tools.IsexectisNull;
import cn.appsys.service.developer.AppInfoService;
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
		model.addAttribute("querySoftwareName", querySoftwareName);
		model.addAttribute("queryStatus", queryStatus);
		model.addAttribute("queryFlatformId", queryFlatformId);
		model.addAttribute("queryCategoryLevel1", queryCategoryLevel1);
		model.addAttribute("queryCategoryLevel2", queryCategoryLevel2);
		model.addAttribute("queryCategoryLevel3", queryCategoryLevel3);
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
				logoPicPath = logoLocPath.substring(logoLocPath.indexOf("AppInfoSystem") - 1).replace("\\", "/");
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
	    	model.addAttribute("appVersionList", appVersionService.getAppVersionListByid(-1,Integer.parseInt(id)));
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
	/**
	 * 查询出历史版本信息
	 * @param vid
	 * @param aid
	 * @return
	 */
	@RequestMapping(value="/appversionmodify")
	public String appversionmodify(String vid,String aid,Model model) {
		if(IsexectisNull.isBlank(vid) && IsexectisNull.isBlank(aid)) {
			List<AppVersion> verlist = appVersionService.getAppVersionListByid(-1,Integer.parseInt(aid));
			model.addAttribute("appVersionList", verlist);
			AppVersion version = appVersionService.getAppVersionListByid(Integer.parseInt(vid),-1).get(0);
			model.addAttribute("appVersion", version);
		}
		return "/developer/appversionmodify";
	}
	/**
	 * 修改版本信息保保存
	 * @param version
	 * @return
	 */
	@RequestMapping(value="/appversionmodifysave")
	public String appversionmodifysave(AppVersion version,HttpServletRequest request,HttpSession session,MultipartFile attach) {
		String apkLocPath = null;  //服务器存储路径
		String downloadLink = null;  //下载路径
		if(!attach.isEmpty()) {
			String path = request.getSession().getServletContext().getRealPath("statics" + File.separator + "uploadfiles");
			String oldFileName = attach.getOriginalFilename();
			String prefix = FilenameUtils.getExtension(oldFileName);
			int filesize = 5120000;
			if(attach.getSize() > filesize) {
				request.setAttribute("fileUploadError", "*上传文件不得超过500MB");
				return "redirect:/dev/flatform/appversionmodify";
			} else if(prefix.equalsIgnoreCase("apk")) {
				String fileName = System.currentTimeMillis() +  oldFileName + ".apk";
				File targetFile = new File(path,fileName);
				if(!targetFile.exists()) {
					targetFile.mkdirs();
				}
				try {
					attach.transferTo(targetFile);
				} catch (Exception e) {
				    e.printStackTrace();
					request.setAttribute("fileUploadError", "*上传失败");
					return "redirect:/dev/flatform/appversionmodify";
				}
				apkLocPath = path + File.separator + fileName;
				downloadLink = apkLocPath.substring(apkLocPath.indexOf("AppInfoSystem") - 1);
				version.setApkFileName(fileName);
			}
			version.setApkLocPath(apkLocPath);
			version.setDownloadLink(downloadLink);
		}
		version.setModifyBy(((DevUser)session.getAttribute("devUserSession")).getId());
		version.setModifyDate(new Date());
		if(appVersionService.modifyVersionsave(version)) {
			return "redirect:/dev/flatform/app/list";
		}
		return "redirect:/dev/flatform/appversionmodify";
	}
	/**
	 * 查看APP信息
	 * @return
	 */
	@RequestMapping(value="/appview/{id}")
	public String view(@PathVariable String id,Model model) {
		if(IsexectisNull.isBlank(id)) {
			model.addAttribute("appInfo", appInfoService.viewapp(Integer.parseInt(id)));
			model.addAttribute("appVersionList", appVersionService.getAppVersionListByid(-1, Integer.parseInt(id)));
		}
		return "/developer/appinfoview";
	}
	/**
	 * 删除APP信息
	 * @param id
	 * @return
	 */
	@RequestMapping("/delapp.json")
	@ResponseBody
	public Object delapp(String id) {
		HashMap<String, String> delmap = new HashMap<String,String>();
		if(IsexectisNull.isBlank(id)) {
			boolean flag = false;
			if(appInfoService.deleteAppInfo(Integer.parseInt(id))) {
				if(appVersionService.deleteAppVersion(Integer.parseInt(id))) {
					flag = true;
				}
			}
			if(flag) {
				delmap.put("delResult", "true");
			} else {
				delmap.put("delResult", "false");
			}
		} else {
			delmap.put("delResult", "notexist");
		}
		return delmap;
	}
	
	/**
	 * 修改APP信息上/下架
	 * @param appId
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/sale.json")
	@ResponseBody
	public Object sale(String appId,String statusid) {
		HashMap<String, String> updstatuc = new HashMap<String,String>();
		try {
			if(IsexectisNull.isBlank(appId) && IsexectisNull.isBlank(statusid)) {
				updstatuc.put("errorCode", "0");
				Integer statucsid = 0;
				if(statusid.equals("5")) {
					statucsid = 4;
				} else {
					statucsid = 5;
				}
				if(appInfoService.updateAppInfoBystatuc(statucsid, Integer.parseInt(appId))) {
					updstatuc.put("resultMsg", "success");
				} else {
					updstatuc.put("resultMsg", "failed");
				}
			} else {
				updstatuc.put("errorCode", "param000001");
			}
		} catch (Exception e) {
			e.printStackTrace();
			updstatuc.put("errorCode", "exception000001");
		}
		
		return updstatuc;
	}
	/**
	 * 删除logo图片
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/delfile.json")
	@ResponseBody
	public Object delfile(String id,String flag) {
		HashMap<String, String> delfilemap = new HashMap<String,String>();
		if(IsexectisNull.isBlank(id)) {
			File file = null;
			if(flag.equals("apk")) {
				AppVersion version = appVersionService.viewAppVersion(Integer.parseInt(id));
				file = new File(version.getApkLocPath());
			} else if(flag.equals("logo")) {
				AppInfo info = appInfoService.modifyAppInfo(Integer.parseInt(id));
				file = new File(info.getLogoLocPath());
			}
			if(file.exists()) {
				if(file.delete()) {
					delfilemap.put("result", "success");
				} else {
					delfilemap.put("result", "failed");
				}
			}
		}
		return delfilemap;
	}
	
}
