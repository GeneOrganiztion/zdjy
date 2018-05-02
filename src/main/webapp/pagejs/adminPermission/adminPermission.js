function initAdminPermissionManager(){
	
	
	/*$("#addAdminModal").draggable();
	$("#editAdminModal").draggable();*/
	var grid_selector = "#grid-table";
	var pager_selector = "#grid-pager";
	//resize to fit page size
	$(window).on('resize.jqGrid', function () {
		$(grid_selector).jqGrid( 'setGridWidth', $(".page-content").width() );
    })
	//resize on sidebar collapse/expand
	var parent_column = $(grid_selector).closest('[class*="col-"]');
	$(document).on('settings.ace.jqGrid' , function(ev, event_name, collapsed) {
		if( event_name === 'sidebar_collapsed' || event_name === 'main_container_fixed' ) {
			//setTimeout is for webkit only to give time for DOM changes and then redraw!!!
			setTimeout(function() {
				$(grid_selector).jqGrid( 'setGridWidth', parent_column.width() );
			}, 0);
		}
    })
	jQuery(grid_selector).jqGrid({
		
		url: webroot + "mapAdminPermission/selectAdminPermission.do",
		mtype: 'post',
		datatype: "json",
		height: 320,
		colNames:['','权限ID','用户ID','用户名称','权限名称','权限url','权限描述'],
		colModel:[
		    {name:'mapAdminPermId',index:'', width:80,hidden:true},
          	{name:'permissionId',index:'permission_id', width:80, sorttype:"int", editable: true},
          	{name:'adminId',index:'admin_id',width:80, editable:true},
          	{name:'username',index:'username', width:80, sorttype:"int", editable: true,sortable:false},
          	{name:'permName',index:'perm_name', width:80, sorttype:"int", editable: true,sortable:false},
			{name:'url',index:'url', width:80, sorttype:"int", editable: true,sortable:false},
			{name:'permDes',index:'perm_des',width:80, editable:true},
		], 
		viewrecords : true,
		rowNum:10,
		rowList:[10,20,30],
		loadonce: false,
		pager : pager_selector,
		altRows: true,
		sortable:true,
	    sortorder:'asc',
		jsonReader: {
			total: 'lastPage', 
			records: 'total',
			root: 'list',
			repeatitems: true
		},
		//multikey: "ctrlKey",
		multiselect: true,
        multiboxonly: false,
        ondblClickRow:function(rowid){
            var rowData = $('#grid-table').getRowData(rowid);//获取选中行的记录 
            alert(rowData.id);
        },
		loadComplete : function() {
			var table = this;
			setTimeout(function(){
				updatePagerIcons(table);
				//enableTooltips(table);
			}, 0);
		},			
	});
	
	$(window).triggerHandler('resize.jqGrid');
	//时间使用js 
	$(".date-select").datepicker({
		showOtherMonths: true,
		selectOtherMonths: false,
		todayHighlight: true
	});
	$('.date-select').datepicker().next().on(ace.click_event, function(){
		$(this).prev().focus();
	});

	jQuery(grid_selector).jqGrid('navGrid',pager_selector,
		{ 	//navbar options
			edit: false,
			editicon : 'ace-icon fa fa-pencil blue',
			add: false,
			addicon : 'ace-icon fa fa-plus-circle purple',
			del: false,
			delicon : 'ace-icon fa fa-trash-o red',
			search: false,
			searchicon : 'ace-icon fa fa-search orange',
			refresh: false,
			refreshicon : 'ace-icon fa fa-refresh green',
			view: false,
			viewicon : 'ace-icon fa fa-search-plus grey',
		},
		{
			//edit record form
			//closeAfterEdit: true,
			//width: 700,
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_edit_form(form);
			}
		},
		{
			//new record form
			//width: 700,
			closeAfterAdd: true,
			recreateForm: true,
			viewPagerButtons: false,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
				.wrapInner('<div class="widget-header" />')
				style_edit_form(form);
			}
		},
		{
			//delete record form
			recreateForm: true,
			beforeShowForm : function(e) {
				var form = $(e[0]);
				if(form.data('styled')) return false;
				
				form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner('<div class="widget-header" />')
				style_delete_form(form);
				
				form.data('styled', true);
			},
			onClick : function(e) {
				//alert(1);
			}
		},
		{
			//search form
			recreateForm: true,
			afterShowSearch: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
				style_search_form(form);
			},
			afterRedraw: function(){
				style_search_filters($(this));
			}
			,
			multipleSearch: true,
			/**
			multipleGroup:true,
			showQuery: true
			*/
		},
		{
			//view record form
			recreateForm: true,
			beforeShowForm: function(e){
				var form = $(e[0]);
				form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap('<div class="widget-header" />')
			}
		}
	);
	
	function updatePagerIcons(table) {
		var replacement = 
		{
			'ui-icon-seek-first' : 'ace-icon fa fa-angle-double-left bigger-140',
			'ui-icon-seek-prev' : 'ace-icon fa fa-angle-left bigger-140',
			'ui-icon-seek-next' : 'ace-icon fa fa-angle-right bigger-140',
			'ui-icon-seek-end' : 'ace-icon fa fa-angle-double-right bigger-140'
		};
		$('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
			var icon = $(this);
			var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
			
			if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
		})
	}
	
	function enableTooltips(table) {
		$('.navtable .ui-pg-button').tooltip({container:'body'});
		$(table).find('.ui-pg-div').tooltip({container:'body'});
	}

	//var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

	$(document).one('ajaxloadstart.page', function(e) {
		$(grid_selector).jqGrid('GridUnload');
		$('.ui-jqdialog').remove();
	});
	function TimeAdd0(time){
		return time < 10 ? ("0" + time) : time;
	}
	function formatDate(cellvalue, options, rowObject){
		var date = new Date(cellvalue);
		var time = date.getFullYear() + "-" + TimeAdd0((date.getMonth() + 1)) + "-" + TimeAdd0(date.getDate()) 
					+ " " + TimeAdd0(date.getHours()) + ":" + TimeAdd0(date.getMinutes()) + ":" + TimeAdd0(date.getSeconds());
		return time;
	}
 	
}



