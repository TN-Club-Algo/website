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
        for (let i = 0; i < fileInput.files.length; i++){
            console.log(i)
            names = names + fileInput.files[i].name + ",";
            console.log(names)
        }
        fileName.textContent = names.slice(0,-1);
    }
}