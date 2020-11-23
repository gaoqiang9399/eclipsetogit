<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/interfaces/appinterface/common.jsp"%>
<%@ include file="/component/interfaces/appinterface/Ding_Wkf_Base.jsp"%>
<%
String pactId = (String)request.getParameter("pactId");
/*  String appId =  (String)request.getParameter("appId"); */
String vouType =  (String)request.getParameter("vouType");
String cusType = (String)request.getParameter("cusType");
%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="/factor/UIplug/iconmoon/style.css" >
<link rel="stylesheet" href="${webPath}/component/interfaces/appinterface/common/jqueryWeUI-1.01/weui.css"/>
<link rel="stylesheet" href="${webPath}/component/interfaces/appinterface/css/approvalPag.css"/>
<script type="text/javascript" src="/factor/component/interfaces/appinterface/js/MfAppBusApply.js"></script>
<script type="text/javascript" src="${webPath}/component/interfaces/appinterface/js/DingApproval.js"></script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<style type="text/css">
</style>
<title>合同审批</title>
<script type="text/javascript">
			var cusNo ="${cusNo}";
			var scNo = "0000000001";
			var appId = "${mfBusApply.appId}";
			var phoneNumber = "${mfCusCustomer.contactsTel}";
		 	var _config;
		 	$(function(){
		 		getPhoto();
		 	});
			MfAppBusApply.getDDReady();//钉钉配置调用dd jsapi
			function getPhoto( ){
				var ajaxdata = {
						appId : appId,
						cusNo : cusNo,
						scNo : scNo
					};
					var ajaxdataS = JSON.stringify(ajaxdata);
					$.ajax({
						url : webPath+"/dingUploadFile/getCusNeedDocs",
						type : "post",
						data : {
							paramJson : ajaxdataS
						},
						dataType : 'json',
						async : false,
						success : function(data) {
							console.log(data);
							var list = data.data;
							list = $.parseJSON(list);
						 	$imageList = $("#image_list");
							for ( var i = 0; i < list.length; i++) {
								var e = list[i];
								//添加头部显示
								$bigTitle = $("<div></div>").addClass("weui-uploader__hd big_title_out").html('<div class="big_title"><div class="weui-uploader__title">'+e.docTypeName+'</div></div>');
								$imageList.append($bigTitle);
								//添加图片ul
								$imgDiv = $("<div></div>").addClass("weui-uploader__bd padding_class");
								$imgUl =  $('<ul></ul>').addClass('weui-uploader__files').attr("id",docSplitNo);
								$imgDiv.append($imgUl);
								$imageList.append($imgDiv);
								//添加照片
								var ImageList = e.imgs;
								for ( var j = 0; j < ImageList.length; j++) {
									var img = ImageList[j];
									var cusNo = img.cusNo;
									var relNo = img.relNo;
									var docSplitNo = img.docSplitNo;
									var docSplitName = img.docSplitName;
									var docType = img.docType;
									var scNo = img.scNo;
									var docNo = img.docNo;
									var docBizNo = img.docBizNo;
									if(typeof(img.docNo)!="undefined"){
										var imgCompressUrl = webPath+'/dingUploadFile/viewCompressImage?docNo='+docNo+'&docBizNo='+docBizNo;
										$imagLi = $('<li></li>').addClass('weui-uploader__file show_file').attr({
												'style':'background-image: url('+imgCompressUrl+')',
												'data-docNo' : docNo,
												'data-docBizNo': docBizNo
										}).html('<div class="photo-span">'+docSplitName+'</div>');
										$imgUl.append($imagLi);
									}
								}
							}
							//为所有图片添加预览事件
							$(".show_file").click(function(){
								var docNo = $(this).attr('data-docNo' );
								var docBizNo = $(this).attr('data-docBizNo' );
								var url = "${webPath}dingUploadFile/viewImage?docNo="+docNo+"&docBizNo="+docBizNo;
								dd.biz.util.previewImage({
								    urls: [url],//图片地址列表
								    current: url,//当前显示的图片链接
								    onSuccess : function(result) {
								        /**/
								    },
								    onFail : function(err) {}
								})
							});
						},
						error : function(){
						}
					})
			}
			dd.ready(function() {
					document.addEventListener('backbutton', function(e) {
		              // 在这里处理你的业务逻辑
					  	 if($('#appDiv').is(':hidden')){
						  	 toList();
						 }else{
							 backDiv();
						 }	
		              e.preventDefault(); //backbutton事件的默认行为是回退历史记录，如果你想阻止默认的回退行为，那么可以通过preventDefault()实现
		  			 });
					//ios处理
					dd.biz.navigation.setLeft({
					    show: true,//控制按钮显示， true 显示， false 隐藏， 默认true
					    control: true,//是否控制点击事件，true 控制，false 不控制， 默认false
					    showIcon: false,//是否显示icon，true 显示， false 不显示，默认true； 注：具体UI以客户端为准
					    text: '',//控制显示文本，空字符串表示显示默认文本
					    onSuccess : function(result) {
						     if($('#appDiv').is(':hidden')){
							  	 toList();
							 }else{
								 backDiv();
							 }	
					    },
					    onFail : function(err) {}
					});
				//拨打电话
				$("#phone").click(function(){
					dd.biz.telephone.showCallMenu({
					    phoneNumber: phoneNumber, // 期望拨打的电话号码
					    code: '+86', // 国家代号，中国是+86
					    showDingCall: false, // 是否显示钉钉电话
					    onSuccess : function() {},
					    onFail : function() {}
					});
				})
				
			})
			//审批后回调函数
			function toList(){
				window.location.href=webPath+"/dingPactApproval/getDingPactApproveList";
			};
			
