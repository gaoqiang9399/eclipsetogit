<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
	<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
	<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
	<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
	<style type="text/css">
		#content {
			height: 89% !important;
		}
	</style>

</head>
<body class="overflowHidden">
<div class="container">
	<div class="row clearfix bg-white tabCont">
		<div class="col-md-12 column">
			<div class="btn-div">
				<div class="col-md-2">
					<button type="button" class="btn btn-primary" onclick="mfRequestPayoutBillList.applyInitInput();">新增</button>
				</div>
				<div class="col-md-8 text-center">
					<span class="top-title">请款管理</span>
				</div>
			</div>
			<div class="search-div" id="search-div">
				<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=请款编号/关联客户"/>
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

<script type="text/javascript" src="${webPath}/component/collateral/js/MfRequestPayoutBill_List.js"></script>
<script type="text/javascript">
    $(function() {
        mfRequestPayoutBillList.init();
    });

   /* function getDetailPage(obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        mfRequestPayoutBillList.getDetailPage(obj,url);
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
            mfRequestPayoutBillList.trClick(webPath+url);
        }, 300);
    }

    function ajaxInprocess(obj, ajaxUrl) {
        if(ajaxUrl.substr(0,1)=="/"){
            ajaxUrl =webPath + ajaxUrl;
        }else{
            ajaxUrl =webPath + "/" + ajaxUrl;
        }
        mfRequestPayoutBillList.ajaxInprocess(obj,ajaxUrl);
    }*/
</script>
</body>
</html>