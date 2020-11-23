<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title></title>
<script type="text/javascript">
var ajaxData = ${ajaxData};
var calcIntstFlag =ajaxData.calcIntstFlag;
			$(function(){
				$("#plan_content").html(ajaxData.html);
				$("#changediv").html('<table style ="font-size:14px;">'
                        +'<tr><td>累计还款总额：&nbsp;&nbsp;'+ajaxData.totalAmt+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>支付息款：&nbsp;&nbsp;'+ajaxData.totalIntstAmt+'元</td></tr></table>');
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				var calculateFlag= ajaxData.calculateFlag;
				if(!calculateFlag){
					$("#mfRepayPlanTrial_button").attr("disabled", true);// 拒绝业务
					$("#mfRepayPlanTrial_button").css("background", "rgb(122,122,122)");// 拒绝业务
				}
			 });
 			function openList(obj){
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var url = $(obj).attr("action");
					var dataParam = JSON.stringify($(obj).serializeArray());
					LoadingAnimate.start();
					$.ajax({
						url : url,
						data : {
							ajaxData : dataParam
						},
						type : 'post',
						dataType : 'json',
						success : function(data) {
							LoadingAnimate.stop();
							if (data.flag == "success") {						
								
								 $("#plan_content").html(data.htmlStr);
								//页面显示累计还款总额及支付息款 
								$("#changediv").html('');
								$("#changediv").html('<table style ="font-size:14px;">'
				                              +'<tr><td>累计还款总额：&nbsp;&nbsp;'+data.totalAmt+'元</td><td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td><td>支付息款：&nbsp;&nbsp;'+data.totalIntstAmt+'元</td></tr></table>');
							} else {
								window.top.alert(data.msg, 0);
							}
						},
						error : function() {
							loadingAnimate.stop();
						}
					});
			}
		};
		function changeDate(){
			var beginDate =  $("input[name=provideDate]").val();
			var term = $("input[name=loanMon]").val();
			var termType = $("[name=termType]").val();
			var intTerm = parseInt(term);
			if(1==termType){ //融资期限类型为月 
				var d = new Date(beginDate);//合同结束日期展示
				d.setMonth(d.getMonth()+intTerm);
				
				var endDate= new Date(beginDate);//合同结束日期
				endDate.setMonth(endDate.getMonth()+intTerm);
				if(2==calcIntstFlag){// 2-首尾都计算
					d.setDate(d.getDate()-1);
					endDate.setDate(endDate.getDate()-1);//合同结束日期
				}else if(1==calcIntstFlag){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为2时值为end_date-1,否则和end_date一致
					d.setDate(d.getDate()-1);
				}else if(1==calcIntstFlag&& 3==pactEndDateShowFlag){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为 3    实际结束日期减一天，显示结束日期再减一天, 否则和end_date一致
					endDate.setDate(endDate.getDate()-1);//合同结束日期 实际结束日期减一天
					d.setDate(endDate.getDate()-1);//显示结束日期再减一天(在实际结束日期的基础上)
				}
				var strEndDate= endDate.getFullYear()+"-"+(endDate.getMonth()>=9?endDate.getMonth()+1:"0"+(endDate.getMonth()+1))+"-"+(endDate.getDate()>9?endDate.getDate():"0"+endDate.getDate());
				$("input[name=endDate]").val(strEndDate);
			}else{ //融资期限类型为日 
				var d = new Date(beginDate);//合同结束日期展示
			 	d.setDate(d.getDate()+intTerm);
				var endDate= new Date(beginDate);//合同结束日期
				endDate.setMonth(endDate.getMonth()+intTerm);
				if(2==calcIntstFlag){// 2-首尾都计算
					d.setDate(d.getDate()-1);
					endDate.setDate(endDate.getDate()-1);////合同结束日期
				}else if(1==calcIntstFlag){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为2时值为end_date-1,否则和end_date一致
					d.setDate(d.getDate()-1);
				}else if(1==calcIntstFlag&& 3==pactEndDateShowFlag){//合同结束日期展示（算头不算尾且PACT_ENDDATE_SHOW_FLAG为 3    实际结束日期减一天，显示结束日期再减一天, 否则和end_date一致
					endDate.setDate(endDate.getDate()-1);//合同结束日期 实际结束日期减一天
					d.setDate(endDate.getDate()-1);//显示结束日期再减一天(在实际结束日期的基础上)
				}
				var strEndDate= endDate.getFullYear()+"-"+(endDate.getMonth()>=9?endDate.getMonth()+1:"0"+(endDate.getMonth()+1))+"-"+(endDate.getDate()>9?endDate.getDate():"0"+endDate.getDate());
				$("input[name=endDate]").val(strEndDate);
			}
		}


</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="repayPlanTrial" theme="simple" name="operform" action="${webPath}/mfRepayPlanTrial/calculateAjax">
						<dhcc:bootstarpTag property="formrepayplantrial0002" mode="query" />						
						<div class="col-sm-offset-5 col-sm-5" style="margin-top:20px;">
							<button id="mfRepayPlanTrial_button" type="button" class="btn btn-default" value="计算"
								style="background: #32B5CB; color: #fff; width: 80px; height: 35px; border: none; border-radius: 0px;"
								onclick="openList('#repayPlanTrial');" >计算</button>
						</div>
						</form>	
				<div>
			</div>
			<div class="table_content" style="width:100%;margin-left:-15px;">
				<div id="changediv" style="position: relative;margin-bottom:10px;margin-top:20px;" align="center">
			    </div>
		    </div>
				<div id="plan_content" class="table_content" style="width:100%;margin-left:-15px;">
				
				</div>
			</div>			
		</div>
	</div>
	</div>

</body>
</html>
