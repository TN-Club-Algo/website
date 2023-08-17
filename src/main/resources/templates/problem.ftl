<#import "./_layout.ftl" as layout /><#-- not an error -->
<!DOCTYPE html>
<meta charset="UTF-8">
<@layout.header>
    <div style="width: 90%; margin: auto">
        <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap;">
            <h2 class="subtitle is-2">
                ${problem.name}
            </h2>

            <a href="/submit/${problem.slug}">
                <div class="button">
                    Soumettre
                </div>
            </a>
        </div>

        <div class="subtitle is-5" id="limits" style="text-align: right;">
            TEMPS LIMITE : ${problem.validationTimeLimit} s<br>
            MÉMOIRE LIMITE : ${problem.validationMemoryLimit} Mo<br>
            <a href="/problem/leaderboard/${problem.slug}">
                <div class="button">
                    Classement
                </div>
            </a>
        </div>

        <span class="m-3"></span>

        <div id="statement" class="content"></div>
    </div>
    <script type="application/javascript">
        window.texme = {
            renderOnLoad: false,
            style: 'none',
        }
    </script>
    <script type="application/javascript" src="https://cdn.jsdelivr.net/npm/texme@1.2.2"></script>
    <script type="application/javascript">
        $(window).on('load', function () {
            var body = document.body.innerHTML
            document.body.innerHTML = `<div id="statement" class="content">${problemStatement}</div>`
            texme.renderPage()
            document.body.innerHTML = body.replace(`<div id="statement" class="content"></div>`, document.body.innerHTML)
        });
    </script>
</@layout.header>