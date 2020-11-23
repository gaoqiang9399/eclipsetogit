package app.component.finance.paramset.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.finance.paramset.entity.CwProofWords;
import app.util.toolkit.Ipage;

/**
 * Title: CwProofWordsBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 26 16:14:31 CST 2016
 **/
@FeignClient("mftcc-platform-fiscal")
public interface CwProofWordsFeign {
	/**
	 * 添加凭证字
	 * 
	 * @param cwProofWords
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/cwProofWords/insert", method = RequestMethod.POST)
	public void insert(@RequestBody CwProofWords cwProofWords,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwProofWords/delete", method = RequestMethod.POST)
	public void delete(@RequestBody CwProofWords cwProofWords,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwProofWords/update", method = RequestMethod.POST)
	public void update(@RequestBody CwProofWords cwProofWords,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwProofWords/getById", method = RequestMethod.POST)
	public CwProofWords getById(@RequestBody CwProofWords cwProofWords,@RequestParam("finBooks") String finBooks) throws ServiceException;

	@RequestMapping(value = "/cwProofWords/findByPage", method = RequestMethod.POST)
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("finBooks") String finBooks) throws ServiceException;

}
