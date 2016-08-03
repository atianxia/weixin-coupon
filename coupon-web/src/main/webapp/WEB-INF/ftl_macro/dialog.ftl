<#--
展现提示框dialog的freemarker宏控件，可以使某个DIV作为弹出框输出
input：欲作为dialog的div
autoOpen:是否自动打开
height:dialog的高度
width:dialog的宽度
modal:是否背景有灰色遮罩
title:抬头标题
-->
<#macro dialog  input="#dialog"  autoOpen="true" height="auto"  width=300 modal="true" title="">
<script>
	$(function() {
	    $( "${input}" ).dialog({
	    			title:"${title}",
				    autoOpen:${autoOpen},
					width: "${width}",
				    height: "${height}",
					modal: ${modal},
					buttons: [{text: "OK",click: function() { $(this).dialog("close"); } }]
			     });
	});
	</script>
</#macro>