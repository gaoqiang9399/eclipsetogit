<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<script type="text/javascript" src="${webPath}/component/model/js/MfSysTemplate_InsertComm.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
		var fileWarterName = "${fileName}";
		var ajaxData = JSON.parse('${ajaxData}');
        var templateNo = "${templateNo}";
		$(function(){
				$("form[id=UploadFileAction_upload]").attr({"enctype":"multipart/form-data"});
				$("form[id=UploadFileAction_upload]").attr({"name":"upload"});
				$("form[id=UploadFileAction_upload]").attr({"id":"upload"});
				$("input[name=upload]").attr({"id":"upload"});

				myCustomScrollbarForForm({
					obj:".scroll-content",
					advanced : {
						updateOnContentResize : true
					}
				});
				MfSysTemplate_InsertComm.init();
				//$("input[name=upload]").attr("mustinput","1");
				/* var uploadLabel=$("input[name=upload]").parents(".rows").find(".form-label");
				var uploadLabelText =$(uploadLabel).text(); */
				//$(uploadLabel).empty().append("<span class='required'>*</span>"+uploadLabelText);
			});
		//暂时没用，保留备用
		function changeCifIdPic(obj){
			var fileName=$("input[name=upload]").val();
			var array=fileName.split("\\");
			fileName=array[array.length-1];
			var fileNameEnd = fileName.toLowerCase().substr(fileName.lastIndexOf(".")); 
			fileName = fileWarterName+fileNameEnd;
			var filePath = "/component/model/docmodel/";
			$("input[name=fileName]").val(fileName);
			$("input[name=uploadFileName]").val(fileName);
			$("input[name=primaryKey]").val("00");
			$("input[name=filePath]").val(filePath);
			$("input[name=formNote]").val("cont");
			var flag = submitJsMethod(obj, '');
			if(flag){
				$("#upload").ajaxSubmit(function(data){
					if(data.indexOf("false;")>-1){
			  			var m=data.split(";")[1];
			  			window.top.alert("上传失败!"+m,0);
					}else{
						$("input[name=upload]").attr("mustinput","0");
						var dataParam = JSON.stringify($(obj).serializeArray());
						$.ajax({
							url:webPath + "/mfSysTemplate/insertAjax",
							data:{ajaxData:dataParam},
							type:'post',
							dataType:'json',
							success:function(data){
								if(data.flag == "success"){
									  window.top.alert(data.msg,1);
									  myclose_click();
								}else{
								 window.top.alert(data.msg,0);
								}
							},error:function(){
								alert(top.getMessage("ERROR_REQUEST_URL", getUrl));
							}
						}); 
					}
		     });
			}
		}
		//保存模板信息
		function insertTemplate(obj){
			var flag = submitJsMethod($(obj).get(0), '');
			if(flag){
				var dataParam = JSON.stringify($(obj).serializeArray());
				$.ajax({
					url:webPath + "/mfSysTemplate/insertAjax",
					data:{ajaxData:dataParam},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.flag == "success"){
						//原来新增基础模板新增成功后的处理
// 							  window.top.alert(data.msg,1);
// 							  var url=webPath + "/mfSysTemplate/getListPage";
// 				  			  $(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url);
// 							  myclose_click();

							//选择组件下拉框新增后的处理
							var sysTemplate = new Object();
							sysTemplate.templateNo = data.mfSysTemplate.templateNo
							sysTemplate.templateNameZh = data.mfSysTemplate.templateNameZh;
							sysTemplate.templateNameEn = data.mfSysTemplate.templateNameEn;
							sysTemplate.templateType = data.mfSysTemplate.templateType;
							sysTemplate.templateTypeName = data.mfSysTemplate.templateTypeName;
							sysTemplate.useFlag = data.mfSysTemplate.useFlag;
							parent.dialog.get('addSysTemplateDialog').close(sysTemplate).remove();
						}else{
						 window.top.alert(data.msg,0);
						}
					},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", webPath + "/mfSysTemplate/insertAjax"));
					}
				}); 
			}
		}
		//暂时没用，保留备用
		function checkFileEnd(){
			var fileName=$("input[name=upload]").val();
			var fileNameEnd = fileName.toLowerCase().substr(fileName.lastIndexOf("."));
			if(fileNameEnd==".doc"||fileNameEnd==".docx"||fileNameEnd==".xlsx"||fileNameEnd==".xls"){
				return true;
			}else{
				window.top.alert("请上传格式为doc、docx、xlsx、xls文件!",0);
				$("input[name=upload]").val("");
				return false;
			}
		}
		//新增模板的关闭方法
		function closeDialog(){
		parent.dialog.get('addSysTemplateDialog').close().remove();
		}
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="mf_content container form-container">
			<div class="content-box scroll-content">
				<div class="tab-content col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form method="post" theme="simple" name="operform" id="operform" action="${webPath}/uploadFile/upload">
							<dhcc:bootstarpTag property="formsystemplate0002" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="insertTemplate('#operform');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="closeDialog();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
