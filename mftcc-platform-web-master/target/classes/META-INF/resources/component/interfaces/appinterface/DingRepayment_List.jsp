<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>到期还款</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<script src="${webPath}/component/include/jquery.form.js" type="text/javascript"></script>
	  	<script type="text/javascript" src="/factor/component/interfaces/appinterface/js/MfAppBusApply.js"></script>
	  	<link rel="stylesheet" type="text/css" href="/factor/component/interfaces/appinterface/css/MfAppBusApply.css"/>
	  	<style>
	  	.weui_bar_item_on{
	  		color:#38adff!important;
	  		background-color:#EAF7FF!important;
	  	}
		</style>
	</head>
	<body>
		<div class="weui_search_bar" id="search_bar" style="display:none;">
			<form class="weui_search_outer" id="search_form">
				<div class="weui_search_inner">
					<i class="weui_icon_search"></i> <input type="search"
						class="weui_search_input" id="search_input" placeholder="搜索"
						required /> <a href="javascript:" class="weui_icon_clear"
						id="search_clear"></a>
				</div>
				<label for="search_input" class="weui_search_text" id="search_text">
					<i class="weui_icon_search"></i> <span>搜索</span>
				</label>
			</form>
			<a href="javascript:" class="weui_search_cancel" id="search_cancel">取消</a>
		</div>
		<input type="hidden" id="pasSts">
			<div class="weui_tab" style="height:auto;">
				<div class="weui_navbar">
<!-- 					<a class="weui_navbar_item" data-pacsts=""> 全部 </a> -->
					 <a class="weui_navbar_item" style="padding:10px 0" data-pacsts="0">还款到期</a>
					 <a	class="weui_navbar_item" style="padding:10px 0" data-pacsts="1">合同到期 </a> 
				</div>
				<div id="tab3_part2" class="" style="margin-top:45px;">
					<!-- 循环start -->
				</div>
				<div class="weui-infinite-scroll">
	  				<div class="infinite-preloader"></div><!-- 菊花 -->
	  					正在加载... <!-- 文案，可以自行修改 -->
				</div>
			</div>
	</body>
	
	<script>
		$(function(){
			  var pasSts;
			  var pageNo=1;
		    MfAppBusApply.getDDReady();//钉钉配置调用dd jsapi
			dd.ready(function(){});
		  //初始化上拉加载
	 	  var loading = false;  //状态标记 
					//初始化页面
					var searchVal = $("#search_input").val();
					//搜索条件赋值给customQuery
					var ajaxData = "[{\"customQuery\":\"\"},{\"customSorts\":\"[]\"}]";
					// 判断上面信息执行一次， 不需要每次都执行
				 $(".weui_navbar_item").click(function(){
				 	var $this = $(this);
					if($this.hasClass("weui_bar_item_on")){//当前激活事件不在加载列表
						return ;
					}
				 	loading = false;
				 	pageNo =1;
					$(".weui_navbar_item").removeClass("weui_bar_item_on");
					var pasSts = $(this).data("pacsts");
					$("#pasSts").val(pasSts);
					$("#tab3_part2").html("");
					loading = !getBusCusList(pageNo,pasSts);
					$(this).addClass("weui_bar_item_on");
				});
				$(".weui_navbar_item").first().click();
								
					
				$(document.body).infinite(100).on("infinite", function() {
					  if(loading) return;
					  loading = true;
	  			  	  pageNo+=1;
					  //搜索条件赋值给customQuery
					  loading = !getBusCusList(pageNo,pasSts);
				}); 
		})
		//没有数据返回false 有数据返回true 用于判断滚动是否继续加载
		function getBusCusList(pageNo,pasSts){
			var flag = false;
			if(pageNo == 1){
				$("#tab3_part2").html("");
			}
			$(".weui-infinite-scroll").show();
			var ajaxDataVal = getAjaxDataString();
			var pasSts = $("#pasSts").val();
			$.ajax({
				url:webPath+"/dingRepayment/getRepayToDateAjax",
				data:{
					pageNo:pageNo,
					ajaxData:ajaxDataVal,
					warningType:pasSts,
				},
				async: false,//同步
				success:function(data){
					var list = data.data;
					for(var i=0;i<list.length;i++){
					var appMap=list[i];
						flag=true;
						var formatAmt = DingUtil.fmoney(appMap.repayPrcp,2);
						var pactAmt = DingUtil.fmoney(appMap.repayIntst,2);;
						var formatDate = DingUtil.fdate(appMap.maxEndDate);
						var onclickFunc = "onclick=\"toBusAppovalDetail('"+appMap.fincId+"','"+formatDate+"');\"";
							var html = "<div class=\"tab3_content\" "+onclickFunc+" >"+
		"					<div class=\"weui_cells weui_cells_access weui-ca\" >"+
// 		"						<div class=\"weui_cell\" style='padding-bottom:0px;'>"+
// 		"							<div style=\"width: 79%\">"+
// 		"								<span>"+appMap.appName+"</span>"+
// 		"							</div>"+
// 		"						</div>"+
								"<div class=\"weui_cell\">"+
									"<table>"+
									"<tr><td style='padding-bottom:10px;'colspan='3'><span>"+appMap.appName+"</span></td></tr>"+
									"<tr class ='gery-mid-text'><td >应还本金(元)</td><td>应还利息(元)</td><td>到期日期</td></tr>"+
									"<tr><td  class='ding-bule'>"+formatAmt+"</td>"+
									"<td >"+pactAmt+"</td><td>"+formatDate+"</td></tr>"+
								"</table></div>"+		
		"					</div>"+
		"				</div>";
						$("#tab3_part2").append(html);
					}
					if(list.length == 0 || list.length < 15){//没有数据的提示
						$(".weui-infinite-scroll").hide();
						$("#tab3_part2").append("<div style=\"text-align:center;margin-top:25px;color:#888; \">已到底部</div>");
						flag = false;
					}
				}
			});
		
			return flag;
		}
		function getAjaxDataString(){
			var searchVal = $("#search_input").val();
			var cusBaseType = $("#cusBaseType").val();
			if(cusBaseType && cusBaseType != "0"){
				return "[[{\"checked\":true,\"andOr\":\"1\",\"condition\":\"cusBaseType\",\"type\":\"0\",\"value\":\""+cusBaseType+"\",\"noValue\":false,\"singleValue\":false,\"betweenValue\":false,\"listValue\":true,\"likeValue\":false}],{\"customQuery\":\""+searchVal+"\"},{\"customSorts\":\"[]\"}]";
			}else{
				return "[{\"customQuery\":\""+searchVal+"\"},{\"customSorts\":\"[]\"}]";
			}
		}
		function toBusAppovalDetail(fincId,dueDate){//解决号和到期日期
// 			DingRepaymentAction_getRepaymentInfo.action?fincId=fincApp17063021042546331&dueDate=2016-08-16 
			window.location.href=webPath+"/dingRepayment/getRepaymentInfo?fincId="+fincId+"&dueDate="+dueDate;
		}
	</script>
</html>

