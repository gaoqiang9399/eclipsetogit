<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
<head>
<title>详情</title>
<script type="text/javascript" src="${webPath}/component/model/js/MfSysTemplate_InsertComm.js?v=${cssJsVersion}"></script>
<script type="text/javascript" src="${webPath}/component/model/js/MfSysTemplateEsignerType_Insert.js?v=${cssJsVersion}"></script>
<script type="text/javascript">
			var ajaxData = JSON.parse('${ajaxData}');
			var templateNameZh = '${mfSysTemplate.templateNameZh}';
			var templateNo = '${mfSysTemplate.templateNo}';
			$(function(){
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
                MfSysTemplateEsignerType_Insert.showIfElectricSign();
				//MfSysTemplate_InsertComm.init();
			});
			function updateTemplate(obj){
				var flag = submitJsMethod($(obj).get(0), '');
				if(flag){
					var dataParam = JSON.stringify($(obj).serializeArray());
					$.ajax({
						url:webPath + "/mfSysTemplate/updateAjax",
						data:{ajaxData:dataParam},
						type:'post',
						dataType:'json',
						success:function(data){
							if(data.flag == "success"){
								top.editFlag=true;
								 /*  window.top.alert(data.msg,1);
								 var url=webPath + "/mfSysTemplate/getListPage";
				  			 	 $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url); */
							 	 myclose_click();
							}else{
							 window.top.alert(data.msg,0);
							}
						},error:function(){
							alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
						}
					});
				}
			}
		</script>
</head>
<%-- <body class="body_bg overFlowHidden">
	<div class="mf_content">
		<div class="content-box">
			<p class="tip">
				<span>说明：</span>带*号的为必填项信息，请填写完整。
			</p>
			<div class="tab-content">
				<form method="post" theme="simple" name="operform" id="operform"
					action="${webPath}/mfSysTemplate/updateAjax">
					<dhcc:bootstarpTag property="formsystemplate0002" mode="query" />
				</form>
			</div>
		</div>
	</div>
	<div class="formRowCenter">
		<dhcc:thirdButton value="保存" action="保存"
			onclick="updateTemplate('#operform');"></dhcc:thirdButton>
		<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel"
			onclick="myclose();"></dhcc:thirdButton>
	</div>
</body> --%>

<body class="overflowHidden bg-white">
		<div class="mf_content container form-container">
			<div class="content-box scroll-content">
				<div class="tab-content col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form method="post" theme="simple" name="operform" id="operform" action="${webPath}/mfSysTemplate/updateAjax">
							<dhcc:bootstarpTag property="formsystemplate0002" mode="query" />
						</form>
					</div>
                    <div class="arch_sort work_sort" style="border: 0px;">
                        <div class="dynamic-block" title="签章人员类型配置" name="MfSysTemplateEsignerTypeController" data-sort="14" data-tablename="mf_sys_template_esigner_type">
                            <div class="list-table">
                                <div class="title">
                                    <span class="formName"><i class="i i-xing blockDian"></i>签章人员类型配置</span>
                                    <button class="btn btn-link formAdd-btn" onclick="MfSysTemplateEsignerType_Insert.addMfSysTemplateEsignerType();" title="新增">
                                        <i class="i i-jia3"></i>
                                    </button>
                                    <button class=" btn btn-link pull-right formAdd-btn" data-toggle="collapse" data-target="#MfSysTemplateEsignerTypeController">
                                        <i class="i i-close-up"></i><i class="i i-open-down"></i>
                                    </button>
                                </div>
                                <div class="content collapse in" id="MfSysTemplateEsignerTypeController">
                                ${esignerTypeTableHtml}
                                </div>
                            </div>
                        </div>
                    </div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="updateTemplate('#operform');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>