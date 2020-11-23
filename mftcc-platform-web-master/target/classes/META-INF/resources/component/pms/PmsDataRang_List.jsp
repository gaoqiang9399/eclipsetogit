<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="utf-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<%@ taglib uri="/WEB-INF/tld/loan.tld" prefix="dhcc" %>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />		
<script type="text/javascript" src="${webPath}/component/include/rightForm.js" ></script>
<script type="text/javascript">
	$(function() {
		showPageList();
	});
	var c ;
	//修改
	function func_getById(obj,url){
		top.pmsDataRang=false;
		window.parent.openBigForm(url,"数据定义配置",function(){
			if(top.pmsDataRang){
				showPageList();
			}
		});
		/* var tr = $(obj).parents("tr");
		$(tr).parents("table").find(".selected").removeClass("selected");
		$(tr).addClass("selected");
		c =	new RightForm(
			{actionUrl:url,formUrl:"PmsDataRangActionAjax_updateAjax.action",title:"数据定义配置"
			,btns:[{value:"提交",type:"button",onClick:"ajaxTrUpdate(this,reload())","data-elem":tr}],width:"400px"}
		); */
	}
	//新增
	function func_input(url){
		top.pmsDataRang=false;
		window.parent.openBigForm(webPath+"/pmsDataRang/input","数据定义配置",function(){
			if(top.pmsDataRang){
				showPageList();
			}
		});
		/* c =	new RightForm(
			{actionUrl:url,formUrl:"PmsDataRangActionAjax_insertAjax.action",title:"数据定义配置",btns:[{value:"保存",type:"button",onClick:"ajaxInsert(this.form)"}],width:"400px"}
		); */
	}
	function showPageList(){
		myCustomScrollbar({
			obj : "#content",//页面内容绑定的id
			url : webPath+"/pmsDataRang/findByPageAjax",//列表数据查询的url
			tableId : "tablepms0001",//列表数据查询的table编号
			tableType : "thirdTableTag",//table所需解析标签的种类
			myFilter : true
		//是否有我的筛选
		});
	};
	function reload(){
		c.close();
	}
	function ajaxInsert(obj){
		var flag = submitJsMethod(obj, '');
		if(flag){
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray()); 
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
						  //$.myAlert.Alert(top.getMessage("SUCCEED_OPERATION"));
						  alert(top.getMessage("SUCCEED_OPERATION"),1)
						  reload();
						  window.location.reload();
					}
				},error:function(data){
					 //$.myAlert.Alert(top.getMessage("FAILED_OPERATION"," "));
					 alert(top.getMessage("FAILED_OPERATION"," "),0)
				}
			});
		}
	}
	//刷新缓存
	function refreshDataRang(){
		LoadingAnimate.start();
		jQuery.ajax({
			url:webPath+"/pmsDataRang/refreshDataRangAjax",
			type:"POST",
			dataType:"json",
			beforeSend:function(){  
			},success:function(data){
				LoadingAnimate.stop();
				if(data.flag == "success"){
					  alert(data.msg,1);
				}
			},error:function(data){
				 alert(top.getMessage("FAILED_REFRESH"," "),0)
			}
		});
	}
	function dataRangConf(obj,url){
		var title = url.substring(url.indexOf("funDisc=")+8,url.length);
		window.top.openBigForm(url,title,"");
	}
	</script>
	<style type="text/css">
		.table_content .ls_list tbody tr.selected{
			background-color:#e0e0e0;
		}
	</style>
	</head>
<body style="overflow-y: hidden;">
	<dhcc:markPoint markPointName="PmsDataRang_List"/>
		<div class="container">
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="search-div">
						<div class="col-xs-9 column">
							<button type="button" class="btn btn-primary pull-left" onclick="func_input('<%=webPath %>/pmsDataRang/inputAjax');">新增</button>
							<ol class="breadcrumb pull-left">
								<li><a href="${webPath}/sysDevView/setC?setType=developer">开发者设置</a></li>
								<li class="active">数据权限定义</li>
							</ol>
							<div style="margin-left: 96%;">
								<button type="button" class="btn btn-primary pull-left" onclick="refreshDataRang();">
								<i class="fa fa-refresh refresh-ctrl" title="刷新"></i>缓存
								</button>
							</div>
						</div>
							<jsp:include page="/component/include/mySearch.jsp?blockType=2&placeholder=业务功能编号/业务功能描述"/>
					</div>
				</div>
			</div>
			<!--页面显示区域-->
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
</html>