package app.component.pss.information.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.pss.information.entity.PssGoods;
import app.util.toolkit.Ipage;

/**
 * Title: PssGoodsBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Aug 15 17:06:56 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssGoodsFeign {

	@RequestMapping("/pssGoods/insert")
	public void insert(@RequestBody PssGoods pssGoods) throws ServiceException;

	@RequestMapping("/pssGoods/delete")
	public void delete(@RequestBody PssGoods pssGoods) throws ServiceException;

	@RequestMapping("/pssGoods/update")
	public void update(@RequestBody PssGoods pssGoods) throws ServiceException;

	@RequestMapping("/pssGoods/updateFlag")
	public int updateFlag(@RequestBody PssGoods pssGoods) throws ServiceException;

	@RequestMapping("/pssGoods/updateDisplayFlag")
	public int updateDisplayFlag(@RequestBody PssGoods pssGoods) throws ServiceException;

	@RequestMapping("/pssGoods/getById")
	public PssGoods getById(@RequestBody PssGoods pssGoods) throws ServiceException;

	@RequestMapping("/pssGoods/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping("/pssGoods/queryList")
	public Ipage queryList(@RequestBody Ipage ipage) throws ServiceException;

	@RequestMapping("/pssGoods/getAll")
	public List<PssGoods> getAll(@RequestBody PssGoods pssGoods) throws ServiceException;
}
