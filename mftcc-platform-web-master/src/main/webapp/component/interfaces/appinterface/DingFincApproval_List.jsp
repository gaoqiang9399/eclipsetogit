<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>放款审批</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<script src="${webPath}/component/include/jquery.form.js" type="text/javascript"></script>
	  	<script type="text/javascript" src="/factor/component/interfaces/appinterface/js/MfAppBusApply.js"></script>
	  	<link rel="stylesheet" type="text/css" href="/factor/component/interfaces/appinterface/css/MfAppBusApply.css"/>
	</head>
	<body>
			<div class="weui_tab" style="height:auto;">
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
		  var loading = false;  //状态标记
		  var  config =MfAppBusApply.getDDReady();//钉钉配置调用dd jsapi
			dd.ready(function() {
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
					  //搜索条件赋值给customQuery
					  loading = !getBusCusList(pageNo,pasSts);
				}); 
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
				url:webPath+"/dingPactApproval/findByPageAjax1",
				data:{
					pageNo:pageNo,
					pasSts:'0',
					pasMaxNo:"1",
					pasMinNo:"113"
				},
				async: false,//同步
				success:function(data){
					var list = data.data;
					var appMap=data.appMap;
					var apply;
					var sysTaskMap = data.data;
					for(var i=0;i<list.length;i++){
						flag=true;
						var sysTaskList = list[i];
						var bizPkNo = sysTaskList.bizPkNo;
						sysTask=appMap[bizPkNo];
						if(typeof(sysTask)=='undefined' || !sysTask){
							continue;
						}
						var onclickFunc = "onclick=\"toPactAppovalDetail('"+sysTaskList.pasUrl+"');\"";
						var backgroudClass = "";
						//审批过不能进详情
						if(sysTask.pasSts && sysTask.pasSts == "1"){//已办理过的业务
							onclickFunc = "";
							backgroudClass = "dis-operate";
						}
						var formatAmt = DingUtil.fmoney(sysTask.pactAmt,2);//金额格式化
						//期限格式化
						var term;
						if(sysTask.termType=='1'){
							term = sysTask.termMonth+"个月";
						}else if(sysTask.termType=='2'){
							term = sysTask.termMonth+"天";
						}else if(sysTask.termType=='3'){
							term = sysTask.termMonth+"期";
						}else if(sysTask.termType=='4'){
							term = sysTask.termMonth+"个月"+sysTask.termDay+"天";
						}
						var rate= sysTask.fincRate;
						var html = "<div class=\"tab3_content  \" "+onclickFunc+">"+
		"					<div class=\"weui_cells weui_cells_access weui-ca "+backgroudClass+" \" >"+
								"<div class=\"weui_cell\">"+
									"<table>"+
									"<tr><td style='padding-bottom:10px;'colspan='4'><span>"+sysTask.appName+"</span></td></tr>"+
									"<tr class ='gery-mid-text'><td colspan='2' style='width:50%;'>放款金额(元)</td><td style='width:22%;margin:5px;'>期限</td><td  style='width:22%;'>利率</td></tr>"+
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
		function toPactAppovalDetail(url){
			var urlparm = url.substring(url.indexOf("?"));
			location.href=webPath+"/dingFincApproval/getViewPoint"+urlparm;
		}
	</script>
</html>

