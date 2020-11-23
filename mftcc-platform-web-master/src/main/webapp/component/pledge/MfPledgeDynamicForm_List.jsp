<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfPledgeDynamicForm/findByPageAjax",//列表数据查询的url
			    	tableId:"tablepledy0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"useFlag",onText:"启用",offText:"停用"});
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			 //打开新增表单
			 function addPleDyForm(){
				 var url = webPath+"/mfPledgeDynamicForm/input";
				 top.openBigForm(url,'新增押品动态表单','80','80');
			}
			//打开编辑表单
			function updatePleDyForm(obj, url){
				top.openBigForm(url,'标签配置','80','80');
			}
			//删除押品动态表单
			function deletePleDyForm(obj,url){
				var pleFormNo = url.split("?")[1].split("=")[1];
				$.ajax({
					url:webPath+"/mfPledgeDynamicForm/getPleFormIfUsed",
					type:'post',
	    			data:{pleFormNo:pleFormNo},
	    			async:false,
					success:function(data){
						if(data.flag=="1"){
							window.top.alert("该表单已被使用，不能删除！",0);
						}else{
							ajaxTrDelete(obj,url);
						}
					},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}
			function getDesignForm(obj,url){
				var formtype = obj;
				//action?pleFormNo=pleFormNo&motherFormId=motherAddFormId&motherFormId=motherAddFormId
				var pleFormNo = url.split("?")[1].split("&")[0].split("=")[1];
				var motherFormId = url.split("?")[1].split("&")[1].split("=")[1]
				var increId = url.split("?")[1].split("&")[2].split("=")[1]
				$.ajax({
					url:webPath+"/mfPledgeDynamicForm/getDesignForm",
					type:'post',
	    			data:{pleFormNo:pleFormNo,formtype:formtype},
	    			async:false,
					success:function(data){
						if(data.flag=="1"){
							var url = '<%=webPath %>'+'/tech/dragDesginer/openForm.action?formId='+data.formid_new;
							window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
						}else{
							doCopyForm(pleFormNo,formtype,motherFormId,increId);
						}
					},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}
			//复制表单
			function doCopyForm(pleFormNo,formtype,motherFormId,increId){
				$.ajax({
					url:webPath+"/mfPledgeDynamicForm/doCopyForm",
					type:'post',
	    			data:{pleFormNo:pleFormNo,formtype:formtype,formid_old:motherFormId,increId:increId},
	    			async:false,
					success:function(data){
						if(data.flag=="success"){
							var url = '<%=webPath %>'+'/tech/dragDesginer/openForm.action?formId='+data.formid_new;
							window.open(url,'width='+(window.screen.availWidth-10)+',height='+(window.screen.availHeight-30)+ 'top=0,left=0,toolbar=no,menubar=no,scrollbars=no, resizable=no,location=no, status=no');
						}else{
							window.top.alert(data.msg,0);
						}
					},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				});
			}

			function restoreForm(obj,url){
				var pleFormNo = url.split("?")[1].split("=")[1];
				alert(top.getMessage("CONFIRM_OPERATION_SERIOUS"),2,function(){
					$.ajax({
						url:webPath+"/mfPledgeDynamicForm/doRestoreForm",
						type:'post',
		    			data:{pleFormNo:pleFormNo},
		    			async:false,
						success:function(data){
							if(data.flag=="success"){
								window.top.alert(data.msg,1);
							}else{
								window.top.alert(data.msg,0);
							}
						},error:function(){
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					});
				});
			}
		</script>
	</head>
	<body>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="addPleDyForm();">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=form">表单设置</a></li>
								<li class="active">担保动态表单 </li>
							</ol>
						</div>
						<jsp:include page="${webPath}/component/include/mySearch.jsp?blockType=2&placeholder=担保动态表单名称/表单说明"/>
					</div>
				</div>
			</div>
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>	
</html>
