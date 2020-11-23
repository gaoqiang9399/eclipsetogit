<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			   myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCollateralClass/findByPageAjax",//列表数据查询的url
			    	tableId:"tablenmd00001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    }); 
			    $("body").mCustomScrollbar({
					advanced:{
						theme:"minimal-dark",
						updateOnContentResize:true
					}
				});
			    
			 });

			function getOpenFormInfo(obj,url){
				var formid_old = "";
				var formid_new = "";
				var formtype = obj;
				var formtitle = "";
				var classId = "";
				classId = url.split("?")[1].split("=")[1];
				//暂不使用，母版确定后使用
				//formid_old = "ple"+classId+formtype
				$.ajax({
					url:webPath+"/mfPledgeClass/getPleClasByIdAjax",
					data:{classId:classId,formtype:formtype},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.flag == "1"){
							formid_new = data.formid_new;
							if(formid_new==""||formid_new==null){
								formtitle = data.formtitle;
								openFormDesign(classId,formtype,formtitle);
							}else{
								var url = '<%=webPath %>'+'/tech/dragDesginer/openForm.action?formId='+formid_new;
								window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
							}
						}else if(data.flag == "0"){
							openFormDesign(classId,formtype,formtitle);
						}
					},error:function(){
						alert("请求"+getUrl+"异常");
					}
				});
				
			}

			function restoreForm(obj,url){
				var classId = url.split("?")[1].split("=")[1];
				alert(top.getMessage("CONFIRM_OPERATION_SERIOUS", ""),2,function(){
					$.ajax({
						url:webPath+"/mfPledgeClass/doRestoreForm",
						type:'post',
		    			data:{classId:classId},
		    			async:false,
						success:function(data){
							if(data.flag=="success"){
								window.top.alert(data.msg,1);
							}else{
								window.top.alert(data.msg,0);
							}
						},error:function(){
							alert("请求"+getUrl+"异常");
						}
					});
				});
				}
			function openFormDesign(classId,formtype,formtitle){
				$.ajax({
					url:webPath+"/mfPledgeClass/doCopyForm",
					data:{formtype:formtype,formtitle:formtitle,classId:classId},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.flag == "success"){
							var url = '<%=webPath %>'+'/tech/dragDesginer/openForm.action?formId='+data.formid_new;
							window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
						}
					},error:function(){
						alert("请求"+getUrl+"异常");
					}
				});
			}
		</script>
	</head>
	<body style="overflow-y: hidden;">
		<dhcc:markPoint markPointName="RiskItem_List"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-md-9 column  color_theme">
							<ol class="breadcrumb pull-left padding_left_0">
								<li><a href="${webPath}/sysDevView/setC?setType=form">表单设置</a></li>
								<li class="active">押品动态表单</li>
							</ol>
						</div>
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=类型编号/类型名称"/>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>	
</html>
