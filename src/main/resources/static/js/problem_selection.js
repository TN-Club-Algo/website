const problems = $.getJSON('/get_problems', function (json) {
    console.log(problems)
    console.log(problems["responseJSON"])
    const example = {
        data() {
            return {
                data: problems["responseJSON"],
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
    app.$mount('#app_test')

});




// $.getJSON('/get_problems', function (json) {
//     appjson.json = json;
// });
// console.log($.getJSON('/get_problems', function (json) {
//     appjson.json = json;
// }))