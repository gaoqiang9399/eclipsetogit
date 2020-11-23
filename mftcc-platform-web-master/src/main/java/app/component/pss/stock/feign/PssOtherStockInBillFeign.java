package  app.component.pss.stock.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssOtherStockInBill;
import app.component.pss.stock.entity.PssOtherStockInDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssOtherStockInBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Aug 14 11:03:39 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssOtherStockInBillFeign {
	
	@RequestMapping(value = "/pssOtherStockInBill/insert")
	public void insert(@RequestBody PssOtherStockInBill pssOtherStockInBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInBill/delete")
	public void delete(@RequestBody PssOtherStockInBill pssOtherStockInBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInBill/update")
	public void update(@RequestBody PssOtherStockInBill pssOtherStockInBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInBill/getById")
	public PssOtherStockInBill getById(@RequestBody PssOtherStockInBill pssOtherStockInBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssOtherStockInBill") PssOtherStockInBill pssOtherStockInBill) throws Exception;

	@RequestMapping(value = "/pssOtherStockInBill/getAll")
	public List<PssOtherStockInBill> getAll(@RequestBody PssOtherStockInBill pssOtherStockInBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInBill/getOtherStockInBillList")
	public Ipage getOtherStockInBillList(@RequestBody Ipage ipg,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInBill/deleteOSIBBatch")
	public Map<String, String> deleteOSIBBatch(@RequestBody Map<String, String> formMap) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInBill/getByOSIBNo")
	public PssOtherStockInBill getByOSIBNo(@RequestBody PssOtherStockInBill pssOtherStockInBill) throws Exception;
	
//	@RequestMapping(value = "/pssOtherStockInBill/insertOSIBBet")
//	public void insertOSIBBet(@RequestBody PssOtherStockInBill pssOtherStockInBill, List<PssOtherStockInDetailBill> list) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInBill/insertOSIBBet")
	public Map<String, String> insertOSIBBet(@RequestBody PssOtherStockInBill pssOtherStockInBill, @RequestParam("list") List<PssOtherStockInDetailBill> list) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockInBill/updateOSIBBet")
	public Map<String, String> updateOSIBBet(@RequestBody PssOtherStockInBill pssOtherStockInBill, @RequestParam("list") List<PssOtherStockInDetailBill> list) throws Exception;
	
	/**审核*/
	@RequestMapping(value = "/pssOtherStockInBill/updateChk")
	public Map<String, String> updateChk(@RequestBody PssOtherStockInBill pssOtherStockInBill, @RequestParam("list")List<PssOtherStockInDetailBill> list) throws Exception;
	
	/**反审核*/
	@RequestMapping(value = "/pssOtherStockInBill/updateChk")
	public Map<String, String> updateChk(@RequestBody PssOtherStockInBill pssOtherStockInBill) throws Exception;
	
	/**批量审核*/
	@RequestMapping(value = "/pssOtherStockInBill/updateChkBatch")
	public Map<String, String> updateChkBatch(@RequestBody Map<String, String> formMap) throws Exception;
	
	/**批量反审核*/
	@RequestMapping(value = "/pssOtherStockInBill/updateReChkBatch")
	public Map<String, String> updateReChkBatch(@RequestBody Map<String, String> formMap) throws Exception;
}
