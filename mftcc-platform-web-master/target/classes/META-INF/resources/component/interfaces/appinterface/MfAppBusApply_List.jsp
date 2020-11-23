<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>进件列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
	  	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	  	<script src="${webPath}/component/include/jquery.form.js" type="text/javascript"></script>
	  	<script type="text/javascript" src="/factor/component/interfaces/appinterface/js/MfAppBusApply.js"></script>
	  	<script src="http://g.alicdn.com/dingding/open-develop/1.6.9/dingtalk.js"></script>
		<script src="https://g.alicdn.com/ilw/ding/0.9.2/scripts/dingtalk.js"></script>
	  	<style>
	  	.weui_search_bar{
	  		background-color:white;
	  	}
	  /* 	#search_text:focus
		{ 
			background-color:#38adff;
			border:1px soild #38adff;
		} */
	  	
	  	.weui_search_bar:after{
	  		border:0px;
	  	}
	  	.weui_cell{
	  		padding :15px 15px;
	  		background-color: white;
	  		border-bottom: 1px solid #ECECEC;
	  	}
		.weui_search_bar{
			position: fixed;
			top:0;
			left:0px;
			right:0px;
			z-index:999;
		}
		
		.weui_navbar{
			position: fixed;
			margin-top:44px;
		}
		.tab3_content_cl{
			width:33.33333%;
			float:left;
			padding-top:20px;
			padding-bottom:20px;
		}
		.tab3_content_cl p{
			text-align:center;
		}
		.font_blue{
			color:blue;
		}
		.tab3_content{
			background-color:white;
			border-bottom:1px dashed #38adff;
		}
		.ding-bule{
			color:#38adff;
		}
		.ding-grey{
			color:#999;
		}
		.smallgray{
			font-size: 10px;
			color: #999999;
		}
		#tab3_part2{
			margin-top:92px;
