<#import "_layout.ftl" as layout />

<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
<script defer src="../static/js/submit.js"></script>
<script src="../static/js/ace/mode-python.js" type="text/python" charset="utf-8"></script>
<script src="../static/js/ace/mode-c_cpp.js" type="text/cpp" charset="utf-8"></script>
<script src="../static/js/ace/mode-c_cpp.js" type="text/c" charset="utf-8"></script>
<script src="../static/js/ace/mode-kotlin.js" type="text/kotlin" charset="utf-8"></script>
<script src="../static/js/ace/mode-java.js" type="text/java" charset="utf-8"></script>
<script src="../static/js/ace/ace.js" type="text/javascript" charset="utf-8"></script>
<meta charset="UTF-8">
<style>
    #editor {
        width: 100%;
        height: 300px;
    }
</style>
<@layout.header>
    <div style="width: 75%; margin: auto">

        <div class="subtitle is-2">
            Soumission : ${problem.name}
        </div>
        <div class="select mb-2">
            <select id="language" name="lang">
                <option value="python">Python 3</option>
                <option value="kotlin">Kotlin</option>
                <option value="c_cpp">C++</option>
                <option value="c">C</option>
                <option value="java">Java</option>
            </select>
        </div>

        <div id="editor" class="mb-6"></div>

        <div style="width: 100%; margin: auto;">
            <form action="/submit/${problem.slug}" method="post" enctype="multipart/form-data">
                <div class="field is-grouped">
                    <div class="control">
                        <div id="myfiles" class="file has-name is-boxed">
                            <label class="file-label">
                                <#if problem.allowMultipleFiles>
                                    <input class="file-input" type="file" multiple name="files">
                                    <div class="file-cta">
                                <span class="file-icon">
                                    <i class="fas fa-upload"></i>
                                </span>
                                        <span class="file-label">
                                    Choisissez vos fichiers
                                </span>
                                    </div>
                                <#else>
                                    <input class="file-input" type="file" name="files">
                                    <div class="file-cta">
                                <span class="file-icon">
                                    <i class="fas fa-upload"></i>
                                </span>
                                        <span class="file-label">
                                    Choisissez un fichier
                                </span>
                                    </div>
                                </#if>
                                <div id="filesnames" class="file-name">
                                    Pas de fichier sélectionné
                                </div>
                            </label>
                        </div>
                    </div>
                    <p class="control">
                        <button class="button">
                            Soumettre
                        </button>
                    </p>
                </div>
            </form>
        </div>
    </div>
    </div>
</@layout.header>