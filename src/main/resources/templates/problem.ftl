<#import "./_layout.ftl" as layout /><#-- not an error -->

<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
<meta charset="UTF-8">
<script defer src="../static/js/problem.js"></script>
<link rel="stylesheet" href="../static/css/problem.css">
<@layout.header>
    <div class="space">
        <div style="float: left;padding-top: 8px;padding-left: 5px;">
            <h1 class="title is-1">${problem.name}</h1>
        </div>
        <a style="padding-top: 17px;" href="/submit/${id}">
            <button class="button copybut">Soumettre</button>
        </a>
    </div>
    <div class="card">
        <div class="card-header no_shadow">
            <p class="card-header-title is-size-5">Description :</p>
        </div>
        <div class="card-content">
            ${problem.descriptionStatement}
        </div>
    </div>
    <div class="card">
        <div class="card-header no_shadow">
            <p class="card-header-title is-size-5">Contraintes :</p>
        </div>
        <div class="card-content">
            ${problem.limitsFormatted}
        </div>
    </div>
    <div class="card">
        <div class="card-header no_shadow">
            <p class="card-header-title is-size-5">Exemples :</p>
        </div>

        <div class="card-content">
            <#assign i = 0>
            <#list problem.getSamples() as example>
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
</@layout.header>