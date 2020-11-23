package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.RecallBase;
import app.component.wkf.entity.Result;
import app.util.toolkit.Ipage;

/**
 * Title: RecallBaseBo.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Mar 15 09:24:17 GMT 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface RecallBaseFeign {
	@RequestMapping(value = "/recallBase/insert")
	public void insert(@RequestBody RecallBase recallBase) throws ServiceException;
	/*20190306yxl催收指派保存*/
	@RequestMapping(value = "/recallBase/insert1")
	public void insert1(@RequestBody RecallBase recallBase) throws ServiceException;

	@RequestMapping(value = "/recallBase/delete")
	public void delete(@RequestBody RecallBase recallBase) throws ServiceException;

	@RequestMapping(value = "/recallBase/update")
	public void update(@RequestBody RecallBase recallBase) throws ServiceException;

	@RequestMapping(value = "/recallBase/updateTel")
	public void updateTel(@RequestBody RecallBase recallBase) throws ServiceException;

	@RequestMapping(value = "/recallBase/submit")
	public void submit(@RequestBody RecallBase recallBase) throws Exception;

	@RequestMapping(value = "/recallBase/getById")
	public RecallBase getById(@RequestBody RecallBase recallBase) throws ServiceException;

	@RequestMapping(value = "/recallBase/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;
	/*20190307yxl条件查询*/
	@RequestMapping(value = "/recallBase/findByPage1")
	public Ipage findByPage1(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/recallBase/getAll")
	public List<RecallBase> getAll(@RequestBody RecallBase recallBase) throws ServiceException;

	/**
	 * 更新经办人，经办人名称，
	 * 
	 * @param recallBase
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/recallBase/updateMgr")
	public void updateMgr(@RequestBody RecallBase recallBase) throws ServiceException;

	@RequestMapping(value = "/recallBase/findByPageUserTask")
	public Ipage findByPageUserTask(@RequestBody Ipage ipage)
			throws ServiceException;

	@RequestMapping(value = "/recallBase/delegateRecall")
	public void delegateRecall(@RequestBody RecallBase recallBase) throws ServiceException;

	@RequestMapping(value = "/recallBase/findDealByPage")
	public Ipage findDealByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping(value = "/recallBase/doCommit")
	public Result doCommit(@RequestParam("taskId")  String taskId, @RequestParam("appId")  String appId, @RequestParam("opinionType")  String opinionType,
			@RequestParam("approvalOpinion")  String approvalOpinion, @RequestParam("transition")  String transition, @RequestParam("opNo")  String opNo,
			@RequestParam("nextUser")  String nextUser, @RequestBody  RecallBase recallBase) throws ServiceException;

	@RequestMapping(value = "/recallBase/insertForSimu")
	public void insertForSimu(@RequestBody RecallBase recallBase) throws ServiceException;
	/*20190306yxl修改插入催收配置的接口增加信函送达地址，快递公司，快递单号三个字段*/
	@RequestMapping(value = "/recallBase/insertForSimu1")
	public void insertForSimu1(@RequestBody RecallBase recallBase) throws ServiceException;
	@RequestMapping(value = "/recallBase/getRecallTimesByConNo")
	public List<RecallBase> getRecallTimesByConNo(@RequestBody String conNo) throws Exception;

	/**
	 * @Description:获取催收历史展示方式 1-表单展示(供产品使用) 2-列表展示(供铁甲网使用)
	 * @return
	 * @author: 李伟
	 * @date: 2017-12-7 下午2:45:35
	 */
	@RequestMapping(value = "/recallBase/getRecallHistoryTypeShow")
	public String getRecallHistoryTypeShow();
}
