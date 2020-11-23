<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String layout = "layout/view";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link id="C" rel="stylesheet" type="text/css" href="${webPath}/<%=layout%>/page/css/C${skinSuffix}.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/iconfont/css/iconfont.css" />
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script>
		<link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" />
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js" ></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
	</head>
		<script type="text/javascript">
	function openProcessDesigner(){
		window.open(webPath+"/workflow/WFDLDesigner.jsp");
	}
	function openProcessDesignerNew(){
		window.open(webPath+"/tech/wkf/modelerEditor.jsp");
	}
	$(function(){
	$("body").mCustomScrollbar()
	})
	
	function openWordDoc(){
		var type= "add";
		var modelid = "dczyp";
		var filename = "dczyp.doc"
		var returnUrl = window.location.href;
		window.location.href=webPath+"/component/model/toPageOffice.jsp?cifno=cus16080815320056010&modelid="+modelid+"&filename="+filename+"&pactno=&appno=&loanNo=&traceNo=&returnUrl="+returnUrl+"&type="+type;
		}
	function detail(){
		var type= "edit";
		var modelid = "dczyp";
		var filename = "dczyp.doc"
		var returnUrl = window.location.href;
		window.location.href=webPath+"/component/model/toPageOffice.jsp?cifno=cus16080815320056010&modelid="+modelid+"&filename="+filename+"&pactno=&appno=&loanNo=&traceNo=&returnUrl="+returnUrl+"&type="+type;
	}
	function model_set(){
		var returnUrl = window.location;
		var pathFileName = "/factor/component/cus/cuspageoffice/docmodle/jzdc.doc";
		var savePath = "/factor/component/cus/cuspageoffice/docmodle/";
		var saveFileName = "jzdc.doc";
		var poCntJson = {
			pathFileName : "" + pathFileName,
			savePath : "" + savePath,
			saveFileName : saveFileName,
			fileType : 0
		};
		poCntJson.returnUrl = window.location.href;
		poCntJson.markUrl="4445152";
		poCntJson.printBtn="0";//取消打印按钮
		var poCnt = JSON.stringify(poCntJson);
		var url="/pageoffice/pageOfficeEdit&poCnt="+encodeURIComponent(poCnt);
		//var url="cusPageOffice_pageOfficeEdit?pathFileName="+pathFileName+"&savePath="+savePath+"&saveFileName="+saveFileName+"&returnUrl="+returnUrl;
		window.open(url, '_self', '');
	}
	</script>
	<body style="height:calc(100% - 10px)">
	<dhcc:markPoint markPointName="C"/>
		<div class="opt_div">
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>页面设计</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/tech/dragDesginer/formIframe.jsp"  target="_blank">表单设计器</a>
					</li>
					
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>模板管理</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mfSysTemplate/getListPage" >pageoffice设计模板</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mfTemplateModel/getListPage" >模板模型配置</a>
					</li>
				</ul>
             </div>
            
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>文档配置</h2>
                <ul>
					<li><i class="icon-building"></i>
						<a href="${webPath}/docBizSceConfig/config">上传文档配置</a>
					</li>
					<%-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/component/doc/UploadFile_test.jsp">单独上传例子</a>
					</li> --%>
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>评级配置</h2>
                <ul>
                	<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/appProperty/getListPage">评级指标项配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/evalScenceConfig/getSetingListPage">评级模型配置</a>
					</li>
					<!-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/evalScenceConfig/getSetting">评级场景配置</a>
					</li> -->
					 <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/evalSysAssess/getListPage">评级级别配置</a>
					</li>
					<!-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/evalSceneBizRel/getListPage">评级场景与业务的关系表</a>
					</li> -->
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>押品配置</h2>
                <ul>
                	<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mfPledgeDynamicForm/getListPage">押品动态表单</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mfPledgeClass/getAllList">押品清单动态表单</a>
					</li>
				</ul>
             </div>
              <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>授信配置</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/authConfig/getListPage">授信配置</a>
					</li>
				</ul>
             </div>
              <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>风险管理</h2>
                <ul>
							<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/riskItem/getListPage">风险拦截项配置</a>
							</li>
							<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/riskPrevent/getListPage">风险拦截模型配置</a>
							</li>
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>催收配置</h2>
                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/recallConfig/getListPage">催收配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/recallBase/getListPage">催收任务</a>
						</li> 
						<!-- <li><i class="i i-jiantoua"></i>
							<a href="${webPath}/recallBase/getListPageForSend">催收任务指派</a>
						</li> -->
					  	<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/recallBase/toSendList">催收任务指派</a>
						</li>
						<li><i class="icon-building"></i>
						<a href="${webPath}/mfUrgeInfoModel/getListPage">催收模板设置</a>
					</li>
					</ul>
             </div>
              <!-- 租后检查 begin -->
              <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>检查配置</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/llcTaskConf/getStatsPage">检查配置</a>
					</li>
					 
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/llcModelConf/getAllListPage">检查模版配置</a>
					</li>
					 <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/llcTaskBase/getListPage">人工检查任务</a>
					</li>
				</ul>
             </div>
             <!-- 租后检查end -->
             
            
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
           		<h2><i class="i i-wenjian4"></i>参数配置</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysUser/getListPage">用户列表</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysOrg/listSysOrg">机构管理</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/busCtlParmMang/getListPage">业务控制参数表</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/busCtlParmMang/getListPageBus">业务控制参数表(业务人员)</a>
					</li>
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>系统设置</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/component/pms/pms_entrance.jsp">入口配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/component/pms/pms_viewpoint.jsp">视角配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/pmsDataRang/findByPage">数据权限定义</a>
					</li>
					<li><i class="i i-jiantoua"></i>
					<a href="${webPath}/sysRole/getAll" >角色权限配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sarmKey/getListPage">数据字典项配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysDescTemp/getListPage">业务描述模板配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysMsgConfig/getListPage">消息模板配置</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/sysGlobalSearch/getListPage">全局搜索配置</a>
					</li>
					<li><i class="icon-cog"></i>
						<a href="${webPath}/mfCusFormConfig/getAllList">客户表单配置</a>
					</li>
					<!-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mfPledgeClass/getAllList">押品动态表单</a>
					</li> -->
					<li><i class="icon-building"></i>
						<a href="${webPath}/mfSysKind/getListPage">产品设置</a>
					</li>
					<li><i class="icon-building"></i>
						<a href="${webPath}/mfSysFeeStd/getListPage">费用设置</a>
					</li>
					<li><i class="icon-building"></i>
						<a href="${webPath}/mfSysParmCtrl/getMainPage">参数化设置</a>
					</li>
					<%--无对应前端页面
					<li><i class="icon-building"></i>
						<a href="${webPath}/mfWarningParm/getListPage">预警天数设置</a>
					</li>--%>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/workCalendar/fullCalendarmonthlist?wait=0">日程填报</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mfCusRelationType/getById">关联关系类型设置</a>
					</li>
