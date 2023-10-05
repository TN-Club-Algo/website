<#import "../_layout.ftl" as layout />
<#macro header>
    <title>Panel administrateur</title>
    <meta charset="UTF-8">
    <style>
        #panel-top {
            height: 5vh;
            display: flex;
        }

        .panel-top-element {
            display: inline-flex;
        }

        #panels {
            float: right;
            margin-left: auto;
        }

        .panel-tab {
            margin-left: 10px;
            margin-right: 10px;
            display: inline-flex;
            height: 100%;
            align-items: center;
        }
    </style>
    <@layout.header>
        <div id="panel-top">
            <h1 id="yo" class="title panel-top-element">
                Panel d'administration
            </h1>
            <div id="panels" class="panel-top-element">
                <a href="/admin/users" class="panel-tab">
                    Utilisateurs
                </a>
                <a href="" class="panel-tab">
                    Statistiques
                </a>
            </div>
        </div>
        <#nested>
    </@layout.header>
</#macro>
