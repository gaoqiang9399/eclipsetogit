<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>列表</title>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>	
<script type="text/javascript" src="${webPath}/component/rec/js/recallBase_Pers.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/rec/js/RecallBase_QueryEntrance.js"></script>
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
<script type="text/javascript">
	$(function() {
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/recallBase/findByPageAjax1",//列表数据查询的url
			tableId : "tablerec0002",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			myFilter : true,//是否有我的筛选
			data:{},
			pageSize : 30,//加载默认行数(不填为系统默认行数)
			topHeight : 100 //顶部区域的高度，用于计算列表区域高度。
		});
	});
	function delegateRecall(obj,url) {
				alert("请确定是否要执行此操作，按“取消”表示不进行此操作！",2,function(){
					$.ajax({
						url:url,
						dataType:"json",
						type:"get",
						success:function(data) {
							if(data.flag=="error") {
								alert(top.getMessage("FAILED_SUBMIT"),0);
							}else {
								self.location.reload();
								alert(top.getMessage("SUCCEED_COMMIT"),1);
							}
						}
					});
				});
	}
    <!-- 20190310 yxl 点击借据号展开详情-->
    function getDetailPage(obj,url){
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        top.LoadingAnimate.start();
        window.location.href=url;
        event.stopPropagation();
    }
	function openDetailInfo(obj, url){
		top.openBigForm(webPath+url,'催收详情','90','90');
	}
</script>
<style type="text/css">
.table_content .ls_list tbody tr.selected{
	background-color:#F7F7F7;
	}
</style>
</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12">
				<div class="btn-div">
					<div class="col-md-2">
						<button type="button" class="btn btn-primary" onclick="collection_handle();">催收登记</button>
						<button type="button" class="btn btn-primary" onclick="collection_Reassignment();">催收指派</button>
					</div>

					<div class="col-md-8 text-center">
						<span class="top-title">催收信息查询</span>
					</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/借据号"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;">
					<div class="pageer" pageNo="1" pageSum="1" pageSize="25"></div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">filter_dic =[{"optCode":"recallWay",
											"optName":"催收方式",
											"parm":${recallWallJsonArray},
											"dicType":"y_n"}];</script>
}
<!-- 20190304 yxl 催收办理-->
<script type="text/javascript">
	function collection_handle() {

        top.window.openBigForm(webPath+"/recallBase/collectionHandlAdd","催收登记",function(){
            //执行完弹框保存操作后会刷新页面
            updateTableData(true);
        });
    }
<!-- 20190305 yxl 催收指派-->
    function collection_Reassignment() {

        top.window.openBigForm(webPath+"/recallBase/collectionReassignment","催收指派",function(){
            updateTableData(true);
        });
    }
    <!-- 20190310 yxl 点击借据号展开详情-->
    function getDetailPage1(obj,url){
	    //判断url是否以"/"开头，如果不是需要加上”/
        if(url.substr(0,1)=="/"){
            url =webPath + url;
        }else{
            url =webPath + "/" + url;
        }
        /*top.LoadingAnimate.start();
        window.location.href=url;
        event.stopPropagation();*/
        top.window.openBigForm(url,"借据详情",function(){
            updateTableData(true);
        });
    }

</script>
</html>
