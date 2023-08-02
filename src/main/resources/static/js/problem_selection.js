// async function fetchData() {
//     try {
//         const response = await fetch('/get_problems');
//         const json = await response.json();
//         return json;
//     } catch (error) {
//         console.error('Error fetching data:', error);
//         return [];
//     }
// }
// async function createVueInstance() {
//     const json = await fetchData();
//
//     let json_problem = json.map(item => JSON.parse(item));
//     console.log(json_problem);
//
//     const example = {
//         data() {
//             return {
//                 data: json_problem,
//                 name: '',
//                 selectedOption: null
//             };
//         },
//         computed: {
//             filteredDataObj() {
//                 return this.data.filter(item => {
//                     return (
//                         item.slug.toLowerCase().indexOf(this.name.toLowerCase()) >= 0
//                     );
//                 });
//             }
//         },
//     };
//
//     const app = new Vue(example);
//     app.$mount('#app_test');
// }
//
// createVueInstance();
const problems = $.getJSON('/get_problems', function (json) {
    // console.log(problems)
    // console.log(problems["responseJSON"])
    // let json_problem = problems["responseJSON"].map((x) => Object.entries(JSON.parse(x)))
    // let json_problem = problems["responseJSON"].map((obj) => {
    //     const jsonObj = JSON.parse(JSON.stringify(Object.fromEntries(obj)));
    //     return jsonObj;
    // });
    // console.log(json_problem)
    // console.log(typeof(problems["responseJSON"][0]))
    // console.log(json)
    // let json_problem = json.map(obj => ({
    //     name: obj.slug,
    //     difficulty: obj.difficulty,
    //     author: obj.author,
    //     type: obj.type,
    //     //...obj, // Spread all other attributes
    // }));
    let json_problem = json.map(item => JSON.parse(item));
    console.log(json_problem);
    console.log(json_problem[0])

    const example = {
        data() {
            return {
                data: json,
                name: '',
                selected: [],
                clearable: true
            }
        },
        methods: {
            selectOption(option) {
                console.log("select update")
                console.log(option)
                if (option) {
                    console.log("not null")
                    console.log(this.selected)
                    this.selected.push(option)
                    this.name = ""
                    console.log(this.selected);
                }
            },
            removeSelectedOption(index) {
                this.selectedOption = [];
            },
            updateName(event) {
                console.log("name update")
                console.log(event)
                this.name = event;
            },
        },
        computed: {
            filteredDataObj() {
                return this.data.filter(item => {
                    console.log(item)
                    console.log(this.name)
                    return (
                        item.toLowerCase().indexOf(this.name.toLowerCase()) >= 0
                    );
                });
            }
        },
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