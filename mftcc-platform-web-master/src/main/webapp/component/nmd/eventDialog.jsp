<%@ page language="java" contentType="text/html; charset=UTF-8"  pageEncoding="UTF-8"%>
<%@page import="cn.mftcc.util.WaterIdUtil"%>
<%@page import="app.base.User"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>新建日程</title>
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
	
	.dialog_select_css{
	    display: inline-block;
	    width:66%;
	    font-size: 12px;
	    border-radius: 4px;
	    margin: 0px;
	    height:34px;
	}
	.eventtitle{
		height:110px;
	}
	/*
	#the-title{
		height:110px;
	}
	*/
	#scheduleTitle{
		height:105px;
	}
</style>
<script type="text/javascript">
	function getShareNameDialog(userInfo) {
		$("input[name=shareName]").val(userInfo.opName+":"+userInfo.opNo);
	};
    function minEndDate() {
        var eventDate = $("input[name=eventDate]").val();
        var currentDay = new Date();
        //转换日期格式为2018-01-01（string）
        var year = currentDay.getFullYear();
        var mm = "0"+(currentDay.getMonth()+1);
        var day = "0"+currentDay.getDate();
        var currentdayString = year + "-" + mm.substring(mm.length-2,mm.length) + "-" + day.substring(day.length-2,day.length);
        if(eventDate==""||eventDate==null){
            return currentdayString;
        }else{
            return eventDate;
        }
    };
	function maxStartDate() {
        var endDate = $("input[name=endDate]").val();
		if(endDate==""||endDate==null || endDate == today){
            return null;
        }else {
            return endDate;
        }
    };
</script>
</head>
<body>
	<div id="newEvent" class="container" style="display: none;">
		<div class="row">
			<form id="eventForm" action="">
				<div class="row">
					<div class="form-group">
						<lable> </lable>
						<label class="control-label"><span class='required'>*</span>标题:</label>
						<div class="input-box title" id="the-title">
							<textarea name="title" id="scheduleTitle" class="form-control eventtitle" placeholder="请输入日程内容" height="110px"></textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<label class="control-label">类型:</label>
						<div class="input-box">
							<select id="className" name="eventType" class="form-control">
								<!-- <option value="1">类型1</option>
								<option value="2">类型2</option>
								<option value="3">类型3</option>
								<option value="4">类型4</option>
								<option value="0">其他</option> -->
								<option value="1">工作计划</option>
								<option value="2">日程安排</option>
								<option value="3">工作记事</option>
								<option value="4">备忘录</option>
								<option value="5">个人日程</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<label class="control-label "><span	class='required'>*</span>开始:</label>
						<div class="input-box start-time">
							<%--<input id="startdate" type="text" title="开始日期" name="eventDate" class="form-control datepicker" datatype="0" onclick="fPopUpCalendarDlg(this);return false;" onblur="func_uior_valTypeImm(this);">--%>
								<input id="startdate" type="text" title="开始日期" name="eventDate" class="form-control datepicker" datatype="0" onclick="selectrili(this,null,today,maxStartDate());" onblur="func_uior_valTypeImm(this);">
							<select	class="form-control timepicker" id="starthour"></select>
							<select class="form-control timepicker" id="startmin"></select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<label class="control-label"><span	class='required'>*</span>结束:</label>
						<div class="input-box end-time">
							<%--<input id="enddate" type="text" title="开始日期" name="endDate" value="" class="form-control datepicker" datatype="0" onclick="fPopUpCalendarDlg(this);return false;" onblur="func_uior_valTypeImm(this);">--%>
							<input id="enddate" type="text" title="开始日期" name="endDate" value="" class="form-control datepicker" datatype="0" onclick="selectrili(this,null,minEndDate());" onblur="func_uior_valTypeImm(this);">


							<select class="form-control timepicker" id="endhour"></select> 
							<select class="form-control timepicker" id="endmin"></select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<label class="control-label">重复周期:</label>
						<div class="input-box">
							<select id="repetition" name="repetition" class="dialog_select_css">
								<!-- <option value="1">不重复</option>
								<option value="2">每日重复</option>
								<option value="3">每周重复</option>
								<option value="4">每月重复</option>
								<option value="5">每年重复</option> -->
								<option value="1">一次性活动</option>
								<option value="2">每天</option>
								<option value="3">每周(每周的星期一)</option>
								<option value="4">每月(1日)</option>
								<option value="5">每年(7月1日)</option>
							</select>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="form-group">
						<label class="control-label wran-label">提醒时间（小时）:</label>
						<div class="input-box">
							<input type="text" class="form-control wran-input" id="warnTime" maxlength="5" name="warnTime" placeholder="距离日程开始时间(不能超过5位数)" onblur="func_uior_valFormat_tips(this,'nonnegative');" onkeyup="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}" onafterpaste="if(this.value.length==1){this.value=this.value.replace(/[^1-9]/g,'')}else{this.value=this.value.replace(/\D/g,'')}">
						</div>
					</div>
				</div>
				<%--<div class="row">
					<div class="form-group">
						<label class="control-label">共享人:</label>
						<div class="input-box">
							<input type="text" class="form-control share" id="location" name="shareName" placeholder="请选择共享人" onclick="selectUserDialog(getShareNameDialog);" readonly="readonly">
						</div>
					</div>
				</div>--%>
				<!-- 隐藏域，为登记人和事件编号赋值-->
				<div class="hidden" style="display: none;">
					<input name="makeMan" value="<%=User.getRegNo(request)%>" /> 
					<input name="calendarNo" value="<%=WaterIdUtil.getWaterId()%>" /> 
					<input name="eventTime" value="" /> 
					<input name="endTime" value="" />
				</div>
			</form>
		</div>
		<div class="row">
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="insert-event" typeclass="insertAjax"></dhcc:thirdButton>
				<dhcc:thirdButton value="删除" action="删除" typeclass="delete hidden" onclick=""></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="artdialog.close()"></dhcc:thirdButton>
			</div>
		</div>
	</div>
</body>
</html>