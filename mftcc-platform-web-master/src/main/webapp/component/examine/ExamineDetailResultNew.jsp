<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%
	/*String cellDatas = (String)request.getAttribute("cellDatas");*/
	String blockDatas = (String)request.getAttribute("blockDatas");
	Object dataMap = request.getAttribute("dataMap");
%>
<!DOCTYPE html>
<html lang="zh-cn">
<head >
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
		<script type="text/javascript" src="${webPath}/tech/layoutDesginer/js/echarts-all.js"></script>
		<link rel="stylesheet" href="${webPath}/tech/wkf/detail/wjProcessDetail.css" />
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailV2.js"></script>
		<script type="text/javascript" src="${webPath}/tech/wkf/detail/wjProcessDetailVertical.js"></script>
		<link rel="stylesheet" href="${webPath}/component/eval/css/detailResult.css?v=${cssJsVersion}" />
		<link id="appEvalInfo" type="text/css" rel="stylesheet" href="${webPath}/component/eval/css/appEvalInfo${skinSuffix}.css" />
		<script type="text/javascript" src="${webPath}/component/examine/js/ExamineDetailResultNew.js"></script>
		<script type="text/javascript" src="${webPath}/component/include/initScroll.js"></script>
		<link rel="stylesheet" href="${webPath}/layout/view/themes/css/initScroll.css" type="text/css"/>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/examine/js/BusExamine.js"></script>
		<link rel="stylesheet" type="text/css" href="${webPath}/component/model/css/templateIncludePage.css?v=${cssJsVersion}">
		<script type="text/javascript" src='${webPath}/component/examine/js/ExamineApplyNew.js'> </script>
		<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js?v=${cssJsVersion}"></script>
		<style type="text/css">
			.cover {
			    cursor: default;
			}

		</style>
		<script type="text/javascript">
		    var basePath = "${webPath}";
		    var dataMap = <%=dataMap%>;
		    var examHisId='${examHisId}';
		    var templateId='${templateId}';
		    var entDetailflag="${entDetailflag}";
		    var scNo="${scNo}";
			var docParm = "relNo="+examHisId+"&scNo="+scNo+"&query=query";//查询文档信息的url的参数
			var templateId="${templateId}";
			var cusNo="${cusNo}";
		 	var appId="${appId}";
		 	var pactId="${pactId}";
		 	var fincId="${fincId}";
		 	var userId = '<%=(String)request.getSession().getAttribute("regNo")%>';
		 	var examProcessId='${mfExamineHis.examProcessId}';
			$(function(){
				//检查历史列表进入详情
				if(entDetailflag=="hisList"){
					$("#examHis-div").hide();
				}
				ExamineApply.template_init(templateId);
                //贷后检查审批历史
                if(examHisId!=''){
                    //获得审批信息
                    showWkfFlowVertical($("#wj-modeler-examine"),examHisId,"","");
                }else{
                    $("#examine-spInfo-block").remove();
                }
			});
			window.onload = function(){
				var wall = new Freewall("#freewall");
				wall.reset({
					draggable: false,
					selector: '.cell',
					animate: true,
					fixSize:0,
					gutterX: 8,
					gutterY: 8,
					onResize: function() {
						wall.fillHoles();
						//wall.refresh();
					}
			});
			if('<%=blockDatas%>'!=null){
				var blockData = eval("("+'<%=blockDatas%>'+")");
				for(var key in blockData){
					var plugintype = blockData[key].plugintype;
					var $cell = $(".cell[cellid='"+key+"']");
					var info = $cell.find('.info')[0];
					if($cell.find(".cover").length>0){
						info.style.height=($cell.height()-35)+"px";
						info.style.width=($cell.width()-10)+"px";
					}else{
						info.style.height="100%";
						info.style.width="100%";
						info.style.top="0px";
						info.style.margin="0";
					}
					if(plugintype=="charts"&&typeof(blockData[key].chart)!="undefined"){
						var myChart = echarts.init(info);
						myChart.setOption(blockData[key].chart.option);
					}
				}
			}
			wall.fillHoles();
			//wall.refresh();
		}
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
		function openBigForm(obj,url){
			window.top.window.openBigForm(url,'评级',false);
		}
		</script>
	</head>
