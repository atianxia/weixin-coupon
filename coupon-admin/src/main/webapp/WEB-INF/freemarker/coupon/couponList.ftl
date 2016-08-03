<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>券列表管理</title>
    <#include "/common.ftl">
    <script type="text/javascript" src="${resRoot}/js/coupon/couponList.js"></script>
</head>
<body>
    <table id="dg" title="券列表管理" class="easyui-datagrid" 
            url="list.do"
            toolbar="#toolbar" pagination="true"
            rownumbers="true" fitColumns="true" singleSelect="true">
        <thead>
            <tr>
                <th data-options="field:'ck',checkbox:true"></th>
                <th field="couponId" width="50" formatter="formatCouponId">优惠券ID</th>
                <th field="couponName" width="50">优惠券名称</th>
                <th field="couponStatus" width="50" formatter="formatCouponStatus">优惠券状态</th>
                <th field="brandLogo" width="50">品牌标志</th>
                <th field="usingRule" width="50">使用规则</th>
                <th field="buyLink" width="50">购买链接</th>
                <th field="expireDate" width="100" >有效日期截至时间</th>
                <th field="createTime" width="100" >创建时间</th>
                <th field="updateTime" width="100">最后更新时间</th>
            </tr>
        </thead>
    </table>
    <div style="margin:10px 0;">
        <span>勾选模式: </span>
        <select onchange="$('#dg').datagrid({singleSelect:(this.value==0)})">
            <option value="0">单行</option>
            <option value="1">多行</option>
        </select><br/>
    </div>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newCoupon()">新建优惠券</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCoupon()">编辑优惠券</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteCoupon()">删除优惠券</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="operateCouponStatus(1)">上架优惠券</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="operateCouponStatus(0)">下架优惠券</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:400px;height:380px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">券信息</div>
        <form id="fm" method="post" novalidate>
            <div class="fitem">
                <label>优惠券ID:</label>
                <input name="couponId" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>优惠券名称:</label>
                <input name="couponName" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>使用规则:</label>
                <input name="usingRule" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>购买链接:</label>
                <input name="buyLink" class="easyui-textbox" required="true">
            </div>
            <div class="fitem">
                <label>有效日期截至时间:</label>
                <input name="expireDate" id="expireDate" class="easyui-datetimebox" data-options="required:true"  required="true" missingMessage="有效日期截至时间"  editable="false"/><font color="red">*</font>
            </div>
            
             <div class="fitem">
                <label>品牌标志:</label>
                
	            <input id="brandLogo" name="brandLogo" class="easyui-textbox">
	            <br>
            </div>
        </form>
        <form id="uploadPicForm"  method="post" enctype="multipart/form-data">
           &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input id="brandLogoPicFile" name="brandLogoPicFile" class="easyui-filebox" data-options="prompt:'请选择文件...'">
           <a href="javascript:void(0)" class="easyui-linkbutton" onclick="uploadPicFile()" >上传</a> 
		</form> 
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveCoupon()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
    </div>
    
    <form id="uploadForm"  method="post"  enctype="multipart/form-data">
             选择文件：　
       <input id="uploadExcel" name="uploadExcel" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">
	   　　<a href="javascript:void(0)" class="easyui-linkbutton" style="width:122px" onclick="uploadExcel()" >导入Excel优惠券</a> 　　     　　　　　    
    </form> 
    
    
    <style type="text/css">
        #fm{
            margin:0;
            padding:10px 30px;
        }
        .ftitle{
            font-size:14px;
            font-weight:bold;
            padding:5px 0;
            margin-bottom:10px;
            border-bottom:1px solid #ccc;
        }
        .fitem{
            margin-bottom:5px;
        }
        .fitem label{
            display:inline-block;
            width:80px;
        }
        .fitem input{
            width:160px;
        }
    </style>
</body>
</html>