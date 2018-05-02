function initAdminManager() {

  $("#SelectProductModol").draggable();
  $("#editProductModal").draggable();

  //查询分类
  //selectClassify();
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

    url: webroot + "product/selectProduct.do",
    mtype: 'post',
    datatype: "json",
    height: 370,
    colNames: ['商品ID', '商品名称', '商品原价', '商品折扣价', '商品库存', '是否上线', '销量', '最后更新时间',
      ''],
    colModel: [
      {
        name: 'id',
        index: 'product_id',
        width: 80,
        sorttype: "int",
        editable: true
      },
      {name: 'proName', index: 'pro_name', width: 80, editable: true},
      {
        name: 'productPrice',
        index: 'product_price',
        width: 80,
        sorttype: "int",
        editable: true
      },
      {
        name: 'proRateprice',
        index: 'pro_rateprice',
        width: 80,
        sorttype: "int",
        editable: true
      },
      {
        name: 'proSum',
        index: 'pro_sum',
        width: 80,
        sorttype: "int",
        editable: true
      },
      {
        name: 'proOnline',
        index: 'pro_online',
        width: 80,
        editable: true,
        sortable: false,
        formatter: formatterProductIsOnline
      },
      {
        name: 'selNumber',
        index: 'sel_number',
        width: 80,
        sorttype: "int",
        editable: true
      },
      {
        name: 'lastModifiedTime',
        index: 'last_modified_time',
        width: 80,
        formatter: formatDate
      },
      {name: 'proDetail', index: 'pro_detail', width: 80, hidden: true}
    ],
    viewrecords: true,
    rowNum: 10,
    rowList: [10, 20, 30],
    loadonce: false,
    pager: pager_selector,
    altRows: true,
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

  function formatterProductIsOnline(cellvalue, options, rowObject) {
    switch (cellvalue) {
      case true:
        return "是";
        break;
      case false:
        return "否";
        break;
      default:
        return null;
        break;
    }
  }

  $('#addProductform').validate({
    errorElement: 'div',
    errorClass: 'help-block',
    focusInvalid: false,
    ignore: "",
    rules: {
      proName: {
        required: true,
        maxlength: 10
      },
      proHead: {
        required: true,
        maxlength: 20
      },
      classify_product: {
        required: true
      },
      productPrice: {
        required: true,
        digits: true,
        min: 0
      },
      proRateprice: {
        required: true,
        digits: true,
        min: 0
      },
      isonline_product: {
        required: true
      },
      proSum: {
        required: true,
        digits: true,
        min: 1
      },
    },

    messages: {

      proName: {
        required: "商品名称为必填字段",
        maxlength: "商品名称不能超过10个字符"
      },
      proHead: {
        required: "商品标题为必填字段",
        maxlength: "商品标题不能超过20个字符"
      },
      productPrice: {
        required: "商品价格为必填字段",
        digits: "商品价格必须正为整数",
        min: "商品价格不能小于0"
      },
      proRateprice: {
        required: "商品折扣价为必填字段",
        digits: "商品折扣价必须为正整数",
        min: "商品折扣价不能小于0"
      },
      isonline_product: {
        required: "请选择商品状态"
      },
      proSum: {
        required: "商品库存为必填字段",
        digits: "商品库存不能小于1",
        min: "商品库存不能小于1"
      },
      state: "Please choose state",
      subscription: "Please choose at least one option",
      classify_product: "请选择商品类别  如商品类别列表为空请先添加商品类别",
    },

    highlight: function (e) {
      $(e).closest('.form-group').removeClass('has-info').addClass('has-error');
    },

    success: function (e) {
      $(e).closest('.form-group').removeClass('has-error');//.addClass('has-info');
      $(e).remove();
    },

    errorPlacement: function (error, element) {
      if (element.is('input[type=checkbox]') || element.is(
              'input[type=radio]')) {
        var controls = element.closest('div[class*="col-"]');
        if (controls.find(':checkbox,:radio').length > 1) {
          controls.append(
              error);
        } else {
          error.insertAfter(element.nextAll('.lbl:eq(0)').eq(0));
        }
      }
      else if (element.is('.select2')) {
        error.insertAfter(
            element.siblings('[class*="select2-container"]:eq(0)'));
      }
      else if (element.is('.chosen-select')) {
        error.insertAfter(
            element.siblings('[class*="chosen-container"]:eq(0)'));
      }
      else {
        error.insertAfter(element.parent());
      }
    },

    submitHandler: function (form) {
    },
    invalidHandler: function (form) {
    }
  });

}

//查询
function queryProduct() {
  var data = $("#queryAssetForm").serialize();
  data = decodeURIComponent(data);
  console.log("data=" + data);
  var url = webroot + "product/selectProduct.do";
  $("#grid-table").jqGrid('setGridParam', {
    url: url + "?" + data,
    //postData:jsonData,
    page: 1,
    mtype: "post"
  }).trigger("reloadGrid"); //重新载入
}

