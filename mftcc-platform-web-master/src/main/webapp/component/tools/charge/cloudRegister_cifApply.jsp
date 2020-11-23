<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<head>
	</head>
<style type="text/css">
.title{
	font-size: 30px;
	margin-top: 35px;
	margin-bottom:35px;
	color: grey;
}
</style>
<body class="overflowHidden bg-white">
	<div class="container form-container">
		<div class="scroll-content">	
			<div class="col-md-8 col-md-offset-2 column margin_top_20">
				<div class="bootstarpTag">
					<div class="form_title">云平台账户申请</div>
					<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
					<form method="post" id="insertForm" action="${webPath}/accountPay/insertCifAccount" theme="simple" name="operform"  enctype="multipart/form-data">
						<dhcc:bootstarpTag property="formcifapply0001" mode="query"/>
						<input type="file" id="file" name="file1" style="display: none"/>
						<input type="hidden" name="flag" value="insert">
					</form>
				</div>
			</div>
		</div>
		<div class="formRowCenter">
			<input type="button" value="保存" onclick="submitThis()">
			<dhcc:thirdButton value="清空" action="关闭" typeclass="cancel" onclick="clearForm()"></dhcc:thirdButton>
		</div>
	</div>
</body>
<script type="text/javascript">
	$(function() {
		$("[name=fileName]").hide();
		var str="<span id='showFileName'></span>";
		var str1="&nbsp;&nbsp;<a href='javacript:void(0)' onclick='showImg()' class='afont'>参考图例</a>&nbsp;&nbsp;<span style='color:grey'>(请上传不超过2M的jpg,png,bmp格式图片)</span>";
		var str2="&nbsp;&nbsp;<a href='javacript:void(0)' class='afont' id='upImg'>点此上传</a>";
		$("[name=fileName]").parent().append(str+str2+str1);
    	$('#upImg').on('click',function(){
    		$("#file").click();
    	})
    	//自定义滚动条
// 		$(".scroll-content").mCustomScrollbar({
// 			advanced:{
// 				theme:"minimal-dark",
// 				updateOnContentResize:true
// 			}
// 		});
    	myCustomScrollbarForForm({
			obj:".scroll-content",
			advanced : {
				theme : "minimal-dark",
				updateOnContentResize : true
			}
		});
	});
	$('#file').change(function(){//当选择文件的输入框值变化时，显示文件名的输入框变化
		var filePath=$("#file").val();
		if(filePath!=null&&filePath!=""){
			if(filePath.length>25){
				filePath=filePath.substring(filePath.length-25, filePath.length);
			}
			$('#showFileName').html(filePath+"&nbsp;&nbsp;&nbsp;");
			$("[name=fileName]").val(filePath);
		}
	})
	function submitThis(){//新增方法
		var flag=0;
		$("[class=required]").each(function(i,o){
			var value=$(o).parents("[class=rows]").find("input").val();
			if(value==null||value==""){
				flag=1;
				return false;
			}
		})
		if(flag==1){
			alert("请填写完整资料再保存！",2);
			return false;
		}
		var filePath = $("#file").val();  
	    if(null!=filePath&&"" != filePath){  
	        var fileType = getFileType(filePath);
	        //判断上传的附件是否为图片  
	        if("jpg"!=fileType && "jpeg"!=fileType && "bmp"!=fileType && "png"!=fileType){  
	            $("#file").val("");  
	            alert("请上传JPG,JPEG,BMP,PNG格式的图片!",2);
	            return false;  
	        }  
	        else{  
	            //获取附件大小（单位：KB）  
	            var fileSize = document.getElementById("file").files[0].size / 1024;  
	            if(fileSize > 2048){  
	            	 $("#file").val("");  
	                alert("图片大小不能超过2MB!",2);
	                return false;  
	            }  
	        }  
	    }
		$("#insertForm")[0].submit();
	}
	 //获得文件类型
    function getFileType(filePath){  
	    var startIndex = filePath.lastIndexOf(".");  
	    if(startIndex != -1)  
	        return filePath.substring(startIndex+1, filePath.length).toLowerCase();  
	    else return "";  
	}
	function clearForm(){
		$("#insertForm")[0].reset();
	}
	function showImg(){
		var url=webPath+'/component/tools/charge/showLicenseDemoImg.jsp';
		top.createShowDialog(url,"营业执照照片示例",'80','80',function(){
		});
	}
</script>
</html>
