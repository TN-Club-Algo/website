<#import "_layout.ftl" as layout />

<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
<script defer src="../static/js/submit.js"></script>
<script src="../static/js/ace/mode-python.js" type="text/python" charset="utf-8"></script>
<script src="../static/js/ace/mode-c_cpp.js" type="text/cpp" charset="utf-8"></script>
<script src="../static/js/ace/mode-kotlin.js" type="text/kotlin" charset="utf-8"></script>
<script src="../static/js/ace/mode-java.js" type="text/java" charset="utf-8"></script>
<script src="../static/js/ace/ace.js" type="text/javascript" charset="utf-8"></script>
<meta charset="UTF-8">
<link rel="stylesheet" href="../static/css/submission.css">
<style>
    #editor {
        width: 100%;
        height: 300px;
    }
</style>
<@layout.header>
    <div id="container">
        <div class="card">
            <div class="card-header subtitle is-2">
                Soumission : ${problemId}
            </div>
            <div class="card-content">
                <div class="select mb-2">
                    <select id="language" name="lang">
                        <option value="python">Python 3</option>
                        <option value="kotlin">Kotlin</option>
                        <option value="c_cpp">C++</option>
                        <option value="java">Java</option>
                    </select>
                </div>

                <div id="editor" class="mb-6">some text</div>

                <form action="/submit/${problemId}" method="post" enctype="multipart/form-data">
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
                                Pas de fichier sélectionné
                            </span>
                            </label>
                        </div>
                    </div>
                    <#--                    <input type="file" multiple name="files" class="selection">-->
                    <br>
                    <button class="mt-3 button is-fullwidth">
                        Soumettre
                    </button>

                </form>
            </div>
        </div>
    </div>
</@layout.header>