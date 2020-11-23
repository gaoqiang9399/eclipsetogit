package app.component.cus.chainrelation.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.cus.chainrelation.entity.MfCusChainRelation;
import app.util.toolkit.Ipage;

/**
 * Title: MfCusChainRelationBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Feb 09 10:26:37 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface MfCusChainRelationFeign {

	@RequestMapping(value = "/mfCusChainRelation/insert")
	public void insert(@RequestBody MfCusChainRelation mfCusChainRelation) throws ServiceException;

	@RequestMapping(value = "/mfCusChainRelation/delete")
	public void delete(@RequestBody MfCusChainRelation mfCusChainRelation) throws ServiceException;

	@RequestMapping(value = "/mfCusChainRelation/update")
	public void update(@RequestBody MfCusChainRelation mfCusChainRelation) throws ServiceException;

	@RequestMapping(value = "/mfCusChainRelation/getById")
	public MfCusChainRelation getById(@RequestBody MfCusChainRelation mfCusChainRelation) throws ServiceException;

	@RequestMapping(value = "/mfCusChainRelation/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	/**
	 * 链属企业/核心企业查看对应的关联关系。
	 * 
	 * @param cusNo
	 * @param cusType
	 * @return List<MfCusChainRelation>
	 * @throws Exception
	 */
	@RequestMapping(value = "/mfCusChainRelation/getCusChainRelations")
	public List<MfCusChainRelation> getCusChainRelations(@RequestParam("cusNo") String cusNo, @RequestParam("cusType")String cusType) throws Exception;
}
