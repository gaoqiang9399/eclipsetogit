package app.component.archives.entity;

import java.util.List;
import java.util.Map;

import app.component.model.entity.MfTemplateBizConfig;

/**
 * 查询要归档的文件或归档操作结果信息类
 * @author yudongwei@mftcc.cn
 *
 */
public class ArchiveResult {
	
	/** 归档主信息 */
	private ArchiveInfoMain archiveInfoMain;
	/** 查询要归档的文件/归档成功的归档明细信息集合 */
	private List<ArchiveInfoDetail> successList;
	/** 查询要归档的文件/归档失败的归档明细信息和错误信息集合 */
	private Map<ArchiveInfoDetail, String> failedMap;
	
	/** 查询要归档的电子模板文件/归档成功的电子模板文件集合 */
	private List<MfTemplateBizConfig> successTemplateList;
	/** 查询要归档的电子模板文件/归档失败的电子模板文件和错误信息集合 */
	private Map<MfTemplateBizConfig, String> failedTemplateMap;
	
	/**
	 * 返回 归档主信息
	 * 
	 * @return archiveInfoMain 归档主信息
	 */
	public ArchiveInfoMain getArchiveInfoMain() {
		return archiveInfoMain;
	}
	/**
	 * 设置 归档主信息
	 * 
	 * @param archiveInfoMain
	 *       归档主信息
	 */
	public void setArchiveInfoMain(ArchiveInfoMain archiveInfoMain) {
		this.archiveInfoMain = archiveInfoMain;
	}
	/**
	 * 返回 查询要归档的文件/归档成功的归档明细信息集合
	 * 
	 * @return successList 查询要归档的文件/归档成功的归档明细信息集合
	 */
	public List<ArchiveInfoDetail> getSuccessList() {
		return successList;
	}
	/**
	 * 设置 查询要归档的文件/归档成功的归档明细信息集合
	 * 
	 * @param successList
	 *       查询要归档的文件/归档成功的归档明细信息集合
	 */
	public void setSuccessList(List<ArchiveInfoDetail> successList) {
		this.successList = successList;
	}
	/**
	 * 返回 查询要归档的文件/归档失败的归档明细信息和错误信息集合
	 * 
	 * @return failedMap 查询要归档的文件/归档失败的归档明细信息和错误信息集合
	 */
	public Map<ArchiveInfoDetail, String> getFailedMap() {
		return failedMap;
	}
	/**
	 * 设置 查询要归档的文件/归档失败的归档明细信息和错误信息集合
	 * 
	 * @param failedMap
	 *       查询要归档的文件/归档失败的归档明细信息和错误信息集合
	 */
	public void setFailedMap(Map<ArchiveInfoDetail, String> failedMap) {
		this.failedMap = failedMap;
	}
	/**
	 * 返回 查询要归档的电子模板文件/归档成功的电子模板文件集合
	 * 
	 * @return successTemplateList 查询要归档的电子模板文件/归档成功的电子模板文件集合
	 */
	public List<MfTemplateBizConfig> getSuccessTemplateList() {
		return successTemplateList;
	}
	/**
	 * 设置 查询要归档的电子模板文件/归档成功的电子模板文件集合
	 * 
	 * @param successTemplateList
	 *       查询要归档的电子模板文件/归档成功的电子模板文件集合
	 */
	public void setSuccessTemplateList(List<MfTemplateBizConfig> successTemplateList) {
		this.successTemplateList = successTemplateList;
	}
	/**
	 * 返回 查询要归档的电子模板文件/归档失败的电子模板文件和错误信息集合
	 * 
	 * @return failedTemplateMap 查询要归档的电子模板文件/归档失败的电子模板文件和错误信息集合
	 */
	public Map<MfTemplateBizConfig, String> getFailedTemplateMap() {
		return failedTemplateMap;
	}
	/**
	 * 设置 查询要归档的电子模板文件/归档失败的电子模板文件和错误信息集合
	 * 
	 * @param failedTemplateMap
	 *       查询要归档的电子模板文件/归档失败的电子模板文件和错误信息集合
	 */
	public void setFailedTemplateMap(Map<MfTemplateBizConfig, String> failedTemplateMap) {
		this.failedTemplateMap = failedTemplateMap;
	}
	
}
