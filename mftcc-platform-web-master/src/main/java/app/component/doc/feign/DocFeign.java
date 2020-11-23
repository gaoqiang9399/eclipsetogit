package app.component.doc.feign;

import java.util.List;
import java.util.Map;

import app.component.archives.entity.ArchiveInfoDetail;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import app.base.ServiceException;
import app.component.cus.entity.MfCusCustomer;
import app.component.doc.entity.DocBizSceConfig;
import app.component.doc.entity.DocDimm;
import app.component.doc.entity.DocManage;
import app.component.doc.entity.DocTypeConfig;
import app.component.doc.entity.SceDocTypeRel;
import app.component.model.entity.MfTemplateBizConfig;
import app.component.nmd.entity.ParmDic;
import app.util.toolkit.Ipage;
import feign.Param;
import net.sf.json.JSONArray;

@FeignClient("mftcc-platform-fileService")
public interface DocFeign {
	@RequestMapping("/docTypeConfig/findByPage")
	Ipage findByPageDocTypeConfig(@RequestBody Ipage ipage);
	@RequestMapping("/nmdInterface/findAllParmDicByKeyName")
	List<ParmDic> findAllParmDicByKeyNameNmdInterface(@RequestBody ParmDic parmDic);
	@RequestMapping("/docTypeConfig/getList")
	List<DocTypeConfig> getListDocTypeConfig(@RequestBody DocTypeConfig docTypeConfig);
	@RequestMapping("/docTypeConfig/insert")
	DocTypeConfig insertDocTypeConfig(@RequestBody DocTypeConfig docTypeConfig);
	@RequestMapping("/docTypeConfig/update")
	DocTypeConfig updateDocTypeConfig(@RequestBody DocTypeConfig docTypeConfig);
	@RequestMapping("/docTypeConfig/delete")
	DocTypeConfig deleteDocTypeConfig(@RequestBody DocTypeConfig docTypeConfig);
	@RequestMapping("/docTypeConfig/getById")
	DocTypeConfig getByIdDocTypeConfig(@RequestBody DocTypeConfig docTypeConfigJsp);
	@RequestMapping("/docTypeConfig/deleteAndBizSce")
	void deleteAndBizSce(@RequestParam("docSplitNo")String docSplitNo);
	@RequestMapping("/docTypeConfig/checkAndCreateForm")
	Map<String, Object> checkAndCreateForm(DocTypeConfig docTypeConfig);
	
	
	@RequestMapping("/docBizSceConfig/deleteBysplitNo")
	void deleteBysplitNoDocBizSceConfig(@RequestBody DocBizSceConfig docBizSceConfig);
	@RequestMapping("/docBizSceConfig/getByDime")
	List<DocBizSceConfig> getByDimeDocBizSceConfig(@RequestBody DocBizSceConfig docBizSceConfig);
	@RequestMapping("/docBizSceConfig/update")
	void updateDocBizSceConfig(@RequestBody DocBizSceConfig docBizSceConfig);
	@RequestMapping("/docBizSceConfig/findByPage")
	Ipage findByPageDocBizSceConfig(@RequestBody Ipage ipage);
	@RequestMapping("/docBizSceConfig/insertDocs")
	Map<String, Object> insertDocs(@RequestBody List<DocBizSceConfig> docBizSceConfigList, @RequestParam("dime1") String dime1,@RequestParam("scNo") String scNo);
	@RequestMapping("/docBizSceConfig/deleteSceDocTypeRel")
	void deleteSceDocTypeRel(@RequestBody SceDocTypeRel sceDocTypeRel);
	@RequestMapping("/docBizSceConfig/insertSceDocTypeRel")
	void insertSceDocTypeRel(@RequestBody SceDocTypeRel sceDocTypeRel);
	@RequestMapping("/docBizSceConfig/insertDocBizSceConfig")
	void insertDocBizSceConfig(@RequestBody DocBizSceConfig docBizSceConfig);
	@RequestMapping("/docBizSceConfig/getById")
	DocBizSceConfig getByIdDocBizSceConfig(@RequestBody DocBizSceConfig docBizSceConfigJsp);
	@RequestMapping("/docBizSceConfig/delete")
	void deleteDocBizSceConfig(@RequestBody DocBizSceConfig docBizSceConfig);
	@RequestMapping("/docBizSceConfig/getListByDocType")
	List<DocTypeConfig> getListByDocTypeDocTypeConfig(@RequestParam("docType")String docType);
	@RequestMapping("/docBizSceConfig/getDocDimms")
	List<DocDimm> getDocDimms(@RequestBody DocDimm docDimm);
	@RequestMapping("/docBizSceConfig/getSplitDocs")
	List<DocBizSceConfig> getSplitDocs(@RequestBody DocBizSceConfig docBizSceConfig);
	@RequestMapping("/docBizSceConfig/getDocBizSecConfigInfo")
	List<Map<String, Object>> getDocBizSecConfigInfo(@RequestBody DocBizSceConfig docBizSceConfig);
	
