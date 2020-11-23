<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<!-- <style type="text/css">
			.footer_loader{
				display: none;
			}
			.spfont{
				font-size: 22px !important;
			}
			.table-float-head{
				display: none;
			}
			.key-bott{
				padding-left: 15px;
			}
			.key-bos{
				padding-bottom: 18px;
			}
			.sptex-col{
				width:180px !important;
				display: inline-block;
			}
			.input-cont{
				/* display: block;  */
			    width: 30%; 
			    height: 34px; 
			    padding: 6px 12px; 
			    font-size: 14px; */
			    line-height: 1.42857143; 
			    background-image: none;
			    border: 1px solid #ccc;
			    border-radius: 4px;
			    -webkit-box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			    box-shadow: inset 0 1px 1px rgba(0,0,0,.075);
			}
			.month_step{
				padding-left: 220px;
				padding-top: 30px;
			}
			.sp-jitiamt{
				text-align: right;
				background-color: #FFFFFF;
   			    background-color: transparent;
    			border: 0px;
    			padding-right:10px;
			} 
			.contain-height{
				height:auto !important;
			}
			.div-amtcon{
				padding-left: 690px;
    		    padding-top: 25px; 
			}
			/* #content,#mCSB_1,.scroll-content{
				height:auto !important;
			} */
		</style> -->
		
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url:webPath+"/cwCusBankAccManage/findByPageAjax", //列表数据查询的url
				tableId : "tablebankacc0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30, //加载默认行数(不填为系统默认行数)
				ownHeight:true,
				callback:function(obj,data){
		    	}
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			     $(".bankNo").each(function(i, item) {
		    		var itemBankNo = $(item).text();
		    		var itemBankNoHtml = $(item).html();
		    		if(/\S{5}/.test(itemBankNo)){
		    			 $(item).html(itemBankNoHtml.replace(itemBankNo,itemBankNo.replace(/\s/g, '').replace(/(.{4})/g, "$1 ")));
		    		}
		    	}); 
			 });
			function getAddPage() {
				window.top.openBigForm(webPath+"/cwCusBankAccManage/inputPage","新增银行账户");
			}
			function updatePage(obj,url) {
				window.top.openBigForm(webPath+url,"修改银行账户");
			}
			function deleteBankAcc(obj,url) {
				window.top.alert("是否确认执行删除操作",2,function(){
					LoadingAnimate.start();
					$.ajax({
						url:url,
						type:'post',
						dataType:'json',
						success:function(data){
							LoadingAnimate.stop();
							if(data.success) {
								$(obj).parents("tr").remove();
								window.top.alert("操作成功！", 1);
							}else {
								window.top.alert("操作失败", 0);
							}
						},
						error:function(){
							LoadingAnimate.stop();
							window.top.alert("操作失败", 0);
						}
					});
				});
			}
		</script>
	</head>
	<body class="overflowHidden">
<body class="overflowHidden">
   	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float:left">
						<button id="addButton" type="button" class="btn btn-primary" onclick="getAddPage();">新增</button>
					</div>
				</div>
				<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=账户名称/开户行"/>
				</div>
			</div>	
		</div>
		<!--页面显示区域-->
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content review_list" style="height: auto;">
			</div>
			</div>
		</div>
    </div>
    <%@ include file="/component/include/PmsUserFilter.jsp"%>
</body>
<script type="text/javascript">
		/*我的筛选加载的json*/
		filter_dic = [
		    
          ];
          
	</script>
</html>
