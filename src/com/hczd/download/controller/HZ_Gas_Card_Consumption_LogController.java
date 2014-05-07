package com.hczd.download.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hczd.download.base.module.HZ_PageData;
import com.hczd.download.card.module.HZ_Main_Gas_Card;
import com.hczd.download.card.service.IHZ_Main_Gas_CardService;
import com.hczd.download.card.util.HZ_Sinopec_Util;

/**
 * 加油卡消费数据控制类
 * @author linjian 
 * @version 2.0
 * @create_date 2013-6-16 上午9:09:37
 */
@Controller
@RequestMapping(value = "/card/gas_card_consumption_log/*")
public class HZ_Gas_Card_Consumption_LogController {
	@Autowired
	private HZ_Sinopec_Util sinopec_Util;
	@Autowired
	private IHZ_Main_Gas_CardService hz_main_gas_cardService;
	/**
	 * 主卡列表页
	 * @author linyb
	 * @create_date 2014-5-6上午9:11:54
	 */
	@RequestMapping(value = "query.htm")
	public String query(ModelMap model){
		return "/card/gas_card_consumption_log/query";
	}
	/**
	 * ajax获取主卡列表数据
	 * @author linyb
	 * @create_date 2014-5-6上午9:09:45
	 * @param name
	 * @param pageData
	 */
	@RequestMapping(value = "ajax_list_mainCard.htm",produces = "application/json")
	public @ResponseBody Map ajax_list(ModelMap model,String name,HZ_PageData pageData){
		Map<String,Object> sys_params = new HashMap<String, Object>();
		sys_params = new HashMap<String, Object>();
		sys_params.put("card_no_like", name);
		
		List<HZ_Main_Gas_Card> main_cards = hz_main_gas_cardService.listPageByParams(sys_params, pageData);
		//下载状态监听
		for (int i = 0; i < main_cards.size(); i++) {
			HZ_Main_Gas_Card main_card = main_cards.get(i);
			if(main_card.getCard_no().matches("^9[0-9]+$")){
				main_cards.remove(i);
			}
			main_card.setState(sinopec_Util.getMainCardState(main_card.getCard_no()));
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rows", main_cards);
		map.put("total", pageData.getTotalSize());
		return  map;
	}
	/**
	 * 启动下载页
	 * @author linyb
	 * @create_date 2014-5-6上午9:36:09
	 */
	@RequestMapping(value="toDownload.htm")
	public String toDownload(ModelMap model,Integer id){
		model.put("hz_mainCard", hz_main_gas_cardService.get(id));
		return "/card/gas_card_consumption_log/download";
	}
	/**
	 * 下载
	 * @author linyb
	 * @create_date 2014-5-6下午1:41:37
	 * @param httpServletRequest
	 * @param model
	 * @param startDate
	 * @param endDate
	 * @param cardNo
	 * @param pwd
	 * @return
	 */
	@RequestMapping(value = "startDownload.htm" ,produces = "application/json")
	public @ResponseBody Map ajax_startDownload(HttpServletRequest httpServletRequest,ModelMap model,String startDate,String endDate,String cardNo,String pwd){
		Map<String,Object> msg = new HashMap<String, Object>();
		String root = httpServletRequest.getServletContext().getRealPath("/") + "card_data/";
		try {
			sinopec_Util.main(cardNo, pwd, startDate, endDate, root);
			msg.put("show_msg", "200");
		} catch (IOException e) {
			msg.put("show_msg", "error");
		}
		return msg;
	}
	/**
	 * 
	 * @author xiaojin
	 * @create_date 2014-4-30下午5:22:19
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "manager.htm")
	public String manager(ModelMap model){
		return "/card/gas_card_consumption_log/manager";
	}
	
	/*@RequestMapping(value = "startDownload.htm")
	public void startDownload(HttpServletRequest httpServletRequest,ModelMap model,String startDate,String endDate,String cardNo,String pwd) throws IOException{
		String root = httpServletRequest.getServletContext().getRealPath("/") + "card_data/";
		sinopec_Util.main(cardNo, pwd, startDate, endDate, root);
	}*/
	
	/**
	 * 缓存数据
	 * @author linyb
	 * @create_date 2014-5-5上午10:30:23
	 * @param model
	 * @return
	 */
	@RequestMapping(value="list_cache_main_card_excel.htm")
	public String list_cache_main_card_excel(HttpServletRequest httpServletRequest,ModelMap model){
		String root = httpServletRequest.getServletContext().getRealPath("/") + "card_data/";
		File file = new File(root);
		File[] files = file.listFiles();
		/*for (File f : files) {
			System.out.println(f.getName() + "----" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(f.lastModified())));
		}*/
		model.put("files", files);
		return "/card/gas_card_consumption_log/list_cache_main_card_excel";
	}
	/**
	 * 缓存数据文件下载
	 * @author linyb
	 * @create_date 2014-5-5下午11:50:01
	 * @param httpServletRequest
	 * @param response
	 * @param name
	 */
	@RequestMapping(value="downloadCache.htm")
	public void downloadCache(HttpServletRequest httpServletRequest, HttpServletResponse response ,@RequestParam String name){
		
		String root = httpServletRequest.getServletContext().getRealPath("/") + "card_data/"+name;
		File file = new File(root);
		String filename = file.getName();
		 try {
            // 以流的形式下载文件。
            InputStream is = new BufferedInputStream(new FileInputStream(file));
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            // 清空response
            response.reset();
            // 设置response的Header
            response.addHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("UTF-8"),"ISO-8859-1"));
            response.addHeader("Content-Length", "" + file.length());
            OutputStream os = new BufferedOutputStream(response.getOutputStream());
            response.setContentType("application/octet-stream");
            os.write(buffer);
            os.flush();
            os.close();
        } catch (Exception e) {
        	e.printStackTrace();
        }
	}
	
	/**
	 * 控制台页面
	 * @author linyb
	 * @create_date 2014-5-5下午2:57:19
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "console_info.htm")
	public String console_info(ModelMap model,String cardNo){
		model.put("cardNo", cardNo);
		return "/card/gas_card_consumption_log/console_info";
	}
	/**
	 * 控制台打印
	 * @author linyb
	 * @create_date 2014-5-5下午3:32:56
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "ajax_msg_console.htm",produces = "application/json")
	public @ResponseBody Map ajax_record_list(HttpServletRequest request,ModelMap model,String cardNo){
		Map<String, Object> map = new HashMap<String, Object>();
		Map<String,StringBuffer> console_msg = sinopec_Util.print_msg();
		map.put("show_msg", console_msg.get(cardNo));
		return  map;
	}
}
