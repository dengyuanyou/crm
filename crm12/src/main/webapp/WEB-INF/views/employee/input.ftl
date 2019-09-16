<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <#include "../common/header.ftl">
    <script src="/jquery/plugins/jquery.validate.min.js"></script>
    <script src="/jquery/plugins/messages_cn.js"></script>
    <script>
        function moveSelected(src, target) { // 样式
            $('.' + target).append($('.' + src + ' > option:selected'));
        }

        function moveAll(src, target) { // 样式
            $('.' + target).append($('.' + src + ' > option'));
        }

        $(function () {
            var $role; // 抽出来
            $('#admin').click(function () {
                console.log($(this).prop('checked'));
                if ($(this).prop('checked')) {
                    $role = $('#role').detach(); // 管理员就删除
                } else {
                    $(this).closest('div').after($role); // 不是管理员还原
                }
            });


            $('#btn_submit').click(function () {
                // 把右边下拉框中option 都加上 selected 属性
                $('.selfRoles option').prop('selected', true);

                $('#editForm').ajaxForm(function (data) {
                    if(data.success){
                        $.messager.alert("温馨提示","操作成功,2S之后关闭");
                        setTimeout(function () {
                            window.location.href="/employee/list.do"
                        },2000);
                    }
                })

            });
            var selfRoleIds = $.map($('.selfRoles option'), function (option, index) { // 数组对象.map
                return $(option).val(); // 函数返回值是  selfRoleIds 数组 里面的元素
            });
            // console.log(selfRoleIds);

            $.each($('.allRoles option'), function (index, option) {
                var value = $(option).val();

                var index = $.inArray(value, selfRoleIds); // 若存在返回索引值，若不存在，返回 -1
                if (index >= 0) { // 若存在，删除 option
                    $(option).remove();
                }
            });


            // 注意放置的位置，要在角色去重后面
            // 针对编辑超管处理
            if ($('#admin').prop('checked')) {
                $role = $('#role').detach();
            }


            // 数据校验
            $("#editForm").validate({
                //规则
                rules:{
                    name:{
                        required:true,
                        minlength:2
                    },
                    password:{
                        required:true,
                        minlength:6
                    },
                    repassword:{
                        required:true,
                        equalTo:"#password"
                    },
                    email:{
                        required:true,
                        email:true
                    },
                    age:{
                        required:true,
                        range:[18,80]
                    }
                }
            })
        });
    </script>
</head>
<body>

<div class="container " style="margin-top: 20px">
    <#include "../common/top.ftl">
    <div class="row">
        <div class="col-sm-3">
            <#assign currentMenu="employee" />
            <#include "../common/menu.ftl">
        </div>
        <div class="col-sm-9">
            <div class="row">
                <div class="col-sm-12">
                    <h1 class="page-head-line">员工编辑</h1>
                </div>
            </div>
            <div class="row col-sm-10">
                <form class="form-horizontal" action="/employee/saveOrUpdate.do" method="post" id="editForm">
                    <input type="hidden" value="${(employee.id)!}" name="id">
                    <div class="form-group">
                        <label for="name" class="col-sm-2 control-label">用户名：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="name" name="name" value="${(employee.name)!}" placeholder="请输入用户名">
                        </div>
                    </div>

                    <#if !employee??>
                    <div class="form-group">
                        <label for="password" class="col-sm-2 control-label">密码：</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" id="password" name="password"
                                   placeholder="请输入密码">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="repassword" class="col-sm-2 control-label">验证密码：</label>
                        <div class="col-sm-6">
                            <input type="password" class="form-control" name="repassword" placeholder="再输入一遍密码">
                        </div>
                    </div>
                    </#if>

                    <div class="form-group">
                        <label for="email" class="col-sm-2 control-label">Email：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="email" name="email" value="${(employee.email)!}"
                                   placeholder="请输入邮箱">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="age" class="col-sm-2 control-label">年龄：</label>
                        <div class="col-sm-6">
                            <input type="text" class="form-control" id="age" name="age" value="${(employee.age)!}"
                                   placeholder="请输入年龄">
                        </div>
                    </div>
                    <div class="form-group">
                        <label for="dept" class="col-sm-2 control-label">部门：</label>
                        <div class="col-sm-6">
                            <select class="form-control" id="dept" name="dept.id">
                                <#list depts as d>
                                    <option value="${d.id}">${d.name}</option>
                                </#list>
                            </select>
                            <script>
                                $("#dept option[value='${(employee.dept.id)!}']").prop("selected", true);
                            </script>
                        </div>
                    </div>

                    <div class="form-group">
                        <label for="admin" class="col-sm-2 control-label">超级管理员：</label>
                        <label class="checkbox-inline" style="margin-left: 15px;">
                            <input type="checkbox" id="admin"
                                   name="admin" ${(employee?? && employee.admin)?string('checked','')}>
                        <#-- <script>
                             <#if employee?? && employee.admin >

                                 $("[name=admin]").prop("checked", true);
                             </#if>
                         </script>-->
                        </label>
                    </div>
                <div class="form-group" id="role">
                    <div>
                        <label for="role" class="control-label" style="margin-left: 60px">角色：</label>
                    </div>
                    <div class="row" style="margin-top: 10px">
                        <div class="col-sm-4 col-sm-offset-1">
                            <select multiple class="form-control allRoles" size="15">
                                <#list roles as role>
                                    <option value="${role.id}">${role.name}</option>
                                </#list>
                            </select>
                        </div>

                        <div class="col-sm-2" style="margin-top: 60px;" align="center">
                            <div>
                                <a type="button" class="btn btn-info  " style="margin-top: 10px"
                                   onclick="moveSelected('allRoles', 'selfRoles')">&nbsp;&gt;&nbsp;</a>
                                <br>
                                <a type="button" class="btn btn-info " style="margin-top: 10px"
                                   onclick="moveSelected('selfRoles', 'allRoles')">&nbsp;&lt;&nbsp;</a>
                                <br>
                                <a type="button" class="btn btn-info " style="margin-top: 10px"
                                   onclick="moveAll('allRoles', 'selfRoles')">&gt;&gt;</a>
                                <br>
                                <a type="button" class="btn btn-info " style="margin-top: 10px"
                                   onclick="moveAll('selfRoles', 'allRoles')">&lt;&lt;</a>
                            </div>
                        </div>

                        <div class="col-sm-4">
                            <select multiple class="form-control selfRoles" size="15" name="ids">
                                <#if employee?? && (employee.roles)??>
                                    <#list employee.roles as role>
                                        <option value="${role.id}">${role.name}</option>
                                    </#list>
                                </#if>
                            </select>
                        </div>
                    </div>
                </div>

                    <div class="form-group">
                        <div class="col-sm-offset-1 col-sm-6">
                            <button id="btn_submit" class="btn btn-default">保存</button>
                            <button type="reset" class="btn btn-default">重置</button>

                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>