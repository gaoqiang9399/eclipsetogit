<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
		var adaptationKindNo = '${adaptationKindNo}';
		var cusBaseType = '${cusBaseType}';
		var cusNo = '${cusNo}';
			$(function(){
				LoadingAnimate.start();
				var table = "tableCreditContract00001";
				var url = webPath+"/mfCusCreditContract/findByPage";
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:url,//列表数据查询的url
			    	tableId:table,//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{adaptationKindNo:adaptationKindNo,cusNo:cusNo},//指定参数
			    	callback:function(){
			    		LoadingAnimate.stop();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			function choseCus(parm){
				parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var id = parmArray[0].split("=")[1];
				$.ajax({
					url:webPath+"/mfCusCreditContract/getByChooseIdAjax?id="+id,
					dataType:"json",
					type:"POST",
					success:function(data){
						if(data.flag == "success"){
							parent.dialog.get('cusDialog').close(data.contractInfo).remove();
							//$(".ui-dialog",parent.document).empty();
						}else{
							alert(top.getMessage("ERROR_SELECT"));
						}
					},error:function(){
						alert(top.getMessage("ERROR_DATA_CREDIT","合同"));
					}
				});
			};
		</script>
	</head>
	<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div col-xs-5 pull-right">
					<div class="znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="授信合同号">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
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
