$(function() {
	$("form").delegate("input, select, textarea", {
		"focus" : function() {
			$(this).parents(".tdvalue").addClass("focusStyle");
		},
		"blur" : function() {
			$(this).parents(".tdvalue").removeClass("focusStyle");
		}
	});
});