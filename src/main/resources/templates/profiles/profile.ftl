<#import "../_layout.ftl" as layout />

<@layout.header>
    <div id="profileApp" class="columns">
        <div class="column is-one-fifth">
            <template>
                <b-menu>
                    <b-menu-list label="Mon compte">
                        <b-menu-item icon="information-outline" label="Info"></b-menu-item>
                        <b-menu-item icon="account" label="Changer mon mot de passe"></b-menu-item>
                    </b-menu-list>
                    <b-menu-list label="Actions">
                        <b-menu-item label="Logout"></b-menu-item>
                    </b-menu-list>
                </b-menu>
            </template>
        </div>
        <div class="column is-four-fifths">

        </div>
    </div>

    <script>
        const indexApp = new Vue();
        indexApp.$mount('#profileApp');
    </script>
</@layout.header>