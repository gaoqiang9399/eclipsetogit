<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String layout = "layout/view";
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link id="C" rel="stylesheet" type="text/css" href="${webPath}/<%=layout%>/page/css/C${skinSuffix}.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/iconfont/css/iconfont.css" />
		<%-- <script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script> --%>
		<script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script>
		<link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" />
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js" ></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
	</head>
		<script type="text/javascript">
	function openProcessDesigner(){
		window.open("workflow/WFDLDesigner.jsp");
	}
	function openProcessDesignerNew(){
		window.open("tech/wkf/modelerEditor.jsp");
	}
	$(function(){
	$("body").mCustomScrollbar()
	})
	</script>
	<body style="height:calc(100% - 10px)">
		<dhcc:markPoint markPointName="sysC"/>
		<div class="opt_div">
			<div class="opt_divNew">
	             <h2><i class="i i-wenjian4"></i>旧账过渡</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtMain/getListPage">旧账过度主表</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtAppProject/getListPage">旧账合同信息</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtCusPersInfo/getListPage">旧账个人承租人信息</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtCusCorpInfo/getListPage">旧账对公承租人信息</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtCusSupplier/getListPage">旧账厂商信息</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtCusProxy/getListPage">旧账经销商信息</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtAppPersGuarantor/getListPage">旧账个人担保人信息</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtAppCorpGuarantor/getListPage">旧账对公担保人信息</a>
						</li>
					    <li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtAuthCont/getListPage">旧账经销商授信信息</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtLeaseItemInfo/getListPage">旧账租赁物信息</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtInsureRegister/getListPage">旧账保险信息</a>
						</li>
						<%-- <li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtAcLnPayPln/getListPage">旧账还款计划信息</a>
						</li> --%>
						<%-- <li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtAppFee/getListPage">旧账或有费用信息</a>
						</li> --%>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtBudgetLog/getListPage">旧账核销明细信息</a>
						</li>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/odtError/getListPage">旧账校验错误信息</a>
						</li>
					</ul>
			 </div>
			<div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>关联客户</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<!-- <a href="${webPath}/cusManageRel/getListPageForSupplier?entranceNo=10">厂商</a> -->
						<a href="${webPath}/cusManageRel/getListPageForSupplier">厂商</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<!-- <a href="${webPath}/cusManageRel/getListPageForProxy?entranceNo=9">经销商</a> -->
						<a href="${webPath}/cusManageRel/getListPageForProxy">经销商</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<!-- <a href="${webPath}/cusManageRel/getListPageForChannel?entranceNo=14">渠道商</a> -->
						<a href="${webPath}/cusManageRel/getListPageForChannel">渠道商</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusGuarantee/getListPage">担保人</a>
					</li>
					<li><i class="i i-jiantoua"></i>
								<a href="${webPath}/cusPartner/getListPage">合作伙伴</a>
					</li>
					<!-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusManageRel/getCusCorrelation">关联关系</a>
					</li> -->
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>其他功能</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusMoveApp/getListPage">客户移交</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/appMoveApp/getListPage">业务移交</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/appProjectInit/getListPage">立项申请</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/appProjectIntention/getListPage">业务申请意向</a>
					</li>
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>租后跟踪</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/claTaskBase/getListPage">五级分类任务</a>
					</li>
					
					<%-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/llcTaskBase/getListPageOfManage">租后检查任务</a>
					</li> --%>
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>租后处理</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/alpPirInfo/getListPage">罚息减免</a>
					</li>
				</ul>
             </div>
             <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>工程管理</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/emProRegister/getListPage">工程登记</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/emEquRegister/getListPage">设备登记</a>
					</li>
				</ul>
             </div> 
             
            <%--  <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>营销管理</h2>
                <ul>
					  <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mktOpp/getListPage">营销机会管理</a>
					</li> 
					 <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mktRpt/getListPage">营销联系报告</a>
					</li> 
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mktServInfo/getListPage">营销服务记录</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mktTeam/getListPage">营销团队</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mktAdv/getListPage">客户建议</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mktAppeal/getListPage">客户投诉</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/mktAsk/getListPage">客户询问</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/appProjectIntention/getListPage">业务申请意向</a>
					</li>
				</ul>
             </div> --%>
		</div>
		
		<script type="text/javascript">
		//window.parent.getHelp();
		getHelp();
		</script>
	</body>
</html>
