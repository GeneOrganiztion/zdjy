<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/assess/assess.js"></script>
<!-- #section:basics/content.breadcrumbs -->
<div class="breadcrumbs" id="breadcrumbs">
    <script type="text/javascript">
      try {
        ace.settings.check('breadcrumbs', 'fixed')
      } catch (e) {
      }
    </script>

    <ul class="breadcrumb">
        <!-- <li>
                <div class="tip-box">
                    <div class="tip-title">位置</div>
                    <div class=tip-arrow-breadcrumb></div>
                </div>
            </li> -->
        <li><i class="ace-icon fa fa-home home-icon"></i><a href="#">首页</a></li>
        <li><a href="#">评价管理</a></li>
        <li class="active"><span>评价操作</span></li>
    </ul>
    <!-- /.breadcrumb -->
</div>
<!-- /section:basics/content.breadcrumbs -->

<div class="page-content">
    <!-- #section:settings.box -->
    <div class="panel-group accordion" id="accordion">
        <div class="panel panel-default">
            <div class="panel-heading">
                <div class="panel-title" flag="show" id="searchTitle">
                    <a data-toggle="collapse" data-parent="#accordion"
                       href="#searchDiv" onclick="change()"> 评价搜索<i
                            class="fa fa-plus-square"></i></a>
                </div>
                <script>
                  function change() {
                    var flag = $("#searchTitle").attr("flag");
                    if (flag == 'show') {
                      $("#searchDiv").slideUp();
                      $("#searchTitle").find("i").addClass("fa-minus-square").removeClass(
                          "fa-plus-square").end().attr("flag", "hide");
                    } else {
                      $("#searchDiv").slideDown();
                      $("#searchTitle").find("i").addClass("fa-plus-square").removeClass(
                          "fa-minus-square").end().attr("flag", "show");
                    }
                  }
                </script>
            </div>
            <div id="searchDiv" class="panel-collapse collapse">
                <div class="panel-body">
                    <form id="queryAssessForm" class="form-horizontal" role="form">
                        <!-- #section:elements.form -->
                        <div class="form-group">
                            <div class="col-md-3">
                                <label class="col-sm-4 control-label no-padding-right"
                                       for="proName">产品名称</label>
                                <div class="col-sm-8">
                                    <input type="text" name="proName" class="form-control"/>
                                </div>
                            </div>
                        </div>
                        <div class="form-group">
                            <div class="col-md-3">
                                <label class="col-sm-4 control-label no-padding-right"
                                       for="beginTime">开始时间 </label>
                                <div class="col-sm-8 input-group date form_date date-picker"
                                     data-date="" data-date-format="yyyy-mm-dd">
                                    <input type="text" name="beginTime" id="beginTime"
                                           class="form-control date-select"> <span
                                        class="input-group-addon"> <i class="fa fa-calendar"></i>
									</span>
                                </div>
                            </div>

                            <div class="col-md-3" style="margin-bottom: 15px;">
                                <label class="col-sm-4 control-label no-padding-right"
                                       for="endTime"> 结束时间 </label>
                                <div class="col-sm-8 input-group date form_date date-picker"
                                     data-date="" data-date-format="yyyy-mm-dd">
                                    <input type="text" name="endTime" id="endTime"
                                           class="form-control date-select"> <span
                                        class="input-group-addon"> <i class="fa fa-calendar"></i>
									</span>
                                </div>
                            </div>
                            <div class="col-md-6 text-right">
                                <button type="button" class="btn btn-md" onclick="queryAssess()">
                                    <i class="ace-icon fa fa-search orange"></i>搜索
                                </button>
                                <button type="button" class="btn btn-md" onclick="reset()">
                                    <i class="ace-icon fa fa-repeat"></i>重置
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
    <div id="actions-before" class="btn-group">
        <button type="button" onclick="addAssess()" class="btn-link">
            <i class="glyphicon glyphicon-plus"></i> &nbsp;添加
        </button>
        <button type="button" onclick="editAssess()" class="btn-link">
            <i class="glyphicon glyphicon-edit"></i> &nbsp;修改
        </button>
        <button type="button" onclick="deleteAssess()" class="btn-link">
            <i class="glyphicon glyphicon-remove"></i> &nbsp;删除评价
        </button>
    </div>
    <div class="row">
        <div class="col-xs-12">
            <table id="grid-table"></table>

            <div id="grid-pager"></div>
            <!-- PAGE CONTENT ENDS -->
        </div>
        <!-- /.col -->
    </div>
    <!-- /.row -->
