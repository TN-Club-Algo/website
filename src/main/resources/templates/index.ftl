<#import "_layout.ftl" as layout />

<@layout.header>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bulma-carousel@4.0.3/dist/css/bulma-carousel.min.css">
    <section class="hero is-medium has-carousel">
        <div id="carousel" class="hero-carousel">
            <#list keys as key>
                <div class="item-${key_index}">
                    <a href="${key.second}">
                        <img src="${key.first}" alt="Slide ${key_index}">
                    </a>
                </div>
            </#list>
        </div>
        <div class="hero-head"></div>
        <div class="hero-body"></div>
        <div class="hero-foot"></div>
    </section>

    <script src="https://cdn.jsdelivr.net/npm/bulma-carousel@4.0.3/dist/js/bulma-carousel.min.js"></script>
    <script>
        bulmaCarousel.attach('#carousel', {
            slidesToScroll: 1,
            slidesToShow: 1,
            infinite: true
        });
    </script>
</@layout.header>