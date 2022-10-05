window.setTimeout(function() {
    a = document.getElementsByClassName("alert");
    a.fadeTo(500, 0).slideUp(500, function(){
        a.remove();
    });
}, 4000);

$(document).ready(function(){
    $('.state select').selectpicker({
        size: false
    });
});

document.getElementById('salary').addEventListener('input', event =>
    event.target.value = (parseInt(event.target.value.replace(/[^\d]+/gi, '')) || 0).toLocaleString('en-US')
);