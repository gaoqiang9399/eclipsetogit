<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
	</head>
	<script type="text/javascript">
	var options;
	$(function(){
		options = $("select[name=idType]").find("option");
		getIdType();
	});
	//三证合一和证件号码联动
	function getIdType(){
		var isThreeToOne = $("select[name=isThreeToOne]").val();
		if (isThreeToOne == "0") {
			makeOptionsJQ(options, 'A,C');
			$("select[name=idType]").val("A");
		} else if (isThreeToOne == "1") {
			makeOptionsJQ(options, 'B');
			$("select[name=idType]").val("B");
		}
		idTypeChange();
	};
	function selectAreaCallBack(areaInfo){
		$("input[name=commAddress]").val(areaInfo.disName);
	};
	function idTypeChange(){
		var idType = $("select[name=idType]").val()
		if(idType=="A"){
			$("input[name=idNum]").attr("alt","organ");
		}else if(idType=="B"){
			$("input[name=idNum]").attr("alt","credit");
		}else if(idType=="C"){
			$("input[name=idNum]").attr("alt","licence");
		}
		$("input[name=idNum]").val("");
		}
	//表单保存
	window.cusInfoInsert = function(obj,callback){
			var flag = submitJsMethod(obj, '');
			if(flag){
				var contentForm = $(obj).parents(".bigForm_content_form");
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray()); 
				$.ajax({
					url:url,
					data:{ajaxData:dataParam},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.flag == "success"){
							  window.top.alert(data.msg,1);
							  var cusNo = data.cusNo;
							  var cusType=data.cysType;
							  var url=webPath+"/mfCusCustomer/getById?cusNo="+cusNo+"&cusType="+cusType;
							  $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
							  myclose();
						}
					},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
					}
				}); 
		}
	}		
	</script>
<body class="overflowAuto body_bg">
	<div class="bigform_content">
		<!-- <div class="bigForm_content_form"> -->
			<form  method="post" theme="simple" name="operform"
				action="${webPath}/mfCusCustomer/insertForBusAjax">
				<dhcc:bigFormTag property="formcuscorpbase0002" mode="query" />
			
				<div class="formRowCenter">
					<dhcc:thirdButton value="保存" action="保存"
						onclick="cusInfoInsert(this.form);"></dhcc:thirdButton>
				</div>
			</form>
		<!-- </div> -->
	</div>
</body>
</html>
