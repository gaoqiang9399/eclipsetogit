package app.component.finance.account.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.account.entity.CwFication;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwFicationBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Jan 19 15:24:19 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwFicationFeign {

	@RequestMapping(value = "/cwFication/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwFication cwFication,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwFication/delete", method = RequestMethod.POST)
	public R delete(@RequestBody CwFication cwFication,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwFication/update", method = RequestMethod.POST)
	public void update(@RequestBody CwFication cwFication,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwFication/getById", method = RequestMethod.POST)
	public CwFication getById(@RequestBody CwFication cwFication,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwFication/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取辅助核算项列表数据
	 * 
	 * @return
	 * @throws ServiceException
	 *             List<Map<String,String>>
	 * @author 刘争帅
	 * @date 2017-2-3 上午10:17:06
	 */
	@RequestMapping(value = "/cwFication/getFicationData", method = RequestMethod.POST)
	public List<Map<String, String>> getFicationData(@RequestParam("finBooks") String finBooks) throws ServiceException;

}