//查询
function queryAdminPermission(){
	var data = $("#queryAdminPermissionForm").serialize();
	var url = webroot + "mapAdminPermission/selectAdminPermission.do";
	$("#grid-table").jqGrid('setGridParam',{ 
        url: url + "?" + data, 
        //postData:jsonData, 
        page:1,
        mtype:"post"
    }).trigger("reloadGrid"); //重新载入 
}
//删除权限
function deleteAdminPermission(){
	var selectedIds = $("#grid-table").jqGrid("getGridParam", "selarrrow");//选择多行记录
	if(selectedIds.length < 1){
		alertmsg("warning", "请至少选中一行!");
		return;
	}
	var ids = "";
	for(var i = 0; i < selectedIds.length; i ++){
		var rowData = $('#grid-table').getRowData(selectedIds[i]);//获取选中行的记录
		var mapAdminPermId = rowData.mapAdminPermId;
		ids = ids + mapAdminPermId + ",";
	}
	if(empty(ids)){
		return;
	}
    Lobibox.confirm({ 
        title:"删除用户权限",      //提示框标题 
        msg: "是否确认删除",   //提示框文本内容 
        callback: function ($this, type, ev) {               //回调函数 
            if (type === 'yes') { 
            	$.ajax({
            		type:"post",
            		url:webroot+"mapAdminPermission/delete.do",
            		data:{"mapAdminPermIds":ids},
            		success:function(data){
            			if(data.success){
            				alertmsg("success", "刪除权限成功!");
            			}else{
            				alertmsg("error", "刪除权限失败!");
            			}
            			//删除成功重新加载jqGrid
            			$("#grid-table").jqGrid('setGridParam',{ 
            		        page:1,
            		        mtype:"post"
            		    }).trigger("reloadGrid"); //重新载入 
            		}
            	});
            } else if (type === 'no') { 
                       
            } 
       } 
     });
	
}
//添加权限
function addPermission(){
	
	//再次打开model之前清空上次的操作记录
	$("#addPermissionModal :input").val("");
	$("#addPermissionModal").modal("show");
	
	
}
//保存权限
function savePermission(){
	//保存权限信息
	var data = getParams("#addPermissionModal");
	$.ajax({
		type: "post",
		url: webroot + "permission/savePermission.do",
		data: data,
		success: function(msg){
			if(msg.success){
				alertmsg("success", "新增权限成功!");
				var data = $("form").serialize();
				var url = webroot + "permission/selectPermission.do";
				$("#grid-table").jqGrid('setGridParam',{ 
			        url: url + "?" + data, 
			        page:1,
			        mtype:"post"
			    }).trigger("reloadGrid"); //重新载入 
			}
		}
	});
	$("#addPermissionModal").modal("hide");
}
//修改权限
function editPermission(){
	var lanId = $("#grid-table").jqGrid("getGridParam","selrow");
	var rowData = $('#grid-table').getRowData(lanId);//获取选中行的记录 
	var id = rowData.permissionId;
	if(!isNoEmpty(id)){
		alertmsg("warning","请至少选中一行 !");
		return;
	}
	$.ajax({
		type: "post",
		url: webroot + "permission/selectPermissionByPermissionId.do",
		data: {permissionId: id},
		success: function(msg){
			$("#editPermissionModal input[name='permissionId']").val(msg.permissionId);
			$("#editPermissionModal input[name='permName']").val(msg.permName);
			$("#editPermissionModal input[name='url']").val(msg.url);
			$("#editPermissionModal input[name='permDes']").val(msg.permDes);
			$("#editPermissionModal").modal("show");
			
			
		}
	});
}
//修改权限
function editAndSavePermission(){
	//保存权限信息
	var data = getParams("#editPermissionform");
	$.ajax({
		type: "post",
		url: webroot + "permission/updatePermission.do",
		data: data,
		success: function(msg){
			if(msg.success){
				alertmsg("success", "修改权限成功!");
				var data = $("form").serialize();
				var url = webroot + "permission/selectPermission.do";
				$("#grid-table").jqGrid('setGridParam',{ 
			        url: url + "?" + data, 
			        page:1,
			        mtype:"post"
			    }).trigger("reloadGrid"); //重新载入 
			}
		}
	});
	$("#editPermissionModal").modal("hide");
}
