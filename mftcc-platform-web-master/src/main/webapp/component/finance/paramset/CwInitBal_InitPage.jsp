<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<title>Insert title here</title>
<style type="text/css">
#yhtOl li{display:inline;}
.actv{
	color:#32b5cb;
	font-size:18px;
	font-weight: bold;
	}
.initBal{
	font-size: 15px;width: 700px;height: 300px;margin-left: 40px;
	}
.WenXinTS{
	font-weight: bolder;font-size: 18px;height: 30px;padding-top: 20px
	}
.linkDownload{
	margin-left: 20px;
}
.TStext{
	margin-left: 20px;height: 75px;padding-top: 20px
}
.nextStep{
	float:right;padding-right: 50px
}
.inputFormFile{
	width: 300px;
}
.nextStepUp{
	padding-top: 55px;float:right;padding-right: 50px
}
#DivB{
	padding-top: 60px;
}
#file{
	display: none;
}
#TsDaoRu{
	float: left;padding-top: 10px;padding-left: 20px
}
#showFile{width: 280px;height: 33px;margin-top: 5px}
#successUp{padding-left: 40px;padding-top: 60px}
</style>
</head>
<script type="text/javascript">
var ifShow="${ifShow}";
var listType = '${param.listType}';

var msg="${msg}";
function downloadFile(){
	window.location.href=webPath+"/cwInitBal/downloadExcel";
}
function uploadFile(){
	if($("#file").val()==null||$("#file").val()==""){
		alert(top.getMessage("FIRST_SELECT_FIELD","文件后再试"), 1);
// 		window.top.alert("请选择文件后再试！", 1);
	return;
	}
	var fileStype=$("#file").val().substr($("#file").val().indexOf(".")+1,3);
	if(fileStype!="xls"){
		alert(top.getMessage("FIRST_SELECT_FIELD","xls类型的文件"), 1);
// 		window.top.alert("请选择.xls类型的文件!", 1);
		return;
	}
	document.getElementById("form").submit();
}
function selectFile(){
	$("#file").click();
}

function next(id){//点击上下步
	document.getElementById("form").reset();
	document.getElementById('initA').style.display = "none";
	document.getElementById('initB').style.display = "none";
	document.getElementById('initC').style.display = "none";
	$("#initA1").removeClass("actv");
	$("#initB1").removeClass("actv");
	$("#initC1").removeClass("actv");
	document.getElementById(id).style.display = "block";
	$("#"+id+"1").addClass("actv");
}

$(function(){
	if(listType=='1'){
		$('#backList').removeClass('hidden');
	}
	if(ifShow!="true"){//直接进页面或者没导入成功的情况  A显示
		if(ifShow=="false"){
			window.top.alert(top.getMessage("SUCCEED_UPLOAD")+msg, 2);
		}
		document.getElementById('initA').style.display = "block";
		document.getElementById('initC').style.display = "none";
		$("#initA1").addClass("actv");
	}else{//导入成功，C显示
		document.getElementById('initC').style.display = "block";
		document.getElementById('initA').style.display = "none";
		window.top.alert(top.getMessage("SUCCEED_UPLOAD"), 1);
// 		window.top.alert("导入成功！", 1);
		$("#initC1").bind("click",function(){//导航  C 解锁 
			next('initC');
		});
		$("#initC1").addClass("actv");
	}
	
	document.getElementById('initB').style.display = "none";
	$('#file').change(function(){//当选择文件的输入框值变化时，显示文件名的输入框变化
		if($("#file").val()!=null&&$("#file").val()!=""){
			$('#showFile').val($("#file").val());
		}
	}) 
})

function back(){
	window.location.href = webPath+"/cwInitBal/getInitPage";
}
</script>
<body>
<div style="margin-top: 30px">
<ol id="yhtOl">
    <li id="initA1" onclick="next('initA')">1.下载模板</li>&nbsp;&nbsp;>&nbsp;&nbsp;
    <li id="initB1" onclick="next('initB')">2.导入Excel</li>&nbsp;&nbsp;>&nbsp;&nbsp;
    <li id="initC1">3.导入结束</li>
</ol>
</div>
<div id="initA" class="initBal">
	<p class="WenXinTS">温馨提示：</p>
	<p class="TStext">请下载统一的模版，并按相应的格式在Excel软件中填写您的业务数据，然后再导入到系统中。</p>
	<a href="javascript:void(0)" onclick="downloadFile()" class="linkDownload">下载模版</a><div></div>
	<div class="nextStep">
		<input type="button" value="返回" onclick="back()" id="backList" class="btn btn-default hidden"/><span>&nbsp;&nbsp;</span>
		<input type="button" value="下一步" onclick="next('initB')" class="btn btn-primary"/>
	</div>
</div>
<div id="initB"  class="initBal">
<div id="DivB">
	<form enctype="multipart/form-data" id="form" method="post"  action="${webPath}/cwInitBal/uploadExcel">  
			<input type="file"  name="file1" id="file">
			  <div id="TsDaoRu"><Strong>请选择要导入的Excel文件：</Strong></div>
			<div onclick="selectFile()">
                 <input type="text" id="showFile" readonly="readonly">
                 <button class="btn btn-default" type="button">浏览</button>
             </div>
	</form> 
</div>
	<div class="nextStepUp">
		<input type="button" value="上一步" onclick="next('initA')" class="btn btn-default"/><span>&nbsp;&nbsp;</span>
		<input type="button" value="导&nbsp;&nbsp;入" onclick="uploadFile()" class="btn btn-primary"/>
	</div>
</div>
<div id="initC"  class="initBal">
	<div id="successUp">
		<Strong>财务余额初始化完成!</Strong><a onclick="back();">&nbsp;&nbsp;浏览导入结果</a>
		
	</div>
</div>
</body>
</html>