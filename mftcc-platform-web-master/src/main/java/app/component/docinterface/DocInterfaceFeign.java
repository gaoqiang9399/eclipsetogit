package app.component.docinterface;


import java.util.List;
import java.util.Map;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import app.base.ServiceException;
import app.component.doc.entity.DocBizManage;
import app.component.doc.entity.DocBizManageParam;
import app.component.doc.entity.DocBizSceConfig;
import app.component.doc.entity.DocManage;
import app.component.doc.entity.DocTypeConfig;
import net.sf.json.JSONArray;

@FeignClient("mftcc-platform-factor")
public interface DocInterfaceFeign {

	/**
	 * 此方法废弃, 初始化客户要件的请使用{@link #initiaCus}方法, 初始化业务要件的请使用{@link #initiaBiz}方法<br>
	 * @param docBizManageParam
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-5 上午11:01:17
	 */

	@RequestMapping(value = "/docInterface/createDocBizManage")
	public void createDocBizManage(@RequestBody DocBizManageParam docBizManageParam) throws Exception;

	/**
	 * 初始化客户要件<br>
	 * 必须: dbmp.cusNo(客户号)<br>
	 * @param docBizManageParam
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-5 下午4:14:30
	 */
	@RequestMapping(value = "/docInterface/initiaCus")
	public void initiaCus(@RequestBody DocBizManageParam docBizManageParam) throws Exception;

	/**
	 * 初始化业务要件<br>
	 * 必须: dbmp.scNo(场景编号), dbmp.relNo(业务编号), dbmp.dime(产品类型), dbmp.cusNo(客户号, 预留字段, 暂未发现使用场景)<br>
	 * scNo场景编号: BizPubParm.SCENCE_TYPE_DOC_*, 字典表scence<br>
	 * @param dbmp
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-5 下午4:10:07
	 */
	@RequestMapping(value = "/docInterface/initiaBiz")
	public void initiaBiz(@RequestBody DocBizManageParam dbmp) throws Exception;

	@RequestMapping(value = "/docInterface/deleteDoc")
	public void deleteDoc(@RequestBody String relNo) throws Exception;
	
	@RequestMapping(value = "/docInterface/getDocObject")
	public Object getDocObject(@RequestBody DocTypeConfig docTypeConfig,@RequestParam("parmMap") Map<String, String> parmMap) throws Exception;
	
	@RequestMapping(value = "/docInterface/docUpValidateMsg")
	public List<String> docUpValidateMsg(@RequestBody String relNo) throws Exception;

	@RequestMapping(value = "/docInterface/getDocBizManageCount")
	public Integer getDocBizManageCount(@RequestBody DocBizManage docBizManage)throws Exception;

	@RequestMapping(value = "/docInterface/getAllDocBizManage")
	public List<DocBizManage> getAllDocBizManage(@RequestBody DocBizManage docBizManage)throws Exception;

	@RequestMapping(value = "/docInterface/insertDocManage")
	public void insertDocManage(@RequestBody DocManage docManage)throws Exception;

	@RequestMapping(value = "/docInterface/getDocManageList")
	public List<DocManage> getDocManageList(@RequestBody DocManage docManage)throws Exception;
	
	/**
	 * 方法描述： 获取要件集合（处理要件路径为绝对路径）
	 * @param docManage 要件
	 * @return 要件集合
	 * @throws Exception
	 * @author yudongwei@mftcc.cn
	 * @date 2017-11-25 17:05:00
	 */
	@RequestMapping(value = "/docInterface/getDocManageListWithFileRealPath")
	public List<DocManage> getDocManageListWithFileRealPath(@RequestBody DocManage docManage)throws Exception;

	@RequestMapping(value = "/docInterface/createDocBizManageOnline")
	public void createDocBizManageOnline(@RequestBody DocBizManageParam dm)throws Exception;
	/**
	 * 获取客户需要上传的所有要件信息
	 * @param dm
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/docInterface/getByCusNo")
	public List<DocBizManage> getByCusNo(@RequestBody DocBizManage docBizManage)  throws Exception;
	@RequestMapping(value = "/docInterface/getDocDisPlayList")
	public JSONArray getDocDisPlayList(@RequestBody DocBizManage docBizManage,@RequestParam("arr")  String[] arr) throws Exception;
	
	/**
	 * 
	 * 方法描述： 单独上传组件
	 * @param dataMap
	 * @return
	 * @throws Exception
	 * JSONArray
	 * @author 沈浩兵
	 * @date 2017-4-7 上午9:21:26
	 */
	@RequestMapping(value = "/docInterface/getDocDisPlayListAloneComm")
	public JSONArray getDocDisPlayListAloneComm(@RequestBody Map<String,Object> dataMap) throws Exception;
	
