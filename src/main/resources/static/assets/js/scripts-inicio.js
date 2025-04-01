$(document).ready(function() {
    console.log('Inicio Script - Funcional!');

    $('.paseevento').on('click', function(event) {
        event.preventDefault();
        let usuario = $("#usuario").val();
        let id = $(this).attr("id");
        console.log(usuario + ' // ' + id);

        if (
          id === undefined
        ){ 
          Swal.fire({
            title: "¡Espera!",
            text: "ID no valido!",
            icon: "warning"
          })
        } else {
          Swal.fire({
            title: "Comenzar",
            text: "Desea comenzar el pase de lista?",
            icon: "question",
            showCancelButton: true,
            cancelButtonText: "Cancelar",
            confirmButtonText: "Confirmar"
          }).then((result) => {
            if(result.isConfirmed) {
              $.ajax({
                url: "/dashboard/paselista",
                type: "GET",
                data: {usuario: usuario, id: id},
                success: function(response) {
                  Swal.fire({
                    title: "¡Guardado!",
                    text: response,
                    icon: "success"
                  }).then(() => {
                    window.location = "/dashboard";
                  });
                },
                error: function(xhr) {
                  Swal.fire({
                    title: "Error " + xhr.status,
                    text: xhr.responseText,
                    icon: "error"
                  });
                }
              })
            }
          })
        }
        
      });
})