</script>
</head>
<body>
	<div id="headDiv" class="weui_cells weui_cells_access marginTop0 baner-background">
		<div style="padding:20px;color:#ffffff">
			<table style="width:100%">
				<tr style="width:100%">
					<td style="width:50%;font-size:21px;"><span>${mfCusCustomer.contactsName}</span></td> <%-- ${mfCusCustomer.contactsName} --%> 
					<td rowspan='2' style="width:50%;text-align:right;"><span id="addBus" class="ding_circle"><span>审</span></span><span id='phone' class="ding_circle " style="padding:6px 10px;"><i class="i i-dianhua ding_circle_icon"></i></span></td>
					
				</tr>
				<tr><td><span>${mfCusCustomer.contactsTel}</span></td></tr>
				<tr><td colspan='2'><span>${mfCusCustomer.commAddress}</span></td></tr>
			</table>	
		</div>
	</div>
	<div class="weui_panel">
	
		<div class="weui_panel_bd" id="panel_db_1">
			<div class="second_part">
				<div class="weui_cells weui_cells_access"  id="weui-ca">
					
					<a class="weui_cell" href="javascript:;">		
						<div class="weui_cell_hd">
						</div>	
												
						<div class="weui_cell_bd weui_cell_primary">			
								<span class='info-box-icon i i-applyinfo' style="margin-right:10px;font-size:24px"></span><span class="ding-bule">${mfBusPact.appName}</span>		
						</div>			
					</a>						
				</div>
		<%-- 		<div class="mf_list_item">							
					<div class="xiangmuxinxi ding-bule" style="padding-left:18px;">								 
						<!-- <i class="info-box-icon i i-qiye "> --></i><span>${mfBusPact.appName}</span>							
					</div>						
				</div>			 --%>			
				<div class="mf_list_item">							
					<div class="baozhengxinxi">								
						<div class="form_xinxi_left">									
							<span class="gery-mid-text">合同金额(元)</span>									
								<p>${rawPactAmt}</p>								
						</div>								
						<div class="form_xinxi_right">									
							<span class="gery-mid-text">合同期限</span>									
								<p>${mfBusPact.termShow}</p>								
						</div>								
						<div class="form_xinxi_left">									
							<span class="gery-mid-text">合同利率</span>									
								<p>${mfBusPact.fincRateShow}${rateUnit}</p>								
						</div>								
						<div class="form_xinxi_right">									
							<span class="gery-mid-text">开始日期</span>									
							<p>${beginDateShow}</p>								
						</div>
						<div class="form_xinxi_left">									
							<span class="gery-mid-text">产品类型</span>									
								<p>${mfBusPact.kindName}</p>								
						</div>								
						<div class="form_xinxi_right">									
							<span class="gery-mid-text">贷款投向</span>									
							<p>${fincUseStr}</p>								
						</div>	
						<div class="form_xinxi_left">									
							<span class="gery-mid-text">还款方式</span>									
								<p>${repayTypeStr}</p>								
						</div>								
						<div class="form_xinxi_right">									
							<span class="gery-mid-text">担保方式</span>									
							<p>${vouTypeStr}</p>								
						</div>	
												
						<div class="clearfloat"></div>							
					</div>						
				</div>					
			</div>
		</div>
	</div>
	<div class="weui_panel">
		<div class="weui_panel_bd" id="panel_db_1">
			<div class="second_part">
				<div class="weui_cells weui_cells_access"  id="weui-ca">
					<a class="weui_cell" href="javascript:;">		
						<div class="weui_cell_hd">								
						</div>								
						<div class="weui_cell_bd weui_cell_primary">			
							<span class='info-box-icon i i-wenjian4' style="margin-right:10px;font-size:24px" ></span><span class="">要件信息</span>		
						</div>			
					</a>						
				</div>
				<!-- 要件 -->
				<div class="weui-cells weui-cells_form">
				  <div class="weui-cell">
				    <div class="weui-cell__bd">
				      <div class="weui-uploader" id="image_list">
				        <!-- <div class="weui-uploader__hd">
				          <p class="weui-uploader__title">调查资料</p>
				          <div class="weui-uploader__info">0/2</div>
				        </div>
				        <div class="weui-uploader__bd">
				          <ul class="weui-uploader__files" id="uploaderFiles">
				            <li id="card1" class="weui-uploader__file" style="background-image:url(component/interfaces/appinterface/img/card1.jpg)"></li>
				            <li id="card2"class="weui-uploader__file" style="background-image:url(component/interfaces/appinterface/img/card2.jpg)"></li>
				            <li id="hetong"class="weui-uploader__file" style="background-image:url(component/interfaces/appinterface/img/hetong.jpg)"></li>
				          </ul>
				        </div> -->
				      </div>
				    </div>
				  </div>
				</div>
				
		
			</div>	
		</div>
	</div>
	<div class="weui_panel" id="appDiv" style='display:none;margin-top:0px;margin-bottom: 25px'>
		<div class="weui_panel_bd" id="panel_db_1">
			<div class="second_part">
				<div class="weui_cells weui_cells_access" id="weui-ca">
					<a class="weui_cell" href="javascript:;">		
						<div class="weui_cell_hd">								
						</div>								
						<div class="weui_cell_bd weui_cell_primary">			
							<span class='info-box-icon i i-dengji'  style="margin-right:10px;font-size:24px"></span><span class="">审批信息</span>		
						</div>			
					</a>						
				</div>
				<form method="post" action="MfAppBusApplyActionAjax_insertForApplyAjax_query.action" class=" weui-cells weui-cells_form " id="insertForm">
					<input type="hidden" title="申请ID（隐）" name="appId" value="${mfBusPact.appId}">
					<input type="hidden" title="合同ID" name="pactId" value="${mfBusPact.pactId}">
					<input type="hidden" title="合同编号" name="pactNo" value="${mfBusPact.pactNo}">
					<input class="weui_input" type="hidden" placeholder="请输入" name="cusName" value="${mfBusPact.cusName}">
					<input type="hidden" title="产品种类编号" name="kindNo" value="${mfBusPact.kindNo}"><!--  -->
					<input type="hidden" name="kindName" value="${mfBusPact.kindName}"><!--  -->
					<input type="hidden" name="appName" value="${mfBusPact.appName}"><!--  -->
