<#import "/_layout.ftl" as layout /><#-- not an error -->

<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
<script defer src="../static/js/submit.js"></script>
<meta charset="UTF-8">
<link rel="stylesheet" href="../static/css/submission.css">
<@layout.header>
    <div id="container">
        <div class="card">
            <div class="card-header">
                Submit ${problemId}
            </div>
            <div class="card-content">
                <form action="/submit/${problemId}" method="post" enctype="multipart/form-data">

                    <textarea id="prog" name="prog" rows="25">
                    </textarea>
                    <div id="menu">
                        <div id="myfiles" class="file has-name is-boxed">
                            <label class="file-label">
                                <input class="file-input" type="file" multiple name="files">
                                <span class="file-cta">
                                <span class="file-icon">
                                    <i class="fas fa-upload"></i>
                                </span>
                                <span class="file-label">
                                    Choisissez vos fichiers
                                </span>
                            </span>
                                <span id="filesnames" class="file-name">
                                pas de fichier sélectionné
                            </span>
                            </label>
                        </div>
                        <div class="select">
                            <select name="lang" class="selection">
                                <option value="python3">python3</option>
                                <option value="kotlin">kotlin</option>
                                <option value="C++">C++</option>
                                <option value="java">java</option>
                                <option value="ocaml">ocaml</option>
                            </select>
                        </div>
                    </div>
                    <#--                    <input type="file" multiple name="files" class="selection">-->
                    <br>
                    <button class="mt-3 button is-fullwidth">
                        Submit
                    </button>

                </form>
            </div>
        </div>
    </div>
</@layout.header>