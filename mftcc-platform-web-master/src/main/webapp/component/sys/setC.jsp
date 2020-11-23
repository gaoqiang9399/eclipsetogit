<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="app.tech.oscache.CodeUtils" %>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    String basePath1 = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort();
    String layout = "layout/view";
    String setType = request.getParameter("setType");
    String reportUrl=PropertiesUtil.getWebServiceProperty("reportURL");
    CodeUtils codeUtils = new CodeUtils();
    String reportProjectFlag=codeUtils.getSingleValByKey("REPORT_PROJECT_FLAG");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
    <title>系统设置平台</title>
    <meta charset="utf-8"/>
    <meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
    <link id="C" rel="stylesheet" type="text/css" href="${webPath}/<%=layout%>/page/css/C${skinSuffix}.css"/>
    <link rel="stylesheet" href="${webPath}/<%=layout%>/themes/iconfont/css/iconfont.css"/>
    <%-- 		<script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script> --%>
    <%-- 		<link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" /> --%>
    <script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js"></script>
    <script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
    <link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css"/>
    <link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css"/>
</head>
<script type="text/javascript">
    var setType = "<%=setType%>";

    function openProcessDesigner() {
        window.open(webPath + "/workflow/WFDLDesigner.jsp");
    }

    function openProcessDesignerNew() {
        window.open(webPath + "/tech/wkf/modelerEditor.jsp");
    }

    $(function () {
        $("body").mCustomScrollbar();
        $("#" + setType).show();
    });

    //五级分类批量入口--暂时放这
    function batchFiveclass() {
        $.ajax({
            url: webPath + "/mfPactFiveclass/batchFiveclass",
            type: 'post',
            data: '',
            async: false,
            success: function (data) {
                if (data.flag = "success") {
                    alert("批量处理完成！", 1);
                }
            }, error: function () {
                alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
            }
        });
    }

    //逾期批量入口--暂时放这
    function batchOverClass() {
        $.ajax({
            url: webPath + "/mfRepayment/batchOverClass",
            type: 'post',
            data: '',
            async: false,
            success: function (data) {
                if (data.flag = "success") {
                    alert(top.getMessage("SUCCEED_OPERATION", "逾期批量"), 3);
                }
            }, error: function () {
                alert(top.getMessage("FAILED_OPERATION", "逾期批量", 0));
            }
        });
    }


    //获取正在放款还款的业务数据
    function loanPaymentBusMapInfo() {
        top.openBigForm(webPath + "/mfRepayment/getLoanPaymentBusMapInfo", "正处理的业务", function () {
        });
    }

    //触发刷新资料完整度
    function refreshDataIntegrity() {
        $.ajax({
            url:webPath+"/mfCusCustomer/getRefreshDataIntegrity",
            type:'post',
            dataType:'json',
            success:function (data) {
                if(data.flag=="success"){
                    alert(top.getMessage("SUCCEED_OPERATION", "刷新资料完整度"), 2);
                }else {
                    alert(top.getMessage("FAILED_OPERATION", "刷新资料完整度"), 2);
                }
            }
        })
    }



    function resetProperties() {
        $.get(webPath + "/mfCacheManage/resetPropertiesAjax", function (data) {
            alert("重置结果：" + data.flag, 1);
        });
    }

    // 打开修改客户姓名页面
    function updateCustomerNameInfo() {
        top.window.openBigForm(webPath + '/mfCusMaintain/cusNameInput', '修改客户信息', function () {

        });
    }


    // 打开修改客户证件号页面页面
    function updateCustomerIdNumInfo() {
        top.window.openBigForm(webPath + '/mfCusMaintain/cusIdNumInput', '修改客户信息', function () {

        });
    }

    // 打开修改客户手机号页面页面
    function updateCustomerTelInfo() {
        top.window.openBigForm(webPath + '/mfCusMaintain/cusTelInput', '修改客户信息', function () {

        });
    }
