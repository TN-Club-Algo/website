<#import "_layout.ftl" as layout />

<script src="https://polyfill.io/v3/polyfill.min.js?features=es6"></script>
<script id="MathJax-script" async src="https://cdn.jsdelivr.net/npm/mathjax@3/es5/tex-mml-chtml.js"></script>
<script defer src="../static/js/submit.js"></script>
<script src="../static/js/ace/mode-python.js" type="text/python" charset="utf-8"></script>
<script src="../static/js/ace/mode-c_cpp.js" type="text/cpp" charset="utf-8"></script>
<script src="../static/js/ace/mode-c_cpp.js" type="text/c" charset="utf-8"></script>
<script src="../static/js/ace/mode-kotlin.js" type="text/kotlin" charset="utf-8"></script>
<script src="../static/js/ace/mode-java.js" type="text/java" charset="utf-8"></script>
<script src="../static/js/ace/mode-golang.js" type="text/java" charset="utf-8"></script>
<script src="../static/js/ace/ace.js" type="text/javascript" charset="utf-8"></script>
<meta charset="UTF-8">
<style>
    #editor {
        width: 100%;
        height: 300px;
    }
</style>
<script src="https://challenges.cloudflare.com/turnstile/v0/api.js" async defer></script>
<@layout.header>
    <div style="width: 75%; margin: auto">

        <div class="subtitle is-2">
            Soumission : ${problem.name}
        </div>
        <div class="select mb-2">
            <select id="language" name="language">
                <option value="python">Python 3</option>
                <option disabled value="kotlin">Kotlin</option>
                <option value="golang">Golang</option>
                <option value="c_cpp">C++</option>
                <option value="c">C</option>
                <option disabled value="java">Java</option>
            </select>
        </div>

        <div id="editor" class="mb-6"></div>

        <div id="notification" class="is-hidden notification is-danger">

        </div>

        <div style="width: 100%; margin: auto;">
            <form id="submitForm" enctype="multipart/form-data">
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
                    <div class="cf-turnstile" data-sitekey="0x4AAAAAAAIoSOON_Aer-2Ox" data-theme="light"></div>
                    <p class="control">
                        <button class="button">
                            Soumettre
                        </button>
                    </p>
                </div>
            </form>
        </div>
    </div>

    <script type="application/javascript">
        $("#submitForm").submit(function (event) {
            event.preventDefault(); // Prevent form submission

            const formData = new FormData(this);

            formData.append("code", editor.getValue());
            formData.append("language", $("#language").val());

            $.ajax({
                type: "POST",
                url: "/submit/${problem.slug}",
                data: formData,
                processData: false, // Prevent jQuery from processing the FormData
                contentType: false, // Let the browser set the Content-Type header
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        window.location.href = data.redirectURL;
                    } else {
                        $("#notification").removeClass("is-hidden");
                        $("#notification").html(data.error);
                    }
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        });
    </script>
</@layout.header>