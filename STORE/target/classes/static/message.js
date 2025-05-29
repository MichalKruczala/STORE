function showMessage() {
    var messageDiv = document.getElementById("message");
    if(messageDiv.textContent != "") {
        messageDiv.style.display = "block";
        setTimeout(function(){
            document.getElementById("message").style.display = "none";
            }, 3000);
    }
}

window.onload = showMessage;