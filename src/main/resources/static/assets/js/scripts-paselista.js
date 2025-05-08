$(document).ready(function() {
    console.log('Paselista Script - Funcional!');

    $('.delete').on('click', function() {
      let uid = $(this).attr('id');
      Swal.fire({
        title: "¿Desea eliminar este registro?",
        text: "# Registro: " + uid,
        icon: "question",
        showCancelButton: true,
        cancelButtonText: "Cancelar",
        confirmButtonText: "Confirmar"
      }).then((result)=>{
        if(result.isConfirmed) {
          $.ajax({
            url: "/admin/paselista/eliminar",
            type: "GET",
            data: {id: uid},
            success: function(response) {
              Swal.fire({
                title: "¡Registro eliminado!",
                text: response,
                icon: "success"
              }).then(() => {
                window.location = "/admin/paselista";
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
      });
    });

    $('#save').submit(function(event) {
      event.preventDefault();

      let id = $('#id').val();
      let usuario = $('#usuario').val();
      let evento = $('#evento').val();
      let cubiculo = $('#cubiculo').val();
      let fecha = $('#fecha').val();
      let inicio = $('#inicio').val();
      let fin = $('#fin').val();
      let pausainicio = $('#pausainicio').val();
      let pausafin = $('#pausafin').val();

      console.log(id);
      if (
        id === undefined ||
        usuario === undefined || usuario === null || usuario === "" ||
        evento === undefined ||
        cubiculo === undefined ||
        fecha === undefined || fecha === null || fecha === "" ||
        inicio === undefined || inicio === null || inicio === "" ||
        fin === undefined ||
        pausainicio === undefined ||
        pausafin === undefined
      ){ 
        Swal.fire({
          title: "¡Espera!",
          text: "Por favor llenar todos los campos.",
          icon: "warning"
        })
      } else {
        Swal.fire({
          title: "¡Perfecto!",
          text: "Todos los campos llenos, ¿desea guardar el registro?",
          icon: "question",
          showCancelButton: true,
          cancelButtonText: "Cancelar",
          confirmButtonText: "Confirmar"
        }).then((result) => {
          if(result.isConfirmed) {
            $.ajax({
              url: "/admin/paselista/guardar",
              type: "POST",
              data: $(this).serialize(),
              success: function(response) {
                Swal.fire({
                  title: "¡Guardado!",
                  text: response,
                  icon: "success"
                }).then(() => {
                  window.location = "/admin/paselista";
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