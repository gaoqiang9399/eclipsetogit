<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
		<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
		<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
		<script type="text/javascript" >
            var cusType = '${cusType}';
            var cusNo = '${cusNo}';
			$(function(){
                init();
			 });
            function init() {
                LoadingAnimate.start();
                myCustomScrollbar({
                    obj:"#content",//页面内容绑定的id
                    url:webPath+"/mfUserPermission/selectCocoboListAjax",//列表数据查询的url
                    data:{
                        cusNo:cusNo,
                        formId:"comLoanApplyBase",
                        element:"coborrName",
                        cusBaseType:"",
                        cusType:"",
                    },
                    tableId:"tablecus00001",//列表数据查询的table编号
                    tableType:"thirdTableTag",//table所需解析标签的种类
                    pageSize:30,//加载默认行数(不填为系统默认行数)
                    callback:function(){
                        LoadingAnimate.stop();
                    }//方法执行完回调函数（取完数据做处理的时候）
                });
            }
        function choseCus(parm) {
            parm = parm.split("?")[1];
            var parmArray = parm.split("&");
            var cusNo = parmArray[0].split("=")[1];
            var cusInfo = new Object();
            cusInfo.cusNo = cusNo;
            parent.dialog.get("coborrDialog").close(cusInfo).remove();
        }
        function inputCus(){
                top.window.openBigForm(webPath+"/mfCusCustomer/inputCoborr","新增客户", function(){
                    init();
                });
            }
		</script>
	</head>
	<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div" id="search-div">
						<button type="button" class="btn btn-primary" onclick="inputCus();">新增</button>
					<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称"/>
				</div>
			</div>
		</div>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
					</div>
				</div>
			</div>
		</div>
	</body>	

</html>
