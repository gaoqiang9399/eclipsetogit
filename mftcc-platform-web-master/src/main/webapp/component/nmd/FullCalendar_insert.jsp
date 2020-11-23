<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>日程新增</title>
		<!-- 滚动轴 -->
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mousewheel-3.0.6.js"></script>
		<script type="text/javascript" src="${webPath}/UIplug/customScrollbar/js/jquery.mCustomScrollbar.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/customScrollbar/css/jquery.mCustomScrollbar.min.css" />
		<script type="text/javascript" src="${webPath}/layout/view/js/warns.js" ></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$('body').mCustomScrollbar({
					theme:"minimal-dark"
				});
			});
			function closePar(data){
				if(data.hasOwnProperty('workCalendar')){
	  				window.top.recWarnd.insertData(data.workCalendar);
	  			}
				parent.relaod(data);
			}
		</script>
		<style type="text/css">
			#eventTime td {
			}
			#eventTime table{
			}
			#eventTime tr{
			}
			#eventTime input[type=button]{
				border: 1px solid #4dbaeb;
				background-color: #ffffff;
				vertical-align: middle;
				margin-left: 10px;
				font-size:14px;
				border-radius: 3px;
				width: 60px;
			    height: 24px;
			    line-height: 20px;
			}
			#eventTime select{
				border: 1px solid #4dbaeb;
				background-color: #ffffff;
				vertical-align: middle;
				margin: 0px;
				border-radius: 3px;
				width: 60px;
			    height: 24px;
			    line-height: 20px;
			    padding: 0px;
			    text-align: center;
			}
			.formRow {
		   	 	margin: 15px auto;
		    }
		</style>
	</head>
	
	<body>
	<div class="right_bg">
		<div class="right_w">
			<div class="from_bg">
				<div class="right_v">
					<form method="post" theme="simple" name="operform" action="${webPath}/workCalendar/fullCalendarInsertAjax">
<%-- 					<s:form method="post" theme="simple" name="operform" action="${webPath}/workCalendar/fullCalendarInsert"> --%>
						<dhcc:formTag property="formhom2012" mode="query" />
						<div class="formRow">
						<dhcc:thirdButton value="提交" action="提交" onclick="ajaxInsert(this.form,closePar)"></dhcc:thirdButton>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">
init();
//初始化层数据
function init() {
	var div = document.createElement("div");
	div.id = "eventTime";
	div.style.display = "none";
	div.style.position = "fixed";
	div.style.zIndex = 90;
	var divHtml = "<table><tr><td><select name='hour'>";
	for ( var i = 0; i < 24; i++) {
		var hour = i;
		if (hour < 10) {
			hour = '0' + i;
		}
		divHtml += "<option value='" + hour + "'>" + hour + "</option>";
	}
	divHtml += "</select></td><td>:</td><td><select name='min'>";
	for ( var j = 0; j < 60; j++) {
		var min = j;
		if (min < 10) {
			min = '0' + j;
		}
		divHtml += "<option value='" + min + "'>" + min + "</option>";
	}
	divHtml += "</select></td><td><input type='button' onclick='getEventTime()' value='确定'/></td></tr></table>";
	div.innerHTML = divHtml;
	document.body.appendChild(div);
}
var eventObj;//点击事件源
//显示层
function showDiv() {
	var objDiv = document.getElementById("eventTime");
	eventObj = window.event.srcElement?window.event.srcElement : window.event.target;
	objLeft = $(eventObj).offset().left;
	objTop = $(eventObj).offset().top;
	objDiv.style.top = objTop+eventObj.offsetHeight;
	objDiv.style.left = objLeft;
	objDiv.style.display = "block";
	objDiv.style.position = "fixed";
	bindEvent(document, 'mouseup', function(ev){
		var eObj = ev.srcElement?ev.srcElement : ev.target;
		if($(eObj).parents("#eventTime").length==0){
	        if(objDiv && objDiv.style.display !== 'none'){
	        	objDiv.style.display= 'none';
	        }
		}
    })
}

//事件监听器
bindEvent = function(elem, even, fn){
    elem.attachEvent ? elem.attachEvent('on'+ even, function(){
        fn.call(elem, win.even);
    }) : elem.addEventListener(even, fn, false);
};

//返回选择的时间
function getEventTime() {
	var hour = document.all("hour").value;
	var min = document.all("min").value;
	eventObj.value = hour + ':' + min;
	document.getElementById("eventTime").style.display = "none";
}
</script>
</html>