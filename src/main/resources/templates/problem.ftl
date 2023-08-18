<#import "./_layout.ftl" as layout /><#-- not an error -->
<!DOCTYPE html>
<meta charset="UTF-8">
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
                <a href="/problem/leaderboard/${problem.slug}">
                    <div class="button">
                        Classement
                    </div>
                </a>
            </div>

            <span class="m-3"></span>

            <div id="statement" class="content"></div>
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