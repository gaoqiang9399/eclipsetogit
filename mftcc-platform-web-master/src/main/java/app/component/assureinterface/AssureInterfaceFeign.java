package app.component.assureinterface;


import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.component.assure.entity.MfAssureInfo;
import app.component.assure.entity.MfBusApplyAssureInfo;

/**
 * 类名： AssureInterface
 * 描述：保证信息管理对外接口
 * @author zhs
 * @date 2017-7-20 下午3:14:50
 */
@FeignClient("mftcc-platform-factor")
public interface AssureInterfaceFeign {

	/**
	 * 
	 * 方法描述： 获取业务关联的保证信息
	 * @param mfAssureInfo
	 * @return
	 * @throws Exception
	 * MfAssureInfo
	 * @author zhs
	 * @date 2017-7-22 下午4:37:19
	 */
	@RequestMapping(value = "/assureInterface/getById")
	public MfAssureInfo getById(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;

	/**
	 * 
	 * 方法描述： 更新保证信息
	 * @param mfAssureInfo
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-7-22 下午6:17:45
	 */
	@RequestMapping(value = "/assureInterface/updateAssureInfo")
	public void updateAssureInfo(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;

	/**
	 * 方法描述： 获取保证人列表
	 * @param mfAssureInfo
	 * @return
	 * @throws Exception
	 * List<MfAssureInfo>
	 * @author YuShuai
	 * @date 2017-9-22 下午7:04:54
	 */
	@RequestMapping(value = "/assureInterface/getAssureList")
	public List<MfAssureInfo> getAssureList(@RequestBody MfAssureInfo mfAssureInfo)throws Exception;
	
	/**
	 * 
	 * 方法描述： 保存保证信息
	 * @param mfAssureInfo
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-11-8 下午8:05:39
	 */
	@RequestMapping(value = "/assureInterface/insertAssureInfo")
	public MfAssureInfo insertAssureInfo(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;

	/**
	 * 
	 * 方法描述： 获取客户为他人担保的项目
	 * @param mfAssureInfo
	 * @return
	 * @throws Exception
	 * List<MfBusApplyAssureInfo>
	 * @author zhs
	 * @date 2017-12-2 下午12:25:42
	 */
	@RequestMapping(value = "/assureInterface/getMultiBusList")
	public List<MfBusApplyAssureInfo> getMultiBusList(@RequestBody MfAssureInfo mfAssureInfo) throws Exception;
	/**
	 * 
	 * 方法描述： Pad端登记担保信息
	 * @param dataMap
	 * @param mfAssureInfo
	 * @return
	 * @throws Exception
	 * MfAssureInfo
	 * @author YaoWenHao
	 * @date 2017-12-14 下午4:55:49
	 */
	@RequestMapping(value = "/assureInterface/insertForApp")
	public Map<String, Object> insertForApp (@RequestBody Map<String, Object> dataMap,@RequestParam("mfAssureInfo")  MfAssureInfo mfAssureInfo) throws Exception;
	/**
	 * 
	 * 方法描述： Pad端单独登记担保信息
	 * @param dataMap
	 * @param mfAssureInfo
	 * @return
	 * @throws Exception
	 * MfAssureInfo
	 * @author YaoWenHao
	 * @date 2017-12-14 下午4:55:49
	 */
	@RequestMapping(value = "/assureInterface/insertOnlyForApp")
	public MfAssureInfo insertOnlyForApp (@RequestBody Map<String, Object> dataMap,@RequestParam("mfAssureInfo")  MfAssureInfo mfAssureInfo) throws Exception;
	/***
	 * 
	 * 方法描述： pad端修改修改保证人关联修改其他信息
	 * @param mfAssureInfo
	 * @throws Exception
	 * void
	 * @author YaoWenHao
	 * @date 2017-12-17 上午11:44:48
	 */
	@RequestMapping(value = "/assureInterface/updateForPad")
	public void updateForPad(@RequestBody MfAssureInfo mfAssureInfo,@RequestParam("appId") String appId) throws Exception;

	/**
	 * 方法描述： 根据id删除保证人信息
	 * @param mfAssureInfo
	 * @throws Exception
	 * void
	 * @author YuShuai
	 * @date 2017-12-20 下午2:41:47
	 */
	@RequestMapping(value = "/assureInterface/deleteAssureInfo")
	public void deleteAssureInfo(@RequestBody MfAssureInfo mfAssureInfo)throws Exception;
}
