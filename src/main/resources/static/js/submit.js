// const previous = document.getElementsByName("prog").value;
// // document.getElementsByName("lang").on("change",Function() {
// //
// // })
const file = document.querySelector('#myfiles');
const fileInput = file.querySelector("input");
// console.log(fileInput.textContent);
fileInput.onchange = () => {
    if (fileInput.files.length > 0) {
        const fileName = document.querySelector('#myfiles .file-name');
        let names = "";
        for (let i = 0; i < fileInput.files.length; i++) {
            console.log(i)
            names = names + fileInput.files[i].name + ",";
            console.log(names)
        }
        fileName.textContent = names.slice(0, -1);
    }
}

var editor = ace.edit("editor");

editor.session.setMode("ace/mode/python");

$("#language").on("change", function () {
    console.log($(this).find(":selected"))
    var mode = $(this).find(":selected").val();
    if (mode === "c") mode = "c_cpp";
    editor.session.setMode("ace/mode/" + mode);
});