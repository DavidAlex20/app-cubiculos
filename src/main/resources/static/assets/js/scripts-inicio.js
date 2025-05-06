$(document).ready(function() {
    console.log('Inicio Script - Funcional!');

    $('.btn-evento').on('click', function(event) {
        event.preventDefault();
        let usuario = $("#usuario").val();
        let id = $(this).attr("id");
        console.log(usuario + ' // ' + id);
        if (
          id === undefined ||
          id === null
        ){ 
          Swal.fire({
            title: "¡Espera!",
            text: "ID no valido!",
            icon: "warning"
          })
        } else {
          Swal.fire({
            title: "Evento",
            text: "¿Actualizar pase de lista?",
            icon: "question",
            showCancelButton: true,
            cancelButtonText: "Cancelar",
            confirmButtonText: "Confirmar"
          }).then((result) => {
            if(result.isConfirmed) {
                $.ajax({
                    url: "/dashboard/evento",
                    type: "GET",
                    data: {usuario: usuario, evento: id},
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

    $('.btn-cubiculo').on('click', function(event) {
        event.preventDefault();
        let usuario = $("#usuario").val();
        console.log(usuario);
        
        Swal.fire({
            title: "Cubiculo",
            text: "¿Actualizar pase de lista?",
            icon: "question",
            showCancelButton: true,
            cancelButtonText: "Cancelar",
            confirmButtonText: "Confirmar"
        }).then((result) => {
        if(result.isConfirmed) {
            $.ajax({
                url: "/dashboard/cubiculo",
                type: "GET",
                data: {usuario: usuario},
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
    });

    $('.btn-pausa').on('click', function(event) {
        event.preventDefault();
        let usuario = $("#usuario").val();
        console.log(usuario);
        
        Swal.fire({
            title: "Pausa",
            text: "¿Actualizar pase de lista?",
            icon: "question",
            showCancelButton: true,
            cancelButtonText: "Cancelar",
            confirmButtonText: "Confirmar"
        }).then((result) => {
        if(result.isConfirmed) {
            $.ajax({
                url: "/dashboard/pausa",
                type: "GET",
                data: {usuario: usuario},
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
    });
})