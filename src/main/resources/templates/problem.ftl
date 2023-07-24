<#import "./_layout.ftl" as layout /><#-- not an error -->

<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
<meta charset="UTF-8">
<script defer src="../static/js/problem.js"></script>
<link rel="stylesheet" href="../static/css/problem.css">
<@layout.header>
    <div id="container">
        <#if problem.name??>
            <div class="space">
            <div style="float: left;padding-top: 8px;padding-left: 5px;">
                <h1 class="title is-1">${problem.name}</h1>
            </div>
            <a style="padding-top: 17px;" href="/submit/${id}">
                <button class="button copybut">Submit</button>
            </a>
            </div>
        <#else>Problem name is missing
        </#if>
        <div class="card">
            <div class="card-header no_shadow">
                <p class="card-header-title is-size-5">Problem statement:</p>
            </div>
            <div class="card-content">
                <#if problem.statement??> ${problem.statement}
                <#else>Problem statement is missing
                </#if>
            </div>
        </div>
        <div class="card">
            <div class="card-header no_shadow">
                <p class="card-header-title is-size-5">Input:</p>
            </div>
            <div class="card-content">
                <#if problem.input??> ${problem.input}
                <#else>Problem input is missing
                </#if>
            </div>
        </div>
        <div class="card">
            <div class="card-header no_shadow">
                <p class="card-header-title is-size-5">Output:</p>
            </div>
            <div class="card-content">
                <#if problem.output??> ${problem.output}
                <#else>Problem output is missing
                </#if>
            </div>
        </div>
        <div class="card">
            <div class="card-header no_shadow">
                <p class="card-header-title is-size-5">Constraints:</p>
            </div>
            <div class="card-content">
                <#if problem.execConstraints??> ${problem.execConstraints}
                <#else>Problem Constraints are missing
                </#if>
            </div>
        </div>
        <div class="card">
            <div class="card-header no_shadow">
                <p class="card-header-title is-size-5">Examples:</p>
            </div>

            <div class="card-content">
                <#list problem.examples as example>
                    <div class="card">
                        <div class="card-header space">
                            <div style="float: left;padding-top: 8px;padding-left: 5px;">Input:</div>
                            <button onclick="myFunction('${example.uuid}')" class="button copybut">Copy</button>
                        </div>
                        <div class="card-content">
                            <#if example.input??>
                                <div id="${example.uuid}">${example.input}</div>
                            <#else> Example Input is missing
                            </#if>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <div style="padding-left: 5px;">Output:</div>
                        </div>
                        <div class="card-content">
                            <#if example.output??> ${example.output}
                            <#else> Example Output is missing
                            </#if>
                        </div>
                    </div>
                    <div class="card">
                        <div class="card-header">
                            <div style="padding-left: 5px">Explanation:</div>
                        </div>
                        <div class="card-content">
                            <#if example.explanation??> ${example.explanation}
                            <#else> (no explanations given)
                            </#if>
                        </div>
                    </div>
                    <br>
                </#list>
            </div>
        </div>
    </div>
<#--    <#list sequence as item>-->
</@layout.header>