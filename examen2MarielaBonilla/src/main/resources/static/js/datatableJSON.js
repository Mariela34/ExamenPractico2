

function obtenerDatos(){
    fetch("http://localhost:8090/get/workshopsjson", {
        method: 'GET',
        headers:{
            'Content-Type': 'application/json'
        }
    })
        .then(response => response.json())
        .catch(error => console.error('Error:', error))
        .then(data => console.log(data));

}



$(document).ready(function() {

    $('#id_workshops').DataTable( {
        'sAjaxSource': 'http://localhost:8092/get/workshopsjson',
        "sAjaxDataProp": "",
        "order": [[ 0, "asc" ]],
        'aoColumns': [
            { 'mData': 'name' },
            { 'mData': 'autor' },
            { 'mData': 'categoria.name' },
            { 'mData': 'objective' },
            { 'mData': 'keywords' },
            { 'mData': 'tiempoDuracion' }


        ]
    } );
} );
