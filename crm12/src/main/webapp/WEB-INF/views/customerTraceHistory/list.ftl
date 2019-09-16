<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>客户关系管理系统</title>
    <#include "../common/header.ftl" >
    <script src="/js/My97DatePicker/WdatePicker.js"></script>
    <script>
        $(function () {
            //提交表单
            $(".btn-submit").click(function () {
                //将表单的提交方式修改为ajax异步提交
                $("#editForm").ajaxSubmit(function (data) {
                    if (data.success) {
                        $.messager.alert("温馨提示", "操作成功,2S之后关闭");
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    }
                })
            });

            //日期插件的使用
            $("#searchForm [name=beginDate]").click(function () {
                WdatePicker({
                    readOnly:true,
                    maxDate:new Date()
                });
            });
            $("#searchForm [name=endDate]").click(function () {
                WdatePicker({
                    readOnly:true,
                    maxDate:new Date(),
                    minDate:$("#searchForm [name=beginDate]").val()
                });


            });

            //编辑的模态框
            $(".btn-input").click(function () {
                var json = $(this).data("json");
                if(json){
                    $("#traceForm [name=id]").val(json.id);
                    $("#traceForm [name=name]").val(json.customerName);
                    $("#traceForm [name=traceTime]").val(json.traceTime);
                    $("#traceForm [name=traceDetails]").val(json.traceDetails);
                    $("#traceForm [name='traceType.id']").val(json.traceType);
                    $("#traceForm [name=traceResult]").val(json.traceResult);
                    $("#traceForm [name=remark]").val(json.remark);
                }
                $("#traceModal").modal("show");
            });

            //跟进时间
            $("#traceForm [name=traceTime]").click(function () {
                WdatePicker({
                    readOnly:true,
                    maxDate:new Date()
                })
            });

            $(".traceSubmit").click(function () {
                $("#traceForm").submit();
            })
        });
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="customerTraceHistory"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">跟进历史管理</h1>
                </div>
            </div>
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/customerTraceHistory/list.do" method="post">
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <div class="form-group">
                    <label>关键字:</label>
                    <input type="text" class="form-control" name="keyword"
                           value="${(qo.keyword)!}" placeholder="请输入客户姓名/录入人">
                </div>
                <div class="form-group">
                    <label>时间:</label>
                    <input type="text" class="form-control" name="beginDate"
                           value="${(qo.beginDate?string("yyyy-MM-dd"))!}"
                           placeholder="请输入开始时间"> ~
                    <input type="text" class="form-control" name="endDate"
                           value="${(qo.endDate?string("yyyy-MM-dd"))!}"
                           placeholder="请输入结束时间">
                </div>
                <button class="btn btn-primary">
                    <span class="glyphicon glyphicon-search"></span> 查询
                </button>
            </form>

            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>客户姓名</th>
                    <th>跟进时间</th>
                    <th>跟进方式</th>
                    <th>跟进记录</th>
                    <th>跟进结果</th>
                    <th>录入人</th>
                    <th>操作</th>
                </tr>
                </thead>
                <#list pageInfo.list as entity>
                <tr>
                    <td>${entity_index + 1}</td>
                    <td>${entity.customer.name}</td>
                    <td>${entity.traceTime?string("yyyy-MM-dd")}</td>
                    <td>${entity.traceType.title}</td>
                    <td>${entity.traceDetails}</td>
                    <td>${entity.traceResultName}</td>
                    <td>${entity.inputUser.name}</td>
                    <td>
                        <a class="btn btn-info btn-xs btn-input" href="javascript:;" data-json='${entity.jsonString}'>
                            <span class="glyphicon glyphicon-pencil"></span>编辑
                        </a>

                    </td>
                </tr>
                </#list>
            </table>
            <#include "../common/page.ftl" />
        </div>
    </div>
</div>


<#--跟进历史-->
<div class="modal fade" id="traceModal" tabindex="-1" role="dialog" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
                <h4 class="modal-title">跟进</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/customerTraceHistory/saveOrUpdate.do" method="post" id="traceForm">
                    <input type="hidden" name="id"/>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">客户姓名：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" readonly name="name"/>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">跟进时间：</label>
                        <div class="col-lg-6 ">
                            <input type="text" class="form-control "  name="traceTime" placeholder="请输入跟进时间">
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">跟进记录：</label>
                        <div class="col-lg-6">
                            <input type="text" class="form-control" name="traceDetails"
                                   placeholder="请输入跟进记录"/>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">交流方式：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="traceType.id">
                                <#list types as c>
                                    <option value="${c.id}">${c.title}</option>
                                </#list>
                            </select>
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-lg-4 control-label">跟进结果：</label>
                        <div class="col-lg-6">
                            <select class="form-control" name="traceResult">
                                <option value="1">优</option>
                                <option value="2">中</option>
                                <option value="3">差</option>
                            </select>
                        </div>
                    </div>
                    <div class="form-group" >
                        <label class="col-lg-4 control-label">备注：</label>
                        <div class="col-lg-6">
                            <textarea type="text" class="form-control" name="remark"></textarea>
                        </div>
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary traceSubmit">保存</button>
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            </div>
        </div>
    </div>
</div>
</body>
</html>