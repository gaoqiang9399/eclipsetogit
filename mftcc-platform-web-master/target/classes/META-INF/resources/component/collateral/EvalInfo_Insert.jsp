<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
	</head>
	<script type="text/javascript">
        <%--var appAmt='${appAmt}';--%>
		$(function() {
			myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
		});
		//校验评估价值1
		function validateAmount(){
			var num  = $("input[name=evalAmount]").val().replace(/,/g,"");
			if(num.length!=0){
				var reg = /^\-[,,0-9]*.?[0-9]*$/;
				if(reg.test(num)){
					alert("评估价值不能输入负数", 0);
					$("input[name=evalAmount]").val("");
				}
			}
		};
		//校验评估价值2（校验后计算抵质押率）
        function validateEvalAmount(){
            var num  = $("input[name=evalAmount]").val().replace(/,/g,"");
            if(num.length!=0){
                var reg = /^\-[,,0-9]*.?[0-9]*$/;
                if(reg.test(num)){
                    alert("评估价值不能输入负数", 0);
                    $("input[name=evalAmount]").val("");
                }else{
                    calRate('evalAmount');
				}
            }
        };
		//校验担保金额
        function validateConfirmAmount(){
            var num  = $("input[name=confirmAmount]").val().replace(/,/g,"");
            if(num.length!=0){
                var reg = /^\-[,,0-9]*.?[0-9]*$/;
                if(reg.test(num)){
                    alert("担保金额不能输入负数", 0);
                    $("input[name=confirmAmount]").val("");
                }else{
                    calRate('confirmAmount');
                }
            }
        };
		//根据评估价值和担保金额计算抵质押率
        function calRate(putType){
            var evalAmount = $("input[name=evalAmount]").val().replace(/,/g,"");
            var confirmAmount = $("input[name=confirmAmount]").val().replace(/,/g,"");
            if(evalAmount.length!=0 && confirmAmount.length!=0){
                evalAmount = parseFloat(evalAmount);
                confirmAmount = parseFloat(confirmAmount);
                if(evalAmount<confirmAmount){
                    if(putType=='evalAmount'){
                        alert("评估价值不能小于担保金额", 0);
                        $("input[name=evalAmount]").val("");
                        $("input[name=mortRate]").val("");
                        return;
					}else if(putType=='confirmAmount'){
                        alert("担保金额不能大于评估价值", 0);
                        $("input[name=confirmAmount]").val("");
                        $("input[name=mortRate]").val("");
                        return;
					}
                }else if( evalAmount==0 || confirmAmount==0){
                    $("input[name=mortRate]").val("0.00");
                }else{
                    $("input[name=mortRate]").val(Math.round(confirmAmount/evalAmount*10000)/100.0);
				}
            }
        };



        /*根据评估价值和申请金额计算抵质押率  (报错，有需要再打开)
        function calRateAppAmt(){
            var evalAmount = $("input[name=evalAmount]").val().replace(/,/g,"");
            if(evalAmount.length!=0){
                if(appAmt == 0){
                    $("input[name=mortRate]").val("0.00");
				}else{
                    evalAmount = parseFloat(evalAmount);
                    appAmt = parseFloat(appAmt);

                    var  mortRate =  Math.round(evalAmount/appAmt*10000)/100.0;
                    if(mortRate > 100){
                        $("input[name=mortRate]").val("100.00");
					}else{
                        $("input[name=mortRate]").val(mortRate);
					}

				}


			}
        };*/


        function validateRate(){
			var num  = $("input[name=mortRate]").val();
			if(num.length!=0){
				var reg = /^\-[0-9]*.?[0-9]*$/;
				if(reg.test(num)){
					alert("抵质押率不能输入负值", 0);
					$("input[name=mortRate]").val("");
				}
			}
		};
		
		function validateMonth(){
			var num  = $("input[name=validTerm]").val();
			if(num.length!=0){
				var reg = /^\-[0-9]*$/;
				if(reg.test(num)){
					alert("评估有效期不能输入负值", 0);
					$("input[name=validTerm]").val("");
				}
			}
		};

		function insertEvalInfo(obj) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				jQuery.ajax({
					url : url,
					data : {ajaxData : dataParam},
					success : function(data) {
						if (data.flag == "success") {
							top.addFlag = true;
							if (data.htmlStrFlag == "1") {
								top.htmlStrFlag = true;
								top.htmlString = data.htmlStr;
								top.updateFlag = true;
								top.tableName = "eval_info";
							}
							myclose_click();
						} else if (data.flag == "error") {
							alert(data.msg, 0);
						}
					},
					error : function(data) {
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
			}
		};
				
		function calConfirmAmount(){
			var value = $("input[name=evalAmount]").val().replace(/,/g,"");
			if(value.length == 0){
				alert("请先输入评估价值",0);
				$("input[name=mortRate]").val("");
			}
			var rate = $("input[name=mortRate]").val();
			$("input[name=confirmAmount]").val(value*rate/100);
		};
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
			            <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="evalInfoInsert" theme="simple" name="operform" action="${webPath}/evalInfo/insertAjax">
								<dhcc:bootstarpTag property="formdlevalinfo0002" mode="query"/>
						</form>	
					</div>
				</div>	
			</div>
			<div class="formRowCenter">
	   			<dhcc:thirdButton value="保存" action="保存" onclick="insertEvalInfo('#evalInfoInsert');"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>
   		</div>
	</body>
</html>