	/**
	 * 方法描述： 根据场景编号和业务编号查询集合
	 * @param docBizManage
	 * @return
	 * @throws Exception
	 * @author yudongwei@mftcc.cn
	 * @date 2017-04-13 14:05:00
	 */
	@RequestMapping(value = "/docInterface/getByRelNo")
	public List<DocBizManage> getByRelNo(@RequestBody DocBizManage docBizManage) throws Exception;
	
	/**
	 * 方法描述： 根据文档类型查询文档细分类型集合
	 * @param docType 文档类型
	 * @return 文档细分类型集合
	 * @throws Exception
	 * @author yudongwei@mftcc.cn
	 * @date 2017-04-24 14:05:00
	 */
	@RequestMapping(value = "/docInterface/getDocTypeConfigList")
	public List<DocTypeConfig> getDocTypeConfigList(@RequestBody String docType) throws Exception;
	
	/**
	 * 方法描述： 根据业务场景编号删除表doc_manage数据和文件
	 * @param docBizNo 业务场景编号
	 * @throws Exception
	 * @author yudongwei@mftcc.cn
	 * @date 2017-04-26 14:05:00
	 */
	@RequestMapping(value = "/docInterface/deleteDocManageByDocBizNo")
	public void deleteDocManageByDocBizNo(@RequestBody String docBizNo) throws Exception;

	/**
	 * 
	 * 方法描述： 获取审批节点的附件信息
	 * @param paramMap
	 * @return
	 * @throws Exception
	 * List<DocManage>
	 * @author zhs
	 * @date 2017-5-22 上午11:40:06
	 */
	@RequestMapping(value = "/docInterface/getApprovalDoc")
	public List<DocManage> getApprovalDoc(@RequestBody Map<String, String> paramMap) throws Exception;
	/**
	 * 根据docNo和docBizNo查询要件
	 * @param docManage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/docInterface/getDocManageById")
	public DocManage getDocManageById(@RequestBody DocManage docManage) throws Exception;
	/**
	 * 根据docNo和docBizNo查询要件
	 * @param docManage
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/docInterface/deleteDocManageByDocNoDocBizNo")
	public void deleteDocManageByDocNoDocBizNo(@RequestBody DocManage docManage) throws Exception;
	
	@RequestMapping(value = "/docInterface/getDocCount")
	public int getDocCount(@RequestBody DocBizManage docBizManage) throws Exception;


	/**
	 * 
	 * 方法描述： 根据dime1获取要件场景的配置列表
	 * @param docBizSceConfig
	 * @return
	 * @throws Exception
	 * List<DocBizSceConfig>
	 * @author zhs
	 * @date 2017-6-22 上午10:55:08
	 */
	@RequestMapping(value = "/docInterface/getByDime")
	public List<DocBizSceConfig> getByDime(@RequestBody DocBizSceConfig docBizSceConfig) throws Exception;
	
	


	/**
	 * 要件展示，含业务主视图、各流程节点、各审批流程节点
	 * @param scNo
	 * @param relsNo
	 * @return
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-6-26 下午7:15:48
	 */
	@RequestMapping(value = "/docInterface/getDocDisPlay")
	public JSONArray getDocDisPlay(@RequestBody String scNo,@RequestParam("relsNo")  String relsNo) throws Exception;

	/**
	 * 
	 * 方法描述： 产品设置中删除节点下的要件配置
	 * @param kindNo
	 * @param nodeNo
	 * @throws Exception
	 * void
	 * @author zhs
	 * @date 2017-7-7 下午4:18:40
	 */
	@RequestMapping(value = "/docInterface/deleteDocBizSceConfig")
	public void deleteDocBizSceConfig(@RequestBody String kindNo,@RequestParam("nodeNo")  String nodeNo) throws Exception;
	
