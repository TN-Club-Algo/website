<#import "_layout.ftl" as layout />

<@layout.header>

    <div id="indexApp">

        <b-carousel :indicator-inside="true">
            <#list keys as key>
                <b-carousel-item>
                    <a href="${key.second}">
                        <b-image class="image" src="${key.first}"></b-image>
                    </a>
                </b-carousel-item>
            </#list>
        </b-carousel>

    </div>

    <script>
        const indexApp = new Vue();
        indexApp.$mount('#indexApp');
    </script>
</@layout.header>