package  app.component.modelinterface;

import java.util.List;
import java.util.Map;

import app.component.model.entity.MfTemplateModelRel;
import app.component.model.entity.MfSysTemplate;
import app.component.model.entity.MfTemplateModel;
import app.component.model.entity.LoanTemplateTagSet;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.model.entity.LoanTemplateTagBase;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import app.base.ServiceException;


/**
 *
 * 类名： ModelInterface
 * 描述：模板管理提供的外部接口
 * @author 沈浩兵
 * @date 2016-9-8 下午5:11:23
 *
 *
 */

@FeignClient("mftcc-platform-factor")
public interface ModelInterfaceFeign {
	/**
	 *
	 * 方法描述： 前台打开模板保存标签替换值及修改添加的数据时插入记录信息
	 * @param mfSysModelSaveInfo
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2016-9-8 下午5:14:35
	 */
	@RequestMapping(value = "/modelInterface/insert")
	public void insert(@RequestBody Map<String,Object> dataMap) throws ServiceException;

	@RequestMapping(value = "/modelInterface/update")
	public void update(@RequestBody MfTemplateModelRel mfTemplateModelRel) throws ServiceException;
	/**
	 *
	 * 方法描述： 获得模板保存信息供新增保存后编辑打开使用
	 * @param mfSysModelSaveInfo
	 * @return
	 * @throws ServiceException
	 * MfSysModelSaveInfo
	 * @author 沈浩兵
	 * @date 2016-9-8 下午5:15:19
	 */
	@RequestMapping(value = "/modelInterface/getById")
	public Map<String,Object> getById(@RequestBody Map<String,Object> dataMap) throws ServiceException;
	/**
	 *
	 * 方法描述： 获得模板信息list
	 * @param mfSysModel
	 * @return
	 * @throws ServiceException
	 * List<MfSysModel>
	 * @author 沈浩兵
	 * @date 2016-9-26 下午6:04:27
	 */
	@RequestMapping(value = "/modelInterface/getListMfSysTemplate")
	public List<MfSysTemplate> getListMfSysTemplate(@RequestBody MfSysTemplate mfSysTemplate) throws ServiceException;

	/**
	 * 根据模板id查询模板信息
	 * @author LJW
	 * date 2017-3-16
	 */
	@RequestMapping(value = "/modelInterface/getById")
	public MfSysTemplate getById(@RequestBody MfSysTemplate mfSysTemplate) throws ServiceException;
	/**
	 *
	 * 方法描述： 获取所有启用的模板模型
	 * @param mfTemplateModel
	 * @return
	 * @throws ServiceException
	 * List<MfSysModel>
	 * @author zhs
	 * @date 2016-11-19 下午3:33:14
	 */
	@RequestMapping(value = "/modelInterface/getTemplateModelList")
	public List<MfTemplateModel> getTemplateModelList(@RequestBody MfTemplateModel mfTemplateModel)throws ServiceException;

	/**
	 *
	 * 方法描述： 初始化业务某生成阶段的文件配置信息
	 * @param mfSysModelSaveInfo
	 * @throws ServiceException
	 * void
	 * @author zhs
	 * @date 2016-11-21 下午2:50:55
	 */
	@RequestMapping(value = "/modelInterface/createSysModelSaveInfo")
	public void createSysModelSaveInfo(@RequestBody MfTemplateModelRel mfTemplateModelRel)throws ServiceException;
	/**
	 *
	 * 方法描述：  获得基础标签list
	 * @param loanTemplateTagBase
	 * @return
	 * @throws ServiceException
	 * List<LoanTemplateTagBase>
	 * @author 沈浩兵
	 * @date 2017-2-18 下午5:23:02
	 */
	@RequestMapping(value = "/modelInterface/getTagBaseList")
	public List<LoanTemplateTagBase> getTagBaseList(@RequestBody LoanTemplateTagBase loanTemplateTagBase) throws ServiceException;
	/**
	 *
	 * 方法描述： 添加模板和标签对应关系
	 * @param templateTagSet
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-2-20 上午10:22:28
	 */
	@RequestMapping(value = "/modelInterface/insertTemplateTagSet")
	public void insertTemplateTagSet(@RequestBody LoanTemplateTagSet templateTagSet)throws ServiceException;
	/**
	 *
	 * 方法描述： 更新模板和标签对应关系
	 * @param templateTagSet
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-2-20 上午10:41:20
	 */
	@RequestMapping(value = "/modelInterface/updateTemplateTagSet")
	public void updateTemplateTagSet(@RequestBody LoanTemplateTagSet templateTagSet)throws ServiceException;
	/**
	 *
	 * 方法描述： 删除模板和标签对应关系
	 * @param templateTagSet
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-2-20 上午11:46:26
	 */
	@RequestMapping(value = "/modelInterface/deteleTemplateTagSet")
	public void deteleTemplateTagSet(@RequestBody LoanTemplateTagSet templateTagSet)throws ServiceException;