//删除用户
function deleteProduct() {

  var selectedIds = $("#grid-table").jqGrid("getGridParam", "selarrrow");//选择多行记录
  if (selectedIds.length < 1) {
    alertmsg("warning", "请至少选中一行!");
    return;
  }
  var ids = "";
  for (var i = 0; i < selectedIds.length; i++) {
    var rowData = $('#grid-table').getRowData(selectedIds[i]);//获取选中行的记录
    var ProductId = rowData.id;
    ids = ids + ProductId + ",";
  }
  Lobibox.confirm({
    title: "删除商品",      //提示框标题
    msg: "是否确认确认删除",   //提示框文本内容
    callback: function ($this, type, ev) {               //回调函数
      if (type === 'yes') {
        $.ajax({
          type: "post",
          url: webroot + "product/deleteproduct.do",
          data: {"ProductId": ids},
          success: function (data) {
            //删除成功重新加载jqGrid
            if (data) {
              alertmsg("success", "删除成功");
              $("#grid-table").jqGrid('setGridParam', {
                page: 1,
                mtype: "post"
              }).trigger("reloadGrid"); //重新载入
            }
          }
        });
      } else if (type === 'no') {

      }
    }
  });

}

//查询所有分类
//function selectClassify(){
//	$.ajax({
//		type: "post",
//		contentType: "application/x-www-form-urlencoded; charset=utf-8",
//		url: webroot + "classify/webclsall.do",
//		success: function(msg){
//			var classify = eval(msg);
//			showLocation(classify);
//			var objSelect1 = $('#classify1');
//			for(var i=0;i<classify.length;i++){
//				objSelect1.append("<optgroup label='"+classify[i].name+"/一级分类"+"'>");			
//				for(var j=0;j<classify[i].row.length;j++){
//					objSelect1.append("<option value='"+classify[i].row[j].id+"'>"+classify[i].row[j].claName+"</option>");
//				}
//				objSelect1.append("</optgroup>");
//			}
//			
//			$('#classify1').trigger("chosen:updated");
//			$('#validation-form').removeClass('hide');
//		}
//	});
//}
//预览商品
function SelectProduct() {
  var lanId = $("#grid-table").jqGrid("getGridParam", "selrow");
  if (!isNoEmpty(lanId)) {
    alertmsg("warning", "请至少选中一行 !");
    return;
  }
  var rowData = $('#grid-table').getRowData(lanId);//获取选中行的记录
  var id = rowData.id;
  console.log("rowData=" + rowData.proDetail);
  /*console.log("rowData="+rowData.productPrice);*/
  document.hs = new $.imageEditer("#imageEditer", {
    selector: "aaa",
    viewType: "view",
    url: webroot + "product/selectImageProduct.do?ProductId=" + id
  });

  //再次打开model之前清空上次的操作记录
  $("#SelectProductModol :input").val("");
  $("#productcontent").html(rowData.proDetail);
  $("#proName").html(rowData.proName + "展示图片以及详情预览");

  $("#SelectProductModol").modal("show");

}

//修改商品
function editProduct() {
  var lanId = $("#grid-table").jqGrid("getGridParam", "selrow");
  if (!isNoEmpty(lanId)) {
    alertmsg("warning", "请至少选中一行 !");
    return;
  }
  var rowData = $('#grid-table').getRowData(lanId);//获取选中行的记录
  var id = rowData.id;
  $.ajax({
    type: "post",
    url: webroot + "product/selectOneProduct.do",
    data: {proId: id},
    success: function (msg) {
      if (msg.proOnline == true) {
        msg.proOnline = 1;
      } else {
        msg.proOnline = 0;
      }

      $("#editProductModal input[name='id']").val(msg.id);
      $("#editProductModal input[name='name']").val(msg.proName);
      $("#editProductModal input[name='head']").val(msg.proHead);
      $("#editProductModal input[name='price']").val(msg.productPrice);
      $("#editProductModal input[name='rateprice']").val(msg.proRateprice);
      $("#editProductModal input[name='area']").val(msg.proArea);
      $("#editProductModal input[name='sum']").val(msg.proSum);
      $("#isonlinePro").val(msg.proOnline.toString());
      $("#ishotPro").val(msg.ishotsell.toString());
      $("#classify1").val(msg.classifyId);
      $("#editProductModal").modal("show");
    }
  });
}

function saveProduct() {
  if (!($('#addProductform').valid())) {
    return;
  }
  var data = $("#addProductform").serialize();
  console.log(data);
  $.ajax({
    type: "post",
    url: webroot + "product/updateProduct.do",
    data: data,
    success: function (msg) {
      if (msg) {
        alertmsg("success", "修改成功");
        $("#editProductModal").modal("hide");
        $("#grid-table").jqGrid('setGridParam', {
          page: 1,
          mtype: "post"
        }).trigger("reloadGrid"); //重新载入
      }
    }
  });
}
