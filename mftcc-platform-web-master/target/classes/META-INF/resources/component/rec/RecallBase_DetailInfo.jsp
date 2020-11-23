<%@page import="net.sf.json.JSONObject"%>
<%@ page language="java"  contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ include file="/component/include/common.jsp"%>
<%
String path = request.getContextPath();
String taskNo = (String)request.getAttribute("taskNo");
String recallSts = (String)request.getAttribute("recallSts");
%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
		<meta charset="utf-8" />
		<meta http-equiv="X-UA-Compatible" content="IE=Edge,chrome=1"/>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		
		<style type="text/css">
			.inner-center{
				/* height : calc(100% - 150px) !important; */
			}
			.inner-north,.inner-center{
				width : calc(100% - 25px) !important;
			}
			.inner-center span{
				margin-left: 30px;
				color: #32b5cb;
				font-size: 14px;
			}
			th{
				text-align:center;
			}
			.content-box{
				width: 80%;
			}
		</style>
		<script type="text/javascript">
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					theme:"minimal-dark",
// 					updateOnContentResize:true
// 				}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
			});
			
		</script>
</head>
 
<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag fourColumn">
						<form method="post" id="operform" theme="simple" name="operform">
								<dhcc:bootstarpTag property="formrec0012" mode="query" />
						</form>
					</div>
				</div>
	   		</div>

   		</div>
		
	</body>
</html>