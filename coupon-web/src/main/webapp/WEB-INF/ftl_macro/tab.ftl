<#--
标准tab形式
tabId:输入生成的tab的Id
titles:输入的tab的抬头，示例<#assign titles = ["抬头1","抬头2","抬头3"] />
pages:插入的每个页面的ftl路径<#assign pages = ["ui/tabs/page1.ftl","ui/tabs/page2.ftl","ui/tabs/page3.ftl"] />
-->
<#macro tab  tabId titles pages>
<div id="${tabId}">
	<ul>
	<#list titles as title>
		<li><a href="#${tabId}_${title_index}">${title}</a></li>
	</#list>	
	</ul>
	<#list pages as page>
	<div id="${tabId}_${page_index}">
	<#include page>
	</div>
	</#list>
</div>
<script>
	$(function() {
		$( "#${tabId}" ).tabs();
	});
	</script>
</#macro>
<#--
iframe tab形式
tabId:输入生成的tab的Id
titles:输入的tab的抬头，示例<#assign titles = ["抬头1","抬头2","抬头3"] />
pages:插入的每个iframe页面的路径:<#assign pages = [base+"/ui/zTree.htm",base+"/ui/pager.htm",base+"/ui/timepicker.htm"] />
-->
<#macro tabIframe  tabId titles pages>
<div id="${tabId}">
	<ul>
	<#list titles as title>
		<li><a href="#mainDiv">${title}</a></li>
	</#list>	
	</ul>
	<div id="mainDiv">
	<iframe id="tabIfame"  src="${pages[0]}" frameborder="0" style="width: 100%;height:500px"> </iframe>
	</div>
</div>
<script>
	$(function() {
		$( "#${tabId}" ).tabs({
   			select: function(event, ui) { 
   			switch(ui.index) {
   			<#list pages as page>
			case ${page_index}:
		    $("#mainDiv").html('<iframe id="${titles[page_index]}"  src="${page}" frameborder="0" style="width: 100%;height:500px"> </iframe>');
			break;
			</#list>
			}
   			}
		});
	});
	</script>
</#macro>