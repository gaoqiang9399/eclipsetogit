<%-- [{"width":"799px","height":"658px","left":"0px","top":"0px","name":"点此拖拽","cellid":"cell_1"},{"width":"517px","height":"153px","left":"807.2666625976562px","top":"0px","name":"业务状态","cellid":"cell_2"},{"width":"517px","height":"194px","left":"807.2666625976562px","top":"161.4499969482422px","name":"押品性质","cellid":"cell_3"},{"width":"517px","height":"295px","left":"807.2666625976562px","top":"363.26666259765625px","name":"信息变更记录","cellid":"cell_4"}] --%>
<%-- {"cell_1":{"cellid":"cell_1","cellname":"点此拖拽","chart":{}},"cell_2":{"cellid":"cell_2","cellname":"业务状态","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_3":{"cellid":"cell_3","cellname":"押品性质","celltype":"","cellsts":"","plugintype":"","chart":{}},"cell_4":{"cellid":"cell_4","cellname":"信息变更记录","celltype":"","cellsts":"","plugintype":"","chart":{}}} --%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="app.component.common.BizPubParm" %>
<%@ include file="/component/include/common.jsp"%>

<%
	String investigateScNo = BizPubParm.SCENCE_TYPE_DOC_INVESTIGATION;
%>

<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<style type="text/css">

			.cover {
				cursor: default;
			}
		</style>
		<script type="text/javascript" src="${webPath}/themes/factor/js/viewpointggl.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/wkfApproveIdea.js"></script>
		<script type="text/javascript">
		var cusNo,appId,pactId,fincId,pleId;
		var cusType = '${mfCusCustomer.cusType}';
		var cusBaseFlag = '${cusBaseFlag}';//表示客户基本信息是否已经录入
		var cusFullFlag = '${cusFullFlag}';//客户信息所有表单信息是否已经全部录入
		var scNo = '${scNo}';//客户要件场景
		var authFlag = '${authFlag}';
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var menuBtn=[];
		var viewPointData = eval("("+'<%=request.getSession().getAttribute("viewPointData") %>'+")");
		var vpNo = '1';
		var appId,wkfAppId,cusNo,appSts,coreCusNo,wareHouseCusNo;
		var investigateScNo ='<%=investigateScNo%>';
		var headImg = '${headImg}';
		var ifUploadHead = '${ifUploadHead}';
		var busModel = '${busModel}';
		var cusNoTmp = "";
		var riskLevel = '${riskLevel}';
		var modelNo = '${mfBusApply.pactModelNo}';
		$(function(){
			cusNo = '${mfBusApply.cusNo}';
			appId = '${mfBusApply.appId}';
			wkfAppId = '${mfBusApply.wkfAppId}';
			appSts = '${mfBusApply.appSts}';//申请状态在BizPubParm.java中的APP_STS_***中
			coreCusNo = '${coreCusNo}';
			wareHouseCusNo = '${wareHouseCusNo}';
			var relNo = "cusNo-"+cusNo;
			//申请详情信息，根据期限类型展示月/日
			var termType = $("[name=termType]").val();
			if(termType!=undefined){
				if(termType=="1"){
					$("input[name=term]").parent().prev().html("&nbsp;个月");
				}else if(termType=="2"){
					$("input[name=term]").parent().prev().html("&nbsp;天");
				}
			}
			//业务模式是保兑仓时隐藏仓储方。展示核心企业			
			if(busModel=="4"){			
				if(coreCusNo=="0"){
					$("div[data-view=cuscore]").hide();
				}else{
					$("div[data-view=cuscore]").show();
				}
				$("div[data-view=cuswarehouse]").hide();
				$("div[data-view=buyinfo]").hide();	
			}else{
				if(wareHouseCusNo=="0"){
					$("div[data-view=cuswarehouse]").hide();
				}else{
					$("div[data-view=cuswarehouse]").show();
				}
				$("div[data-view=cuscore]").hide();
				$("div[data-view=buyinfo]").hide();	
				
			}
			if(busModel==5){
			    if(coreCusNo=="0"){					
					$("div[data-view=buyinfo]").hide();													
				}else{
					$("div[data-view=buyinfo]").show();	
				}
				$("div[data-view=cuscore]").hide();
				$("div[data-view=cuswarehouse]").hide();
			}
							
			var data=headImg;
			if(ifUploadHead!="1"){
				data = "themes/factor/images/"+headImg;
			}
			data = encodeURIComponent(encodeURIComponent(data));
			document.getElementById('headImgShow').src=webPath+"/uploadFile/viewUploadImageDetail?srcPath="+data;
			//审批信息模块
			if(appSts != '0' && appSts != '1'){
				//获得审批信息
				getSPInfo();
			}else{
				$("#spInfo-div").remove();
			}
			
			setBlock("2","费用标准","busfee",webPath+'/mfBusAppFee/getListPage?appId='+appId);
			
			getNextBusPoint();
			
			adjustheight();
			var hisTaskList = eval("(" + '${json}' + ")").hisTaskList;
			$.each(hisTaskList,function(i,hisTask){
				if(hisTask.state = 'completed'){
					
					if(hisTask.approveIdea !="同意" && hisTask.approveIdea !=""){
						var getUrl = hisTask.approveIdea.split('#')[1];
						var showType = hisTask.approveIdea.split('#')[0];
						var title = hisTask.description;
						if(typeof(getUrl) != "undefined" && typeof(getUrl) != null && getUrl != ''){
							
							setBlock(showType,title,hisTask.dbId,getUrl);							
							
						}
					}
				}
				
			}); 
			
			$("*").removeAttr("onClick");
			$("*").removeAttr("onclick");
			$("*").removeClass("dblclickflag");
			$("*").removeClass("bolddblclickflag");
			$("a").attr("href","javascript:void(0)");
			
			adjustheight();
			
			
		});
		
		
		function setBlock(showType,title,name,getUrl){
			var clearDiv = '<div style="clear:both;"></div>';
			var htmlStr = "";
			
			if(showType == "1"){
				
				var formInfo = "<div class='title'><span>"+title+"</span></div>"	
				 + "<div class='content' name='"+name+"'></div>";
				
				if($(".block-info").hasClass("add-block-here")){
					htmlStr = htmlStr + "<div class='right-block-info'>" + formInfo + "</div>";
					$(".add-block-here").find(".form-table").append(htmlStr);		
					$(".add-block-here").removeClass("add-block-here");
				}else{
					htmlStr = htmlStr + "<div class='block-info add-block-here'><div class='form-table'>"
							+ "<div class='left-block-info' >" + formInfo + "</div>"
							+ "</div></div>";
					$(".block-new-block").before(htmlStr);
					$(".block-new-block").before(clearDiv);
				}
			}else if(showType == "2"){
				var tableStr = "";
				htmlStr = htmlStr + "<div class='block-info'>"
				+ "<div class='list-table'>"
				+ "<div class='title'><span>"+title+"</span></div>"
				+ "<div class='content margin_left_15' name='"+name+"'>"+tableStr+"</div>"
				+ "</div>"
				+ "</div>";
				$(".block-new-block").before(htmlStr);
				$(".block-new-block").before(clearDiv);
			}
			$.ajax({
				url:getUrl,
				type:'post',
				dataType:'html',
				success:function(data){
					var $html = $(data);
					if(showType == "1"){
						var formStr = $html.find("form").prop("outerHTML");
						$(".content[name='"+name+"']").html(formStr);
					}else if(showType == "2"){
						var tableStr = $html.find("table").prop("outerHTML");
						$(".content[name='"+name+"']").html(tableStr);
					}
					adjustheight();
				},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		};
		
		function adjustheight(){
			$("#freewall > div:nth-child(1)").css("height","auto");
			$("#freewall > div.cell.right-cell").css("height","auto");
			//调整左边和右边的height，使高度小的适应高度大的
			var leftheight = parseInt($("#freewall > div:nth-child(1)").height());
			var rightheight =  parseInt($("#freewall > div.cell.right-cell").height());
			if(leftheight > rightheight){
				$("#freewall > div.cell.right-cell").css("height",leftheight);
			}else if(leftheight < rightheight){
				$("#freewall > div:nth-child(1)").css("height",rightheight);
			} 
		};
		
		function getNextBusPoint(){
			
			$(".block-next").empty();
			$(".block-next").unbind("click.next");
			$(".block-next").removeClass("toDo");
			var busPointInfo = '';
			if(appSts == '4'){//审批通过
				busPointInfo = "<span>申请已审批通过，请在合同视角中查看信息</span>";
				$(".block-next").append(busPointInfo);
				$(".block-next").css("display","block");
			}else if(appSts == '5'){//申请被否决
				busPointInfo = "<span>申请已被否决，业务结束</span>";
				$(".block-next").append(busPointInfo);
				$(".block-next").css("display","block");
			}else{
				$.ajax({
					url:webPath+"/mfBusApply/getTaskInfoAjax?wkfAppId="+wkfAppId,
					type:'post',
	    			dataType:'json',
					success:function(data){
						var url = data.url;
						var title=data.title;
						var nodeName = data.nodeName;
						if(menuBtn.indexOf(nodeName)!="-1"){
							$(".block-next").append("<span id='point'>下一业务步骤：<a class='font_size_20'>"+title+">></a></span>");
							$(".block-next").bind("click.next", function(){
								toNextBusPoint(url,title,nodeName);
							}); 
							$(".block-next").addClass("toDo");
							$(".block-next").css("display","block");
							if(nodeName!="investigation"){
								//调查资料
								var pleFlag = '${pleFlag}';
								var vouType = '${vouType}';
								if(vouType!="1" && pleFlag=="1"){
									$(".bus-investigate").show();
								}
							}
						}
						adjustheight();
					}
				});
			}
		};
	
		//跳转至下一业务节点
		function toNextBusPoint(url,title,nodeName){
			if(riskLevel == "99"){//riskLevel为99标书拒绝级业务
				var pointInfo = '<div style="height: 120px;padding: 20px;width: 300px;"><div style="height: 40px;">该业务风险过高，无法进行下一步</div><div><a  href="javaScript:void(0);" onclick="busRisk();">查看风险>></a></div></div>';
				dialog({
					title:'风险提示',
					id:"riskInfoDialog",
					backdropOpacity:0,
					content:pointInfo
				}).showModal();
				return false;
			}
					top.flag=false;//表示是否进行业务操作
					top.submitFlag=false;
					top.pleFlag = false;
					top.wareHouseFlag = false;
					top.coreFlag = false;
					top.getInfoFlag = false;//业务操作后表示是否需要获得信息
					var tmpUrl=url.split("&")[0];
					var popFlag = tmpUrl.split("?")[1].split("=")[1];
					if(popFlag=="0"){
						alert("确定进行“业务审批”操作？",2,function(){
							if(!valiDocIsUp(appId)){
								return false;
							} 
							LoadingAnimate.start();
							$.ajax({
								url:url,
								type:'post',
				    			dataType:'json',
				    			async:false,
				    			complete: function() {
				    				LoadingAnimate.stop();
								},
								success:function(data){
									if(data.flag=="success"){
										if(data.node=="processaudit"){
											//审批提醒弹窗，不自动关闭
											window.top.alert(data.msg,3);
											$(".block-next").hide();
										}else{
											getNextBusPoint();
										}
									}else{								    
										alert(data.msg,0);
									}
								}
							});
						});
					}else{
						if(nodeName=="investigation"){
							url = url+"&scNo="+investigateScNo;
						}
						//押品登记节点，选择押品类别
						if(nodeName=="pledge_reg"){
							openPleDyForm(url,title);
						}else{
							top.window.openBigForm(url,title,wkfCallBack);
						}
					}
// 				}
// 			});
		}
		function openPleDyForm(url,title){
			$.ajax({
				url:webPath+"/mfBusPledge/getPleDyFormInfo",
				data:{appId:appId},
				type:'post',
				dataType:'json',
				success:function(data){
					top.pleFlag=false;
					var formid_new = data.formid_new;
					url = url+'&formid_new='+formid_new;
					top.window.openBigForm(url,title,goToPleDetailInfo);
				},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		}

		//获取押品信息
		function goToPleDetailInfo(){
			if(top.pleFlag){
				window.location.href=webPath+"/mfBusPledge/getPledgeById?cusNo="+cusNo+"&appId="+appId;
			}
		};
		//选择押品类别
		function selectPledgeClass(url,title){
			$.ajax({
				url:webPath+"/mfBusPledge/getIfRegisterPledge",
				data:{appId:appId},
				type:'post',
				dataType:'json',
				success:function(data){
					//没有登记押品信息，打开押品类别；登记过，打开押品清单登记页面
					if(data.flag == "0"){
						top.openBigForm('${webPath}/MfPledgeClassAction_getAllPledgeClassList.action?cusNo='+cusNo+'&appId='+appId,'押品类别',wkfCallBack);
					}else{
						top.window.openBigForm(url,title,wkfCallBack);
					}
				},error:function(){
					alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
				}
			});
		}
	
		//回调函数
		function wkfCallBack(){		
			if(top.flag){
				getNextBusPoint();
				if(top.pleFlag){
					setPleInfo(top.pleInfo);
					$(".bus-investigate").show();
				}
				if(top.wareHouseFlag){
					$("div[data-view=cuswarehouse]").show();
					cusNoTmp = top.cusInfo.cusNo;
					//setWareHouseInfo(top.cusInfo);
				}
				if(top.coreFlag){
					if(busModel=="4"){
						$("div[data-view=cuscore]").show();
					}else if(busModel=="5"){
					$("div[data-view=buyinfo]").show();
					}
					
					cusNoTmp = top.cusInfo.cusNo;
					//setWareHouseInfo(top.cusInfo);
				}
				if(top.getInfoFlag){
					if(top.showType != null){
						setBlock(top.showType,top.title,top.name,top.getInfoUrl);
					}
				}
			}
			
		}


		function setPleInfo(pleInfo){
			
			$(".ple-block").empty();
			var htmlStr = '<div class="i i-qiehuan qiehuan"  onclick="getPleInfo();" ></div>'
						+ '<div style="clear:both;"></div>'
						+ '<div style="margin-top:-10px;">'
						+ '<img src="${webPath}/themes/factor/images/pledge4.png" class="left-img pointer" onclick="getInfoForView(\'ple\',\''+appId +'\');" />'
						+ '<div class="block-content">'
						+ '<p class="content-title">'+ pleInfo.pledgeName + '<span class="margin_left_20">' + pleInfo.pledgeNoShow + '</span></p>'
						+ '<p><span class="myspan2">已引用</span><span class="myspan1">' + pleInfo.envalue +'</span><span class="myspan2">万</span>'
						+ '</p></div></div>'
						+'<div class="line-div"></div>';
			$(".ple-block").html(htmlStr);
			
		};
		
		
		function getCusInfo(cusNoThis){ 
			window.location.href=webPath+"/mfCusCustomer/getById?cusNo="+cusNoThis+"&cusType=101";
		}
	
		//获取押品信息
		function getPleInfo(){
			window.location.href=webPath+"/mfBusPledge/getPledgeById?cusNo="+cusNo+"&appId="+appId;
		};
		
	
		
		function getInfoForView(typeThis,id){//弹窗查看块状信息
			if(typeThis == 'cus'){
				//处理新增仓储方或核心企业保存后，点击仓储方或核心企业按钮查看详情
				if(cusNoTmp!=""){
					id = cusNoTmp;
				}
				top.window.openBigForm(webPath+'/mfCusCustomer/getByIdForShow?cusNo='+id,'客户信息',viewCloseCallBack);
			}else if(typeThis == 'ple'){
				top.window.openBigForm(webPath+'/mfBusPledge/getByIdForView?appId='+id,'押品信息',viewCloseCallBack);
			}
		}
		function viewCloseCallBack(){
			
		}
		
		
		//业务资料信息
	 	function busDocInfo(){
	 		top.window.openBigForm(webPath+'/docManage/pubUpload?relNo='+appId+'&cusNo='+cusNo,"业务资料",viewCloseCallBack);
	 	};
		//风险拦截
		function busRisk(){
			if(!(dialog.get('riskInfoDialog') == null)){
				dialog.get('riskInfoDialog').close();
			}
			top.createShowDialog(webPath+'/riskForApp/preventList?relNo='+appId,'风险拦截信息');
		};
		
		function getSPInfo(){
				$.ajax({
					type: "post",
					data:{appNo:"${appId}"},
					dataType: 'json',
					url: webPath+"/wkfApprovalOpinion/getApplyApprovalOpinionList",
					contentType: "application/x-www-form-urlencoded; charset=UTF-8",
					success: function(data) {
						Wkf_zTreeNodes=data.zTreeNodes;
						Wkf_zTreeObj = $.fn.zTree.init($("#wfTree"), Wkf_setting, Wkf_zTreeNodes);
					},
					error: function(XMLHttpRequest, textStatus, errorThrown) {
						console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
					}
				});
		};
		
		
		
			//验证文档是否上传
	function valiDocIsUp(relNo){
		var flag = true;
		$.ajax({
			type: "post",
			dataType: 'json',
			url: webPath+"/docBizManage/valiWkfDocIsUp",
			data:{relNo:relNo},
			async: false,
			success: function(data) {
				if(!data.flag){
					window.top.alert(data.msg,0);
				}
				flag = data.flag;
			},
			error: function(XMLHttpRequest, textStatus, errorThrown) {
				console.log(XMLHttpRequest.status+"-"+XMLHttpRequest.readyState+"-"+textStatus);
			}
		});
		return flag;
	}

	//费用信息编辑
	function getFeeById(obj,url){
		var $obj = $(obj);
		top.obj = $obj.parents(".block-info");
		top.htmlStrFlag = false;//标识是否将客户表单信息的html字符串放在top下
		top.htmlString = null;
    	top.window.openBigForm(url,'修改费用项',closeCallBack1);
	};

	function closeCallBack1(){
		var $obj = $(top.obj);
		if(top.htmlStrFlag){
			$obj.find(".content").empty();
			$obj.find(".content").html(top.htmlString);
			adjustheight();
		}
	};
	

	
	//查看产品信息
	function getKindInfo(){
		top.window.openBigForm(webPath+'/mfBusAppKind/getById?appId='+appId,'产品信息',viewCloseCallBack);
	}
	
	
	
	
	
	</script>
	</head>
		
	<script type='text/javascript' src='${webPath}/UIplug/zTree/jquery.ztree.all-3.5.min.js'></script>
<link rel="stylesheet" href="${webPath}/UIplug/zTree/zTreeStyle/zTreeStyle.css" />
<link rel="stylesheet" href="${webPath}/component/wkf/css/wkf_approval.css" />
<body style="overflow-y:auto;">				
    <div class="layout">
		<div id="freewall" style="margin: 8px;height: auto;" class="free-wall">
			<div class='cell' cellid='cell_1' style='top:0px; left:2px; width:65.5%; height: auto;background-color:#FFFFFF;' data-handle=".handle">
				<div class="info margin_top_025">
					<!--主要信息 -->
					<div class="block-info">
						<c:if  test='${mfBusApply.appSts=="0" || mfBusApply.appSts=="1"}'>
							<div class="block-next font_size_14"></div>
						</c:if>
					</div>
					<div class="block-info">
						<!--业务主要信息 -->
						<div class="bus-main-info" >
							<div class="icon-div">
								<div class="i i-applyinfo bus-pact font77 font-color-apply">
									<span class="font-img-span">申请信息</span>
								</div>
								<c:choose> 
								  <c:when test="${fn:length(mfBusApply.kindName)>8}">
								     <span class="kind-span pointer" onclick="getKindInfo();" title="${mfBusApply.kindName}/>">${fn:substring(mfBusApply.kindName, 0, 8)}... <span>
								  </c:when> 
								  <c:otherwise>  
									  <span class="kind-span  pointer" onclick="getKindInfo();">${mfBusApply.kindName}<span> 
								  </c:otherwise> 
								</c:choose> 
							</div>
							<div class="main-div" style="margin-top: 25px">
								<p class="font_size_18" style="margin: 0px 0px 14px 2px;" >${mfBusApply.appName}</p>
								<div class="info-show-div">
									<!-- 风险拦截级别 -->
									<div class="hidden-status-block risklevel${riskLevel} margin_bottom_4" onclick="busRisk();">
										<i class="i i-risk"></i>
										${riskName}
									</div >
								</div>
								<div style="clear:both;"></div>
								<div class="detail-info-div">
									<p class="font_size_26 " style="margin-top: 15px">
										<span class="color_theme"><i class="i i-rmb"></i><s:property value='mfBusApply.fincAmt'/></span><span class="myspan2 color_black margin_right_65">万</span>
										<span class="color_theme"><i class="i i-richengshezhi"></i><s:property value='mfBusApply.term'/></span><span class="myspan2 color_black margin_right_65 ">
										<c:choose> 
										  <c:when test="${mfBusApply.termType=="1"}">   
											个月
										  </c:when> 
										  <c:otherwise>   
											天
										  </c:otherwise> 
										</c:choose> </span>
										<span class="color_theme"><i class="i i-tongji1"></i>${mfBusApply.fincRate}</span><span class="myspan2 color_black margin_right_65">%</span>
									</p>
								</div>
							</div>
						</div>
					</div>
					<div style="clear:both;"></div>
					<div style="clear:both;"></div>
					<div class="block-info">
						<div class="base-info">
							<div class="content">
								<form method="post" theme="simple" name="operform" action="${webPath}/mfBusApply/updateAjaxByOne">
									<dhcc:propertySeeTag property="formapply0006" mode="query"/>
								</form>
							</div>
						</div>
					</div>
					<div style="clear:both;"></div>
					
					
					<div class="block-new-block"></div>
					<div style="clear:both;"></div>
					<div class="block-info">
						<div class="form-table">
						   <div class="title">
							 <span><i class="i i-xing blockDian"></i>审批历史</span>
							 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
								<i class='i i-close-up'></i>
								<i class='i i-open-down'></i>
							</button>
						   </div>
							<div class="content margin_left_15 collapse in" id="spInfo-div">
								<iframe src='tech/wkf/processDetail.jsp?appNo=${mfBusApply.appId}&appWorkflowNo=${mfBusApply.applyProcessId}' 
									marginheight="0" marginwidth="0" frameborder="0" scrolling="no" width="100%" height="100%" id = "processDetailIframe">
								</iframe>
								<ul id="wfTree" class="ztree">
			 					</ul>
							</div>
						</div>
					</div>
					
				</div>
			</div>
			<div class='cell right-cell' cellid='cell_2' style='top:0px; left:65.5%; height: auto; background-color:#FFFFFF;margin-left: 10px;' data-handle=".handle">
				<div class="info margin_top_025" >
					<div class="block-info block-view" data-view='cusloan' style="display: none;">
						<div class="i i-qiehuan qiehuan pointer"  onclick="getCusInfo('<s:property value='mfCusCustomer.cusNo'/>');" ></div>
						<div style="clear:both;"></div>
						<div style="margin-top:-10px;">
							<img id="headImgShow" name="headImgShow" class="left-img user-img pointer" onclick="getCusInfo('<s:property value='mfCusCustomer.cusNo'/>');" />
							<div class="block-content">
								<p class="content-title">
									<span class="pointer" onclick="getCusInfo('<s:property value='mfCusCustomer.cusNo'/>');">
									<s:property value='mfCusCustomer.cusName'/></span>
								</p>
								<p class="font_size_14">
									<span><i class="i i-ren1"></i><s:property value='mfCusCustomer.contactsName' /></span>
									<span class="vertical-line">|</span>
									<span><i class="i i-dianhua"></i><s:property value='mfCusCustomer.contactsTel' /></span>
									<span class="vertical-line">|</span>
									<span><i class="i i-idcard2"></i><s:property value='mfCusCustomer.idNum' /></span>
								</p>
								<p class="font_size_14">
								<i class="i i-location"></i>
								<c:choose> 
								  <c:when test="${mfCusCustomer.commAddress!=""}">   
									${mfCusCustomer.commAddress}
								  </c:when> 
								  <c:otherwise>   
									未登记
								  </c:otherwise> 
								</c:choose>
								</p>
							</div>
						</div>
					<div class="line-div"></div>
					</div>
					<div class="block-info ple-block block-view" data-view='pledge' style="display: none;">
						<c:if  test='${pleFlag=="1"}'>
							<div class="i i-qiehuan qiehuan pointer"  onclick="getPleInfo();" ></div>
							<div style="clear:both;"></div>
							<div style="margin-top:-10px; position:relative;">
								<div class="i i-pledge font-img font-color-pledge pointer" onclick="getPleInfo();">
									<span class ="font-img-span-right" >押品信息</span>
								</div>
								<div class="block-content">
									<p class="content-title" title=<s:property value='mfBusPledge.pledgeName'/>>
									<span class="pointer" onclick="getPleInfo();">
										${mfBusPledge.pledgeName}
									</span>
									<span class="margin_left_20 pointer" onclick="getPleInfo();" title=<s:property value='mfBusPledge.pledgeNoShow'/> >
									${mfBusPledge.pledgeNoShow}</span></p>
									<p>
										<span class="myspan2">已引用</span> 
										<span id='envalueAmt' class="myspan1"><i class="i i-rmb"></i><s:property value='mfBusPledge.envalueAmt'/></span><span class="myspan2">万</span>
										<span id='receiptsAmount' class="myspan1"><i class="i i-wenjian"></i><s:property value='mfBusPledge.receiptsAmount'/></span><span class="myspan2">张单据</span>
<%-- 										<span class="myspan1"><s:property value='mfBusPledge.pledgeRate'/></span><span class="myspan2">%</span> --%>
									</p>
								</div>
							</div>
						</c:if>
						<c:if  test='${pleFlag=="0"}'>
							<div class="none-info-left-img">
								<div class="i i-pledge font-img-none">
									<span class="font-img-span-none">押品信息</span>
								</div>
								<span class="none-info-left-img-span">暂无押品</span>
							</div>
						</c:if>
						<div class="line-div"></div>
					</div>
					<div class="block-info his-info block-view" data-view='hisinfo' style="display: none;">
						<div class='his-info-title'>
							<div class='handle'>
								<span id="hisinfo-span">信息变更记录</span>
								<div class="his-detail-opt" ></div>
							</div>
						</div>
						<table>
							<c:forEach items="${bizInfoChangeList}" var="bizInfoChange" varStatus="status">
								<tr style="height: 30px;">
									<td  style="text-align: left;font-size: 14px;width: 100px;">
										${bizInfoChange}
									</td>
									<td style="color: #7f7f7f;font-size: 12px;"  align="left">
										  ${status.count}
									</td>
								</tr>
							</c:forEach>
							<%-- <s:iterator value="bizInfoChangeList" status="statu" > 
							<tr style="height: 30px;">
								<td  style="text-align: left;font-size: 14px;width: 100px;">
									<s:property value="date" />
								</td>
								<td style="color: #7f7f7f;font-size: 12px;"  align="left">
									<s:property value="cont" />
								</td>
							</tr>
							</s:iterator> --%>
						</table>
					</div>
				</div>
			</div>
		</div>
    </div>
</body>
</html>