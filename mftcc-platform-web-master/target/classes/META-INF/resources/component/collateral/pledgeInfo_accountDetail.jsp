<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"
		 contentType="text/html; charset=UTF-8"%>
<%@ include file="/component/include/common.jsp"%>
<%@ include file="/component/include/pub_view_table.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<title>列表</title>
	<script type="text/javascript" src="${webPath}/component/collateral/js/PledgeInfo_account.js?v=${cssJsVersion}"></script>
	<script type="text/javascript">
        var appId = '${appId}';
        var pactId = '${pactId}';
        $(function() {
            PledgeInfo_account.initPact();
            PledgeInfo_account.initAccount();
            //滚动条
            myCustomScrollbarForForm({
                obj: ".scroll-content",
                advanced: {
                    updateOnContentResize: true
                }
            });
        });
        function backUp() {
            $("#firstContainer").css("display","block");
            $("#accountDetail").css("display","none");
        }
        function save(obj){
            var data = JSON.stringify($(obj).serializeArray());
            var flag = submitJsMethod($(obj).get(0), '');
            if (flag) {
                LoadingAnimate.start();
                $.ajax({
                    type : "POST",
                    data:{ajaxData:data},
                    url : "${webPath}/pledgeBaseInfo/updateByOne",
                    dataType : "json",
                    success : function(data) {
                        LoadingAnimate.stop();
                        if(data.flag=="success"){
                            window.top.alert(data.msg,1);
                            $("#firstContainer").css("display","block");
                            $("#accountDetail").css("display","none");
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
	<style type="text/css">
		th {
			text-align: left !important;
		}
		.list-table-replan .ls_list tbody tr td {
			text-align: left !important;
		}
	</style>
</head>
<body class="overflowHidden">
		<div class="container" id="firstContainer">
			<div class="scroll-content">
			<%--第一页--%>
			<div class="row clearfix bg-white tabCont">
				<div class="col-md-12 column">
					<div class="btn-div">
						<div class="col-md-12 text-center">
							<span class="top-title">合同详情</span>
						</div>
					</div>
				</div>
			</div>
			<div class="form-table base-info">
			<div class="content collapse in" id="pactDetailInfo" name="pactDetailInfo">
				<form method="post" theme="simple" id="pactDetailForm" name="operform" action="${webPath}/mfBusPact/updateAjaxByOne">
					<dhcc:propertySeeTag property="formpact0004" mode="query" />
				</form>
			</div>
			</div>

			<div class="col-xs-12 column">
				<div class="list-table-replan">
					<div class="title">
						<span>账款列表</span>

						<button class="btn btn-link formAdd-btn"  onclick="PledgeInfo_account.outWareHouse()" title="批量出库">批量出库</button> |

						<button class="btn btn-link formAdd-btn"  onclick="PledgeInfo_account.importExcel()" title="导入">导入</button> <-
                        <form action="/pledgeBaseInfo/importAccountExcel" style="display: inline-block;width: 80px" enctype="multipart/form-data" method="post" id="upload">
                            <input  type="file" name="file" >
                        </form>
                        <%--<button class="btn btn-link formAdd-btn"  onclick="PledgeInfo_account.importExcel()" title="批量更新">批量更新</button>--%>

					</div>
					<div class="content margin_left_15 collapse in" id="content">
					<dhcc:tableTag property="tableaccountInfoBase" paginate="tableaccountInfoBase" head="true"></dhcc:tableTag>
				</div>
				</div>
			</div>
     </div>

		<%--详情--%>

		<div class="container form-container" id="accountDetail" style="display: none">
			<div class="scroll-content">
				<div class="col-md-10 col-md-offset-1 column margin_top_20">
					<div class="bootstarpTag" id="detail">
						<div class="form-tips">说明：带*号的为必填项信息，请填写完整。</div>
						<form method="post" id="operform" theme="simple" name="operform" action="${webPath}/pledgeBaseInfo/updateByOne">
							<dhcc:bootstarpTag property="formdlpledgebaseinfo0004" mode="query"/>
						</form>
					</div>
				</div>
			</div>
			<div class="formRowCenter">
				<dhcc:thirdButton value="保存" action="保存" onclick="save('#operform');"></dhcc:thirdButton>
				<dhcc:thirdButton value="取消" action="取消" typeclass="cancel" onclick="backUp();"></dhcc:thirdButton>
			</div>
		</div>
		</div>
</body>