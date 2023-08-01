<#import "./_layout.ftl" as layout /><#-- not an error -->

<!--<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>-->
<!--<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>-->
<!--<script defer src="../static/js/submit.js"></script>-->
<meta charset="UTF-8">
<link rel="stylesheet" href="../static/css/new_problem.css">
<@layout.header>
    <div id="container">
        <div class="card">
            <div class="card-header">
                Problem Creation
            </div>
            <div class="card-content">
                <form action="/new_problem" method="post" enctype="multipart/form-data">
                    <div class="field">
                        <label class="label">Problem Name</label>
                        <div class="control">
                            <input class='input' type="text" name="pbName">
                        </div>
                    </div>
                    <div class="field">
                        <label class="label">Statement</label>
                        <div class="control">
                            <textarea class="txtArea" name="statement" rows="15"></textarea>
                        </div>
                    </div>

                    <div class="columns is-mobile">

                        <div class="column">
                            <div class="field">
                                <label class="label">Input</label>
                                <div class="control">
                                    <textarea class="txtArea" name="input" rows="5"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="column">
                            <div class="field">
                                <label class="label">Output</label>
                                <div class="control">
                                    <textarea class="txtArea" name="output" rows="5"></textarea>
                                </div>

                            </div>
                        </div>
                    </div>




                    <br>
                    <button class="mt-3 button is-fullwidth">
                        Create Problem
                    </button>

                </form>
            </div>
        </div>
    </div>
</@layout.header>