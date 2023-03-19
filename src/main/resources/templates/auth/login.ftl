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
                <div class="vertical-center" style="background-color: rgba(182,182,182,0.27); padding: 2.5rem; border-radius: 0.8rem">
                    <form action="/login" method="post">
                        <h2 class="title has-text-centered">Se connecter</h2>
                        <div class="field">
                            <p class="control has-icons-left">
                                <input class="input" type="text" placeholder="Email">
                                <span class="icon is-small is-left">
                                    <i class="fas fa-envelope"></i>
                                </span>
                            </p>
                        </div>
                        <div class="field">
                            <p class="control has-icons-left">
                                <input class="input" type="password" placeholder="Mot de passe">
                                <span class="icon is-small is-left">
                                    <i class="fas fa-lock"></i>
                                </span>
                            </p>
                        </div>

                        <a href="" class="is-link has-text-black">Un problème pour se connecter ?</a>

                        <button class="mt-3 button is-fullwidth">
                            Se connecter
                        </button>

                        <p class="m-5 has-text-centered">— Vous n'avez pas encore de compte ? —</p>

                        <a href="">
                            <button class="button" style="width: 50%; margin-left: 50%; transform: translateX(-50%)">
                                S'inscrire
                            </button>
                        </a>
                    </form>
                </div>
            </div>
        </div>
    </div>
</@layout.header>