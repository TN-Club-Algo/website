<#import "/_layout.ftl" as layout /><#-- not an error -->

<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
<meta charset="UTF-8">
<link rel="stylesheet" href="../static/css/problem.css">
<@layout.header>
    <div id="container">
        <h1 class="title is-1"><#if problem.name??> ${problem.name}
            <#else>Problem name is missing
            </#if>
        </h1>
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
                <p class="card-header-title is-size-5">Constraints:</p>
            </div>
            <div class="card-content">
                <#if problem.exec_constraints??> ${problem.exec_constraints}
                <#else>Problem Constraints are missing
                </#if>
            </div>
        </div>
        <div class="card">
            <div class="card-header no_shadow">
                <p class="card-header-title is-size-5">Examples:</p>
            </div>
            <#list problem.examples as example>
            <div class="card-content">
                <div class="card">
                    <div class="card-header">
                        Input:
                    </div>
                    <div class="card-content">
                        <#if example.input??> ${example.input}
                        <#else> Example Input is missing
                        </#if>
                    </div>
                </div>
                <div class="card">
                    <div class="card-header">
                        Output:
                    </div>
                    <div class="card-content">
                        <#if example.output??> ${example.output}
                        <#else> Example Output is missing
                        </#if>
                    </div>
                </div>
                <div class="card">
                    <div class="card-header">
                        Explanation:
                    </div>
                    <div class="card-content">
                        <#if example.explanation??> ${example.explanation}
                        <#else> (no explanations given)
                        </#if>
                    </div>
                </div>
            </div>
        </div>
        </#list>
    </div>
<#--    <#list sequence as item>-->
</@layout.header>