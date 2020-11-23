package app.component.calc.penaltyinterface;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.calc.penalty.entity.MfBusAppPenaltyChild;
import app.component.calc.penalty.entity.MfBusAppPenaltyMain;
import app.component.calc.penalty.entity.MfSysPenaltyMain;

@FeignClient("mftcc-platform-factor")
public interface PenaltyInterfaceFeign {
	/**
	 * 描述: 
	 * @param mfSysPenaltyMain
	 * @throws Exception
	 */
	@RequestMapping(value = "/penaltyInterface/insert")
	public void insert(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/penaltyInterface/update")
	public void update(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/penaltyInterface/getById")
	public MfSysPenaltyMain getById(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/penaltyInterface/delete")
	public void delete(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/penaltyInterface/getAllList")
	public List<MfSysPenaltyMain> getAllList(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	@RequestMapping(value = "/penaltyInterface/updateOrInsert")
	public void updateOrInsert(@RequestBody MfSysPenaltyMain mfSysPenaltyMain) throws Exception;
	
	/**
	 * 违约金主产品表插入记录
	 * @param mfBusAppPenaltyMain
	 */
	@RequestMapping(value = "/penaltyInterface/insertMfBusAppPenaltyMain")
	public void insertMfBusAppPenaltyMain(@RequestBody String appId,@RequestParam("kindNo") String kindNo) throws Exception;
	
	/**
	 * 违约金产品子表插入记录
	 * @param mfBusAppPenaltyChild
	 */
	@RequestMapping(value = "/penaltyInterface/insertMfBusAppPenaltyChild")
	public void insertMfBusAppPenaltyChild(@RequestBody String appId,@RequestParam("kindNo") String kindNo) throws Exception;
    /**
     * 
     * 方法描述：通过申请号  和借据剩余日期获取违约金计算比例
     * @param appPenaltyChildQuery
     * @return 
     * MfBusAppPenaltyChild
     * @author WD
     * @date 2017-7-21 上午10:12:14
     */
	@RequestMapping(value = "/penaltyInterface/getMfBusAppPenaltyChildByBean")
	public MfBusAppPenaltyChild getMfBusAppPenaltyChildByBean(
			MfBusAppPenaltyChild appPenaltyChildQuery)throws Exception;
    /**
     * 
     * 方法描述：通过申请号 和位于金分类 获取违约金主表信息
     * @param mfBusAppPenaltyMain
     * @return
     * @throws Exception 
     * MfBusAppPenaltyMain
     * @author WD
     * @date 2017-7-22 下午3:34:27
     */
	@RequestMapping(value = "/penaltyInterface/getMfBusAppPenaltyMainById")
	public MfBusAppPenaltyMain getMfBusAppPenaltyMainById(
			MfBusAppPenaltyMain mfBusAppPenaltyMain)throws Exception;
		
}
