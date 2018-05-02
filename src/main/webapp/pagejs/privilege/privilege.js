function initPrivilegeManager(){
	
	
	//$("#addAdminModal").draggable();
	//$("#editAdminModal").draggable();
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
		
		url: webroot + "privilege/selectPrivilege.do",
		mtype: 'post',
		datatype: "json",
		height: 320,
		colNames:['权限ID','权限名称','权限英文名称','权限代码','URL','创建时间','最后更新时间'],
		colModel:[
          	{name:'privilegeId',index:'privilege_id', width:80, sorttype:"int", editable: true},
          	{name:'displayName',index:'display_name',width:80, editable:true},
			{name:'privilegeName',index:'privilege_name', width:80, sorttype:"int", editable: true,sortable:false},
			{name:'privilegeCode',index:'privilege_code',width:80, editable:true},
			{name:'url',index:'url',width:80, editable:true},
			{name:'createTime',index:'create_time',width:80, editable:true,formatter:formatDate},
			{name:'lastModifiedTime',index:'last_modified_time',width:80,formatter:formatDate}
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
function queryPrivilege(){
	var data = $("#queryRoleForm").serialize();
	var url = webroot + "privilege/selectPrivilege.do";
	$("#grid-table").jqGrid('setGridParam',{ 
        url: url + "?" + data, 
        //postData:jsonData, 
        page:1,
        mtype:"post"
    }).trigger("reloadGrid"); //重新载入 
}
//删除权限
function deletePrivilege(){
	var selectedIds = $("#grid-table").jqGrid("getGridParam", "selarrrow");//选择多行记录
	if(selectedIds.length < 1){
		alertmsg("warning", "请至少选中一行!");
		return;
	}
	var ids = "";
	for(var i = 0; i < selectedIds.length; i ++){
		var rowData = $('#grid-table').getRowData(selectedIds[i]);//获取选中行的记录
		var privilegeId = rowData.privilegeId;
		ids =ids + privilegeId + ",";
	}
    Lobibox.confirm({ 
        title:"删除权限",      //提示框标题 
        msg: "是否确认删除",   //提示框文本内容 
        callback: function ($this, type, ev) {               //回调函数 
            if (type === 'yes') { 
            	$.ajax({
            		type:"post",
            		url:webroot+"privilege/deletePrivilege.do",
            		data:{"privilegeIds":ids},
            		success:function(data){
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
function addPrivilege(){
	//再次打开model之前清空上次的操作记录
	$("#addPrivilegeModal :input").val("");
	$("#addPrivilegeModal").modal("show");
}
//保存权限
function savePrivilege(){
	//保存权限信息
	var data = getParams("#addPrivilegeModal");
	$.ajax({
		type: "post",
		url: webroot + "privilege/savePrivilege.do",
		data: data,
		success: function(msg){
			if(msg.success){
				alertmsg("success", "新增权限成功!");
				var data = $("form").serialize();
				var url = webroot + "privilege/selectPrivilege.do";selectPrvilege
				$("#grid-table").jqGrid('setGridParam',{ 
			        url: url + "?" + data, 
			        page:1,
			        mtype:"post"
			    }).trigger("reloadGrid"); //重新载入 
			}
		}
	});
	$("#addPrivilegeModal").modal("hide");
}
//修改权限
function editPrivilege(){
	var lanId = $("#grid-table").jqGrid("getGridParam","selrow");
	var rowData = $('#grid-table').getRowData(lanId);//获取选中行的记录 
	var privilegeId = rowData.privilegeId;
	if(!isNoEmpty(privilegeId)){
		alertmsg("warning","请至少选中一行 !");
		return;
	}
	$.ajax({
		type: "post",
		url: webroot + "privilege/selectByPrivilegeId.do",
		data: {privilegeId: privilegeId},
		success: function(msg){
			$("#editPrivilegeModal input[name='privilegeId']").val(msg.privilegeId);
			$("#editPrivilegeModal input[name='displayName']").val(msg.displayName);
			$("#editPrivilegeModal input[name='privilegeCode']").val(msg.privilegeCode);
			$("#editPrivilegeModal input[name='url']").val(msg.url);
			$("#editPrivilegeModal input[name='parentId']").val(msg.parentId);
			$("#editPrivilegeModal").modal("show");
		}
	});
}
//修改权限 
function editAndSavePrivilege(){
	var data = getParams("#editPrivilegeform");
	$.ajax({
		type: "post",
		url: webroot + "privilege/updatePrivilege.do",
		data: data,
		success: function(msg){
			if(msg.success){
				alertmsg("success", "修改权限成功!");
				var data = $("form").serialize();
				var url = webroot + "privilege/selectPrivilege.do";
				$("#grid-table").jqGrid('setGridParam',{ 
			        url: url + "?" + data, 
			        page:1,
			        mtype:"post"
			    }).trigger("reloadGrid"); //重新载入 
			}
		}
	});
	$("#editPrivilegeModal").modal("hide");
}