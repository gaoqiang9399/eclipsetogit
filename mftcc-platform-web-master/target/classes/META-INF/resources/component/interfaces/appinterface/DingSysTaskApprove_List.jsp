<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>融资审批</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<script src="${webPath}/component/include/jquery.form.js" type="text/javascript"></script>
	  	<script type="text/javascript" src="/factor/component/interfaces/appinterface/js/MfAppBusApply.js"></script>
	  	<link rel="stylesheet" type="text/css" href="/factor/component/interfaces/appinterface/css/MfAppBusApply.css"/>
	</head>
	<body>
		<input type="hidden" id="pasSts">
			<div class="weui_tab" style="height:auto;">
<!-- 				<div class="weui_navbar"> -->
<!-- 					<a class="weui_navbar_item" data-pacsts=""> 全部 </a> -->
<!-- 					 <a	class="weui_navbar_item" data-pacsts="1"> 已办理 </a>  -->
<!-- 					 <a class="weui_navbar_item" data-pacsts="0">未办理 </a> -->
<!-- 				</div> -->
				<div id="tab3_part2" class="">
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
		  var pasSts='0';
		  var pageNo=1;
		  //初始化上拉加载
	 	  var loading = false;  //状态标记 
	 	   var config = MfAppBusApply.getDDReady();//钉钉配置调用dd jsapi
			dd.ready(function() {
					//初始化页面
					var searchVal = $("#search_input").val();
					//搜索条件赋值给customQuery
					var ajaxData = "[{\"customQuery\":\"\"},{\"customSorts\":\"[]\"}]";
					// 判断上面信息执行一次， 不需要每次都执行
				dd.runtime.permission.requestAuthCode({
					corpId: config.corpId,
					onSuccess: function(result) {
						$.ajax({
							url: webPath+"/dingInterface/getUserInfoAjax?code=" + result.code,
							type: "get",
							dataType: 'json',
							async: false,
							success: function(data) {
								 	loading = false;
								 	pageNo =1;
									loading = !getBusCusList(pageNo,pasSts);
							}, 
							error: function(returnJSON) {
								alert("连接失败，请刷新页面重试");
							}
						})
					},
					onFail: function(err) {
					}
				})
				
					
					$(document.body).infinite(50).on("infinite", function() {
						  if(loading) return;
						  loading = true;
		  			  	  pageNo+=1;
						  pasSts= $("#pasSts").val();
						  //搜索条件赋值给customQuery
						  loading = !getBusCusList(pageNo,pasSts);
						 /*  if(getBusCusList(pageNo,pasSts)){
						  	loading = false;
						  } */
					}); 
// 				 	 $(".weui_navbar_item").click(function(){
// 					 	var $this = $(this);
// 						if($this.hasClass("weui_bar_item_on")){//当前激活事件不在加载列表
// 							return ;
// 						}
// 					 	loading = false;
// 					 	pageNo =1;
// 						$(".weui_navbar_item").removeClass("weui_bar_item_on");
// 						var pasSts = $(this).data("pacsts");
// 						$("#pasSts").val(pasSts);
// 						$("#tab3_part2").html("");
// 						loading = !getBusCusList(pageNo,pasSts);
// 						$(this).addClass("weui_bar_item_on");
// 					});
// 					$(".weui_navbar_item").first().click();  
				});	
		})
		//没有数据返回false 有数据返回true 用于判断滚动是否继续加载
		function getBusCusList(pageNo,pasSts){
			var flag = false;
			if(pageNo == 1){
				$("#tab3_part2").html("");
			}
			$(".weui-infinite-scroll").show();
			$.ajax({
				url:webPath+"/dingBusApproval/findByPageAjax1",
				data:{
					pageNo:pageNo,
					pasSts:pasSts,
				},
				async: false,//同步
				success:function(data){
					console.log(data);
					var list = data.data;
					var appMap=data.appMap;
					var apply;
					var sysTaskMap = data.sysTaskMap;
					for(var i=0;i<list.length;i++){
						flag=true;
						var sysTask = list[i];
						var bizPkNo = sysTask.bizPkNo;
						apply=appMap[bizPkNo];
						if(typeof(apply)=='undefined' || !apply){
							continue;
						}
						var stageClass="ding-bule";
						if(!apply.busStage){
							apply.busStage = "未办理";
							apply.appTime="未办理";
							apply.appAmt="0.00";
							apply.appName="未办理";
							stageClass="ding-grey"
						}
						var onclickFunc = "onclick=\"toBusAppovalDetail('"+sysTask.pasUrl+"');\"";
						var backgroudClass = "";
						//审批过不能进详情
						if(sysTask.pasSts && sysTask.pasSts == "1"){//已办理过的业务
							onclickFunc = "";
							backgroudClass = "dis-operate";
						}
						//格式化金额
						var formatAmt = DingUtil.fmoney(apply.appAmt,2);
									//期限格式化
						var term =apply.termShow;
						
						var rate= apply.fincRate;
						
							var html = "<div class=\"tab3_content\" "+onclickFunc+" >"+
		"					<div class=\"weui_cells weui_cells_access weui-ca "+backgroudClass+" \" >"+
								"<div class=\"weui_cell\">"+
									"<table>"+
									"<tr><td style='padding-bottom:10px;'colspan='4'><span>"+apply.appName+"</span></td></tr>"+
									"<tr class ='gery-mid-text'><td colspan='2' >申请金额(元)</td><td style='width:22%;margin:5px;'>期限</td><td  style='width:22%;'>利率</td></tr>"+
									"<tr><td colspan='2'  class='ding-bule'>"+formatAmt+"</td>"+
									"<td style='width:22%;margin:5px;'>"+term+"</td><td style='width:22%'>"+rate+"%</td></tr>"+
								"</table></div>"+		
		"					</div>"+
		"				</div>";
						$("#tab3_part2").append(html);
					}
					if(list.length == 0 || list.length < 15){//没有数据的提示
						$(".weui-infinite-scroll").hide();
						$("#tab3_part2").append("<div style=\"text-align:center;margin-top:20px;color:#888; \">已到底部</div>");
						flag = false;
					}
				}
			});
		
			return flag;
		}
		function toBusAppovalDetail(url){
			window.location.href=webPath+"/dingBusApproval/getViewPoint"+url;
		}
	</script>
</html>

