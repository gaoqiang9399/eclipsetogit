package app.component.finance.account.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.account.entity.CwFicationData;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwFicationDataBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Jan 23 10:26:36 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwFicationDataFeign {

	@RequestMapping(value = "/cwFicationData/insert", method = RequestMethod.POST)
	public R insert(@RequestBody CwFicationData cwFicationData,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwFicationData/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwFicationData cwFicationData,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwFicationData/update", method = RequestMethod.POST)
	public void update(@RequestBody CwFicationData cwFicationData,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwFicationData/getById", method = RequestMethod.POST)
	public CwFicationData getById(@RequestBody CwFicationData cwFicationData,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwFicationData/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,
			@RequestParam("finBooks") String finBooks)
			throws ServiceException;

	/**
	 * 方法描述： 根据类型获取辅助核算数据
	 * 
	 * @param ipage
	 * @param formMap
	 * @return
	 * @throws ServiceException
	 *             Ipage
	 * @author Javelin
	 * @date 2017-3-1 下午4:05:25
	 */
	@RequestMapping(value = "/cwFicationData/getFicationData", method = RequestMethod.POST)
	public Ipage getFicationData(@RequestBody Ipage ipage, @RequestParam("finBooks") String finBooks)
			throws ServiceException;

	/**
	 * 方法描述：系统同步 员工、部门、客户到辅助核算数据表
	 * 
	 * @throws ServiceException
	 * @throws Exception
	 */
	@RequestMapping(value = "/cwFicationData/insertSysDataFicationData", method = RequestMethod.POST)
	public void insertSysDataFicationData(@RequestParam("type") String type,@RequestParam("finBooks") String finBooks) throws ServiceException;

}
