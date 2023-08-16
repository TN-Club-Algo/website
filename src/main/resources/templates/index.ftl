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

        <b-message type="is-info mt-6">
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Fusce id fermentum quam. Proin sagittis, nibh id hendrerit imperdiet, elit sapien laoreet elit
        </b-message>

    </div>

    <script>
        const indexApp = new Vue();
        indexApp.$mount('#indexApp');
    </script>
</@layout.header>