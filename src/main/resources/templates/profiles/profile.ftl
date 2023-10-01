<#import "../_layout.ftl" as layout />

<@layout.header>
    <script>
        <#if user.preferNickname>
        preferNickname = true;
        <#else>
        preferNickname = false;
        </#if>
    </script>
    <div id="profileApp" class="columns" style="width: 90%; margin: auto">
        <div class="column is-one-fifth">
            <template>
                <b-menu>
                    <b-menu-list label="Mon compte">
                        <b-menu-item icon="information-outline" label="Info" active onclick="info()"></b-menu-item>
                        <b-menu-item icon="gift" label="Récompenses" active onclick="awards()"></b-menu-item>
                        <b-menu-item icon="account" label="Changer mon mot de passe"
                                     onclick="changePassword()"></b-menu-item>
                    </b-menu-list>
                    <b-menu-list label="Actions">
                        <b-menu-item label="Se déconnecter" href="/logout"></b-menu-item>
                    </b-menu-list>
                </b-menu>
            </template>
        </div>
        <div class="column is-four-fifths">
            <div id="info" class="card">
                <div class="card-content">
                    <template class="mb-3">
                        <section>
                            <b-notification
                                    auto-close
                                    type="is-success"
                                    duration=10000
                                    v-model="isActive"
                                    aria-close-label="Close notification">
                                Le changement a bien été pris en compte
                            </b-notification>
                        </section>
                    </template>
                    <template class="mb-3">
                        <section>
                            <b-notification
                                    id="errorNotification"
                                    auto-close
                                    type="is-danger"
                                    duration=10000
                                    v-model="isErrorActive"
                                    aria-close-label="Close notification">
                            </b-notification>
                        </section>
                    </template>

                    <form id="infoForm" action="/profile/info" method="POST">
                        <label for="firstName">Prénom</label>
                        <input class="input mt-2 mb-2" type="text" name="firstName" value="${user.firstName}">

                        <label for="lastName">Nom</label>
                        <input class="input mt-2 mb-2" type="text" name="lastName" value="${user.lastName}">

                        <label for="nickname">Surnom</label>
                        <input class="input mt-2 mb-2" type="text" name="nickname" value="${user.nickname}">

                        <b-field>
                            <b-checkbox disabled v-model="preferNickname" oninput="preferNickname = !preferNickname">
                                Préférer l'utilisation du surnom
                            </b-checkbox>
                        </b-field>

                        <label for="email">Email</label>
                        <input class="input mt-2 mb-2" type="email" name="email" value="${user.email}" disabled>

                        <button class="button is-primary">Modifier</button>
                    </form>
                </div>
            </div>
            <div id="changePass" class="card is-hidden">
                <div class="card-content">
                    <template class="mb-3">
                        <section>
                            <b-notification
                                    auto-close
                                    type="is-success"
                                    duration=10000
                                    v-model="isActive2"
                                    aria-close-label="Close notification">
                                Le changement a bien été pris en compte
                            </b-notification>
                        </section>
                    </template>
                    <template class="mb-3">
                        <section>
                            <b-notification
                                    id="errorNotification2"
                                    auto-close
                                    type="is-danger"
                                    duration=10000
                                    v-model="isErrorActive2"
                                    aria-close-label="Close notification">
                            </b-notification>
                        </section>
                    </template>

                    <form id="passwordForm" action="/profile/password" method="POST">
                        <label for="oldPassword">Ancien mot de passe</label>
                        <input class="input mt-2 mb-2" type="password" name="oldPassword"
                               placeholder="Votre ancien mot de passe">

                        <label for="newPassword">Nouveau mot de passe</label>
                        <input class="input mt-2 mb-2" type="password" name="newPassword"
                               placeholder="Votre nouveau mot de passe">

                        <label for="newPasswordConfirm">Confirmer le nouveau mot de passe</label>
                        <input class="input mt-2 mb-2" type="password" name="newPasswordConfirm"
                               placeholder="Confirmer ce nouveau mot de passe">

                        <button class="button is-primary">Modifier</button>
                    </form>
                </div>
            </div>
        </div>
    </div>

    <script>
        const datum = {
            data() {
                return {
                    isActive: false,
                    isErrorActive: false,
                    isActive2: false,
                    isErrorActive2: false,

                    preferNickname: true,
                }
            }
        }

        const indexApp = new Vue(datum);
        indexApp.$mount('#profileApp');
        let info = function () {
            $('#info').removeClass('is-hidden');
            $('#changePass').addClass('is-hidden');
            $('#awards').addClass('is-hidden');
        }

        let awards = function () {
            $('#awards').removeClass('is-hidden');
            $('#info').addClass('is-hidden');
            $('#changePass').addClass('is-hidden');
        }

        let changePassword = function () {
            $('#info').addClass('is-hidden');
            $('#changePass').removeClass('is-hidden');
            $('#awards').addClass('is-hidden');
        }

        $("#infoForm").submit(function (event) {
            event.preventDefault(); // Prevent form submission

            const formData = new FormData(this);

            formData.append("preferNickname", preferNickname);

            $.ajax({
                type: "POST",
                url: "/profile/info",
                data: formData,
                processData: false, // Prevent jQuery from processing the FormData
                contentType: false, // Let the browser set the Content-Type header
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        indexApp.isActive = true;
                    } else {
                        indexApp.isErrorActive = true;
                        $("#errorNotification").text("Une erreur est survenue : " + data.error)
                    }
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        });

        $("#passwordForm").submit(function (event) {
            event.preventDefault(); // Prevent form submission

            const formData = new FormData(this);

            $.ajax({
                type: "POST",
                url: "/profile/password",
                data: formData,
                processData: false, // Prevent jQuery from processing the FormData
                contentType: false, // Let the browser set the Content-Type header
                dataType: "json",
                success: function (data) {
                    if (data.success) {
                        indexApp.isActive2 = true;
                    } else {
                        indexApp.isErrorActive2 = true;
                        $("#errorNotification2").text("Une erreur est survenue : " + data.error)
                    }
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        });
    </script>
</@layout.header>