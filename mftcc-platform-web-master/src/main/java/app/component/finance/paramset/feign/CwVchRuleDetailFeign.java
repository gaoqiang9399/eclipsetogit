package app.component.finance.paramset.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.finance.paramset.entity.CwVchRuleDetail;
import app.util.toolkit.Ipage;

/**
 * Title: CwVchRuleDetailBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Fri Mar 10 15:48:51 CST 2017
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwVchRuleDetailFeign {

	@RequestMapping(value = "/cwVchRuleDetail/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwVchRuleDetail cwVchRuleDetail,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchRuleDetail/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwVchRuleDetail cwVchRuleDetail,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchRuleDetail/update", method = RequestMethod.POST)
	public void update(@RequestBody CwVchRuleDetail cwVchRuleDetail,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchRuleDetail/getById", method = RequestMethod.POST)
	public CwVchRuleDetail getById(@RequestBody CwVchRuleDetail cwVchRuleDetail,@RequestParam("finBooks") String finBooks) throws Exception;

	@RequestMapping(value = "/cwVchRuleDetail/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("finBooks") String finBooks) throws Exception;

	/**
	 * 
	 * 方法描述： 根据uuid删除
	 * 
	 * @param cwVchRuleDetail
	 * @return
	 * @throws Exception
	 *             String
	 * @author lzshuai
	 * @date 2017-4-20 上午11:46:33
	 */
	@RequestMapping(value = "/cwVchRuleDetail/deletebyUuid", method = RequestMethod.POST)
	public String deletebyUuid(@RequestBody CwVchRuleDetail cwVchRuleDetail,@RequestParam("finBooks") String finBooks) throws Exception;
}
