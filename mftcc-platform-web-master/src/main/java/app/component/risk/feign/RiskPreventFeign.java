package app.component.risk.feign;

import java.util.List;
import java.util.Map;

import app.component.risk.entity.RiskItem;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.nmd.entity.ParmKey;
import app.component.param.entity.Scence;
import app.component.risk.entity.RiskBizItemRel;
import app.component.risk.entity.RiskItemThreashode;
import app.component.risk.entity.RiskPrevent;

@FeignClient("mftcc-platform-factor")
public interface RiskPreventFeign {
	
	//获取所有风险拦截场景及生效的维度相关数据
	
	@RequestMapping(value = "/riskPrevent/getAllSceRiskDimList")
	public List<RiskPrevent> getAllSceRiskDimList() throws ServiceException;
	
	//获取分先拦截场景列表
	@RequestMapping(value = "/riskPrevent/getPreventSce")
	public List<Scence> getPreventSce() throws ServiceException;
	//获取场景支持的字典项
	@RequestMapping(value = "/riskPrevent/getLimitParmKey")
	public List<ParmKey> getLimitParmKey() throws ServiceException;
	//通过场景编号得到场景对应的维度关联
	@RequestMapping(value = "/riskPrevent/getRiskPreSceConfigInUse")
	public RiskPrevent getRiskPreSceConfigInUse(@RequestBody RiskPrevent riskPrevent) throws ServiceException;
	//通过场景编号，维度组合获取所有的生成维度度细分组合
	@RequestMapping(value = "/riskPrevent/getRiskPreventSceGens")
	public List<RiskPrevent> getRiskPreventSceGens(@RequestBody RiskPrevent riskPrevent) throws ServiceException;
	//通过维度细分类型编号获取所有的风险拦截项
	@RequestMapping(value = "/riskPrevent/getSelectedRiskItems")
	public List<RiskPrevent> getSelectedRiskItems (@RequestBody RiskPrevent riskPrevent) throws ServiceException;
	
	@RequestMapping(value = "/riskPrevent/getNotSelectedRiskItems")
	public List<RiskItem> getNotSelectedRiskItems (@RequestBody RiskPrevent riskPrevent) throws ServiceException;

	
	//新增场景维度关系
	@RequestMapping(value = "/riskPrevent/insertRiskPreventSceConfig")
	public void insertRiskPreventSceConfig(@RequestBody RiskPrevent riskPrevent) throws ServiceException;
	
	//新增场景维度细分类型
	@RequestMapping(value = "/riskPrevent/insertRiskPreventSceGen")
	public void insertRiskPreventSceGen(@RequestBody RiskPrevent riskPrevent) throws ServiceException;
	
	//新增维度类型与风险拦截项关系
	@RequestMapping(value = "/riskPrevent/insertRiskPreventItemRel")
	public void insertRiskPreventItemRel(@RequestBody RiskPrevent riskPrevent) throws ServiceException;
	
	//删除维度类型与风险拦截项关心
	@RequestMapping(value = "/riskPrevent/deleteRiskPreventItemRel")
	public void  deleteRiskPreventItemRel(@RequestBody RiskPrevent riskPrevent) throws ServiceException;
	
	//修改维度类型与风险拦截项关心
	@RequestMapping(value = "/riskPrevent/updateRiskPreventItemRel")
	public void updateRiskPreventItemRel(@RequestBody RiskPrevent riskPrevent) throws ServiceException;
	
	//在风险拦截项中修改阀值
	@RequestMapping(value = "/riskPrevent/updateRiskItemThreadHode")
	public void updateRiskItemThreadHode(@RequestBody RiskItemThreashode riskItemThreashode) throws ServiceException;
	
	
	@RequestMapping(value = "/riskPrevent/insertRiskBizItemRel")
	public void insertRiskBizItemRel(@RequestBody String scenceNo,@RequestParam("bizParm") Object bizParm,@RequestParam("relNo") String relNo) throws ServiceException;
	
	@RequestMapping(value = "/riskPrevent/insertRiskBizItemRel")
	public void insertRiskBizItemRel(@RequestBody String scenceNo,@RequestParam("parmMap") Map<String,String> parmMap,@RequestParam("relNo") String relNo) throws ServiceException;
	
	@RequestMapping(value = "/riskPrevent/getRiskValueResultMap")
	public Map<String, Map<String,String>> getRiskValueResultMap(@RequestBody List<RiskItem> riskItemList,@RequestParam("parmMap") Map<String,String> parmMap) throws ServiceException;
	
	@RequestMapping(value = "/riskPrevent/updatePassFlag")
	public List<RiskBizItemRel> updatePassFlag(@RequestBody String relNo) throws ServiceException;
	
	@RequestMapping(value = "/riskPrevent/deleteRiskBizItemRelByRelNo")
	public void deleteRiskBizItemRelByRelNo(@RequestBody String relNo) throws ServiceException;
	
	
	@RequestMapping(value = "/riskPrevent/findByRelNo")
	public List<RiskBizItemRel> findByRelNo(@RequestBody RiskBizItemRel riskBizItemRel) throws ServiceException;

	/**
	 * 
	 * 方法描述： 获取该模型下已配置的拦截项类别
	 * @param genNo
	 * @return
	 * @throws ServiceException
	 * List<String>
	 * @author zhs
	 * @date 2016-10-27 下午6:18:47
	 */
	@RequestMapping(value = "/riskPrevent/getSelectedRiskPreventClass")
	public List<String> getSelectedRiskPreventClass(@RequestBody String genNo) throws ServiceException;
	
	
	

	
	
	


	
}
