<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
<style type="text/css">
#formtextarea{padding-left: 15px;padding-right: 15px}
#seardiv{padding-left: 5px;padding-top: 0px;}
#subButn{float: right;padding-right: 15px;}
#searchDiv{padding-top: 0px;width: 200px;float: left}
</style>
<title></title>
<script type="text/javascript" >
	$(function(){
	    myCustomScrollbar({
	    	obj:"#content",//页面内容绑定的id
	    	url:webPath+"/cwVoucherRemarks/findByPageAjax",//列表数据查询的url
	    	tableId:"tablevoucherremarks0001",//列表数据查询的table编号
	    	tableType:"thirdTableTag",//table所需解析标签的种类
	    	ownHeight:true,
	    	topHeight:140,
	    	pageSize:10,//加载默认行数(不填为系统默认行数)
	    	callback:function(){
				$('#content tbody tr').bind("dblclick",function(){//给每行绑定双击事件
					var remark = $(this).find("td:first").text();
					top.cwBackData = remark;//获取摘要返回父页面
					$(top.window.document).find("#showDialog .close").click();//关闭此页面
				});
				$('#content tbody tr').each(function(index,item){//给编辑按钮绑定单击事件
					$(item).find("td").eq(2).find('a').bind("click",function(){
						updateThis(item);
					})
				})
				
		    }//方法执行完回调函数
	    });
	 });
	 function resetTextarea(){//清空填写内容
	 	$('#formUid').val("");
	 	$("#voucherRemark").val("");
	 }
	 
	 function insertNew(id){//点击保存按钮之后的操作
	 	var url = $(id).attr("action");
	 	var dataParam = JSON.stringify($(id).serializeArray());
		LoadingAnimate.start();
		$.ajax({
			url:url,
			data : {
				ajaxData : dataParam
			},
			type : 'post',
			dataType : 'json',
			success : function(data) {
				LoadingAnimate.stop(); 
				if(data.flag=="success"){
					var remark = $("#voucherRemark").val();//获取摘要返回父页面
					top.cwBackData = remark;//获取摘要返回父页面
					$(top.window.document).find("#showDialog .close").click();//关闭此页面
				}else{
					window.top.alert(data.msg, 1);
				}
			}
		});
	 }
	 function updateThis(dom){
	 	$("#voucherRemark").val($(dom).find("td").eq(0).text());//表单文本域填充表格的摘要内容
	 	$("#formUid").val($(dom).find("[name=uid]").val())//给表单的隐藏域uid填充uid
	 }
</script>
	</head>
<body style="overflow-y: hidden;">
	<div class="container">
		<div class="row clearfix">
		<div id="formtextarea">
			<form id="insertUpdate" action="${webPath}/cwVoucherRemarks/ajaxSave">
			<input type="hidden" name="uid" id="formUid">
			  <div class="form-group">
			    <label for="name">请输入摘要内容：</label>
			    <textarea class="form-control" rows="2" name="voucherRemark" id="voucherRemark"></textarea>
			  </div>
			</form>
		</div>
		</div>
		<div class="row clearfix bg-white tabCont">
					<div class="znsearch-div" id="searchDiv">
						<div class="search-div" id="seardiv">
							<div class="input-group pull-left" >
								<i class="i i-fangdajing"></i>
								<input type="text" class="form-control" id="filter_in_input" placeholder="智能搜索">
								<span class="input-group-addon" id="filter_btn_search">搜索</span>
							</div>
						</div>
					</div>
					<div style="float: right">
						<div id="subButn">
						<button type="button" class="btn btn-primary" id="saveButn" onclick="insertNew('#insertUpdate')">保存</button>
						<button type="button" class="btn btn-default" onclick="resetTextarea()">取消</button>
						</div>
					</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content"  style="height: auto;"></div>
			</div>
		</div>
	</div>
</body>
</html>
