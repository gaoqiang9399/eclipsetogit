package app.component.rec.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.rec.entity.MfUrgeInfoModel;
import app.util.toolkit.Ipage;

/**
 * Title: MfUrgeInfoModelBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue May 31 10:44:59 CST 2016
 **/
@FeignClient("mftcc-platform-factor")
public interface MfUrgeInfoModelFeign {
	@RequestMapping(value = "/MfUrgeInfoModel/insert")
	public void insert(@RequestBody MfUrgeInfoModel mfUrgeInfoModel) throws ServiceException;

	@RequestMapping(value = "/MfUrgeInfoModel/delete")
	public void delete(@RequestBody MfUrgeInfoModel mfUrgeInfoModel) throws ServiceException;

	@RequestMapping(value = "/MfUrgeInfoModel/update")
	public void update(@RequestBody MfUrgeInfoModel mfUrgeInfoModel) throws ServiceException;

	@RequestMapping(value = "/MfUrgeInfoModel/getById")
	public MfUrgeInfoModel getById(@RequestBody MfUrgeInfoModel mfUrgeInfoModel) throws ServiceException;

	@RequestMapping(value = "/MfUrgeInfoModel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage,@RequestParam("mfUrgeInfoModel") MfUrgeInfoModel mfUrgeInfoModel) throws ServiceException;

	@RequestMapping(value = "/MfUrgeInfoModel/findList")
	public List<MfUrgeInfoModel> findList(@RequestBody MfUrgeInfoModel mfUrgeInfoModel) throws ServiceException;

	@RequestMapping(value = "/MfUrgeInfoModel/updateSts")
	public int updateSts(@RequestBody MfUrgeInfoModel mfUrgeInfoModel) throws ServiceException;

}
