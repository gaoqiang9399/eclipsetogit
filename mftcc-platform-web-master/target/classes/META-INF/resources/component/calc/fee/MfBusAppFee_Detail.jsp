<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		
	</head>
		<script type="text/javascript">
	var options;
	var fincSts = '${fincSts}';
	$(function(){
		options = $("select[name=standard]").find("option");
		var takeNode = $("select[name=takeNode]").val();  
		if (takeNode == "1") {
			makeOptionsJQ(options, '1');
			$("select[name=standard]").val("1");
		} else if (takeNode == "2") {
			makeOptionsJQ(options, '1,2,3');
			$("select[name=standard]").val("1");
		}else if (takeNode == "3") {
			makeOptionsJQ(options, '1,4');
			$("select[name=standard]").val("1");
		}else if (takeNode == "4") {
			makeOptionsJQ(options, '1,5');
			$("select[name=standard]").val("1");
		}else if (takeNode == "5") {
			makeOptionsJQ(options, '1,6');
			$("select[name=standard]").val("1");
		}
		//makeOptionsJQ(options, '1');
		//$("select[name=standard]").val("1");
		
		
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				theme:"minimal-dark",
// 				updateOnContentResize:true
// 			}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	});
	function getStandard(){
		var takeNode = $("select[name=takeNode]").val();  
		if (takeNode == "1") {
			makeOptionsJQ(options, '1');
			$("select[name=standard]").val("1");
		} else if (takeNode == "2") {
			makeOptionsJQ(options, '1,2,3');
			$("select[name=standard]").val("1");
		}else if (takeNode == "3") {
			makeOptionsJQ(options, '1,4');
			$("select[name=standard]").val("1");
		}else if (takeNode == "4") {
			makeOptionsJQ(options, '1,5');
			$("select[name=standard]").val("1");
		}else if (takeNode == "5") {
			makeOptionsJQ(options, '1,6');
			$("select[name=standard]").val("1");
		}
	};
	function check(){
		
	};
	function ajaxUpdateThis(obj){
		var flag = submitJsMethod($(obj).get(0), '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			$.ajax({
				url:url,
				data:{ajaxData:dataParam,tableId:top.tableId},
				type:'post',
				dataType:'json',
				success:function(data){
					if(data.flag == "success"){
						  window.top.alert(data.msg,1);
						 if(data.htmlStrFlag == "1"){
						  top.htmlStrFlag = true;
						  top.htmlString = data.htmlStr;
					  }
					 // myclose_click();
					  //alert($(top.window.document).find(".dhccModalDialog").length);
					 $(top.window.document).find(".dhccModalDialog").find(".i-x5").click();
					  
					  /*
					  if(callback&&typeof(callback)=="function"){
						  callback.call(this,data);
					  }
					  */
					}else{
						  window.top.alert(top.getMessage("FAILED_UPDATE"),1);
					}
				},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", ""));
				}
			}); 
		}
	};
	//费率值不能输入负数  
	function controlFeeRate(){
		var rateScaleVal = $("input[name='rateScale']").val();
		if(rateScaleVal>="0"){
			
		}else{
			$("input[name='rateScale']").val("");
		}
		
	}
	
	$(function(){
		if(fincSts != null && fincSts >="5"){
			$(".formRowCenter").children(":first").hide();
		}
	});
	
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
	            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
				
					<form  method="post" id="busFeeForm" theme="simple" name="operform" action="${webPath}/mfBusAppFee/updateAjax">
						<dhcc:bootstarpTag property="formbusfee0001" mode="query"/>
					</form>
					
					</div>
				</div>
			</div>	
		
		<div class="formRowCenter">
   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxUpdateThis('#busFeeForm');"></dhcc:thirdButton>
   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
   		</div>
   	</div>
	</body>
</html>