<#import "../_layout.ftl" as layout />

<title>Vos soumissions</title>
<meta charset="UTF-8">
<link rel="stylesheet" href="../../static/bulma.min.css">
<@layout.header>
    <script type="module" src="../../static/js/app.js"></script>
    <h1 class="title is-1" style="text-align: center">Vos soumissions</h1>

    <b-message class="is-hidden" type="is-warning">
        Vous avez une soumission en file d'attente. Vous êtes placé n°<span id="queuePosition"></span> dans la file
        d'attente.
    </b-message>

    <div>
        <h2 class="subtitle">Vous trouverez ici les soumissions que vous avez réalisé. Si vous venez de soumettre votre
            code, celui-ci devrait bientôt s'afficher ici et vous donner des informations en direct.</h2>
    </div>

    <div id="testApp" class="mt-4">
        <template>
            <section>
                <b-table
                        :data="data"
                        :loading="loading"

                        paginated
                        backend-pagination
                        :total="total"
                        :per-page="10"
                        @page-change="onPageChange"
                        aria-next-label="Next page"
                        aria-previous-label="Previous page"
                        aria-page-label="Page"
                        aria-current-label="Current page"

                        backend-sorting
                        :default-sort-direction="defaultSortOrder"
                        :default-sort="[sortField, sortOrder]"
                        @sort="onSort">

                    <b-table-column field="problem_name" label="Nom du problème" sortable v-slot="props">
                        <a class="is-link" :href="props.row.problem_url">
                            {{ props.row.problem_name }}
                        </a>
                    </b-table-column>

                    <b-table-column field="your_code" label="Votre code" v-slot="props">
                        <a class="is-link" :href="props.row.your_code">
                            Télécharger
                        </a>
                    </b-table-column>

                    <b-table-column field="progress" label="Status" v-slot="props">
                        {{ props.row.progress }}
                    </b-table-column>

                    <b-table-column field="date" label="Date" sortable centered v-slot="props">
                        {{
                            props.row.date ? new Date(props.row.date).toLocaleDateString() + " à " + new Date(props.row.date).toLocaleTimeString().split(":").slice(0, 2).join(":") : 'unknown'
                        }}
                    </b-table-column>

                </b-table>
            </section>
        </template>
    </div>
</@layout.header>