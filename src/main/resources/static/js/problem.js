function myFunction(id) {
    var copyText = document.getElementById(id);

    // copyText.select();
    // copyText.setSelectionRange(0, 99999); // For mobile devices

    // Copy the text inside the text field
    navigator.clipboard.writeText(copyText.innerText);
    console.log(copyText.innerText)
}