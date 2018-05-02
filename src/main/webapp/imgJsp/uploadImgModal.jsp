<%@ page language="java" pageEncoding="UTF-8" contentType="text/html;charset=utf-8" %>
<%--<%@ include file="/common/taglibs.jsp"%>
<link rel="stylesheet" href="<c:url value='/ace/plugins/css/ztree/zTreeStyle.css'/>"/>

<script src="<c:url value='/ace/plugins/js/ztree/jquery.ztree.core-3.5.js'/>"></script>
<script src="<c:url value='/ace/plugins/js/ztree/jquery.ztree.excheck-3.5.js'/>"></script>
<script src="<c:url value='/ace/plugins/js/ztree/jquery.ztree.exhide-3.5.js'/>"></script>--%>
<style>
    @media screen and (max-width: 1366px) {
        .modal-dialog {
            margin: 50px auto;
        }
    }

    .select_uploadimgmodal {
        margin: 0;
        max-height: 230px;
        overflow-y: auto;
    }

    .select_uploadimgmodal li {
        list-style-type: none;
        border: 1px solid #AAA;
        padding: 2px;
        background-color: #FFF;
        float: left;
    }

    .select_uploadimgmodal img {
        height: 80px;
    }

    .select_uploadimgmodal div {
        background-color: #d0021b;
        border: 2px solid #FFF;
        color: #FFF;
        text-align: center;
        height: 28px;
        cursor: pointer;
    }

    .select_uploadimgmodal div:hover {
        background-color: #ff6900;
    }

    .name_uploadimgmodal {
        width: 110px;
    }

    .img_uploadimgmodal {
        max-width: 500px;
        max-height: 400px;
    }

    .span_uploadimgmodal {
        display: block;
        float: left;
        margin: 1px;
        cursor: pointer;
    }

    #selectDiv {
        height: 120px;
        overflow-y: auto;
    }

    .widget-footer {
        padding-top: 15px;
        padding-bottom: 15px;
    }

    .widget-footer > button:first-child {
        color: #fff !important;
        background-color: #ff6900 !important;
    }

    #ftpFileSearchButton:hover {
        font-size: 150% !important;
        cursor: pointer;
    }
</style>
<input type="hidden" id="imgContextPath" value="<%=request.getContextPath()%>/"/>
<%--<input type="hidden" id="imgSourceDir" value="source/<%=MamConstants.FTP_LINK%>/"/>--%>
<%-- 图片保存路径 --%>
<input type="hidden" id="imgStoreDir" value="store/asset/"/>
<input type="hidden" id="imgSelector" value="<%=request.getParameter("selector")%>"/>

