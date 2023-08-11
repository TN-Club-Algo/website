<#macro header>
    <#assign known = SPRING_SECURITY_CONTEXT??>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>AlgoTN</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://kit.fontawesome.com/701effbac8.js" crossorigin="anonymous"></script>
        <link rel="stylesheet" href="https://unpkg.com/buefy/dist/buefy.min.css">
        <link rel="icon" type="image/x-icon" href="/api/image/algotn.ico">
    </head>
    <body>
    <div class="container">
        <div id="app">
            <template class="mb-3">
                <b-navbar>
                    <template #brand>
                        <b-navbar-item tag="router-link" :to="{ path: '/' }">
                            <img
                                    src="/api/image/algotn.webp"
                                    alt="AlgoTN"
                            >
                        </b-navbar-item>
                    </template>
                    <template #start>
                        <b-navbar-item href="/">
                            Accueil
                        </b-navbar-item>
                        <b-navbar-item href="/blog">
                            Blog
                        </b-navbar-item>
                        <b-navbar-item href="/problem">
                            Problèmes
                        </b-navbar-item>
                        <#if known>
                            <b-navbar-item href="/profile/test">
                                Tests
                            </b-navbar-item>
                        </#if>
                        <b-navbar-item href="/scoreboard">
                            Classement
                        </b-navbar-item>
                    </template>

                    <template #end>
                        <#if known>
                            <b-navbar-item href="/profile">
                                Profil
                            </b-navbar-item>
                            <b-navbar-item href="/logout">
                                Déconnexion
                            </b-navbar-item>
                        <#else>
                            <b-navbar-item href="/login">
                                Se connecter
                            </b-navbar-item>
                        </#if>

                    </template>
                </b-navbar>
            </template>
        </div>

        <#nested>

        <script src="https://unpkg.com/vue@2"></script>
        <!-- Full bundle -->
        <script src="https://unpkg.com/buefy/dist/buefy.min.js"></script>

        <!-- Individual components -->
        <script src="https://unpkg.com/buefy/dist/components/table"></script>
        <script src="https://unpkg.com/buefy/dist/components/input"></script>

        <script>
            new Vue({
                el: '#app'
            })
        </script>
    </div>
    </body>
    </html>
</#macro>