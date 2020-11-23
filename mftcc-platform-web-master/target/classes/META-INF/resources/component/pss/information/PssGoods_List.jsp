<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/rcswitcher-master/js/rcswitcher.min.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/rcswitcher-master/css/rcswitcher.min.css" type="text/css" />
<script type="text/javascript" src="${webPath}/component/include/myRcswitcher.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/ViewImg/viewer.js"></script>
<link rel="stylesheet" href="${webPath}/UIplug/ViewImg/viewer.css" type="text/css">
<script type="text/javascript">
	$(function(){
		//加载列表
		myCustomScrollbar({
			obj:"#content",//页面内容绑定的id
			url:webPath+"/pssGoods/getListPageAjax",//列表数据查询的url
			tableId:"tablepssgoods0001",//列表数据查询的table编号
			tableType:"thirdTableTag",//table所需解析标签的种类
			pageSize:12,//加载默认行数(不填为系统默认行数)
			topHeight:100,//顶部区域的高度，用于计算列表区域高度。
			callback:function(){
			    		$("table").tableRcswitcher({
			    		name:"flag",onText:"启用",offText:"停用"});
			    		$('#tab tr').each(function (){
						var docNo = $(this).children('td').eq(0).children('input').val();
						var docBizNo = $(this).children('td').eq(1).children('input').val();
						if(docNo==""||docBizNo==""){
							$(this).children('td').eq(3).append("<img src=/factor/component/pss/css/image.png>");
						}else{
							$(this).children('td').eq(3).append("<img src=DocUploadAction_viewCompressImage.action?docNo="+docNo+"&amp;docBizNo="+docBizNo+">");
						}
				});
			}//方法执行完回调函数（取完数据做处理的时候）
		});
	    addCheckAllEvent();
	    
	    $('.pss-date').on('click', function(){
			fPopUpCalendarDlg({
				isclear: true,
				/* min: currDate.substring(0, 8) + '01 00:00:00', //最小日期
				max: currDate + ' 23:59:59', //最大日期 */
				choose:function(data){
				}	
			});
		});
		
	    $('#pssImpATB').hover(function(){
	    	$('#pssImpATB').find('button:eq(0) span').addClass('triangle-up');
	    	$('#pssImpATB').find('button:eq(0) span').removeClass('triangle-down');
	    	$('#pssHideImpATB').addClass('pss-imp-left').addClass('pss_block');
	    },function(){
	    	$('#pssImpATB').find('button:eq(0) span').addClass('triangle-down');
	    	$('#pssImpATB').find('button:eq(0) span').removeClass('triangle-up');
	    	$('#pssHideImpATB').removeClass('pss-imp-left').removeClass('pss_block');
	    });
	    
	    $('#pssChkATB').hover(function(){
	    	$('#pssChkATB').find('button:eq(0) span').addClass('triangle-up');
	    	$('#pssChkATB').find('button:eq(0) span').removeClass('triangle-down');
	    	$('#pssHideChkATB').addClass('pss-chk-left').addClass('pss_block');
	    },function(){
	    	$('#pssChkATB').find('button:eq(0) span').addClass('triangle-down');
	    	$('#pssChkATB').find('button:eq(0) span').removeClass('triangle-up');
	    	$('#pssHideChkATB').removeClass('pss-chk-left').removeClass('pss_block');
	    });
	});
		
	//批量删除
	function deleteBatch(){
		var goodsIds = getCheckedATIds();
		if(goodsIds != ''){
			var url = webPath+'/pssGoods/deleteBatchAjax';
			var dataParam = '[{"name":"goodsIds","value":"'+goodsIds+'"}]'; 
			jQuery.ajax({
				url:url,
				data:{ajaxData:dataParam},
				type:"POST",
				dataType:"json",
				beforeSend:function(){  
				},success:function(data){
					if(data.flag == "success"){
							alert(top.getMessage("SUCCEED_OPERATION") ,1);
						}else if(data.flag == "error"){
						 alert(data.msg,0);
					}
					updateTableData();//重新加载列表数据
				},error:function(data){
					 alert(top.getMessage("FAILED_OPERATION"," "),0);
				}
			});
		}
	};
		
	//去除表头 点击事件 换成 全选事件
	var isCheckAll = false;
	function addCheckAllEvent() {
		 $(".table-float-head").delegate("th:first-child","click",function(){
			if (isCheckAll) {
				$(".table_content").find(':checkbox').each(function() {
					this.checked = false;
				});
				isCheckAll = false;
			} else {
				$(".table_content").find(':checkbox').each(function() {
					this.checked = true;
				});
				isCheckAll = true;
			}
		});
	}
	
	//获取凭证选择的凭证编号
	function getCheckedATIds(){
		var goodsIds = '';
        var goodsId = '';
        var vals=0;
        $(".table_content").find(':checkbox').each(function() {
        	if($(this).is(":checked")){
        		goodsId = $(this).val().substring(8);
        		if(goodsId != '' && goodsId != null){
            		goodsIds = goodsIds + "," + goodsId;
            		vals++;
        		}
        	}
        });
        if(vals==0){
        	alert(top.getMessage("FIRST_SELECT_FIELD","需要操作的商品"), 1);
        }else{
        	goodsIds = goodsIds.substring(1);
        }
        return goodsIds;
	};
	function addGoods(){
		//window.location = webPath+'/PssGoodsAction_getInputPage.action';
		top.openBigForm(webPath+"/pssGoods/getInputPage","新增",updateTableData);
	};
	function updateGoods(url){
		top.openBigForm(url,"详情",updateTableData);			
	};
</script>
</head>
<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="btn-div column">
					<div class="show-btn" style="float: left">
						<dhcc:pmsTag pmsId="pss-goods-insert">
							<button type="button" class="btn btn-primary"
								onclick="addGoods();">新增</button>
						</dhcc:pmsTag>
						<dhcc:pmsTag pmsId="pss-goods-delete">
							<button type="button" class="btn btn-default"
								onclick="deleteBatch();">删除</button>
						</dhcc:pmsTag>
					</div>
				</div>
				<!-- 我的筛选选中后的显示块 -->
			<div class="search-div">
					<jsp:include page="/component/include/mySearch.jsp?blockType=4&placeholder=商品编号/商品名称"/>
				</div>
			</div> 
		</div>
		<!--页面显示区域-->
		<div id="content" class="table_content" style="height: auto;"></div>
	</div>
</body>
</html>