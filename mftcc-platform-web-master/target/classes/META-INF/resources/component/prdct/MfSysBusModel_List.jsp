<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<% 
	String param  = (String)request.getParameter("param");
%>
<!DOCTYPE html>
<html>
<head>
<title><%=request.getAttribute("title") %></title>
</head>
<script type="text/javascript">
	$(function(){
		$(".bigform_content").mCustomScrollbar({
			advanced:{
				updateOnContentResize:true
			}
		});
	});
	var cusNo = '<%=param%>';
	window.name = "curWindow";
			function cancelClick() {
				window.close();
			}
			
			function choseBusModel(lk) 
			{
				var parm=lk.split("?")[1];
				var parmArray=parm.split("&");
				var busModel = parmArray[1].split("=")[1];
				//跳页面
				var url=webPath+"/mfSysKind/input?busModel="+busModel;
				$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
				$(top.window.document).find("#showDialog").remove();

			}
		
		</script>
	<body class="body_bg">
		<div class="bigform_content" style="padding-top: 0px;">
			<div class="content_table">
				<div class="table_show">
					<dhcc:tableTag paginate="parmDiclist" property="tablebusmodel0001" head="true" />
				</div>
			</div>
		</div>
	</body>
</html>