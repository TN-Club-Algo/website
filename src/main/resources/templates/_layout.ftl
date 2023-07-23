<#macro header>
    <!DOCTYPE html>
    <html lang="en">
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <title>Bladur</title>
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma@0.9.4/css/bulma.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
        <script src="https://kit.fontawesome.com/701effbac8.js" crossorigin="anonymous"></script>
        <#--<link rel="icon" type="image/png" href=""/>-->
    </head>
    <body>
    <div class="container">

        <nav class="navbar" role="navigation" aria-label="main navigation">
            <div class="navbar-menu">

                <div class="navbar-start">
                    <a class="navbar-item" href="/">
                        Home
                    </a>

                    <a class="navbar-item" href="/blog">
                        Blog
                    </a>

                    <a class="navbar-item" href="/problem">
                        Problèmes
                    </a>

                    <a class="navbar-item" href="/profile/test">
                        Test
                    </a>

                    <a class="navbar-item" href="/scoreboard">
                        Classement
                    </a>

                    <div class="navbar-item has-dropdown is-hoverable">
                        <a class="navbar-link">
                            More
                        </a>

                        <div class="navbar-dropdown">
                            <a class="navbar-item" href="@{/contact}">
                                Contact
                            </a>
                            <a class="navbar-item" href="@{/legalNotice}">
                                Legals
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </nav>
        <#nested>
    </div>
    </body>
    </html>
</#macro>