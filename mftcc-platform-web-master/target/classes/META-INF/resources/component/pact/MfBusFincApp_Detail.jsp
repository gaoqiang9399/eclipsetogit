<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"历史信息","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"历史信息","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ include file="/component/include/pub_view.jsp"%>

<%
	String cellDatas = (String) request.getAttribute("cellDatas");
	String blockDatas = (String) request.getAttribute("blockDatas");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">

			.cover {
				cursor: default;inputDisagreeBuss
			}
		</style>
	
		<script type="text/javascript">
		function submitBtnOne(formId,dataParam){
			var flag = false;
			var submitUrl = $("#"+formId).attr('action');
			//dataParam.demoId = "100002";
			var dataParam = JSON.stringify(dataParam); 
			jQuery.ajax({
				url:submitUrl,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				async:false,//关键
				success:function(data){
					if(data.flag=="error"){
						alert(data.msg);
					}else{
						alert(data.msg);
						flag = true;//必须写
					}
				},error:function(data){
					alert(  top.getMessage("FAILED_SAVE"));
				}
			});
			return flag;//必须写
		}
		var appId,wkfAppId;
		$(function(){
			appId = '${mfBusFincApp.appId}';
			wkfAppId = '${wkfAppId}';
			var leftheight = $("#freewall > div:nth-child(1)").height();
			$("#freewall > div.cell.right-cell").css("height",leftheight);
		});
		
		//跳转至下一业务节点
		function toNextBusPoint(){
			$.ajax({
				url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId,
				type:'post',
    			dataType:'json',
				success:function(data){
					var url = data.url;
					var title=data.title;
					top.flag=false;
					top.submitFlag=false;
					top.window.openBigForm(url,title,wkfCallBack);
				}
			});
		}
		
		//回调函数
		function wkfCallBack(){
			if(top.flag){
				if(top.submitFlag){//如果已经提交，流程之后的操作就不在该页面进行了
					$(".block-add").hide();
				}
			}
			
		}
		
		
		
		
		function getCusInfo(cusNo){ 
			window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNo;
		}
	
		//获取押品信息
		function getPleInfo(){
			window.location.href=webPath+"/mfBusPledge/getPleByCusId?cusNo=200001";
		}
		
		function getBigForm(){
			window.parent.openBigForm(webPath+"/mfCusCustomer/getBigForm?cusNo=200001&cusType=202","客户信息");
		}
		</script>
	</head>
	<body style="overflow-y:auto;">
	    <div class="layout">
			<div id="freewall" style="margin: 8px;height: auto;" class="free-wall">
				<div class='cell' cellid='cell_1' style='top:0px; left:2px; width:65.5%; height: auto;padding-bottom: 30px; background-color:#FFFFFF;' data-handle=".handle">
					<div class="info margin_top_025">
							<!--主要信息 -->
						<div class="block-info">
							<!--业务主要信息 -->
							<div class="bus-main-info">
								<div>
									<img src="themes/factor/images/pact1.png" class="bus-pact">
								</div>
								<div class="main-div">
									<p class="font_size_18">${mfBusPact.kindName}</p>
									<p class="font_size_26 margin_bottom_30 margin_top_30">
										<span class="color_theme">${mfBusPact.fincAmt}</span><span class="myspan2 color_black margin_right_65">万</span>
										<span class="color_theme">${mfBusPact.termMonth}</span><span class="myspan2 color_black margin_right_65 ">月</span>
										<span class="color_theme">${mfBusPact.fincRate}</span><span class="myspan2 color_black margin_right_65">%</span>
									</p>
								</div>
							</div>
						</div>
						<div class="block-info">
							<!--个人/公司次要信息 -->
							<div class="base-info">
								<div class="content">
									<form method="post" theme="simple" name="operform" action="${webPath}/mfCusCorpBaseInfo/updateAjaxByOne">
										<dhcc:propertySeeTag property="formpact0004" mode="query"/>
									</form>
								</div>
							</div>
						</div>
						<c:if test='${mfBusPact.pactSts=="0" || mfBusPact.pactSts=="1" || (mfBusPact.pactSts=="3" && mfBusPact.putoutSts=="1")}'>
							<div style="clear:both;"></div>
							<div class="block-add" onclick="toNextBusPoint();">
		<!-- 						<img src="themes/factor/images/jiahao.png" class="jiahao"> -->
								<div style="margin-top:30px; color:#DEDEDE;">
									<span style="font-size:30px;" class="i i-jiantou2"></span><span style="font-size:30px;" class="i i-jiantou2"></span>
								</div>
							</div>
						</c:if>
					</div>
				</div>
				<div class='cell right-cell' cellid='cell_2' style='top:0px; left:65.5%; height: auto; background-color:#FFFFFF;margin-left: 10px;' data-handle=".handle">
					<div class="info margin_top_025" >
						<div class="block-info">
							<div class="right-img"  onclick="getCusInfo('${mfCusCustomer.cusNo}');" ></div>
							<div style="clear:both;"></div>
							<div style="margin-top:-10px;">
								<img src="${webPath}/themes/factor/images/user2.jpg"  class="left-img user-img"/>
								<div class="block-content">
									<p class="content-title">${mfCusCustomer.cusName }</p>
									<p class="font_size_14">${mfCusCustomer.contactsName }&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp; ${mfCusCustomer.contactsTel }</p>
									<p class="font_size_14">
									<c:if test="${!empty mfCusCustomer.commAddress}">
									${mfCusCustomer.commAddress }
									</c:if>
									<c:if test="${empty mfCusCustomer.commAddress}">
										未登记
									</c:if>
									</p>
								</div>
							</div>
						</div>
						<c:if test='${vouType!="1" && pleFlag=="1"}'>
							<div class="line-div"></div>
							<div class="block-info">
								<div class="right-img"  onclick="getPleInfo();" ></div>
								<div style="clear:both;"></div>
								<div style="margin-top:-10px;">
									<img src="themes/factor/images/pledge1.png" class="left-img" />
									<div class="block-content">
										<p class="content-title" title="${mfBusPledge.pledgeName }">${fn:substring(mfBusPledge.pledgeName,0,4) }<span class="margin_left_20" title="${mfBusPledge.pledgeNo }" >${fn:substring(mfBusPledge.pledgeNo,0,8) }</span></p>
										<p>
											<span class="myspan2">未引用</span> 
											<span class="myspan1">${mfBusPledge.envalue}</span><span class="myspan2">元</span>
											<span class="myspan1">${mfBusPledge.pledgeRate}</span><span class="myspan2">%</span>
										</p>
									</div>
								</div>
							</div>
						</c:if>
						<c:if test='${vouType!="1" && pleFlag=="0"}'>
							<div class="line-div"></div>
							<div class="block-info">
								<div class="center-img" onclick="applyInsert();">
									<div><img src="themes/factor/images/pledge2.png"/></div>
									<div><span>暂无数据</span></div>
								</div>
							</div>
						</c:if>
						<div class="line-div"></div>
						<div class="block-info">
							<div class='cover' style="height: 48px; margin-left: -10px; margin-right: -10px;">
								<div class='handle'>
									<span style="font-size: 16px; color: black;padding-left: 15px;" onclick="getCusInfo1();">历史信息</span>
								</div>
							</div>
							<table width="100%" height="auto" style="border-collapse: separate;border-spacing: 0px 0px;margin-top: 20px;margin-left:11px;">
								<tr style="height: 30px;">
		
									<td  style="text-align: left;font-size: 14px;width: 100px;">
										2016-07-08
									</td>
									<td style="color: #7f7f7f;font-size: 12px;"  align="left">
										业务申请成功
									</td>
								</tr>
								<tr style="height: 30px;">
									<td  style="text-align: left;font-size: 14px;width: 100px;">
										2016-07-08
									</td>
									<td style="color: #7f7f7f;font-size: 12px;"  align="left">
										信息修改成功
									</td>
								</tr>
							</table>
						</div>
					</div>
				</div>
			</div>
	    </div>
	</body>
</html>