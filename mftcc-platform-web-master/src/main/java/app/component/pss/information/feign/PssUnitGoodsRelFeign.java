package app.component.pss.information.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import app.base.ServiceException;
import app.component.pss.information.entity.PssUnitGoodsRel;
import app.util.toolkit.Ipage;

/**
 * Title: PssUnitGoodsRelBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Dec 12 17:08:28 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssUnitGoodsRelFeign {

	@RequestMapping("/pssUnitGoodsRel/insert")
	public void insert(@RequestBody PssUnitGoodsRel pssUnitGoodsRel) throws ServiceException ;

	@RequestMapping("/pssUnitGoodsRel/deleteByGoodsId")
	public void deleteByGoodsId(@RequestBody PssUnitGoodsRel pssUnitGoodsRel) throws ServiceException ;

	@RequestMapping("/pssUnitGoodsRel/deleteByUgrId")
	public void deleteByUgrId(@RequestBody PssUnitGoodsRel pssUnitGoodsRel) throws ServiceException ;

	@RequestMapping("/pssUnitGoodsRel/update")
	public void update(@RequestBody PssUnitGoodsRel pssUnitGoodsRel) throws ServiceException ;

	@RequestMapping("/pssUnitGoodsRel/getById")
	public PssUnitGoodsRel getById(@RequestBody PssUnitGoodsRel pssUnitGoodsRel) throws ServiceException ;

	@RequestMapping("/pssUnitGoodsRel/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException ;

	@RequestMapping("/pssUnitGoodsRel/getAll")
	public List<PssUnitGoodsRel> getAll(@RequestBody PssUnitGoodsRel pssUnitGoodsRel) throws ServiceException ;

	@RequestMapping("/pssUnitGoodsRel/getAllByGoodsId")
	public List<PssUnitGoodsRel> getAllByGoodsId(@RequestBody PssUnitGoodsRel pssUnitGoodsRel) throws ServiceException ;
}
