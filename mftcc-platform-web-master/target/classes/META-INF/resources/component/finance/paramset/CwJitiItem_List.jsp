<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<style type="text/css">
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
			#content,#mCSB_1,.scroll-content{
				height:auto !important;
			}
		</style>
		
		<script type="text/javascript" >
			var jtId = '${jtId}';
			$(function(){
			    myCustomScrollbar({
				obj : "#content", //页面内容绑定的id
				url:webPath+"/cwJitiItem/findByPageAjax?jtId="+jtId, //列表数据查询的url
				tableId : "tablecwjitiitem0001", //列表数据查询的table编号
				tableType : "thirdTableTag", //table所需解析标签的种类
				pageSize:30, //加载默认行数(不填为系统默认行数)
				callback:function(obj,data){
					$("#jitiamt").val(data.jtItemJtBal);
					$("#jieAccNo").val(data.jKemu);
					$("#daiAccNo").val(data.dKemu);
		    	}
				//,topHeight : 50 //顶部区域的高度，用于计算列表区域高度。
			    });
			 });
		</script>
	</head>
	<body class="overflowHidden">
		<div id="month_step_2" class="month_step">
					<div class="row vch_item">
						<div  class="col-md-12 key-bott key-bos" >
								<span class="txt sptex-col">贷款减值损失准备(损益类)：</span>
								<input type="text" class="input-cont" id="jieAccNo" name="accno1" readonly="readonly">
						</div>
					</div>
					<div class="row vch_item">
						<div  class="col-md-12 key-bott" >
							<span class="txt sptex-col">贷款损失准备(资产类)：</span>
							<input type="text" class="input-cont"  id="daiAccNo" name="acc_no_2"  readonly="readonly">
						</div>
						
					</div>
		</div>
		<div class="container contain-height " style="padding-top: 30px">
			<div class="scroll-content"">
				<div class="col-md-8 col-md-offset-2 margin_top_0">
					<div id="content" class="table_content" style="height: auto;">
					</div>
				</div>
			</div>
		</div>
		<div class="div-amtcon"><span class="sp-jitizi">本次提取金额：</span>
				<input type="text" class="sp-jitiamt"  id="jitiamt" value="" name="acc_no_4" disabled="disabled">元
		</div>
		<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<%-- <dhcc:thirdButton value="计提" action="计提" onclick="jitiSaveData()"></dhcc:thirdButton> --%>
	   			<%-- <dhcc:thirdButton value="取消" action="取消" typeclass="myclose" onclick="myclose();"></dhcc:thirdButton> --%>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   	</div>	
	</body>
	<script type="text/javascript">
		function myclose(){
			$(parent.document).find("#showDialog .close").click();
		}
	</script>
</html>
