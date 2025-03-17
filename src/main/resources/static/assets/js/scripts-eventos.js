$(document).ready(function() {
    console.log('Eventos Script - Funcional!');

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
            url: "/admin/eventos/eliminar",
            type: "GET",
            data: {id: uid},
            success: function(response) {
              Swal.fire({
                title: "¡Registro eliminado!",
                text: response,
                icon: "success"
              }).then(() => {
                window.location = "/admin/eventos";
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
      let titulo = $('#titulo').val();
      let lugar = $('#lugar').val();
      let inicio = $('#inicio').val();
      let fin = $('#fin').val();
      let fecha = $('#fecha').val();

      if (
        id === undefined || id === null || id === "" ||
        titulo === undefined || titulo === null || titulo === "" ||
        lugar === undefined || lugar === null || lugar === "" ||
        inicio === undefined || inicio === null || inicio === "" ||
        fin === undefined || fin === null || fin === "" ||
        fecha === undefined || fecha === null || fecha === ""
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
              url: "/admin/eventos/guardar",
              type: "POST",
              data: $(this).serialize(),
              success: function(response) {
                Swal.fire({
                  title: "¡Guardado!",
                  text: response,
                  icon: "success"
                }).then(() => {
                  window.location = "/admin/eventos";
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