<#import "../_layout.ftl" as layout />

<title>Liste des Compétitions</title>
<@layout.header>
    <#if errorMessage??>
        <div class="notification is-danger">${errorMessage}</div>
    </#if>

    <div id="appIndex">

        <b-message type="mt-6 mb-6">
            Vous pouvez vous inscrire à une compétition même si elle a débuté.<br>
            <#if !userContests??>
                <p class="has-text-danger">Vous devez être connecté pour vous inscrire à une compétition.</p>
            </#if>
        </b-message>

        <div class="card">
            <header class="card-header" style="position: relative;height: 54px;">
                <p class="subtitle is-4 p-3">
                    Compétitions en cours
                </p>
                <#if hasAuthority?? && hasAuthority>
                    <div class="container">
                        <b-button id="head_centered" tag="a"
                                  href="/contest/submit"
                                  target="_blank"
                                  style="position: absolute;top: 50%;transform: translateY(-50%);right: 20px;">
                            Créer une Compétition
                        </b-button>
                    </div>
                </#if>
            </header>
            <div>
                <div class="card-content">
                    <div class="content">
                        <table class="table is-fullwidth is-striped">
                            <thead>
                            <tr>
                                <th>Compétition</th>
                                <th>Début</th>
                                <th>Durée restante</th>
                                <th>Inscrits</th>
                            </tr>
                            </thead>
                            <tbody>
                            <#list currentContests as contest>
                                <tr>
                                    <td><a href="/contest/${contest.uuid}">${contest.name}</a></td>
                                    <td>
                                        <div>${contest.getBeginningFormatted()}</div>
                                    </td>
                                    <td>${contest.getRemainingDurationFormatted()}</td>
                                    <td style="display: flex;align-items: center;">${contest.registeredUser?size}
                                        <#if userContests?? && !userContests?seq_contains(contest)>
                                            <form type="hidden" id="contestRegister/${contest.uuid}">
                                            </form>
                                            <button class="form button ml-2 is-small"
                                                    id="contestRegister/${contest.uuid}">
                                                S'y inscrire
                                            </button>
                                        <#elseif userContests?? && userContests?seq_contains(contest)>
                                            <span class="tag is-success ml-2">
                                        ENREGISTRÉ
                                    </span>
                                        </#if>
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
                    Toutes les compétitions
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
                        <#list contests as contest>
                            <tr>
                                <td><a href="/contest/${contest.uuid}">${contest.name}</a></td>
                                <td>
                                    <div>${contest.getBeginningFormatted()}</div>
                                </td>
                                <td>${contest.getDurationFormatted()}</td>
                                <td style="display: flex;align-items: center;">${contest.registeredUser?size}
                                    <#if userContests?? && !userContests?seq_contains(contest)>
                                        <form type="hidden" id="contestRegister/${contest.uuid}">
                                        </form>
                                        <button class="form button ml-2 is-small" id="contestRegister/${contest.uuid}">
                                            S'y
                                            inscrire
                                        </button>
                                    <#elseif userContests?? && userContests?seq_contains(contest)>
                                        <span class="tag is-success ml-2">
                                        ENREGISTRÉ
                                    </span>
                                    </#if>
                                </td>
                            </tr>
                        </#list>
                        </tbody>
                    </table>
                </div>
            </div>
        </div>
    </div>

    <script>
        $(document).ready(function () {
            $('.form').on('click', function (event) {
                event.preventDefault();

                const urlSubmit = $(this).attr('id');
                const formData = "";

                $.ajax({
                    type: "POST",
                    url: urlSubmit,
                    data: formData,
                    dataType: "json",
                    success: function (data) {
                    },
                    error: function (error) {
                    }
                })
            })
        });
    </script>
    <script>
        new Vue({
            el: '#appIndex'
        })
    </script>
</@layout.header>
