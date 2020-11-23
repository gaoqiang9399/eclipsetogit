package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.RecallConfigNote;
import app.util.toolkit.Ipage;

/**
 * Title: RecallConfigNoteBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Mar 20 09:55:30 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface RecallConfigNoteFeign {
	@RequestMapping(value = "/RecallConfigNote/insert")
	public void insert(@RequestBody RecallConfigNote recallConfigNote) throws ServiceException;

	@RequestMapping(value = "/RecallConfigNote/delete")
	public void delete(@RequestBody RecallConfigNote recallConfigNote) throws ServiceException;

	@RequestMapping(value = "/RecallConfigNote/update")
	public void update(@RequestBody RecallConfigNote recallConfigNote) throws ServiceException;

	@RequestMapping(value = "/RecallConfigNote/getById")
	public RecallConfigNote getById(@RequestBody RecallConfigNote recallConfigNote) throws ServiceException;

	@RequestMapping(value = "/RecallConfigNote/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("recallConfigNote") RecallConfigNote recallConfigNote) throws ServiceException;

	@RequestMapping(value = "/RecallConfigNote/getAll")
	public List<RecallConfigNote> getAll(@RequestBody RecallConfigNote recallConfigNote) throws ServiceException;

	@RequestMapping(value = "/RecallConfigNote/getByCollectionPlanNo")
	public RecallConfigNote getByCollectionPlanNo(@RequestBody RecallConfigNote recallConfigNote) throws ServiceException;

	@RequestMapping(value = "/RecallConfigNote/updateSts")
	public int updateSts(@RequestBody RecallConfigNote recallConfigNote) throws ServiceException;

	@RequestMapping(value = "/RecallConfigNote/getByRecKindNo")
	public List<RecallConfigNote> getByRecKindNo(@RequestBody RecallConfigNote recallConfigNote) throws ServiceException;
}
