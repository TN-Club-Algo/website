<#--<!doctype html>-->

<#--<head>-->
<#--    <meta charset="utf-8">-->
<#--    <meta http-equiv="X-UA-Compatible" content="IE=edge">-->
<#--    <meta name="viewport" content="width=device-width, initial-scale=1">-->

<#--    <!-- Include Bootstrap &ndash;&gt;-->
<#--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css"-->
<#--          integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">-->
<#--    <!-- Optional theme &ndash;&gt;-->
<#--    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap-theme.min.css"-->
<#--          integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">-->
<#--</head>-->
<#--<body>-->
<#import "../_layout.ftl" as layout />
<@layout.header>

    <div class="container-fluid">
        <h3 class="display-3">This is an example Vue.js application developed with Spring Boot</h3>
        <p class="lead">This file is rendered by a Spring built-in default controller for index.html (/) using
            Spring's built-in
            Thymeleaf templating engine.
            Although we don't need it per se, we customized the information passed to this
            view from thymeleaf by adding a controller method for "/" route to demonstrate how to pass information from
            Thymeleaf to this page.
            The combination of template engine and frontend framework like Vue can make this a powerful approach to
            build
            more complex applications while leveraging the benefits of a framework like Vue.js.
            You can use thymeleaf features too but this project focuses mainly on using Vue.js on the
            frontend as the framework and makes minimal use of Thymeleaf.
            Also we don't use any routing and multiple components in this example, so what you see is technically a
            Single Page Application (SPA) without any routes configured.
        </p>

        <div id="contents-main">
            <div class="lead">
                <strong>Name of Event:</strong>
                <span text="${eventName}"></span>
            </div>
            <div id="contents">
                <!-- Since we create a Vue component pointing to id=contents,
                     Vue will generate a unordered list of items such
                     as this inside this div.
                     v-for will cause a loop to run over all players
                     as per the players array found in app.data
                <ul>
                    <li></li>
                    <li></li>
                </ul>
                -->
                <ul>
                    <li style="list-style-type:none" v-for="player in players">
                        <player-card
                                v-bind:player="player" v-bind:key="player.id">
                        </player-card>
                    </li>
                </ul>
            </div>
        </div>
    </div>
    <div id="app_test" class="container">
        <section>
            <p class="content"><b>Selected:</b> {{ selected }}</p>
            <b-field label="Find a JS framework">
                <b-autocomplete
                        rounded
                        v-model="name"
                        :data="filteredDataArray"
                        placeholder="e.g. jQuery"
                        icon="magnify"
                        clearable
                        @select="option => selected = option">
                    <template #empty>No results found</template>
                </b-autocomplete>
            </b-field>
        </section>
    </div>
    <!-- include Vue.js -->
    <!-- we include babel js so that we can write ES6 code in the browser
         for a more production like setup it is recommended to setup a build process
         to transpile and minify the code (such as using webpack)
     -->

    <script>
        // player-card is now a Vue component that generates a 'card' showing a player
        // It can be used
        // declaratively like <player-card v-bind:player="someplayer">
        Vue.component('player-card', {
            props: ['player'],
            template: `
              <div class="card">
              <div class="card-body">
                <h6 class="card-title">
                  {{ player.name }}
                </h6>
                <p class="card-text">
                <div>
                  {{ player.description }}
                </div>
                </p>
              </div>
              </div>`
        });

        var apps = new Vue({
            el: '#contents',
            data: {
                players: [
                    {id: "1", name: "Lionel Messi", description: "Argentina's superstar"},
                    {id: "2", name: "Christiano Ronaldo", description: "Portugal top-ranked player"}
                ]
            }
        });

    </script>
    <script>
        const example = {
            data() {
                return {
                    data: [
                        'Angular',
                        'Angular 2',
                        'Aurelia',
                        'Backbone',
                        'Ember',
                        'jQuery',
                        'Meteor',
                        'Node.js',
                        'Polymer',
                        'React',
                        'RxJS',
                        'Vue.js'
                    ],
                    name: '',
                    selected: null
                }
            },
            computed: {
                filteredDataArray() {
                    return this.data.filter((option) => {
                        return option
                            .toString()
                            .toLowerCase()
                            .indexOf(this.name.toLowerCase()) >= 0
                    })
                }
            }
        }

        const app_test = new Vue(example)
        app_test.$mount('#app_test')
    </script>
</@layout.header>