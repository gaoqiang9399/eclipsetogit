<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_TrenchBussList.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/app/applybigpage/js/applyBigPage.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
    var channelSourceNo = "${channelSourceNo}";
	$(function() {
		mfBusApplyList.init();
	});
	
	function getDetailPage(obj,url){
		mfBusApplyList.getDetailPage(obj,url);
		event.stopPropagation();
	}
	
	var timeFunc=null;
	//监听ctrl键
	document.onkeydown=function(event){
		var e = event || window.event || arguments.callee.caller.arguments[0];
		//若点击了ctrl 键则 清除timeFunc
		if(e && e.ctrlKey){ 
			clearTimeout(timeFunc);
		}
	}; 
	
	function trClick(url){		
		clearTimeout(timeFunc);
		timeFunc=setTimeout(function(){
			mfBusApplyList.trClick(url);
		}, 300);
	}

	function ajaxInprocess(obj, ajaxUrl) {
		mfBusApplyList.ajaxInprocess(obj,ajaxUrl);
	}
</script>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div style="display:none;">
					<input name="kindName" type="hidden"></input>
					<input name="kindNo" type="hidden"></input>
				</div>
				<!-- <div class="btn-div">
						<button type="button" class="btn btn-primary" onclick="mfBusApplyList.applyInput();">进件</button>
						<button type="button" class="btn btn-primary" onclick="mfBusApplyList.applyInputBusCommon();">进件</button>
				</div> -->

				<div class="btn-div">
					<!-- 渠道商进件 -->
					<button type="button" class="btn btn-primary" onclick="mfBusApplyList.applyInputByTrench();">进件</button>
				</div>

				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/项目名称"/>
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
	/*我的筛选加载的json*/
	filter_dic = [{
		"optName" : "客户类别",
		"parm" : ${cusTypeJsonArray},
		"optCode" : "cusType",
		"dicType" : "y_n"
	}, {
		"optName" : "申请金额",
		"parm" : [],
		"optCode" : "appAmt",
		"dicType" : "num"
	},{
		"optName" : "申请日期",
		"parm" : [],
		"optCode" : "appTime",
		"dicType" : "date"
	},{
		"optName" : "申请期限",
		"parm" : [],
		"optCode" : "term",
		"dicType" : "num"
	}, /* {"optCode":"termType",
		"optName":"期限类型",
		"parm":[{"optName":"月","optCode":"1"},
				{"optName":"天","optCode":"2"}],
		"dicType":"y_n"
	},  {
		"optCode" : "busStage",
		"optName" : "办理阶段",
		"parm" : ${flowNodeJsonArray},
		"dicType" : "y_n"
	},{
					"optCode" : "vouType",
					"optName" : "担保方式",
					"parm" : [ {
						"optName" : "信用担保",
						"optCode" : "1"
					}, {
						"optName" : "保证担保",
						"optCode" : "2"
					}, {
						"optName" : "抵押担保",
						"optCode" : "3"
					}, {
						"optName" : "质押担保",
						"optCode" : "4"
					} ],
					"dicType" : "y_n"
				} 
		{
		"optCode" : "cusType",
				"optName" : "客户类型",
				"parm" : ${cusTypeJsonArray},
				"dicType" : "y_n"
	},/* {
		"optName" : "联系人",
		"parm" : [],
		"optCode" : "contactsName",
		"dicType" : "val"
	},{
		"optName" : "联系电话",
		"parm" : [],
		"optCode" : "contactsTel",
		"dicType" : "val"
	},{
		"optName" : "项目名称",
		"parm" : [],
		"optCode" : "appName",
		"dicType" : "val"
	}, */{
		"optName" : "产品种类",
		"parm" : ${kindTypeJsonArray},
		"optCode" : "kindNo",
		"dicType" : "y_n"
	},/*  {
		"optName" : "基本客户类型",
		"parm" : ${gdCusTypeJsonArray},
		"optCode" : "cusBaseType",
		"dicType" : "y_n"
	}, */
	];
	//           addDefFliter("0","担保方式","vouType","VOU_TYPE","1,2,3");
	// 		  addDefFliter("0","申请状态","appSts","APP_STS","0,1,2,3,4,5");
</script>
</html>
