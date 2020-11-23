<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
		<%
		  String operAmt = request.getParameter("payValue");
		%>
<head>
<%@ include file="/component/include/common.jsp"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script>
	var inter;
	var i = 0;
	var orderID;
	var moneyPay = '<%=operAmt%>';
		$(function(){
			rechargeOpen();
			var interval = setInterval(function(){
				orderID = $('#dd_num').html();
				money_new = $('#money_new').html();
	            if (orderID != 0) {
	                $.ajax({
	                	url:webPath+"/servlet/OnlinePayOrderQueryServlet?outTradeNo="+orderID,//调用ashx获得订单状态
	                    type: "GET",
	                    dataType:"json",
	                    data: "",
	                    success: function (data) {
	                        if (data.trade_state=="SUCCESS") { //支付成功
	                           window.location.href = "<%=request.getContextPath() %>/component/tools/charge/successPay.jsp?orderID="+orderID+"&money_new="+money_new; //页面跳转
	                        }else if(data.trade_state=="NOTPAY"){  //未支付
	                        	
	                        }else if(data.trade_state=="PAYERROR"){ //支付失败
	                        
	                        }
	                    },
	                    error: function () {
	                         alert("微信支付失败，请联系客服"); 
	                    }
	                });
	            }
			}, 5000);
		});
		function rechargeOpen(){
			$.ajax({
				type:"post",
				url:webPath+"/servlet/OnlinePayServlet",
		        dataType:"json",  
		        data:{'money':'<%=operAmt%>'},
		        success:function(data){
		        	if(data !=null ){
		        		$("#dd_num").text(data.out_trade_no);
		        		$("#money_new").text(data.total_fee);
		        		$("#er-img").attr({src:'<%=request.getContextPath() %>/qrcode/qrcode.png?'+Math.random()});
		        		$("#er-img").removeClass("wxWt_img");
		        		$("#er-img").attr("height","173");
		        	}
		        },
		        error:function(){
		        }
			});
		}
	</script>
	<style type="text/css"> 
	.wxWt_img{
	margin-top:30px;
	}
	.pull-right{
	float: right;
	}
	.pull-left{
	float: left;
	}
	.er_cont {
    width: 100%;
    height: auto;
    font-size: 14px;
    color: #464646;
	}
	.er_cont .cont_top {
    width: 550px;
    height: 50px;
    line-height: 50px;
    margin: 0 auto;
	}
	.cont_center{
	width: 550px;
	margin: 0 auto;
	height: 60px;
	}
	</style>
<title>二维码</title>
</head>
<body>
	<div style="width: 100%; height: auto;">
		<div class="er_cont" id="money-btm">
			<div class="cont_top">
				<div class="pull-left">
					<span class="er_right pull-left"></span>
					<span class="pull-left">微信支付</span>
				</div>
				<div class="pull-right"><span style="font-size: 16px">收款账户:北京微金时代科技有限公司</span></div>
			</div>
			<div class="cont_center">
				<div class="center" style="text-align: left !important;">
					<div class="pull-left">
						<p>订单编号：<span id="dd_num"></span></p>
						<p>订单类型：在线充值</p>
					</div>
					<div class="pull-right">
						应付金额：￥<span style="font-size: 18px;" id="money_new"><%=operAmt%></span>
					</div>
				</div>
			</div>
			<div class="cont_btom">
				<div class="pull-left" style="margin-left: 20px;">
					<p class="er_img wxWt_img" style="height: 173px;width: 173px;"><img id="er-img" src="<%=request.getContextPath() %>/themes/factor/images/loadingLogo.gif?v=${cssJsVersion}" width="173" height="122"/></p>
					<p class="er_btm"><img src="<%=request.getContextPath() %>/component/tools/image/sm_text.png" width="173" height="54"/></p>
				</div>
				<div class="pull-right" style="margin-right: 20px;">
					<p class="er_img wxWt_img" style="height: 250px;width: 200px;"><img id="er-img" src="<%=request.getContextPath() %>/component/tools/image/smer.png" width="200px" height="250px"/></p>
				</div>
			</div>
		</div>
	</div>
</body>
</html>