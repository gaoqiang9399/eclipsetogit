<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<script type="text/javascript" src="${webPath}/component/pact/change/js/MfBusLoanChange_List.js?v=${cssJsVersion}"></script>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url : webPath+"/mfBusLoanChange/findByPageAjax", //列表数据查询的url
				tableId : "tablepact0001", //列表数据查询的table编号
				tableType : "tableTag", //table所需解析标签的种类
				pageSize:30 //加载默认行数(不填为系统默认行数)
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });

            function getDetailPage(obj,url){
                if(url.substr(0,1)=="/"){
                    url =webPath + url;
                }else{
                    url =webPath + "/" + url;
                }
                top.LoadingAnimate.start();
                window.location.href=url;
            };
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12">
					<div class="btn-div">
						<div class="col-md-2">
							<button type="button" class="btn btn-primary"
									onclick="MfBusloanChange_List.pactChangeInput();">新增
							</button>
						</div>
						<div class="col-md-8 text-center">
							<span class="top-title">保后变更</span>
						</div>
					</div>
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
					</div>
				</div>
			</div>
				<div class="row clearfix">
				<div class="col-md-12">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>
	<script type="text/javascript">
        /*我的筛选加载的json*/
        filter_dic = [
            {
                "optName" : "产品种类",
                "parm" : ${kindTypeJsonArray},
                "optCode" : "kindNo",
                "dicType" : "y_n"
            },{
                "optName": "合同金额",
                "parm": [],
                "optCode":"pactAmt",
                "dicType":"num"
            },{
                "optName": "放款金额",
                "parm": [],
                "optCode":"putoutAmt",
                "dicType":"num"
            },{
                "optName": "合同期限",
                "parm": [],
                "optCode":"term",
                "dicType":"num"
            },{
                "optCode" : "busStage",
                "optName" : "办理阶段",
                "parm" : ${flowNodeJsonArray},
                "dicType" : "y_n"
            },{
                "optName": "合同开始日期",
                "parm": [],
                "optCode":"beginDate",
                "dicType":"date"
            },{
                "optName": "放款申请日期",
                "parm": [],
                "optCode":"currPutoutAppDate",
                "dicType":"date"
            }
        ];

	</script>
</html>
