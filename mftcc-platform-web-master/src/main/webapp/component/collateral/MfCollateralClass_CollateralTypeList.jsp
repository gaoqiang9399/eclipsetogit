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
	console.log(cusNo);
	var cusName = '${cusName}';
	console.log(cusName);
	var appId = '${appId}';
	console.log(appId);
	var classId;
	var vouType;
	var className;
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url:webPath+"/mfCollateralClass/selectCollateralTypeAjax",//列表数据查询的url
			tableId : "tabledlcollateralclass0002",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			pageSize : 30 //加载默认行数(不填为系统默认行数)
		});
	});
	function enterClick(lk) {
		var parm = lk.split("?")[1];
		var parmArrays = parm.split("&");
		var parmArray1 = parmArrays[0].split("=");
		classId = parmArray1[1];
		//alert(classId);
		var parmArray2 = parmArrays[1].split("=");
		vouType = parmArray2[1];
		//alert(vouType);
		var parmArray3 = parmArrays[2].split("=");
		className = parmArray3[1];
				
		//var url="${webPath}/mfBusPledge/input?query='0'&cusNo="+cusNo+"&appId="+appId+"&pledgeClassNo="+pledgeClassNo;
		
		//var url = webPath+"/pledgeBaseInfo/collateralClassInput?cusNo=" + cusNo
		var url = webPath+"/pledgeBaseInfo/InputInsert?cusNo=" + cusNo
				+ "&cusName=" + cusName
				+ "&appId=" + appId
				+ "&addWkfFlag=0"
				+ "&classId=" + classId
				+ "&vouType=" + vouType
				+ "&className=" + className;
		//alert(url);
		
		//openPledgeForm(url);
		//$(top.window.document).find("#showDialog .close").click();
		window.location.href=url;  
	}

	function openPledgeForm(url) {
		LoadingAnimate.start();
		$.ajax({
			url:webPath+"/mfCollateralClass/getCollateralClassByClassNoAjax",
			data : {
				classId:classId,
				className:className,
				vouType:vouType,
				cusNo:cusNo,
				cusName:cusName
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop();
				var formid_new = data.formid_new;
				var formid_old = data.formid_old;
				var pledgeImpawnNo = data.pledgeImpawnNo;
				url = url + '&formid_new=' + formid_new + '&formid_old='
						+ formid_old +"&collateralNo=" + pledgeImpawnNo;
				//var obj = $(top.window.document).find("body");
				//obj.find("#bigFormShowiframe").attr("src", "");
				//obj.find("#bigFormShowiframe").attr("src", url);
				//alert(url);
				//window.location.href = url;
				//top.createShowDialog(url,'押品登记','90','90');
				$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
				$(top.window.document).find("#showDialog").remove();

			},
			error : function() {
				alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
			}
		});
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
					<jsp:include page="/component/include/mySearch.jsp?blockType=4"/>
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
