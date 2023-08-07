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
// var input = document.querySelector('.scoreInput.input');
// input.addEventListener('input', resizeInput);
//
// function resizeInput() {
//     this.style.width = this.value.length + "ch";
// }
// import SvgIcon from '/@jamescoyle/vue-icon'
// import { mdiAccount } from '/@mdi/js'
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
    let json_problem = json.map(item => {let fulljson = JSON.parse(item)
        fulljson["json"] = item
        return fulljson
    });
    console.log(json_problem);
    console.log(json_problem[0])
    const example = {
        // components:{
        //     SvgIcon
        // },
        data() {
            return {
                data: json_problem,
                name: '',
                selected: [],
                clearable: true,
                begDate: new Date(),
                showWeekNumber: false,
                enableSeconds: false,
                hourFormat: undefined, // Browser locale
                locale: undefined, // Browser locale
                firstDayOfWeek: undefined, // 0 - Sunday
                endDate: new Date()
            }
        },
        methods: {
            clearDateTime () {
                this.selected = null
            },
            selectOption(option) {
                console.log("select update")
                console.log(option)
                if (option) {
                    console.log("not null")
                    console.log(this.selected)
                    option["score"] = 700
                    this.selected.push(option)
                    this.name = ""
                    console.log(this.selected);
                }
            },
            showDate(date){
              console.log(date)
            },
            updateScore(event,index){
                console.log("score update")
                console.log(event)
                this.selected[index]["score"] = parseInt(event)
                console.log(this.selected)
                this.updateWidth(index)
            },
            updateWidth(index){
                console.log("updating width")
                var classname = "widthUpdate" + index
                console.log(classname)
                var inputField = document.getElementById(classname);
                if (!inputField){
                    console.log("not found")
                }else {
                    var width = this.selected[index]["score"].length + 2; // 8px per character
                    inputField.style.width = width + "ch";
                }
            },
            removeSelectedOption(index) {
                console.log(index)
                // this.selected = [];
                this.selected.splice(index, 1);
            },
            updateName(event) {
                console.log("name update")
                console.log(event)
                if (event){
                    this.name = event;
                }else{
                    this.name = "";
                }

            },
            concatWidth(index){
                return "widthUpdate" + index
            },
        },
        computed: {
            inputWidth() {
                return (option) => {
                    const extraSpace = 5; // You can adjust this value as needed.
                    const maxLength = String(option.score).length;
                    return `${maxLength + extraSpace}ch`;
                };
            },
            filteredDataObj() {
                return this.data.filter(item => {
                    console.log(item)
                    console.log(this.name)
                    return (
                        item.json.toLowerCase().indexOf(this.name.toLowerCase()) >= 0
                    );
                });
            }
        },
    }
    // const timer = setInterval(() => {
    //     console.log("current state of selected")
    //     console.log(example.data().selected)
    // }, 1000);
    const app = new Vue(example)
    app.$mount('#app_test')

});




// $.getJSON('/get_problems', function (json) {
//     appjson.json = json;
// });
// console.log($.getJSON('/get_problems', function (json) {
//     appjson.json = json;
// }))