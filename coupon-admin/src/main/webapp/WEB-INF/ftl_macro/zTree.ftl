<#--
在使用的页面中必须import这个ftl文件
示例：<#import "/common/ui/zTree.ftl" as zTree />
-->
<#--
zTree js & css setup 
you must already load jquery.js
设置zTree所需要的路径宏，在页面中直接写
<@zTree.setJsCss />调用即可
-->
<#macro setJsCss >
<link href="${resRoot}/js/jquery/zTree/3.5/css/zTreeStyle.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="${resRoot}/js/jquery/zTree/3.5/js/jquery.ztree.all-3.5${minSuffix}.js"></script>
</#macro>
<#-- 
 simpleTree
 id:the tree ui's id 设置这个生成的tree所在ul元素的id
 rootPId:the tree's root id设置根id默认可以不输入
 url: the json format data url 输入zTree的url,该URL必须返回JSON形式，树的示例格式如下：
 [{"id":"2","pId":"1","name":"机票管理","url":"menu/menu_2.htm","target":"mainFrame","isParent":true},
 {"id":"3","pId":"1","name":"团购","url":"menu/menu_3.htm","target":"mainFrame","isParent":true},
 {"id":"4","pId":"1","name":"酒店","url":"menu/menu_4.htm","target":"mainFrame","isParent":true},
 {"id":"900","pId":"1","name":"系统管理","url":"../menu/menu_900.htm","target":"mainFrame","isParent":true},
 {"id":"800","pId":"1","name":"demo展示","url":"menu/menu_800.htm","target":"mainFrame","isParent":true}]
 isCheck: if set "true",we can see the checkbox before all tree leaf 如果设置为true 则每个树节点上都有一个checkbox
 isEdit:if set "true" we can see edit button after the leaf 如果我们设置为true则我们可以看见编辑按钮在树的节点后面
-->
<#macro zTree id url  rootPId=1  isCheck="false" isEdit="false"  async="false" callback="false" onClick="zTreeOnClick">
 <script>
 <#--
 <#if async == "false">
 var zNodes;
$.ajax({
    url: "${url}",
    dataType: 'json',
    async: false,
	cache: false,
    success: function(data){
        zNodes = data;
    }
});
</#if>
var setting = {
<#if callback == "true">
callback: {
		onClick: ${onClick}
			},
</#if>
<#if async == "true">
async: {
				enable: true,
				url:"${url}",
				dataType: "json",
				autoParam:["id=pid"]
			},
</#if>
		edit:{
			enable : ${isEdit}
		},
		check:{
		    enable : ${isCheck}
		},	
      data:{
        simpleData: {
            enable: true,
            rootPId: ${rootPId}
        }
    }
}
$(document).ready(function(){
    $.fn.zTree.init($("#${id}"), setting<#if async == "false">,zNodes</#if>);
});
//-->
</script>
<ul id="${id}" class="ztree"></ul>
</#macro>
