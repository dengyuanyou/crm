<style>
    /* .lg2 {
        position: absolute;
        top: 26px;
        left: 150px;

    }*/
     .lg2 {
         position: absolute;
         top: 10px;
         left: 270px;

     }
    .title{
        font-size: 15px;
        color: #0f74a8;

    }
</style>
<div class="navbar cm-navbar">
    <img class="logo" alt="Brand" src="/images/78910.png">
    <img src="/images/111.png" class="lg2">
       <#--<p class="list-group-item title" align="center">只为专注服务</p>-->
    <span class="pageTitle">&nbsp;</span>
    <ul class="nav navbar-nav navbar-right cm-navbar-nav  ">
        <li>
           <p class="navbar-text text-info">
               <span class="glyphicon glyphicon-user"></span>
               <@shiro.principal property="name" />
           </p>
        </li>

        <li><a href="/logout.do" class="glyphicon glyphicon-off">安全退出</a></li>
        <li><a href="">个人设置</a></li>
    </ul>
</div>