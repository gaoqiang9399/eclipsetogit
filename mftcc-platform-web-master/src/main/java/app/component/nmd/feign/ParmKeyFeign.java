package app.component.nmd.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.nmd.entity.ParmKey;
import app.util.toolkit.Ipage;

/**
 * Title: ParmKeyBo.java Description:
 * 
 * @author:jiangyunxin@dhcc.com.cn
 * @Thu Apr 10 09:10:06 GMT 2014
 **/
@FeignClient("mftcc-platform-factor")
public interface ParmKeyFeign {

	@RequestMapping("/parmKey/getById")
	public ParmKey getById(@RequestBody ParmKey parmKey) throws ServiceException;

	@RequestMapping("/parmKey/del")
	public void del(@RequestBody ParmKey parmKey) throws ServiceException;

	@RequestMapping("/parmKey/insert")
	public void insert(@RequestBody ParmKey parmKey) throws ServiceException;

	@RequestMapping("/parmKey/update")
	public void update(@RequestBody ParmKey parmKey) throws ServiceException;

	@RequestMapping("/parmKey/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws ServiceException;

	@RequestMapping("/parmKey/findByList")
	public List<ParmKey> findByPage(@RequestBody ParmKey parmKey) throws ServiceException;
}