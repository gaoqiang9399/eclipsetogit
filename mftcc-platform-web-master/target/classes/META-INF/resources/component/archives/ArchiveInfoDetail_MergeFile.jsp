<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html lang="zh-cn">
	<head>
		<title>归档文件合并</title>
		<style type="text/css">
			* { margin:0; padding:0; }
			div.centent {
			   float:left;
			   text-align: center;
			   margin: 10px;
			}
			span { 
				display:block; 
				margin:2px 2px;
				padding:4px 10px; 
				background:#898989;
				cursor:pointer;
				font-size:12px;
				color:white;
			}
		</style>
		<script type="text/javascript">
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
				var archiveInfoDetailList = '${archiveInfoDetailListJSONArray}';
				archiveInfoDetailList = eval(archiveInfoDetailList);
				
				var $selectNoSort = $("#selectNoSort");
				$.each(archiveInfoDetailList, function(i, archiveInfoDetail){
					var $option = $("<option value='" + archiveInfoDetail.archiveDetailNo + "'>" + archiveInfoDetail.docName + "</option>");
					$selectNoSort.append($option);
				});
				
				$("#add").click(function() {
					var $options = $("#selectNoSort option:selected");
					$options.appendTo("#selectSort");
				});
				
				$("#add_all").click(function() {
					var $options = $("#selectNoSort option");
					$options.appendTo("#selectSort");
				});
				
				$("#selectNoSort").dblclick(function () {
					var $options = $("option:selected", this);
					$options.appendTo("#selectSort");
				});
				
				$("#remove").click(function() {
					var $options = $("#selectSort option:selected");
					$options.appendTo("#selectNoSort");
				});
				
				$("#remove_all").click(function() {
					var $options = $("#selectSort option");
					$options.appendTo("#selectNoSort");
				});
				
				$("#selectSort").dblclick(function () {
					var $options = $("option:selected", this);
					$options.appendTo("#selectNoSort");
				});
			});
		
			/* function myclose(){
				$(top.window.document).find("#showDialog .close").click();
			} */
			
			function mergeFile(obj) {
				var flag = submitJsMethod($(obj).get(0), '');
				if (flag) {
					var archiveDetailNos = "";
					var $options = $("#selectSort option");
					$options.each(function() {
						archiveDetailNos += $(this).val() + "@";
					});
					if (archiveDetailNos.length > 0) {
						archiveDetailNos = archiveDetailNos.substring(0, archiveDetailNos.length - 1);
						var archiveDetailNosInput = $(":input[name=archiveDetailNos]").get(0);
						$(archiveDetailNosInput).val(archiveDetailNos);
						
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
									window.top.alert(top.getMessage("SUCCEED_OPERATION","归档文件合并"), 1);
									setTimeout("myclose_click();", 1000);
								} else {
									window.top.alert(data.msg, 0);
								}
							},
							error : function(data) {
								LoadingAnimate.stop();
								window.top.alert(top.getMessage("ERROR_REQUEST_URL", url));
							}
						});
					} else {
						window.top.alert(top.getMessage("FIRST_ARCHIVE_SEQUENCE"), 0);
					}
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
						<form  method="post" id="formdl_archive_merge01" theme="simple" name="operform" action="${webPath}/archiveInfoDetail/compressMergeFileAjax">
							<dhcc:bootstarpTag property="formdl_archive_merge01" mode="query" />
							<div class="form-tips">注意：目前支持文档、图片类型文件合并。按照要合并文档的顺序，依次选中（双击）左侧列表区域的文件添加到右侧。</div>
							<table>
								<tr>
									<td>
										<div class="centent">
											<select multiple id="selectNoSort" style="width:320px;height:240px;">
											</select>
											<div>
												<span id="add">选中添加到右边&gt;&gt;</span>
												<span id="add_all">全部添加到右边&gt;&gt;</span>
											</div>
										</div>
									</td>
									<td>
										<div class="centent">
											<select multiple id="selectSort" style="width:320px;height:240px;">
											</select>
											<div>
												<span id="remove">&lt;&lt;选中删除到左边</span>
												<span id="remove_all">&lt;&lt;全部删除到左边</span>
											</div>
										</div>
									</td>
								</tr>
							</table>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="合并" action="合并" onclick="mergeFile('#formdl_archive_merge01');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
