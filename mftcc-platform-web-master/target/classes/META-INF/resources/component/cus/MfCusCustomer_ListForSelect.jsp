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
		<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_applyInput.js"></script>
		<script type="text/javascript" src="${webPath}/component/app/js/MfBusApply_InputQuery.js"></script>
		<script type="text/javascript" >
		var cusType = '${cusType}';
		var cusBaseType = '${cusBaseType}';
		var selectType = '${selectType}';
			$(function(){
				LoadingAnimate.start();
				var table = "tablecus00001";
				if(2 == selectType){
					table = "tablecusPhone00001";
				}
                var url = webPath+"/mfCusCustomer/findByPageAjax";
				if(3 == selectType){//选择指定合同相关的客户
					url = webPath+"/mfCusCustomer/findByPactAjax";
					//不显示智能搜索
					$("#filter_btn_search").parents(".row").remove();
				}
				if(4 == selectType){
					url = webPath+"/mfCusCustomer/findByPageAjax?removeCusId="+cusType;
					cusType = null;
				}	
				if(5==selectType){
					url = webPath+"/mfCusCustomer/findByPageAjax?cusBaseType="+cusType;
                    cusType = null;
				}
                if(6==selectType){//仅仅选择企业和个人客户
                    url = webPath+"/mfCusCustomer/findPerAndCoreByPageAjax";
                }
                if(8==selectType){//仅仅选择企业和个人客户
                    url = webPath+  "/mfCusCustomer/findByBusPage?cusBaseType="+cusType;
                }
                if(12==selectType){//仅仅选择企业和个人客户(存在借据)
                    url = webPath+"/mfCusCustomer/findPerAndCoreHaveLoanByPageAjax";
                }
                if(7==selectType){//根据客户类型
                    url = webPath+"/mfCusCustomer/findByPageAjax?";
                }
			    myCustomScrollbar({
			    	obj:"#content",//页面内容绑定的id
			    	url:url,//列表数据查询的url
			    	tableId:table,//列表数据查询的table编号
			    	tableType:"thirdTableTag",//table所需解析标签的种类
			    	pageSize:30,//加载默认行数(不填为系统默认行数)
			    	data:{cusType:cusType},//指定参数
			    	callback:function(){
			    		LoadingAnimate.stop();
			    	}//方法执行完回调函数（取完数据做处理的时候）
			    });
			 });
        function choseCus(parm) {
            parm = parm.split("?")[1];
            var parmArray = parm.split("&");
            var cusNo = parmArray[0].split("=")[1];
            $.ajax({
                url: webPath + "/mfCusCustomer/getCusByIdAjax?cusNo=" + cusNo,
                dataType: "json",
                type: "POST",
                success: function (data) {
                    if (data.flag == "success") {
                        if (data.listCus == "1") {
                            dialog({
                                id: 'cusMoreDialog',
                                title: "重名客户",
                                url: webPath + "/mfCusCustomer/getCusListByName?cusNo=" + cusNo,
                                width: 500,
                                height: 360,
                                backdropOpacity: 0,
                                onshow: function () {
                                    this.returnValue = null;
                                },
                                onclose: function () {
                                    if (this.returnValue) {
                                        //返回对象的属性:实体类MfCusCustomer中的所有属性
                                        if (typeof(callback) == "function") {
                                            callback(this.returnValue);
                                        } else {
                                            getCusInfoArtDialog(this.returnValue);
                                        }
                                    }
                                    var cusNo1 = $("input[name=cusNo]",parent.document).val();
                                    if(cusNo1==cusNo){//确认
                                        choseCus1(cusNo);
                                    }
                                }
                            }).showModal();
                        } else {
                            parent.dialog.get('cusDialog').close(data.cusInfo).remove();
                        }
                    } else {
                        alert(top.getMessage("ERROR_SELECT"));
                    }
                }, error: function () {
                    alert(top.getMessage("ERROR_DATA_CREDIT", "客户"));
                }
            });
        }
        function choseCus1(parm) {
            var cusNo = parm;
            $.ajax({
                url: webPath + "/mfCusCustomer/getCusByIdAjax?cusNo=" + cusNo,
                dataType: "json",
                type: "POST",
                success: function (data) {
                    if (data.flag == "success") {
                        parent.dialog.get('cusDialog').close(data.cusInfo).remove();
                    } else {
                        alert(top.getMessage("ERROR_SELECT"));
                    }
                }, error: function () {
                    alert(top.getMessage("ERROR_DATA_CREDIT", "客户"));
                }
            });
        }
		</script>
	</head>
	<body>
	<div class="container">
		<div class="row clearfix bg-white tabCont">
			<div class="col-md-12 column">
				<div class="search-div col-xs-5 pull-right">
					<div class="znsearch-div">
						<div class="input-group pull-right">
							<i class="i i-fangdajing"></i>
							<input type="text" class="form-control" id="filter_in_input" placeholder="客户名称/证件号码">
							<span class="input-group-addon" id="filter_btn_search">搜索</span>
						</div>
					</div>
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
