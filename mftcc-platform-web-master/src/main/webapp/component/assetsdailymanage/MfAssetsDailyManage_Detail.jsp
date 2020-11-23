<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>详情</title>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<!-- 
				两列表单使用 col-md-8 col-md-offset-2
				四列表单使用 col-md-10 col-md-offset-1
				 -->
				<div class="col-md-10 col-md-offset-1 margin_top_20">
					<div class="bootstarpTag">
						<!-- <div class="form-title">资产日常管理详情</div> -->
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="MfAssetsDailyManageForm" theme="simple" name="operform" action="${webPath}/mfAssetsDailyManage/updateAjax">
							<dhcc:bootstarpTag property="formassetsDailyManageDetail" mode="query"/>
						</form>
					</div>
				</div>
	   		</div>
			<div class="formRowCenter">
				<!-- ajaxSave方法不适用时需另行实现。新写JS代码必须闭包。 -->
<%--
	   			<dhcc:thirdButton value="保存" action="保存" onclick="ajaxSave('#MfAssetsDailyManageForm')"></dhcc:thirdButton>
--%>
	   			<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
	   		</div>	
	   	</div>
		<script type="text/javascript">
            //详情页面修改的方法；
             function ajaxSave(obj){
                var flag = submitJsMethod($(obj).get(0), '');
                var url = $(obj).attr("action");
                if(flag){
                    var dataParam = JSON.stringify($(obj).serializeArray());
                    jQuery.ajax({
                        url:url,
                        data:{ajaxData:dataParam},
                        type:"POST",
                        dataType:"json",
                        beforeSend:function(){
                            LoadingAnimate.start();
                        },success:function(data){
                            if(data.flag == "success"){
                                alert(data.msg,1);
                                myclose_click();//保存完成之后关闭弹窗，回到列表
                            }else if(data.flag == "error"){
                                alert(data.msg,0);
                            }
                        },error:function(data){
                            alert(top.getMessage("FAILED_OPERATION"," "),0);
                        },complete:function(){
                            LoadingAnimate.stop();
                        }
                    });
                }
            };
		</script>
	</body>
</html>