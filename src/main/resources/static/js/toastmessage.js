$(document).ready(function () {
    $('.state select').selectpicker({
        size: false
    });
});

document.getElementById('salary').addEventListener('input', event =>
    event.target.value = (parseInt(event.target.value.replace(/[^\d]+/gi, '')) || 0).toLocaleString('en-US')
);