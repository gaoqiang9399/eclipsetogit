<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
	contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>新增</title>
<link rel="stylesheet" href="${webPath}/themes/factor/css/search_filter.css" />
<link rel="stylesheet" href="${webPath}/UIplug/select3/css/select3.css" />
<link rel="stylesheet" href='${webPath}/component/pss/css/Pss.css'/>
<script type="text/javascript" src="${webPath}/themes/factor/js/search_filter.js"></script>
<script type="text/javascript" src="${webPath}/UIplug/select3/js/select3-full.js"></script>
<script type="text/javascript" src="${webPath}/component/pss/js/Pss.js"></script>
<style type="text/css">
	.pss-bigform-form > div {
		margin-top: 10px;
		width: 75%;
	}
</style>
<script type="text/javascript">
	var ajaxData = '${ajaxData}';
    ajaxData =JSON.parse(ajaxData);
	var storehouseId = "${storehouseId}";
	
	$(function() {
		myCustomScrollbar({
	    	obj : "#content",//页面内容绑定的id
	    	url : webPath+"/pssFreightSpace/findByPageAjax",//列表数据查询的url
	    	tableId : "tabledl_pssfreight_space01",//列表数据查询的table编号
	    	tableType : "thirdTableTag",//table所需解析标签的种类
	    	pageSize : 999999,//加载默认行数(不填为系统默认行数)
	    	data : {"storehouseId" : storehouseId},
	    	callback : function(options, data) {
	    		//行操作事件
	    		Pss.addRowOperateEvent("sequence");
	    		//使用状态事件
	    		addEnabledStatusEvent("enabledStatusName", "enabledStatus");
	    		//字符串列事件
	    		var stringThs = new Array("freightSpaceNo", "freightSpaceName", "memo");
	    		Pss.addStringEvent(stringThs);
	    		
	    		var tabHeadThs = new Array("freightSpaceNo", "freightSpaceName", "enabledStatusName");
				Pss.pssAddTabMustEnter(tabHeadThs, "#content");
	    	}
	    });
	    
	    $('.footer_loader').remove();
	    $('.table-float-head').remove();
	    
	    $('.pss_detail_list').css('height', 'auto');
	    $('#mCSB_1').css('height', 'auto');
	    
		$("#content .mCSB_container").css("overflow-x", "auto");
	    	
		myCustomScrollbarForForm({
			obj : ".scroll-content",
			advanced : {
				updateOnContentResize : true
			}
		});
	});
	
	//获取table分录数据数组
	function getTableData() {
		var pssFreightSpaces = new Array();
		$("#tablist>tbody tr").each(function(trIndex, tr) {
			var pssFreightSpace = new Object();
			$("#tablist>thead th").each(function(thIndex, th) {
				var $td = $(tr).children("td:eq(" + thIndex + ")");
				var inputValue = $td.children("input[name='" + $(th).attr("name") + "']").val();
				var popsValue = $td.children(".pops-value").text();
				if (typeof(inputValue) != "undefined" && inputValue.length > 0) {
					pssFreightSpace[$(th).attr("name")] = inputValue;
				} else if (typeof(popsValue) != "undefined" && popsValue.length > 0) {
					pssFreightSpace[$(th).attr("name")] = popsValue;
				} else {
					var tdText = $td.text();
					pssFreightSpace[$(th).attr("name")] = tdText;
				}
			});
			pssFreightSpaces.push(pssFreightSpace);
		});
		return pssFreightSpaces;
	}
	
	//验证提交数据
	function validateSubmitData(pssFreightSpaces) {
		if (pssFreightSpaces != null && pssFreightSpaces.length > 0) {
			for (var index in pssFreightSpaces) {
				if (pssFreightSpaces[index].freightSpaceNo != null && pssFreightSpaces[index].freightSpaceNo.length > 0) {
					if (pssFreightSpaces[index].freightSpaceName == null || pssFreightSpaces[index].freightSpaceName.length == 0) {
						DIALOG.tip("请输入对应的仓位名称", 3000);
						return false;
					}
					if (pssFreightSpaces[index].enabledStatus == null || pssFreightSpaces[index].enabledStatus.length == 0) {
						DIALOG.tip(top.getMessage("FIRST_SELECT_FIELD", "相应的使用状态"), 3000);
						return false;
					}
				}
				if (pssFreightSpaces[index].freightSpaceName != null && pssFreightSpaces[index].freightSpaceName.length > 0) {
					if (pssFreightSpaces[index].freightSpaceNo == null || pssFreightSpaces[index].freightSpaceNo.length == 0) {
						DIALOG.tip("请输入对应的仓位编码", 3000);
						return false;
					}
				}
			}
		}
		
		return true;
	}
	
	function addEnabledStatusEvent(enabledStatusNameTh, enabledStatusTh) {
		var enabledStatusNameThIndex = $("#tablist>thead th[name='" + enabledStatusNameTh + "']").index();
		var enabledStatusThIndex = $("#tablist>thead th[name='" + enabledStatusTh + "']").index();
		var trs = $("#tablist>tbody tr");
		$(trs).each(function(index, tr) {
			var $enabledStatusNameTd = $(tr).children("td:eq(" + enabledStatusNameThIndex + ")");
			var $input = $("<input style='display:none;' id='enabledStatusInput" + index + "'></input>");
			$enabledStatusNameTd.append($input);
			
			var $enabledStatusTd = $(tr).children("td:eq(" + enabledStatusThIndex + ")");
			var enabledStatus = $enabledStatusTd.children("input[name='" + enabledStatusTh + "']").val();
			if (enabledStatus.length > 0) {
				$input.val(enabledStatus);
			}
		});
		
		$(trs).each(function(index, tr) {
			$(tr).children("td:eq(" + enabledStatusNameThIndex + ")").on('click', function() {
				var $td = $(this);
				var $enabledStatusTd = $(this).parent("tr").children("td:eq(" + enabledStatusThIndex + ")");
				var inputLength = $(this).children("input").length;
				if (inputLength == 0) {
					var $input = $("<input style='display:none;' id='enabledStatusInput" + index + "'></input>");
					$(this).append($input);
				}
				var divLength = $(this).children("div .pops-select").length;
				if (divLength == 0) {
					$(this).children("input").popupSelection({
						searchOn:false, //启用搜索
						inline:true, //下拉模式
						multiple:false, //多选
						items:ajaxData.enabledStatus,
						changeCallback : function (obj, elem) {
							$enabledStatusTd.children("input[name='" + enabledStatusTh + "']").val(obj.val());
							Pss.popsSelectSelected($td);
						}
					});
					Pss.addPopsSelectClick($td);
				}
			}).click();
		});
	};
	
	function insertStorehouse(obj) {
		var flag = submitJsMethod($(obj).get(0), '');
		if (flag) {
			var url = $(obj).attr("action");
			var dataParam = JSON.stringify($(obj).serializeArray());
			LoadingAnimate.start();
			var pssFreightSpaces = getTableData();
			if(!validateSubmitData(pssFreightSpaces)) {
				LoadingAnimate.stop();
				return false;
			}
		
			jQuery.ajax({
				url : url,
				data : {
					ajaxData : dataParam,
					pssFreightSpacesJson : JSON.stringify(pssFreightSpaces)
				},
				type : "POST",
				dataType : "json",
				beforeSend : function() {
				},
				success : function(data) {
					LoadingAnimate.stop();
					if (data.flag == "success") {
						alert(top.getMessage("SUCCEED_OPERATION"), 1);
						top.addFlag = true;
						top.storehouseId = $("input[name=storehouseId]").val();
						top.storehouseName = $("input[name=storehouseName]")
								.val();
						if (data.htmlStrFlag == "1") {
							top.htmlStrFlag = true;
							top.htmlString = data.htmlStr;
							top.updateFlag = true;
							top.tableName = "ins_info";
						}
						myclose_click();
					} else if (data.flag == "error") {
						alert(data.msg, 0);
					}
				},
				error : function(data) {
					LoadingAnimate.stop();
					alert(top.getMessage("FAILED_OPERATION", " "), 0);
				}
			});
		}
	}
	
	function pageClose() {
		myclose_click();
	}

	function setSysOrgInfo(sysOrg) {
		$("input[name=brName]").val(sysOrg.brName);
		$("input[name=brNo]").val(sysOrg.brNo);
	}
</script>
</head>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">
			<div class="row clearfix bg-white tabCont">
				<div class="pss-bigform-form">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="pssStorehouse" theme="simple"
							name="operform" action="${webPath}/pssStorehouse/insertAjax">
							<dhcc:bootstarpTag property="formpssstorehouse0002" mode="query" />
						</form>
						<div id="content" class="table_content pss_detail_list">
						</div>
					</div>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<dhcc:pmsTag pmsId="pss-storehouse-insert">
				<dhcc:thirdButton value="保存" action="保存"
					onclick="insertStorehouse('#pssStorehouse');"></dhcc:thirdButton>
			</dhcc:pmsTag>
			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
				onclick="pageClose();"></dhcc:thirdButton>
		</div>
	</div>
</body>
</html>
