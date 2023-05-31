function myFunction(id) {
    var copyText = document.getElementById(id);

    // Copy the text inside the text field
    navigator.clipboard.writeText(copyText.innerText);
    console.log(copyText.innerText)
}