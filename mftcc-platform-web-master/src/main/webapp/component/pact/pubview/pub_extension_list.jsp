<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>

<script type="text/javascript" src='${webPath}/component/pact/pubview/js/pub_extension_list.js'></script>

<script type="text/javascript">
    $(function () {
        pub_extension_list.init('${param.fincId}', '${param.formId}');
    });
</script>

<!-- 展期历史 -->
<div  class="row clearfix" id="extensionListNew">
    <div class="col-xs-12 column">
        <div class="list-table-replan">
            <div class="title">
                <span><i class="i i-xing blockDian"></i>展期历史</span>
                <button class="btn btn-link pull-right" data-toggle="collapse" data-target="#extensionList">
                    <i class='i i-close-up'></i> <i class='i i-open-down'></i>
                </button>
            </div>
            <div class="content margin_left_15 collapse in" id="extensionList">
            </div>
        </div>
    </div>
</div>
