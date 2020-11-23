<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head >
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
	<link id="C" rel="stylesheet" type="text/css" href="${webPath}/layout/view/page/css/C${skinSuffix}.css" />
	<link rel="stylesheet" href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" />
	<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
</head>
<body style="height:calc(100% - 10px)">
	<dhcc:markPoint markPointName="C"/>
		<div class="opt_div">
			<div id="caiwu">
				<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>凭证管理</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/cwVoucherMst/toVoucherAdd">凭证录入</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/cwVoucherMst/getListPage">凭证查询</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/component/finance/voucher/pingzhengzhengli.jsp" >凭证整理</a>
						</li>
						
					</ul>
	            </div>	
				<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>期末处理</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/cwMenthEnd/CwMendthEnd" >结账</a>
						</li>
						
					</ul>
	            </div>	
				<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>账簿查询</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/generalLedger/getListPage">总账</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/detailAccount/getListPage">明细账</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="#"  target="_blank">科目余额表</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="#"  target="_blank">科目汇总表</a>
						</li>
						
					</ul>
	            </div>	
				<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>财务报表</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="#"  target="_blank">资产负债表</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="#"  target="_blank">现金流量表</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="#"  target="_blank">利润表</a>
						</li>
					</ul>
	            </div>	
				<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>参数设置</h2>
	                <ul>
	                	<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/cwCycleHst/getListPage" >会计周期管理</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/cwComItem/getListPage?accType=1" >科目管理</a>
							
							<!-- <a href="#"  target="_blank" target="_blank">科目管理</a> -->
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/cwInitBal/getInitPage"  target="_self">财务余额初始化</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/cwProofWords/getListPage"  target="_self">凭证字设置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="#"  target="_blank">凭证岗位设置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/cwSysParam/getListPage" >参数化设置</a>
						</li>		
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/cwVoucherRemarks/getListPage"  target="_self" >凭证摘要管理</a>
						</li>
					</ul>
	            </div>
	           <h3>以下为Demo</h3>
				<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>标准交易</h2>
	                <ul>
	                	<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/tech/dragDesginer/formIframe.jsp"  target="_blank">表单设计器</a>
						</li>
						<li><i class="icon-cog"></i>
							<a href="${webPath}/demo/getListPage">标准交易(一)</a>
						</li>
						<li><i class="icon-cog"></i>
							<a href="${webPath}/demo2/getListPage">标准交易(二)[弃用]</a>
						</li>
						<li><i class="icon-cog"></i>
							<a href="${webPath}/demo3/getListPage">标准交易(三)</a>
						</li>
						<li><i class="icon-cog"></i>
							<a href="${webPath}/demo4/getListPage">标准交易(四)</a>
						</li>
						
					</ul>
	            </div>	
	            <div class="opt_divNew">
           			<h2><i class="i i-wenjian4"></i>特殊交易功能</h2>
                		<ul>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/demoSpecial/getListPage">列表列动态切换</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/demo/input?demoId=100001">标准交易新增</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/demoTest/demoCzr?demoId=100001">综合页面例子</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/demoTest/demoBigForm?demoId=100001">大表单页面(bigForm)</a>
							</li>
						</ul>
            	</div>
            	<div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>特殊交易功能2</h2>
                	<ul>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/layout/view/page/bigform2.jsp">表单自动下拉选择</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/pactCharts.action">合同图表</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/demoTest/demoProperty?demoId=100001">(详情页面)propertySeeTag</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/demoTest/demoProperty2?demoId=100001">(详情页面)propertySeeTag2</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/demoTest/demoRecord?demoId=100001">(记录数页面)RecordCountTag</a>
							</li>
							<li><i class="icon-cog"></i>
								<a href="${webPath}/demoTest/demoRecord2?demoId=100001">(记录数页面)RecordCountTag2</a>
							</li>
						</ul>
            	</div>
			</div>
			<div id="form" style="display: none;">
				<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>页面设计</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/tech/dragDesginer/formIframe.jsp"  target="_blank">表单设计器</a>
						</li>
						
					</ul>
	             </div>	
				<div class="opt_divNew">
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
             	</div>
             	<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>押品配置</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/mfPledgeClass/getAllList">押品动态表单</a>
						</li>
					</ul>
             	</div>
			 </div>
			 <div id = "task" style="display: none;">
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
			 </div>
			 <div id = "dic" style="display:none">
			  	<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>数据字典配置</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/parmKey/getListPage">数据字典项配置</a>
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
							<a href="${webPath}/sysRole/getAll" >角色权限配置</a>
						</li>
					</ul>
	             </div>
			  </div>
			  <div id="base" style="display:none">
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
							<a href="${webPath}/pmsDataRang/findByPage">数据权限定义</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/secEntrance/showConfig">安全审计配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/sysGlobalSearch/getListPage">全局搜索配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/sysDescTemp/getListPage">业务描述模板配置</a>
						</li>
						<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/desginModeler.action">布局设计器</a>
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
		</div>
</body>
</html>