<!-- 					<li><i class="icon-building"></i> -->
<%-- 						<a href="${webPath}/MfModTableConf/getListPage">修改历史字段设置</a> --%>
<!-- 					</li> -->
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>财务模型配置</h2>
                <ul>
                	<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinParm/getReportPage">财务报表配置</a>
					</li>
                	<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinForm/getListPage">财务指标管理</a>
					</li>
					<!--  <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinModel/input">财务报表模型</a>
					</li> 
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinParm/getListPage">财务报表基础元素</a>
					</li> -->
					
					<!-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFinValid/getListPage">平衡校验公式</a>
					</li> -->
             	</ul>
             </div>
             
             
             <%-- 为平台开发者准备的例子，演示不需要 
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>标准交易</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/Demo/getListPage">标准交易(一)</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/Demo2/getListPage">标准交易(二)[弃用]</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/Demo3/getListPage">标准交易(三)</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/Demo4/getListPage">标准交易(四)</a>
					</li>
					<li><i class="icon-cog"></i>
						<a href="${webPath}/lease/MfCusCustomer/getListPage">客户登记</a>
					</li>
					<li><i class="icon-cog"></i>
						<a href="${webPath}/lease/MfCusFormConfig/getAllList">客户表单配置</a>
					</li>
				</ul>
             </div>
            <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>特殊交易功能</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/DemoSpecial/getListPage">列表列动态切换</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/Demo/input?demoId=100001">标准交易新增</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/DemoTest/demoCzr?demoId=100001">综合页面例子</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/DemoTest/demoBigForm?demoId=100001">大表单页面(bigForm)</a>
					</li>
				</ul>
             </div> 
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>特殊交易功能2</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/layout/view/page/bigform2.jsp">表单自动下拉选择</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/pactCharts">合同图表</a>
					</li>
					<li><i class="i i-jiantoua"></i>		
						<a href="${webPath}/lease/DemoTest/demoProperty?demoId=100001">(详情页面)propertySeeTag</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/DemoTest/demoProperty2?demoId=100001">(详情页面)propertySeeTag2</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/DemoTest/demoRecord?demoId=100001">(记录数页面)RecordCountTag</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/DemoTest/demoRecord2?demoId=100001">(记录数页面)RecordCountTag2</a>
					</li>
					
				</ul>
             </div>--%>
             
              <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>安全选项设置</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/wkfTask/findByPage">待办业务</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/secEntrance/showConfig">安全审计配置</a>
					</li>
				</ul>
             </div>
             
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>其他功能</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/lease/desginModeler">布局设计器</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/component/pms/pms_GetFilterSetting.jsp" target="_blank">筛选自定义生成</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/pmsFilterRel/getListPage">自定义筛选关系</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/tech/layoutDesginer/layoutDesgin_dis.jsp">模块权限控制</a>
					</li>
				</ul>
             </div>
             
             
		</div>
		
		<script type="text/javascript">
		//window.parent.getHelp();
		getHelp();
		</script>
	</body>
</html>
