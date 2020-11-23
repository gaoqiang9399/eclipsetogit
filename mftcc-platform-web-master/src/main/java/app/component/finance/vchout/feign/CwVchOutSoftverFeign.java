package app.component.finance.vchout.feign;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.vchout.entity.CwVchOutSoftver;
import app.util.toolkit.Ipage;

/**
 * Title: CwVchOutSoftverBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 18 17:37:08 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwVchOutSoftverFeign {

	@RequestMapping(value = "/cwVchOutSoftver/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwVchOutSoftver cwVchOutSoftver) throws Exception;

	@RequestMapping(value = "/cwVchOutSoftver/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwVchOutSoftver cwVchOutSoftver) throws Exception;

	@RequestMapping(value = "/cwVchOutSoftver/update", method = RequestMethod.POST)
	public void update(@RequestBody CwVchOutSoftver cwVchOutSoftver) throws Exception;

	@RequestMapping(value = "/cwVchOutSoftver/getById", method = RequestMethod.POST)
	public CwVchOutSoftver getById(@RequestBody CwVchOutSoftver cwVchOutSoftver) throws Exception;

	@RequestMapping(value = "/cwVchOutSoftver/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("cwVchOutSoftver") CwVchOutSoftver cwVchOutSoftver) throws Exception;

	/**
	 * 方法描述： 提供选择使用
	 * 
	 * @param ipage
	 * @param cwVchOutSoftver
	 * @return
	 * @throws Exception
	 *             Ipage
	 * @author Javelin
	 * @date 2017-12-23 上午11:37:45
	 */
	@RequestMapping(value = "/cwVchOutSoftver/getSelectExportVch", method = RequestMethod.POST)
	public Ipage getSelectExportVch(@RequestBody Ipage ipage, @RequestParam("cwVchOutSoftver") CwVchOutSoftver cwVchOutSoftver) throws Exception;

	/**
	 * 方法描述： 获取导出凭证模版数据excel对象
	 * 
	 * @param cwVchOutSoftver
	 * @param vchNos
	 * @return
	 * @throws Exception
	 *             HSSFWorkbook
	 * @author Javelin
	 * @date 2017-12-22 上午10:56:10
	 */
	@RequestMapping(value = "/cwVchOutSoftver/getExportVoucherTemplateWB", method = RequestMethod.POST)
	public byte[] getExportVoucherTemplateWB(@RequestBody CwVchOutSoftver cwVchOutSoftver, @RequestParam("vchNos")String vchNos,@RequestParam("finBooks") String finBooks)
			throws Exception;
}
