<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
		<script type="text/javascript" src='${webPath}/component/calc/fee/js/MfSysFeeItem.js'> </script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
						<div class="bootstarpTag">
			            	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form  method="post" id="feeItemDetailForm" theme="simple" name="operform" action="${webPath}/mfSysFeeItem/updateAjax">
								<dhcc:bootstarpTag property="formfeeitem0003" mode="query"/>
							</form>	
						</div>
				</div>
			</div>
			<div class="formRowCenter">
	   			 <dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#feeItemDetailForm');"></dhcc:thirdButton> 
	   			 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
	   		</div>
   		</div>
	<script type="text/javascript">
	var options;
	var refundCusType=$("[name=refundCusType]").val();
	$(function(){
		//隐藏收取节点
		$('#feeItemDetailForm').children('table').children('tbody').children('tr').eq(1).hide();
// // 		//费用收取时间暂不用，赋默认值
// 		$("[name=feeCollectTime]").val("0");
		options = $("select[name=standard]").find("option");
// 		var takeNode = $("select[name=takeNode]").val();  
// 		if (takeNode == "1") {
// 			makeOptionsJQ(options, '1');
// 			$("select[name=standard]").val("1");
// 		} else if (takeNode == "2") {
// 			makeOptionsJQ(options, '1,2,3');
// 			$("select[name=standard]").val("1");
// 		}else if (takeNode == "3") {
// 			makeOptionsJQ(options, '1,4');
// 			$("select[name=standard]").val("1");
// 		}else if (takeNode == "4") {
// 			makeOptionsJQ(options, '1,5');
// 			$("select[name=standard]").val("1");
// 		}else if (takeNode == "5") {
// 			makeOptionsJQ(options, '1,6');
// 			$("select[name=standard]").val("1");
// 		}
// 		makeOptionsJQ(options, '2,5');
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		//处理退费联动
		var itemType=$("select[name=itemType]").val();
		if(itemType=='2'){//是退费
			$("select[name=refundCusType]").attr("disabled",false);
			$("select[name=refundCusType]").attr("mustinput","1");
			$("select[name=refundCusType]").parents("tr").find("td:eq(0)").find("label").html("<font color='#FF0000'>*</font>返费对象类型");
		}else{//不是退费
			$("select[name=refundCusType]").parents("tr").hide();
			$("select[name=refundCusType]").attr("disabled",true);
		}
		$("[name=itemType]").bind("change",function(){
			if($(this).val()!="2"){//不是退费
				$("select[name=refundCusType]").parents("tr").hide();
				$("select[name=refundCusType]").attr("disabled",true);
			}else{//是退费
				$("select[name=refundCusType]").attr("disabled",false);
				$("select[name=refundCusType]").parents("tr").show();
				$("select[name=refundCusType]").parents("tr").find("td:eq(0)").find("label").html("<font color='#FF0000'>*</font>返费对象类型");
			}
		})
	});
	</script>
	</body>
</html>