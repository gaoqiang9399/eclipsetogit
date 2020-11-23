<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<%@ include file="/component/include/message.jsp" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String basePath1 = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort();
String layout = "layout/view";
String setType = request.getParameter("setType");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<link id="C" rel="stylesheet" type="text/css" href="${webPath}/<%=layout%>/page/css/C${skinSuffix}.css" />
		<link rel="stylesheet" href="${webPath}/<%=layout%>/themes/iconfont/css/iconfont.css" />
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.min.js" ></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js" ></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" />
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
	</head>
	<script type="text/javascript">
			var setType = "<%=setType%>";
			<%-- function openProcessDesigner(){
				window.open("${webPath}/workflow/WFDLDesigner.jsp");
			}
			function openProcessDesignerNew(){
				window.open("${webPath}/tech/wkf/modelerEditor.jsp");
			} --%>
			$(function(){
				$("body").mCustomScrollbar();
				$("#"+setType).show();
				
			});
			
			//账套是否显示出来
			
		/* 	function ztBooksShow(){
				$.ajax({
					url:"/cwZtBooks/getztShowAjax",
					type:'post',
					data:'',
					async:false,
					success:function(data){
						if(data.flag=="success"){
							$("#ztshow").show();
						}else {
							$("div#ztshow").remove();
						}
					},error:function(){
						$("div#ztshow").remove();
					}
				});
			} */
			
	</script>
	<body style="height:calc(100% - 10px)">
	<dhcc:markPoint markPointName="C"/>
		<div class="opt_div">
			  <div id="orga" ><!-- style="display:none" -->
			  	<div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>组织机构</h2>
	                <ul>
						<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/sysOrg/listSysOrg">机构管理</a>
						</li>
					</ul>
	             </div>
			  </div>
			  <div class="opt_divNew">
	           		<h2><i class="i i-wenjian4"></i>系统logo设置</h2>
	                <ul>
	                	<li><i class="i i-jiantoua"></i>
							<a href="${webPath}/mfSysCompanyMst/getById">系统logo设置</a>
						</li>
					</ul>
	             </div>
			 <div class="opt_divNew" id="ztshow" >
		           		<h2><i class="i i-wenjian4"></i>财务帐套管理</h2>
		                <ul>
							<li><i class="icon-building"></i>
								<a href="${webPath}/cwZtBooks/getListPage">帐套管理</a>
							</li>
							
						</ul>
	           </div>
		</div>
		<script type="text/javascript">
		</script>
	</body>
</html>