	@RequestMapping("/sceDocTypeRel/getSceDocTypeRels")
	List<SceDocTypeRel> getSceDocTypeRels(@RequestBody SceDocTypeRel sceDocTypeRel);

	
	
	
	
	@RequestMapping("/docManage/insert")
	public DocManage insertDocManage(@RequestBody DocManage docManage);
	@RequestMapping("/docManage/insertForApp")
	public DocManage insertForAppDocManage(@RequestBody DocManage docManage);
	@RequestMapping("/docManage/delete")
	public DocManage deleteDocManage(@RequestBody DocManage docManage);
	@RequestMapping("/docManage/update")
	public DocManage updateDocManage(@RequestBody DocManage docManage);
	@RequestMapping("/docManage/getById")
	public DocManage getByIdDocManage(@RequestBody DocManage docManage);
	@RequestMapping("/docManage/getListForPackDown")
	public List<DocManage> getListForPackDownDocManage(@RequestBody DocManage docManage);
	@RequestMapping("/docManage/findByPage")
	public Ipage findByPageDocManage(@RequestBody Ipage ipage);
	/**
	 * 
	 * 方法描述： 根据业务编号删除上传的要件
	 * @param docManage
	 * @throws ServiceException
	 * void
	 * @author 沈浩兵
	 * @date 2017-10-10 下午3:40:42
	 */
	@RequestMapping("/docManage/deleteByBizNo")
	public DocManage deleteByBizNoDocManage(@RequestBody DocManage docManage);
	
	
	@RequestMapping("/docInterface/getDocDisPlayListAloneComm")
	String getDocDisPlayListAloneComm(@RequestBody Map<String, Object> dataMap);
	@RequestMapping("/docInterface/getDocDisPlayListAloneCommForList")
	String getDocDisPlayListAloneCommForList(@RequestBody Map<String, Object> dataMap);
    @RequestMapping("/docInterface/getDocDisPlayListAloneCommForUpload")
    String getDocDisPlayListAloneCommForUpload(@RequestBody Map<String, Object> dataMap);
	@RequestMapping("/docInterface/getCusDocDisPlay")
	JSONArray getCusDocDisPlay(@RequestParam("scNo")String scNo,@RequestParam("cusNo") String cusNo, @RequestParam("relNo")String relNo, @RequestParam("relsNo")String relsNo,  @RequestParam("cusType")String cusType);
	@RequestMapping("/docInterface/getCusDocDisPlayForUpload")
	JSONArray getCusDocDisPlayForUpload(@RequestParam("scNo")String scNo,@RequestParam("cusNo") String cusNo, @RequestParam("relNo")String relNo, @RequestParam("relsNo")String relNos,  @RequestParam("cusType")String cusType) throws Exception;
	@RequestMapping("/docInterface/getDocDisPlayForUpload")
	JSONArray getDocDisPlayForUpload(@RequestParam(value = "scNo" , required = false) String scNo, @RequestParam("relsNo") String relsNo);
	@RequestMapping("/docInterface/getCusDocDisPlayForList")
	public List<DocManage> getCusDocDisPlayForList(@RequestParam("scNo")String scNo,@RequestParam("cusNo") String cusNo, @RequestParam("relNo")String relNo, @RequestParam("relsNo")String relNos,  @RequestParam("cusType")String cusType) throws Exception;
	@RequestMapping("/docInterface/getCusDocDisPlayForRankList")
	public List<DocManage> getCusDocDisPlayForRankList(@RequestParam("scNo")String scNo,@RequestParam("cusNo") String cusNo, @RequestParam("relNo")String relNo, @RequestParam("relsNo")String relNos,  @RequestParam("cusType")String cusType,@RequestParam("num")String num) throws Exception;

