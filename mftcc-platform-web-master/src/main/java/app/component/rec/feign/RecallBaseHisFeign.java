package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.RecallBaseHis;
import app.util.toolkit.Ipage;

/**
 * Title: RecallBaseHisBo.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Tue Mar 15 09:27:00 GMT 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface RecallBaseHisFeign {
	@RequestMapping(value = "/RecallBaseHis/insert")
	public void insert(@RequestBody RecallBaseHis recallBaseHis) throws ServiceException;

	@RequestMapping(value = "/RecallBaseHis/delete")
	public void delete(@RequestBody RecallBaseHis recallBaseHis) throws ServiceException;

	@RequestMapping(value = "/RecallBaseHis/update")
	public void update(@RequestBody RecallBaseHis recallBaseHis) throws ServiceException;

	@RequestMapping(value = "/RecallBaseHis/getById")
	public RecallBaseHis getById(@RequestBody RecallBaseHis recallBaseHis) throws ServiceException;

	@RequestMapping(value = "/RecallBaseHis/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("recallBaseHis") RecallBaseHis recallBaseHis) throws ServiceException;

	@RequestMapping(value = "/RecallBaseHis/getAll")
	public List<RecallBaseHis> getAll(@RequestBody RecallBaseHis recallBaseHis) throws ServiceException;

	/**
	 * 获取合同下催收历史条数
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/RecallBaseHis/getCountByConNo")
	public Integer getCountByConNo(@RequestBody String conNo) throws ServiceException;

	@RequestMapping(value = "/RecallBaseHis/getRecallBaseHisList")
	public List<RecallBaseHis> getRecallBaseHisList(@RequestBody RecallBaseHis recallBaseHis) throws ServiceException;
}
