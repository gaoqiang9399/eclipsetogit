package app.component.finance.account.feign;

import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.account.entity.CwRelation;
import app.component.finance.util.R;
import app.util.toolkit.Ipage;

/**
 * Title: CwRelationBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Jan 24 09:42:23 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwRelationFeign {

	@RequestMapping(value = "/cwRelation/insert", method = RequestMethod.POST)
	public R insert(@RequestBody CwRelation cwRelation,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwRelation/delete", method = RequestMethod.POST)
	public R delete(@RequestBody CwRelation cwRelation,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwRelation/update", method = RequestMethod.POST)
	public R update(@RequestBody CwRelation cwRelation,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwRelation/getById", method = RequestMethod.POST)
	public CwRelation getById(@RequestBody CwRelation cwRelation,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwRelation/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取CwRelation根
	 * 
	 * @param cwRelationbean
	 * @return
	 * @throws ServiceException
	 *             CwRelation
	 * @author lzshuai
	 * @date 2017-3-1 下午8:55:53
	 */
	@RequestMapping(value = "/cwRelation/getCwFicationBean", method = RequestMethod.POST)
	public CwRelation getCwFicationBean(@RequestBody CwRelation cwRelationbean,@RequestParam("finBooks") String finBooks) throws ServiceException;

	/**
	 * 方法描述： 获取所有科目下挂有的辅助核算(@RequestBody 凭证使用)
	 * 
	 * @param paramMap
	 * @return
	 * @throws ServiceException
	 *             Map<String,Object>
	 * @author Javelin
	 * @date 2017-3-1 下午2:01:54
	 */
	@RequestMapping(value = "/cwRelation/getRelaForVchData", method = RequestMethod.POST)
	public Map<String, Object> getRelaForVchData(@RequestBody Map<String, String> paramMap,@RequestParam("finBooks") String finBooks) throws ServiceException;

}
