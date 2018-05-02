<%@ page language="java" import="java.util.*" pageEncoding="utf-8" %>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://"
            + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
    <meta charset="utf-8"/>
    <title></title>

    <meta name="description" content="User login page"/>
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, maximum-scale=1.0"/>

    <!-- bootstrap & fontawesome -->
    <link rel="stylesheet" href="<%=path%>/assets/css/bootstrap.css"/>
    <link rel="stylesheet" href="<%=path%>/assets/css/font-awesome.css"/>

    <!-- text fonts -->
    <link rel="stylesheet" href="<%=path%>/assets/css/ace-fonts.css"/>

    <!-- ace styles -->
    <link rel="stylesheet" href="<%=path%>/assets/css/ace.css"/>

    <link rel="stylesheet" href="<%=path%>/plugins/css/lobibox.css"/>


    <link rel="stylesheet" href="<%=path%>/assets/css/jquery.idcode.css"/>
    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=path%>assets/css/ace-part2.css"/>
    <![endif]-->
    <link rel="stylesheet" href="<%=path%>/assets/css/ace-rtl.css"/>


    <!--[if lte IE 9]>
    <link rel="stylesheet" href="<%=path%>/assets/css/ace-ie.css"/>
    <![endif]-->

    <!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

    <!--[if lt IE 9]>
    <script src="<%=path%>/assets/js/html5shiv.js"></script>
    <script src="<%=path%>/assets/js/respond.js"></script>
    <![endif]-->
    <!-- <style type="text/css">
            .light-login {
                body: #dfe0e2 url(/images/pattern.jpg) repeat;
                }
            </style> -->

</head>

<body class="login-layout light-login">
<div class="main-container">
    <div class="main-content">
        <div class="row">
            <div class="col-sm-10 col-sm-offset-1">
                <div class="login-container">
                    <div class="center">
                        <h1>
                            <i class="ace-icon fa fa-leaf green"></i> <span class="red">纵达酒业</span>
                        </h1>
                        <h4 class="blue" id="id-company-text">&copy; 后台管理系统</h4>
                    </div>

                    <div class="space-6"></div>

                    <div class="position-relative">
                        <div id="login-box"
                             class="login-box visible widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header red lighter bigger">
                                        <div id="errorMsg">
                                            <c:if test="${msg != null }">${msg }</c:if>
                                        </div>
                                    </h4>

                                    <div class="space-6"></div>

                                    <form id="loginForm"
                                          action="<%=basePath%>login.do" method="post">
                                        <fieldset>
                                            <label class="block clearfix"> <span
                                                    class="block input-icon input-icon-right"> <input
                                                    type="text" class="form-control" name="user"
                                                    id="user"/>
														<i class="ace-icon fa fa-user"></i>
												</span>
                                            </label> <label class="block clearfix"> <span
                                                class="block input-icon input-icon-right"> <input
                                                type="password" class="form-control" name="psd"
                                                id="psd"/>
														<i class="ace-icon fa fa-lock"></i>
												</span>
                                        </label>

                                            <div class="space"></div>
                                            <%--验证码--%>
                                            <label class="block clearfix form-group">
														<span class="block help-validate">
															<input id="captcha" name="captcha"
                                                                   type="text" placeholder="验证码"
                                                                   required="true"/>
															<img alt="" src="<%=path%>/captcha.do">
														</span>
                                            </label>
                                            <div class="clearfix">
                                                <div class="center">
                                                    <input id="saveCookie" type="checkbox"
                                                           onclick="saveUserInfo()"/>
                                                    <span style="font-size:14px; color:blue;">记住密码</span>
                                                    <button type="button" id="loginBtn"
                                                            class="width-35 pull-right btn btn-sm btn-primary">
                                                        <i class="ace-icon fa fa-key"></i> <span
                                                            class="bigger-110">登录</span>
                                                    </button>
                                                </div>
                                            </div>

                                            <div class="space-4"></div>
                                        </fieldset>
                                    </form>

                                    <!-- <div class="social-or-login center">
                                            <span class="bigger-110">Or Login Using</span>
                                        </div>
