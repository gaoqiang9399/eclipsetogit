<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>新增</title>
		<meta http-equiv="Content-Type" content="multipart/form-data; charset=utf-8" />
		<script type="text/javascript" src="${webPath}/component/include/jquery.form.js" ></script>
		<script type="text/javascript" src="${webPath}/component/include/jquery.form.min.js" ></script>
		<script type="text/javascript" src='${webPath}/component/include/uior_val1.js'> </script>
		<script type="text/javascript" src="${webPath}/component/model/js/MfSysTemplate_InsertComm.js?v=${cssJsVersion}"></script>
        <script type="text/javascript" src="${webPath}/component/model/js/MfSysTemplateEsignerType_Insert.js?v=${cssJsVersion}"></script>
		<script type="text/javascript">
		var fileWarterName = "${fileName}";
		var templateNo = "${templateNo}";
		var ajaxData = JSON.parse('${ajaxData}');
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
			    MfSysTemplateEsignerType_Insert.showIfElectricSign();
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
                var templateSource = $("input[name='templateSource']").val();
                var templateSuffix = $("select[name='templateSuffix']").val();
                if(templateSource == '00000' && templateSuffix=='4'){
					if($("input[name=upload]").val() == ''){
                        window.top.alert("请上传格式为html文件!",0);
                        $("input[name=upload]").val("");
                        return false;
					}else{
                        var fileName = $("input[name='file']")[0].files[0].name;
                        $("input[name='templateNameEn']").val(fileName);
					}
                }

				var dataParam = JSON.stringify($(obj).serializeArray());
				$.ajax({
					url:webPath + "/mfSysTemplate/insertAjax",
					data:{ajaxData:dataParam},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.flag == "success"){
					 	 	window.top.alert(data.msg,1);
					 	 	top.templateType=data.mfSysTemplate.templateType;
					 	 	top.saveFlag = true;
					 	 	/* var url=webPath + "/mfSysTemplate/getListPage";
	  				  		$(top.window.document).find(".pt-page-current").find("iframe").eq(0).attr("src",url); */
						  	myclose_click();
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
		
		function selectTemplateByRadio(){
			selectSysTemplate("",function(sysTemplate){
				$.ajax({
					url:webPath + "/mfSysTemplate/getSysTemplateHtmlAjax",
					data:{templateNo:sysTemplate.templateNo},
					type:'post',
					dataType:'json',
					success:function(data){
						if(data.flag == "success"){
							$("#operform").html(data.htmlStr);
						}else{
						 window.top.alert(data.msg,0);
						}
					},error:function(){
						alert(top.getMessage("ERROR_REQUEST_URL", webPath + "/mfSysTemplate/insertAjax"));
					}
				}); 
			});
		}
		function changetemplateSuffix(){
		    var templateSource = $("input[name='templateSource']").val();
		    if($("select[name='templateSuffix']").val() == '4'){
		        $(".opt_divNew").show();
                if(templateSource == '00000'){
                    $(".anchor2-anchor").show();
                    $(".anchor2-anchor input[name='upload']").removeAttr("disabled");
				}
			}else{
                $(".opt_divNew").hide();
                $(".anchor2-anchor").hide();
			}
		}

		function selectHtmlTemplate(){
            $("input[name='file']").click();
        }

        function  uploadFiles() {
            var fileName = $("input[name='file']")[0].files[0].name;
            var fileNameEnd = fileName.toLowerCase().substr(fileName.lastIndexOf("."));
            if(fileNameEnd==".html"){
                $("input[name=upload]").val(fileName);
                var file = new FormData($("#file")[0]);
				$.ajax({
					url: pageOfficePath + "/officeService/pageOffice/uploadTemplate",
					data: file,
					type: 'post',
					dataType: 'json',
                    processData: false,
                    contentType : false,
					success: function (data) {
						if (data.flag == "success") {
						} else {
							window.top.alert(data.msg, 0);
						}
					}, error: function () {
						alert(top.getMessage("ERROR_REQUEST_URL", webPath + "/mfSysTemplate/insertAjax"));
					}
				});
			}else{
                window.top.alert("请上传格式为html文件!",0);
                $("input[name=upload]").val("");
                return false;
            }
        }
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="mf_content container form-container">
			<div class="content-box scroll-content">
				<div class="tab-content col-md-8 col-md-offset-2 column margin_top_20">
					<div class="opt_divNew" style="display: none">
						<ul>
							<li><i class="i i-jiantoua"></i>
								<a href="${pageOfficePath}/officeService/pageOffice/downloadTemplateFile?fileName=templateFile.html">模板下载</a>
							</li>
						</ul>
					</div>
					<form method="post" id="file" action="" enctype="multipart/form-data">
						<input type="file" name="file"  multiple="multiple" style="display: none" onchange="uploadFiles()">
					</form>
					<div class="bootstarpTag">
						<form method="post" theme="simple" name="operform" id="operform" action="${webPath}/uploadFile/upload">
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

                                </div>
                            </div>
                        </div>
                    </div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="insertTemplate('#operform');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>
