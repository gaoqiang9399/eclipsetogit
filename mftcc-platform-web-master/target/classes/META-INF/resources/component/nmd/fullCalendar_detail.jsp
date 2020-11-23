<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%@ include file="/component/include/pub_view.jsp"%>
<%
	String id = request.getParameter("calendarNo");
%>
<!DOCTYPE html>
<html>
	<head>
		<title>日程修改</title>
		<script type="text/javascript" src="${webPath}/layout/view/js/warns.js" ></script>
		<script type="text/javascript">
			$(document).ready(function(){
				$('body').mCustomScrollbar({
					theme:"minimal-dark"
				});
			});
			function closePar(data){
				if(data.hasOwnProperty('workCalendar')){
	  				window.top.recWarnd.updateData(data.workCalendar);
	  			}
				parent.relaod(data);
			}
			function closeDelPar(data){
				if(data.hasOwnProperty('workCalendar')){
	  				window.top.recWarnd.deleteData(data.workCalendar);
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
						<form method="post" theme="simple" name="operform" action="${pageContext.request.webPath}/workCalendar/fullCalendarUpdateAjax">
							<dhcc:formTag property="formhom2013" mode="query" />
							<div class="formRow">
								<input type="button" name="save" value="保存" onClick="ajaxDateUpdate(this.form)"/>
								<input type="button" name="del" value="删除" onClick="ajaxDateDelete(this.form,'${pageContext.request.webPath}/workCalendar/fullCalendarDeleteAjax',closeDelPar)"/>
                         	</div>
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
		//关闭当前窗口，刷新父窗口
/* 		function func_close(){
			self.close();
		}
		function updateCalendar(frm){
			frm.action = "${webPath}/workCalendar/fullCalendarupdate";
			frm.submit();
			alert(top.getMessage("SUCCEED_SAVE"));
			self.close();
			window.opener.location.reload();
		}
		function delCalendar(frm){
			frm.action = "${webPath}/workCalendar/del";
			frm.submit();
			alert("删除成功");
			self.close();
			window.opener.location.reload();
		} */
		
		
		var ajaxDateUpdate = function(obj){
			var flag = submitJsMethod(obj, '');
			if(flag){
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				jQuery.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:"POST",
					dataType:"json",
					beforeSend:function(){  
					},success:function(data){
						if(data.flag == "success"){
							window.top.alert(top.getMessage("SUCCEED_OPERATION"),1);
							closePar(data);
						}else if(data.flag=="error"){
							if(data.flag!==undefined&&data.flag!=null&&data.flag!=""){
								window.top.alert(data.msg,0);
							}else{
								window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
							}
						}
					},error:function(data){
						window.top.alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			}
		}
		var ajaxDateDelete = function(obj,url,callback){
			var calendarNo = $("input[type=hidden][name=calendarNo]").val();
			window.top.alert("是否确认删除!",2,function(){
				jQuery.ajax({
					url:url,
					data:{calendarNo:calendarNo},
					type:"POST",
					dataType:"json",
					success:function(data){
						if(data.flag == "success"){
							 alert(top.getMessage("SUCCEED_OPERATION"),1);
							 callback.call(this);
						}
					},error:function(data){
						 alert(top.getMessage("FAILED_OPERATION"," "),0);
					}
				});
			});
		}
	</script>
</html>