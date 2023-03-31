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
                    <#if errorMessage??>
                        <div class="notification is-danger">${errorMessage}</div>
                    </#if>
                    <form action="/password-reset/${token}" method="post">
                        <h2 class="title has-text-centered">Modification de votre mot de passe</h2>
                        <div class="field">
                            <p class="control has-icons-left">
                                <input id="password" class="input" type="text" placeholder="Mot de passe"
                                       name="password" required>
                                <span class="icon is-small is-left">
                                    <i class="fas fa-lock"></i>
                                </span>
                            </p>
                        </div>
                        <div class="field">
                            <p class="control has-icons-left">
                                <input id="password-confirm" class="input" type="text"
                                       placeholder="Confirmer le mot de passe" name="password-confirm" required>
                                <span class="icon is-small is-left">
                                    <i class="fas fa-lock"></i>
                                </span>
                            </p>
                        </div>
                        <button class="mt-3 button is-fullwidth">
                            Changer votre mot de passe
                        </button>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@layout.header>