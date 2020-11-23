<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		
	</head>
	<script type="text/javascript">

		var cusNo = '${cusNo}';
		var appId = '${appId}';
		var collateralType = '${collateralType}';
		$(function() {
// 			$(".scroll-content").mCustomScrollbar({
// 				advanced : {
// 					updateOnContentResize : true
// 				}
// 			});
             $("input[name=pledgeName]").after('<span class="input-group-addon">'+
                '<i class="i i-fangdajing pointer" onclick="_selectCollateralData(_setCollateralData);"></i></span>');
            myCustomScrollbarForForm({
				obj:".scroll-content",
				advanced : {
					updateOnContentResize : true
				}
			});
			var groupNameLabel = $("input[name=groupName]").parents(".rows")
					.find(".form-label");
			var groupNameLabelText = $(groupNameLabel).text();
			$(groupNameLabel).empty().append(
					"<span class='required'>*</span>" + groupNameLabelText);
			$("input[name=groupName]").attr("mustinput", "1");
		});
		
		function getLegalIdType() {
			var legalIdType = $("select[name =legalIdType]").val();
			if (legalIdType == "0") {
				$("input[name=legalIdNum]").attr("alt", "idnum");
			} else {
				$("input[name=legalIdNum]").attr("alt", "tmp");
			}
			$("input[name=legalIdNum]").val("");
		};
		function ifGroupCustomer(obj) {
			var ifGroupType = $(obj).val();
			if (ifGroupType == 0) {//非集团客户
				$("input[name=groupName]").attr("mustinput", "0");
				$("input[name=groupName]").parents(".rows").hide();
			} else {//集团客户
				$("input[name=groupName]").attr("mustinput", "1");
				$("input[name=groupName]").parents(".rows").show();
			}
		}

		function insertCertiInfo(obj) {
			var flag = submitJsMethod($(obj).get(0), '');
			if (flag) {
				var url = $(obj).attr("action");
				var dataParam = JSON.stringify($(obj).serializeArray());
				LoadingAnimate.start();
				jQuery.ajax({
					url : url,
					data : {
						ajaxData : dataParam,
						appId:appId,
						tableId:"tablecertiInfoInputList"
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						LoadingAnimate.stop();
						if (data.flag == "success") {
							//					  alert(top.getMessage("SUCCEED_OPERATION"),1);
                            top.addReceInfoFlag = true;
							var receAccountList=$('#bigFormShowiframe', parent.document).contents().find("#receAccountList");
                            if(receAccountList.length<=0){
                                receAccountList=$('#taskShowDialogiframe', parent.document).contents().find("#receAccountList");
							}
							receAccountList.html(data.tableData);
                            myclose_click();
							//window.close();
						} else if (data.flag == "error") {
							alert(data.msg, 0);
						}
					},
					error : function(data) {
						LoadingAnimate.stop();
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
			}
		}
		
		function getCusMngNameDialog(userInfo) {
			$("input[name=cusMngName]").val(userInfo.opName);
			$("input[name=cusMngNo]").val(userInfo.opNo);
		};
		
		//从集团客户放大镜赋值给表单属性
		function getGroInfoArtDialog(groupInfo) {
			$("input[name=groupName]").val(groupInfo.groupName);
			$("input[name=groupNo]").val(groupInfo.groupNo);
			$("input[name='groupName']").attr("readonly", true);
		};
		
		function selectAreaCallBack(areaInfo) {
			$("input[name=careaProvice]").val(areaInfo.disName);
		};
					//表单信息提示
	function func_uior_addTips(obj,msg){
		var $this =$(obj);
		var val = $this.val();
		if ($this.hasClass("Required")) {
			$this.removeClass("Required");
		}
		if($this.parent().find(".Required-font").length>0){
			$this.parent().find(".Required-font").remove();
		}
		//if(val==null||val==""||typeof(val)=="undefined"){
			//var $label = $('<label class="Required-font"><i class="i i-jingbao"></i>'+msg+'</label>');
			var $label = $('<div class="error required">'+msg+'</div>');
			$label.appendTo($this.parent());
			$this.addClass("Required");
		//}
		$this.one("focus.addTips", function(){
			$label.remove();
		});
	};
	function validateDupCertiNo(){
				$.ajax({
					url:webPath+"/certiInfo/validateDupCertiNoAjax",
					data : {
						ajaxData : 	$("input[name=certiNo]").val()
					},
					type : "POST",
					dataType : "json",
					beforeSend : function() {
					},
					success : function(data) {
						if(data.result == "0"){
							func_uior_addTips($("input[name=certiNo]"),"权属证书编号已存在");
							$("input[name=certiNo]").val("");
						}
					},
					error : function(data) {
						$("input[name=certiNo]").val("");
						alert(top.getMessage("FAILED_OPERATION"," "), 0);
					}
				});
	};
	//选择客户的押品
	var _selectCollateralData=function(_setCollateralData){
		selectCollateralDataDialog(_setCollateralData,cusNo,appId);
	};
        //选择业务中关联的未出库的押品
        function selectCollateralDataDialog(callback,cusNo,appId){
            var  tmpheight=$(window.document).height();
            tmpheight=tmpheight*0.80;
            dialog({
                id:"collateralDataDialog",
                title:'选择押品',
                url:webPath+'/pledgeBaseInfo/getPledgeInfoPageForSelect2?cusNo='+cusNo+"&appId="+appId+"&collateralType="+collateralType,
                width:800,
                height: tmpheight,
                backdropOpacity:0,
                onshow:function(){
                    this.returnValue = null;
                },onclose:function(){
                    if(this.returnValue){
                        //返回对象的属性:opNo,opName;如果多个，使用@分隔
                        if(typeof(callback)== "function"){
                            callback(this.returnValue);
                        }else{
                        }
                    }
                }
            }).showModal();
        };
        //选择客户押品回调设置押品相关字段。
        var _setCollateralData=function(data){
            $("input[name='pledgeName']").val(data.pledgeName);
			$("input[name='collateralId']").val(data.pledgeNo);
            $.ajax({
                url:webPath+"/certiInfo/getPledgeMethod",
                data : {pledgeMethod:data.pledgeMethod},
                type:'post',
                dataType:'json',
                success:function(data){
                    if (data.flag == "success"){
                        $("[name='dealTypeName']").val(data.dealTypeName);
                        $("[name='dealType']").val(data.dealType);
					}
                }
            });
        /* jQuery.ajax({
                url :webPath+"/mfBusCollateralRel/getAddPledgeBaseHtmlAjax",
                data : {pledgeNo:pledgeNo},
                type : "POST",
                dataType : "json",
                beforeSend : function() {
                },
                success : function(data) {
                    LoadingAnimate.stop();
                    if (data.flag == "success") {
                        $("#pledgeBaseInfoInsert").find("table").remove();
                        $("#pledgeBaseInfoInsert").find(".hidden-content").remove();
                        $("#pledgeBaseInfoInsert").html(data.htmlStr);
                        isQuote="1";
                        $("input[name=classId]").popupSelection({
                            searchOn:true,//启用搜索
                            inline:true,//下拉模式
                            multiple:false,//多选选
                            items:data.collClass,
                        });
                        $("input[name=classId]").parents("td").find(".pops-select").remove();
                    } else if (data.flag == "error") {
                        alert(data.msg, 0);
                    }
                },
                error : function(data) {
                    LoadingAnimate.stop();
                    alert(top.getMessage("FAILED_OPERATION"," "), 0);
                }
            });*/
        };
	</script>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
		            	<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" id="certiInfoInsert" theme="simple" name="operform" action="${webPath}/certiInfo/insertForListAjax">
							<dhcc:bootstarpTag property="formdlcertiinfo0003" mode="query"/>
						</form>	
					</div>
				</div>	
			</div>
			
		
				<div class="formRowCenter">
		   			<dhcc:thirdButton value="保存" action="保存" onclick="insertCertiInfo('#certiInfoInsert');"></dhcc:thirdButton>
		   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
		   		</div>
   		</div> 。
	</body>
</html>
