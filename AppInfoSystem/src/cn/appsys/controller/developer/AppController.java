package cn.appsys.controller.developer;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.pages;
import cn.appsys.service.developer.AppService;
import cn.appsys.tools.IsexectisNull;

/**
 * App信息控制器
 * @author DELL
 *
 */
@Controller
@RequestMapping("/dev/app")
public class AppController {

	private Logger logger = Logger.getLogger(AppController.class);
	@Autowired
	private AppService appservice;
	
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
		model.addAttribute("statusList", appservice.getDataList("APP_STATUS"));    //APP状态下拉列表
		model.addAttribute("flatFormList", appservice.getDataList("APP_FLATFORM"));  //所属平台下拉列表
		model.addAttribute("categoryLevel1List", appservice.getclassfiy(null));  //一级分类
		//分页类
		pages page = new pages();
		page.setShowPageCount(5);
		page.setTotalCount(appservice.getcount(info));
		//控制分页首尾
		if(pagecurrNo < 0) {
			pagecurrNo = 1;
		} else if(pagecurrNo > page.getTotalPageCount()) {
			pagecurrNo = page.getTotalPageCount();
		}
		page.setCurrentPageNo(pagecurrNo);
		model.addAttribute("pages", page);
		logger.debug(page.getCurrentPageNo()+"=============="+page.getShowPageCount());
		model.addAttribute("appInfoList", appservice.getappinfo(info,page.getCurrentPageNo(),page.getShowPageCount()));
		return "developer/appinfolist"; 
	}
	
	@RequestMapping(value="/categorylevellist.json",method=RequestMethod.GET)
	@ResponseBody
	public List<AppCategory> categorylist(@RequestParam String pid) {
		List<AppCategory> catelist = null;
		if(IsexectisNull.isBlank(pid)) {
			catelist = appservice.getclassfiy(pid);
		}
		return catelist;
	}
}
