package app.component.nmd.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.nmd.entity.ParmDic;
import app.util.toolkit.Ipage;

/**
 * Title: ParmDicBo.java Description:
 * 
 * @author:jiangyunxin@dhcc.com.cn
 * @Thu Apr 10 09:11:38 GMT 2014
 **/
@FeignClient("mftcc-platform-factor")
public interface ParmDicFeign {

	@RequestMapping("/parmDic/getById")
	public ParmDic getById(@RequestBody ParmDic parmDic) throws ServiceException;

	@RequestMapping("/parmDic/findlist")
	public List<ParmDic> findlist(@RequestBody ParmDic parmDic) throws ServiceException;

	@RequestMapping("/parmDic/del")
	public void del(@RequestBody ParmDic parmDic) throws ServiceException;

	@RequestMapping("/parmDic/insert")
	public void insert(@RequestBody ParmDic parmDic) throws ServiceException;

	@RequestMapping("/parmDic/insertForSelect")
	public ParmDic insertForSelect(@RequestBody ParmDic parmDic) throws ServiceException;

	@RequestMapping("/parmDic/update")
	public void update(@RequestBody ParmDic parmDic) throws ServiceException;

	@RequestMapping("/parmDic/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg) throws ServiceException;

	@RequestMapping("/parmDic/findBylist")
	public List<ParmDic> findByPage(@RequestBody ParmDic parmDic) throws ServiceException;

	@RequestMapping("/parmDic/getParmDicList")
	public List<ParmDic> getParmDicList() throws ServiceException;

	@RequestMapping("/parmDic/findByParmDicAllByKename")
	public List<ParmDic> findByParmDicAllByKename(@RequestBody String keyName) throws ServiceException;

	@RequestMapping("/parmDic/getCount")
	public int getCount(@RequestBody String keyName);
}