<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" >
		var archiveStatus = "${archiveStatus}";
		$(function(){
			myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath + "/archiveInfoMain/findCusByPageAjax?archiveStatus=" + archiveStatus, //查询已归档，待封档的数据
				tableId : "tablearchivecusselect", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			});
		});

		function choseCus(parm) {
			parm = parm.split("?")[1];
			var parmArray = parm.split("&");
			var cusNo = parmArray[0].split("=")[1];
			$.ajax({
				url: webPath + "/mfCusCustomer/getCusByIdAjax?cusNo=" + cusNo,
				dataType: "json",
				type: "POST",
				success: function (data) {
					if (data.flag == "success") {
						if (data.listCus == "1") {
							dialog({
								id: 'cusMoreDialog',
								title: "重名客户",
								url: webPath + "/mfCusCustomer/getCusListByName?cusNo=" + cusNo,
								width: 500,
								height: 360,
								backdropOpacity: 0,
								onshow: function () {
									this.returnValue = null;
								},
								onclose: function () {
									if (this.returnValue) {
										//返回对象的属性:实体类MfCusCustomer中的所有属性
										if (typeof(callback) == "function") {
											callback(this.returnValue);
										} else {
											getCusInfoArtDialog(this.returnValue);
										}
									}
									var cusNo1 = $("input[name=cusNo]",parent.document).val();
									if(cusNo1==cusNo){//确认
										choseCus1(cusNo);
									}
								}
							}).showModal();
						} else {
							parent.dialog.get('archiveCusDialog').close(data.cusInfo).remove();
						}
					} else {
						alert(top.getMessage("ERROR_SELECT"));
					}
				}, error: function () {
					alert(top.getMessage("ERROR_DATA_CREDIT", "客户"));
				}
			});
		}
	</script>
</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12">
			<div class="search-div">
			</div>
		</div>
	</div>
	<div class="row clearfix">
		<div class="col-md-12">
			<div id="content" class="table_content" style="height: auto;">
			</div>
		</div>
	</div>
</div>
</body>
</html>
