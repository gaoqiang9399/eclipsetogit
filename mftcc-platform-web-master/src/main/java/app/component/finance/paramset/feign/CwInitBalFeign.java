package app.component.finance.paramset.feign;

import java.io.File;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwInitBal;
import app.component.finance.voucher.entity.CwVoucherAssist;
import app.util.toolkit.Ipage;

/**
 * Title: CwInitBalBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jan 03 10:25:01 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwInitBalFeign {

	@RequestMapping(value = "/cwInitBal/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwInitBal cwInitBal,@RequestParam ("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwInitBal/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwInitBal cwInitBal,@RequestParam ("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwInitBal/update", method = RequestMethod.POST)
	public void update(@RequestBody CwInitBal cwInitBal,@RequestParam ("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwInitBal/getById", method = RequestMethod.POST)
	public CwInitBal getById(@RequestBody CwInitBal cwInitBal,@RequestParam ("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwInitBal/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 读取临时文件夹中的excel文件，并导入数据库，返回处理结果
	 * 
	 * @param file1
	 * @param path
	 * @return
	 */
	@RequestMapping(value = "/cwInitBal/getreaderInitExcleResult", method = RequestMethod.POST)
	public String[] getreaderInitExcleResult(@RequestBody File file1, @RequestParam("file1FileName") String file1FileName,@RequestParam ("finBooks") String finBooks) throws Exception;
	
	@RequestMapping(value = "/cwInitBal/doDealMapData", method = RequestMethod.POST)
	public String[] doDealMapData(@RequestBody Map<String, String[]> map, @RequestParam ("finBooks") String finBooks,@RequestParam ("regNo")String regNo,@RequestParam ("regName")String regName) throws Exception;

	/**
	 * 生成模板表格
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwInitBal/getHSSFWorkbook", method = RequestMethod.POST)
	public HSSFWorkbook getHSSFWorkbook(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 判断是否已经财务余额初始化
	 * 
	 * @return '0'未初始化 '1'已初始化
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwInitBal/getIfBalInited", method = RequestMethod.POST)
	public Map<String, String> getIfBalInited(@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 获取财务辅助内容
	 * 
	 * @param assbean
	 * @return
	 * @throws Exception
	 *             Map<String,Object>
	 * @author lzshuai
	 * @date 2017-6-3 下午5:28:00
	 */
	@RequestMapping(value = "/cwInitBal/getMapObjByAcchrt", method = RequestMethod.POST)
	public Map<String, Object> getMapObjByAcchrt(@RequestBody CwVoucherAssist assbean,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 财务初始余额添加辅助核算项
	 * 
	 * @param assbean
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-6-3 下午6:57:53
	 */
	@RequestMapping(value = "/cwInitBal/doInitAssistInsert", method = RequestMethod.POST)
	public String doInitAssistInsert(@RequestBody CwVoucherAssist assbean,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 财务初始化添加辅助核算余额
	 * 
	 * @param accHrt
	 * @param voucherNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-6-21 上午11:33:22
	 */
	@RequestMapping(value = "/cwInitBal/addInitAssistBal", method = RequestMethod.POST)
	public String addInitAssistBal(@RequestBody Map<String, String> haMap,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 删除初始化余额辅助核算的功能
	 * 
	 * @param accHrt
	 * @param voucherNo
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-6-22 上午9:03:54
	 */
	@RequestMapping(value = "/cwInitBal/delInitAssistBal", method = RequestMethod.POST)
	public String delInitAssistBal(@RequestParam("accHrt") String accHrt, @RequestParam("voucherNo") String voucherNo,@RequestParam ("finBooks") String finBooks) throws Exception;

	/***
	 * 
	 * 方法描述： 试算平衡校验
	 * 
	 * @return
	 * @throws Exception
	 *             Map<String,String>
	 * @author lzshuai
	 * @date 2017-6-28 下午5:01:58
	 */
	@RequestMapping(value = "/cwInitBal/doCheckBalDatalance", method = RequestMethod.POST)
	public Map<String, String> doCheckBalDatalance(@RequestParam ("finBooks") String finBooks) throws Exception;

}