<%-- 					<input class="weui_input" type="hidden" placeholder="请输入" name="cusNo" value="${mfBusPact.cusNo}"> --%>
					<input type="hidden" title="合同金额" name="pactAmtShow" value="${mfBusPact.pactAmtShow}">
					<input type="hidden" title="合同期限" name="termShow" value="${mfBusPact.termShow}">
					<input type="hidden"  name="fincRateShow" value="${mfBusPact.fincRateShow}">
					<input type="hidden" name="vouType" value="${mfBusPact.vouType}"><!-- 担保方式 -->
					<input type="hidden"  name="gracePeriodFinc" value="${mfBusPact.gracePeriodFinc}"><!-- 宽限天数 -->
					<input type="hidden" title="付款条件" name="payCondition" value="${mfBusPact.payCondition}">
					<input type="hidden" title="币种" name="curNo" value="${mfBusPact.curNo}">
					<input type="hidden" title="合同开始日期" name="beginDate" value="${mfBusPact.beginDate}">
					<input type="hidden" title="合同结束日期" name="endDate" value="${mfBusPact.endDate}">
					<input type="hidden" title="合同签订日期" name="signDate" value="${mfBusPact.signDate}">
					<input type="hidden" title="逾期利率" name="overRate" value="${mfBusPact.overRate}">
					<input class="weui_input" type="hidden" placeholder="请输入" name="overFloat" value="${mfBusPact.overFloat}"><!-- 逾期利率上浮 -->
					<input class="weui_input" type="hidden" placeholder="请输入" name="cmpdFloat" value="${mfBusPact.cmpdFloat}"><!-- 复利率上浮 -->
					<input type="hidden" name="formId" value="${formId}">
					<div class="weui_cell">
						<div class="weui_cell_hd">
							<label class="weui_label">批复金额</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<input class="weui_input"  placeholder="请输入" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput='1' name="pactAmt" value="${approvePactAmt}">
						</div>
						元
					</div>
					<div class="weui_cell">
						<div class="weui_cell_hd">
							<label class="weui_label">批复期限</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput='1' placeholder="请输入" name="term" value="${mfBusPact.term}">
						</div>
						${termUnit}<!-- 期限单位 -->
					</div>
					<div class="weui_cell">
						<div class="weui_cell_hd">
							<label class="weui_label">批复利率</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<input class="weui_input" type="tel" onkeyup="DingUtil.inputOnlyNumber(this)" mustinput='1' placeholder="请输入" name="fincRate" value="${mfBusPact.fincRate}">
						</div>
						${rateUnit}
					</div>
					<%-- <div class="weui_cell" style="display: none;">
						<div class="weui_cell_hd">
							<label class="weui_label">逾期利率上浮</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<input class="weui_input" type="hidden" placeholder="请输入" name="overFloat" value="${mfBusPact.overFloat}">
						</div>
						${rateUnit}
					</div> --%>
					<%-- <div class="weui_cell" style="display: none;">
						<div class="weui_cell_hd">
							<label class="weui_label">复利率上浮</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<input class="weui_input" type="hidden" placeholder="请输入" name="cmpdFloat" value="${mfBusPact.cmpdFloat}">
						</div>
						${rateUnit}
					</div> --%>
					<div class="weui_cell weui_cell_select" style="padding-top:0px;padding-bottom:0px;">
						<div class="weui_cell_hd">
							<label class="weui_label">还款方式</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<select class="weui_select" name="repayTypeShow" style="padding-left:0px;" readonly>
								
							</select>
						</div>
						<input type="hidden" name="repayType" value="${mfBusPact.repayType}">
					</div>
					<div class="weui_cell weui_cell_select" style="padding-top:0px;padding-bottom:0px;">
						<div class="weui_cell_hd">
							<label class="weui_label">额度是否可循环</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<select class="weui_select" name="authCycle" style="padding-left:0px;" >
								<option value="1">是</option>
								<option value="0">否</option>
							</select>
						</div>
					</div>
					<div class="weui_cell weui_cell_select" style="padding-top:0px;padding-bottom:0px;">
						<div class="weui_cell_hd">
							<label class="weui_label">意见类型</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<select class="weui_select" name="opinionType" style="padding-left:0px;" onchange="DingWkfApprove.opinionTypeChange();">
						
							</select>
						</div>
					</div>
					<div class="weui_cell weui_cell_select" style="padding-top:0px;padding-bottom:0px;display:none;" id="nextApprovePerson-div">
						<div class="weui_cell_hd">
							<label class="weui_label">下一岗位</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<select class="weui_select"  id="nextApprovePerson" name="nextApprovePerson" style="padding-left:0px;" onchange="">
						
							</select>
						</div>
					</div>
					<div class="weui_cell weui_cell_select" id="rollbackNameDiv" style="padding-top:0px;padding-bottom:0px;display: none;">
						<div class="weui_cell_hd">
							<label class="weui_label">发回岗位</label>
						</div>
						<div class="weui_cell_bd weui_cell_primary">
							<select class="weui_select" name="rollbackName" style="padding-left:0px;">
						
							</select>
						</div>
					</div>
					<div class="weui-cells weui-cells_form">
					  <div class="weui-cell">
					    <div class="weui-cell__bd">
					      <textarea class="weui-textarea" name="approvalOpinion" maxlength="200" placeholder="请输入文本少于200字" rows="3"></textarea>
					      <!-- <div class="weui-textarea-counter"><span>0</span>/200</div> -->
					    </div>
					  </div>
					</div>
				</form>
			</div>	
		</div>
	</div>
	<div class="weui_panel">
	
		<div class="weui_panel_bd" >
			<div class="second_part">
				<div class="weui_cells weui_cells_access"  id="weui-ca">
					
					<a class="weui_cell" href="javascript:;">		
						<div class="weui_cell_hd">
						</div>	
												
						<div class="weui_cell_bd weui_cell_primary">			
								<span class='info-box-icon i i-wenjian3' style="margin-right:10px;font-size:24px"></span><span class="">审批历史</span>		
						</div>			
					</a>						
				</div>
				<div class="mf_list_item">							
					<div class="shenpilishi">								
						<!-- <div class="ul_li">
								<div><span class="item-line"></span><i class="i i-duihao11 icon-line-font"></i><span class="line-span">刘沛·已同意</span></div>	
								<div class="item-time-div"><span>审批时间：</span><span>2017-12-12</span></div>							
						</div>	 -->							
					</div>						
				</div>					
			</div>
		</div>
	</div>
