<#import "_layout.ftl" as layout />

<@layout.header>

    <div id="indexApp" class="mt-3">

        <b-carousel :indicator-inside="true">
            <#list keys as key>
                <b-carousel-item>
                    <a href="${key.second}">
                        <b-image class="image" src="${key.first}"></b-image>
                    </a>
                </b-carousel-item>
            </#list>
        </b-carousel>

        <b-message type="is-info mt-2">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce id fermentum quam. Proin sagittis, nibh id
            hendrerit imperdiet, elit sapien laoreet elit
        </b-message>

        <div class="columns">
            <div class="column is-two-fifths">
                Bienvenue sur la plateforme de problèmes d'AlgoTN ! <br>
                Vous pourrez ici découvrir des problèmes d'algorithmique de niveaux diversifiés, qui pourront être
                corrigés en séance de club. <br><br>
                Notre objectif est de permettre à tous de découvrir l'algorithmique et l'importance de la bonne
                structure de donnée ou du bon algorithme face à certains problèmes
            </div>
            <div class="column is-one-fifth"></div>
            <div class="column is-two-fifths">
                Le site est en construction, des fonctionnalités ou des changements esthétiques pourront être ajoutées
                au fur et à mesure. <br>
                Nous nous efforcerons à résoudre tout bug dans les plus brefs délais.
            </div>
        </div>

    </div>

    <script>
        const indexApp = new Vue();
        indexApp.$mount('#indexApp');
    </script>
</@layout.header>