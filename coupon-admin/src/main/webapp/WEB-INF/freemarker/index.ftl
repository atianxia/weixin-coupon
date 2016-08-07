<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>九苑后台管理系统</title>
   <#include "/common.ftl">
   <script>
		function addTab(title, url){
			if ($('#tt').tabs('exists', title)){
				$('#tt').tabs('select', title);
			} else {
				var content = '<iframe scrolling="auto" frameborder="10px"  src="'+url+'" style="width:100%;height:100%;"></iframe>';
				$('#tt').tabs('add',{
					title:title,
					content:content,
					closable:true,
					fit:true
				});
			}
		}
	</script>
	
	<script>
     var index = 0;
         function addPanel() {
             index++;
             $('#tabs').tabs('add', {
                 title: 'Tab' + index,
                 content: '<div style="padding:10px">Content' + index + '</div>',
                 closable: true
             });
         }
         function removePanel() {
             var tab = $('#tabs').tabs('getSelected');
             if (tab) {
                 var index = $('#tabs').tabs('getTabIndex', tab);
                 $('#tabs').tabs('close', index);
             }
         }
     </script>
	
</head>
 <body class="easyui-layout">
     <div data-options="region:'north',border:false" style="height: 74px; background: #2596ea; padding: 10px">
        九苑优惠券管理系统
     </div>
  
    <div data-options="region:'west',split:true,collapsible:false" title="菜单" style="width: 170px;" >
        <div title="券管理" data-options="selected:true" style="padding:10px;">
                    <a href="#" class="easyui-link" onclick="addTab('券列表管理','coupon/index.do')">券列表管理</a>
                    <br><br>
                    <a href="#" class="easyui-link" onclick="addTab('优惠券使用情况','coupon/detail.do')">优惠券使用情况</a>
                </div>
     </div>
    <div id="tt" data-options="region:'center',title:'九苑优惠券管理系统'" class="easyui-tabs">
	</div>
      </body>


</html>