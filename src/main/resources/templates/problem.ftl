<#import "./_layout.ftl" as layout /><#-- not an error -->
<!DOCTYPE html>

<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
<script type="module">

    import { LaTeXJSComponent } from "https://cdn.jsdelivr.net/npm/latex.js/dist/latex.mjs"
    customElements.define("latex-js", LaTeXJSComponent)
</script>
<meta charset="UTF-8">
<@layout.header>
    <div style="width: 75%; margin: auto">
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
    </div>
</@layout.header>

<div class="container-fluid">
    <latex-js baseURL="https://cdn.jsdelivr.net/npm/latex.js/dist/">
        ${problem.fullStatement}
    </latex-js>
</div>

<div class="card">
    <div class="card-header no_shadow">
        <p class="card-header-title is-size-5">Exemples :</p>
    </div>

    <div class="card-content">
        <#assign i = 0>
        <#list problem.sampleFiles.samples as example>
            <div class="card">
                <div class="card-header space">
                    <div style="float: left;padding-top: 8px;padding-left: 5px;">Entrée :</div>
                </div>
                <div class="card-content">
                    <div id="example-input-${i}">${example.first}</div>
                </div>
            </div>
            <div class="card">
                <div class="card-header">
                    <div style="padding-left: 5px;">Sortie :</div>
                </div>
                <div class="card-content">
                    <div id="example-output-${i}">${example.second}</div>
                </div>
            </div>
            <br>
        </#list>
    </div>
</div>