	/**
	 * 工作流开始事件，文档模板业务关联配置初始化
	 *
	 * @param templateBizConfig
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-7-7 上午9:25:11
	 */
	@RequestMapping(value = "/modelInterface/doTemplateBizConfigInit")
	public void doTemplateBizConfigInit(@RequestBody MfTemplateBizConfig templateBizConfig) throws Exception;

	/**
	 * 方法描述： 根据mf_template_show.showtype  node_no, mf_template_biz_config的appId获取文档的唯一编号
	 * @param map
	 * @return
	 * @throws Exception
	 * String
	 * @author YuShuai
	 * @date 2017-8-2 下午2:45:56
	 */
	@RequestMapping(value = "/modelInterface/getfTemplateBizConfigId")
	public String getfTemplateBizConfigId(@RequestBody Map<String, String> map)throws Exception;
	/**
	 *
	 * 方法描述： 根据业务编号获得保存的模板信息
	 * @param mfTemplateModelRel
	 * @return
	 * @throws Exception
	 * List<MfTemplateModelRel>
	 * @author 沈浩兵
	 * @date 2017-8-28 下午2:17:11
	 */
	@RequestMapping(value = "/modelInterface/getMfTemplateModelRelList")
	public List<MfTemplateModelRel> getMfTemplateModelRelList(@RequestBody MfTemplateModelRel mfTemplateModelRel) throws Exception;
	/**
	 *
	 * 方法描述： 查询业务文档模板配置
	 * @param templateBizConfig
	 * @return
	 * @throws Exception
	 * List<MfTemplateBizConfig>
	 * @author 沈浩兵
	 * @date 2017-10-21 下午3:52:32
	 */
	@RequestMapping(value = "/modelInterface/getBizConfigList")
	public List<MfTemplateBizConfig> getBizConfigList(@RequestBody MfTemplateBizConfig templateBizConfig) throws Exception;

	/**
	 *
	 * 方法描述： 获取业务模板关联信息
	 * @param mfTemplateBizConfig
	 * @return
	 * @throws Exception
	 * MfTemplateBizConfig
	 * @author zhs
	 * @date 2018-1-18 下午12:01:47
	 */
	@RequestMapping(value = "/modelInterface/getMfTemplateBizConfig")
	public MfTemplateBizConfig getMfTemplateBizConfig(@RequestBody MfTemplateBizConfig mfTemplateBizConfig)throws Exception;
	/**
	 * 方法描述： 根据模板编号获取模板文件名
	 * @param templateNo
	 * @return
	 * String
	 * @author 仇招
	 * @date 2018-1-26 下午3:50:26
	 */
	@RequestMapping(value = "/modelInterface/getTemplateFileNameById")
	public String getTemplateFileNameById(@RequestBody String templateNo)throws Exception ;
	@RequestMapping(value = "/modelInterface/getTemplateFileNameById", method = RequestMethod.POST)
	public void updateMfTemplateBizConfig(@RequestBody MfTemplateBizConfig mfTemplateBizConfig)throws Exception ;
	@RequestMapping(value = "/modelInterface/doWordReplaceToPdf")
	void doWordReplaceToPdf(@RequestBody MfTemplateBizConfig tbc)throws Exception ;

	@RequestMapping(value = "/modelInterface/doVouAfterTrackInit")
    void doVouAfterTrackInit(@RequestBody MfTemplateBizConfig templateBizConfig)throws Exception ;
}


