package  app.component.pss.stock.feign;
import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.pss.stock.entity.PssOtherStockOutBill;
import app.component.pss.stock.entity.PssOtherStockOutDetailBill;
import app.util.toolkit.Ipage;

/**
* Title: PssOtherStockOutBillBo.java
* Description:
* @author:kaifa@dhcc.com.cn
* @Mon Aug 14 11:47:07 CST 2017
**/

@FeignClient("mftcc-platform-factor")
public interface PssOtherStockOutBillFeign {
	
	@RequestMapping(value = "/pssOtherStockOutBill/insert")
	public void insert(@RequestBody PssOtherStockOutBill pssOtherStockOutBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutBill/delete")
	public void delete(@RequestBody PssOtherStockOutBill pssOtherStockOutBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutBill/update")
	public void update(@RequestBody PssOtherStockOutBill pssOtherStockOutBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutBill/getById")
	public PssOtherStockOutBill getById(@RequestBody PssOtherStockOutBill pssOtherStockOutBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutBill/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("pssOtherStockOutBill") PssOtherStockOutBill pssOtherStockOutBill) throws Exception;

	@RequestMapping(value = "/pssOtherStockOutBill/getAll")
	public List<PssOtherStockOutBill> getAll(@RequestBody PssOtherStockOutBill pssOtherStockOutBill) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutBill/getOtherStockOutBillList")
	public Ipage getOtherStockOutBillList(@RequestBody Ipage ipg,@RequestParam("formMap")  Map<String, String> formMap) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutBill/getByOSOBNo")
	public PssOtherStockOutBill getByOSOBNo(@RequestBody PssOtherStockOutBill pssOtherStockOutBill) throws Exception;
	
//	@RequestMapping(value = "/pssOtherStockOutBill/insertOSOBBet")
//	public void insertOSOBBet(@RequestBody PssOtherStockOutBill pssOtherStockOutBill, List<PssOtherStockOutDetailBill> list) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutBill/insertOSOBBet")
	public Map<String, String> insertOSOBBet(@RequestBody PssOtherStockOutBill pssOtherStockOutBill,@RequestParam("list") List<PssOtherStockOutDetailBill> list) throws Exception;
	
	@RequestMapping(value = "/pssOtherStockOutBill/updateOSOBBet")
	public Map<String, String> updateOSOBBet(@RequestBody PssOtherStockOutBill pssOtherStockOutBill,@RequestParam("list") List<PssOtherStockOutDetailBill> list) throws Exception;
	
	/**审核*/
	@RequestMapping(value = "/pssOtherStockOutBill/updateChk")
	public  Map<String, String> updateChk(@RequestBody PssOtherStockOutBill pssOtherStockOutBill,@RequestParam("list") List<PssOtherStockOutDetailBill> list) throws Exception;
	
	/**反审核*/
	@RequestMapping(value = "/pssOtherStockOutBill/updateChk1")
	public  Map<String, String> updateChk(@RequestBody PssOtherStockOutBill pssOtherStockOutBill) throws Exception;
	
	/**批量删除*/
	@RequestMapping(value = "/pssOtherStockOutBill/deleteOSOBBatch")
	public Map<String, String> deleteOSOBBatch(@RequestBody Map<String, String> formMap) throws Exception;
	
	/**批量审核*/
	@RequestMapping(value = "/pssOtherStockOutBill/updateChkBatch")
	public Map<String, String> updateChkBatch(@RequestBody Map<String, String> formMap) throws Exception;
	
	/**批量反审核*/
	@RequestMapping(value = "/pssOtherStockOutBill/updateReChkBatch")
	public Map<String, String> updateReChkBatch(@RequestBody Map<String, String> formMap) throws Exception;
	
}
