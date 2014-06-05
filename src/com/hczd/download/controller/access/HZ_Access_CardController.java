package com.hczd.download.controller.access;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.httpclient.Cookie;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hczd.download.access.module.HZ_Access_Card;
import com.hczd.download.access.service.IHZ_Access_CardService;
import com.hczd.download.access.util.HZ_AccessUtil;
import com.hczd.download.base.module.HZ_PageData;
/**
 * 
 * @author linyb
 * @version 1.0 
 * @create_date 2014-5-28下午3:44:43
 */
@Controller
@RequestMapping(value = "/access/access_card/*")
public class HZ_Access_CardController {
	@Autowired
	private IHZ_Access_CardService hz_access_cardService;
	@Autowired
	private HZ_AccessUtil hz_accessUtil;
	/**
	 * @author linyb
	 * @create_date 2014-5-28下午3:43:02
	 * @return  通行卡列表页面
	 */
	@RequestMapping(value="query.htm")
	public String query(ModelMap model){
		return "/access/query";
	}
	@RequestMapping(value="index.htm")
	public String index(ModelMap model){
		return "access/index";
	}
	/**
	 * ajax获取列表数据
	 * @author linyb
	 * @create_date 2014-5-28下午3:47:13
	 * @param model
	 * @param pageData
	 * @return
	 */
	@RequestMapping(value = "ajax_list.htm",produces = "application/json")
	public @ResponseBody Map ajax_list(ModelMap model,HZ_PageData pageData,String card_no ,String vehicle_no){
		Map<String,Object> params = new HashMap<String, Object>();
		params.put("card_no_like", card_no);
		params.put("vehicle_no_like", vehicle_no);
		
		List<HZ_Access_Card> cards = hz_access_cardService.listPageByParams(params, pageData);
		
		Map<String,Object> msg = new HashMap<String, Object>();
		msg.put("rows", cards);
		msg.put("total", pageData.getTotalSize());
		return msg;
	}
	/**
	 * 启动下载页
	 * @author linyb
	 * @create_date 2014-5-28下午4:55:17
	 * @param model
	 * @param id
	 * @return 下载选择页面
	 */
	@RequestMapping(value="toDownload.htm")
	public String toDownload(ModelMap model,Integer id){
		if(id!=null){
			model.addAttribute("hz_access_card", hz_access_cardService.get(id));
		}
		return "/access/download";
	}
	/**
	 * 开始下载
	 * @author linyb
	 * @create_date 2014-5-28下午5:02:43
	 * @param startDate  开始时间
	 * @param endDate  结束时间
	 * @param cardNo   卡号
	 * @return
	 */
	@RequestMapping(value = "startDownload.htm" ,produces = "application/json")
	public @ResponseBody String ajax_startDownload(HttpServletRequest httpServletRequest,ModelMap model,String startDate,String endDate,String cardNo){
		String base_path =httpServletRequest.getServletContext().getRealPath("/");
		String msg = null;
		StringBuilder sb = hz_accessUtil.getConsole_info();
		try {
			//登录闽通卡
			Cookie[] cs =  hz_accessUtil.login();
			if(cs!= null){
				hz_accessUtil.print_msg(sb, "登录成功..");
				//登录成功,判断下载单张，还是全部
				if(StringUtils.isNotBlank(cardNo)){
					hz_accessUtil.print_msg(sb, "准备开始单张下载..");
					msg = hz_accessUtil.download(base_path,startDate, endDate, cardNo,cs,HZ_AccessUtil.DOWNLOAD_TYPE_SINGLE);
				}else{
					hz_accessUtil.print_msg(sb, "准备开始下载..");
					msg = hz_accessUtil.download(base_path ,startDate, endDate, null, cs,HZ_AccessUtil.DOWNLOAD_TYPE_All);
				}
			}else{
				hz_accessUtil.print_err_msg(sb, "登录失败！");
				msg = "login_error";
			}
		} catch (Exception e) {
			hz_accessUtil.print_err_msg(sb, "出现未知错误！");
			msg = "error";
		}
		return msg;
	}
	/**
	 * 实时控制台
	 * @author linyb
	 * @create_date 2014-6-5下午3:10:47
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "console_info.htm")
	public String console_info(ModelMap model){
		return "/access/console_info";
	}
	/**
	 * 
	 * @author linyb
	 * @create_date 2014-6-5下午3:12:28
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "ajax_msg_console.htm",produces = "application/json")
	public @ResponseBody String ajax_record_list(HttpServletRequest request,ModelMap model){
		StringBuilder sb = hz_accessUtil.getConsole_info();;
		return sb.toString();
	}
}
