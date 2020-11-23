<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<title>归档文件补充</title>
		<script type="text/javascript">
			var aloneFlag = true;
			var dataDocParm={
				relNo:'${docBizNo}',
				docType:'archivesDoc',
				docTypeName:"",
				docSplitName:"补充文件",
				query:''
			};
			
			var ajaxData = '${ajaxData}';
			ajaxData = eval("("+ajaxData+")");
		
			$(function() {
// 				$(".scroll-content").mCustomScrollbar({
// 					advanced : {
// 						theme : "minimal-dark",
// 						updateOnContentResize : true
// 					}
// 				});
				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						theme : "minimal-dark",
						updateOnContentResize : true
					}
				});
				/* var scenceList = '${scenceListJSONArray}';
				scenceList = eval(scenceList);
				
				var scNoSelect = $(":input[name=scNo]").get(0);
				$.each(scenceList, function(i, scence){
					var $option = $("<option value='" + scence.scNo + "'>" + scence.scName + "</option>");
					$(scNoSelect).append($option);
				});
				
				var $docTypeOptions = $(":input[name=docType] option");
				var docType = $docTypeOptions.get(0).value;
				initDocSplitNo(docType); */
				
				//场景名称
				$("#formdl_archive_detail01 input[name=scNo]").popupSelection({
					searchOn : true,//启用搜索
					inline : true,//下拉模式
					multiple : false,//多选
					items : ajaxData.scence
				});
				
				var $docSplitNoInput = $("#formdl_archive_detail01 input[name=docSplitNo]").clone();
				//文档类型
				$("#formdl_archive_detail01 input[name=docType]").popupSelection({
					searchOn : true,//启用搜索
					inline : true,//下拉模式
					multiple : false,//多选
					items : ajaxData.docType,
					changeCallback : function (obj, elem) {
						initDocSplitNo(obj.val(), $docSplitNoInput);
					}
				});
				
				/* var $isPaperOptions = $(":input[name=isPaper] option");
				$isPaperOptions.each(function(){
					if ($(this).val() == "0") {
						$(this).attr("selected", true);
					}
				}); */
				
				$(".upload_body").css("margin", "0px");
				
				$("input[name=paperKeeperNo]").popupSelection({
					searchOn:true,//启用搜索
					inline:true,//下拉模式
					multiple:false,//多选选
					items:ajaxData.sysUser,
					changeCallback : function (obj, elem) {		
						$("input[name=paperKeeperName]").val(obj.data("text"));
					}
				});
			});
			
			/* function changeDocSplitNo(obj) {
				initDocSplitNo($(obj).children('option:selected').val());
			} */
			
			function initDocSplitNo(docType, $docSplitNoInput) {
				jQuery.ajax({
					url : webPath+"/archiveInfoDetail/getListByDocTypeAjax?docType=" + docType,
					type : "POST",
					dataType : "json",
					success : function(data) {
						if (data.success) {
							var $docSplitNoParent = $("#formdl_archive_detail01 input[name=docSplitNo]").parent();
							$docSplitNoParent.children().remove();
							var $newDocSplitNoInput = $docSplitNoInput.clone();
							$docSplitNoParent.append($newDocSplitNoInput);
							//文档细分类型
							$newDocSplitNoInput.popupSelection({
								searchOn : true,//启用搜索
								inline : true,//下拉模式
								multiple : false,//多选
								items : data.docTypeConfig
							});
						}
					},
					error : function(data) {
						window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
					}
				});
			}
			
			function myclose(){
				jQuery.ajax({
					url : webPath+'/archiveInfoDetail/deleteFileByDocBizNoAjax?docBizNo=${docBizNo}',
					type : "POST",
					dataType : "json",
					success : function(data) {
					},
					error : function(data) {
					}, complete:function(){
					}
				});
				$(top.window.document).find("#showDialog .close").click();
			}
			
			function addFile(obj) {
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var url = $(obj).attr("action");
					var dataParm = JSON.stringify($(obj).serializeArray());
					LoadingAnimate.start();
					jQuery.ajax({
						url : url,
						data : {
							ajaxData : dataParm
						},
						type : "POST",
						dataType : "json",
						success : function(data) {
							LoadingAnimate.stop();
							if (data.success) {
								top.refresh = true;
								window.top.alert(top.getMessage("SUCCEED_SAVE_CONTENT","归档文件"), 1);
								setTimeout("$(top.window.document).find('#showDialog .close').click()", 1000);
							} else {
								window.top.alert(data.msg, 0);
							}
						},
						error : function(data) {
							LoadingAnimate.stop();
							window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
						}
					});
				}
			};
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form  method="post" enctype="multipart/form-data" id="formdl_archive_detail01" theme="simple" name="operform" action="${webPath}/archiveInfoDetail/addFileAjax">
							<dhcc:bootstarpTag property="formdl_archive_detail01" mode="query" />
						</form>
						<!-- 要件信息 -->
						<div class="row clearfix">
							<div class="col-xs-12 column">
								<%@ include file="/component/doc/webUpload/uploadutil.jsp"%>
							</div>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="addFile('#formdl_archive_detail01');"></dhcc:thirdButton>
				<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
