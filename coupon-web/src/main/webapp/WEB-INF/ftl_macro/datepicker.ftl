<#--
jquery ui时间和日期控件
-->
<#--
设置时间或者日期控件需要的css和初始化JS
-->
<#macro setJsCss >
<script type="text/javascript" src="${resRoot}/js/jquery/jquery-ui/1.9.2/datepicker-init.js"></script>
<link href="${resRoot}/js/jquery/jquery-ui/1.9.2/timepicker/timepicker.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resRoot}/js/jquery/jquery-ui/1.9.2/timepicker/timepicker.js"></script>
<script type="text/javascript" src="${resRoot}/js/jquery/jquery-ui/1.9.2/timepicker/timepicker_init.js"></script>
</#macro>
<#--
 日期控件，可以通过其选择年月日
输入参数：input 输入jquery的选择表达式匹配input框
-->
<#macro datepicker  input="#datepicker">
<script>
	$(function() {
		$("${input}").datepicker({buttonImage: '${resRoot}/js/jquery/jquery-ui/1.9.2/css/images/calendar.gif'});
	});
	</script>
</#macro>
<#--
 时间控件，可以通过其选择几点几分
输入参数：input 输入jquery的选择表达式匹配input框
-->
<#macro timepicker  input="#timepicker">
<script>
	$(function() {
		$( "${input}" ).timepicker({buttonImage: '${resRoot}/js/jquery/jquery-ui/1.9.2/css/images/calendar.gif'});
	});
	</script>
</#macro>
<#--
 日期时间控件，可以通过其选择年月日几点几分
输入参数：input 输入jquery的选择表达式匹配input框
-->
<#macro datetimepicker  input="#datetimepicker">
<script>
	$(function() {
		$( "${input}" ).datetimepicker({buttonImage: '${resRoot}/js/jquery/jquery-ui/1.9.2/css/images/calendar.gif'});
	});
	</script>
</#macro>