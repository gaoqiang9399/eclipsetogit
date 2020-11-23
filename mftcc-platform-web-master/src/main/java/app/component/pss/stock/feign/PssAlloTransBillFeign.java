package  app.component.pss.stock.feign;
import java.io.File;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssAlloTransBill;
import app.component.pss.stock.entity.PssAlloTransDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssAlloTransBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Thu Aug 10 10:29:03 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssAlloTransBillFeign {
	
	@RequestMapping(value = "/pssAlloTransBill/insert")
	public void insert(@RequestBody PssAlloTransBill pssAlloTransBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransBill/delete")
	public void delete(@RequestBody PssAlloTransBill pssAlloTransBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransBill/update")
	public void update(@RequestBody PssAlloTransBill pssAlloTransBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransBill/getById")
	public PssAlloTransBill getById(@RequestBody PssAlloTransBill pssAlloTransBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransBill/getByATBNo")
	public PssAlloTransBill getByATBNo(@RequestBody PssAlloTransBill pssAlloTransBill) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssAlloTransBill") PssAlloTransBill pssAlloTransBill) throws Exception;
	

	@RequestMapping(value = "/pssAlloTransBill/getAlloTransBillList")
	public Ipage getAlloTransBillList(@RequestBody Ipage ipg,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;

	@RequestMapping(value = "/pssAlloTransBill/getAll")
	public List<PssAlloTransBill> getAll(@RequestBody PssAlloTransBill pssAlloTransBill) throws Exception;
	
	/**批量审核*/
	@RequestMapping(value = "/pssAlloTransBill/updateChkBatch")
	public Map<String, String> updateChkBatch(@RequestBody Map<String, String> formMap) throws Exception;
	
	/**批量反审核*/
	@RequestMapping(value = "/pssAlloTransBill/updateReChkBatch")
	public Map<String, String> updateReChkBatch(@RequestBody Map<String, String> formMap) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransBill/deleteATBBatch")
	public Map<String, String> deleteATBBatch(@RequestBody Map<String, String> formMap) throws Exception;
	

	@RequestMapping(value = "/pssAlloTransBill/insertATB")
	public void insertATB(@RequestBody PssAlloTransBill pssAlloTransBill,@RequestParam("tableListMap")  List<Map<String, String>> tableListMap) throws Exception;
	
//	@RequestMapping(value = "/pssAlloTransBill/insertATBBet")
//	public void insertATBBet(@RequestBody PssAlloTransBill pssAlloTransBill, List<PssAlloTransDetailBill> list) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransBill/insertATBBet")
	public Map<String, String> insertATBBet(@RequestBody PssAlloTransBill pssAlloTransBill, @RequestParam("list")List<PssAlloTransDetailBill> list) throws Exception;
	

	@RequestMapping(value = "/pssAlloTransBill/updateATB")
	public void updateATB(@RequestBody PssAlloTransBill pssAlloTransBill,@RequestParam("tableListMap")  List<Map<String, String>> tableListMap) throws Exception;
	
//	@RequestMapping(value = "/pssAlloTransBill/updateATBBet")
//	public void updateATBBet(@RequestBody PssAlloTransBill pssAlloTransBill, List<PssAlloTransDetailBill> list) throws Exception;
	
	@RequestMapping(value = "/pssAlloTransBill/updateATBBet")
	public Map<String, String> updateATBBet(@RequestBody PssAlloTransBill pssAlloTransBill, @RequestParam("list")List<PssAlloTransDetailBill> list) throws Exception;
	
	/**审核*/
	@RequestMapping(value = "/pssAlloTransBill/updateChk")
	public Map<String, String> updateChk(@RequestBody PssAlloTransBill pssAlloTransBill,@RequestParam("list") List<PssAlloTransDetailBill> list) throws Exception;
	
	/**反审核*/
	@RequestMapping(value = "/pssAlloTransBill/updateReChk")
	public Map<String, String> updateReChk(@RequestBody PssAlloTransBill pssAlloTransBill) throws Exception;
	
	/**
	 * 方法描述：获取文件输入流
	 * @param is
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/pssAlloTransBill/getDownFileStream")
	public InputStream getDownFileStream(@RequestBody InputStream is)throws Exception;
	
	/**
	 * 方法描述： 调拨单模版导入
	 * @param file
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2018-01-05 上午11:33:56
	 */

	@RequestMapping(value = "/pssAlloTransBill/uploadExcel")
	public String uploadExcel(@RequestBody File file) throws Exception;
	
	/**
	 * 方法描述： 调拨单模版导入
	 * @param file
	 * @return
	 * @throws Exception
	 * String
	 * @author HGX
	 * @date 2018-01-24 下午17:25:03
	 */
	@RequestMapping(value = "/pssAlloTransBill/uploadExcel")
	public String uploadExcel(@RequestBody File file,@RequestParam("type")  String type) throws Exception;
	
	/**
	 * 
	 * 方法描述： 调拨单导出Excel
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author hgx
	 * @date 2018-01-03 上午11:05:03
	 */
	@RequestMapping(value = "/pssAlloTransBill/downloadToExcel")
	public HSSFWorkbook downloadToExcel(@RequestBody Map<String, String> paramMap) throws Exception;
	
}
