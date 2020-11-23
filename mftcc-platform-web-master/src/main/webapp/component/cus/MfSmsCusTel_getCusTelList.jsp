<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<script type="text/javascript" >
		//var cusType = '${cusType}';
			$(function(){
			      myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/mfCusCustomer/findCusTelListAjax",//列表数据查询的url
			    	tableId:"tablecusTel0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	ownHeight:true,
			    	pageSize:30//加载默认行数(不填为系统默认行数)
			    });
			    
			     addCheckAllEvent();
			   // $("input[name='cusTel']").val(123);
			 });
		
			
		</script>
	</head>
	<body class="bodybg-gray" style="background: white;">
		
		<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
						<button type="button" class="btn btn-primary" id="batchRe_btn" onclick="bacthCusTes();">批量选择</button>
				</div>
				<!-- 我的筛选选中后的显示块 -->
				<div class="search-div" id="search-div">
						<!-- begin -->
							<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称/手机号"/>
						<!-- end -->
				</div>
			</div>
		</div>
		
		<div class="row clearfix">
			<div class="col-md-12 column">
				<!--页面显示区域-->
				 <div id="content" class="table_content cusTel_list"  style="height: auto;">
					</div> 
			</div>
		</div>
	<%-- 	 <div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxSave('#MfSmsCusDetailForm')"></dhcc:thirdButton>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	  --%>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	<script type="text/javascript">
		/*我的筛选加载的json*/
		filter_dic = [
	         {
				"optName" : "签约",
				"parm" : ${qianyueJsonArray},
				"optCode" : "loanFlag",
				"dicType" : "y_n"
			},  {
                  "optName":"借款日期",
                  "parm": [],
                  "optCode":"loanDate",
                  "dicType":"date"
              },{
                  "optName": "借款次数",
                  "parm": [],
                  "optCode":"loanCount",
                  "dicType":"num"
              },{
                  "optName": "借款失败",
                  "parm": ${laonfaileJsonArray},
                  "optCode":"loanFail",
                  "dicType":"y_n"
              }
          ];
          
	</script>
	
	<script type="text/javascript">
			function choseCus(parm){
				parm=parm.split("?")[1];
				var parmArray=parm.split("&");
				var cusNo = parmArray[0].split("=")[1];
				
				$.ajax({
					url:webPath+"/mfCusCustomer/getCusByIdAjax?cusNo="+cusNo,
					dataType:"json",
					type:"POST",
					success:function(data){
						if(data.flag == "success"){
							//alert(data.cusInfo.cusTel+"/"+data.cusInfo.cusNo)
							//parent.dialog.get('cusDialog').close(data.cusInfo);
							 // top.getTel(data.cusInfo);
							  top.cwBackData = data.cusInfo;
							
							//alert($("input[name=cusTel]").val());
							myclose_showDialogClick();
						}else{
							alert("获取客户信息失败");
						}
					},error:function(){
						alert("获取客户信息出错");
					}
				});
			};
			
			//去除表头 点击事件 换成 全选事件
			var isCheckAll = false;
			function addCheckAllEvent() {
			 	$(".table-float-head").delegate("th:first-child","click",function(){
					if (isCheckAll) {
						$(".cusTel_list").find(':checkbox').each(function() {
							this.checked = false;
						});
						isCheckAll = false;
					} else {
						$(".cusTel_list").find(':checkbox').each(function() {
							this.checked = true;
						});
						isCheckAll = true;
					}
				});
			}
			
			function bacthCusTes(){
				var tranceNos = getCheckedNos();
				// top.cwBackData = data.cusInfo;
				//alert(tranceNos);
				 top.cwBackData = tranceNos;
				//alert($("input[name=cusTel]").val());
				myclose_showDialogClick();
			}
			function getCheckedNos(){
				var nos = '';
	            var no = '';
	            var vals=0;
	            $(".cusTel_list").find(':checkbox').each(function() {
	            	if($(this).is(":checked")){
	            		var nametel = $(this).val();
	            		//no = $(this).val().substring(8);
	            		if(nametel != '' && nametel != null){
	            			var telarr =nametel.split("&");
	            			no = telarr[0].substring(8)+"["+telarr[1].substring(7)+"]"+telarr[2].substring(6);
		            		nos = nos + "|" + no;
		            		vals++;
	            		}
	            	}
	            });
	            if(vals==0){
	            	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的数据"), 1);
// 	            	alert("请选择需要操作的数据。", 1);
	            }else{
	            	nos = nos.substring(1);
	            }
	            return nos;
			}
			
	</script>
	
	
</html>
