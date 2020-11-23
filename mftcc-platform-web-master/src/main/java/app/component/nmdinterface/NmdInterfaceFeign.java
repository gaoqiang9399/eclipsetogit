package app.component.nmdinterface;

import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.app.entity.MfBusAppKind;
import app.component.app.entity.MfBusApply;
import app.component.nmd.censor.entity.MfBusCensorBase;
import app.component.nmd.censor.entity.MfBusCensorBiz;
import app.component.nmd.entity.NmdArea;
import app.component.nmd.entity.NmdComInf;
import app.component.nmd.entity.ParmDic;
import app.component.nmd.entity.RateNorm;
import app.component.nmd.entity.SysLegalHolidayRecord;
import app.component.nmd.entity.WorkCalendar;
import app.util.toolkit.Ipage;

@FeignClient("mftcc-platform-factor")
public interface NmdInterfaceFeign {

	/**
	 * 获取公司信息
	 */
	@RequestMapping("/nmdInterface/getById")
	public NmdComInf getById(@RequestBody NmdComInf nmdComInf) throws Exception;

	@RequestMapping("/nmdInterface/getRateNormForLoan")
	public RateNorm getRateNormForLoan(@RequestBody int termMon, @RequestParam("sts") String sts,
			@RequestParam("rateType") String rateType, @RequestParam("sysDate") String sysDate);

	@RequestMapping("/nmdInterface/getParmDicList")
	public List getParmDicList() throws Exception;

	/**
	 * 根据key_name查询所有字典项
	 * 
	 * @param parmDic
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/nmdInterface/findAllParmDicByKeyName")
	public List findAllParmDicByKeyName(@RequestBody ParmDic parmDic) throws Exception;

	/**
	 * 根据区域编号查找区域信息
	 * 
	 * @param nmdArea
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/nmdInterface/getNmdAreaByAreaNo")
	public NmdArea getNmdAreaByAreaNo(@RequestBody NmdArea nmdArea) throws Exception;

	/**
	 * 查询当天要提醒的信息
	 * 
	 * @param nmdArea
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/nmdInterface/fullCalendarDaylist")
	public List<WorkCalendar> fullCalendarDaylist(@RequestParam("opNo") String opNo) throws Exception;

	/**
	 * 查询parm_dic_all数据字典想视图
	 * 
	 * @param key
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/nmdInterface/findByParmDicAllByKename")
	public List<ParmDic> findByParmDicAllByKename(@RequestParam("key") String key) throws Exception;

	/**
	 * 
	 * 方法描述： 获得数据字典分页列表
	 * 
	 * @param ipg
	 * @param parmDic
	 * @return
	 * @throws Exception
	 *             Ipage
	 * @author 沈浩兵
	 * @date 2016-9-21 下午6:19:07
	 */
	@RequestMapping("/nmdInterface/findByPage")
	public Ipage findByPage(@RequestBody Ipage ipg, @RequestParam("parmDic") ParmDic parmDic) throws Exception;

	/**
	 * 
	 * 方法描述： 根据数据字典名和字典项编号获得字典项信息
	 * 
	 * @param parmDic
	 * @return
	 * @throws Exception
	 *             ParmDic
	 * @author 沈浩兵
	 * @date 2016-9-22 下午4:22:35
	 */
	@RequestMapping("/nmdInterface/getParmDicById")
	public ParmDic getParmDicById(@RequestBody ParmDic parmDic) throws Exception;

	/**
	 * 
	 * 方法描述： 添加数据字典项
	 * 
	 * @param parmDic
	 * @return
	 * @throws Exception
	 *             ParmDic
	 * @author 沈浩兵
	 * @date 2017-3-20 下午6:05:32
	 */
	@RequestMapping("/nmdInterface/insertParmDic")
	public void insertParmDic(@RequestBody ParmDic parmDic) throws Exception;

	/**
	 * 
	 * 方法描述： 更新数据字典项
	 * 
	 * @param parmDic
	 * @throws Exception
	 *             void
	 * @author 沈浩兵
	 * @date 2017-3-21 上午10:11:28
	 */
	@RequestMapping("/nmdInterface/updateParmDic")
	public void updateParmDic(@RequestBody ParmDic parmDic) throws Exception;

	/**
	 * 方法描述： 根据产品与节点获取对应审查表文件列表数据
	 * 
	 * @param mfBusCensorBiz
	 * @return
	 * @throws Exception
	 *             List<MfBusCensorBase>
	 * @author Javelin
	 * @date 2017-7-15 下午3:22:22
	 */
	@RequestMapping("/nmdInterface/getBusCensorBaseList")
	public List<MfBusCensorBase> getBusCensorBaseList(@RequestBody MfBusCensorBiz mfBusCensorBiz) throws Exception;

	/**
	 *
	 * 方法描述：通过申请 期限 获取基准利率类型 和 基准利率值，基准利率日期
	 * 
	 * @param mfBusApply
	 * @param mfBusAppKind
	 * @return baseMap.put("baseRate",baseRate );/nmdInterface/基准利率</br>
	 *         baseMap.put("baseRateDate",baseRateDate);/nmdInterface/基准利率开始日期
	 *         </br>
	 *         baseMap.put("baseRateType",baseRateType);/nmdInterface/基准利率类型
	 *         1-贷款基准利率 2-公积金贷款率 3-贴现基准利率</br>
	 *         Map<String,String>
	 * @author WD
	 * @date 2017-8-29 上午10:35:15
	 */
	@RequestMapping("/nmdInterface/getMfSysRateTypeMap")
	public Map<String, String> getMfSysRateTypeMap(@RequestBody MfBusApply mfBusApply,
			@RequestParam("mfBusAppKind") MfBusAppKind mfBusAppKind) throws Exception;

	@RequestMapping("/nmdInterface/getCount")
	public int getCount(@RequestBody String keyName);

	/**
	 * 
	 * 方法描述：根据条件获取法定假日结束后的第一个工作日
	 * 
	 * @param mapParm
	 * @return
	 * @throws Exception
	 *             SysLegalHolidayRecord
	 * @author WD
	 * @date 2017-10-30 下午8:27:13
	 */
	@RequestMapping("/nmdInterface/getFestivalSetBeanByWorkDay")
	public SysLegalHolidayRecord getFestivalSetBeanByWorkDay(@RequestBody Map<String, String> mapParm) throws Exception;
}