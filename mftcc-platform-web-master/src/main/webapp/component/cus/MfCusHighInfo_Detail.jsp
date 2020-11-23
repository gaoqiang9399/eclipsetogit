<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<!DOCTYPE html>
<html>
	<%-- <head>
		<title>详情</title>
	</head>
	<body class="body_bg">
		<div class="bigform_content">
			<div class="bigForm_content_form">
				<div class="form_title">高管信息表</div>
				<form  method="post" theme="simple" name="operform" action="">
					<dhcc:formTag property="formcushigh00001" mode="query" />
				</form>	
			</div>
		</div>
	</body> --%>
	
	<head>
		<title>详情</title>
		
		<script type="text/javascript"  src='${webPath}/component/cus/js/MfCusHighInfo.js'> </script>
		<script type="text/javascript">
		$(function() {
            MfCusHighInfo.init();
            MfCusHighInfo.educationList();
            MfCusHighInfo.workList();
            /*MfCusHighInfo.socialList();*/
		//证件类型选择组件
		$("select[name=idType]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:false//单选
		});
		//投保情况选择组件
		/* try{
			$("select[name=situationInsurance]").popupSelection({
				searchOn:true,//启用搜索
				inline:true,//下拉模式
				multiple:true//多选
				
			});
		}catch(e){} */
		
		});
        //子表取消新增
        function highChildCancel(){
            $("#list").css("display","block");
            $("#update").css("display","none");
            $("#isnert").css("display","none");
		}
		//子表保存
		function save(obj){
            var data = JSON.stringify($(obj).serializeArray());
            var flag = submitJsMethod($(obj).get(0), '');
            if (flag) {
                LoadingAnimate.start();
                $.ajax({
                    type : "POST",
                    data:{ajaxData:data},
                    url : "${webPath}/mfCusHighChildInfo/insertAjax",
                    dataType : "json",
                    success : function(data) {
                        LoadingAnimate.stop();
                        if(data.flag=="success"){
                            window.top.alert(data.msg,1);
                            $("#list").css("display","block");
                            $("#insert").css("display","none");
                            MfCusHighInfo.educationList();
                            MfCusHighInfo.workList();
                          /*  MfCusHighInfo.socialList();*/
                        }else{
                            window.top.alert(data.msg,0);
                        }
                    },
                    error : function(xmlhq, ts, err) {
                        loadingAnimate.stop();
                        console.log(xmlhq);
                        console.log(ts);
                        console.log(err);
                    }
                });
            }
		}
		//修改
			function  update(obj) {
                var data = JSON.stringify($(obj).serializeArray());
                var flag = submitJsMethod($(obj).get(0), '');
                if (flag) {
                    LoadingAnimate.start();
                    $.ajax({
                        type : "POST",
                        data:{ajaxData:data},
                        url : "${webPath}/mfCusHighChildInfo/updateAjax",
                        dataType : "json",
                        success : function(data) {
                            LoadingAnimate.stop();
                            if(data.flag=="success"){
                                window.top.alert(data.msg,1);
                                $("#list").css("display","block");
                                $("#update").css("display","none");
                                MfCusHighInfo.educationList();
                                MfCusHighInfo.workList();
                                /*MfCusHighInfo.socialList();*/
                            }else{
                                window.top.alert(data.msg,0);
                            }
                        },
                        error : function(xmlhq, ts, err) {
                            loadingAnimate.stop();
                            console.log(xmlhq);
                            console.log(ts);
                            console.log(err);
                        }
                    });
                }
            }
		</script>
	</head>
	<body class="overflowHidden bg-white">
		<div class="container form-container" id="list">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
					<form  method="post" id="cusHighInfoUpdate" theme="simple" name="operform" action="${webPath}/mfCusHighInfo/updateAjax">
						<dhcc:bootstarpTag property="formcushigh00003" mode="query" />
					</form>
				    </div>
				</div>
				<%--//所受教育--%>
				<div class="col-xs-12 column">
					<div class="list-table-replan">
						<div class="title">
							<span>所受教育</span>
							<button class="btn btn-link formAdd-btn"  onclick="MfCusHighInfo.educationAdd()" title="新增"><i class="i i-jia3"></i></button>
						</div>
						<div class="content margin_left_15 collapse in" id="education">
							<dhcc:tableTag property="tablehighChildEducationList" paginate="tablehighChildEducationList" head="true"></dhcc:tableTag>
						</div>
					</div>
				</div>
				<%--//社会活动--%>
				<%--<div class="col-xs-12 column">
					<div class="list-table-replan">
						<div class="title">
							<span>社会活动</span>
							<button class="btn btn-link formAdd-btn"  onclick="MfCusHighInfo.socialAdd()" title="新增"><i class="i i-jia3"></i></button>
						</div>
						<div class="content margin_left_15 collapse in" id="social">
							<dhcc:tableTag property="tablehighChildSocialList" paginate="tablehighChildSocialList" head="true"></dhcc:tableTag>
						</div>
					</div>
				</div>--%>
				<%--//主要工作经历--%>
				<div class="col-xs-12 column">
					<div class="list-table-replan">
						<div class="title">
							<span>主要工作经历</span>
							<button class="btn btn-link formAdd-btn"  onclick="MfCusHighInfo.workAdd()" title="新增"><i class="i i-jia3"></i></button>
						</div>
						<div class="content margin_left_15 collapse in" id="work">
							<dhcc:tableTag property="tablehighChildWorkList" paginate="tablehighChildWorkList" head="true"></dhcc:tableTag>
						</div>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="saveCusHighInfoInsert('#cusHighInfoUpdate','update');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="myclose();"></dhcc:thirdButton>
			</div>
		</div>
         <%--新增页面--%>
		<div class="container form-container" style="display: none;" id="insert">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="cusHighChildInfoInsert" theme="simple" name="operform" action="${webPath}/mfCusHighChildInfo/insertAjax">
							<dhcc:bootstarpTag property="formcusHighChildInfoBase" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="save('#cusHighChildInfoInsert');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="highChildCancel();"></dhcc:thirdButton>
			</div>
		</div>
       <%--编辑页面--%>
		<div class="container form-container" style="display: none;" id="update">
			<div class="scroll-content">
				<div class="col-md-8 col-md-offset-2 column margin_top_20">
					<div class="bootstarpTag">
						<form  method="post" id="cusHighChildInfoUpdate" theme="simple" name="operform" action="${webPath}/mfCusHighChildInfo/updateAjax">
							<dhcc:bootstarpTag property="formcusHighChildInfoBase" mode="query" />
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="update('#cusHighChildInfoUpdate');"></dhcc:thirdButton>
				<dhcc:thirdButton value="关闭" action="关闭" typeclass="cancel" onclick="highChildCancel();"></dhcc:thirdButton>
			</div>
		</div>
	</body>
</html>