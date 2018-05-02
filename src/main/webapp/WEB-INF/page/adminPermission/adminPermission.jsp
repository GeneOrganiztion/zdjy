<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
	String path = request.getContextPath();
%>
<script src="<%=path%>/pagejs/adminPermission/adminPermission.js"></script>
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
		<li><a href="#">系统管理</a></li>
		<li class="active"><span>用户权限</span></li>
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
						href="#searchDiv" onclick="change()"> 用户权限搜索<i
						class="fa fa-plus-square"></i></a>
				</div>
				<script>
					function change() {
						var flag = $("#searchTitle").attr("flag");
						if (flag == 'show') {
							$("#searchDiv").slideUp();
							$("#searchTitle").find("i").addClass(
									"fa-minus-square").removeClass(
									"fa-plus-square").end()
									.attr("flag", "hide");
						} else {
							$("#searchDiv").slideDown();
							$("#searchTitle").find("i").addClass(
									"fa-plus-square").removeClass(
									"fa-minus-square").end().attr("flag",
									"show");
						}
					}
				</script>
			</div>
			<div id="searchDiv" class="panel-collapse collapse">
				<div class="panel-body">
					<form id="queryAdminPermissionForm" class="form-horizontal"
						role="form">
						<!-- #section:elements.form -->
						<div class="form-group">
							<div class="col-md-3">
								<label class="col-sm-4 control-label no-padding-right"
									for="username">用户名称</label>
								<div class="col-sm-8">
									<input type="text" name="username" class="form-control" />
								</div>
							</div>
							<div class="col-md-3">
								<label class="col-sm-4 control-label no-padding-right"
									for="shortName">权限名称</label>
								<div class="col-sm-8">
									<input type="text" name="permName" class="form-control" />
								</div>
							</div>
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
						</div>
						<div class="form-group">
							<div class="col-md-12 text-right">
								<button type="button" class="btn btn-md"
									onclick="queryAdminPermission()">
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
		<button type="button" onclick="deleteAdminPermission()"
			class="btn-link">
			<i class="glyphicon glyphicon-remove"></i> &nbsp;删除用户权限
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
<script type="text/javascript">
	jQuery(function($) {
		initAdminPermissionManager();
	})
</script>

