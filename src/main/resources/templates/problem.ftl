<#import "./_layout.ftl" as layout /><#-- not an error -->
<!DOCTYPE html>
<meta charset="UTF-8">
<link rel="stylesheet" href="../static/css/problem_display.css">
<@layout.header>
    <div id="problemApp" style="width: 90%; margin: auto">
        <#if linkedContestsNames??>
            <template>
                <section>
                    <b-message type="is-warning">
                        Attention ce problème fait partie de la ou les compétition(s) ${linkedContestsNames}.<br>
                        Si vous souhaitez soumettre le problème dans le cadre de la compétition, vous devez vous
                        enregistrer pour la compétition.<br>
                        S'enregistrer après la soumission décalera votre date de soumission à la date d'enregistrement.
                    </b-message>
                </section>
            </template>
        </#if>

        <#if not_available?? && !not_available>
            <div style="display: flex; justify-content: space-between; align-items: center; flex-wrap: wrap;">
                <h2 class="subtitle is-2">
                    ${problem.name}
                </h2>
            </div>

            <div>
                Ce problème n'est pas disponible pour le moment.
            </div>
        <#else>
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
            </div>

            <span class="m-3"></span>

            <div id="statement" class="content"></div>
            <br>
            <div class = "sample">
                <div class = "input1">
                    <div class = "header">
                        <span class="heading">Entrée</span>
                        <span class="copyInput"></span>
                        <span class="dlInput"></span>
                    </div>
                    <div class = "displayInput">
            <pre>3
2 2 1 1
5 2 3 1
4 4 3 3</pre>
                    </div>
                </div>
                <div class = "output">
                    <div class = "header">
                        <span class="heading">Sortie</span>
                        <span class="copyInput"></span>
                        <span class="dlInput"></span>
                    </div>
                    <div class = "displayOutput">
            <pre>Case #1: NO
Case #2: YES
Case #3: NO</pre>
                    </div>
                </div>
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
        </#if>
    </div>
    <script>
        let vue = new Vue({
            el: '#problemApp',
        })
    </script>
</@layout.header>