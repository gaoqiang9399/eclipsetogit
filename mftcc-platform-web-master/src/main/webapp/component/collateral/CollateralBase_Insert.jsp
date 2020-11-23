<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_Insert.js"></script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/Collateral_common.js"></script>
		<script type="text/javascript" src='${webPath}/component/cus/identitycheck/js/IdentityCheck.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/CollateralPersonInsert.js"></script>
		<script type="text/javascript" src='${webPath}/component/cus/js/MfCusCustomer_InputUpdate.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript" src='${webPath}/component/auth/js/creditTool.js?v=${cssJsVersion}'> </script>
		<script type="text/javascript" src="${webPath}/component/collateral/js/realEstateAssessment.js"></script>
		<script type="text/javascript"  src='${webPath}/component/cus/js/assetsEvalCommon.js?v=${cssJsVersion}'> </script>

	</head>
	
	<script type="text/javascript">
		var ajaxData = '${ajaxData}';
	    ajaxData = JSON.parse(ajaxData);
	    var appId='${appId}';
	    var entrance='${entrance}';
	    //var entrance = "business";
		var formId='${formId}';
		var entrFlag='${entrFlag}';
		var cusNo='${cusNo}';
		var cusType='${dataMap.cusType}';
		var busModel='${busModel}';
		var classId='${classId}';
		var collateralType = '${collateralType}';
		if(collateralType=='' || collateralType==null || collateralType=='null'){
			collateralType='pledge';
		}
		var vouType = '${vouType}';
		var vouTypeShort = '${vouTypeShort}';
		var classModel = '${classModel}';
		var corpCusTypeStr='${dataMap.corpCusTypeStr}';
		var assureAmtCusTypeStr='${dataMap.assureAmtCusTypeStr}';
	    var perCusTypeStr='${dataMap.perCusTypeStr}';
		var corpIdTypeStr='${dataMap.corpIdTypeStr}';
	    var perIdTypeStr='${dataMap.perIdTypeStr}';
	  	//是否支持跳过标识0不支持1支持
	    var supportSkipFlag ='${supportSkipFlag}';
		var isQuote="0";
	    var extensionApplyId ='${extensionApplyId}';
	    var nodeNo = '${nodeNo}';
	    var changeCusRelFlag = '${dataMap.changeCusRelFlag}';
	    var busEndDate = '${busEndDate}';
		var appAmt = '${appAmt}';
		var companyId = '${dataMap.companyId}';
		var classFirstNo = '${classFirstNo}';
		var projectName = '${projectName}';
        var kindNo = '${kindNo}';
		var alliance = '${alliance}';
		var evalState;//估值状态
		var evalTaskNum;//估值任务编号
		var title;
		$(function() {
			CollateralInsert.init();
            CollateralCommon.init()
            title = $(top.window.document).find("#myModalLabel").text();
		if(alliance==1){
            var obj = $("select[name='assureType']");
            CollateralCommon.assureTypeChangeEvent(obj);
		}

		});
		function selectAssureList(){
            CollateralCommon.selectAssureList();
		};
        function selectAssureList_ht(){
            CollateralPersonInsert.selectAssurePersonList();
        };

        /**
         * 押品期限文本框失焦时设置截止时间
         */
         function collateralTermOnBlur(obj){

            var creditTerm = Number(obj.value);
            var beginDate = $("input[name=pleValueDate]").val();
            var pleExpiryDate = "";
            if(creditTerm != "" && beginDate != ""){
                pleExpiryDate = creditHandleUtil.getAddMonthRes(beginDate,creditTerm,"m");
            }else{
                $("input[name=pleExpiryDate]").val("");
            }
            $("input[name=pleExpiryDate]").val(pleExpiryDate);
        };

        /**
         * 开始时间文本框失焦时设置截止时间
         */
         function collateralbeginDateOnBlur(){

            var creditTerm = Number($("input[name=extOstr02]").val());
            var beginDate = $("input[name=pleValueDate]").val();
            if(beginDate != ""){
                if(creditTerm != ""){
                    $("input[name=pleExpiryDate]").val(creditHandleUtil.getAddMonthRes(beginDate,creditTerm,"m"));
                }else{
                    $("input[name=pleExpiryDate]").val(pleExpiryDate);
                }
            }
        };


	</script>
	<script type="text/javascript">
			//根据身份证信息填写性别，生日，年龄
			var func_using_IDcard_to_set_sex_birthday=function(idNode){				
				var idType = $("select[name=idType]").val();				
				var id = $(idNode).val();				
				if(idType == "0" && 18 == id.length ){				
					$("select[name=sex]").val("");					
					StringUtil.setBirthyAndSexByID(idNode, 'sex', 'brithday','age');							
				}
			}
			//首次抵押余额 小于 等于 抵押物估值 在首次抵押余额判断
			var judgeOfValue = function(firstPle){							
				var pleOriginalValueStr = $("input[name=pleOriginalValue]")[0];//抵押物估值
			    if(pleOriginalValueStr.value == null || pleOriginalValueStr.value == "" || pleOriginalValueStr.value == "0.00"){
			    	$("input[name=extNum03]").val("");
					window.top.alert("请先填写抵押物估值！", 0);
				} 			
				var	pleOriginalValue = parseFloat(pleOriginalValueStr.value.replace(/,/g,""));//替换掉逗号 并转换成float类型
								
				var extNumValueStr = $("input[name=extNum03]")[0];//首次抵押余额
				var extNumValue = parseFloat(extNumValueStr.value.replace(/,/g,""));
				
				var flag = false;
				if(pleOriginalValue != "" && extNumValue != ""){
					flag = pleOriginalValue < extNumValue?true:false;
				}
				if(flag){
					window.top.alert("首次抵押余额不能大于抵押物估值！", 0);
					//$("input[name=pleOriginalValue]").val("");
					$("input[name=extNum03]").val("");
					flag=false;
				}				
			}
			//房屋坐落修改为抵押物地址，选择区域后填详细地址
			var selectAreaCallBack = function(areaInfo){
				$("input[name=pleAddress]").val(areaInfo.disName);
			};
	</script>
		<script type="text/javascript">
			Date.prototype.format = function(format) {
	      	 var date = {
	              "M+": this.getMonth() + 1,
	              "d+": this.getDate(),
	              "h+": this.getHours(),
	              "m+": this.getMinutes(),
	              "s+": this.getSeconds(),
	              "q+": Math.floor((this.getMonth() + 3) / 3),
	              "S+": this.getMilliseconds()
	       };
	       if (/(y+)/i.test(format)) {
	              format = format.replace(RegExp.$1, (this.getFullYear() + '').substr(4 - RegExp.$1.length));
	       }
	       for (var k in date) {
	              if (new RegExp("(" + k + ")").test(format)) {
	                     format = format.replace(RegExp.$1, RegExp.$1.length == 1
	                            ? date[k] : ("00" + date[k]).substr(("" + date[k]).length));
	              }
	       }
	       return format;
	}
	</script>
	<script type="text/javascript">
		//网智天元
		//根据上牌日期和当前日期，自动带出车辆使用月份
		var getMonth=function(){
			var pleProduceDate = $("input[name=pleProduceDate]").val();
			console.log("上牌日期"+pleProduceDate)
			var pleProduceDate = pleProduceDate.replace(/-/g,"");
			var newDate = new Date();    
 			var currDate = newDate.format('yyyyMMdd');
 			var m = getMonthNumber(pleProduceDate, currDate);
 			if($("input[name=pleProduceDate]").val() == null || $("input[name=pleProduceDate]").val() == ""){
 				$("input[name=extInt02]").val("");
 			}else{
 				$("input[name=extInt02]").val(m);
 			}
		}
	</script>
	<script type="text/javascript">
		 function getMonthNumber(date1, date2) {
            //默认格式为"20030303",根据自己需要改格式和方法
            var year1 = date1.substr(0, 4);
            var year2 = date2.substr(0, 4);
            var month1 = date1.substr(4, 2);
            var month2 = date2.substr(4, 2);
            var len = (year2 - year1) * 12 + (month2 - month1);

            var day = date2.substr(6, 2) - date1.substr(6, 2);
            if (day > 0) {
                len += 1;
            } 
           /*  else if (day < 0) 
            {
                len -= 1;
            } */
            return len;
        }
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20" id="pledgeBaseInfoDiv">
					<div class="bootstarpTag fourColumn">
					<c:choose>
						<c:when test="${vouTypeShort=='1'}">
							<div class="text-center">主反担保方式为${vouTypeName}担保，不需要添加反担保信息</div>
						</c:when>
						<c:otherwise>
						 <div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						  <form  method="post" id="pledgeBaseInfoInsert" theme="simple" name="operform" action="/mfBusCollateralRel/insertCollateralAjax">
							 <dhcc:bootstarpTag property="formdlpledgebaseinfo0004" mode="query"/>
						  </form>
						  <input type="hidden" name = "isQuote"/>
						  <input type="hidden" name = "entrFlag"/>
						</c:otherwise>
					</c:choose>
					
				    </div>
				<!-- 授信中和担保信息新增押品信息，不需要上传要件 -->
				<c:if test="${entrFlag!='credit' && entrFlag!='collateral'}">
					<%@ include file="/component/include/biz_node_plugins.jsp"%>
				</c:if>
			</div>

				<div class="col-md-10 col-md-offset-1 column margin_top_20" style="display: none" id="houseEvalDiv">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post"  theme="simple" id="houseEvalForm" name="operform" action="${webPath}/mfHouseEval/evalHouseInfo">
							<dhcc:bootstarpTag property="formHouseEval" mode="query"/>
						</form>
					</div>
				</div>

				<div class="col-md-8 col-md-offset-2 column margin_top_20" style="display: none" id="houseEvalRDiv">
					<div class="bootstarpTag fourColumn">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post"  theme="simple" id="houseEvalFormMan" name="operform" action="${webPath}/mfHouseEval/evalHouseInfo">
							<dhcc:bootstarpTag property="formHouseEvalMan" mode="query"/>
						</form>
					</div>
				</div>



		 </div>
			<div class="formRowCenter" id="normal" style="display:block">
				<%--<c:if test='${vouTypeShort!="1"}'>--%>
	   				<dhcc:thirdButton value="保存" action="保存" onclick="CollateralInsert.insertCollateralBase('#pledgeBaseInfoInsert','0');"></dhcc:thirdButton>
				<%--</c:if>--%>
				<c:if test='${supportSkipFlag == "1"}'>
					<%-- 报后变更允许跳过 --%>
					<dhcc:thirdButton value="跳过" action="跳过" typeclass="skipButton" onclick="CollateralInsert.insertCollateralBase('#pledgeBaseInfoInsert','1');"></dhcc:thirdButton>
				</c:if>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>


			<div class="formRowCenter" style="display: none" id="saveBtnHouseEval">
				<dhcc:thirdButton value="在线评估" action="在线评估" typeclass="insertAjax" onclick="CollateralInsert.ajaxSaveHouseEval('#houseEvalForm','1');"></dhcc:thirdButton>
				<dhcc:thirdButton value="跳转人工评估" action="跳转人工评估" typeclass="insertAjax" onclick="CollateralInsert.changeRengong();"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="CollateralInsert.changeFormDisplay();"></dhcc:thirdButton>
			</div>

			<div class="formRowCenter" style="display: none" id="saveBtnRHouseEval">
				<dhcc:thirdButton value="人工评估" action="人工评估" typeclass="insertAjax" onclick="CollateralInsert.ajaxSaveHouseEval('#houseEvalFormMan','2');"></dhcc:thirdButton>
				<dhcc:thirdButton value="返回" action="返回" typeclass="cancel" onclick="CollateralInsert.changeFormDisplay();"></dhcc:thirdButton>
			</div>


   		</div>
	</body>
	<script type="text/javascript">
        $(function() {
            //进件环节客户不允许选择客户
            if(entrance !="collateral"){
                $("input[name=cusName]").removeAttr("onclick");
            }

        });
	</script>
</html>