<!-- 	<div style="height:75px;width:100%;"></div> -->
	<div id="sumBtn" class="ding_btn_div" style="display:none;">
			<a href="javascript:;" class="weui_btn weui_btn_primary mf_btn " style='background-color:#38adff' onclick="doFormSubmit('#insertForm');">提交</a>
		</div>
</body>
<script>
	var dataJson = ${dataJson};//审批意见类型和还款方式
	var appId = '${mfBusPact.appId}';
	var pactId="${mfBusPact.pactId}";
	$(function(){
		$("#addBus").click(function(){
			showAppDiv();
		})
		if(dataJson){
		//审批意见类型后台拼装
			var opinionTypeList = dataJson.opinionTypeList;
			if(opinionTypeList){
				for(var i = 0 ;i<opinionTypeList.length;i++){
					var item = opinionTypeList[i];
					var optionHtml = "<option value="+item.optionValue+">"+item.optionlabel+"</option>";
					$("select[name='opinionType']").append(optionHtml);
				}
			}
			//还款方式
			var repayTypeList = dataJson.repayTypeList;
			if(repayTypeList){
				for(var i = 0 ;i<repayTypeList.length;i++){
					var item = repayTypeList[i];
					var optionHtml = "<option value="+item.id+">"+item.name+"</option>";
					$("select[name='repayTypeShow']").append(optionHtml);
				}
			}
		};
		showWkfFlowDing($(".shenpilishi"),pactId,"4");
		
	});
	function doFormSubmit(obj){
		var opinionType = $("select[name=opinionType]").val();//下拉框换成选择组件后，直接从input中取值
		var approvalOpinion  = $("textarea[name=approvalOpinion]").val();
		//验证必填项不能为空
		commitProcess(webPath+"/dingPactApproval/submitUpdateAjax?pactId="+pactId+"&opinionType="+opinionType,obj,'applySP');
	};
	function showWkfFlowDing(dom,appNo,formType){
		var $workArea = dom;
		$.ajax({
			url:webPath+"/dingWkfChart/getWkfChartInfoAjax",
			type:'post',
			data:{appNo:appNo,formType:formType,isApp:false},
			dataType:'json',
			success:function(data){
				formHtml = data.formHtml;
				formId = data.formId;
				isHtml = data.isHtml;
				var nodes = [];
				var _arr = [];
				var jsonArray = data.jsonArray;
				$.each(jsonArray, function(k, v){
					if(v.type=="start"){
						nodes.push(v);
						_arr.push(v.nextName);
					}
					$.each(jsonArray, function(k1, v1) {
						if(v1.id == _arr[_arr.length - 1]) {//根据下个审批人排序
							_arr.push(v1.nextName);
							nodes.push(v1);
						}
					});
				});
				nodes.shift();//掐头
				nodes.pop();//去尾
				console.log(nodes);
				console.log(_arr);
				for(var i=0;i<nodes.length;i++){
					addNodeVertical($workArea,nodes[i]);
				}
			}
		});
	};
	//添加审批节点
	function addNodeVertical($workArea,node){
		var opinion = "";
		var itemColorClass = "icon-color-dis";//图标颜色
		var itemLineClass = "item-line";//图标下线的颜色
		if(node.result&&node.result=="pass"){
			opinion="同意";
			itemColorClass="icon-color-pass";
			itemLineClass = "item-line item-line-pass";
		}else if(node.result&&node.result=="rollback"){
			opinion="发回重审";
			itemColorClass = "icon-color-rollback";
		}else if(node.result){
			opinion="否决";
			itemColorClass = "icon-color-rollback";
		}
		if(node.state&&node.state=="open"){
			itemColorClass="icon-color-active";
		}
		var itemHtml = "";
		if(node.state&&node.state=="open"){//当前节点不放用户
			itemHtml = "<div class='weui-cell'>"+
			    "<div class='weui-cell__bd '>"+
			     "<span class=\"line-span\">"+node.name.replace(/\\/g, "")+"&nbsp;&nbsp;</span><span style=\"float: right;\">"+opinion+"</span>"+
			    "</div>"+
			    "<div class='weui-cell__ft background-active'>审批中</div>"+
			  "</div>";
			
		}else if(node.state&&node.state!="open"){
			itemHtml ="<div class='weui-cell'>"+
			    "<div class='weui-cell__bd '>"+
			     "<span class=\"line-span\">"+node.name.replace(/\\/g, "")+"&nbsp;&nbsp;"+node.userName+"</span>"+
			    "</div>"+
			    "<div class='weui-cell__ft '><span class='background-pass' style=\"float: right;\">"+opinion+"</span></div>"+
			  "</div>"+
			   "<div class=\"item-time-div\" style='width:100%;margin-bottom:5px;'><span style='margin-left:35px;'>审批时间：</span><span>"+node.end+"</span></div>"
			   +node.appHtml;
			itemHtml = itemHtml+"<div class=\"item-approve-end-icon close \" onclick=\"DingApproval.openApproveDetail(this); \"><i class=\"i i-open-down\"></i></div>";
		}else{
			itemHtml ="<div class='weui-cell'>"+
			    "<div class='weui-cell__bd '>"+
			     "<span class=\"line-span\">"+node.name.replace(/\\/g, "")+"&nbsp;&nbsp;</span><span style=\"float: right;\">"+opinion+"</span>"+
			    "</div>"+
			    "<div class='weui-cell__ft background-dis'>未审批</div>"+
			  "</div>";
		}
		$workArea.append(itemHtml);
	}
	
	function showAppDiv(){
      $("#sumBtn").show();
		$("#headDiv").hide();
		$(".weui_panel").hide();
		$("#appDiv").show();
		
	}
	function backDiv(){
		$("#headDiv").show();
		$(".weui_panel").show();
		$("#appDiv").hide();
		$("#sumBtn").hide();
	}
</script>
</html>