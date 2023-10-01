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
        <script src="https://cdnjs.cloudflare.com/ajax/libs/axios/1.2.1/axios.min.js"></script>
        <link rel="stylesheet" href="https://unpkg.com/buefy/dist/buefy.min.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/@mdi/font@5.8.55/css/materialdesignicons.min.css">
        <link rel="icon" type="image/x-icon" href="/api/image/algotn.ico">

        <script src="https://unpkg.com/vue@2"></script>
        <!-- Full bundle -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/buefy/0.9.22/buefy.min.js"></script>

        <!-- Individual components -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/buefy/0.9.22/components/table/index.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/buefy/0.9.22/components/input/index.min.js"></script>
    </head>
    <body>
    <div class="container">
        <div id="app">
            <template class="mb-3">
                <b-navbar>
                    <template #brand>
                        <b-navbar-item href="/">
                            <img src="/api/image/algotn.webp"
                                 alt="AlgoTN">
                        </b-navbar-item>
                    </template>
                    <template #start>
                        <b-navbar-item href="/">
                            Accueil
                        </b-navbar-item>
                        <b-navbar-item href="/problem">
                            Problèmes
                        </b-navbar-item>
                        <#if known>
                            <b-navbar-item href="/profile/test">
                                Tests
                            </b-navbar-item>
                        </#if>
                        <a class="navbar-item" href="/contest">
                            Compétitions
                        </a>
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

        <script>
            new Vue({
                el: '#app',
            })
        </script>

        <script src="../static/js/index.js"></script>
    </div>
    </body>
    </html>
</#macro>
