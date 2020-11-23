package app.component.archives.entity;

public class Constant {
	
	public static String ARCHIVE_DIR_NAME = "archive";
	
	public static String BACKUP_DIR_NAME = "backup";
	
	public static String DELETE_DIR_NAME = "delete";
	
	public static String MERGE_DIR_NAME = "merge";
	
	public static String DOWNLOAD_DIR_NAME = "download";
	
	public static String PREVIEW_DIR_NAME = "preview";
	
	/** 工程下媒体文件存放路径 */
	public static String MEDIA_CACHE_PATH = "component/archives/cache";
	
	/** 编号前缀：AM：归档主信息 */
	public static String PREFIX_ARCHIVE_MAIN_NO = "AM";
	/** 编号前缀：AD：归档明细信息 */
	public static String PREFIX_ARCHIVE_DETAIL_NO = "AD";
	/** 编号前缀：ADL：归档文件操作日志 */
	public static String PREFIX_ARCHIVE_DETAIL_LOG_NO = "ADL";
	/** 编号前缀：AB：归档文件备份 */
	public static String PREFIX_ARCHIVE_BACKUP_NO = "AB";
	/** 编号前缀：AL：归档文件借阅 */
	public static String PREFIX_ARCHIVE_LEND_NO = "AL";
	/** 编号前缀：AH：归档文件合并 */
	public static String PREFIX_ARCHIVE_MERGE_NO = "AH";
	/** 编号前缀：AA：归档文件补充 */
	public static String PREFIX_ARCHIVE_ADD_NO = "AA";
	
	/** 档案状态：01：归档 */
	public static String ARCHIVE_STATUS_NORMAL = "01";
	/** 档案状态：02：封档 */
	public static String ARCHIVE_STATUS_SEAL = "02";
	
	/** 归档阶段：01：合同签订 */
	public static String ARCHIVE_PACT_STATUS_SIGN = "01";
	/** 归档阶段：02：合同完结 */
	public static String ARCHIVE_PACT_STATUS_OVER = "02";
	
	/** 归档文件状态：01：正常 */
	public static String ARCHIVE_DOC_STATUS_NORMAL = "01";
	/** 归档文件状态：02：删除 */
	public static String ARCHIVE_DOC_STATUS_DELETED = "02";
	
	/** 是否：1：是 */
	public static String YES = "1";
	/** 是否：0：否 */
	public static String NO = "0";
	
	/** 归档文件来源：01：业务归档 */
	public static String ARCHIVE_DOC_SOURCE_BUSINESS = "01";
	/** 归档文件来源：02：归档补充 */
	public static String ARCHIVE_DOC_SOURCE_ARCHIVES = "02";
	
	/** 归档文件属性：01：文档 */
	public static String ARCHIVE_DOC_ATTRIBUTE_DOCUMENT = "01";
	/** 归档文件属性：02：图片 */
	public static String ARCHIVE_DOC_ATTRIBUTE_PICTURE = "02";
	/** 归档文件属性：03：音频 */
	public static String ARCHIVE_DOC_ATTRIBUTE_AUDIO = "03";
	/** 归档文件属性：04：视频 */
	public static String ARCHIVE_DOC_ATTRIBUTE_VIDEO = "04";
	/** 归档文件属性：05：压缩 */
	public static String ARCHIVE_DOC_ATTRIBUTE_COMPRESS = "05";
	/** 归档文件属性：09：其它 */
	public static String ARCHIVE_DOC_ATTRIBUTE_OTHER = "09";
	
	/** 归档操作类型：01：归档 */
	public static String ARCHIVE_OPERATION_TYPE_ARCHIVE = "01";
	/** 归档操作类型：02：新增 */
	public static String ARCHIVE_OPERATION_TYPE_ADD = "02";
	/** 归档操作类型：03：删除 */
	public static String ARCHIVE_OPERATION_TYPE_DELETE = "03";
	/** 归档操作类型：04：恢复 */
	public static String ARCHIVE_OPERATION_TYPE_RECOVER = "04";
	/** 归档操作类型：05：借阅 */
	public static String ARCHIVE_OPERATION_TYPE_LEND = "05";
	/** 归档操作类型：06：归还 */
	public static String ARCHIVE_OPERATION_TYPE_RETURN = "06";
	/** 归档操作类型：07：合并 */
	public static String ARCHIVE_OPERATION_TYPE_MERGE = "07";
	/** 归档操作类型：08：备份 */
	public static String ARCHIVE_OPERATION_TYPE_BACKUP = "08";
	/** 归档操作类型：09：封档 */
	public static String ARCHIVE_OPERATION_TYPE_SEAL = "09";

}