	/**
	 * 方法描述：上传要件
	 * @param docManage
	 * @throws Exception
	 */
	@RequestMapping(value = "/docInterface/insertDocManageNew")
	public void insertDocManageNew(@RequestBody DocManage docManage)  throws Exception;
	/**
	 * 方法描述：上传要件
	 * @param docManage
	 * @throws Exception
	 */
	@RequestMapping(value = "/docInterface/insertDocManageNewForApp")
	public void insertDocManageNewForApp(@RequestBody DocManage docManage)  throws Exception;
	/**
	 * 方法描述：更新要件
	 * @param docManage
	 * @throws Exception
	 */
	@RequestMapping(value = "/docInterface/updateDocManageNew")
	public void updateDocManageNew(@RequestBody DocManage docManage)  throws Exception;

	/**
	 * 要件必传验证
	 * 
	 * @param relNo 业务主键。可以是relNo="app17051118033081417"，也可以是relNo="cus17051118030821210,app17051118033081417,CONT17051118253580739,fincApp17051918314490254"
	 * @return 未满足必传要求返回false，满足返回true
	 * @throws Exception
	 * @author WangChao
	 * @date 2017-7-23 上午10:13:04
	 */
	@RequestMapping(value = "/docInterface/validateDocMustInput")
	public Boolean validateDocMustInput(@RequestBody String relNo) throws Exception;
	/**
	 * 
	 * 方法描述： 初始化贷后检查要件
	 * @param dbmp
	 * @throws Exception
	 * void
	 * @author 沈浩兵
	 * @date 2017-7-28 下午3:37:47
	 */
	@RequestMapping(value = "/docInterface/initExamineBiz")
	public void initExamineBiz(@RequestBody DocBizManageParam dbmp) throws Exception;

	/**
	 * 获取要件信息
	 * @param docManage
	 * @return
	 */
	@RequestMapping(value = "/docInterface/docManageBoGetById")
	DocManage docManageBoGetById(@RequestBody DocManage docManage);

	/**
	 * 初始化特殊客户类型要件
	 * @param dbmp
	 * @param cusType
	 */
	@RequestMapping(value = "/docInterface/initiaSECus")
	public void initiaSECus(@RequestBody DocBizManageParam dbmp,@RequestParam("cusType")  String cusType) throws Exception;
	
	/**
	 * 方法描述： 根据要件类型和业务编号获取要件信息列表
	 * @param List<DocManage>
	 * @return
	 * @author zhang_dlei
	 * @date 2017-6-22 下午6:08:08
	 */
	@RequestMapping(value = "/docInterface/getByBizAndSplitNo")
	public List<DocManage> getByBizAndSplitNo(@RequestBody DocManage docManage) throws Exception;
	/**
	 * 
	 * 方法描述： 文档类型小类的唯一编号获得押品类型信息
	 * @param docSplitNo
	 * @return
	 * @throws Exception
	 * DocTypeConfig
	 * @author 沈浩兵
	 * @date 2017-10-12 上午10:56:10
	 */
	@RequestMapping(value = "/docInterface/getDocTypeConfig")
	public DocTypeConfig getDocTypeConfig(@RequestBody String docSplitNo) throws Exception;

	/**
	 * 
	 * 方法描述： 获取客户视角要件信息（直接读取的后台配置）
	 * @param scNo
	 * @param string
	 * @return
	 * @throws Exception
	 * JSONArray
	 * @author zhs
	 * @date 2017-10-25 下午5:40:50
	 */
	@RequestMapping(value = "/docInterface/getCusDocDisPlay")
	public JSONArray getCusDocDisPlay(@RequestParam("scNo") String scNo,@RequestParam("cusNo")  String cusNo,@RequestParam("relNo") String relNo,@RequestParam("cusType") String cusType) throws Exception;
	/**
	 * 
	 * 方法描述： 业务阶段验证要件必填
	 * @param relNo
	 * @param scNo
	 * @return
	 * @throws ServiceException
	 * List<String>
	 * @author YaoWenHao
	 * @date 2017-12-18 下午4:42:17
	 */
	@RequestMapping(value = "/docInterface/valiWkfDocIsUp")
	public List<String> valiWkfDocIsUp(@RequestBody String relNo,@RequestParam("scNo")  String scNo) throws ServiceException;
	
	@RequestMapping(value = "/docInterface/deleteFileForPad")
	public void deleteFileForPad(@RequestBody DocManage docManage)throws ServiceException;

	@RequestMapping(value = "/docInterface/insertDocBizScnConfig")
	public void insertDocBizScnConfig(@RequestBody DocBizSceConfig docBizSceConfig)throws ServiceException;

	@RequestMapping(value = "/docInterface/insertDocOfflineCollect")
	public void insertDocOfflineCollect(@RequestBody Map<String, Object> map) throws Exception;
}
