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
					closable:true
				});
			}
		}
	</script>
</head>
<body>
    <div style="margin:20px 0;">
    	
    </div>
    <div class="easyui-layout" style="width:1800px;height:900px;">
        <div data-options="region:'west',split:true" title="菜单" style="width:200px;">
            <div class="easyui-accordion" data-options="fit:true,border:false">
                <div title="券管理" data-options="selected:true" style="padding:10px;">
                    <a href="#" class="easyui-link" onclick="addTab('券列表管理','coupon/index.do')">券列表管理</a>
                    <br><br>
                    <a href="#" class="easyui-link" onclick="addTab('优惠券使用情况','coupon/detail.do')">优惠券使用情况</a>
                </div>
                <!-- 
                <div title="Title2" data-options="selected:true" style="padding:10px;">
                </div>
                <div title="Title3" style="padding:10px">
                </div> 
                -->
            </div>
        </div>
        <div id="tt" data-options="region:'center',title:'九苑优惠券管理系统'" class="easyui-tabs">
		</div>
        
        
    </div>
</body>
</html>