
function checkCP(event) {
if (window.location.port != 4000) {
    alert("No copy paste for you!");
    event.preventDefault();
  }
}

function toggleDiv(id) {
  var x = document.getElementById(id);
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}
