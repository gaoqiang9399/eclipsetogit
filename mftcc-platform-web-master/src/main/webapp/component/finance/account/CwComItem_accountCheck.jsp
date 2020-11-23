<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/search_filter.css" />
<script type="text/javascript"
	src="${webPath}/themes/factor/js/search_filter.js"></script>
<title>科目检查</title>
<style>
.filed-ps {
	margin-bottom: 10px;
	padding: 5px;
}

</style>
</head>
<body>
<div class="scroll-content">
	<fieldset class="filed-ps">
		<legend>
			<font style="color: #417EB7;font-size:6px;">凭证管理</font>
		</legend>
	<div id="pzManageDiv" class="table_content" style="height: auto;">

		<table id="pzManagetable" width="100%" border="0" align="center"
			cellspacing="1" class="ls_list" title="pingzhengguanli">
			<colgroup style="width: 20%"></colgroup>
			<colgroup style="width: 30%"></colgroup>
			<colgroup style="width: 10%"></colgroup>
			<colgroup style="width: 6%"></colgroup>
			<colgroup style="width: 20%"></colgroup>
			<colgroup></colgroup>
			<thead>
				<tr>
					<th scope="col" width="30%" align="center" name="accNo">凭证日期</th>
					<th scope="col" width="30%" align="center" name="accName">凭证字号</th>
				<!-- 	<th scope="col" width="30%" align="center" name="accName">位置</th> -->
				</tr>
			</thead>
			<tbody id="tab">
			 	
				
			</tbody>
		</table>
	</div>
</fieldset>

<fieldset class="filed-ps">
		<legend>
			<font style="color: #417EB7;font-size:6px;">交易代码配置</font>
		</legend>
	<div id="jyManageDiv" class="table_content" style="height: auto;">

		<table id="jiaoYiManagetable" width="100%" border="0" align="center"
			cellspacing="1" class="ls_list" title="pingzhengzhengli">
			<colgroup style="width: 20%"></colgroup>
			<colgroup style="width: 30%"></colgroup>
			<colgroup style="width: 10%"></colgroup>
			<colgroup style="width: 6%"></colgroup>
			<colgroup style="width: 20%"></colgroup>
			<colgroup></colgroup>
			<thead>
				<tr>
					<th scope="col" width="30%" align="center" name="accNo">交易代码名称</th>
					<th scope="col" width="30%" align="center" name="accName">交易方式名称</th>
				<!-- 	<th scope="col" width="30%" align="center" name="accName">位置</th> -->
				</tr>
			</thead>
			<tbody id="tab">
			</tbody>
		</table>
	</div>
</fieldset>
<fieldset class="filed-ps">
		<legend>
			<font style="color: #417EB7;font-size:6px;">报表项配置</font>
		</legend>
	<div id="reportManageDiv" class="table_content" style="height: auto;">

		<table id="reportManagetable" width="100%" border="0" align="center"
			cellspacing="1" class="ls_list" title="pingzhengzhengli">
			<colgroup style="width: 20%"></colgroup>
			<colgroup style="width: 30%"></colgroup>
			<colgroup style="width: 10%"></colgroup>
			<colgroup style="width: 6%"></colgroup>
			<colgroup style="width: 20%"></colgroup>
			<colgroup></colgroup>
			<thead>
				<tr>
					<th scope="col" width="30%" align="center" name="accNo">报表类型</th>
					<th scope="col" width="30%" align="center" name="accName">报表项名称</th>
					<!-- <th scope="col" width="30%" align="center" name="accName">位置</th> -->
				</tr>
			</thead>
			<tbody id="tab">
			</tbody>
		</table>
	</div>
</fieldset>
</div>
</body>
<script type="text/javascript">
	$(function() {
		//loadedpz();
		//自定义滚动条
	/* 	$("body").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:true
			}
		}); */
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced : {
// 				theme : "minimal-dark",
// 				updateOnContentResize : true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
		
		//凭证管理
		pingzhengManage();
		//交易代码配置
		jiaoYiManage();
		//报表项配置
		reportManage();
	});
	//凭证管理
	function pingzhengManage(){
		var pzlistMap = '${pzlistMap}';
		var pzlistMap_flag = '${pzlistMap_flag}';
		
		if(pzlistMap){
			var pzData = $.parseJSON(pzlistMap);
			console.log(pzData);
			if (pzlistMap_flag == 'success') {
					$(".appendClass").remove();
					var length = pzData.length;
					if (length == 0) {
						$('#pzManageDiv tbody').append('<tr><td style="text-align: center;" colspan="10">暂无数据</td></tr>')
						return;
					}
					for ( var i = 0; i < pzData.length; i++) {
						
					 	$("#pzManagetable").append(
								"<tr class=\"appendClass\" ><td width=\"30%\"  align=\"center\">"
								+ pzData[i].voucher_date+ "</td>"
								+"<td width=\"30%\" align=\"center\" >"
										+ pzData[i].pz_prefix+ "-"+ pzData[i].voucher_note_no
										+ "</td></tr>"); 
									/* 	+ "</td><td width=\"30%\"  align=\"center\">"
										+ pzData[i].plce + "</td></tr>"); */
					}
					
				} else {
					//alert("error");
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
		}
	}
	//交易代码配置
	function jiaoYiManage(){
		var jylistMap = '${jylistMap}';
		var pzlistMap_flag = '${pzlistMap_flag}';
		
		if(jylistMap){
			var jyData = $.parseJSON(jylistMap);
			
			if (pzlistMap_flag == 'success') {
					$(".appendClass2").remove();
					var	length = jyData.length;
					if (length == 0) {
						$('#jyManageDiv tbody').append('<tr><td style="text-align: center;" colspan="10">暂无数据</td></tr>')
						return;
					}
					for ( var i = 0; i < jyData.length; i++) {
						$("#jiaoYiManagetable").append(
								"<tr class=\"appendClass2\" ><td width=\"30%\"  align=\"center\">"
								+ jyData[i].tx_name + "</td>"
								+"<td width=\"30%\" align=\"center\" >"+ jyData[i].prdt_no+ "</td></tr>");
					}
					
				} else {
					//alert("error");
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
		}
	}
	
	//报表项配置
	function reportManage(){
		var reportlistMap = '${reportlistMap}';
		var pzlistMap_flag = '${pzlistMap_flag}';
		
		if(reportlistMap){
			var reportData = $.parseJSON(reportlistMap);
			
			if (pzlistMap_flag == 'success') {
					$(".appendClass3").remove();
					var	length = reportData.length;
					if (length == 0) {
						$('#reportManageDiv tbody').append('<tr><td style="text-align: center;" colspan="10">暂无数据</td></tr>')
						return;
					}
					for ( var i = 0; i < reportData.length; i++) {
						$("#reportManagetable").append(
								"<tr class=\"appendClass3\" ><td width=\"30%\"  align=\"center\">"
								+ reportData[i].btypeName + "</td>"
								+"<td width=\"30%\" align=\"center\" >"
										+ reportData[i].show_name 
										+ "</td></tr>");
					}
					
				} else {
					//alert("error");
					alert(top.getMessage("FAILED_OPERATION"," "), 0);
				}
		}
	}
	
</script>
</html>