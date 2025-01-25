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
            À l'occasion du TN event et  à l'image d'une école d'informatique, le club algo vous propose de mesurer vos capacités dans une de nos disciplines fétiche : l'algorithmie.  🎉<br><br>

            L'ingénieux club Algo s'est mis à la tâche pour vous en faire voir de toutes les couleurs sur des défis de toutes difficultés.<br><br>

            Affrontez-vous ce <b>8 février</b>. 🏆<br><br>
        </b-message>

        <div class="columns">
            <div class="column is-two-fifths">
                <div class="card">
                    <div class="card-content">
                        Bienvenue sur la plateforme de problèmes d'AlgoTN ! <br>
                        Vous pourrez ici découvrir des problèmes d'algorithmique de niveaux diversifiés, qui pourront
                        être
                        corrigés en séance de club. <br><br>
                        Notre objectif est de permettre à tous de découvrir l'algorithmique et l'importance de la bonne
                        structure de donnée ou du bon algorithme face à certains problèmes
                    </div>
                </div>
            </div>
            <div class="column is-one-fifth"></div>
            <div class="column is-two-fifths">
                <div class="card">
                    <div class="card-content">
                        Le site est en construction, des fonctionnalités ou des changements esthétiques pourront être
                        ajoutées
                        au fur et à mesure. <br>
                        Nous nous efforcerons à résoudre tout bug dans les plus brefs délais. N'hésitez pas à en
                        signaler sur Discord ou à suggérer des améliorations !
                    </div>
                </div>
            </div>
        </div>

    </div>

    <script>
        const indexApp = new Vue();
        indexApp.$mount('#indexApp');
    </script>
</@layout.header>