	@RequestMapping("/docInterface/getDocDisPlay")
	JSONArray getDocDisPlay(@RequestParam("scNo") String scNo, @RequestParam("relsNo") String relsNo);

	@RequestMapping("/docInterface/getDocDisPlayForList")
	List<DocManage> getDocDisPlayForList(@RequestParam("scNo") String scNo, @RequestParam("relsNo") String relsNo);
	@RequestMapping("/docInterface/getDocDisPlayForRankList")
	List<DocManage> getDocDisPlayForRankList(@RequestParam("scNo") String scNo, @RequestParam("relsNo") String relsNo,@RequestParam("num") String num);

	@RequestMapping("/docInterface/getDocObject")
	Object getDocObject(@RequestBody Map<String, Object> parmMap);
	
	
	@RequestMapping("/mfTemplateBizConfig/getBizConfigListFilterRepeat")
	List<MfTemplateBizConfig> getBizConfigListFilterRepeat(@RequestBody MfTemplateBizConfig templateBizConfig);
	@RequestMapping("/mfTemplateBizConfig/getById")
	MfTemplateBizConfig getById(@RequestBody MfTemplateBizConfig templateBizConfig);
	
	@RequestMapping("/docUpLoad/upLoadFile") 
	String uploadFileToService(@RequestParam("relNo") String relNo, @RequestParam("docType")String docType,@RequestParam("upload") MultipartFile file,@RequestParam("docBizNo") String docBizNo,
			@RequestParam("scNo")String scNo, @RequestParam("docSplitNo")String docSplitNo,@RequestParam("cusNo") String cusNo,@Param("docManage") DocManage docManage);
	
	@RequestMapping("/docUpLoad/viewImage") 
	ResponseEntity<byte[]> viewImage(@RequestParam("docNo")String docNo,@RequestParam("docBizNo") String docBizNo);
	
	@RequestMapping("/docUpLoad/viewCompressImage") 
	ResponseEntity<byte[]> viewCompressImage(@RequestParam("docNo")String docNo,@RequestParam("docBizNo") String docBizNo);
	
	@RequestMapping("/docUpLoad/getFileDownload") 
	ResponseEntity<byte[]> getFileDownload(@RequestParam("docNo")String docNo,@RequestParam("docBizNo") String docBizNo);
	
	@RequestMapping("/docUpLoad/getFileByFilePath") 
	ResponseEntity<byte[]> getFileByFilePath(@RequestParam("filePath") String filePath);

	@RequestMapping("/docUpLoad/getZipFileDownloadForDocType")
	ResponseEntity<byte[]> getZipFileDownloadForDocType(@RequestParam("ajaxData")String ajaxData,@RequestParam("cusNo")String cusNo,@RequestParam("docType")String docType,@RequestParam("docTypeName") String docTypeName);

	/**
	 * 压缩要下载的文件
	 * @param archiveMainNo 归档编号
	 * @param archiveInfoDetailList 归档明细信息集合
	 * @return 结果
	 * @throws ServiceException
	 */
	@RequestMapping(value = "/fileUtil/compressDownloadFile")
	public boolean compressDownloadFile(@RequestParam("archiveMainNo") String archiveMainNo,
										@RequestBody List<ArchiveInfoDetail> archiveInfoDetailList)
			throws ServiceException;
	@RequestMapping(value = "/fileUtil/downloadArchiveFile")
	public ResponseEntity<byte[]>  downloadArchiveFile(@RequestParam("archiveMainNo") String archiveMainNo)
			throws ServiceException;

	@RequestMapping("/docUpLoad/getZipFileDownloadByPath")
	ResponseEntity<byte[]> getZipFileDownloadByPath(@RequestParam("filePath")String filePath,@RequestParam("fileName")String fileName);

	@RequestMapping("/mfSysCompanyMst/deleteFile")
	public Map<String, Object> deleteFile(@RequestParam("flag") String flag, @RequestParam("comNo") String comNo) throws Exception;
}
