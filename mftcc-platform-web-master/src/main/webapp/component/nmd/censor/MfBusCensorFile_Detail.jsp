<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<!-- <script type="text/javascript"
	src="/factor/component/include/closePopUpBox.js"></script> -->
<link rel="stylesheet"
	href="${webPath}/component/sys/sysextend/css/MfBusCensorFile_Insert.css" />
<style type="text/css">
	.centerfix{
		width=300px;
	}
	#myfromrow{
		    padding: 5px 0px 75px 0px;
	}
		.table_content {
    overflow: hidden;
    padding: 0px 103px 0px 123px;
}
</style>
</head>
<body class="overflowHidden bg-white">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12">
			<div class="btn-div">
				<div class="scroll-content">
					<div class="col-md-10 col-md-offset-1 margin_top_20">
						<div class="bootstarpTag fourColumn">
							<div class="form-title"></div>
							<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form method="post" id="MfBusCensorFileForm" theme="simple"
								name="operform"
								action="${webPath}/mfBusCensorFile/updateAjax">
								<dhcc:bootstarpTag property="formcensorfileinsert" mode="query" />
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
	<div class="centerfix">
		<div class="row clearfix">
			<div id="content" class="table_content"
				style="height: auto; top: 25px;"></div>
		</div>
	</div>
	<div class="formRowCenter" id="myfromrow">
		<dhcc:thirdButton value="更新" action="保存"
			onclick="myInsertAjax('#MfBusCensorFileForm')"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
			onclick="myclose();"></dhcc:thirdButton>
	</div>
	<!--<%@ include file="/component/include/PmsUserFilter.jsp"%>-->
</body>
<script type="text/javascript">
	$(function() {
		initPage();
	});
	function initPage() {
		myCustomScrollbar({
			obj : "#content", //页面内容绑定的id
			url:webPath+"/mfBusCensorFile/findByPageBaseAjax", //列表数据查询的url
			tableId : "tablecensorfilebase0001", //列表数据查询的table编号
			tableType : "thirdTableTag", //table所需解析标签的种类
			pageSize : 500, //加载默认行数(不填为系统默认行数)
			topHeight : 200
		//顶部区域的高度，用于计算列表区域高度。
		});
		//通过dom解析,获取到审查文件编号
		var theCensorFileNo = "${censorFileNo}";
		$.ajax({
				url:webPath+"/mfBusCensorFile/findItemByIdAjax",
				type : 'post',
				data : "censorFileNo="+theCensorFileNo,
				dataType : 'json',
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						top.addFlag=true;
						var resultJson =$.parseJSON(data.theList);
						for(var ii = 0;ii<resultJson.length;ii++){
							var itemID = resultJson[ii].itemNo;
							$querytable = $("table[title='tablecensorfilebase0001']");
							var $queryinput = $querytable.find("input[type='hidden']");
							for(var iij = 0;iij < $queryinput.length;iij++){
								if($($queryinput.get(iij)).val()==itemID){
									($($queryinput.get(iij))).parent().parent().find("input[type='checkbox']").attr("checked",true);
								}
							}
						} 
					}else {
						window.top.alert(data.msg, 0);
					}
				},
				error : function() {
					LoadingAnimate.stop(); 
					alert(top.getMessage("ERROR_REQUEST_URL", "${webPath}/mfBusCensorFile/findItemByIdAjax?censorFileNo="+theCensorFileNo));
				}
			});
	}
	 	function myInsertAjax(dom){//新增方法
	 		var cenList = new Array();
	 		//获取所有checkBox
	 		var $tds = $("tbody tr td input[type='checkbox']");
	 		for(var i = 0;i<$tds.length;i++){
	 			var $thisEle = $($tds.get(i));
	 			if($thisEle.is(':checked')){
	 				//获取父节点的兄弟节点的兄弟节点的文本内容
	 				var thisText = $thisEle.parent().parent().find("input[type='hidden']").val();
	 				cenList.push(thisText);
	 			}
	 		}
	 		var jsonArray = JSON.stringify(cenList);
			var flag = submitJsMethod($(dom).get(0), '');
			if(flag){
				//获取表单数据
				var url = $(dom).attr("action");
				var dataParam = JSON.stringify($(dom).serializeArray());
				LoadingAnimate.start();
				//发送ajax请求
					$.ajax({
						url:url,
						data : {
							ajaxData : dataParam,
							baseList : jsonArray
						},
						type : 'post',
						dataType : 'json',
						success : function(data) {
							LoadingAnimate.stop(); 
							if (data.flag == "success") {
								top.addFlag=true;
								alert(data.msg, 3);
								/*
								//触发搜索按钮,点击数据
								$("#filter_btn_search").click();
								//重新加载页面数据
								*/
								$("#filter_btn_search").click();
								myclose_click();
							} else {
								window.top.alert(data.msg, 0);
							}
						},
						error : function() {
							LoadingAnimate.stop(); 
							alert(top.getMessage("ERROR_REQUEST_URL", url));
						}
					});
			}
		}
</script>
</html>