<#import "../_layout.ftl" as layout />

<meta charset="UTF-8">
<link rel="stylesheet" href="../static/css/new_problem.css">
<script type="module" src="../static/js/problem_selection.js"
<@layout.header>
    <div id="container">
        <div class="card">
            <div class="card-header">
                Contest Creation
            </div>
            <div class="card-content">
                <form action="/contest/submit" method="post" enctype="multipart/form-data" @submit.prevent>
                    <div id="app_test" class="container">
                        <section>
                            <input type="hidden" name="selectedProblems" :value="returnSelected"/>
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
                                    </span>
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
                        </section>
                        <div class="columns">
                            <div class="column">
                                <b-field label="Contest Name">
                                    <b-input placeholder="Integration 2023" rounded name=contestName></b-input>
                                </b-field>
                            </div>
                            <div class="column">
                                <b-field label="Creator Name">
                                    <b-input placeholder="Club Algo" rounded name=creator></b-input>
                                </b-field>
                            </div>
                        </div>
                        <div>
                            <b-field grouped group-multiline>
                                <div class="control">
                                    <b-switch v-model="showWeekNumber">Show week number</b-switch>
                                </div>
                                <div class="control">
                                    <b-switch v-model="enableSeconds">Enable seconds</b-switch>
                                </div>
                                <#--todo correct the datatime picker because hour not working -->
                                <b-field horizontal label="Locale">
                                    <b-select v-model="locale">
                                        <option :value="undefined"></option>
                                        <option value="de-DE">de-DE</option>
                                        <option value="en-CA">en-CA</option>
                                        <option value="en-GB">en-GB</option>
                                        <option value="en-US">en-US</option>
                                        <option value="es-ES">es-ES</option>
                                        <option value="es-MX">es-MX</option>
                                        <option value="fr-CA">fr-CA</option>
                                        <option value="fr-FR">fr-FR</option>
                                        <option value="it-IT">it-IT</option>
                                        <option value="ja-JP">ja-JP</option>
                                        <option value="pt-BR">pt-BR</option>
                                        <option value="ru-RU">ru-RU</option>
                                        <option value="zn-CN">zn-CN</option>
                                    </b-select>
                                </b-field>
                                <b-field horizontal label="First day of week">
                                    <b-select v-model="firstDayOfWeek">
                                        <option :value="undefined"></option>
                                        <option :value="0">Sunday</option>
                                        <option :value="1">Monday</option>
                                        <option :value="2">Tuesday</option>
                                        <option :value="3">Wednesday</option>
                                        <option :value="4">Thursday</option>
                                        <option :value="5">Friday</option>
                                        <option :value="6">Saturday</option>
                                    </b-select>
                                </b-field>
                                <b-field horizontal label="Hour format">
                                    <b-select v-model="hourFormat">
                                        <option :value="undefined"></option>
                                        <option value="12">12</option>
                                        <option value="24">24</option>
                                    </b-select>
                                </b-field>
                            </b-field>
                        </div>
                        <div class="columns">
                            <div class="column">
                                <section>
                                    <b-field label="Select datetime">
                                        <b-datetimepicker
                                                v-model="beginningDate"
                                                rounded
                                                placeholder="Click to select..."
                                                icon="calendar-today"
                                                :icon-right="selected ? 'close-circle' : ''"
                                                icon-right-clickable
                                                @icon-right-click="clearDateTime"
                                                :locale="locale"
                                                :first-day-of-week="firstDayOfWeek"
                                                :datepicker="{ showWeekNumber }"
                                                :timepicker="{ enableSeconds, hourFormat }"
                                                horizontal-time-picker>
                                        </b-datetimepicker>
                                    </b-field>
                                </section>
                                <input type="hidden" name="beginningDate" v-model="beginningDate.toISOString()"/>
                            </div>
                            <div class="column">
                                <b-field grouped group-multiline label="Select end date">
                                    <b-datetimepicker v-model="endDate"
                                                      rounded
                                                      placeholder="Click to select..."
                                                      icon="calendar-today"
                                                      :icon-right="selected ? 'close-circle' : ''"
                                                      icon-right-clickable
                                                      @icon-right-click="clearDateTime"
                                                      :locale="locale"
                                                      :first-day-of-week="firstDayOfWeek"
                                                      :datepicker="{ showWeekNumber }"
                                                      :timepicker="{ enableSeconds, hourFormat }"
                                                      horizontal-time-picker>>

                                        <b-button
                                                label="Today"
                                                type="is-primary"
                                                icon-left="calendar-today"
                                                @click="endDate = new Date()"/>

                                        <b-button
                                                label="Clear"
                                                type="is-danger"
                                                icon-left="close"
                                                outlined
                                                @click="endDate = null"/>
                                    </b-datetimepicker>
                                </b-field>
                                <input type="hidden" name="endDate" v-model="endDate.toISOString()"/>
                            </div>
                        </div>
                        <div>
                            <b-button
                                    label="show"
                                    type="is-primary"
                                    icon-left="calendar-today"
                                    @click="showDate(beginningDate)"/>
                        </div>
                        <b-field label="Description" horizontal>
                            <b-input maxlength="400" type="textarea" name="description"></b-input>
                        </b-field>
                        <br>
                        <button class="mt-3 button is-fullwidth">
                            Créer la Compétition
                        </button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</@layout.header>
