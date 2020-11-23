<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%
	String wkfAppId = (String)request.getParameter("wkfAppId");
	String taskId = (String)request.getParameter("taskId");
	String appId = (String)request.getParameter("appId");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript">
			var wkfAppId='<%=wkfAppId%>';
			var taskId='<%=taskId%>';
			var appId='<%=appId%>';
			var cusNo='${cusNo}';
			var scNo ='${scNo}';//客户要件场景
			var docParm = "cusNo="+cusNo+"&relNo="+cusNo+"&scNo="+scNo;//查询文档信息的url的参数
			$(function(){
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced:{
// 						updateOnContentResize:true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
			});
			window.ajaxUpdate1 = function(obj,callback){
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray()); 
					LoadingAnimate.start();
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam,wkfAppId:wkfAppId,taskId:taskId,appId:appId},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							LoadingAnimate.stop();
							if(data.flag == "success"){
								window.top.alert(data.msg,3,function(){
									 top.flag=true;
									 top.pactUpdateFlag=true;//表示是否是合同签约节点
									 top.pactDetailInfo = data.pactDetailInfo;
									 myclose_click();
								});									
							}
						},error:function(data){
							 LoadingAnimate.stop();
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			};
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
<!-- 						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div> -->
					<form method="post" id="pactInsertForm" theme="simple" name="operform"  action="${webPath}/mfLoanApply/updateForCapitalAjax">
						<dhcc:bootstarpTag property="formfincapp0001" mode="query"/>
					</form>	
					</div>
					<div class="row clearfix">
						<div class="col-md-8 col-md-offset-2 column margin_top_20" >
							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="提交" action="提交" onclick="ajaxUpdate1('#pactInsertForm',myclose)"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