<!-- 添加图片框-->
<div class="modal small fade" id="uploadimgmodal" tabindex="-2" role="dialog"
     aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width: 1000px;">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">
                    &times;
                </button>
                <h4 class="modal-title" id="modalLabel">添加图片</h4>
            </div>
            <div class="modal-body no-padding-bottom clearfix">
                <div class="col-md-7">
                    <div class="widget-box transparent">
                        <div class="widget-body no-border">
                            <div class="widget-main no-padding">
                                <div class="tabbable" id="uploadImgDiv">
                                    <ul class="nav nav-tabs" id="myTab">
                                        <li class="active">
                                            <a data-toggle="tab" href="#home"
                                               onclick="$('#nav-search').hide();">
                                                <i class="ace-icon fa fa-home orange bigger-125"></i>
                                                本地上传
                                            </a>
                                        </li>

                                        <%--<li>
                                            <a data-toggle="tab" href="#ftpContent" id="ftpTab" onclick="$('#nav-search').show();">
                                                <i class="green ace-icon fa fa-cloud orange bigger-120"></i>
                                                FTP选取
                                            </a>
                                        </li>
                                        <li>
                                            <a data-toggle="tab" href="#videoContent" id="videoTab" onclick="$('#nav-search').hide();">
                                                <i class="green ace-icon fa fa-film orange bigger-120"></i>
                                                视频截取
                                            </a>
                                        </li>
                                        <li style="float:right;display:none;" id="nav-search">
                                            <div class="nav-search">
                                                <span class="input-icon">
                                                    <input type="hidden" id="fileResPathIpt">
                                                    <input type="text" placeholder="文件名或路径(/999000/pic)" class="nav-search-input" style="padding-left: 6px;padding-right: 24px;width:220px;" id="ftpFileSearchText" autocomplete="off">
                                                    <i class="ace-icon fa fa-search nav-search-icon" style="left:initial; right:3px;" id="ftpFileSearchButton"></i>
                                                </span>
                                            </div>
                                        </li>--%>
                                    </ul>

                                    <div class="tab-content no-padding">
                                        <div id="home" class="tab-pane fade in active">
                                            <div class="row center clearfix">
                                                <div class='col-xs-12 space-32'></div>
                                                <div class="col-xs-12">
                                                    <i class="ace-icon fa fa-circle green"></i>
                                                    选择本地图片
                                                    <div class='space-6'></div>
                                                    <div class="col-md-12">
                                                        <input type='file'
                                                               style='width:1px;height:1px;'
                                                               id="assetLocalUpload"
                                                               name="inputFile"/>
                                                        <%--<button type="button" class="btn btn-sm"
                                                            id="assetLocalBtn">
                                                            <i class="fa fa-crop orange bigger-120"></i> 上传需裁剪
                                                            <div id="loading_imgup" class="loading_imgup"
                                                                style="display: none; float: right;"></div>
                                                        </button>--%>

                                                        <button type="button" class="btn btn-sm"
                                                                id="assetLocalBtn_nocut">
                                                            <i class="fa fa-home orange bigger-120"></i>上传图片
                                                            <div id="loading_imgup_nocut"
                                                                 class="loading_imgup"
                                                                 style="display: none; float: right;"></div>
                                                        </button>
                                                    </div>
                                                </div>

                                                <%--<div class='col-xs-12 space-12'></div>

                                                <div class="col-xs-12">
                                                    <i class="ace-icon fa fa-circle green"></i>
                                                    方法二：选择FTP图片，选择编辑图片
                                                    <div class='space-6'></div>
                                                    <div class="col-md-12">
                                                        <button type="button" class="btn btn-sm" id="assetFtpBtn">
                                                            <i class="fa fa-cloud orange"></i> 选择FTP图片
                                                        </button>
                                                    </div>
                                                </div>

                                                <div class='col-xs-12 space-12'></div>

                                                <div class="col-xs-12">
                                                    <i class="ace-icon fa fa-circle green"></i>
                                                    方法三：视频截取，选择视频截取图片
                                                    <div class='space-6'></div>
                                                    <div class="col-md-12">
                                                        <button type="button" class="btn btn-sm" id="assetVideoBtn">
                                                            <i class="fa fa-film orange"></i> 视频截取
                                                        </button>
                                                    </div>
                                                </div>--%>
                                            </div>
                                        </div>

                                        <div id="ftpContent" class="tab-pane fade"
                                             style="margin-top:5px;">
                                            <div class="clearfix">
                                                <div class="col-md-4 no-padding">
                                                    <div class="widget-box transparent">
                                                        <div class="widget-body">
                                                            <div class="widget-main padding-8 zTreeBackground "
                                                                 style="height:360px;">
                                                                <ul id="fileResTree-uploadimgmodal"
                                                                    class="ztree"></ul>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>

                                                <div class="col-md-8 no-padding">
                                                    <div class="widget-box transparent">
                                                        <div class="widget-body no-border-left"
                                                             style="height:361px;">
                                                            <div class="widget-main"
                                                                 id="fileRes-row-uploadimgmodal">
                                                                <table id="fileRes-grid-table-uploadimgmodal"></table>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class='col-md-12' style="padding-top: 12px;">
                                                    <button id="chooseIMG-ftp" type="button"
                                                            class="btn btn-md" title="需切图？点击这个按钮吧！">
                                                        <i class="fa fa-gavel orange"></i> 选取裁剪
                                                    </button>
                                                    <button id="useIMG-ftp" type="button"
                                                            class="btn btn-md"
                                                            title="已切好图？使用这个按钮吧！">
                                                        <i class="fa fa-check orange bigger-110"></i>
                                                        立即使用
                                                    </button>
                                                    <button id="useVDO-ftp" type="button"
                                                            class="btn btn-md"
                                                            title="视频截取？使用这个按钮吧！">
                                                        <i class="fa fa-film orange bigger-110"></i>
                                                        视频截取
                                                    </button>
                                                </div>
                                            </div>
                                        </div>

                                        <div id="videoContent" class="tab-pane fade"
                                             style="margin-top:5px;">
                                            <div class="row center clearfix">
                                                <div class="col-md-6">
                                                    <i class="ace-icon fa fa-circle green"></i>
                                                    方法一：选择本媒资视频，使用该视频截取
                                                    <div class='space-6'></div>
                                                    <select style="width: 120px;" id="asset_media">

                                                    </select>
                                                    <button id="chooseVDO-asset" type="button"
                                                            class="btn btn-md" title="截取该视频">
                                                        <i class="fa fa-check orange"></i>
                                                    </button>
                                                </div>
                                                <div class="col-md-6">
                                                    <i class="ace-icon fa fa-circle green"></i>
                                                    方法二：选择FTP视频，使用选中视频截取
                                                    <div class='space-6'></div>
                                                    <button id="chooseVDO-ftp" type="button"
                                                            class="btn btn-md" title="选取FTP视频">
                                                        <i class="fa fa-cloud orange"></i> 选取FTP视频
                                                    </button>
                                                </div>
                                            </div>
                                            <div class='space-6'></div>
                                            <div class="clearfix">
                                                <div class="col-md-12">
                                                    <div id="imgs-loading" class="asset-loading"
                                                         style="padding-top: 60px;display:none;">
                                                        <img src="assets/img/loading.gif" alt="图片">
                                                        <h5>
                                                            <span>正在操作请稍候.....</span>
                                                        </h5>
                                                    </div>
                                                    <div id="imgs-video">
														<span class="span_uploadimgmodal"
                                                              onclick="$(this).find('input').prop('checked', true);">
										            		<input style="position: absolute;"
                                                                   type="radio" name="img-video">
															<img width="165px" height="92px"
                                                                 src="common/images/no_image.png">
														</span>
                                                        <span class="span_uploadimgmodal"
                                                              onclick="$(this).find('input').prop('checked', true);">
															<input style="position: absolute;"
                                                                   type="radio" name="img-video">
															<img width="165px" height="92px"
                                                                 src="common/images/no_image.png">
														</span>
                                                        <span class="span_uploadimgmodal"
                                                              onclick="$(this).find('input').prop('checked', true);">
															<input style="position: absolute;"
                                                                   type="radio" name="img-video">
															<img width="165px" height="92px"
                                                                 src="common/images/no_image.png">
														</span>
                                                        <span class="span_uploadimgmodal"
                                                              onclick="$(this).find('input').prop('checked', true);">
															<input style="position: absolute;"
                                                                   type="radio" name="img-video">
															<img width="165px" height="92px"
                                                                 src="common/images/no_image.png">
														</span>
                                                        <span class="span_uploadimgmodal"
                                                              onclick="$(this).find('input').prop('checked', true);">
															<input style="position: absolute;"
                                                                   type="radio" name="img-video">
															<img width="165px" height="92px"
                                                                 src="common/images/no_image.png">
														</span>
                                                        <span class="span_uploadimgmodal"
                                                              onclick="$(this).find('input').prop('checked', true);">
															<input style="position: absolute;"
                                                                   type="radio" name="img-video">
															<img width="165px" height="92px"
                                                                 src="common/images/no_image.png">
														</span>
                                                    </div>
                                                </div>
                                                <div class='col-md-12' style="padding-top: 12px;">
                                                    <button id="refresh-video" type="button"
                                                            class="btn btn-md">
                                                        <i class="fa fa-coffee orange"></i> 换一批配图
                                                    </button>

                                                    <button id="chooseIMG-video" type="button"
                                                            class="btn btn-md" title="需切图？点击这个按钮吧！">
                                                        <i class="fa fa-gavel orange"></i> 选取裁剪
                                                    </button>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>

                                <div id="jcropDiv" class="row" style="display: none;">
                                    <div id="jcropImg" style="height: auto;">
                                        <img class="img_uploadimgmodal" src="">
                                    </div>

                                    <div class='col-md-12 space-6'></div>

                                    <div class="col-md-12">
                                        <input id="cutParam" name="cutParam" class="hide"/>
                                        <button id="chooseIMG" type="button" class="btn btn-sm">
                                            <i class="ace-icon fa fa-crop orange"></i>
                                            裁剪
                                        </button>
                                        <button id="resetIMG" type="button" class="btn btn-sm">
                                            <i class="ace-icon fa fa-repeat orange"></i>
                                            重新上传
                                        </button>
                                    </div>
                                    <div class="col-md-12"></div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div><!-- /.col-md-8 -->

                <div class=" col-md-5">
                    <%--<div class="widget-box transparent">
                        <div class="widget-header widget-header-small">
                            <h4 class="widget-title smaller">
                                <i class="ace-icon fa fa-cog orange"></i>参数设置
                            </h4>
                            <div class="widget-toolbar action-buttons">
                            </div>
                        </div>

                        &lt;%&ndash;<div class="widget-body">
                            <div class="widget-main padding-8">
                                <div class="row">
                                    <div class="col-sm-12">
                                        <div class="profile-user-info">
                                            <div class="profile-info-row">
                                                <div class="profile-info-name name_uploadimgmodal">图片类型</div>
                                                <div class="profile-info-value">
                                                    <select class="" id="strategy">
                                                        <option value="2" selected="selected">H32原图</option>
                                                        <option value="0">V34原图</option>
                                                        <option tar="db" value="1">V23原图</option>
                                                        <option tar="db" value="7">H53原图</option>
                                                        <option tar="db" value="8">H43原图</option>
                                                        <option tar="db" value="9">S11原图</option>
                                                        <option tar="db" value="3">剧照</option>
                                                        <option tar="db" value="4">海报</option>
                                                        <option tar="db" value="5">壁纸</option>
                                                        <option tar="db" value="6">台标</option>
                                                        <option tar="tw" value="10">正文图片</option>
                                                    </select> &nbsp;&nbsp; <input type="hidden" id="imgName" />
                                                </div>
                                            </div>
                                            <div class="profile-info-row">
                                                <div class="profile-info-name name_uploadimgmodal">长宽比</div>
                                                <div class="profile-info-value" id="scale">3:2</div>
                                            </div>
                                            <div class="profile-info-row">
                                                <div class="profile-info-name name_uploadimgmodal">原图分辨率</div>
                                                <div class="profile-info-value">
                                                    <span class="editable" id="resolution"></span>
                                                </div>
                                            </div>
                                            <div class="profile-info-row">
                                                <div class="profile-info-name name_uploadimgmodal">缩放比例</div>
                                                <div class="profile-info-value">
                                                    <span class="editable" id="assscale"></span>
                                                </div>
                                            </div>
                                            <div class="profile-info-row">
                                                <div class="profile-info-name name_uploadimgmodal">裁剪宽高</div>
                                                <div class="profile-info-value">
                                                    <input type="text" class="input-mini" id="asswidth" readonly/>

                                                    <input type="text" class="input-mini" id="assheight" readonly/>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>&ndash;%&gt;
                       </div>--%>

                    <div class="widget-box transparent">
                        <div class="widget-header widget-header-small">
                            <h4 class="widget-title smaller">
                                <i class="ace-icon fa fa-expand orange"></i> 已选择图片
                            </h4>
                            <div class="widget-toolbar action-buttons"></div>
                        </div>

                        <div class="widget-body">
                            <div class="widget-main padding-8">
                                <div class="row select_uploadimgmodal" id="selectDiv">
                                    <ul class="cleafix no-margin-left">
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <div class="widget-footer text-right">
                            <button type="button" class="btn btn-md" id="saveIMG">保存</button>
                            <button type="button" class="btn btn-md" data-dismiss="modal">关闭
                            </button>
                        </div>
                    </div>
                </div><!-- /.col-md-4 -->
            </div><!-- /.modal-body -->

            <!-- <div class="modal-footer">
                <button type="button" class="btn btn-md" id="saveIMG">保存</button>
             <button type="button" class="btn btn-md" data-dismiss="modal">关闭</button>
            </div> --><!-- /.modal-footer -->
        </div><!-- /.modal-content -->
    </div><!-- /.modal -->
