<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@page import="cn.mftcc.util.WaterIdUtil"%>
<%@page import="app.base.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
<style type="text/css">
.hidden{
	display: none;
}
.delete{
	background: #fff !important;
    color: #000 !important;
    border: 1px solid #d2d3d6 !important;
}

.delete:HOVER{
	background: #e7e7e7 !important;
    color: #000 !important;
}
</style>
</head>
<body>
	<div id="newEvent" style="display: none;">
		<form id="eventForm" action="">
			<div class="row">
				<div class="form-group">
				
					<!--
					<div class="input-box title">
						<textarea name="title" id="title" class="form-control eventtitle"
							placeholder="请输入节假日的名称"></textarea>
					</div>
					-->
					<label class="control-label ">假日名称</label>
					<div class="input-box start-time">
						<input id="title" type="text" title="title" name="title"
							class="form-control datepicker" >
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label class="control-label ">开始:</label>
					<div class="input-box start-time">
						<input id="startdate" type="text" title="开始日期" name="eventDate"
							class="form-control datepicker" datatype="0"
							onclick="fPopUpCalendarDlg(this);return false;"
							onblur="func_uior_valTypeImm(this);">
					</div>
				</div>
			</div>
			<div class="row">
				<div class="form-group">
					<label class="control-label">结束:</label>
					<div class="input-box end-time">
						<input id="enddate" type="text" title="开始日期" name="endDate" value="";
							class="form-control datepicker" datatype="0"
							onclick="fPopUpCalendarDlg(this);return false;"
							onblur="func_uior_valTypeImm(this);">
					</div>
				</div>
			</div>
			<!-- 隐藏域，为登记人和事件编号赋值-->
			<div class="hidden" style="display: none;">
				<input name="makeMan" value="<%=User.getRegNo(request)%>" /> <input
					name="calendarNo" value="<%=WaterIdUtil.getWaterId()%>" /> <input
					name="eventTime" value="" /> <input name="endTime" value="" />
			</div>
		</form>
		<div class="formRowCenter background-border-none">
			<dhcc:thirdButton value="保存" action="insert-event"
				typeclass="insertAjax"></dhcc:thirdButton>
			<dhcc:thirdButton value="删除" action="删除" typeclass="delete hidden" onclick=""></dhcc:thirdButton>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="artdialog.close()"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>