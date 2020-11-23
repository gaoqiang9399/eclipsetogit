<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/pact/css/MfPactFiveclass_commonForBatch.css" />
		<script type="text/javascript" src='${webPath}/component/pact/js/MfPactFiveclass_commonForBatch.js'></script>
		<script type="text/javascript" src='${webPath}/component/pact/js/MfPactFiveclass_ListForBatch.js'></script>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfPactFiveclass/findFiveClassAndPactByPageAjaxForBatch",//列表数据查询的url
			    	tableId:"tablequeryfiveandpactbatch",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize : 30,//加载默认行数(不填为系统默认行数)
					topHeight : 100, //顶部区域的高度，用于计算列表区域高度。
                    callback:function(){
                    	MfPactFiveclass_commonForBatch.changeTrColor();
                    }//方法执行完回调函数（取完数据做处理的时候）
			    });
			    
                $("#tab").on("change", "select[name='nowFiveclass']", function(){
                	var _tr = $(this).parents("tr");
                	MfPactFiveclass_commonForBatch.changeSelectColor(_tr);
                });
			});
			
			 function getDetailApply(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	window.location.href=url;
			 }
			 function getDetailCus(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	window.location.href=url;
			 }
			 function getDetailFive(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			 	window.location.href=url;
			 }
			 
			function fiveclassView(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
				top.openBigForm(url, "五级分类详情",function(){});
			}
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
						<dhcc:pmsTag pmsId="fiveclass-storage">
							<button type="button" class="btn btn-primary" onclick="MfPactFiveclass_ListForBatch.storage();">暂存</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="fiveclass-batch-apply">
							<button type="button" class="btn btn-primary" onclick="MfPactFiveclass_ListForBatch.batchApply();">批量认定申请</button>
						</dhcc:pmsTag>
                        <dhcc:pmsTag pmsId="fiveclass-stage-apply">
                            <button type="button" class="btn btn-primary" onclick="MfPactFiveclass_ListForBatch.stageApply();">阶段认定</button>
                        </dhcc:pmsTag>
						<dhcc:pmsTag pmsId="fiveclass-batch-result">
							<button type="button" class="btn btn-primary" onclick="MfPactFiveclass_ListForBatch.applyResult();">五级分类结果</button>
						</dhcc:pmsTag>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=项目申请号/借据号"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
		<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		filter_dic = [
				{
	                "optName": "借据金额",
	                "parm": [],
	                "optCode":"putoutAmt",
	                "dicType":"num"
	            },
		      {
                  "optName": "上次认定结果",
                  "parm": ${fiveStsJsonArray},
                  "optCode":"lastFiveclass",
                  "dicType":"y_n"
              }, {
                  "optName": "本次自动分类结果",
                  "parm": ${fiveStsJsonArray},
                  "optCode":"systemFiveclass",
                  "dicType":"y_n"
              }, {
                  "optName": "客户经理认定结果",
                  "parm":  ${fiveStsJsonArray},
                  "optCode":"nowFiveclass",
                  "dicType":"y_n"
              }];
	</script>
</html>
