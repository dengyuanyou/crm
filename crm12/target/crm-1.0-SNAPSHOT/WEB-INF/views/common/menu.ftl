<ul id="menu" class="list-group">
    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#systemMgr">
            <span>系统管理</span>
        </a>
        <ul class="in" id="systemMgr">
            <li class="department"><a href="/department/list.do">部门管理</a></li>
            <li class="employee"><a href="/employee/list.do">员工管理</a></li>
            <li class="permission"><a href="/permission/list.do">权限管理</a></li>
            <li class="role"><a href="/role/list.do">角色管理</a></li>
        </ul>
    </li>


    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#dataMgr">
            <span>数据管理</span>
        </a>
        <ul class="in" id="dataMgr">
            <li class="systemDictionary"><a href="/systemDictionary/list.do">字典目录</a></li>
            <li class="systemDictionaryItem"><a href="/systemDictionaryItem/list.do">字典明细</a></li>
        </ul>
    </li>
    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#customerMgr">
            <span>潜在客户管理</span>
        </a>
        <ul class="in" id="customerMgr">
            <li class="customer_list"><a href="/customer/list.do">客户列表</a></li>
            <li class="customer_potential"><a href="/customer/potentialList.do">潜在客户</a></li>
            <li class="customer_pool"><a href="/customer/poolList.do">客户池</a></li>
            <li class="customer_failList"><a href="/customer/failList.do">失败客户</a></li>
            <li class="customerTraceHistory"><a href="/customerTraceHistory/list.do">跟进历史</a></li>
            <li class="customerTransferHistory"><a href="/customerTransferHistory/list.do">移交历史</a></li>
        </ul>
    </li>
    <li class="list-group-item">
        <a href="#" data-toggle="collapse" >
            <span>报表统计</span>
        </a>
        <ul class="in" >
            <li class="customerReport"><a href="/chart/list.do">潜在客户报表</a></li>
        </ul>
    </li>
    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#formalCustomerMgr">
            <span>正式客户管理</span>
        </a>
        <ul class="in" id="formalCustomerMgr">
            <li class="formalCustomer"><a href="#">正式客户</a></li>
            <li class="travel"><a href="#">流失客户</a></li>
        </ul>
    </li>



    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#hrMgr">
            <span>人事管理</span>
        </a>
        <ul class="in" id="hrMgr">
            <li class="user"><a href="#">员工管理</a></li>
            <li class="region"><a href="#">招聘计划</a></li>
            <li class="region"><a href="#">工资管理</a></li>
        </ul>
    </li>
    <li class="list-group-item">
        <a href="#" data-toggle="collapse" data-target="#personMgr">
            <span>个人管理</span>
        </a>
        <ul class="in" id="personMgr">
            <li class="user"><a href="#">修改密码</a></li>
            <li class="region"><a href="#">个人偏好</a></li>
            <li class="region"><a href="#">签到签退</a></li>
        </ul>
    </li>
</ul>

<!--设置菜单回显-->
<script>
    $(".in li.${currentMenu}").addClass("active");
</script>