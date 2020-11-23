<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/webPath.jsp" %>
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
		<script type="text/javascript" src="${webPath}/<%=layout%>/js/jquery-1.11.2.min.js"></script>
		<script type="text/javascript" src="${webPath}/component/help/sysHelp.js" ></script>
		<link rel="stylesheet" href="${webPath}/component/help/sysHelp.css" />
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js" ></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
		<script type="text/javascript">
		$(function(){
			$("body").mCustomScrollbar();
		})
		</script>
	</head>
	<body style="height:calc(100% - 10px)">
	<dhcc:markPoint markPointName="sysFinaC"/>
		<div class="opt_div">
               <div class="opt_divNew">
           		<h2><i class="i i-wenjian4"></i>资金管理</h2>
                <ul>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/fundTotal/getFundToatalAll">虚拟总账</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/fundDic/getListPage">收付款明细项维护</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/fundBase/getListPage">资金账户管理</a>
					</li>
					<!-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFundAcc/getListPage">客户资金池</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/cusFundLog/getListPage">客户资金池明细</a>
					</li> -->
						<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/fundLog/getListPage?dicType=1">资金流水</a>
					</li>
					<!-- <li><i class="i i-jiantoua"></i>
						<a href="${webPath}/fundLog/getListPage?dicType=2">付款操作</a>
					</li> -->
				<!-- 	<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/acLmAtpyDtl/getListPage?dcFlag=1">收款操作新</a>
					</li> -->
					</li>
						<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/budgetLog/getListPageForReceive">待收款项</a>
					</li>
					<li><i class="i i-jiantoua"></i>
						<a href="${webPath}/acLmAtpyDtl/getListPage?dcFlag=2">待付款项</a>
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
