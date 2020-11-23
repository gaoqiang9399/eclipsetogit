package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.RecallConfigLetter;
import app.util.toolkit.Ipage;

/**
 * Title: RecallConfigLetterBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Thu Mar 23 15:31:18 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface RecallConfigLetterFeign {
	@RequestMapping(value = "/RecallConfigLetter/insert")
	public void insert(@RequestBody RecallConfigLetter recallConfigLetter) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLetter/delete")
	public void delete(@RequestBody RecallConfigLetter recallConfigLetter) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLetter/update")
	public void update(@RequestBody RecallConfigLetter recallConfigLetter) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLetter/getById")
	public RecallConfigLetter getById(@RequestBody RecallConfigLetter recallConfigLetter) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLetter/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("recallConfigLetter") RecallConfigLetter recallConfigLetter) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLetter/getAll")
	public List<RecallConfigLetter> getAll(@RequestBody RecallConfigLetter recallConfigLetter) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLetter/getByCollectionPlanNo")
	public RecallConfigLetter getByCollectionPlanNo(@RequestBody RecallConfigLetter recallConfigLetter) throws ServiceException;

	@RequestMapping(value = "/RecallConfigLetter/getByRecKindNo")
	public List<RecallConfigLetter> getByRecKindNo(@RequestBody RecallConfigLetter recallConfigLetter) throws ServiceException;
}
