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
		var cusNo = '${cusNo}';
			$(function(){
				LoadingAnimate.start();
				var table = "tablefincInfoSelect";
				var url = webPath+"/mfBusFincApp/findFincByPageAjax";
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:url,//列表数据查询的url
			    	tableId:table,//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{cusNo:cusNo
					},//指定参数
			    	callback:function(){
			    		LoadingAnimate.stop();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
			function chooseFinc(parm){
				parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var fincId = parmArray[0].split("=")[1];
				$.ajax({
					url:webPath+"/mfBusFincApp/getfincInfoByFincIdAjax?fincId="+fincId,
					dataType:"json",
					type:"POST",
					success:function(data){
						if(data.flag == "success"){
							var obj = $.extend({},data.mfBusFincApp);
							parent.dialog.get('fincDialog').close(obj).remove();
							//$(".ui-dialog",parent.document).empty();
						}else{
							alert(top.getMessage("ERROR_SELECT"));
						}
					},error:function(){
						alert(top.getMessage("ERROR_DATA_CREDIT","客户"));
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
							<input type="text" class="form-control" id="filter_in_input" placeholder="客户名称/项目名称">
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
