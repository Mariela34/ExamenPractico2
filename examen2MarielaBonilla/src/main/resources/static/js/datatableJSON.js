$(document).ready(function() {

    $('#id_workshops').DataTable( {
        'sAjaxSource': 'http://localhost:8092/workshopsjson',
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
