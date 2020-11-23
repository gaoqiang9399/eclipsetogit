<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%--bootstrapjs--%>
<div id="ckl">
			<div>
				<div class="my_filter_title">
					<h4>我的筛选</h4>
					<i class="i i-jia1" onclick="showFilterBox();"></i>
				</div>
				<ul class="ztree" id="my_filter">

				</ul>
			</div>
		</div>
		<div class="modal fade bs-example-modal-lg my-filter-box" tabindex="-1" role="dialog" aria-labelledby="myLargeModalLabel">
			<div class="modal-dialog modal-lg">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" id="close" class="close"><span aria-hidden="true">&times;</span><span class="sr-only">Close</span></button>
						<h4 class="modal-title text-center" id="myModalLabel3"><input type="text" id="filterName"/><input type="hidden" id="filterNo"/></h4>
					</div>
					<div class="modal-body">
						<div class="row clearfix">
							<div class="col-md-12 column">
								<button class="btn " id="add_filter" type="button">新增</button>
								<button class="btn " id="save_filter" type="button">保存</button>
							</div>
						</div>
					</div>
					<div class="modal-footer" style="min-height: 70vh;">
						<ul class="ztree" id="my_filter_group">
						</ul>
					</div>
				</div>
			</div>
		</div>
			<script type='text/javascript'>
			</script>