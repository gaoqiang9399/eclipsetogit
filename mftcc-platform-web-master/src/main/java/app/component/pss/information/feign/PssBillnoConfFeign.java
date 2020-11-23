package app.component.pss.information.feign;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.pss.information.entity.PssBillnoConf;
import app.util.toolkit.Ipage;

/**
 * Title: PssBillnoConfBo.java Description:
 * 
 * @author:kaifa@dhcc.com.cn
 * @Tue Sep 12 14:31:41 CST 2017
 **/
@FeignClient("mftcc-platform-factor")
public interface PssBillnoConfFeign {

	@RequestMapping("/mfQueryDisagree/insert")
	public void insert(@RequestBody PssBillnoConf pssBillnoConf) throws ServiceException ;

	@RequestMapping("/mfQueryDisagree/delete")
	public void delete(@RequestBody PssBillnoConf pssBillnoConf) throws ServiceException ;

	@RequestMapping("/mfQueryDisagree/mfQueryDisagree")
	public void update(@RequestBody PssBillnoConf pssBillnoConf) throws ServiceException ;

	@RequestMapping("/mfQueryDisagree/getById")
	public PssBillnoConf getById(@RequestBody PssBillnoConf pssBillnoConf) throws ServiceException ;

	@RequestMapping("/mfQueryDisagree/mfQueryDisagree")
	public PssBillnoConf getByType(@RequestBody PssBillnoConf pssBillnoConf) throws ServiceException ;

	@RequestMapping("/mfQueryDisagree/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipage) throws ServiceException ;

	@RequestMapping("/mfQueryDisagree/getAll")
	public List<PssBillnoConf> getAll(@RequestBody PssBillnoConf pssBillnoConf) throws ServiceException ;

	@RequestMapping("/mfQueryDisagree/getBillNo")
	public String getBillNo(@RequestParam("billType") String billType, @RequestParam("option")String option) throws ServiceException ;

	@RequestMapping("/mfQueryDisagree/matchRuleNo")
	public Boolean matchRuleNo(@RequestParam("billType") String billType, @RequestParam("billNo")String billNo) throws ServiceException ;

}
