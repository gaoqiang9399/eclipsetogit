<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript">
	
	//var appId = '${appId}';
	var cusNo = '${cusNo}';
	//var pledgeNo = '${pledgeNo}';
	var vouType;
	var pledgeClassNo;
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/mfBusCollateral/selectPledgeImpanwnTypeAjax",//列表数据查询的url
			tableId : "tabledlcollateralnew0010",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30 //加载默认行数(不填为系统默认行数)
		});
	});
	function enterClick(lk) {
		var parm = lk.split("?")[1];
		var parmArray = parm.split("=");
		pledgeClassNo = parmArray[1];
		//跳页面 判断抵押质押
		if(vouType == 3){
			var url = webPath+"/mfBusPledgeDetail/input?cusNo=" + cusNo
				+ "&addWkfFlag=0"
				+ "&pledgeClassNo=" + pledgeClassNo;
		}else if(vouType == 3){
			var url = webPath+"/mfBusPledgeDetail/input?cusNo=" + cusNo
				+ "&addWkfFlag=0"
				+ "&pledgeClassNo=" + pledgeClassNo;
		}
		//var url="${webPath}/mfBusPledge/input?query='0'&cusNo="+cusNo+"&appId="+appId+"&pledgeClassNo="+pledgeClassNo;
		
		openPledgeForm(url);
		//$(top.window.document).find("#showDialog .close").click();
	}

	function openPledgeForm(url) {
		$.ajax({
			url:webPath+"/mfPledgeClass/getPleClassByClassNoAjax",
			data : {
				classNo : pledgeClassNo
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				var formid_new = data.formid_new;
				var formid_old = data.formid_old;
				url = url + '&formid_new=' + formid_new + '&formid_old='
						+ formid_old;
				var obj = $(top.window.document).find("body");
				obj.find("#bigFormShowiframe").attr("src", "");
				obj.find("#bigFormShowiframe").attr("src", url);
				//top.createShowDialog(url,'押品登记','90','90');

			},
			error : function() {
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		})
	}

	function wkfCallBack() {

	}
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=2"/>
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
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
</html>
