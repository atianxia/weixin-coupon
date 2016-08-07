$(document).ready(function() {
	$('#localLinkRow').css("display", "none");
	$('#couponCodeFileRow').css("display", "none");
})
function uploadExcel(){ 
	var row = $('#dg').datagrid('getSelected');
	if(!row || !row.couponId){
		$.messager.alert('提示','请勾选券码对应的优惠券！','info');
		return;
	}
	$('#uploadForm').form('submit',{
        url: "import.do?couponId="+row.couponId,
        onSubmit: function(){//得到上传文件的全路径
	       	 var fileName= $('#uploadExcel').filebox('getValue');
	    	 //进行基本校验
	    	 if(fileName==""){   
	    		$.messager.alert('提示','请选择上传文件！','info'); 
	    	 }else{
	    		 //对文件格式进行校验
	    		 var d1=/\.[^\.]+$/.exec(fileName); 
	    		 if(d1!=".xls"){
	    	    	 $.messager.alert('提示','请选择xls格式文件！','info'); 
	    	    	 $('#uploadExcel').filebox('setValue',''); 
	    	     }
	    	 }
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
            	$('#dg').datagrid('reload');    // reload the coupon data
            	$.messager.show({title: '提示', msg: '操作成功！'}); 
            }
        }
    });
	
}


function uploadPicFile() { 
	$('#uploadPicForm').form('submit',{
        url: "picFileUpload.do",
        onSubmit: function(){
            return true;
        },
        success: function(result){
        	var result = eval('('+result+')');
        	$("input[name=brandLogo]").val(''+ result.picUrl);
        	$("input[name=brandLogo]").prev().val(''+ result.picUrl);
        	$.messager.show({title: '提示', msg: '上传品牌标志图片成功！'}); 
        }
     });
}




var url;
var beforeSubmit;
function newCoupon(){
    $('#dlg').dialog('open').dialog('center').dialog('setTitle','新建优惠券');
    $('#fm').form('clear');
    url = 'add.do';
    beforeSubmit = newCheck;
}
function editCoupon(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
    	if(couponType == 1){
    		$('#localLinkRow').removeAttr("style");
    	}else{
    		$('#localLinkRow').css("display", "none");
    	}
        $('#dlg').dialog('open').dialog('center').dialog('setTitle','编辑优惠券');
        $('#fm').form('load',row);
        url = 'save.do?couponId='+row.couponId;
        
    }
    beforeSubmit=null;
    
}

function newCheck(){
	var couponType = $('#couponType').val();
	if(!couponType){
		$.messager.alert('提示','请选择优惠券类型！','info'); 
		return false;
	}
	if(couponType==0){
		 var couponCodeFile= $('#couponCodeFile').filebox('getValue');
		 //进行基本校验
		 if(couponCodeFile==""){   
			$.messager.alert('提示','请选择券码文件！','info'); 
			return false;
		 }else{
			 //对文件格式进行校验
			 var d1=/\.[^\.]+$/.exec(couponCodeFile); 
			 if(d1!=".xls" && d1!=".xlsx"){
		    	 $.messager.alert('提示','请选择xls格式文件！','info'); 
		    	 $('#couponCodeFile').filebox('setValue',''); 
		    	 return false;
		     }
		 }
	}
	
	if(couponType==1){
		var localLinkFile= $('#localLinkFile').filebox('getValue');
		//进行基本校验
		 if(localLinkFile==""){   
			$.messager.alert('提示','请选择本地领取图片！','info'); 
			return false;
		 }
	}
	
	var brandLogoFile= $('#brandLogoFile').filebox('getValue');
	//进行基本校验
	 if(brandLogoFile==""){   
		$.messager.alert('提示','请选择品牌标志图片文件！','info'); 
		return false;
	 }
	 return true;
}

function saveCoupon(){
	
    $('#fm').form('submit',{
        url: url,
        onSubmit: function(){
        	if(beforeSubmit){
        		if(!beforeSubmit()){
        			return false;
        		}
        	}
        	var usingRule = $.trim($('#usingRule').val());
        	if( usingRule ==""){
		    	$.messager.alert('提示','请输入使用规则！','info'); 
				return false;
        	}
            return $(this).form('validate');
        },
        success: function(result){
            var result = eval('('+result+')');
            if (result.errorMsg){
                $.messager.show({
                    title: 'Error',
                    msg: result.errorMsg
                });
            } else {
                $('#dlg').dialog('close');        // close the dialog
                $('#dg').datagrid('reload');    // reload the coupon data
                $('#fm').form('clear');
//                $("input[name$='file']").next().children("input[type='text']").val('');
            }
        }
    });
}
function deleteCoupon(){
    var row = $('#dg').datagrid('getSelected');
    if (row){
        $.messager.confirm('Confirm','确定要删除优惠券?',function(r){
            if (r){
                $.post('delete.do',{couponIds:row.couponId},function(result){
                    if (result.success){
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    }
                },'json');
            }
        });
    }
}

function operateCouponStatus(operateType){
    var row = $('#dg').datagrid('getSelected');
    var remindMsg = operateType==1?"确定要上架优惠券":"确定要下架优惠券"
    if (row){
        $.messager.confirm('Confirm',remindMsg,function(r){
            if (r){
                $.post('operateCouponStatus.do',{couponIds:row.couponId,operateType:operateType},function(result){
                    if (result.success){
                        $('#dg').datagrid('reload');    // reload the user data
                    } else {
                        $.messager.show({    // show error message
                            title: 'Error',
                            msg: result.errorMsg
                        });
                    }
                },'json');
            }
        });
    }
}

var formatCouponId =function(couponId){
	return '<a href="#" class="easyui-link" onclick="parent.window.addTab(' + "'优惠券" + couponId + "使用情况','coupon/detail.do?couponId="+ couponId+ "')\">" + couponId +'</a>'
}

var formatCouponStatus = function(couponStatus){
	return couponStatus ==1 ? "上架" : "下架";
}

var formatCouponType = function(couponType){
	return couponType ==0 ? "本地领取" : (couponType ==1 ? "本地链接展示" :"第三方领取");
}

function couponTypeChange(couponType){
	if(couponType == 1){
		$('#localLinkRow').removeAttr("style");
	}else{
		$('#localLinkRow').css("display", "none");
	}
	
	if(couponType ==0){
		$('#couponCodeFileRow').removeAttr("style");
		$('#couponCodeFileRow').attr("required", "true");
	}else{
		$('#couponCodeFileRow').css("display", "none");
		$('#couponCodeFileRow').attr("required", "false");
	}
	
}
