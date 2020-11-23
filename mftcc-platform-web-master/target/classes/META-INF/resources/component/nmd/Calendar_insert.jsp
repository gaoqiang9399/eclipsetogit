<%@ page language="java" contentType="text/html; charset=GBK"
	pageEncoding="GBK"%>
<%@ include file="../../include/tld.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>日程新增</title>
		<script src="${webPath}/creditapp/js/sys_manage.js"
			type="text/javascript">
</script>
	</head>
	
	<body>
	<div class="right_bg">
		<div class="right_w">
			<div class="from_bg">
				<div class="right_v">
					<form method="post" theme="simple" name="operform" action="${pageContext.request.webPath}/workCalendar/insert">
						<dhcc:formTag property="formhom2002" mode="query" />
						<div class="from_btn">
						<dhcc:button typeclass="button3" commit="true" value="保存" action="保存"  ></dhcc:button>
						<input type="button" name="back" value="返回" onClick="javascript:self.close()" class="button_form" />					</div>
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
	div.style.width = "150px";
	div.style.height = "20px";
	div.style.position = "absolute";
	div.style.zIndex = 90;
	div.style.backgroundColor = 'C1CDCD';
	var divHtml = "<table><tr><td><select name='hour'>";
	for ( var i = 0; i < 24; i++) {
		var hour = i;
		if (hour < 10) {
			hour = '0' + i;
		}
		divHtml += "<option value='" + hour + "'>" + hour + "</option>";
	}
	divHtml += "</select>点</td><td><select name='min'>";
	for ( var j = 0; j < 60; j++) {
		var min = j;
		if (min < 10) {
			min = '0' + j;
		}
		divHtml += "<option value='" + min + "'>" + min + "</option>";
	}
	divHtml += "</select>分</td><td><input type='button' onclick='getEventTime()' value='确定'/></td></tr></table>";
	div.innerHTML = divHtml;
	document.body.appendChild(div);
}
var eventObj;//点击事件源
//显示层
function showDiv() {
	var objDiv = document.all("eventTime");
	eventObj = window.event.srcElement;
	objLeft = eventObj.offsetLeft;
	objTop = eventObj.offsetTop;
	objParent = eventObj.offsetParent;
	while (objParent.tagName.toUpperCase() != "BODY") {
		objLeft += objParent.offsetLeft;
		objTop += objParent.offsetTop;
		objParent = objParent.offsetParent;
	}
	objDiv.style.top = objTop + 20;
	objDiv.style.left = objLeft;
	
	objDiv.style.display = "block";
	objDiv.style.position = "absolute";
}
//返回选择的时间
function getEventTime() {
	var hour = document.all("hour").value;
	var min = document.all("min").value;
	eventObj.value = hour + ':' + min;
	document.all("eventTime").style.display = "none";
}
</script>
</html>