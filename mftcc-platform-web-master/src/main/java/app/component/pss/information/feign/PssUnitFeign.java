package app.component.pss.information.feign;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pss.information.entity.PssUnit;
import app.util.toolkit.Ipage;

/**
 * Title: PssUnitBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Mon Dec 04 10:18:34 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssUnitFeign {

	@RequestMapping("/pssUnit/insert")
	public void insert(@RequestBody PssUnit pssUnit) throws ServiceException ;

	@RequestMapping("/pssUnit/insert1")
	public Map<String, String> insert(@RequestBody List<PssUnit> unitList) throws ServiceException ;

	@RequestMapping("/pssUnit/delete")
	public void delete(@RequestBody PssUnit pssUnit) throws ServiceException ;

	@RequestMapping("/pssUnit/getSameName")
	public int getSameName(@RequestBody PssUnit pssUnit) throws ServiceException ;

	@RequestMapping("/pssUnit/update")
	public void update(@RequestBody PssUnit pssUnit) throws ServiceException ;

	@RequestMapping("/pssUnit/updateFlag")
	public int updateFlag(@RequestBody PssUnit pssUnit) throws ServiceException ;

	@RequestMapping("/pssUnit/getById")
	public PssUnit getById(@RequestBody PssUnit pssUnit) throws ServiceException ;

	@RequestMapping("/pssUnit/getMutiById")
	public List<PssUnit> getMutiById(@RequestBody PssUnit pssUnit) throws ServiceException ;

	@RequestMapping("/pssUnit/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException ;

	@RequestMapping("/pssUnit/findByPageMuti")
	public Ipage findByPageMuti(@RequestBody Ipage ipage) throws ServiceException ;

	@RequestMapping("/pssUnit/findByPageSingle")
	public Ipage findByPageSingle(@RequestBody Ipage ipage) throws ServiceException ;

	@RequestMapping("/pssUnit/getAll")
	public List<PssUnit> getAll(@RequestBody PssUnit pssUnit) throws ServiceException ;

	@RequestMapping("/pssUnit/getAllOnByUnitType")
	public List<PssUnit> getAllOnByUnitType(@RequestBody PssUnit pssUnit) throws ServiceException ;

	@RequestMapping("/pssUnit/getByRelIdGoodsId")
	public Ipage getByRelIdGoodsId(@RequestBody Ipage ipg, @RequestParam("relId") String relId, @RequestParam("goodsId")String goodsId) throws ServiceException ;
}
