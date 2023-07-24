<#import "./_layout.ftl" as layout /><#-- not an error -->

<title>Test result</title>
<script src="../../static/js/jquery.min.js"></script>
<script src="../../static/js/stomp.js"></script>
<script src="https://cdn.jsdelivr.net/npm/sockjs-client@1/dist/sockjs.min.js"></script>
<meta charset="UTF-8">
<script src="../../static/js/app.js"></script>
<link rel="stylesheet" href="../../static/bulma.min.css">
<@layout.header>
    <div class="card">
        <header class="card-header">
            <p class="subtitle is-4 p-3">
                Problèmes en vedette
            </p>
        </header>
        <div class="card-content">
            <div class="content">
                <table class="table is-fullwidth is-striped">
                    <thead>
                    <tr>
                        <th>Problème</th>
                        <th>Thèmes</th>
                        <th>Difficulté</th>
                        <th>Avancement</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list keys as key>
                        <tr>
                            <td><a href="/problem/${key}">${problems[key].name}</a></td>
                            <td>
                                <#list problems[key].keywords as tag>
                                    <span class="tag">${tag}</span>
                                </#list>
                            </td>
                            <td>
                                <#if problems[key].difficulty gt 0>
                                    <#list 1..problems[key].difficulty as _>
                                        <span class="icon has-text-danger">
                                            🟐
                                        </span>
                                    </#list>
                                </#if>
                                <#if 5 - problems[key].difficulty gt 0>
                                    <#list 1..(5 - problems[key].difficulty) as _>
                                        <span class="icon has-text-grey-light">
                                            🟐
                                        </span>
                                    </#list>
                                </#if>
                            </td>
                            <td>✗</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>


    <template>
        <section>
            <b-table
                    :data="data"
                    :paginated="isPaginated"
                    :per-page="perPage"
                    :current-page.sync="currentPage"
                    :pagination-simple="isPaginationSimple"
                    :pagination-position="paginationPosition"
                    :default-sort-direction="defaultSortDirection"
                    :pagination-rounded="isPaginationRounded"
                    :sort-icon="sortIcon"
                    :sort-icon-size="sortIconSize"
                    default-sort="user.first_name"
                    aria-next-label="Next page"
                    aria-previous-label="Previous page"
                    aria-page-label="Page"
                    aria-current-label="Current page"
                    :page-input="hasInput"
                    :pagination-order="paginationOrder"
                    :page-input-position="inputPosition"
                    :debounce-page-input="inputDebounce">

                <b-table-column field="id" label="ID" width="40" sortable numeric v-slot="props">
                    {{ props.row.id }}
                </b-table-column>

                <b-table-column field="user.first_name" label="First Name" sortable v-slot="props">
                    {{ props.row.user.first_name }}
                </b-table-column>

                <b-table-column field="user.last_name" label="Last Name" sortable v-slot="props">
                    {{ props.row.user.last_name }}
                </b-table-column>

                <b-table-column field="date" label="Date" sortable centered v-slot="props">
                <span class="tag is-success">
                    {{ new Date(props.row.date).toLocaleDateString() }}
                </span>
                </b-table-column>

                <b-table-column label="Gender" v-slot="props">
                <span>
                    <b-icon pack="fas"
                            :icon="props.row.gender === 'Male' ? 'mars' : 'venus'">
                    </b-icon>
                    {{ props.row.gender }}
                </span>
                </b-table-column>

            </b-table>
        </section>
    </template>

    <script>
        const data = require('@/data/sample.json')

        export default {
            data() {
                return {
                    data,
                    isPaginated: true,
                    isPaginationSimple: false,
                    isPaginationRounded: false,
                    paginationPosition: 'bottom',
                    defaultSortDirection: 'asc',
                    sortIcon: 'arrow-up',
                    sortIconSize: 'is-small',
                    currentPage: 1,
                    perPage: 5,
                    hasInput: false,
                    paginationOrder: '',
                    inputPosition: '',
                    inputDebounce: ''
                }
            }
        }
    </script>


    <div class="card mt-6">
        <header class="card-header">
            <p class="subtitle is-4 p-3">
                Tous les problèmes
            </p>
        </header>
        <div class="card-content">
            <div class="content">
                <table class="table is-fullwidth is-striped">
                    <thead>
                    <tr>
                        <th>Problème</th>
                        <th>Thèmes</th>
                        <th>Difficulté</th>
                        <th>Avancement</th>
                    </tr>
                    </thead>
                    <tbody>
                    <#list keys as key>
                        <tr>
                            <td><a href="/problem/${problems[key].slug}">${problems[key].name}</a></td>
                            <td>
                                <#list problems[key].keywords as tag>
                                    <span class="tag">${tag}</span>
                                </#list>
                            </td>
                            <td>
                                <#if problems[key].difficulty gt 0>
                                    <#list 1..problems[key].difficulty as _>
                                        <span class="icon has-text-danger">
                                            🟐
                                        </span>
                                    </#list>
                                </#if>
                                <#if 5 - problems[key].difficulty gt 0>
                                    <#list 1..(5 - problems[key].difficulty) as _>
                                        <span class="icon has-text-grey-light">
                                            🟐
                                        </span>
                                    </#list>
                                </#if>
                            </td>
                            <td>✗</td>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
</@layout.header>