-->
                                    <div class="space-6"></div>

                                    <!-- <div class="social-login center">
                                            <a class="btn btn-primary">
                                                <i class="ace-icon fa fa-facebook"></i>
                                            </a>

                                            <a class="btn btn-info">
                                                <i class="ace-icon fa fa-twitter"></i>
                                            </a>

                                            <a class="btn btn-danger">
                                                <i class="ace-icon fa fa-google-plus"></i>
                                            </a>
                                        </div> -->
                                </div>
                                <!-- /.widget-main -->

                                <!-- <div class="toolbar clearfix">
                                        <div>
                                            <a href="#" data-target="#forgot-box" class="forgot-password-link">
                                                <i class="ace-icon fa fa-arrow-left"></i>
                                                I forgot my password
                                            </a>
                                        </div>

                                        <div>
                                            <a href="#" data-target="#signup-box" class="user-signup-link">
                                                I want to register
                                                <i class="ace-icon fa fa-arrow-right"></i>
                                            </a>
                                        </div>
                                    </div> -->
                            </div>
                            <!-- /.widget-body -->
                        </div>
                        <!-- /.login-box -->

                        <div id="forgot-box" class="forgot-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header red lighter bigger">
                                        <i class="ace-icon fa fa-key"></i> Retrieve Password
                                    </h4>

                                    <div class="space-6"></div>
                                    <p>Enter your email and to receive instructions</p>

                                    <form>
                                        <fieldset>
                                            <label class="block clearfix"> <span
                                                    class="block input-icon input-icon-right"> <input
                                                    type="email" class="form-control"
                                                    placeholder="Email"/>
														<i class="ace-icon fa fa-envelope"></i>
												</span>
                                            </label>

                                            <div class="clearfix">
                                                <button type="button"
                                                        class="width-35 pull-right btn btn-sm btn-danger">
                                                    <i class="ace-icon fa fa-lightbulb-o"></i> <span
                                                        class="bigger-110">Send Me!</span>
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>
                                <!-- /.widget-main -->

                                <div class="toolbar center">
                                    <a href="#" data-target="#login-box"
                                       class="back-to-login-link"> Back to login <i
                                            class="ace-icon fa fa-arrow-right"></i>
                                    </a>
                                </div>
                            </div>
                            <!-- /.widget-body -->
                        </div>
                        <!-- /.forgot-box -->

                        <div id="signup-box" class="signup-box widget-box no-border">
                            <div class="widget-body">
                                <div class="widget-main">
                                    <h4 class="header green lighter bigger">
                                        <i class="ace-icon fa fa-users blue"></i> New User
                                        Registration
                                    </h4>

                                    <div class="space-6"></div>
                                    <p>Enter your details to begin:</p>

                                    <form>
                                        <fieldset>
                                            <label class="block clearfix"> <span
                                                    class="block input-icon input-icon-right"> <input
                                                    type="email" class="form-control"
                                                    placeholder="Email"/>
														<i class="ace-icon fa fa-envelope"></i>
												</span>
                                            </label> <label class="block clearfix"> <span
                                                class="block input-icon input-icon-right"> <input
                                                type="text" class="form-control"
                                                placeholder="Username"/>
														<i class="ace-icon fa fa-user"></i>
												</span>
                                        </label> <label class="block clearfix"> <span
                                                class="block input-icon input-icon-right"> <input
                                                type="password" class="form-control"
                                                placeholder="Password"/> <i
                                                class="ace-icon fa fa-lock"></i>
												</span>
                                        </label> <label class="block clearfix"> <span
                                                class="block input-icon input-icon-right"> <input
                                                type="password" class="form-control"
                                                placeholder="Repeat password"/> <i
                                                class="ace-icon fa fa-retweet"></i>
												</span>
                                        </label> <label class="block"> <input type="checkbox"
                                                                              class="ace"/> <span
                                                class="lbl"> I accept the <a
                                                href="#">User Agreement</a>
												</span>
                                        </label>

                                            <div class="space-24"></div>

                                            <div class="clearfix">
                                                <button type="reset"
                                                        class="width-30 pull-left btn btn-sm">
                                                    <i class="ace-icon fa fa-refresh"></i> <span
                                                        class="bigger-110">Reset</span>
                                                </button>

                                                <button type="button"
                                                        class="width-65 pull-right btn btn-sm btn-success">
                                                    <span class="bigger-110">Register</span> <i
                                                        class="ace-icon fa fa-arrow-right icon-on-right"></i>
                                                </button>
                                            </div>
                                        </fieldset>
                                    </form>
                                </div>

                                <div class="toolbar center">
                                    <a href="#" data-target="#login-box"
                                       class="back-to-login-link"> <i
                                            class="ace-icon fa fa-arrow-left"></i> Back to login
                                    </a>
                                </div>
                            </div>
                            <!-- /.widget-body -->
                        </div>
                        <!-- /.signup-box -->
                    </div>
                    <!-- /.position-relative -->

                    <!-- <div class="navbar-fixed-top align-right">
                            <br />
                            &nbsp;
                            <a id="btn-login-dark" href="#">Dark</a>
                            &nbsp;
                            <span class="blue">/</span>
                            &nbsp;
                            <a id="btn-login-blur" href="#">Blur</a>
                            &nbsp;
                            <span class="blue">/</span>
                            &nbsp;
                            <a id="btn-login-light" href="#">Light</a>
                            &nbsp; &nbsp; &nbsp;
                        </div>  -->
                </div>
            </div>
            <!-- /.col -->
        </div>
        <!-- /.row -->
    </div>
    <!-- /.main-content -->
