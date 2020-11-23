;
var MfMakingMeetingSummary_Detail = function(window, $) {
	var _init = function() {
        $.ajax({
            url:webPath+"/mfMakePolicyMeeting/getLastedByMakePolicyMeetingAppId?appId="+appId,
            type:'post',
            dataType:'json',
            success:function(data){
                if(data.ifShowMakePolicyMeeting == '0'){
                    $("#cusMakePolicyMeetingDiv").remove();
                }else{
                    var html = data.cusMakePolicyMeetingDetail;
                    //$("#cusMakePolicyMeetingDetailForm").empty().html(html);

                    var mfReviewMemberList = data.mfReviewMemberList;
                    let reviewHtml = '';
                    if(mfReviewMemberList != null && mfReviewMemberList.length > 0){
                        reviewHtml = '<table width="100%" height="auto" title="formmakepolicymeetingDetail">';
                        $.each(mfReviewMemberList,function(key,value){  //遍历键值对
                            //评审人员类型
                            reviewHtml = reviewHtml
                                + '<tr>'
                                + '<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">评审人员类型</span></p>'
                                + '<span class="font-smallup bold"><div style="display:inline-block;"  class="fieldShow" value="'+value.reviewMemberType+'">'+value.reviewMemberType+'</div></span></td>'

                                + '<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">评审委员</span></p>'
                                + '<span class="font-smallup bold"><div style="display:inline-block;"  class="fieldShow" value="'+value.reviewMemberName+'">'+value.reviewMemberName+'</div></span></td>'

                                + '<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">意见</span></p>'
                                + '<span class="font-smallup bold"><div style="display:inline-block;"  class="fieldShow" value="'+value.reviewMemberOpinion+'">'+value.reviewMemberOpinion+'</div></span></td>'

                                + '<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">其他需要补充或落实的内容</span></p>'
                                + '<span class="font-smallup bold"><div style="display:inline-block;"  class="fieldShow" value="'+value.reviewMemberOtherContent+'">'+value.reviewMemberOtherContent+'</div></span></td>'
                                + '</tr>';

                            // 复议或否决的主要理由
                            if(value.reviewMemberOpinion == "2" || value.reviewMemberOpinion == "3" ) {
                                reviewHtml = reviewHtml  +'<tr>'
                            }else {
                                reviewHtml = reviewHtml  +'<tr  style="display: none;">'
                            }
                            reviewHtml = reviewHtml
                                + '<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">复议或否决的主要理由</span></p>'
                                + '<span class="font-smallup bold"><div style="display:inline-block;"  class="fieldShow" value="'+value.reviewMemberReason+'">'+value.reviewMemberReason+'</div></span></td>'
                                + '</tr>';
                            // 其他理由
                            if (value.reviewMemberReason == "7") {
                                reviewHtml = reviewHtml +'<tr>'
                            }else{
                                reviewHtml = reviewHtml +'<tr  style="display: none;">'
                            }
                            reviewHtml = reviewHtml
                                + '<td colspan="1" rowspan="1"><p class="font-small"><span class="theader">其他理由</span></p>'
                                + '<span class="font-smallup bold"><div style="display:inline-block;"  class="fieldShow" value="'+value.reviewMemberOtherReason+'">'+value.reviewMemberOtherReason+'</div></span></td>'
                                + '</tr>';
                        });
                        reviewHtml = reviewHtml +'</table>';
                    }
                    $("#cusMakePolicyMeetingDetailForm").empty().html(reviewHtml+html);
                }
            }
        });
	};
	return {
		init : _init,
	};
}(window, jQuery);
