<#macro header>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <!DOCTYPE html>
    <html lang="en">
<#--    <html xmlns:th="http://www.thymeleaf.org">-->
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Bladur</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://kit.fontawesome.com/701effbac8.js" crossorigin="anonymous"></script>
        <script src="https://cdn.jsdelivr.net/npm/vue@2.5.16/dist/vue.js"></script>
        <script src="https://unpkg.com/buefy/dist/buefy.min.js"></script>
        <script src="../../static/js/jquery.min.js"></script>
        <link rel="stylesheet" href="https://unpkg.com/buefy/dist/buefy.min.css">
        <#--<link rel="icon" type="image/png" href=""/>-->
    </head>
    <body>
    <div class="container">

        <nav class="navbar" role="navigation" aria-label="main navigation">
            <div class="navbar-menu">
                <div class="navbar-start">
                    <a class="navbar-item" href="/">Home</a>
                    <a class="navbar-item" href="/blog">Blog</a>
                    <a class="navbar-item" href="/problem">Problèmes</a>
                    <#if known>
                        <a class="navbar-item" href="/profile/test">Test</a>
                    </#if>
                    <a class="navbar-item" href="/contest">Compétitions</a>
                    <a class="navbar-item" href="/scoreboard">Classement</a>
                    <div class="navbar-item has-dropdown is-hoverable">
                        <a class="navbar-link">Plus</a>
                        <div class="navbar-dropdown">
                            <a class="navbar-item" href="@{/contact}">Contact</a>
                            <a class="navbar-item" href="@{/legalNotice}">Legals</a>
                        </div>
                    </div>
                </div>

                <div class="navbar-end">
                    <#if known>
                        <a class="navbar-item" href="/profile">Profil</a>
                        <a class="navbar-item" href="/logout">Déconnexion</a>
                    <#else>
                        <a class="navbar-item" href="/login">Se connecter</a>
                    </#if>
                </div>
            </div>
        </nav>

        <style>
            .navbar-menu {
                display: flex;
                justify-content: space-between;
                align-items: center;
            }
        </style>

        <div>
            <#nested>
        </div>

<#--        <script src="https://unpkg.com/vue@2"></script>-->
        <!-- Full bundle -->
        <script>
            const app = new Vue()
            app.$mount('#app')
        </script>
        <!-- Individual components -->
        <script src="https://unpkg.com/buefy/dist/components/table"></script>
        <script src="https://unpkg.com/buefy/dist/components/input"></script>

<#--        <script>-->
<#--            new Vue({-->
<#--                el: '#app'-->
<#--            })-->
<#--        </script>-->
    </div>
    </body>
    </html>
</#macro>