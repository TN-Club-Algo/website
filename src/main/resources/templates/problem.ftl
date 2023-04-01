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
        <div>
            When \(a \ne 0\), there are two solutions to \(ax^2 + bx + c = 0\) and they are
            $$x = {-b \pm \sqrt{b^2-4ac} \over 2a}.$$
        </div>
    </div>
</div>
</body>
</html>