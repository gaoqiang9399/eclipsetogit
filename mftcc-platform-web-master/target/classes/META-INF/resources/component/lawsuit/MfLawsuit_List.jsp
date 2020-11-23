<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/pub_view.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
	<head>
		<title>列表</title>
	</head>
	<body class="body_bg">
	    <div class="container">
	    <br>
			<!--页面显示区域-->
			<div class="row clearfix">
				<div class="col-md-12 column">
					<div id="content" class="table_content"  style="height: auto;">
						<dhcc:tableTag property="tablelawsuit0001" paginate="mfLawsuitList" head="true"></dhcc:tableTag>
					</div>
				</div>
			</div>
		</div>
	</body>
<script type="text/javascript" src="${webPath}/component/lawsuit/js/MfLawsuit_List.js"></script>
	<script type="text/javascript">
		$(function(){
			lawsuitList.init();
		});
		function getById(obj,url){
			lawsuitList.getById(obj,url);
		};
        function getDetailCus(obj,url){
            if(url.substr(0,1)=="/"){
                url =webPath + url;
            }else{
                url =webPath + "/" + url;
            }
            window.location.href=url;
        }
	</script>
</html>
