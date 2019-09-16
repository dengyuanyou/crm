<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>客户关系管理系统</title>
    <#include "../common/header.ftl" >
    <script src="/js/ckeditor/ckeditor.js"></script>
    <script src="/js/My97DatePicker/WdatePicker.js"></script>

    <script>
        $(function () {

            //显示模态框
            $(".btn-input").click(function () {
                console.log(1);
                //先清空表单中的数据
                // $("#editForm input").val("");
                //DOM中的reset函数,可以将整个表单重置
                document.getElementById("transferForm").reset();
                // $("#editForm :input").val("");
                //取出按钮上绑定的json数据
                var json = $(this).data("json");
                console.log(json);
                if (json) {
                    //回显数据
                    $("#contract_id").val(json.id);
                    $("#customer_id").val(json.customerId);
                    $("#phone").val(json.customerTel);
                    $("#seller_name").val(json.sellerId);
                    $("#seller_email").val(json.sellerEmail);
                    $("#contract_inputTime").val(json.inputTime);
                    $("#contract_money").val(json.money);
                    $("#editor").val(json.instruction);
                    console.log(json.customerName);
                }
                $("#transferModal").modal("show");
            });

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

            //跟进模态框弹出
            $(".btn-trace").click(function () {
                var json = $(this).data("json");
                if (json) {
                    $("#traceForm [name=name]").val(json.name);
                    $("#traceForm [name='customer.id']").val(json.id);
                }
                $("#traceModal").modal("show");
            });

            // 为跟进时间绑定点击事件
            $("#traceForm [name=traceTime]").click(function () {
                WdatePicker({
                    readOnly: true,
                    maxDate: new Date()
                })
            });

            //提交跟进表单
            $(".traceSubmit").click(function () {
                $("#traceForm").submit();
            });

            $(".transferBtn").click(function () {
                var json = $(this).data("json");
                if (json) {
                    $("#transferForm [name='customer.id']").val(json.id)
                    $("#transferForm [name='customer.name']").val(json.name)
                    $("#transferForm [name='oldSeller.id']").val(json.sellerId)
                    $("#transferForm [name='oldSeller.name']").val(json.sellerName)
                }
                $("#transferModal").modal("show");
            });

            $(".transferSubmit").click(function () {

                //在提交之前强制将ckeditor中的内容放到textarea中
                $('#editor').val(CKEDITOR.instances.editor.getData());

                $("#transferForm").ajaxSubmit(function (data) {
                    if(data.success) {
                        $.messager.alert("温馨提示,","提交成功，2s之后刷新");
                        setTimeout(function () {
                            window.location.reload();
                        },2000)
                    }
                })
            });

            //修改状态
            $(".changeStatusBtn").click(function () {
                var json = $(this).data("json");
                if (json) {
                    $("#statusForm [name=cid]").val(json.id);
                    $("#statusForm [name=name]").val(json.name);
                    $("#statusModal").modal("show");
                }
            });
            $(".statusSubmit").click(function () {
                $("#statusForm").submit();
            })

            //开始日期
            $("#searchForm [name=beginDate]").click(function () {
                WdatePicker({
                    readOnly:true,
                    maxDate:new Date()
                });
            });

            //截止日期
            $("#searchForm [name=endDate]").click(function () {
                WdatePicker({
                    readOnly: true,
                    maxDate: new Date(),
                    minDate: $("#searchForm [name=beginDate]").val()
                })

            });
            // 为签约日期绑定点击事件
            $("#contract_inputTime").click(function () {
                WdatePicker({
                    readOnly: true,
                    maxDate: new Date()
                })
            });

            //点击合同明细查看合同内容
            $(".btn-content").click(function () {
                var contentId = $(this).data("json");
                console.log(contentId);

                //发送ajax请求根据合同id查询合同内容
                $.get("/contract/selectByContentId.do",{contentId:contentId},function (data) {
                    //将获取的数据放到ckeditor富文本编辑器中
                    console.log(data);
                    $("#transferForm1 input[name=id]").val(data.id);
                    $("#editor1").val(data.instruction);
                    CKEDITOR.instances.editor1.setData(data.instruction);

                })
                //弹出模态框
                $("#transferModa2").modal("show");
            });

            //合同内容修改
            $(".transferSubmit1").click(function () {

                //在提交之前强制将ckeditor中的内容放到textarea中
                $('#editor1').val(CKEDITOR.instances.editor1.getData());

                //ajax请求提交提交表单
                $("#transferForm1").ajaxSubmit(function (data) {
                    if(data.success){
                        $.messager.alert("温馨提示:","保存成功,2s之后自动刷新!");
                        setTimeout(function () {
                            window.location.reload();
                        },2000)
                    }
                })
            });

            //删除合同
            $(".btn-delete").click(function () {
                var contractId = $(this).data("json");
                console.log(contractId);

                //弹出提示框
                $.messager.confirm("操作提示:","您确定要删除吗?",function (data) {
                        //发送ajax进行删除
                        $.post("/contract/deleteContract.do",{contractId:contractId},function (data) {
                            if(data.success){
                                $.messager.alert("温馨提示:","删除成功,2s之后自动刷新!");
                                setTimeout(function () {
                                    window.location.reload();
                                },2000)
                            }else {
                                $.messager.alert("温馨提示:","删除失败!");
                            }
                        })
                })

            })

            //提交表单
           /* $(".transferSubmit").click(function () {
                //将表单的提交方式修改为ajax异步提交
                $("#transferForm").ajaxSubmit(function (data) {
                    if (data.success) {
                        $.messager.alert("温馨提示", "操作成功,2S之后关闭");
                        setTimeout(function () {
                            window.location.reload();
                        }, 2000);
                    }
                })
            });*/

            })
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="mycontract_list"/>
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">我的合同</h1>
                </div>
            </div>
            <!--高级查询--->
            <form class="form-inline" id="searchForm" action="/contract/list.do" method="post" >
                <input type="hidden" name="currentPage" id="currentPage" value="1">
                <div class="form-group">
                    <label>关键字:</label>
                    <input type="text" class="form-control" name="keyword" value="${(qo.keyword)!}"
                           placeholder="请输入客户姓名/电话/销售人名字/邮箱">
                </div>
                <div class="form-group">
                    <label>时间:</label>
                    <input type="text" class="form-control" name="beginDate" value="${(qo.beginDate?string("yyyy-MM-dd"))!}"
                           placeholder="请输入开始时间"> ~
                    <input type="text" class="form-control" name="endDate" value="${(qo.endDate?string("yyyy-MM-dd"))!}"
                           placeholder="请输入结束时间">
                </div>
                <button type="submit" class="btn btn-primary">
                    <span class="glyphicon glyphicon-search"></span> 查询
                </button>
            </form>
            <table class="table table-hover table-bordered">
                <thead>
                <tr>
                    <th>编号</th>
                    <th>客户姓名</th>
                    <th>客户电话</th>
                    <th>签约日期</th>
                    <th>签约金额</th>
                    <th>操作</th>
                </tr>
                </thead>
                <#list pageInfo.list as entity>
                <tr>
                    <td>${entity_index + 1}</td>
                    <td>${entity.customer.name}</td>
                    <td>${entity.customer.tel}</td>
                    <td>${entity.inputTime?string("yyyy-MM-dd")}</td>
                    <td>${entity.money}</td>
                    <td>
                        <a class="btn btn-info btn-xs btn-content" href="javascript:;" data-json="${entity.contentId}">
                            <span class="glyphicon glyphicon-pencil"></span>合同明细
                        </a>
                    </td>

                </tr>
                </#list>
            </table>
            <#include "../common/page.ftl" />
        </div>
    </div>
