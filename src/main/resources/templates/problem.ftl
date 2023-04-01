<!DOCTYPE html>
<html lang="en">
<head>

    <script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
    <script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="../static/css/problem.css">
    <title>Title</title>
</head>
<body>
<div id="content">
    <div id="container">
        <h1><#if problem.name??> ${problem.name}
            <#else>Problem name is missing
            </#if>
        </h1>
        <div @class="delimiters">
            Problem Statement:
        </div>
        <div>
            <#if problem.statement??> ${problem.statement}
            <#else>Problem statement is missing
            </#if>
        </div>
        <div @class="delimiters">
            Input:
        </div>
        <div>
            <#if problem.input??> ${problem.input}
            <#else>Problem input is missing
            </#if>
        </div>
        <div @class="delimiters">
            Output:
        </div>
        <div>
            <#if problem.output??> ${problem.output}
            <#else>Problem output is missing
            </#if>
        </div>
        <div @class="delimiters">
            Constraints:
        </div>
        <div>
            <#if problem.exec_constraints??> ${problem.exec_constraints}
            <#else>Problem Constraints are missing
            </#if>
        </div>
    </div>
</div>
</body>
</html>