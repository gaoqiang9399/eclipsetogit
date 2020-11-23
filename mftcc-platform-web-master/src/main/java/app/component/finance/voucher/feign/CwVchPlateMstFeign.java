package app.component.finance.voucher.feign;

import java.io.File;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.util.R;
import app.component.finance.voucher.entity.CwVchPlateMst;
import app.util.toolkit.Ipage;

/**
 * Title: CwVchPlateMstBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Mar 07 11:04:27 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwVchPlateMstFeign {

	/**
	 * 方法描述： 新增凭证模版
	 * 
	 * @param formMap
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-7 上午11:12:08
	 */
	@RequestMapping(value = "/cwVchPlateMst/addVchPlate", method = RequestMethod.POST)
	public R addVchPlate(@RequestBody Map<String, Object> formMap,@RequestParam ("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取凭证模版
	 * 
	 * @param plateNo
	 * @return
	 * @throws ServiceException
	 *             Map<String,Object>
	 * @author Javelin
	 * @date 2017-3-7 下午3:33:08
	 */
	@RequestMapping(value = "/cwVchPlateMst/getVchPlateByNo", method = RequestMethod.POST)
	public Map<String, Object> getVchPlateByNo(@RequestBody String plateNo,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 获取凭证模版Excel对象
	 * @return
	 * @throws Exception
	 * HSSFWorkbook
	 * @author Javelin
	 * @date 2017-3-16 下午3:51:24
	 */
	@RequestMapping(value = "/cwVchPlateMst/getVchTemplateWB", method = RequestMethod.POST) 
	public HSSFWorkbook getVchTemplateWB(@RequestParam("finBooks") String finBooks) throws Exception;
	@RequestMapping(value = "/cwVchPlateMst/getVchTemplateWBData", method = RequestMethod.POST) 
	public byte[] getVchTemplateWBData(@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 方法描述： 凭证模版导入
	 * 
	 * @param file
	 * @return
	 * @throws Exception
	 *             String
	 * @author Javelin
	 * @date 2017-3-17 上午11:33:56
	 */
	@RequestMapping(value = "/cwVchPlateMst/importVchPlate", method = RequestMethod.POST)
	public R importVchPlate(@RequestBody File file,@RequestParam("finBooks") String finBooks,@RequestParam("regNo") String regNo, @RequestParam("regName") String regName) throws Exception;

	/**
	 * 方法描述： 获取导出凭证模版数据excel对象
	 * 
	 * @param vchNos
	 * @return
	 * @throws Exception
	 *             HSSFWorkbook
	 * @author Javelin
	 * @date 2017-3-18 下午4:04:52
	 */
	@RequestMapping(value = "/cwVchPlateMst/getExportVchTemplateWB", method = RequestMethod.POST)
	public HSSFWorkbook getExportVchTemplateWB(@RequestBody String vchNos,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchPlateMst/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwVchPlateMst cwVchPlateMst,@RequestParam ("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwVchPlateMst/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwVchPlateMst cwVchPlateMst,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwVchPlateMst/update", method = RequestMethod.POST)
	public void update(@RequestBody CwVchPlateMst cwVchPlateMst,@RequestParam ("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwVchPlateMst/getById", method = RequestMethod.POST)
	public CwVchPlateMst getById(@RequestBody CwVchPlateMst cwVchPlateMst,@RequestParam ("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwVchPlateMst/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam ("finBooks") String finBooks) throws ServiceException;

}