<body class="overflowHidden bg-white">
    <div class="layout container form-container">
	<div id="freewall" class="free-wall scroll-content">
			<div class='cell' cellid='cell_1' data-handle=".handle">
				<div id = "detailInfo-div" class="scroll_xy">
					<div class='cover'>
							<div class='handle'>
								<span>最新检查结果</span>
							</div>
					</div>
					<div id="evalInfo" class="info">
						<table class="evel_table">
							<tbody></tbody>
						</table>
					</div>
					<dhcc:pmsTag pmsId="loan-check-checkcard">
					<div id="examCardInfo">
						<c:forEach items="${examCardListDataList}" var="examCard" varStatus="status">
							<div id="dx${examCard.examineCardId}" name = "dx${examCard.examineCardId}" class="li_content_type">
								<div class='cover'>
									<div class='handle'>
										<span>${examCard.examineCardName}</span>
									</div>
								</div>
								<div class="li_content">
										<table class="ls_list_a" style="width:100%">
											<thead></thead>
											<tbody class="level1"></tbody>
										</table>
								</div>
							</div>
						</c:forEach>
						</div>
					</dhcc:pmsTag>

					<!-- 检查模板 -->
					<div id ="examDocTemplate-div" class="table_content">
						<div class="col-md-12 margin_left_15 column" >
							<div id="template_div">
								<div class="list-table margin_0">
									<div class="title">
										<span><i class="i i-xing blockDian"></i>文档模板</span>
									</div>
								</div>
								<div id="bizConfigs" class="template-config item-div padding_left_15"></div>
							</div>
						</div>
					</div>
					<div id ="examUpload" class="table_content">
						<div class="col-xs-12 margin_left_15 column" >
							<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
						</div>
					</div>
					<!--  贷后检查审批历史 -->
					<div id="examine-spInfo-block" class="table_content approval-hist">
						<div class="list-table col-xs-12 margin_left_15 column">
							<div class="title">
								<span><i class="i i-xing blockDian"></i>贷后检查审批历史</span>
								<button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#examine-spInfo-div">
									<i class='i i-close-up'></i>
									<i class='i i-open-down'></i>
								</button>
							</div>
							<div class="content margin_left_15 collapse in " id="examine-spInfo-div">
								<div class="approval-process">
									<div id="modeler1" class="modeler">
										<ul id="wj-modeler-examine" class="wj-modeler" isApp = "false">
										</ul>
									</div>
								</div>
							</div>
						</div>
					</div>
					<div id ="examApproveHis-div" class="table_content approval-hist" style ="display:none">
						<div class="list-table">
								<div class="title" style="background:#f8f9fc;height: 37px;">
									 <span><i class="i i-xing blockDian"></i>审批历史</span>
									 <button  class="btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#spInfo-div">
										<i class='i i-close-up'></i>
										<i class='i i-open-down'></i>
									</button>
							  	</div>
							  	<div class="content margin_left_15 collapse in " id="spInfo-div">
                                           <div class="approval-process">
                                                <div id="modeler1" class="modeler">
                                                     <ul id="wj-modeler2" class="wj-modeler" isApp = "false">
                                                     </ul>
                                                </div>
                                           </div>
                                </div>
							</div>
					</div>
					<div id ="examHis-div" class="table_content">
						<div class='cover'>
							<div class='handle'>
								<span>检查历史列表</span>
							</div>
						</div>
						<form method="post" theme="simple" name="operform" action="${webPath}/testGn/update">
							<dhcc:tableTag paginate="mfExamineHisList" property="tableexamhis0001" head="true" />
						</form>
					</div>
				</div>
			</div>
	</div>
    </div>
</body>
<script>
	var tmpHeight=$(window).height();
	var tmpWidth=$(window).width();
	$("#detailInfo-div").width(tmpWidth);
	$("#detailInfo-div").height(tmpHeight);
	$(".cell").width(tmpWidth);
	$(".cell").height(tmpHeight);
	$("form[id='TestGnAction_update'] table[id='tablist']").css({"table-layout":"fixed"});
</script>
</html>