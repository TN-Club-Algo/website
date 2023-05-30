<#import "/_layout.ftl" as layout /><#-- not an error -->

<!--<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>-->
<!--<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>-->
<!--<script defer src="../static/js/submit.js"></script>-->
<meta charset="UTF-8">
<!--<link rel="stylesheet" href="../static/css/submission.css">-->
<@layout.header>
    <div id="container">
        <div class="card">
            <div class="card-header">
                Problem Creation
            </div>
            <div class="card-content">
                <form action="/new_problem" method="post" enctype="multipart/form-data">
                    <input name="uuid" type="text" disabled>
                    <input name="title" type="text">
                    <input name="statement" type="text">
                    <input name="input" type="text">
                    <input name="execConstraints" type="text">
                    <input name="exempleInput" type="text">
                    <input name="exempleOutput" type="text">
                    <input name="explaination" type="text">



                    <br>
                    <button class="mt-3 button is-fullwidth">
                        Create Problem
                    </button>

                </form>
            </div>
        </div>
    </div>
</@layout.header>