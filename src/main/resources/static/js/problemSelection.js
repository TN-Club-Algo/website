// todo width adjustement not working
const problems = $.getJSON('/get_problems', function (json) {

    let json_problem = json.map(item => {
        let fulljson = JSON.parse(item)
        fulljson["json"] = item
        return fulljson
    });
    console.log(json_problem);
    console.log(json_problem[0])
    const example = {
        data() {
            return {
                data: json_problem,
                name: '',
                selected: [],
                clearable: true,
                beginningDate: new Date(),
                showWeekNumber: false,
                enableSeconds: false,
                hourFormat: undefined, // Browser locale
                locale: undefined, // Browser locale
                firstDayOfWeek: undefined, // 0 - Sunday
                endDate: new Date(),
                returnSelected: []
            }
        },
        methods: {
            parseSelected() {
                console.log("parsing")
                console.log(this.selected)
                if (this.selected.length > 0) {

                    let value = Array()
                    for (let ind = 0; ind < this.selected.length; ind++) {
                        value.push([this.selected[ind].slug, this.selected[ind].score])
                    }
                    console.log("parsing done")
                    this.returnSelected = value
                } else {
                    console.log("parsing done")
                    this.returnSelected = []
                }

            },
            clearDateTime() {
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
                this.parseSelected()
            },
            showDate(date) {
                console.log(date)
            },
            updateScore(event, index) {
                console.log("score update")
                console.log(event)
                this.selected[index]["score"] = parseInt(event)
                console.log(this.selected)
                this.updateWidth(index)
                this.parseSelected()
            },
            updateWidth(index) {
                console.log("updating width")
                var classname = "widthUpdate" + index
                console.log(classname)
                var inputField = document.getElementById(classname);
                if (!inputField) {
                    console.log("not found")
                } else {
                    var width = this.selected[index]["score"].length + 2; // 8px per character
                    inputField.style.width = width + "ch";
                }
            },
            removeSelectedOption(index) {
                console.log(index)
                this.selected.splice(index, 1);
                this.parseSelected()
            },
            updateName(event) {
                console.log("name update")
                console.log(event)
                if (event) {
                    this.name = event;
                } else {
                    this.name = "";
                }

            },
            concatWidth(index) {
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
    const app = new Vue(example)
    app.$mount('#app_test')

});