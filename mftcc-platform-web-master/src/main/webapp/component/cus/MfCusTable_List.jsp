<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		
	</head>
	<body class="body_bg">
		<div class="bigform_content">
		<div class="content_table">
			<dhcc:tableTag paginate="mfCusTableList" property="tablecustable00001" head="true" />
		</div>
    </div>
	</body>	
	<script type="text/javascript" >
		function enterClick(lk){
			var cusNo = top.cusNo;
			var parm=lk.split("?")[1];
			var parmArray=parm.split("&");
			var nameZn = parmArray[0].split("=")[1];
			var action = parmArray[1].split("=")[1];
			var showType = parmArray[2].split("=")[1];
			var isMust = parmArray[3].split("=")[1];
			//处理action为controller;
			action = webPath+"/"+action.substring(0,1).toLowerCase()+action.substring(1);
			action =action.replace("Action","");
			top.title = nameZn;
			top.showType = showType;
			top.action = action;
			top.name = action;
			top.isMust = isMust;
			var actionUrl = action + "/input?cusNo="+cusNo;
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
</html>
