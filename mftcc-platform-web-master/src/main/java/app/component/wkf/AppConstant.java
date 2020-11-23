package app.component.wkf;

public class AppConstant {
	/**user key of request session**/
	public static final String SESSION_USER_KEY = "AppUserSession";
	/**default page size of application**/
	public static final int DEFAULT_PAGE_SIZE = 10;
	
	/**whether check session**/
	public static final boolean CHECKSESSION = true;
	/**do not permit use workflow while application user session timeout **/
	public static final String TIMEOUT_PAGE = "/timeout.jsp";
	
	public static final String PARAM_PROCESSDEF_NAME = "processDefName";
	public static final String PARAM_PROCESSDEF_KEY = "processDefKey";
	
	public static final String END_STS = "ended";
	public static final String PARAM_MSG = "AppMsg";
	/**意见类型 START**/
	public static final String OPINION_TYPE_ARREE="1";//同意
	public static final String OPINION_TYPE_REFUSE="2";//否决
	public static final String OPINION_TYPE_ROLLBACK="3";//退回
	public static final String OPINION_TYPE_RESTART="4";//发回重审
	public static final String OPINION_TYPE_DISARREE="5";//建议否决
	public static final String OPINION_TYPE_DEALER="6";//经销商补充资料
	public static final String OPINION_TYPE_DVISITOR="7";//指派家访
	public static final String OPINION_TYPE_VISITOR="8";//家访完成
	public static final String OPINION_TYPE_DEALEREND="9";//经销商补充资料完成
	public static final String OPINION_TYPE_SUGGESTPASS="10";//建议批准
	public static final String OPINION_TYPE_SUGGESTREFUSE="11";//建议拒绝
	public static final String OPINION_TYPE_BACKUPDATE="6.1";//驳回，补充资料
	public static final String OPINION_TYPE_CANCEL="6.2";//取消，补充资料
	public static final String OPINION_TYPE_REFUSEBLACK="2.1";//拒绝并加入黑名单
	public static final String OPINION_TYPE_ARREE_CONDITION="1.1";//附条件同意
	public static final String OPINION_TYPE_CONTINUE="1.2";//续议
	public static final String OPINION_TYPE_RETCONTINUE="12";//复议
	/**意见类型 END**/
	public static final String PASS_TYPE_SUPPLEMENT="pass_supplement";//经销商补充资料
	public static final String PASS_TYPE_HOMEVISITOR="pass_homevisit";//指派家访
	public static final String PASS_TYPE_VISITOREND="pass_homeend";//家访完成
	public static final String PASS_TYPE_SUPPLEMENTEND="pass_supplementend";//经销商补充资料完成
	public static final String PASS_TYPE_SUGGESTPASS="pass_suggestpass";//建议批准
	public static final String PASS_TYPE_REFUSEPASS="pass_suggestrefuse";//建议拒绝
	
	

	/**指派类型 START**/
//	public static final String DESIGNATE_TYPE_MANUAL="MANUAL";//手动指派下一个任务审批人员
	public static final String DESIGNATE_TYPE_AUTO_APP="AUTO_APP";//业务自动指派下一个任务审批人员
	public static final String DESIGNATE_TYPE_AUTO_WF="AUTO_WF";//流程自动指派下一个任务审批人员(选上一个任务审批人)
	public static final String DESIGNATE_TYPE_AUTO_WFG="AUTO_WFG";//流程自动指派下一个任务审批人员(根据角色号查出一组人)
	public static final String DESIGNATE_TYPE_AUTO_WFE="AUTO_APPE";//业务自动指派下一个任务审批人员(展期)
	/**指派类型 END**/
	public static final String FIRST_OP_NO="firstOpNo";
	/**任务提交类型 START**/
//	public static final String COMMIT_TYPE_AUTO="AUTO";
//	public static final String COMMIT_TYPE_MANUAL="MANUAL";
	/**任务提交类型 END**/
	
	/**默认审批意见 START**/
	public static final String DEFAULT_APPROVAL_OPINOIN="同意";
	/**默认审批意见 END**/
	
	/**按任务名称查询流程任务 START**/
	public static final String DEFAULT_QUERY_TASK_NAME="node5178763844";
	
	public static final String VISITOR_TASK_NAME="flow5429691672_visitor";//指派家访
	public static final String VISITOREND_TASK_NAME="flow3314341678_visitorEnd";//家访完成
	public static final String DEALOR_TASK_NAME="flow1153584224_dealor";//经销商补充资料
	public static final String REfUSE_TASK_NAME="flow9763522618_refuse";//建议拒绝
	
	
	/**
	 * 流程图里的人工分配下一环节执行人员--禁止
	 */
	public static final String IS_ASSIGN_NEXT_FORBID="0";
	
	/**
	 * 流程图里的人工分配下一环节执行人员--可选
	 */
	public static final String IS_ASSIGN_NEXT_OPTION="1";
	
