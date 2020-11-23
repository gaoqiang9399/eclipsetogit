package app.component.pms.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pms.entity.PmsDataSub;
import app.util.toolkit.Ipage;
import net.sf.json.JSONArray;

/**
 * Title: PmsDataSubBo.java Description:
 * 
 * @author:@dhcc.com.cn
 * @Wed Sep 21 02:27:39 GMT 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface PmsDataSubFeign {
	
	@RequestMapping(value = "/pmsDataSub/insert")
	public void insert(@RequestBody PmsDataSub pmsDataSub) throws ServiceException;

	@RequestMapping(value = "/pmsDataSub/insertConf")
	public void insertConf(@RequestParam("ajaxData") String ajaxData, @RequestParam("funNo") String funNo) throws ServiceException;

	@RequestMapping(value = "/pmsDataSub/delete")
	public void delete(@RequestBody PmsDataSub pmsDataSub) throws ServiceException;

	@RequestMapping(value = "/pmsDataSub/update")
	public void update(@RequestBody PmsDataSub pmsDataSub) throws ServiceException;

	@RequestMapping(value = "/pmsDataSub/getById")
	public PmsDataSub getById(@RequestBody PmsDataSub pmsDataSub) throws ServiceException;

	@RequestMapping(value = "/pmsDataSub/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage, @RequestParam("pmsDataSub") PmsDataSub pmsDataSub) throws ServiceException;

	@RequestMapping(value = "/pmsDataSub/findByPageForConf")
	public List<PmsDataSub> findByPageForConf(@RequestBody PmsDataSub pmsDataSub) throws ServiceException;
}
