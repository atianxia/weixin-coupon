<#--
设置jqGrid路径
-->

<#macro setJsCss>
<link rel="stylesheet" type="text/css" media="screen" href="${resRoot}/js/jquery/jqGrid/css/ui.jqgrid.css" />
<script src="${resRoot}/js/jquery/jqGrid/js/i18n/grid.locale-cn.js" type="text/javascript"></script>
<script src="${resRoot}/js/jquery/jqGrid/js/jquery.jqGrid.min.js" type="text/javascript"></script>
</#macro>

 <#--
 url :取得分页数据的url
 colModel:数据对象，name:显示的名字，index:在排序时后端需要的名字，例子：[{ name: 'orderId',index: 'order_Id'}, {name: 'orderName',index: 'order_Name'}, {name: 'orderPrice',index: 'order_Price'}, {name: 'orderDesc',index: 'order_desc'}]
 rowNum：每页行数
 caption:表格的抬头
 rowList:切换每页行数
 tableId：定义个表格的ID
 pagerId:分页页码
 root:json的对象数据
 page:当前页码
 total：总页数
 records：总记录数
 edit:是否允许编辑
 add:是否允许添加
del:是否允许删除
 -->
 
 <#macro jqGrid url colModel  tableId="gridTable" pagerId="gridPager"  rowNum=10  caption="表格"   rowList="[10, 20, 30]"    root="datas" page="pageNumber"  total="pageCount" records="totalDataCount"  edit="false" add="false" del="false">
 <table id="${tableId}"></table>
<div id="${pagerId}"></div>
<script>
    jQuery("#gridTable").jqGrid({
        jsonReader: {
            root: "${root}",
            page: "${page}",
            total: "${total}",
            records: "${records}",
            repeatitems: false
        },
        url: '${url}',
        datatype: "json",
		height:"auto",
        colModel: ${colModel},
        rowNum: ${rowNum},
        rowList: ${rowList},
        pager: '#${pagerId}',
        viewrecords: true,
        caption: "${caption}"
    });
    jQuery("#gridTable").jqGrid('navGrid', '#gridPager', {
        edit: ${edit},
        add: ${add},
        del: ${del}
    });
</script>
</#macro>