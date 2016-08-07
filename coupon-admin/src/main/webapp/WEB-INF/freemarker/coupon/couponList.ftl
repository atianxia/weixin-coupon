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
                <th field="couponType" width="50" formatter="formatCouponType">优惠券类型</th>
                <th field="couponStatus" width="50" formatter="formatCouponStatus">优惠券状态</th>
                <th field="onlineTime" width="50">优惠券上架时间</th>
                <th field="amount" width="50">优惠券总量</th>
                <th field="receivedTimes" width="50">已领取次数</th>
                <th field="expireDate" width="100" >有效日期截至时间</th>
                <th field="brandLogo" width="50">品牌标志</th>
                <th field="usingRule" width="50">使用规则</th>
                <th field="localLink" width="50">本地链接</th>
                <th field="buyLink" width="50">购买链接</th>
                <th field="createTime" width="100" >创建时间</th>
                <th field="updateTime" width="100">最后更新时间</th>
            </tr>
        </thead>
    </table>
    <div id="toolbar">
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-add" plain="true" onclick="newCoupon()">新建优惠券</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-edit" plain="true" onclick="editCoupon()">编辑优惠券</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-remove" plain="true" onclick="deleteCoupon()">删除优惠券</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-ok" plain="true" onclick="operateCouponStatus(1)">上架优惠券</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-no" plain="true" onclick="operateCouponStatus(0)">下架优惠券</a>
    </div>
    
    <div id="dlg" class="easyui-dialog" style="width:500px;height:480px;padding:10px 20px"
            closed="true" buttons="#dlg-buttons">
        <div class="ftitle">券信息</div>
        <form id="fm" method="post" novalidate enctype="multipart/form-data">
          <table border="0">
  			  <tr style="display:none">
			    <td><label>优惠券ID:</label></td>
			    <td><input name="couponId" type="text"></td>
  			  </tr>
  			  <tr>
			    <td><span>优惠券类型: </span></td>
			    <td>
				    <select id="couponType" name="couponType" required="true" onchange="couponTypeChange(this.options[this.options.selectedIndex].value)" >
			            <option value="0">本地领取</option>
			            <option value="1">本地链接展示</option>
			            <option value="2">第三方领取</option>
			        </select>
			        <font color="red">*</font>
		        </td>
  			  </tr>
  			  <tr>
			    <td><label>优惠券名称:</label></td>
			    <td><input name="couponName" class="easyui-textbox" required="true"></td>
  			  </tr>
  			  <tr>
	  			  <td><label>购买链接:</label></td>
	  			  <td><input name="buyLink" class="easyui-textbox" required="true"></td>
  			  </tr>
  			  <tr>
	  			  <td><label>有效截至时间:</label></td>
	  			  <td><input name="expireDate" id="expireDate" class="easyui-datetimebox" data-options="required:true"  required="true" missingMessage="有效日期截至时间"  editable="false"/><font color="red">*</font></td>
  			  </tr>
  			  <tr id="localLinkRow">
	  			  <td><label>本地领取图片:</label></td>
	  			  <td>
	  			  	<input id="localLinkFile" name="localLinkFile" class="easyui-filebox" data-options="prompt:'请选择领取图片...'">
	              </td>
  			  </tr>
  			  <tr>
	  			  <td><label>品牌标志图片:</label></td>
	  			  <td>
	            	<input id="brandLogoFile" name="brandLogoFile" class="easyui-filebox" data-options="prompt:'请选择品牌标志图片...'">
	              </td>
  			  </tr>
  			  
  			  <tr>
	  			  <td><label>使用规则:</label></td>
	  			  <td><textarea name="usingRule" id="usingRule" style="width:250px;height:150px;"></textarea></td>
  			  </tr>
  			  <tr id="couponCodeFileRow">
	  			  <td><label>券码文件:</label></td>
	  			  <td>
	  			  	<input id="couponCodeFile" name="couponCodeFile" class="easyui-filebox" data-options="prompt:'请选择券码excel...'">
	              </td>
  			  </tr>
			</table>
        </form>
        
    </div>
    <div id="dlg-buttons">
        <a href="javascript:void(0)" class="easyui-linkbutton c6" iconCls="icon-ok" onclick="saveCoupon()" style="width:90px">Save</a>
        <a href="javascript:void(0)" class="easyui-linkbutton" iconCls="icon-cancel" onclick="javascript:$('#dlg').dialog('close')" style="width:90px">Cancel</a>
    </div>
   	<br><br><br><br>
    <form id="uploadForm"  method="post"  enctype="multipart/form-data">
              选择优惠券码excel文件：　
       <input id="uploadExcel" name="uploadExcel" class="easyui-filebox" style="width:200px" data-options="prompt:'请选择文件...'">
	   　　<a href="javascript:void(0)" class="easyui-linkbutton" style="width:122px" onclick="uploadExcel()" >导入优惠券码</a> 　　     　　　　　    
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