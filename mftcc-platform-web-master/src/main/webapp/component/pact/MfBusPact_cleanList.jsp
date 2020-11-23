<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript">
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/mfBusPact/findCleanListByPageAjax",//列表数据查询的url
			tableId : "tablebusclean0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30 //加载默认行数(不填为系统默认行数)
		});
	});
	//批量清理业务数据
	function busDeleteBatch() {
		var datas =[];
		var checkCnt=0;
	    $(".ls_list input:checkbox[name='appId']:checked").each(function(){
            var paramStr = $(this).val();
            var appId = paramStr.split("&")[0].split("=")[1];
            var pactId = paramStr.split("&")[1].split("=")[1];
            var paramObj ={};
            paramObj["appId"] = appId;
			paramObj["pactId"] = pactId;
			datas.push(paramObj);
            checkCnt++;
	    });
	    if(checkCnt==0){
	   		alert(top.getMessage("FIRST_SELECT_ITEM"),4);
	   	 	return false;
	    }else{
			alert(top.getMessage("CONFIRM_DELETE"),2,function(){
			    var listParam = JSON.stringify(datas);
				$.ajax({
					url:webPath+"/mfDataClean/deleteBusBatchAjax",
					data:{ajaxData:listParam},
					type : "POST",
					dataType : "json",
					beforeSend:function(){  
						LoadingAnimate.start();
					},success:function(data){
						if(data.flag == "success"){
							alert(data.msg,1);
							$(".ls_list input:checkbox[name='appId']:checked").each(function(){
					            $(this).parents("tr").remove();
						    });
							updateMyCustomScrollbar.delTrData();
						}else{
							alert(data.msg,0);
						}
					},error:function(data){
					 	
					},complete: function(){
						LoadingAnimate.stop();
					}
				});
			});
		 }
	};

</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div">
					<button type="button" class="btn btn-primary pull-left" onclick="busDeleteBatch();">批量清理</button>
					<ol class="breadcrumb pull-left">
						<li><a href="${webPath}/sysDevView/setC?setType=clean">数据清理</a></li>
						<li class="active">业务数据清理</li>
					</ol>
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/项目名称"/>
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
