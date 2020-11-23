<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="s" uri="/struts-tags"%>

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
         <script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
        <link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.css" />
        <link rel="stylesheet" href="${webPath}/themes/view/css/view-main.css" />
        <script type="text/javascript">

		$(function(){
			$(".contentB").mCustomScrollbar({
				advanced:{
					updateOnContentResize:true,
				},
			});
			
		});
        </script>
        <script type="text/javascript">
        
			$(document).ready(function() {
				$("ul").hide();
				$("#buttonDiv").hide();
				setTimeout(function(){showUl(0)},500);
				function showUl(i){

					if($("ul").eq(i).length>0){					
						$("ul").eq(i).delay(500).show(0,function(){	
							if($("ul").eq(i+1).length>0){
								showUl(i+1);
								$(".contentB").mCustomScrollbar("scrollTo",100,"bottom"); 
							}else{
								$(".ratingProgress").hide();
								$("#buttonDiv").show();
								clearInterval(timer);
								$(".contentB").mCustomScrollbar("scrollTo",100,"bottom"); 
							}
						});
					}
									
				}

				setTimeout(function(){$(".ratingProgress").show()},500);
				function progressBar(){
					$(".progressBar").css("width","0%");
					$(".progressBar").animate({
						width:'100%'
					},400)
				}
				var timer=setInterval(progressBar,500);
			});
			
			
			function goNext() {
				var nextUrl = '${nextUrl}';
				if(nextUrl.substr(0,1)=="/"){
					nextUrl =webPath + nextUrl; 
				}else{
					nextUrl =webPath + "/" + nextUrl;
				}
				//location.href = nextUrl;
				window.top.cloesBigForm(nextUrl);
			}
			function back() {
				var backUrl = '${backUrl}';
				if(backUrl.substr(0,1)=="/"){
					backUrl =webPath + backUrl; 
				}else{
					backUrl =webPath + "/" + backUrl;
				}
				//location.href = backUrl;
				window.top.cloesBigForm(backUrl);
			}
        </script>
		<style>
		html{ height:100%}
		body{  height:96%;}
		h3{margin-top:0px;margin-bottom: 20px;margin-left: 20px;color: #4183a6; font-size: 16px;font-style: normal;
			font-weight:normal; display:block;}
		.ratingBg{    border-radius: 5px;background-color: #ebebeb;height:100%;padding-top:10px;}
		.ratingBg ul{ width:90%;margin:0 auto; overflow:hidden; padding:0;}
		.ratingBg ul li{ background:#fafafa; height:30px; float:left; list-style:none; border:1px solid #e3e4e3; margin-top:3px; line-height:30px; text-align:center; font-size:12px; color:#000;}
		.ratingBg ul li.ratingLi2{ width:19%;color:#000;font-weight:700;}
		.ratingBg ul li.ratingLi3{ width:25%; color:#5e994a; text-align:left;}
		.ratingBg ul li.ratingLi3 i{ margin-left:100px; margin-right:10px; font-size:18px;}
		.ratingBg ul.ratingWarning li.ratingLi3{ color:#dfb64d;}
		.ratingBg ul.ratingStop li.ratingLi3{ color:#b1424d;}
		.ratingBg ul li.ratingLi4{ width:calc(56% - 6px); font-weight:700;}
		.ratingBg ul.ratingWarning li{ color:#dfb64d; background-color:#F9E6C2;}
		.ratingBg ul.ratingStop li{ color:#b1424d; background-color:#f3c9ce;}
		.ratingImg1{ display:inline-block; width:8px; height:8px; background:url(image/icon-lw.png) no-repeat; background-size:8px; margin-right:12px;}
		.ratingButton{ width:120px; height:30px; line-height:30px; background:none; border:none; background-color:#009bed; color:#fff; font-size:14px; display:block; float:right; margin-right:4%; margin-top:30px;}
		.progress {
			display:none;
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
			display:none;
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
		</style>
		<link href="${webPath}/UIplug/Font-Awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
		<link href="${webPath}/layout/view/themes/iconfont/css/iconfont.css" rel="stylesheet" type="text/css">
	</head>
<body style="" class="contentA">
	<div class="ratingBg">
    	<h3></h3>
    <div class="contentB" style="height:calc(100% - 90px)">	
    <c:forEach var="rel" items="${rels}" varStatus='st'>
    	 	<div style="overflow:hidden;" class="ratingDiv">
    	 	  <c:if test="${rel.riskLevel eq '低风险' && rel.sqlPassFlag eq 0}">
    				<ul  class="ratingWarning">
    		  </c:if>
    		  <c:if test="${rel.riskLevel eq '高风险' && rel.sqlPassFlag eq 0}">
    		  <ul  class="ratingStop">
    		   </c:if>
    		    <%-- <s:else >
    		    <ul>
    		    </s:else> --%>
    		
    			
    		   <%-- <li class="ratingLi1"><span><s:property value="#st.index+1" /></span></li> --%>
    		    
    		   <li class="ratingLi2">${rel.riskLevel }</li>
    		   <li class="ratingLi4">${rel.itemDesc }</li>
    		   <c:if test="${rel.riskLevel eq '低风险' && rel.sqlPassFlag eq 0}">
    		   	    <li class="ratingLi3"><i class="fa fa-exclamation-triangle"></i>不通过</li>
    		   </c:if>
    		   <c:if test="${rel.riskLevel eq '高风险' && rel.sqlPassFlag eq 0}">
    		   		<c:set var="canNext" value="0" />
            		<li class="ratingLi3"><i class="fa fa-ban"></i>不通过</li>
    		   </c:if>
    		   <c:if test="${(rel.riskLevel ne '高风险' && rel.riskLevel ne '低风险') || rel.sqlPassFlag ne 0}">
            			<li class="ratingLi3"><i class="fa fa-check-circle-o"></i>通过</li>
    		   </c:if>
    		    <c:if test="${rel.sysErrorFlag eq 0}">
    		   	     <c:set var="canNext" value="0" />
    		   </c:if>
    		</ul>
    		</div>
    	 </c:forEach>	
    	
    <div style="padding-left" class="ratingProgress">  
        <div class="progress">
            <span class="progressBar"></span>
        </div>
	</div>
	</div>
	<div id="buttonDiv">
 		<c:if test="${canNext eq 0}">
    		 <input type="button" disabled="disabled" style="background-color: red" value="已拦截" class="ratingButton" >
     	</c:if>
     <c:if test="${canNext ne 0}">
     	   <input type="button"  value="下一步" class="ratingButton" onclick="goNext()">
     </c:if>
     		
     	<input type="button"  value="关闭" class="ratingButton" onclick="back()">    
     	</div>
     	 </div>
    
</body>
</html>