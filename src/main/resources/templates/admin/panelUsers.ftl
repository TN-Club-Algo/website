<#import "../_layout.ftl" as layout />
<#import "./panelLayout.ftl" as panelLayout/>

<style>
    #research-panel {
        border-radius: 2px;
        border: 1px #e5e5e5 solid;
        height: 50%;
        background-color: #f0f0f0;
        margin-right: 1%;
    }
    #user-panel {
        border-radius: 2px;
        border: 1px #e5e5e5 solid;
        height: 75%;
        background-color: #f0f0f0;
        margin-left: 1%;
    }
</style>
<@panelLayout.header>
    <div id="panelUsersApp" class="columns mt-4">
        <div id="research-panel" class="column is-one-third">
            <template>
                <section>
                    <b-field label="Rechercher un utilisateur">
                        <b-autocomplete
                                rounded
                                v-model="name"
                                :data="data"
                                :loading="isFetching"
                                placeholder="Rechercher par son nom, prénom, email..."
                                icon="magnify"
                                clearable
                                @typing="getAsyncData"
                                @select="option => selected = option">
                            <template #empty>Aucun utilisateur trouvé</template>
                        </b-autocomplete>
                    </b-field>
                </section>
            </template>
        </div>
        <div id="user-panel" class="column is-two-thirds">

        </div>
    </div>

    <script>
        const datum = {
            data() {
                return {
                    data: [],
                    selected: null,
                    isFetching: false
                }
            },
            methods: {
                loadAsyncData: _.debounce(function (name) {
                    if (!name.length) {
                        this.data = []
                        return
                    }

                    this.isFetching = true
                    axios.get(`/api/users/all?starts_with=${name}`)
                        .then(({ data }) => {
                            this.data = []
                            data.results.forEach((item) => this.data.push(item))
                        })
                        .catch((error) => {
                            this.data = []
                            throw error
                        })
                        .finally(() => {
                            this.isFetching = false
                        })
                }, 500),
            }
        }
        let vue = new Vue(datum)
        vue.$mount("#panelUsersApp")
    </script>
</@panelLayout.header>