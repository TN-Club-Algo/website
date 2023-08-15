<#import "../_layout.ftl" as layout />

<title>Liste des Compétitions</title>
<script src="../../static/js/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<@layout.header>

    <div id="appIndex" class="card">
        <header class="card-header" style="position: relative;height: 54px;">
            <p class="subtitle is-4 p-3">
                Compétitons en cours
            </p>
            <div class="container">
                <b-button id="head_centered" tag="a"
                          href="/contest/submit"
                          target="_blank"
                          style="position: absolute;top: 50%;transform: translateY(-50%);right: 20px;">
                    Créer une Compétition
                </b-button>
            </div>
        </header>
        <div>
            <div class="card-content">
                <div class="content">
                    <table class="table is-fullwidth is-striped">
                        <thead>
                        <tr>
                            <th>Compétitions</th>
                            <th>Début</th>
                            <th>Durée</th>
                            <th>Inscrits</th>
                        </tr>
                        </thead>
                        <tbody>
                        <#-- todo edit the front for the contest-->
                        <#list contests as contest>
                            <tr>
                                <td><a href="/contest/${contest.uuid}">${contest.contestName}</a></td>
                                <td>
                                    <div>${contest.beginning}</div>
                                </td>
                                <td>${contest.end}</td>
                                <td style="display: flex;align-items: center;">${contest.nbUser}
                                    <form type="hidden" id="contestRegister/${contest.uuid}">
                                    </form>
                                    <b-button class="form" id="contestRegister/${contest.uuid}">Register</b-button>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>


    <div class="card mt-6">
        <header class="card-header">
            <p class="subtitle is-4 p-3">
                Toutes les Compétitions
            </p>
        </header>
        <div class="card-content">
            <div class="content">
                <table class="table is-fullwidth is-striped">
                    <thead>
                    <tr>
                        <th>Compétitions</th>
                        <th>Début</th>
                        <th>Durée</th>
                        <th>Inscrits</th>
                    </tr>
                    </thead>
                    <tbody>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <script>
        $(document).ready(function () {
            $('.form').on('click', function (event) {
                event.preventDefault();

                var urlSubmit = $(this).attr('id');

                var formData = ""

                $.ajax({
                    type: "POST",
                    url: urlSubmit,
                    data: formData,
                    dataType: "json",
                    success: function (data) {
                        console.log("registered")
                    },
                    error: function (error) {

                        console.log("nop")
                        console.error("Error:", error);
                    }
                })
            })
            // var data = $('.form').map(function () {
            //     console.log($(this))
            //     console.log($(this.attributes.form)[0].nodeValue)
            //     var urlSubmit = $(this.attributes.form)[0].nodeValue
            //     $(this).submit(function (event) {
            //         // event.preventDefault(); // Prevent form submission
            //
            //         $.ajax({
            //             type: "POST",
            //             url: urlSubmit,
            //             data: formData,
            //             dataType: "json",
            //             // success: function (data) {
            //             //     $("#successNotification").remove();
            //             //     if (!data.success) {
            //             //         $("#password").val("");
            //             //         $("#errorNotification").html(data.message).removeClass("is-invisible");
            //             //     } else {
            //             //         window.location.href = data.redirect;
            //             //     }
            //             // },
            //             // error: function (error) {
            //             //     console.error("Error:", error);
            //             // }
            //         });
            //     });
            // });
        });
    </script>
    <script>
        new Vue({
            el: '#appIndex'
        })
    </script>
</@layout.header>
