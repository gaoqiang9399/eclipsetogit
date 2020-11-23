<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" src="${webPath}/component/cus/js/MfCusCustomerMore_List.js?v=${cssJsVersion}"></script>
		<script type="text/javascript" >
			var action = "${action}";
			var formEditFlag = "${formEditFlag}";
			var cusNo = "${cusNo}";
			var title = "${title}";
            top.moreCusInfo = true;
			$(function(){
			    myCustomScrollbar({
                    obj:"#content",//页面内容绑定的id
                    url:webPath+"/mfCusCustomer/getDyFormHtmlPageAjax?action="+action+"&formEditFlag="+formEditFlag+"&cusNo="+cusNo,//列表数据查询的url
                    tableType:"thirdTableTag",//table所需解析标签的种类
                    topHeight:30,
                    pageSize:30,//加载默认行数(不填为系统默认行数)
					formEditFlag:formEditFlag
			    });

				if (typeof(formEditFlag) != "undefined" && formEditFlag == "query") {//业务视角/客户视角登记的表单在editFlag=query时，客户表单块不允许操作（新增、删除）
					$(".formAdd-btn").hide();
					$(".editBtn").html("编辑");;
					//删除
					$(".delBtn").html("删除");
				}
			 });

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
		</script>
	</head>
	<body class="overflowHidden">
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder= 请输入关键字"/>
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
	</body>
</html>
