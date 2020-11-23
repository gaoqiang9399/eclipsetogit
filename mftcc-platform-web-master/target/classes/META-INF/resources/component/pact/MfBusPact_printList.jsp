<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
        <script type="text/javascript" src="${webPath}/component/model/js/mfPageOffice.js"></script>
		<script type="text/javascript" >
			var queryType = "${queryType}";
			var url = webPath+"/mfBusPact/findLoanAfterByPageAjax";
			var tableId = "tablepactfincPrint";
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:url,//列表数据查询的url
			    	tableId:tableId,//列表数据查询的table编号
			    	tableType:"tableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{pactSts:"8"}//指定参数，过滤掉已经封挡的数据
			    });
                //绑定全选事件
                $("th[name='sel_all']").click(function(){
                    _checkAll();
                });
			 });
			 
			/**跳转至还款页面**/
			function toRepayment(obj,url){
				if(url.substr(0,1)=="/"){
					url =webPath + url; 
				}else{
					url =webPath + "/" + url;
				}
			  	location.href = url;
				event.stopPropagation();
			}
			
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
					if(url.substr(0,1)=="/"){
						url =webPath + url; 
					}else{
						url =webPath + "/" + url;
					}
					top.LoadingAnimate.start();		
					window.location.href=url;
				}, 300);
			}

            var _checkAll= function(){
                $("input[type='checkbox']").each(function(i,n){
                    if ($(n).is(":checked")) {
                        $(n).prop('checked', false);
                    }else{
                        $(n).prop('checked', true);
                    }
                });
            };

			var printAll = function printAll(){
                var fincIds = "";
                $("#tablist #tab").find("input:checkbox:checked").each(function () {
                    fincIds += ","+$(this).val().split("=")[1];
                });
                if(fincIds == ""){
                    alert("请选择打印的用款",0);
                    return;
                }else{
                    fincIds = fincIds.substring(1);
                }
                var pageCntObj = new Object();
                pageCntObj.fincIds = fincIds;
                pageCntObj.fileName = "18091815333626910.xlsx";
                pageCntObj.saveBtn = "0";
                mfPageOffice.openPageOffice(pageCntObj);
            };
		</script>
	</head>
<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12">
				<div  class="btn-div">
					<div class="col-md-2">
					</div>
					<div class="col-md-8 text-center">
						<span class="top-title">用款申请单打印</span>
					</div>
				</div>
                <div class="search-div">
                    <button type="button" class="btn btn-primary pull-left" onclick="_checkAll();">全选</button>
                    <button type="button" class="btn btn-primary pull-left" style="margin-left: 10px" onclick="printAll();">打印</button>
                    <jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=客户名称/项目名称"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: calc(100% - 60px);">
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
</html>
