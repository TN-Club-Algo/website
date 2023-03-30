<#import "../_layout.ftl" as layout />
<script src="../../static/js/register.js"></script>
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
                        <div class="notification is-success">${successMessage}</div>
                    </#if>
                    <#if errorMessage??>
                        <div class="notification is-danger">${errorMessage}</div>
                    </#if>
                    <form action="/register" method="post">
                        <h2 class="title has-text-centered">S'inscrire</h2>
                        <div class="field">
                            <p class="control has-icons-left">
                                <input id="username" class="input" type="text" placeholder="Email" name="userName"
                                       required>
                                <span class="icon is-small is-left">
                                    <i class="fas fa-envelope"></i>
                                </span>
                            </p>
                        </div>
                        <div class="field">
                            <p class="control has-icons-left">
                                <input id="password" class="input" type="password" placeholder="Mot de passe"
                                       name="password" required>
                                <span class="icon is-small is-left">
                                    <i class="fas fa-lock"></i>
                                </span>
                            </p>
                        </div>
                        <div class="field">
                            <p class="control has-icons-left">
                                <input id="confirm-password" class="input" type="password"
                                       placeholder="Confirmer le mot de passe" name="confirm-password" required>
                                <span class="icon is-small is-left">
                                    <i class="fas fa-lock"></i>
                                </span>
                            </p>
                        </div>

                        <button class="mt-3 button is-fullwidth">
                            Créer votre compte
                        </button>
                    </form>

                    <p class="m-5 has-text-centered">— Vous avez déjà un compte ? —</p>

                    <a href="/login">
                        <button class="button" style="width: 50%; margin-left: 50%; transform: translateX(-50%)">
                            Se connecter
                        </button>
                    </a>
                </div>
            </div>
        </div>
    </div>
</@layout.header>