</div>

<#--合同内容编辑模态框-->
<div id="transferModa2" class="modal fade" tabindex="-1" role="dialog">
    <div class="modal-dialog" role="document">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span>
                </button>
                <h4 class="modal-title">合同内容编辑</h4>
            </div>
            <div class="modal-body">
                <form class="form-horizontal" action="/contract/UpdateContent.do" method="post" id="transferForm1"  style="margin: -3px 118px">
                    <input type="hidden" value="" name="id">
                    <div class="form-group">
                        <textarea name="instruction" id="editor1" rows="10" cols="85">

                        </textarea>
                        <script>
                            var ck1 = CKEDITOR.replace('editor1');
                        </script>
                    </div>

                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                <button type="button" class="btn btn-primary transferSubmit1" >保存</button>
            </div>
        </div>
    </div>
</div>

<script>
    //当点击客户姓名的时候触发查询客户电话的二级联动
    function requestTel() {
        var requestTel = $("select option:selected");
        var tel = requestTel.val();

        //根据销售人的id查询销售人的邮箱
        $.post("/customer/selectCustomerInfo.do",{tel:tel},function (data) {
            console.log(tel);
            console.log(data);

            //将获取的客户的电话,销售人的姓名,邮箱设置到对应的表单控件中
           $("#phone").val(data.tel);
           $("#seller_name").val(data.seller_id);
           $("#seller_email").val(data.seller_email);
        });
    }
</script>

</body>
</html>