<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
		<link rel="stylesheet" href="${webPath}/component/finance/voucher/css/ui.min.css" />
		<script type="text/javascript" >
			$(function(){
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:webPath+"/cwReviewBusiness/findByPageAjax",//列表数据查询的url
			    	tableId:"tablereview0001",//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	ownHeight:true,
			    	callback:function(options,data){
					    addEvent();
						var val ="1";
					    $(".filter-val").each(function(){
					    	var myfilter = eval($(this).find("input[type=hidden]").val());
							if(myfilter!=""){
								val = myfilter[0].value;
							}
						});
						var tit = "";
						if("0"==val){
							tit = "checkAll,customerName,cusTel,txCode,busShowNo,prdtNo,remarks,totalAmt,createDate,pzPrefix,buttonCol";
							$('#batchRe_btn').removeAttr('disabled').addClass('btn-primary');
						}else{
							tit = "customerName,cusTel,txCode,busShowNo,prdtNo,remarks,totalAmt,createDate,pzPrefix";
// 							$('#batchRe_btn').addClass('hidden');
							$('#batchRe_btn').attr('disabled', true).removeClass('btn-primary');
						}
						$(".search-title").find("span").attr("value", tit);
						showTable(false, '');
						
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			    addCheckAllEvent();
			    
			 });
			
			function addEvent(){
				//生成凭证
//				$('#tablist>tbody').off('click', '.createVoucher,.openLink,.deleteVoucher');
				$('.createVoucher').off('click').on('click', function(){
					var uid = $(this).data('id') + '';
					
					$.ajax({
						url:webPath+'/cwReviewBusiness/doCheckHaveRulesAjax',
						type:'post',
						data:{"traceNo":uid},
						async:false,
						dataType:'json',
						error:function(data){
							alert(top.getMessage(data.msg), 1);
						},
						success:function(data){
							if(data){
								if(data.flag=='success'){
									var param = JSON.stringify({'which': 'review', 'uid': uid});
									window.parent.openBigForm(encodeURI('${webPath}/cwVoucherMst/toVoucherAddSet?ajaxData='+param), '凭证新增',callBackFunction);
								}else{
									alert(data.msg, 0);
								}
							}
						}
					})
					
				});
				
				//凭证详情
				$('.openLink').off('click').on('click', function(){
					var voucherid = $(this).data('voucherid') + '';
					window.parent.openBigForm(encodeURI('${webPath}/cwVoucherMst/toVoucherEdit?which=review&voucherNo='+voucherid), '凭证详情',updateTableData);
				});
			}
			
			//去除表头 点击事件 换成 全选事件
			var isCheckAll = false;
			function addCheckAllEvent() {
			 	$(".table-float-head").delegate("th:first-child","click",function(){
					if (isCheckAll) {
						$(".review_list").find(':checkbox').each(function() {
							this.checked = false;
						});
						isCheckAll = false;
					} else {
						$(".review_list").find(':checkbox').each(function() {
							this.checked = true;
						});
						isCheckAll = true;
					}
				});
			}
			
			
			//弹窗查看详情
			function ajaxGetReviewInfo(obj, ajaxUrl){
				window.parent.openBigForm(webPath+ajaxUrl, '详情',callBackFunction);
			}
			function callBackFunction(){
				isCheckAll = false;
				updateTableData();
			}
			
			//获取选择的编号
			function getCheckedNos(){
				var nos = '';
	            var no = '';
	            var vals=0;
	            $(".review_list").find(':checkbox').each(function() {
	            	if($(this).is(":checked")){
	            		no = $(this).val().substring(8);
	            		if(no != '' && no != null){
		            		nos = nos + "," + no;
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
			
			function busBacthReview(){
				var tranceNos = getCheckedNos();
				if(tranceNos != ''){
					var url = '${webPath}/cwReviewBusiness/busBatchReviewAjax';
					var dataParam = '[{"name":"tranceNos","value":"'+tranceNos+'"}]'; 
					jQuery.ajax({
						url:url,
						data:{ajaxData:dataParam},
						type:"POST",
						dataType:"json",
						beforeSend:function(){  
						},success:function(data){
							if(data.flag == "success"){
// 								 alert(top.getMessage("SUCCEED_OPERATION"),1);
								alert(data.msg, 2);
								 callBackFunction();//重新加载列表数据
// 								 addCheckAllEvent();
							}else if(data.flag == "error"){
								 alert(data.msg,0);
							}
						},error:function(data){
							 alert(top.getMessage("FAILED_OPERATION"," "),0);
						}
					});
				}
			}
			
			
		</script>
	</head>
	<body class="overflowHidden">
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div">
					<button type="button" class="btn btn-primary" id="batchRe_btn" onclick="busBacthReview();">批量复核</button>
				</div>
				<div class="search-title hidden"><span value=""></span></div>
				<div class="search-div" id="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=3&placeholder=客户名称"/>
				</div>
			</div>
		</div>
		<div class="row clearfix">
			<div class="col-md-12 column">
				<div id="content" class="table_content review_list"  style="height: auto;">
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/component/include/PmsUserFilter.jsp"%>
	</body>	
	 <script type="text/javascript">
		  
		//默认执行复核的数据
		$(document).ready(function(){
			timer = window.setTimeout(function selectedFilter(changeFlag){
		          		if(changeFlag){
							window.clearTimeout(timer);//清除时间间隔的设置
						}
						if($("#my_filter_2")){
							$('#my_filter_2_a').trigger("click");
						   //$("#my_filter_1").removeClass("mySelectedNode");//去除全部
						  // $("#my_filter_2").addClass("mySelectedNode");
						    changeFlag = true ;
						}
					},200);
			

			})   
			    
			    
	</script> 
		<script type="text/javascript">
		/*我的筛选加载的json*/
        //  ${kindPro}
          filter_dic = [
	         {
				"optName" : "产品类型",
				"parm" : ${jsonKind},
				"optCode" : "prdtNo",
				"dicType" : "y_n"
			},{
                  "optName": "业务类型",
                  "parm": ${jsonProType},
                  "optCode":"txCode",
                  "dicType":"y_n"
              },  {
                  "optName":"日期",
                  "parm": [],
                  "optCode":"createDate",
                  "dicType":"date"
              }
          ];
          
          
	</script>
</html>