</div>

<script type="text/javascript">
  jQuery(function ($) {
    var jsonIdx = '<%=request.getParameter("imgIdx")%>';		//pUrl 图片路径，pName 图片名称, pType 0本地上传1Ftp
    //pUrl 图片路径，pName 图片名称, pType 0本地上传1Ftp
    var jcrop, s, pUrl, pName, pType, cSize, cScale, pData = [], needCut = false,
        imgIdx = $.parseJSON(jsonIdx), inSaving = 0;
    if (typeof String.prototype.startsWith != 'function') {//startsWith兼容性解决
      String.prototype.startsWith = function (str) {
        return (this.match("^" + str) == str)
      }
    }

    var isNews = false;
    $('.active', '#left_menu li').each(function () {
      if (this.className == 'active' && this.innerHTML.indexOf('图文') > -1) {
        isNews = true;
      }

    });

    if (isNews) {
      $('#strategy option').each(function () {
        var tar = $(this).attr('tar');
        if (tar == 'tw') {
          $(this).show();
        } else if (tar == 'db') {
          $(this).hide();
        }
      });
    } else {
      $('#strategy option').each(function () {
        var tar = $(this).attr('tar');
        if (tar == 'tw') {
          $(this).hide();
        } else if (tar == 'db') {
          $(this).show();
        }
      });
    }

    //通过文件名搜索ftp文件
    $("#ftpFileSearchButton").off("click").bind("click", function () {
      var grid_selector = "#fileRes-grid-table-uploadimgmodal";
      var serchText = $("#ftpFileSearchText").val();
      var postData = $(grid_selector).jqGrid("getGridParam", "postData");

      var lobj_sdata = {
        'filePath': postData.filePath,
        'serchText': serchText
      };

      if (serchText.indexOf('/') > -1) {
        lobj_sdata = {
          'filePath': serchText,
          'serchText': ''
        };
      }

      $.extend(postData, lobj_sdata);
      $(grid_selector).jqGrid("setGridParam", {
        search: true    // 将jqGrid的search选项设为true
      }).trigger("reloadGrid");
    });

    $("#ftpFileSearchText").off('keydown').on('keydown', function (ev) {
      if (ev.keyCode == 13) {
        $("#ftpFileSearchButton").trigger("click");
      }
    });

    $("#assetLocalBtn").off("click").bind("click", function () {
      needCut = true;
      $("#assetLocalUpload").trigger("click");
    });
    $("#assetLocalBtn_nocut").off("click").bind("click", function () {
      needCut = false;
      $("#assetLocalUpload").trigger("click");
    });

    $("#assetFtpBtn").off("click").bind("click", function () {
      $("#ftpTab").trigger("click");
    });

    $("#assetVideoBtn").off("click").bind("click", function () {
      $("#videoTab").trigger("click");
    });

    $('#strategy').off("change").bind("change", function (ev) {

      var sObj = getScaleObj();
      $('#scale').html(sObj.scaleHtml);
      $('#imgName').val(getCutImgName(pName, sObj.imgPostfix));
      if (jcrop) {
        var cs = jcropTracker(s);
        jcropAni(cs);
      }
      ;
    });

    $("#asswidth").off("change").bind("change", function () {

    });

    $("#selectDiv ul").click(function (e) {
      var target = e.target;
      if (target.tagName == 'DIV') {
        Lobibox.confirm({
          title: "提示",
          msg: "确认删除?",
          callback: function ($this, type, ev) {
            if (type === 'yes') {
              var li = $(target).parent();
              pData.remove($("#selectDiv li").index(li));
              console.log(pData);
              li.remove();
            }
          }
        });
      }
    });

    $('#assetLocalUpload').off("change").bind("change", function (ev) {
      var farr = $(this).val().split('\\');
      var fname = farr[farr.length - 1];

      if (!needCut) {
        if (isChineseChar(fname)) {
          alertmsg("warning", "图片名称不能包含中文、日文、韩文");
          $(this).val(null)
          return;
        }

        /*if(fname.indexOf('_V34_sc')==-1 && fname.indexOf('_V23_sc')==-1 && fname.indexOf('_H32_sc')==-1 && fname.indexOf('_JZ_sc')==-1 && fname.indexOf('_HB_sc')==-1
                && fname.indexOf('_TB_sc')==-1 && fname.indexOf('_H53_sc')==-1 && fname.indexOf('_H43_sc')==-1 && fname.indexOf('_S11_sc')==-1 && fname.indexOf('_BZ_sc')==-1 ) {
            alertmsg("warning","未按照规范命名图片，请重命名后再上传");
            $(this).val(null)
            return;
        }*/
        if (pData && pData.length > 0) {
          for (var i = 0; i < pData.length; i++) {
            if (pData[i].name == fname) {
              alertmsg("warning", "已上传该图片，请重新选择");
              $(this).val(null)
              return;
            }
          }
        }
      }
      if (!isNumWord(fname)) {
        alertmsg("warning", "图片名称只能包含英文、数字、下划线");
        $(this).val(null)
        return;
      }
      if ($("#imgUploadForm").length == 0) {
        //var assessId = $("#assessId").val();
        var form = $('<form id="imgUploadForm" enctype="multipart/form-data" method="post">');
        form.attr("action", webroot + "assess/saveAssessImg.do");
        typeof ($(this).attr("name")) == 'undefined' ? $(this).attr("name", 'inputFile') : '';
        $(this).wrap(form);
        var option = {
          beforeSubmit: function (formData, jqForm, options) {
          },
          success: function (response, statusText, xhr, form) {
            //console.log(response);
            if (response.success) {
              var path = response.data;
              $("#loading_imgup_nocut").hide();

              //var theImage = new Image();

              chooseImages(path, null, null, null);
            }
            /*var obj = responseText;
            pType = 0;//local
            var tmpName = obj.fileName[0];
            if(obj.scName && obj.scName.length>0 && !isChineseChar(obj.scName[0])) {
                tmpName = obj.scName[0];
            }
            if(needCut) {
                initJcrop(obj.path, tmpName);
            }else {
                $("#loading_imgup_nocut").hide();

                var theImage = new Image();
                theImage.src = obj.path;

                var imageWidth = theImage.width;
                var imageHeight = theImage.height;

                chooseImages(obj.path, tmpName, imageWidth, imageHeight);
            }*/

            var obj = document.getElementById('assetLocalUpload');
            obj.value = null;
          }
        }
        $('#imgUploadForm').ajaxForm(option);
      }
      var cutIpt = $("#imgUploadForm").find('#needCut');
      if (cutIpt.length > 0) {
        cutIpt.val(needCut ? 1 : 0);
      } else {
        $("#imgUploadForm").append(
            $('<input id="needCut" name="needCut" type="hidden" value="' + (needCut ? 1 : 0)
                + '">'));
      }
      if (needCut) {
        $("#loading_imgup").show();
      } else {
        $("#loading_imgup_nocut").show();
      }
      $("#imgUploadForm").submit();
    });

    $("#chooseIMG").off("click").bind("click", function () {
      var sourceDir = $("#imgSourceDir").val();
      var ppName = "";
      if (pUrl.startsWith(sourceDir)) {
        ppName = pUrl.replace(sourceDir, "");
      } else {
        ppName = pUrl.substring(pUrl.indexOf("/", 1))
      }

      var imgCutName = $('#imgName').val();

      for (var i = 0; i < pData.length; i++) {
        if (imgCutName == pData[i].name) {//同名说明已经截取过
          alertmsg("warning", "该比例图片已剪切过了，无需重复剪切..");
          return;
        }
      }
      if (document.hs) {
        var imgs = document.hs.getImgs();
        if (imgs && imgs.length > 0) {
          for (var j = 0; j < imgs.length; j++) {
            if (imgCutName == imgs[j].name) {//同名说明已经截取过
              alertmsg("warning", "该比例图片已剪切过了，无需重复剪切..");
              return;
            }
          }
        }
      }

      var x = Math.round(cSize.x * cScale);
      var y = Math.round(cSize.y * cScale);
      var w = parseInt($("#asswidth").val());//Math.round(cSize.w * cScale);
      var h = parseInt($("#assheight").val());//Math.round(cSize.h * cScale);

      w = (w + x) > s.w ? (s.w - x) : w;
      h = (h + y) > s.h ? (s.h - y) : h;

      var param = ppName + ";" + x + ";" + y + ";" + w + ";" + h + ";" + imgCutName + ";" + pType,
          params = '{"cutParam":"' + param + '"}';
      var request = $.ajax({
        url: "<c:url value='/asset/assetAction!imgCut.htm'/>",
        method: 'POST',
        data: eval('(' + params + ')'),
        dataType: 'text'
      });
      request.done(function (msg) {
        if (msg == '-1') {
          alertmsg("error", "图片有问题，请更换图片");
          return;
        }
        chooseImages(msg, imgCutName, w, h);
        //裁剪完后，图片类型自动跳转到V34
        var newValue = $("#strategy option:selected").val();
        if (newValue == '2') {
          $("#strategy").find("option[value='0']").attr("selected", true);
          $("#strategy").trigger("change");
        }

      });
      request.fail(function (jqXHR, textStatus) {
        console.log("ImgCuttor request failed: " + textStatus);
      });
    });

    $("#resetIMG").off("click").bind("click", function () {
      $("#uploadImgDiv").show();
      $("#jcropDiv").hide();
    });

    $("#saveIMG").off("click").bind("click", function () {
      saveImages();
    });

    function chooseImages(path, imgCutName, w, h) {
      var dd = new Date().getTime();
      var src = path + "?dd=" + dd;
      pData.push({
        title: imgCutName,
        alt: imgCutName,
        name: imgCutName,
        pixel: w + '*' + h,
        path: path,
        src: src
      });

      var pli = $("<li><img src='" + src + "'><div>删除</div></li>");
      $("#selectDiv ul").append(pli);

      //新闻图片特殊处理, 剧照、海报、壁纸相同
      var sObj = getScaleObj();
      if (sObj.imgPostfix.indexOf("_TW_") > -1) {
        imgIdx.tw++;

        $('#imgName').val(getCutImgName(imgCutName, sObj.imgPostfix));
      } else if (sObj.imgPostfix.indexOf("_JZ_") > -1) {
        imgIdx.jz++;

        $('#imgName').val(getCutImgName(imgCutName, sObj.imgPostfix));
      } else if (sObj.imgPostfix.indexOf("_HB_") > -1) {
        imgIdx.hb++;

        $('#imgName').val(getCutImgName(imgCutName, sObj.imgPostfix));
      } else if (sObj.imgPostfix.indexOf("_BZ_") > -1) {
        imgIdx.bz++;

        $('#imgName').val(getCutImgName(imgCutName, sObj.imgPostfix));
      }
    }

    function initJcrop(pth, name) {
      if ($("#jcropImg img").length > 0) {
        $("#jcropImg").html("");
      }
      pUrl = pth;
      pName = name;

      var sObj = getScaleObj();
      $('#imgName').val(getCutImgName(pName, sObj.imgPostfix));

      var img = $("<img class='img_uploadimgmodal' src='" + pUrl + "'>");
      $("#jcropImg").append(img);

      $("#jcropImg img").bind("load", function () {
        s = imgSize('#jcropImg img');
        $('#resolution').html(s.w + "*" + s.h);
        $("#assscale").html(s.cScale);

        //console.log(s.w+"*"+s.h);

        var cs = jcropTracker(s);

        jcrop = $.Jcrop('#jcropImg img', {
          onChange: showCoords,
          onSelect: showCoords,
          setSelect: [0, 0, cs.w, cs.h],
        });

        jcropAni(cs);

        $("#asswidth").val(cs.w);
        $("#assheight").val(cs.h);

        $("#loading_imgup").hide();
      });

      $("#uploadImgDiv").hide();
      $("#jcropDiv").show();
    }

    function saveImages() {
      var selector = $("#imgSelector").val();
      var assessId = $("#" + selector + " #assessId").val();

      if (inSaving == 1) {
        alertmsg("warning", "图片较多，正在保存中..");
        return;
      }
      //TODO ajax save img
      /*if(pData == null || pData.length == 0) {
          alertmsg("error", "图片还未裁剪..");
          return;
      }*/
      //var contextPath = $("#imgContextPath").val();
      var paths = "";
      for (var i = 0; i < pData.length; i++) {
        var p = pData[i].src;
        /*if (p.indexOf(contextPath) > -1) {
          p = p.replace(contextPath, "");
        }*/

        paths = paths + p + (i < pData.length - 1 ? ";" : "")
      }

      //var selector = $("#imgSelector").val();
      //var aid = $("#" + selector).val();
      if (!assessId || assessId == '') {
        for (var i = 0; i < pData.length; i++) {
          var d = pData[i];
          var p = d.path;
          if (p.indexOf(contextPath) > -1) {
            p = p.replace(contextPath, "");
          }

          document.hs.addImage({
            title: d.name,
            alt: d.name,
            name: d.name,
            pixel: d.pixel,
            src: d.path
          });
        }
        $("#uploadimgmodal").modal("hide");
        return;
      }
      inSaving = 1;
      var params = {
        assessId: assessId,
        paths: paths
      };
      var request = $.ajax({
        url: webroot + "image/saveImage.do",
        method: 'POST',
        data: params,
        dataType: 'json'
      });
      request.done(function (obj) {
        console.log(obj);
        if (obj.success) {
          //var imgDir = $("#imgStoreDir").val();

          if (document.hs) {
            for (var i = 0; i < obj.data.length; i++) {
              var d = obj.data[i];
              console.log(d);
              document.hs.addImage({
                id: d.imageId,
                title: null,
                alt: null,
                name: null,
                pixel: null,
                src: d.url
              }, true);
            }
          }

          $("#uploadimgmodal").modal("hide");
        } else {
          alertmsg("warning", obj.msg);
        }
        inSaving = 0;
      });
      request.fail(function (jqXHR, textStatus) {
        inSaving = 0;
        console.log("request failed: " + textStatus);
      });
    }

    //获取图片大小
    function imgSize(select) {
      var screenImage = $(select);

      var theImage = new Image();
      theImage.src = screenImage.attr("src");

      var imageWidth = theImage.width;
      var imageHeight = theImage.height;
      var scl = imageWidth / $("#jcropImg img").width();

      cScale = scl;

      //console.log(cScale);

      return {
        w: imageWidth,
        h: imageHeight,
        cScale: cScale
      }
    }

    function getScaleObj() {

      var newValue = $("#strategy option:selected").val();
      var i = 0;
      var scale = 1;
      var scaleHtml = "Any";
      var imgPostfix = "_sc";
      switch (newValue) {
        case '0':
          scale = 3 / 4;
          scaleHtml = "3:4";
          imgPostfix = "_V34_sc";
          break;
        case '1':
          scale = 2 / 3;
          scaleHtml = "2:3";
          imgPostfix = "_V23_sc";
          break;
        case '2':
          scale = 3 / 2;
          scaleHtml = "3:2";
          imgPostfix = "_H32_sc";
          break;
        case '3':

          scale = 0;
          i = i++;
          imgPostfix = "_n_JZ_sc";
          break;
        case '4':
          scale = 0;
          i = i++;
          imgPostfix = "_n_HB_sc";
          break;
        case '5':
          scale = 0;
          i = i++;
          imgPostfix = "_n_BZ_sc";
          break;
        case '6':
          scale = 0;
          imgPostfix = "_TB_sc";
          break;
        case '7':
          scale = 5 / 3;
          scaleHtml = "5:3";
          imgPostfix = "_H53_sc";
          break;
        case '8':
          scale = 4 / 3;
          scaleHtml = "4:3";
          imgPostfix = "_H43_sc";
          break;
        case '9':
          scale = 1 / 1;
          scaleHtml = "1:1";
          imgPostfix = "_S11_sc";
          break;
        case '10':
          scale = 0;
          imgPostfix = "_TW_n_sc";
          break;
        default:
          scale = 1;
      }

      return {
        scale: scale,
        scaleHtml: scaleHtml,
        imgPostfix: imgPostfix
      };
    }

    //获取截取后的图片
    function getCutImgName(ppName, imgPostfix) {
      var iname = "xxx_sc.jpg";
      if (ppName) {//xxx.jpg
        var idx = ppName.lastIndexOf(".");
        var nn = ppName.substr(0, idx);//xxx
        var nt = ppName.substr(idx);//.jpg

        nn = cleanPName(nn);

        if (imgPostfix.indexOf("_n_") > -1) {//图片序列自增
          var idx = "0";
          if (imgPostfix.indexOf("_TW_") > -1) {
            idx = (imgIdx.tw + 1) + "";
          } else if (imgPostfix.indexOf("_JZ_") > -1) {
            idx = (imgIdx.jz + 1) + "";
          } else if (imgPostfix.indexOf("_HB_") > -1) {
            idx = (imgIdx.hb + 1) + "";
          } else if (imgPostfix.indexOf("_BZ_") > -1) {
            idx = (imgIdx.bz + 1) + "";
          }

          if (idx.length == 1) {
            idx = "00" + idx;
          } else if (idx.length == 2) {
            idx = "0" + idx;
          }

          imgPostfix = imgPostfix.replace("_n_", "_" + idx + "_");
        }
        if (nn.indexOf(imgPostfix) == -1) {
          //for (var i = 1; i <pData.length; i++) {
          //if (i < 10) {
          iname = nn + imgPostfix + ".jpg";//xxx_V34_sc.jpg
          //} else {
          //iname = nn +"_0" + (++i) + imgPostfix + ".jpg";//xxx_V34_sc.jpg
          //}
          //}
        } else {
          iname = ppName;
        }
      }
      return iname;
    }

    function cleanPName(iname) {//xx_001_JZ_sc.jpg
      var tmpname = iname.replace('_V34_sc', '').replace('_V23_sc', '').replace('_H32_sc', '')
      .replace('_TB_sc', '').replace('_H53_sc', '').replace('_H43_sc', '').replace('_S11_sc', '');

      tmpname = tmpname.replace(/_TW_\d{3}_sc/, '');
      tmpname = tmpname.replace(/_\d{3}_JZ_sc/, '');
      tmpname = tmpname.replace(/_\d{3}_HB_sc/, '');
      tmpname = tmpname.replace(/_\d{3}_BZ_sc/, '');
      return tmpname;
    }

    function jcropTracker(s) {
      var w, h, scale = getScaleObj().scale;

      if (scale == 0) {
        w = s.w;
        h = s.h;
      } else if (s.w / s.h > scale) {
        w = Math.round(s.h * scale);
        h = s.h;
      } else {
        w = s.w;
        h = Math.round(s.w / scale);
      }
      return {
        scale: scale,
        w: w,
        h: h
      };
    }

    function jcropAni(cs) {
      if (cs.scale == 0) {
        jcrop.setOptions({aspectRatio: 0});
        return;
      }
      jcrop.animateTo([0, 0, cs.w, cs.h]);
      jcrop.setOptions({aspectRatio: cs.scale});
    }

    function showCoords(c) {
      cSize = c;

      var rst = getIntSize({
        w: c.w * cScale,
        h: c.h * cScale
      });
      $("#asswidth").val(rst.w);
      $("#assheight").val(rst.h);
    };

    function getIntSize(ss) {
      var rst = {};
      var sObj = getScaleObj();
      var sw = Math.round(ss.w);
      var sh = Math.round(ss.h);
      if (sObj.scale != 0) {
        var imgEl = $("#jcropImg img");
        if (s.w / s.h == sObj.scale && Math.round(cSize.w) == imgEl.width() && Math.round(cSize.h)
            == imgEl.height()) {
          rst.w = s.w;
          rst.h = s.h;
        } else if (sObj.scale > sw / sh) {//更窄，高调整
          for (var i = 0; i < 13; i++) {
            sh -= 1;

            var w = sh * sObj.scale;
            if (w == parseInt(w)) {
              rst.w = w;
              rst.h = sh;
              break;
            }
          }
        } else if (sObj.scale < sw / sh) {//更宽，宽调整
          for (var i = 0; i < 13; i++) {
            sw -= 1;

            var h = sw / sObj.scale;
            if (h == parseInt(h)) {
              rst.w = sw;
              rst.h = h;
              break;
            }
          }
        } else {
          rst.w = sw;
          rst.h = sh;
        }
      } else {
        rst.w = sw;
        rst.h = sh;
      }

      return rst;
    }

    function getCutName() {

    }

    /*		//ftp选取
            var fileResSource = '1';
            var fileResType = '13';

            var setting = {
                    async: {
                        enable: true,
                        url:webroot+"other/fileScanAction!scanFile.htm",
                        autoParam:["name=n", "level=lv", "path=filePath"],
                        otherParam:{"fileType":fileResType, "isTree":1},
                        dataFilter: filter
                    },
                    callback: {
                        onClick: clickHandler
                    }
                };

            function filter(treeId, parentNode, childNodes) {
                if (!childNodes) return null;
                for (var i=0, l=childNodes.length; i<l; i++) {
                    childNodes[i].name = childNodes[i].name.replace(/\.n/g, '.');
                }
                return childNodes;
            }

            function clickHandler(event, treeId, treeNode, clickFlag) {
                query_jqgrid(treeNode.path);
            }

            $.fn.zTree.init($("#fileResTree-uploadimgmodal"), setting);

            var grid_selector = "#fileRes-grid-table-uploadimgmodal";
            $(window).on('resize.jqGrid', function () {
                /!* $(grid_selector).jqGrid( 'setGridWidth', $("#fileRes-row-uploadimgmodal").width()); *!/
                $(grid_selector).jqGrid( 'setGridWidth', 348);
                /!* $(grid_selector).jqGrid( 'setGridHeight', $("#fileRes-row-uploadimgmodal").height()-32 ); *!/
            });
            $(grid_selector).jqGrid({
                url: webroot+"other/fileScanAction!scanFile.htm",
                mtype: 'post',
                datatype: "json",
                height: 290,
                colNames:['文件名','类型','路径','文件夹','大小','修改时间', 'cpid'],
                colModel:[
                    {name:'name',index:'name', width: 100},
                    {name:'type',index:'type', formatter:formatFileResType, width: 30},
                    {name:'path',index:'path', hidden:true},
                    {name:'isParent',index:'isParent', hidden:true},
                    {name:'size',index:'size', formatter:formatFileResSize, width: 30},
                    {name:'updateTime',index:'updateTime', hidden:true},
                    {name:'cpid',index:'cpid', hidden:true}
                ],
                rowNum:1000,
                rowList:[1000],
                viewrecords : true,
                loadonce: false,
                altRows: true,
                jsonReader: {
                    repeatitems: false
                },
                multiselect: true,
                multiboxonly: true,
                postData: { "isTree": 0, "fileType" : fileResType, "isSource" : fileResSource},
                ondblClickRow : function(rowid,iRow,iCol,e){
                    var rowData = $(grid_selector).getRowData(rowid);//获取选中行的记录
                    if(rowData && rowData.isParent=='true') {
                        query_jqgrid(rowData.path);
                    }
                },
                loadComplete: function(){
                    var re_records = $(grid_selector).getGridParam('records');
                    if(re_records == 0 || re_records == null){
                        if($(".norecords").html() == null){
                            $(grid_selector).parent().append("<div class=\"norecords\">没有符合数据</div>");
                        }
                        $(".norecords").show();
                    }else {
                        $(".norecords").hide();
                    }

                    //if(fileResSource == '1') {
                    //	$("#cb_fileRes-grid-table-uploadimgmodal").attr("disabled","disabled");
                    //}
                }
            });

            $(window).trigger("resize.jqGrid");

            function formatFileResType(cellvalue, options, rowObject){
                var result = '';
                switch (parseInt(cellvalue)) {
                    case 0 :
                        result = "文件夹";
                        break;
                    case 1 :
                        result = "视频";
                        break;
                    case 7 :
                        result = "音频";
                        break;
                    case 99 :
                        result = "图片";
                        break;
                    default:
                        break;
                }
                return result;
            }

            function formatFileResSize(cellvalue, options, rowObject){
                if(rowObject.isParent) {
                    return "";
                }else {
                    var x = parseInt(cellvalue)/1024;
                    if(x < 1000) {
                        return Math.round(x*100)/100+"KB";
                    }else {
                        var x = parseInt(x)/1024;
                        return Math.round(x*100)/100+"MB";
                    }
                }
            }

            function query_jqgrid(path) {
                var lobj_sdata = {
                        'filePath': path
                    };
                var postData = $(grid_selector).jqGrid("getGridParam", "postData");
                $.extend(postData, lobj_sdata);
                $(grid_selector).jqGrid("setGridParam", {
                    search: true    // 将jqGrid的search选项设为true
                }).trigger("reloadGrid");
                $("#fileResPathIpt").val(path);
            }

            $("#chooseIMG-ftp").off("click").on("click", function() {
                var ids=$(grid_selector).jqGrid("getGridParam","selarrrow");

                //var paths = [];

                if(ids && ids.length > 0) {
                    var hasDir = false;
                    for(var i=0; i<ids.length; i++) {
                        var rowData = $(grid_selector).jqGrid("getRowData",ids[i]);
                        console.log(rowData);

                        if(rowData.type.indexOf('图') == -1) {
                            alertmsg("warning", "请选择图片");
                            return;
                        }

                        if(!hasDir && rowData.isParent=='true') {
                            alertmsg("warning", "已忽略文件夹");
                            continue;
                        }
                        var pth = rowData.path;
                        var name = rowData.name;
                        if(isChineseChar(name)) {
                            alertmsg("warning", "图片名称不能包含中文、日文、韩文");
                            return;
                        }
                        if(!isNumWord(name)) {
                            alertmsg("warning", "图片名称只能包含英文、数字、下划线");
                            return;
                        }
                        if(rowData.cpid && rowData.cpid.length > 0) {
                            pth = rowData.cpid + "/" + pth;
                        }

                        //paths.push(pth);

                        pType = 1;//FTP
                        var sourceDir = $("#imgSourceDir").val();
                        initJcrop(sourceDir+pth, name);
                        break;
                    }
                }

                //console.log(paths);


            });*/

    $("#useIMG-ftp").off("click").on("click", function () {
      var ids = $(grid_selector).jqGrid("getGridParam", "selarrrow");

      var datas = [];
      if (ids && ids.length > 0) {
        for (var i = 0; i < ids.length; i++) {
          var rowData = $(grid_selector).jqGrid("getRowData", ids[i]);

          if (rowData.type.indexOf('图') == -1) {
            alertmsg("warning", "已自动忽略被选中的视频");
            continue;
          }
          if (rowData.isParent == 'true') {
            alertmsg("warning", "已忽略文件夹");
            continue;
          }
          var pth = rowData.path;
          var name = rowData.name;
          if (isChineseChar(name)) {
            alertmsg("warning", "图片名称不能包含中文、日文、韩文");
            return;
          }
          if (!isNumWord(name)) {
            alertmsg("warning", "图片名称只能包含英文、数字、下划线");
            return;
          }
          if (rowData.cpid && rowData.cpid.length > 0) {
            pth = rowData.cpid + "/" + pth;
          }
          rowData.path = pth;

          datas.push(rowData);
        }
      }

      console.log(datas);

      if (datas.length > 0) {
        var sourceDir = $("#imgSourceDir").val();
        for (var j = 0; j < datas.length; j++) {
          var dd = datas[j];

          var theImage = new Image();
          theImage.src = sourceDir + dd.path;

          var imageWidth = theImage.width;
          var imageHeight = theImage.height;

          chooseImages(sourceDir + dd.path, dd.name, imageWidth, imageHeight);
        }
      }
    });

    function doStr(str) {
      return {run: eval("(" + str + ")")};
    }

    //视频截图开始
    var isPumping = false;
    var isFile = false;
    var pumfileId = null;
    var pumpath = null;
    $("#chooseVDO-asset").off('click').bind("click", assetPumping);
    $("#chooseVDO-ftp").off('click').bind("click", chooseFtpPumping);
    $("#chooseIMG-video").off('click').bind("click", choosePumping);

    $("#useVDO-ftp").off('click').bind("click", function () {
      var ids = $(grid_selector).jqGrid("getGridParam", "selarrrow");

      if (ids && ids.length > 0) {
        for (var i = 0; i < ids.length; i++) {
          var rowData = $(grid_selector).jqGrid("getRowData", ids[i]);

          if (rowData.type.indexOf('视') == -1) {
            alertmsg("warning", "请选择视频");
            return;
          }

          var pth = rowData.path;
          var name = rowData.name;
          if (isChineseChar(name)) {
            alertmsg("warning", "视频名称不能包含中文、日文、韩文");
            return;
          }
          if (rowData.cpid && rowData.cpid.length > 0) {
            pth = rowData.cpid + "/" + pth;
          }

          pType = 1;//FTP
          ftpPumping(pth);

          $("#videoTab").trigger("click");
          break;
        }
      }
    });

    $("#refresh-video").off('click').bind("click", function () {
      if (isFile && pumfileId != null) {
        pumping(pumfileId);
      } else if (!isFile && pumpath != null) {
        ftpPumping(pumpath);
      }
    });

    (function mediaList() {
      var selector = $("#imgSelector").val();
      var aid = $("#" + selector).val();

      var $media = $("#asset_media");
      if (aid) {
        $media.html('');

        $.ajax({
          url: webroot + "asset/assetAction!mediaList.htm",
          method: 'POST',
          data: {"aid": aid},
          dataType: 'text'
        }).done(function (msg) {
          var obj = $.parseJSON(msg);
          if (obj.rows && obj.rows.length > 0) {
            for (var k = 0; k < obj.rows.length; k++) {
              var dd = obj.rows[k];

              $media.append('<option value=\"' + dd.id + '\">' + (dd.isSource ? '(源)' : '')
                  + dd.name + '</option>');
            }
          } else {
            $media.append('<option value="">无</option>');
          }
        });
      } else {
        $media.append('<option value="">无</option>');
      }
    })();

    function assetPumping() {
      var fileId = $("#asset_media option:selected").val();

      if (fileId && fileId.length > 0) {
        pumping(fileId);
      }
    }

    function ftpPumping(path) {
      if (path && path.length > 0) {
        pumping(null, path);
      }
    }

    function chooseFtpPumping() {
      $("#ftpTab").trigger("click");
    }

    //判断抽帧后拿到的图片是否存在
    function checkImageExist(imgUrl) {
      var imgObj = new Image();
      imgObj.src = imgUrl;
      if (imgObj.width > 0 && imgObj.height > 0) {
        return true;
      } else {
        return false;
      }
    }

    function pumping(fileId, path) {
      if (isPumping) {
        alertmsg("warning", "正在截图，请稍候..");
        return;
      }
      alertmsg("warning", "开始截图，请稍候..");

      var $imgs = $("#imgs-video").find("img");
      for (var n = 0; n < $imgs.length; n++) {
        $imgs[n].src = 'common/images/no_image.png';
      }
      $("#imgs-loading").show();

      if (fileId) {
        isFile = true;
        pumfileId = fileId;
        pumpath = null;
      } else {
        isFile = false;
        pumfileId = null;
        pumpath = path;
      }

      isPumping = true;
      var pumpingFrameNum = 6;

      var data = {
        'fileId': fileId,
        'path': path,
        'nums': pumpingFrameNum
      };

      $.ajax({
        url: webroot + "asset/assetAction!pumpingFrame.htm",
        method: 'POST',
        data: data,
        dataType: 'text',
        success: function (msg, textStatus) {
          var obj = $.parseJSON(msg);
          if (obj.success) {
            if (obj.data && obj.data.length > 0) {

              var times = 0;
              var existCount = [];
              var timerId = setInterval(function () {
                console.log("times:" + times);

                for (var i = 0; i < obj.data.length; i++) {
                  var src = obj.data[i];
                  if ($imgs[i] && checkImageExist(webroot + src)) {
                    $imgs[i].src = src;
                    $($imgs[i]).attr("_src", src);

                    $("#imgs-loading").hide();

                    if ($.inArray(i, existCount) == -1) {
                      existCount.push(i);
                    }
                  }
                }
                console.log("existCount:" + existCount);
                if (existCount.length == pumpingFrameNum || times >= 10) {//抽帧图片都生成成功
                  clearInterval(timerId);

                  isPumping = false;
                  $("#imgs-loading").hide();
                }

                times++;
              }, 2000);
            }
          } else {
            isPumping = false;
            alertmsg("warning", obj.msg == null ? "操作失败，请稍候再试" : obj.msg);
          }
        },
        error: function (data, textStatus) {
          isPumping = false;
          alertmsg("warning", "操作失败，请稍候再试");
        }
      });
    }

    function choosePumping() {
      if (pumfileId == null && pumpath == null) {
        alertmsg("warning", "请先截图");
        return;
      }

      var $radios = $("#imgs-video").find("input");
      var $imgs = $("#imgs-video").find("img");

      var pth = null;

      for (var i = 0; i < $radios.length; i++) {
        var rdo = $radios[i];
        if (rdo && rdo.checked) {
          pth = $($imgs[i]).attr("_src");
          break;
        }
      }
      console.log(pth);

      if (pth) {
        pType = 0;
        initJcrop(webroot + pth, pth.substring(pth.lastIndexOf("/") + 1, pth.length));
      } else {
        alertmsg("warning", "请选取需剪裁的图片");
      }
    }

  });
</script>