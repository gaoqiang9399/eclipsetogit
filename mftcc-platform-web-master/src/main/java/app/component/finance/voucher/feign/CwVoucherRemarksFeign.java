package app.component.finance.voucher.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.voucher.entity.CwVoucherRemarks;
import app.util.toolkit.Ipage;

/**
 * Title: CwVoucherRemarksBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Sat Jan 07 09:04:42 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwVoucherRemarksFeign {

	@RequestMapping(value = "/cwVoucherRemarks/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwVoucherRemarks cwVoucherRemarks,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVoucherRemarks/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwVoucherRemarks cwVoucherRemarks,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVoucherRemarks/update", method = RequestMethod.POST)
	public void update(@RequestBody CwVoucherRemarks cwVoucherRemarks,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVoucherRemarks/getById", method = RequestMethod.POST)
	public CwVoucherRemarks getById(@RequestBody CwVoucherRemarks cwVoucherRemarks,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVoucherRemarks/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 保存凭证摘要
	 * 
	 * @param map
	 *            form提交的参数
	 * @return
	 */
	@RequestMapping(value = "/cwVoucherRemarks/saveVoucherRemarks", method = RequestMethod.POST)
	public String[] saveVoucherRemarks(@RequestBody Map<String, Object> map,@RequestParam("finBooks") String finBooks) throws Exception;

}
