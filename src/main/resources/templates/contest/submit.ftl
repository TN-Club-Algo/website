<#import "../_layout.ftl" as layout />

<meta charset="UTF-8">
<link rel="stylesheet" href="../static/css/new_problem.css">
<script type="module" src="../static/js/problem_selection.js"
<@layout.header>
    <div id="container">
        <div class="card">
            <div class="card-header">
                Problem Creation
            </div>
            <div class="card-content">
                <form action="/new_problem" method="post" enctype="multipart/form-data" @submit.prevent>
                    <div id="app_test" class="container">
                        <section>
                            <div>
                                Selected:
                                <p v-for="(option, index) in selected" :key="index"
                                   style="display: flex;align-items: center">
                                    <span style="margin-right: 1ch;">{{index + 1}})</span>
                                    <span style="margin-right: 1ch;"><b> Problem Name:</b> {{ option.slug }}</span>
                                    <span style="margin-right: 1ch;"><b> Author:</b> {{ option.author }}</span>
                                    <span style="margin-right: 1ch;"><b> Difficulty:</b> {{ option.difficulty }}</span>
                                    <span style="margin-right: 1ch;"><b> Type:</b> {{ option.type }}</span>
                                    <span style="display: flex;align-items: center"><b> Score:</b>
                                        <b-input :style="{width : inputWidth(index)}"
                                                 type="number"
                                                 maxlength="30"
                                                 :value="option.score"
                                                 @input="event => updateScore(event,index)"
                                                 @keyup.enter.prevent
                                                 :id="concatWidth(index)">

                                            </b-input>
                                    <button type="button" @click="removeSelectedOption(index)">Remove problem</button>
                                </p>
                            </div>
                            <b-field label="Find problems">
                                <b-autocomplete
                                        rounded
                                        :value="name"

                                        <#--                                        v-model="name"-->
                                        :data="filteredDataObj"
                                        placeholder="e.g. ex1"
                                        icon="magnify"
                                        :clearable="clearable"
                                        @input="updateName"
                                        @select="selectOption">
                                    <template #empty>No results found</template>
                                    <template slot-scope="props">
                                        <div>
                                            <span><b>Problem Name:</b> {{ props.option.slug }}</span>
                                            <span><b>Author:</b> {{ props.option.author }}</span>
                                            <span><b>Difficulty:</b> {{ props.option.difficulty }}</span>
                                            <span><b>Type:</b> {{ props.option.type }}</span>
                                        </div>
                                    </template>
                                </b-autocomplete>
                            </b-field>

                            <#--                            <p class="content"><b>Selected:</b> {{ selected }}</p>-->
                            <#--                            <b-field label="Find problems">-->
                            <#--                                <b-autocomplete-->
                            <#--                                        rounded-->
                            <#--                                        v-model="name"-->
                            <#--                                        :data="filteredDataObj"-->
                            <#--                                        field="slug"-->
                            <#--                                        placeholder="e.g. ex1"-->
                            <#--                                        icon="magnify"-->
                            <#--                                        clearable-->
                            <#--                                        @select="option => (selected = option)">-->
                            <#--                                    <template #empty>No results found</template>-->
                            <#--                                </b-autocomplete>-->
                            <#--                            </b-field>-->
                        </section>
                    </div>


                    <div class="field">
                        <label class="label">Contest Name</label>
                        <div class="control">
                            <input class='input' type="text" name="pbName">
                        </div>
                    </div>
                    <div class="field">
                        <label class="label">Statement</label>
                        <div class="control">
                            <textarea class="txtArea" name="statement" rows="15"></textarea>
                        </div>
                    </div>

                    <div class="columns is-mobile">

                        <div class="column">
                            <div class="field">
                                <label class="label">Input</label>
                                <div class="control">
                                    <textarea class="txtArea" name="input" rows="5"></textarea>
                                </div>
                            </div>
                        </div>
                        <div class="column">
                            <div class="field">
                                <label class="label">Output</label>
                                <div class="control">
                                    <textarea class="txtArea" name="output" rows="5"></textarea>
                                </div>

                            </div>
                        </div>
                    </div>


                    <br>
                    <button class="mt-3 button is-fullwidth">
                        Créer la Compétition
                    </button>

                </form>
            </div>
        </div>
    </div>
</@layout.header>
