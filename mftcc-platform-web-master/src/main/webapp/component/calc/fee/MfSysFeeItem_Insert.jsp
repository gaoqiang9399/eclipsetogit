<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src='${webPath}/component/calc/fee/js/MfSysFeeItem.js'> </script>
	</head>
	<script type="text/javascript">
	var options;
	$(function(){
	var feeStdNo =$("input[name=feeStdNo]").val();
		options = $("select[name=standard]").find("option");
		makeOptionsJQ(options, '2,5');
		$("select[name=standard]").val("2");
		//退费联动
		$("select[name=refundCusType]").parents("tr").hide();
		$("select[name=refundCusType]").attr("disabled",true);
		$("select[name=itemType]").bind("change",function(){
			if($(this).val()!="2"){//不是退费
				$("select[name=refundCusType]").parents("tr").hide();
				$("select[name=refundCusType]").attr("disabled",true);
			}else{//是退费
				$("select[name=refundCusType]").attr("disabled",false);
				$("select[name=refundCusType]").parents("tr").show();
				$("select[name=refundCusType]").val("");
				$("select[name=refundCusType]").attr("mustinput","1");
				$("select[name=refundCusType]").parents("tr").find("td:eq(0)").find("label").html("<font color='#FF0000'>*</font>返费对象类型");
			}
		});
// 		$(".scroll-content").mCustomScrollbar({
// 				advanced:{
// 					updateOnContentResize:true
// 				}
// 		});
		myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
		$("select[name=itemNo]").popupSelection({		
			searchOn:true,//启用搜索
			ajaxUrl:webPath+"/mfSysFeeItem/getPageFeeItemAjax?feeStdNo="+feeStdNo,//异步获取选项的url
			inline:true,//下拉模式
			multiple:false,//多选选
		});
		//隐藏收取节点  
		$('#feeItemInsertForm').children('table').children('tbody').children('tr').eq(1).hide();
//	//费用收取时间暂不用，赋默认值
// 		$("[name=feeCollectTime]").val("0");
	});
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
					<div class="col-md-8 col-md-offset-2 column margin_top_20">
						<div class="bootstarpTag">
	            				<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
							<form  method="post" id="feeItemInsertForm" theme="simple" name="operform" action="${webPath}/mfSysFeeItem/insertAjax">
								<dhcc:bootstarpTag property="formfeeitem0002" mode="query"/>
							</form>	
						</div>
					</div>
			</div>	
		
			<div class="formRowCenter">
				 <dhcc:thirdButton value="保存" action="保存" onclick="ajaxInsertThis('#feeItemInsertForm');"></dhcc:thirdButton> 
				 <dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose_click();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
