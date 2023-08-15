// const previous = document.getElementsByName("prog").value;
// // document.getElementsByName("lang").on("change",Function() {
// //
// // })
const file = document.querySelector('#myfiles');
const fileInput = file.querySelector("input");
fileInput.onchange = () => {
    if (fileInput.files.length > 0) {
        const fileName = document.querySelector('#myfiles .file-name');
        let names = "";
        for (let i = 0; i < fileInput.files.length; i++) {
            names = names + fileInput.files[i].name + ",";
        }
        fileName.textContent = names.slice(0, -1);
    }
}

var editor = ace.edit("editor");

editor.session.setMode("ace/mode/python");

$("#language").on("change", function () {
    var mode = $(this).find(":selected").val();
    if (mode === "c") mode = "c_cpp";
    editor.session.setMode("ace/mode/" + mode);
});