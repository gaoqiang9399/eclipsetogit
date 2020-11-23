<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		function enterClick(lk){
			var cusNo = top.cusNo;
			var parm=lk.split("?")[1];
			var parmArray=parm.split("&");
			var nameZn = parmArray[0].split("=")[1];
			var nameEn = parmArray[1].split("=")[1];
			//处理action为controller;
			nameEn = "/"+action.substring(0,1).toLowerCase()+action.substring(1);
			nameEn =action.replace("Action","");
			if(nameEn == "mfCusStaff"){
				top.actionUrl = nameEn + "/getById?cusNo="+cusNo;
			}else if(nameEn == "MfCusBankAccManageAction"){
				top.actionUrl = nameEn + "/getListPageBig?cusNo="+cusNo;
			}
			var actionUrl = nameEn + "/input?cusNo="+cusNo;
			//top.openBigForm(actionUrl,nameZn);
			var obj = $(top.window.document).find("body");
			//obj.find("#bigFormShow").find(".modal-dialog").css({"width":width,"height":height});
			obj.find("#bigFormShowiframe").attr("src","");
			obj.find("#bigFormShowiframe").attr("src",actionUrl);
			
			top.nameZn = nameZn;
			obj.find("#bigFormShow #myModalLabel").html(nameZn);
			obj.find("#bigFormShow").modal({
		        backdrop:false,
		        show:true,
		        keyboard: false
		    });
		};
		</script>
	</head>
	<body>
	<div class="bigform_content">
		<div class="content_table">
			<dhcc:tableTag property="tablecusfcon00002" paginate="mfCusFormConfigList" head="true"></dhcc:tableTag>
		</div>
    </div>
	</body>	
</html>