</script>
<body style="height:calc(100% - 10px)">
<dhcc:markPoint markPointName="C"/>
<div class="opt_div">
    <div id="form" style="display:none">
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>页面设计</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/tech/dragDesginer/formIframe.jsp" target="_blank">表单设计器</a>
                </li>
                <%-- <li><i class="i i-jiantoua"></i>
                <a href="${webPath}/lease/DemoTest/demoBigForm?demoId=100001">大表单页面(bigForm)</a>
                </li> --%>
            </ul>
        </div>
        <%-- <div class="opt_divNew">
               <h2><i class="i i-wenjian4"></i>客户动态表单配置</h2>
            <ul>
                <li><i class="icon-cog"></i>
                    <a href="${webPath}/mfCusFormConfig/getAllList">客户表单配置</a>
                </li>
            </ul>
         </div>
        <div class="opt_divNew">
               <h2><i class="i i-wenjian4"></i>担保配置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfPledgeDynamicForm/getListPage">担保动态表单</a>
                </li>
            </ul>
         </div>--%>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>押品配置</h2>
            <ul>
                <!-- <li><i class="i i-jiantoua"></i>
							<a href="${webPath}/mfPledgeClass/getAllList">押品动态表单</a>
						</li> -->
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfCollateralClass/getMfCollateralConfig">押品动态表单</a>
                </li>
            </ul>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>授信配置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfDynamicForm/getListPage">授信申请动态表单</a>
                </li>
            </ul>
        </div>
    </div>
    <%--  <div id = "task" style="display:none">
         <div class="opt_divNew">
               <h2><i class="i i-wenjian4"></i>流程配置</h2>
            <ul>
                  <!-- <li><i class="i i-jiantoua"></i>
                    <a href="#" onclick="openProcessDesignerNew()">流程设计器（新）</a>
                </li> -->
                <li><i class="i i-jiantoua"></i>
                    <a href="#" onclick="openProcessDesigner()">流程设计器</a>
                </li>
                <!--<li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/wkfApprovalUser/batchInput">批量添加审批用户</a>
                </li>无法打开，报错  		-->
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/processDefinition/getListPage">流程定义管理</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/task/getListPage">流程任务管理</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/processInstance/getListPage">流程实例管理</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/wkfBusinessConfig/getListPage">工作流业务配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/wkfApprovalRole/getConfPage">审批角色/用户配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/wkfType/getListPage">流程视角配置</a>
                </li>
                <!--<li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/wkfModeRole/getListPage">审批模式表</a>
                </li> 暂未用到-->
            </ul>
         </div>
         <div class="opt_divNew">
           <h2><i class="i i-wenjian4"></i>待办业务</h2>
        <ul>
            <li><i class="i i-jiantoua"></i>
                <a href="${webPath}/wkfTask/findByPage">待办业务</a>
            </li>
        </ul>
     </div>
     </div> --%>
    <div id="model" style="display:none">
        <%-- <div class="opt_divNew">
              <h2><i class="i i-wenjian4"></i>文档配置</h2>
           <ul>
               <li><i class="icon-building"></i>
                   <a href="${webPath}/docTypeConfig/getListPage">文档类型配置</a>
               </li>
               <li><i class="icon-building"></i>
                   <a href="${webPath}/docModel/getListPage">文档模型配置</a>
               </li>
               <li><i class="i i-jiantoua"></i>
                   <a href="${webPath}/component/doc/UploadFile_test.jsp">单独上传例子</a>
               </li>
           </ul>
        </div>
        <div class="opt_divNew">
              <h2><i class="i i-wenjian4"></i>归档管理配置</h2>
           <ul>
               <li><i class="icon-building"></i>
                   <a href="${webPath}/archiveConfig/getDetailPage">归档管理配置</a>
               </li>
           </ul>
        </div> --%>
        <!-- <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>财务模型配置</h2>
                <ul>
                	<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinParm/getReportPage">财务报表配置</a>
					</li>
                	<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinForm/getListPage">财务指标管理</a>
					</li>
					 <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinModel/input">财务报表模型</a>
					</li> 
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinParm/getListPage">财务报表基础元素</a>
					</li>
					
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinValid/getListPage">平衡校验公式</a>
					</li>
             	</ul>
             </div> -->
        <!-- <div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>评级配置</h2>
	                <ul>
	                	<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/appProperty/getListPage">评级指标项配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/evalScenceConfig/getSetingListPage">评级模型配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/evalScenceConfig/getSetting">评级场景配置</a>
						</li>
						 <li><i class="i i-jiantoua"></i>
							<a href="${webPath}/evalSysAssess/getListPage">评级级别配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/evalSceneBizRel/getListPage">评级场景与业务的关系表</a>
						</li>
					</ul>
	             </div> -->
        <!--  <div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>授信配置</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/authConfig_getListPage">授信配置</a>
						</li>
						<li>
							<i class="i i-jiantoua"></i>
							<a href="${webPath}/mfCusCreditModel/getListPage">授信模型配置</a>
						</li>
					</ul>
	             </div> -->
        <%-- <div class="opt_divNew">
              <h2><i class="i i-wenjian4"></i>产品配置</h2>
           <ul>
               <li><i class="icon-building"></i>
                   <a href="${webPath}/mfSysKind/getListPage">产品设置</a>
               </li>
           </ul>
        </div>
        <div class="opt_divNew">
              <h2><i class="i i-wenjian4"></i>费用配置</h2>
           <ul>
               <li><i class="icon-building"></i>
                   <a href="${webPath}/mfSysFeeStd/getListPage">费用设置</a>
               </li>
           </ul>
        </div>--%>
        <!-- <div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>风险管理</h2>
	                <ul>
								<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/riskItem/getListPage?pageStr=risk">风险拦截项配置</a>
								</li>
								<li><i class="i i-jiantoua"></i>
									<a href="${webPath}/riskPrevent/getListPage?pageStr=risk">风险拦截模型配置</a>
								</li>
								<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/riskItem/getListPage?pageStr=cus">客户准入项配置</a>
								</li>
								<li><i class="i i-jiantoua"></i>
									<a href="${webPath}/riskPrevent/getListPage?pageStr=cus">客户准入模型配置</a>
								</li>
								<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/riskItem/getListPage?pageStr=pro">产品准入项配置</a>
								</li>
								<li><i class="i i-jiantoua"></i>
									<a href="${webPath}/riskPrevent/getListPage?pageStr=pro">产品准入模型配置</a>
								</li>
					</ul>
             	</div> -->
        <!-- 租后检查 begin -->
        <dhcc:pmsTag pmsId="set-loan-check-conf">

            <div class="opt_divNew">
                <h2><i class="i i-wenjian4"></i>贷后检查设置</h2>
                <ul>

                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/mfExamineTemplateConfig/getListPage">检查模板配置</a>
                    </li>
                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/mfExamRemindConfig/getListPage">检查提醒配置</a>
                    </li>
                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/mfExamIndex/getListPage">检查指标配置</a>
                    </li>
                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/mfExamRiskConfig/getListPage">风险模型配置</a>
                    </li>
                    <!--  <li><i class="i i-jiantoua"></i>
							<a href="${webPath}/llcTaskConf/getStatsPage">检查模板配置old</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/llcModelConf/getAllListPage">检查模版配置</a>
						</li>
						 <li><i class="i i-jiantoua"></i>
							<a href="${webPath}/llcTaskBase/getListPage">人工检查任务</a>
						</li> -->
                </ul>
            </div>
        </dhcc:pmsTag>
        <!-- 租后检查end -->
        <%-- <dhcc:pmsTag pmsId="set-loan-five-class-conf">
            <div class="opt_divNew">
                  <h2><i class="i i-wenjian4"></i>五级分类配置</h2>
               <ul>
                   <li><i class="i i-jiantoua"></i>
                       <a href="${webPath}/mfFiveclassModel/getListPage">五级分类模型配置</a>
                   </li>
                   <li><i class="i i-jiantoua"></i>
                       <a href="javascript:void(0);" onclick="batchFiveclass()">五级分类批量执行</a>
                   </li>
               </ul>
           </div>
        </dhcc:pmsTag>
        <div class="opt_divNew">
              <h2><i class="i i-wenjian4"></i>贷后预警</h2>
           <ul>
               <li><i class="i i-jiantoua"></i>
                   <a href="${webPath}/pliWarning/getListPage">贷后检查预警</a>
               </li>
               <li><i class="i i-jiantoua"></i>
                   <a href="${webPath}/cuslendWarning/getListPage">客户还款预警</a>
               </li>
           </ul>
       </div> --%>
    </div>
    <div id="clean" style="display:none">
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>数据清理</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfCusCustomer/getCleanListPage">客户数据清理</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfBusPact/getCleanListPage">业务数据清理</a>
                </li>
            </ul>
        </div>
    </div>
    <div id="argu" style="display:none">
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>参数配置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/busCtlParmMang/getListPage">业务控制参数表</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/busCtlParmMang/getListPageBus">业务控制参数表(业务人员)</a>
                </li>
                <%--无对应前端页面
                <li><i class="icon-building"></i>
                    <a href="${webPath}/mfWarningParm/getListPage">预警天数设置</a>
                </li>--%>
                <li><i class="icon-building"></i> -->
                    <a href="${webPath}/mfSysParmCtrl/getMainPage">参数化设置</a>
                </li>
            </ul>
        </div>
    </div>
    <div id="template" style="display:none">
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>模板管理</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfSysTemplate/getListPage">pageoffice设计模板</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfTemplateModel/getListPage">模板模型配置</a>
                </li>
            </ul>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>消息模板</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysMsgConfig/getListPage">消息模板配置</a>
                </li>
            </ul>
        </div>
    </div>
    <div id="orga" style="display:none">
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>组织机构</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysUser/getListPage">用户列表</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysOrg/listSysOrg">机构管理</a>
                </li>
            </ul>
        </div>
    </div>
    <div id="limit" style="display:none">
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>权限配置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysRole/getAll">角色权限配置</a>
                </li>
            </ul>
        </div>
    </div>
    <div id="base" style="display:none">
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>文档配置</h2>
            <ul>
                <li><i class="icon-building"></i>
                    <a href="${webPath}/docTypeConfig/getListPage">文档类型配置</a>
                </li>
                <!-- 						<li><i class="icon-building"></i> -->
                <!-- 							<a href="${webPath}/docModel/getListPage">文档模型配置</a> -->
                <!-- 						</li> -->
                <%-- <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/component/doc/UploadFile_test.jsp">单独上传例子</a>
                </li> --%>
            </ul>
        </div>
        <dhcc:pmsTag pmsId="set-sys-base-template">
            <div class="opt_divNew">
                <h2><i class="i i-wenjian4"></i>模板配置</h2>
                <ul>
                    <li><i class="icon-building"></i>
                        <a href="${webPath}/mfSysTemplate/templateConfig">基础模板配置</a>
                    </li>
                </ul>
            </div>
        </dhcc:pmsTag>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>归档管理配置</h2>
            <ul>
                <li><i class="icon-building"></i>
                    <a href="${webPath}/archiveConfig/getListPage">归档管理配置</a>
                </li>
            </ul>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>风险管理</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/riskItem/getListPage?pageStr=risk">风险拦截项配置</a>
                </li>
                <!-- 								<li><i class="i i-jiantoua"></i> -->
                <!-- 									<a href="${webPath}/riskPrevent/getListPage?pageStr=risk">风险拦截模型配置</a> -->
                <!-- 								</li> -->
                <%--<li><i class="i i-jiantoua"></i>--%>
                    <%--<a href="${webPath}/riskItem/getListPage?pageStr=cus">客户准入项配置</a>--%>
                <%--</li>--%>
                <!-- 								<li><i class="i i-jiantoua"></i> -->
                <!-- 									<a href="${webPath}/riskPrevent/getListPage?pageStr=cus">客户准入模型配置</a> -->
                <!-- 								</li> -->
              <%--  <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/riskItem/getListPage?pageStr=pro">产品准入项配置</a>
                </li>--%>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/riskItem/getListPage?pageStr=type">客户分类入项配置</a>
                </li>
                <!-- 								<li><i class="i i-jiantoua"></i> -->
                <!-- 									<a href="${webPath}/riskPrevent/getListPage?pageStr=pro">产品准入模型配置</a> -->
                <!-- 								</li> -->
            </ul>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>押品类别管理</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfCollateralClass/getMfCollateralConfig?entranceType=assets&classFirstNo=A,B,C,D">押品类别管理</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfMsgPledge/getListPage">押品预警配置</a>
                </li>
            </ul>
        </div>
        <dhcc:pmsTag pmsId="set-receivables-manage-conf">
            <div class="opt_divNew">
                <h2><i class="i i-wenjian4"></i>应收账款管理</h2>
                <ul>
                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/mfBusCollateral/getEntrance?type=7">账款管理</a>
                    </li>
                </ul>
            </div>
        </dhcc:pmsTag>
        <dhcc:pmsTag pmsId="set-params-setting-conf">
            <div class="opt_divNew">
                <h2><i class="i i-wenjian4"></i>参数设置</h2>
                <ul>
                    <%--<li><i class="icon-building"></i>
                        <a href="${webPath}/mfSysRateExchange/getListPage">汇率设置</a>
                    </li>--%>
                    <li><i class="icon-building"></i>
                        <a href="${webPath}/mfSysRateBase/getListPage">基准利率查看</a>
                    </li>
                    <li><i class="icon-building"></i>
                        <a href="${webPath}/sysLegalHolidayRecord/getListPage">节假日列表</a>
                    </li>
                    <li><i class="icon-building"></i>
                        <a href="${webPath}/sysLegalHolidayRecord/goCalendar">节假日日历</a>
                    </li>
                    <dhcc:pmsTag pmsId="set-file-inspect-conf">
                        <li><i class="icon-building"></i>
                            <a href="${webPath}/mfBusCensorFile/getListPage">文件审查项</a>
                        </li>
                    </dhcc:pmsTag>
                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/wkfApprovalRole/getConfPage">审批角色/用户配置</a>
                    </li>
                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/cuslendWarning/getMsgModleListPage">消息模板配置</a>
                    </li>
                    <dhcc:pmsTag pmsId="set-threeport-params-conf">
                        <li><i class="i i-jiantoua"></i>
                            <a href="${webPath}/mfThirdApiAdapt/getSettingPage">三方接口参数配置</a>
                        </li>
                    </dhcc:pmsTag>
                    <dhcc:pmsTag pmsId="dealers_rate">
                        <li><i class="i i-jiantoua"></i>
                            <a href="${webPath}/mfBusDealersRate/getListPage">经销商利率维护</a>
                        </li>
                    </dhcc:pmsTag>
                </ul>
            </div>
        </dhcc:pmsTag>

        <%-- <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>表单设计器</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/tech/dragDesginerClient/formIframe.jsp">表单设计器</a>
                </li>
            </ul>
         </div> --%>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>系统logo设置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfSysCompanyMst/getById">系统logo设置</a>
                </li>
            </ul>
        </div>
        <div class="opt_divNew" id="ztshow" style="display:none">
            <h2><i class="i i-wenjian4"></i>财务帐套管理</h2>
            <ul>
                <li><i class="icon-building"></i>
                    <a href="${webPath}/cwZtBooks/getListPage">帐套管理</a>
                </li>

            </ul>
        </div>
        <dhcc:pmsTag pmsId="set-sys-base-report">
            <div class="opt_divNew">
                <h2><i class="i i-wenjian4"></i>报表配置</h2>
                <ul>
                    <li><i class="icon-building"></i>
                        <a href="${webPath}/mfReportQueryConditionConfigSet/getListPage">报表查询条件配置</a>
                    </li>
                </ul>
            </div>
        </dhcc:pmsTag>
        <dhcc:pmsTag pmsId="set-loan-five-class-conf">
            <div class="opt_divNew">
                <h2><i class="i i-wenjian4"></i>五级分类配置</h2>
                <ul>
                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/mfFiveclassModel/getListPage">五级分类模型配置</a>
                    </li>
                </ul>
            </div>
        </dhcc:pmsTag>

        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>保后预警</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/pliWarning/getListPage">保后跟踪预警</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/cuslendWarning/getListPage">客户还款预警</a>
                </li>
                <%--催收预警提醒--%>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/cuslendWarning/getRecallWarningListPage">催收预警提醒</a>
                </li>
            </ul>
        </div>


        <dhcc:pmsTag pmsId="set-facility-manage-conf">
            <div class="opt_divNew">
                <h2><i class="i i-wenjian4"></i>设备管理</h2>
                <ul>
                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/mfAppDevice/getListPage">备案管理</a>
                    </li>
                </ul>
            </div>
        </dhcc:pmsTag>


        <dhcc:pmsTag pmsId="cus-info-maintain">
            <div class="opt_divNew">
                <h2><i class="i i-wenjian4"></i>客户信息维护</h2>
                <ul>
                    <li><i class="i i-jiantoua"></i>
                        <a class="config-font" href="#" onclick="updateCustomerNameInfo();">修改客户姓名</a>
                    </li>
                    <li><i class="i i-jiantoua"></i>
                        <a class="config-font" href="#" onclick="updateCustomerIdNumInfo();">修改客户证件号码</a>
                    </li>
                    <li><i class="i i-jiantoua"></i>
                        <a class="config-font" href="#" onclick="updateCustomerTelInfo();">修改客户手机号码</a>
                    </li>
                    <li><i class="i i-jiantoua"></i>
                        <a class="config-font" href="#" onclick="refreshDataIntegrity();">刷新资料完整度</a>
                    </li>

                </ul>
            </div>
        </dhcc:pmsTag>
    </div>

    <div id="developer" style="display:none">
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>基础配置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/component/pms/pms_entrance.jsp">入口配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/component/pms/pms_viewpoint.jsp">视角配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/component/pms/pms_biz_opt.jsp">功能权限定义</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/pmsDataRang/findByPage">数据权限定义</a>
                </li>
                <%-- <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/secEntrance/showConfig">安全审计配置</a>
                </li>--%>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysGlobalSearch/getListPage">全局搜索配置</a>
                </li>
               <%-- <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysDescTemp/getListPage">业务描述模板配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/lease/desginModeler">布局设计器</a>
                </li>--%>
              <%--  <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/component/pms/pms_GetFilterSetting.jsp" target="_blank">筛选自定义生成</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/pmsFilterRel/getListPage">自定义筛选关系</a>
                </li>--%>
               <%-- <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/tech/layoutDesginer/layoutDesgin_dis.jsp">模块权限控制</a>
                </li>--%>
               <%-- <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysBtnDef/getListTree">按钮定义配置</a>
                </li>--%>
               <%-- <li><i class="i i-jiantoua"></i>
                    <a href="javascript:void(0);" onclick="resetProperties();">重置Properties文件缓存(系统缓存管理)</a>
                </li>--%>
            </ul>
           <%-- <h2><i class="i i-wenjian4"></i>关联关系图</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/component/cus/relation/relation.jsp?jumpLink=/component/cus/relation/json/data2.json&keyNo=a474b83164dc5917cc93d8ef41beffc0">客户关联关系</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfCusFormConfig/getMfCusConfig?fromPage=devView">客户表单配置</a>
                </li>
            </ul>--%>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>数据字典配置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/parmKey/getListPage">数据字典项配置</a>
                </li>
            </ul>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>表单设计器</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/tech/dragDesginer/formIframe.jsp" target="_blank">表单设计器</a>
                </li>
            </ul>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>参数设置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/busCtlParmMang/getListPage">业务控制参数表</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/busCtlParmMang/getListPageBus">业务控制参数表(业务人员)</a>
                </li>
                <%--无对应前端页面
                <li><i class="icon-building"></i>
                    <a href="${webPath}/mfWarningParm/getListPage">预警天数设置</a>
                </li>--%>
                <li><i class="icon-building"></i>
                    <a href="${webPath}/mfMsgVar/getListPage">消息模板参数设置</a>
                </li>
                <%--无对应方法
                 <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfMsgListener/getListPage">监听消息配置</a>
                </li>  --%>
            </ul>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>流程配置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="#" onclick="openProcessDesignerNew()">流程设计器（新）</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="#" onclick="openProcessDesigner()">流程设计器</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/processDefinition/getListPage">流程定义管理</a>
                </li>
               <%-- <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/task/getListPage">流程任务管理</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/processInstance/getListPage">流程实例管理</a>
                </li>--%>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/wkfBusinessConfig/getListPage">工作流业务配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/wkfApprovalRole/getConfPage">审批角色/用户配置</a>
                </li>
                <%--<li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/wkfType/getListPage">流程视角配置</a>
                </li>--%>
            </ul>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>演示demo</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="javascript:void(0);" onclick="batchOverClass()">逾期批量执行</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="javascript:void(0);" onclick="loanPaymentBusMapInfo()">解除正在执行的放款还款业务</a>
                </li>
            </ul>
        </div>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>模板管理</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfTemplateTagBase/getListPage">标签配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfSysTemplate/getListPage">pageoffice设计模板</a>
                </li>
                <%--<li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/freeMarkerDownloadModel/getListPage">freemarker标签配置</a>
                </li>--%>
            </ul>
        </div>
        <%--<dhcc:pmsTag pmsId="set-cusandbus-clean-conf">--%>
        <%--</dhcc:pmsTag>--%>
        <div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>客户与业务清理</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfDataClean/getListPage">客户与业务清理</a>
                </li>
                <dhcc:pmsTag pmsId="set-passoword-reset-conf">
                    <li><i class="i i-jiantoua"></i>
                        <a href="${webPath}/mfKingsoftPaymentPassword/getListPage">客户交易密码重置</a>
                    </li>
                </dhcc:pmsTag>

                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfCusInfoDw/getListPage">客户信息下载</a>
                </li>

            </ul>
        </div>
        <%--<div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>操作员权限管理</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysOrg/listSysOrg?opNoType=2">渠道操作员权限配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysOrg/listSysOrg?opNoType=3">资金机构操作员权限配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysOrg/listSysOrg?opNoType=4">仓储机构操作员权限配置</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/sysOrg/listSysOrg?opNoType=5">核心企业操作员权限配置</a>
                </li>
            </ul>
        </div>--%>
        <%--<div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>加密字段配置</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/mfEncryptFields/getEntListPage">加密字段配置</a>
                </li>
            </ul>
        </div>--%>
        <%--<div class="opt_divNew">
            <h2><i class="i i-wenjian4"></i>报表</h2>
            <ul>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/component/report/MfReportEntranceOpen.jsp?&reportId=report-ygyh-daybeginDayendSummary&reporttype=D&reportUrl=<%=reportUrl%>&reportProjectFlag=<%=reportProjectFlag%>&uid=7f5e93d40859f2db71c5f12b422ce57c">日始日终核验报表</a>
                </li>
                <li><i class="i i-jiantoua"></i>
                    <a href="${webPath}/component/report/MfReportEntranceOpen.jsp?&reportId=report-ygyh-loanFinancialDaily&reporttype=D&reportUrl=<%=reportUrl%>&reportProjectFlag=<%=reportProjectFlag%>&uid=f5ccb184d7cc367ba000c38dfa01c3f1">贷款账务日计表</a>
                </li>
            </ul>
        </div>--%>
    </div>

</div>
<script type="text/javascript">
    //window.parent.getHelp();
    // 		getHelp();
</script>
</body>

</html>
