<#import "_layout.ftl" as layout />

<@layout.header>
    <nav class="navbar" role="navigation" aria-label="main navigation">
        <div class="navbar-menu">

            <div class="navbar-start">
                <a class="navbar-item" href="@{/}">
                    Home
                </a>

                <a class="navbar-item" href="@{/blog}">
                    Blog
                </a>

                <a class="navbar-item" href="@{/problem}">
                    Problèmes
                </a>

                <a class="navbar-item" href="@{/profile/test}">
                    Test
                </a>

                <a class="navbar-item" href="@{/scoreboard}">
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
</@layout.header>