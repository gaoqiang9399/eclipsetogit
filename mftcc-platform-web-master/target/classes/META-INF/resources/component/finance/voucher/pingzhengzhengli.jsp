<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%-- <%@ include file="/component/include/pub_view_table.jsp"%> --%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>凭证整理</title>
<link rel="stylesheet"
	href="${webPath}/themes/factor/css/search_filter.css" />
<link rel="stylesheet" href="${webPath}/themes/factor/css/list.css" />
<script type="text/javascript"
	src="${webPath}/themes/factor/js/search_filter.js"></script>
<script type="text/javascript" >
	var date="${date}";
</script>
<style>
.divstyle0 {
	/* color: #000000; */
	/* font-size: 14px; */
	height: 60px;
	width: 575px;
	margin: 15px;
	/* background-color: #FFFFFF; */
}

.divstyle1 { /* margin: 10px 5px; */
	position: absolute;
	left: 604px;
	top: 270px;
}

.divstyle6 {
	border: 1px solid #E9EBF2;
	height: 158px;
	width: 575px;
	margin: 15px;
	background-color: #FFFFFF;
}

.divstyle7 {
	height: 70px;
	width: 450px;
	margin: 5px;
	padding-top: 10px;
}

.divstyle8 {
	color: #000000;
/* 	font-size: 14px;  */
	height: 70px;
	width: 450px;
	margin: 5px;
	padding-bottom: 10pz;
}

.divstyle9 {
	color: #000000;
	/* font-size: 12px; */
	height: 80px;
	width: 100px;
	margin: 10px 5px;
	position: relative;
	top: -70px;
	left: 455px;
}

.divstyle10 {
	color: #000000;
	/*   font-size: 12px; */
	height: 60px;
	width: 215px;
	margin: 5px;
}

.divstyle11 {
	color: #000000;
	/* font-size: 12px; */
	height: 60px;
	width: 215px;
	margin: 10px;
	position: relative;
	left: 220px;
	top: -70px;
}
.divstyle12{
	color: #000000;
	margin: 15px;
	font-size: 16px;
}
.span1,.span2{
	font-size: 16px; 
	color:#B5B8BD;
}

.span3,.span4,.span5{
	padding-right: 10px; 
	/* padding-left: 10px; */
	padding-top : 10px;
	/* border: 1px solid #E9EBF2; */
}

.colorSty{
	color:#32B5CB;
}
 .divstyle01{
 margin-top:10px; 
} 
.pzspansty{
	width: 80px; display: inline-block;
}
.pzdivsty{
	height: auto; width: 600px;display:none;
}
</style>
<script type="text/javascript" src="${webPath}/component/finance/voucher/js/pingzhengzhengli.js"></script>

</head>
<body style="background-color: #F0F5FB" class="overflowHidden">
	<div class="scroll-content">

	<div class="divstyle0">
		<div class="divstyle01">
			<span class="span1">凭证整理</span>
		</div>
		<div class="divstyle01">
			<span class="span2"> <span class="span3 colorSty">1、检查</span> →<span class="span4">2、整理</span> → <span class="span5">3、完成</span> <span>
		</div>

	</div>
	<div class="divstyle6" id="maindivstyle">
		<div class="divstyle7">
			<div class="divstyle10">
				<span class="txt">整理范围： </span> <input type="text"
					class="form-control vch-date mgr pzspansty" id="weeks" value=""
					onclick="laydatemonth(this);"><span class="txt">期</span>
			</div>
			<div class="divstyle11">
				<span class="txt">凭证字：</span> <select
					class="form-control vch-select mgr pzspansty" name="pz_type" id="pz_type">
					<option value="0">全部</option>
				</select>
			</div>
		</div>
		<div class="divstyle8">
			<div class="divstyle7">
				<div class="divstyle10">
					<input type="radio" name="rbb" id="radio1" value="1" checked /> <span
						class="txt">按凭证号顺次前移补齐断号</span>
				</div>
				<div class="divstyle11">
					<input type="radio" name="rbb" id="radio2" value="2" /> <span
						class="txt">按凭证日期重新顺次编号</span>
				</div>
			</div>
			<div class="divstyle9">
				<button type="button" class="btn btn-info"
					onclick="checkPingzheng()">检查</button>
			</div>
		</div>
	</div>
	<div id="zhenglitableDiv" class="table_content pzdivsty">

		<table id="pzInfotable" width="100%" border="0" align="center"
			cellspacing="1" class="ls_list" title="pingzhengzhengli">
			<thead>
				<tr>
					<th scope="col" width="30%" align="center" name="accNo">原凭证</th>
					<th scope="col" width="30%" align="center" name="accName">整理后的凭证号</th>

				</tr>
			</thead>
			<tbody id="tab">
			<!-- 	<tr>
					<td width="30%" align="center">共1t</td>
					<td width="30%" align="center">共同类</td>
				</tr> -->
				
			</tbody>
		</table>
	</div>
	<div class="divstyle1" style="display:none;" id="beginzhengliDiv">
		<button type="button" class="btn btn-info" onclick="beginZhengli()">开始整理</button>
	</div>
	<div class="divstyle12" id="returnListdiv" style="display:none;">
		<a href="${webPath}/cwVoucherMst/getListPage" pagetype="qian2" >返回凭证列表</a>
	</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		//loadedpz();
		PzzhengliJs.init();
		
		$("#weeks").val(date);
		//自定义滚动条
		$(".page-voucher").mCustomScrollbar({
			advanced:{
				theme:"minimal-dark",
				updateOnContentResize:true
			}
		});
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
		//$("#maindivstyle").hide();
	});
	
</script>
</html>