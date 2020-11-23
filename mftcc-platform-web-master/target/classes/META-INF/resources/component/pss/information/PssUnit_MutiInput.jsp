<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html lang="zh-cmn-Hans">
<head>
<link rel="stylesheet" href="${webPath}/component/pss/general/css/PssParamEntrance.css" />
<style type="text/css">
ul li{
	list-style-type:none;
	display: list-item;
}
.deputyDiv{
	margin-bottom:10px;
}
.deputyName{
	margin-left:3px;
}
.deputRate{
	width:90px;
}
</style>
<script type="text/javascript">
	var webPath = '${webPath}';
	$(function() {
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	});
 	function addUnit(){
 		var rowCount=1;
 		$(".deputyDiv").each(function (){rowCount++;});
 		var myLi=$("<li></li>");
	 	var myDiv=$("<div class='deputyDiv'></div>");
	 	var myA=$("<a class='i i-lajitong' style='cursor: pointer;'></a>");
	 	myA.click(function(){
	 		$(this).parents("li").remove();
	 		var rowNum=1;
	 		$(".deputyDiv").each(function(){
	 			$(this).children("label").text("副单位"+rowNum+"：");
	 			rowNum++;
	 		});
	 	});
	 	var myLab=$("<label id='deputyUnitLabel'>副单位"+rowCount+"：</label>");
	 	var deputyUnit=$("<input type='text' class='deputyName items-btn'>");
	 	var myEq=$("<span style='margin-left:10px;margin-right:10px'>=</span>");
		var deputyRate=$("<input type='text' class='deputRate items-btn'>");				
		myDiv.append(myA).append(myLab).append(deputyUnit).append(myEq).append(deputyRate);				
	 	myLi.append(myDiv);
 		$("#mod-form-rows").append(myLi);
 	};
 	function insertUnit(){
 		var deputyArr = new Array();
 		var baseUnit=new Object();
 		var insertFlag =1;
 		baseUnit.unitName=$("#baseUnit").val();
 		if(!checkStr(baseUnit.unitName)){
 			alert("基本单位不能为空");
 		}else{
	 		baseUnit.relNum=1;
	 		baseUnit.isBase='1';
/*  		var tmpName=baseUnit.unitName+",";
	 		var tmpRate='1:'; */
	 		deputyArr.push(baseUnit);
	 		$(".deputyDiv").each(function(){
	 			var deputy=new Object();
	 			deputy.unitName=$(this).children(".deputyName").val();
	 			deputy.relNum=$(this).children(".deputRate").val();
	 			if(!isint1(deputy.relNum)){
	 				insertFlag=0;
	 				return false;
	 			}
	 			deputy.isBase='0';
	 			if(deputy.unitName&&deputy.relNum){
	 				deputyArr.push(deputy);
	 			};
	 		});
	 		if(insertFlag==1){
	 			addAjax(deputyArr);
	 		}else{
				alert("与基本单位的关系必须为大于0的正整数！",0);	 		
	 		}
 		}
 	};
 	function isint1(str)
	{
	 var result=str.match(/^[1-9]$|^([1-9])([0-9]){0,3}$/);
	 if(result==null) return false;
	 return true;
	};
 	function checkStr(str){
 		if(str==null||str==undefined||str==""){
 			return false;
 		}else if (str.replace(/(^s*)|(s*$)/g, "").length ==0) 
		{ 
			return false;
		} 
 		return true;
 	};
 	function addAjax(arr){
 		var url =webPath+"/pssUnit/insertMutiAjax";
				var dataParam = JSON.stringify(arr);
				LoadingAnimate.start();
				jQuery.ajax({
					url : url,
					data : {
						ajaxData : dataParam
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							alert(top.getMessage("SUCCEED_OPERATION"),1);
							top.groupName=data.groupName;
							top.unitId=data.unitId;
							myclose_showDialogClick();
						} else if (data.flag == "error") {
							alert(data.msg, 0);
						}
					},
					error : function(data) {
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
 	};
</script>
</head>
<body style="background-color:#FFF">
	<div id="manage-wrap" >
		<form id="manage-form">
			<ul>
				<li id="groupUnitName" style="display:none">
					<div>
						<label for="name">多单位名称：</label>
					</div>
					<div>
						<input type="text" class="items-btn" name="name" id="unitGroup">
					</div>
				</li>
				<li>
					<div style="margin-top:30px;">
						<span style="color:red">*</span><label style="margin-left:2px;"
							for="baseUnit">基本单位：</label>
						<input type="text" class="items-btn" id="baseUnit">
						<span style="margin-left:10px">基本单位应为最小度量单位</span>
					</div>
				</li>
			</ul>
			<ul id="mod-form-rows">

			</ul>
			<input type="button" class="ui-btn" id="addDeputy"  onclick="addUnit()" value="增加单位" style="margin-left:40px;">
		</form>
		<div class="formRowCenter">
			<dhcc:pmsTag pmsId="pss-unit-insert">
				<dhcc:thirdButton value="保存" action="保存" onclick="insertUnit()"></dhcc:thirdButton>
			</dhcc:pmsTag>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_showDialogClick()"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>