</div>
<!-- /.page-content -->
<!-- 添加评价model start -->
<div class="modal fade" id="addAssessModal" tabindex="-1" role="dialog"
     aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>
                <h4 class="modal-title">添加评价</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal " id="addAssessform">
                    <div class="form-group">
                        <label class="control-label col-xs-12 col-sm-4 no-padding-right"
                               for="proName">商品名称</label>
                        <div class="col-xs-12 col-sm-5">
                            <div class="input-group">
                                <input type="text" id="inputProName" autocomplete="off"
                                       class="form-control search-query" placeholder="请输入名称"/>
                                <input id="inputProNameId" type="hidden"/>
                                <input id="assessId" type="hidden"/>
                                <span class="input-group-btn">
									<button type="button" class="btn btn-purple btn-sm"
                                            onclick="findProName('addAssessModal')">
										查询
									</button>
								</span>
                            </div>
                            <div id="proResultDiv">
                            </div>
                        </div>
                    </div>
                    <div class="space-2"></div>
                    <div class="form-group">
                        <label class="control-label col-xs-12 col-sm-4 no-padding-right"
                               for="realname">评价人名</label>
                        <div class="col-xs-12 col-sm-8">
                            <div class="clearfix">
                                <input type="text" autocomplete="off" name="assessName"
                                       class="col-xs-12 col-sm-6"/>
                            </div>
                        </div>
                    </div>
                    <div class="space-2"></div>
                    <div class="form-group">
                        <label class="control-label col-xs-12 col-sm-4 no-padding-right"
                               for="realname">评价内容</label>
                        <div class="col-xs-12 col-sm-8">
                            <div class="clearfix">
                                <textarea class="form-control" id="assessContent"
                                          class="col-xs-12 col-sm-6" placeholder="请输入评价"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12 col-sm-12 text-center">
                            <button type="button" id="saveAssessButton" class="btn btn-md"
                                    onclick="saveAssess()">保存
                            </button>
                        </div>
                    </div>
                </form>
                <div id="imgEditDiv" class="col-lg-12 text-center" style="display:none">
                    <div id="imageEditer"></div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<!-- 修改评价model start -->
<div class="modal fade" id="modifyAssessModal" tabindex="-1" role="dialog"
     aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>
                <h4 class="modal-title">修改评价</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal " id="addAssessform">
                    <div class="form-group">
                        <label class="control-label col-xs-12 col-sm-4 no-padding-right"
                               for="proName">商品名称</label>
                        <div class="col-xs-12 col-sm-5">
                            <div class="input-group">
                                <input type="text" id="inputProName" autocomplete="off"
                                       class="form-control search-query" placeholder="请输入名称"/>
                                <input id="inputProNameId" type="hidden"/>
                                <input id="assessId" type="hidden"/>
                                <span class="input-group-btn">
									<button type="button" class="btn btn-purple btn-sm"
                                            onclick="findProName('modifyAssessModal')">
										查询
									</button>
								</span>
                            </div>
                            <div id="proResultDiv">
                            </div>
                        </div>
                    </div>
                    <div class="space-2"></div>
                    <div class="form-group">
                        <label class="control-label col-xs-12 col-sm-4 no-padding-right"
                               for="realname">评价人名</label>
                        <div class="col-xs-12 col-sm-8">
                            <div class="clearfix">
                                <input type="text" autocomplete="off" name="assessName"
                                       class="col-xs-12 col-sm-6"/>
                            </div>
                        </div>
                    </div>
                    <div class="space-2"></div>
                    <div class="form-group">
                        <label class="control-label col-xs-12 col-sm-4 no-padding-right"
                               for="realname">评价内容</label>
                        <div class="col-xs-12 col-sm-8">
                            <div class="clearfix">
                                <textarea class="form-control" id="assessContent"
                                          class="col-xs-12 col-sm-6" placeholder="请输入评价"></textarea>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-xs-12 col-sm-12 text-center">
                            <button type="button" id="saveAssessButton" class="btn btn-md"
                                    onclick="editAndSaveAssess()">保存
                            </button>
                        </div>
                    </div>
                </form>
                <div id="imgEditDiv" class="col-lg-12 text-center">
                    <div id="imageEditer"></div>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal -->
</div>

<!-- 查看评价model start -->
<div class="modal fade" id="viewAssessModal" tabindex="-1" role="dialog"
     aria-labelledby="modalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">&times;
                </button>
                <h4 class="modal-title">修改评价</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal " id="addAssessform">
                    <div class="form-group">
                        <label class="control-label col-xs-12 col-sm-4 no-padding-right"
                               for="proName">商品名称</label>
                        <div class="col-xs-12 col-sm-5">
                            <div class="input-group">
                                <input type="text" id="inputProName" disabled="true"
                                       class="form-control search-query"/>
                            </div>
                            <div id="proResultDiv">
                            </div>
                        </div>
                    </div>
                    <div class="space-2"></div>
                    <div class="form-group">
                        <label class="control-label col-xs-12 col-sm-4 no-padding-right"
                               for="realname">评价人名</label>
                        <div class="col-xs-12 col-sm-8">
                            <div class="clearfix">
                                <input type="text" disabled="true" name="assessName"
                                       class="col-xs-12 col-sm-6"/>
                            </div>
                        </div>
                    </div>
                    <div class="space-2"></div>
                    <div class="form-group">
                        <label class="control-label col-xs-12 col-sm-4 no-padding-right"
                               for="realname">评价内容</label>
                        <div class="col-xs-12 col-sm-8">
                            <div class="clearfix">
                                <textarea class="form-control" id="assessContent" readonly="true"
                                          class="col-xs-12 col-sm-6" placeholder="请输入评价"></textarea>
                            </div>
                        </div>
                    </div>
                </form>
                <div id="imgEditDiv" class="col-lg-12 text-center">
                    <div id="imageEditer"></div>
                </div>
            </div>
        </div>
    </div>
</div>

<div id="modalDialog"></div>
<script type="text/javascript">
  jQuery(function ($) {
    initAssessManager();
  })
</script>
<style>
    #proResultDiv {
        background-color: #f1f1f1;
        overflow: auto;
        overflow-x: hidden;
        height: 60px;
        cursor: default;
        display: none
    }

    #proResultDiv ul {
        margin-left: auto;
        margin-left: 2px;
    }

    #proResultDiv li {
        width: 208px;
        white-space: nowrap;
        text-overflow: ellipsis;
        overflow: hidden;
    }

    #proResultDiv li:hover {
        background-color: #888;
        color: #fff;
    }
</style>