</div>
<!-- /.main-container -->

<!-- basic scripts -->

<!--[if !IE]> -->
<script type="text/javascript">
  window.jQuery || document.write("<script src='<%=path%>/assets/js/jquery.js'>" + "<"
      + "/script>");
</script>

<!-- <![endif]-->

<!--[if IE]>
<script type="text/javascript">
    window.jQuery || document.write("<script src='../assets/js/jquery1x.js'>" + "<" + "/script>");
</script>
<![endif]-->
<script type="text/javascript">
  if ('ontouchstart'
      in document.documentElement) document.write("<script src='<%=path%>/assets/js/jquery.mobile.custom.js'>"
      + "<" + "/script>");
</script>
<script src="<%=path%>/assets/js/jquery.js"></script>
<script src="<%=path%>/assets/js/jquery.cookie.js"></script>
<script src="<%=path%>/assets/js/jquery.idcode.js"></script>
<script src="<%=path%>/plugins/js/lobibox.js"></script>
<script src="<%=path%>/pagejs/util.js"></script>
<!-- inline scripts related to this page -->
<script type="text/javascript">
  jQuery(function ($) {
    if ($.cookie("rmbUser") == "true") {
      $("#rmbUser").attr("checked", true);
      $("#user").val($.cookie("userName"));
      $("#psd").val($.cookie("passWord"));
    }
    $(document).on('click', '.toolbar a[data-target]', function (e) {
      e.preventDefault();
      var target = $(this).data('target');
      $('.widget-box.visible').removeClass('visible');//hide others
      $(target).addClass('visible');//show target
    });

    //$.idcode.setCode();   //加载生成验证码方法
    /* $("#butn").click(function(){
        var IsBy = $.idcode.validateCode()  //调用返回值，返回值结果为true或者false
        if(IsBy){
            alert("验证码输入正确")
        }else {
            alert("请重新输入")
        }
    }) */

    $("#loginBtn").click(function () {
      $("#errorMsg").html("");
      var user = $("#user").val();
      var psd = $("#psd").val();
      var captcha = $("#captcha").val();
      if (empty(user) || empty(psd)) {
        $("#errorMsg").append("用户名或密码不能为空");
        return;
      }
      if(empty(captcha)){
        $("#errorMsg").append("验证码不能为空");
        return;
      }
      $("#loginForm").submit();
    });

  });

  function saveUserInfo() {
    if ($("#saveCookie").is(':checked')) {
      var userName = $("#user").val();
      var passWord = $("#psd").val();
      console.log("userName:" + userName + "------" + "passWord:" + passWord);
      $.cookie("rmbUser", "true", {expires: 7}); // 存储一个带7天期限的 cookie
      $.cookie("userName", userName, {expires: 7}); // 存储一个带7天期限的 cookie
      $.cookie("passWord", passWord, {expires: 7}); // 存储一个带7天期限的 cookie
    } else {
      $.cookie("rmbUser", "false", {expires: -1});        // 删除 cookie
      $.cookie("userName", '', {expires: -1});
      $.cookie("passWord", '', {expires: -1});
    }
  }

</script>
</body>
</html>
