package app.component.accnt.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.accnt.entity.MfAccntTransfer;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: MfAccntTransferBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Wed May 25 17:50:56 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfAccntTransferFeign {
	@RequestMapping(value = "/mfAccntTransfer/insert")
	public void insert(@RequestBody MfAccntTransfer mfAccntTransfer) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransfer/delete")
	public void delete(@RequestBody MfAccntTransfer mfAccntTransfer) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransfer/update")
	public void update(@RequestBody MfAccntTransfer mfAccntTransfer) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransfer/getById")
	public MfAccntTransfer getById(@RequestBody MfAccntTransfer mfAccntTransfer) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransfer/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("mfAccntTransfer") MfAccntTransfer mfAccntTransfer)
			throws ServiceException;

	@RequestMapping(value = "/mfAccntTransfer/getList")
	public List<MfAccntTransfer> getList(@RequestBody MfAccntTransfer mfAccntTransfer) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransfer/doInProcess")
	public void doInProcess(@RequestBody MfAccntTransfer mfAccntTransfer) throws ServiceException;

	@RequestMapping(value = "/mfAccntTransfer/doCommit")
	public Result doCommit(@RequestParam("mfAccntTransfer") String taskId, @RequestParam("appNo") String appNo, @RequestParam("opinionType") String opinionType,
			@RequestParam("approvalOpinion") String approvalOpinion, @RequestParam("transition") String transition, @RequestParam("opNo") String opNo,
			@RequestParam("nextUser") String nextUser, @RequestBody MfAccntTransfer mfAccntTransfer) throws ServiceException;

	/**
	 * @author czk
	 * @Description: 获得可以进行回购的单据信息列表，目前该接口并未做筛选，直接返回了所有票据 date 2016-6-6
	 * @param pactId
	 * @return
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/mfAccntTransfer/getRepoList")
	public List<MfAccntTransfer> getRepoList(@RequestBody String pactId) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取所有待还的应收账款列表
	 * 
	 * @param mfAccntTransfer
	 * @return
	 * @throws ServiceException
	 *             List<MfAccntTransfer>
	 * @author zhs
	 * @date 2016-6-14 下午5:02:39
	 */
	@RequestMapping(value = "/mfAccntTransfer/getAccntListForUnRepay")
	public List<MfAccntTransfer> getAccntListForUnRepay(@RequestBody MfAccntTransfer mfAccntTransfer) throws ServiceException;

}