	/**
	 * 流程图里的人工分配下一环节执行人员--必须
	 */
	public static final String IS_ASSIGN_NEXT_MUST="2";
	
	/**按任务名称查询流程任务 END**/
	/**
	 * 乘用车初审随机选人角色
	 */
	public static final String PAS_FST_CHECK_APPROVER="CYC002";
	/**
	 * 乘用车放款初审随机选人角色
	 */
	public static final String PAS_DUE_CHECK_APPROVER="CYC006";
	/**
	 * 商用车初审随机选人角色
	 */
	public static final String COM_FST_CHECK_APPROVER="SYC002";
	/**
	 * 商用车放款初审随机选人角色
	 */
	public static final String COM_DUE_CHECK_APPROVER="SYC006";
	/**
	 * 商用车厂商初审随机选人角色
	 */
	public static final String COM_SUP_FST_CHECK_APPROVER="SYCC002";
	/**
	 * 商用车厂商放款初审随机选人角色
	 */
	public static final String COM_SUP_DUE_CHECK_APPROVER="SYCC006";
	/**
	 * 展期初审随机选人角色
	 */
	public static final String COM_EXTEND_CHECK_APPROVER="ZQ001";//展期
	/**
	 * 流程key start
	 */
	/**
	 * 乘用车
	 */
	public static final String PAS_WKF_KEY="cyc201304041050";
	/**
	 * 商用车 供货商
	 */
	public static final String COM_SUP_WKF_KEY="syc201304041051_cs";
	/**
	 * 商用车
	 */
	public static final String COM_WKF_KEY="syc201304041051";
	/**
	 * 履约险
	 */
	public static final String COM_WKF_KEY_LYX="syclyx20150330_2015330113121833";
	/**
	 * 展期流程
	 */
	public static final String COM_WKF_EXTEND_KEY="extend_201341110837780";
	/**
	 * 授信流程
	 */
	public static final String COM_WKF_AUTH_KEY="authApply_201341013350295";
	
	/**
	 * 评级个人流程编号
	 */
	public static final String  EVAL_PER_WKF_NO ="evalApp_2016915223422973";
	/**
	 * 评级对公流程编号
	 */
	public static final String  EVAL_CORP_WKF_NO ="evalApp_2016915223422973";
	
	/**
	 * 流程key end
	 */
	/**
	 * 乘用车
	 */
	public static final String PAS_WKF_TRANSITION="flow7722673133";
	/**
	 * 商用车 供货商
	 */
	public static final String COM_SUP_WKF_TRANSITION="flow9795394886";
	/**
	 * 商用车
	 */
	public static final String COM_WKF_TRANSITION="flow1079539488";
	/**
	 * 退回业务变更
	 */
	public static final String BACK_WKF_TRANSITION="flow5761592794";
	
	/**
	 * 厂商授信
	 */
	public static final String AUTH_APP_NO_FOR_SUPPLIER = "201641610533158";
	/**
	 * 经销商授信
	 */
	public static final String AUTH_APP_NO_FOR_PROXY = "auth_proxy_201610311708362"/*"app_auth_for_proxy_2016910162111710"*/;
	/**
	 * 厂商对经销商授信
	 */
	public static final String AUTH_APP_NO_FOR_SUPPLIER_TO_PROXY = "auth_supplierToProxy_20161025164917946";
	
	/**
	 * 业务申请通用续议流程 
	 */
	public static final String BIZ_CONTINUE_WORK_FLOW = "bizContinueWorkFlow1_2016102892211139";
	
	/**
	 * 业务申请通用复议流程 
	 */
	public static final String BIZ_CONDITION_WORK_FLOW = "bizReconsiderWorkFlow_201611251751323";
	
	/**
	 * 业务申请通用条件变更流程 
	 */
	public static final String BIZ_CONDITION_CHANGE_WORK_FLOW = "bizChangeWorkFlownew_20161124161427781";
	
	/**
	 * 罚息减免申请流程
	 */
	public static final String ALP_PIR_INFO = "alp_pir_info_2016112213834504";
	
	/**
	 * 利率申请流程
	 */
	public static final String ALP_RATE_ADJ = "ALP_RATE_ADJ_2016114211142691";
	
	/**
	 * 融资管理申请无资产池
	 */
	public static final String FIN_BASE_NO_CP = "fin_base_cp_2016119141958867";
	
	/**
	 * 授信续议流程
	 */
	public static final String APP_AUTH_CONTINUE = "authContinue_20161110135925964";
	
	/**
	 * 授信复议流程
	 */
	public static final String APP_AUTH_AGAIN = "auth_again_201611261164138";
	
	/**
	 * 授信变更流程
	 */
	public static final String APP_AUTH_CHANGE = "auth_change_20161126133657262";
	
	/**
	 * 续保押金流程
	 */
	public static final String ALP_REINS_BACK = "alpReInsBack_2016121134246616";
	
}