/* 			background-color:#efeff4; */
		}
		.mf_list_item{
			padding: 10px 15px 0px 15px;
		}
		table {  
            table-layout: fixed;  
            width: 100%;  
        }  
		td {  
                overflow: hidden;  
                white-space: nowrap;  
                text-overflow: ellipsis;  
            }  
         .gery-mid-text{
         	font-size:13px;
         	color:#999;
         	padding-bottom:5px;
         }
         .weui_cell:before{
         	border:0px;
         	width: auto;
         }
         .weui_cells:after{
         	border:0px;
         }
         .icon-font{
         	font-size: 28px;
   			color: #10AEFF;
         }
         .weui_bar_item_on{
	  		color:#38adff!important;
	  		background-color:#EAF7FF!important;
	  	}
	</style>
	</head>
	<body>
			<div class="weui_search_bar" id="search_bar">
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
			<div class="weui_tab" style="height:auto;">
				<div class="weui_navbar">
					<a class="weui_navbar_item"  style="padding:8px 0" data-type="0"> 全部 </a>
					 <a	class="weui_navbar_item"  style="padding:8px 0" data-type="2"> 个人 </a> 
					 <a class="weui_navbar_item"  style="padding:8px 0" data-type="1">企业 </a>
					 <input type="hidden" id="cusBaseType">
				</div>
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
		  	var pageNo=1;
		  	var loading = false;  //状态标记
		  	var config = MfAppBusApply.getDDReady();//钉钉配置调用dd jsapi
			dd.ready(function() {
			// 判断上面信息执行一次， 不需要每次都执行
				MfAppBusApply.setRight(); //右侧设置进件按钮
				dd.runtime.permission.requestAuthCode({
					corpId: config.corpId,
					onSuccess: function(result) {
						$.ajax({
							url: webPath+"/dingInterface/getUserInfoAjax?code=" + result.code,
							type: "get",
							dataType: 'json',
							async: false,
							success: function(data) {
								$(".weui_navbar_item").click(function(){
									var $this = $(this);
									if($this.hasClass("weui_bar_item_on")){//当前激活事件不在加载列表
										return ;
									}
									$(".weui_navbar_item").removeClass("weui_bar_item_on");
									$("#cusBaseType").val($this.data("type"));
									loading = false;  //状态标记
									pageNo=1;
									loading = !getBusCusList(pageNo);//防止首次进入页面加载一次，在滚动时又加载一次的情况
									$(this).addClass("weui_bar_item_on");
								});
								//初始化页面
								$(".weui_navbar_item").first().click(); 
							}, 
							error: function(returnJSON) {
								alert("连接失败，请刷新页面重试");
							}
						})
					},
					onFail: function(err) {
		
					}
				})
// 				$(".weui_navbar_item").click(function(){
// 					var $this = $(this);
// 					if($this.hasClass("weui_bar_item_on")){//当前激活事件不在加载列表
// 						return ;
// 					}
// 					$(".weui_navbar_item").removeClass("weui_bar_item_on");
// 					$("#cusBaseType").val($this.data("type"));
// 					loading = false;  //状态标记
// 					pageNo=1;
// 					loading = !getBusCusList(pageNo);
// 					$(this).addClass("weui_bar_item_on");
// 				});
// 				//初始化页面
// 				$(".weui_navbar_item").first().click();  
				//fastclick.js后点击搜索框失效
				$("#search_bar").click(function(){
					$(this).addClass("weui_search_focusing");
				});
				//取消搜索后再次调用获取数据
				$("#search_cancel").click(function(){
					pageNo = 1;
					loading = false; 
					//搜索条件清空
					loading = !getBusCusList(pageNo);
				});
				//搜索表单
				$("#search_form").ajaxForm(function(){
					pageNo = 1;
					$("#tab3_part2").html("");
					loading = false; 
					//搜索条件赋值给customQuery
					loading = !getBusCusList(pageNo);
				});
				//初始化上拉加载
				$(document.body).infinite(50).on("infinite", function() {
					  if(loading) return;
					  loading = true;
	  			  	  pageNo+=1;
					  //搜索条件赋值给customQuery
					  loading = !getBusCusList(pageNo);
					 /*  if(getBusCusList(pageNo)){
					  	loading = false;
					  } */
				});
 			});
		});
		//没有数据返回false 有数据返回true 用于判断滚动是否继续加载
		function getBusCusList(pageNo){
			var ajaxDataVal = getAjaxDataString();
			var flag = false;
			if(pageNo == 1){
				$("#tab3_part2").html("");
			}
			$(".weui-infinite-scroll").show();
			$.ajax({
				url:webPath+"/mfAppBusApply/findByPageAjax1",
				data:{
					pageNo:pageNo,
					ajaxData:ajaxDataVal
				},
				async: false,//同步
				success:function(data){
					console.log(data);
					var list = data.data;
					
					for(var i=0;i<list.length;i++){
						flag=true;
						var apply = list[i];
						var stageClass="ding-bule";
						var iconClass = "i i-ren3";//个人图标
						var cusTypeStr = apply.cusType;
						if(cusTypeStr && cusTypeStr.substr(0,1) == "1"){//企业
							iconClass = "i i-qiye";
						}
						var nameStr = apply.cusName;
						if(apply.appName){
							nameStr = apply.appName;
						}
							var html = "<div class=\"weui_cell\" onclick=\"goCusCustomerDetail('"+apply.cusNo+"','"+apply.appId+"');\" >"+
"					          <div class=\"weui_cell_hd\" style=\"margin-right: 10px;\"><i class=\""+iconClass+" icon-font\"></i></div>"+
"					          <div class=\"weui_cell_bd weui_cell_primary\">"+
"					            <p>"+nameStr+"</p>"+
"					          </div>"+
"					     </div>";
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
		//跳转到客户详情页面
		function goCusCustomerDetail(cusNo,appId){
			if(!appId || appId == 'null'){
				appId = '';
			}
			var url = webPath+'/mfAppCusCustomer/getById?cusNo='+cusNo+'&appId='+appId;
			window.location.href = url;
		}
		//获取搜索条件组装字符串，用来和pc参数保持一致cusBaseType 1企业 2个人
		function getAjaxDataString(){
			var searchVal = $("#search_input").val();
			var cusBaseType = $("#cusBaseType").val();
			if(cusBaseType && cusBaseType != "0"){
				return "[[{\"checked\":true,\"andOr\":\"1\",\"condition\":\"cusBaseType\",\"type\":\"0\",\"value\":\""+cusBaseType+"\",\"noValue\":false,\"singleValue\":false,\"betweenValue\":false,\"listValue\":true,\"likeValue\":false}],{\"customQuery\":\""+searchVal+"\"},{\"customSorts\":\"[]\"}]";
			}else{
				return "[{\"customQuery\":\""+searchVal+"\"},{\"customSorts\":\"[]\"}]";
			}
		}
	</script>
</html>
