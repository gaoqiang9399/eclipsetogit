<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/component/include/webPath.jsp" %>
<%
String contextPath = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+webPath+"/";
%>
<!DOCTYPE html>
<html>
	<head >
		<title>列表表单</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <script type="text/javascript" src="${webPath}/layout/view/js/jquery-1.11.2.min.js"></script>
        <!--<script type="text/javascript" src="${webPath}/component/include/filter.js"></script>-->
        <script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
        <link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" />
        <script type="text/javascript">

		$(function(){
			$(".contentB").mCustomScrollbar({
				advanced:{
					updateOnContentResize:true,
				},
			});
		});
        </script>
		<style>
		html{ height:100%}
		body{  height:93.5% !important;padding:10px;}
		h3{margin-top:0px;margin-bottom: 20px;margin-left: 20px;color: #4183a6; font-size: 16px;font-style: normal;
		font-weight:normal; display:block;}
		.ratingBg{border-radius: 5px;background-color: #fff;height:100%;padding-top:10px;border: none;}
		.ratingBg ul{ width:90%;margin:0 auto; overflow:hidden; padding:0;}
		.ratingBg ul li{ float:left; list-style:none; border:none; margin-top:3px; line-height:30px; text-align:center; font-size:12px; color:#409000;}
		.ratingBg ul li.ratingLi2{width:20%; color:#67CCDE;font-size: 14px;text-align: left;}
		.ratingBg ul li.ratingLi3{width:20%; color:#E17E7F;font-size: 14px;text-align: left;}
		/* .ratingBg ul li.ratingLi3{ width:25%; color:#E17E7F; text-align:left;font-size: 14px;} */
		.ratingBg ul li.passFlag{ width:25%; color:#84B175; text-align:left;font-size: 14px;}
		.ratingBg ul li.ratingLi2 i{margin-right:10px; font-size:18px;}
		.ratingBg ul li.ratingLi3 i{margin-right:10px; font-size:18px;}
		.ratingBg ul li.passFlag i{ margin-left:100px; margin-right:10px; font-size:18px;}
		.ratingBg ul li.ratingLi1{width:30%; color: #575757; font-size: 14px; text-align: left; }
		.ratingBg ul li.ratingLi4{width:50%; color: #67CCDE; font-size: 14px; text-align: left; }
		.ratingImg1{ display:inline-block; width:8px; height:8px; background:url(image/icon-lw.png) no-repeat; background-size:8px; margin-right:12px;}
		.ratingButton{ width:120px; height:30px; line-height:30px; background:nnone; border:none; background-color:#009bed; color:#fff; font-size:14px; display:block; float:right; margin-right:4%; margin-top:30px;}
		.progress {
			height: 20px;
			overflow: hidden;
			background-color: #f5f5f5;
			border-radius: 4px;
			-webkit-box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
			box-shadow: inset 0 1px 2px rgba(0,0,0,.1);
			width:90%;
			margin:0 auto;
		}
		.progressBar{
			background-color: #5e994a;
			display:block;
			width:0%;
			background-image: -webkit-linear-gradient(45deg,rgba(255,255,255,.15) 25%,transparent 25%,transparent 50%,rgba(255,255,255,.15) 50%,rgba(255,255,255,.15) 75%,transparent 75%,transparent);
			height:100%;
			background-size: 40px 40px;
		}
		.ratingProgress{
			overflow:hidden;
			margin-left:5%;
			margin-top:10px;
			display:none;
		}
		.ratingDiv:nth-child(even){
			background: #f9f9f9;
		}
		</style>
		<link href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" rel="stylesheet" type="text/css">
		<link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
	</head>
	
<body>
	<div class="ratingBg">
    	<div class="contentB" style="height:calc(100% - 50px)">
    		<c:if test="${fn:length(rels) == 0}">
	    		<div style="text-align:center;">
	    			暂无数据
	    		</div>
    		</c:if>
    		<c:if test="${fn:length(rels) != 0}">
    		<c:forEach var="rel" items="${rels}" varStatus='st'>
    	 	<div style="overflow:hidden;margin-left: 30px;margin-right: 30px;" class="ratingDiv">
    	 	 <ul>
				 <li class="ratingLi1"><b>${rel.itemName}</b></li>
    		   <li class="ratingLi1">${rel.itemDesc}</li>
<%--     		   <li class="ratingLi1"><s:property value="#rel.itemValue"/></li> --%>
    		   <c:if test="${rel.riskLevel eq 0}">
    		   	<li class="ratingLi2" style="margin-left: 150px"><i class="fa fa-check-circle-o"></i>${rel.riskLevelShow}</li>
    		   </c:if>
    		   <c:if test="${rel.riskLevel ne 0}">
    		   	<li class="ratingLi3" style="margin-left: 150px"><i class="fa fa-exclamation-triangle"></i>${rel.riskLevelShow}</li>
    		   </c:if>
    		   <%-- <s:if test="#rel.sqlPassFlag==0">
    		   	    <li class="ratingLi3"><i class="fa fa-exclamation-triangle"></i>不通过</li>
    		   </s:if>
    		  
    		   <s:elseif test="#rel.sqlPassFlag==3">
            		<li class="ratingLi3"><i class="fa fa-ban"></i>创建拦截项失败</li>
    		   </s:elseif>
    		   <s:else >
            			<li class="passFlag"><i class="fa fa-check-circle-o"></i>通过</li>
    		   </s:else>
    		    <s:if test="#rel.sysErrorFlag==0 ">
    		    
    		   </s:if> --%>
    		</ul>
    		</div>
    	 </c:forEach>	
    	 </c:if>
    	 </div>
    	 </div>
</body>
</html>