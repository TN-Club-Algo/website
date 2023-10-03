<#import "../_layout.ftl" as layout />
<style>
    @media screen and (min-width: 769px) {
        .vertical-center {
            margin: 0;
            position: absolute;
            top: 50%;
            left: 50%;
            -ms-transform: translate(-50%, -50%);
            transform: translate(-50%, -50%);
            width: 40%;
        }
    }

    @media screen and (max-width: 769px) {
        .vertical-center {
            width: 100%;
            margin-top: 50vh;
            -ms-transform: translateY(-50%);
            transform: translateY(-50%);
        }
    }

</style>
<@layout.header>
    <div class="columns" style="height: 100%; width: 100%; margin: 0">
        <div class="column">
            <div>
                <div class="vertical-center"
                     style="background-color: rgba(182,182,182,0.27); padding: 2.5rem; border-radius: 0.8rem">
                    <#if successMessage??>
                        <div id="successNotification" class="notification is-success">${successMessage}</div>
                    </#if>
                    <form id="loginForm" <#--action="/login" method="post"-->>
                        <h2 class="title has-text-centered">Se connecter</h2>
                        <div class="field">
                            <p class="control has-icons-left">
                                <input id="username" name="username" class="input" type="text" placeholder="Email"
                                       autocomplete="username"
                                       required>
                                <span class="icon is-small is-left">
                                    <i class="fas fa-envelope"></i>
                                </span>
                            </p>
                        </div>
                        <div class="field">
                            <p class="control has-icons-left">
                                <input id="password" name="password" class="input" type="password"
                                       autocomplete="current-password"
                                       placeholder="Mot de passe" required>
                                <span class="icon is-small is-left">
                                    <i class="fas fa-lock"></i>
                                </span>
                            </p>
                        </div>
                    </form>

                    <a href="/password-reset" class="is-link has-text-black">Un problème pour se connecter ?</a>

                    <button form="loginForm" class="mt-3 button is-fullwidth">
                        Se connecter
                    </button>

                    <a href="/oauth2/authorization/google">
                        <button class="button" id="google-login">
                            Se connecter avec TN.net
                        </button>
                    </a>

                    <!-- fail notification -->
                    <div id="errorNotification" class="notification is-danger is-small is-invisible"></div>

                    <p class="m-5 has-text-centered">— Vous n'avez pas encore de compte ? —</p>

                    <a href="/register">
                        <button class="button" style="width: 50%; margin-left: 50%; transform: translateX(-50%)">
                            S'inscrire
                        </button>
                    </a>
                </div>
            </div>
        </div>
    </div>

    <style>
        .notification.is-danger.is-small {
            margin-top: 1rem;
            opacity: 0.8;
            padding: 0.5rem 0.75rem;
        }
    </style>

    <script type="application/javascript">
        $("#loginForm").submit(function (event) {
            event.preventDefault(); // Prevent form submission

            const formData = $(this).serialize();
            $.ajax({
                type: "POST",
                url: "/login",
                data: formData,
                dataType: "json",
                success: function (data) {
                    $("#successNotification").remove();
                    if (!data.success) {
                        $("#password").val("");
                        $("#errorNotification").html(data.message).removeClass("is-invisible");
                    } else {
                        window.location.href = data.redirect;
                    }
                },
                error: function (error) {
                    console.error("Error:", error);
                }
            });
        });
    </script>

</@layout.header>