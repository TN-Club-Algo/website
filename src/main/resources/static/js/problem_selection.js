import Vue from 'vue'

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

const app = new Vue(example)
app.$mount('#app')
// $.ajax({
//     url: "/get_problems",
//     type: 'GET',
//     success: function(response) {
//         var problemList = response.data;
//         console.log(problemList);
//     }
// });
// console.log($.getJSON('/get_problems'))