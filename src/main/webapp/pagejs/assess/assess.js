function initAssessManager() {
  //in ajax mode, remove remaining elements before leaving page
  //加载
  $(document).one('ajaxloadstart.page', function (e) {
    $('[class*=select2]').remove();
    $('select[name="duallistbox_demo1[]"]').bootstrapDualListbox('destroy');
    $('.rating').raty('destroy');
    $('.multiselect').multiselect('destroy');
  });

  $("#addAssessModal").draggable();
  $("#editAssessModal").draggable();
  var grid_selector = "#grid-table";
  var pager_selector = "#grid-pager";
  //resize to fit page size
  $(window).on('resize.jqGrid', function () {
    $(grid_selector).jqGrid('setGridWidth', $(".page-content").width());
  })
  //resize on sidebar collapse/expand
  var parent_column = $(grid_selector).closest('[class*="col-"]');
  $(document).on('settings.ace.jqGrid', function (ev, event_name, collapsed) {
    if (event_name === 'sidebar_collapsed' || event_name
        === 'main_container_fixed') {
      //setTimeout is for webkit only to give time for DOM changes and then redraw!!!
      setTimeout(function () {
        $(grid_selector).jqGrid('setGridWidth', parent_column.width());
      }, 0);
    }
  })
  jQuery(grid_selector).jqGrid({

    url: webroot + "assess/selectAssess.do",
    mtype: 'post',
    datatype: "json",
    height: 320,
    colNames: ['评价ID', '产品名称', '用户名', '评价内容', '创建时间', '最后更新时间', '操作'],
    colModel: [
      {
        name: 'assessId',
        index: 'assess_id',
        width: 80,
        sorttype: "int",
        editable: true
      },
      {name: 'product.proName', index: 'pro_name', width: 80, editable: true},
      {name: 'userName', index: 'user_name', width: 80, editable: true},
      {
        name: 'assessContent',
        index: 'assessContent',
        width: 80,
        sorttype: "int",
        editable: true,
        sortable: false
      },
      {
        name: 'createTime',
        index: 'create_time',
        width: 80,
        editable: true,
        formatter: formatDate
      },
      {
        name: 'lastModifiedTime',
        index: 'last_modified_time',
        width: 80,
        formatter: formatDate
      },
      {
        name: '',
        width: 80,
        sortable: false,
        formatter: formatterOperate
      }
    ],
    viewrecords: true,
    rowNum: 10,
    rowList: [10, 20, 30],
    loadonce: false,
    pager: pager_selector,
    altRows: true,
    sortable: true,
    sortorder: 'asc',
    jsonReader: {
      total: 'lastPage',
      records: 'total',
      root: 'list',
      repeatitems: true
    },
    //multikey: "ctrlKey",
    multiselect: true,
    multiboxonly: false,
    ondblClickRow: function (rowid) {
      var rowData = $('#grid-table').getRowData(rowid);//获取选中行的记录
      alert(rowData.id);
    },
    loadComplete: function () {
      var table = this;
      setTimeout(function () {
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
  $('.date-select').datepicker().next().on(ace.click_event, function () {
    $(this).prev().focus();
  });

  jQuery(grid_selector).jqGrid('navGrid', pager_selector,
      { 	//navbar options
        edit: false,
        editicon: 'ace-icon fa fa-pencil blue',
        add: false,
        addicon: 'ace-icon fa fa-plus-circle purple',
        del: false,
        delicon: 'ace-icon fa fa-trash-o red',
        search: false,
        searchicon: 'ace-icon fa fa-search orange',
        refresh: false,
        refreshicon: 'ace-icon fa fa-refresh green',
        view: false,
        viewicon: 'ace-icon fa fa-search-plus grey',
      },
      {
        //edit record form
        //closeAfterEdit: true,
        //width: 700,
        recreateForm: true,
        beforeShowForm: function (e) {
          var form = $(e[0]);
          form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner(
              '<div class="widget-header" />')
          style_edit_form(form);
        }
      },
      {
        //new record form
        //width: 700,
        closeAfterAdd: true,
        recreateForm: true,
        viewPagerButtons: false,
        beforeShowForm: function (e) {
          var form = $(e[0]);
          form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar')
          .wrapInner('<div class="widget-header" />')
          style_edit_form(form);
        }
      },
      {
        //delete record form
        recreateForm: true,
        beforeShowForm: function (e) {
          var form = $(e[0]);
          if (form.data('styled')) {
            return false;
          }

          form.closest('.ui-jqdialog').find('.ui-jqdialog-titlebar').wrapInner(
              '<div class="widget-header" />')
          style_delete_form(form);

          form.data('styled', true);
        },
        onClick: function (e) {
          //alert(1);
        }
      },
      {
        //search form
        recreateForm: true,
        afterShowSearch: function (e) {
          var form = $(e[0]);
          form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap(
              '<div class="widget-header" />')
          style_search_form(form);
        },
        afterRedraw: function () {
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
        beforeShowForm: function (e) {
          var form = $(e[0]);
          form.closest('.ui-jqdialog').find('.ui-jqdialog-title').wrap(
              '<div class="widget-header" />')
        }
      }
  );

  function updatePagerIcons(table) {
    var replacement =
        {
          'ui-icon-seek-first': 'ace-icon fa fa-angle-double-left bigger-140',
          'ui-icon-seek-prev': 'ace-icon fa fa-angle-left bigger-140',
          'ui-icon-seek-next': 'ace-icon fa fa-angle-right bigger-140',
          'ui-icon-seek-end': 'ace-icon fa fa-angle-double-right bigger-140'
        };
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(
        function () {
          var icon = $(this);
          var $class = $.trim(icon.attr('class').replace('ui-icon', ''));

          if ($class in replacement) {
            icon.attr('class', 'ui-icon '
                + replacement[$class]);
          }
        })
  }

  function enableTooltips(table) {
    $('.navtable .ui-pg-button').tooltip({container: 'body'});
    $(table).find('.ui-pg-div').tooltip({container: 'body'});
  }

  //var selr = jQuery(grid_selector).jqGrid('getGridParam','selrow');

  $(document).one('ajaxloadstart.page', function (e) {
    $(grid_selector).jqGrid('GridUnload');
    $('.ui-jqdialog').remove();
  });

  function TimeAdd0(time) {
    return time < 10 ? ("0" + time) : time;
  }

  function formatDate(cellvalue, options, rowObject) {
    var date = new Date(cellvalue);
    var time = date.getFullYear() + "-" + TimeAdd0((date.getMonth() + 1)) + "-"
        + TimeAdd0(date.getDate())
        + " " + TimeAdd0(date.getHours()) + ":" + TimeAdd0(date.getMinutes())
        + ":" + TimeAdd0(date.getSeconds());
    return time;
  }

  //格式化商品jqgrid之后的操作
  function formatterOperate(cellvalue, options, rowObject) {
    return detial = "<button onclick=\"viewAssess(" + rowObject.assessId
        + ")\" class=\"btn btn-minier btn-yellow\">查看评价</button>";
  }

  //格式化报告jqgrid之后的操作
  /*function formatterOperate(cellvalue, options, rowObject){
    return detial = "<button onclick=\"viewAssessPermission(" + rowObject.AssessId + ")\" class=\"btn btn-minier btn-yellow\">查看权限</button>";
  }*/

}

//查看评价
function viewAssess(assessId) {
  //加载数据
  $.ajax({
    type: "post",
    url: webroot + "assess/selectAssessByAssessId.do",
    data: {assessId: assessId},
    success: function (msg) {
      $("#viewAssessModal #inputProName").val(msg.data.product.proName);
      $("#viewAssessModal input[name='assessName']").val(msg.data.userName);
      $("#viewAssessModal #assessContent").val(msg.data.assessContent);
    }
  });
  //加载评价图片
  //加载imageedit js
  document.hs = new $.imageEditer("#viewAssessModal #imageEditer", {
    selector: "viewAssessModal",
    viewType: "view",
    url: webroot + "image/findImgByAssessId.do?assessId=" + assessId
  });
  $("#viewAssessModal .modal-content").css("height", "675px");
  $("#viewAssessModal").modal("show");
}

function findProName(obj) {
  var selector = null;
  if ("addAssessModal" == obj) {
    selector = "addAssessModal";
  } else if ("modifyAssessModal" == obj) {
    selector = "modifyAssessModal";
  }
  var proName = $("#" + selector + " #inputProName").val();
  if (empty(proName)) {
    alertmsg("warning", "请输入产品名称");
    return;
  }
  $("#" + selector + " #proResultDiv").html("");
  $.ajax({
    type: "post",
    url: webroot + "product/selectProductByParams.do",
    data: {"proName": proName},
    success: function (data) {
      var html = "<ul>";
      for (var i = 0; i < data.length; i++) {
        html += "<li id='" + data[i].id + "' title='" + data[i].proName + "'>" +
            data[i].proName +
            "</li>";
      }
      $("#" + selector + " #proResultDiv").append(html + "</ul>");
      $("#" + selector + " #proResultDiv").show();
      $("#" + selector + " #proResultDiv li").click(function (e) {
        $("#" + selector + " #inputProName").val($(this).attr("title"));
        $("#" + selector + " #inputProNameId").val($(this).attr("id"));
        $("#" + selector + " #proResultDiv").hide();
      });
    }
  });
}

//查询
function queryAssess() {
  var data = $("#queryAssessForm").serialize();
  var url = webroot + "assess/selectAssess.do";
  $("#grid-table").jqGrid('setGridParam', {
    url: url + "?" + data,
    //postData:jsonData,
    page: 1,
    mtype: "post"
  }).trigger("reloadGrid"); //重新载入
}

//删除评价
function deleteAssess() {
  var selectedIds = $("#grid-table").jqGrid("getGridParam", "selarrrow");//选择多行记录
  if (selectedIds.length < 1) {
    alertmsg("warning", "请至少选中一行!");
    return;
  }
  var ids = "";
  for (var i = 0; i < selectedIds.length; i++) {
    var rowData = $('#grid-table').getRowData(selectedIds[i]);//获取选中行的记录
    var assessId = rowData.assessId;
    ids = ids + assessId + (i < selectedIds.length - 1 ? "," : "");
  }
  Lobibox.confirm({
    title: "删除评价",      //提示框标题
    msg: "是否确认确认删除",   //提示框文本内容
    callback: function ($this, type, ev) {               //回调函数
      if (type === 'yes') {
        $.ajax({
          type: "post",
          url: webroot + "assess/deleteAssess.do",
          data: {"assessIds": ids},
          success: function (data) {
            //删除成功重新加载jqGrid
            $("#grid-table").jqGrid('setGridParam', {
              page: 1,
              mtype: "post"
            }).trigger("reloadGrid"); //重新载入
          }
        });
      } else if (type === 'no') {

      }
    }
  });
}

//添加评价
function addAssess() {
  //打开添加评价model之前回复之前的操作
  var addAssessModalHeight = $("#addAssessModal .modal-content").css("height");
  var heightArr = addAssessModalHeight.split("px");
  var heightInt = parseInt(heightArr[0]);
  if (heightInt > 450) {
    $("#addAssessModal #saveAssessButton").attr("disabled", false);
    var height = heightInt - 450;
    $("#addAssessModal .modal-content").css("height", height + "px");
    $("#addAssessModal #imgEditDiv").hide();
  }

  //加载imageedit js
  document.hs = new $.imageEditer("#addAssessModal #imageEditer", {
    selector: "addAssessModal",
    viewType: "edit",
    url: ""
  });
  //再次打开model之前清空上次的操作记录
  $("#addAssessModal :input").val("");
  $("#addAssessModal").modal("show");

}

//保存评价
function saveAssess() {
  var inputProNameId = $("#addAssessModal #inputProNameId").val();
  var assessName = $("#addAssessModal input[name='assessName']").val();
  var assessContent = $("#addAssessModal #assessContent").val();
  if (empty(inputProNameId)) {
    alertmsg("warning", "请选择商品！");
    return;
  }
  $.ajax({
    type: "post",
    url: webroot + "assess/saveAssess.do",
    data: {
      proId: inputProNameId,
      assessName: assessName,
      assessContent: assessContent
    },
    success: function (msg) {
      if (msg.success) {
        $("#addAssessModal #assessId").val(msg.returnId);
        $("#addAssessModal #saveAssessButton").attr("disabled", true);
        var addAssessModalHeight = $("#addAssessModal .modal-content").css(
            "height");
        var heightArr = addAssessModalHeight.split("px");
        var height = parseInt(heightArr[0]) + 450;
        $("#addAssessModal .modal-content").css("height", height + "px");
        $("#addAssessModal #imgEditDiv").show();
      } else {
        alertmsg("error", "保存评价失败！");
      }
    }
  })

}

//修改评价
function editAssess() {
  var lanId = $("#grid-table").jqGrid("getGridParam", "selrow");
  var rowData = $('#grid-table').getRowData(lanId);//获取选中行的记录
  var id = rowData.assessId;
  if (!isNoEmpty(id)) {
    alertmsg("warning", "请至少选中一行 !");
    return;
  }
  //加载数据
  $.ajax({
    type: "post",
    url: webroot + "assess/selectAssessByAssessId.do",
    data: {assessId: id},
    success: function (msg) {
      $("#modifyAssessModal #inputProName").val(msg.data.product.proName);
      $("#modifyAssessModal #inputProNameId").val(msg.data.product.id);
      $("#modifyAssessModal #assessId").val(msg.data.assessId);
      $("#modifyAssessModal input[name='assessName']").val(msg.data.userName);
      $("#modifyAssessModal #assessContent").val(msg.data.assessContent);
    }
  });
  //加载评价图片
  //加载imageedit js
  document.hs = new $.imageEditer("#modifyAssessModal #imageEditer", {
    selector: "modifyAssessModal",
    viewType: "edit",
    url: webroot + "image/findImgByAssessId.do?assessId=" + id
  });
  $("#modifyAssessModal .modal-content").css("height", "675px");
  $("#modifyAssessModal").modal("show");
}

//保存修改评价
function editAndSaveAssess() {
  var assessId = $("#modifyAssessModal #assessId").val();
  var inputProNameId = $("#modifyAssessModal #inputProNameId").val();
  var assessName = $("#modifyAssessModal input[name='assessName']").val();
  var assessContent = $("#modifyAssessModal #assessContent").val();
  if (empty(inputProNameId)) {
    alertmsg("warning", "请选择商品！");
    return;
  }
  $.ajax({
    type: "post",
    url: webroot + "assess/editAndSaveAssess.do",
    data: {
      proId: inputProNameId,
      assessName: assessName,
      assessContent: assessContent,
      assessId: assessId
    },
    success: function (msg) {
      if (msg.success) {
        alertmsg("success", "保存成功！");
      } else {
        alertmsg("error", "保存评价失败！");
      }
    }
  })
}

