$(document).ready(function() {
    console.log('Reportes Script - Funcional!');

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
            url: "/admin/reportes/eliminar",
            type: "GET",
            data: {id: uid},
            success: function(response) {
              Swal.fire({
                title: "¡Registro eliminado!",
                text: response,
                icon: "success"
              }).then(() => {
                window.location = "/admin/reportes";
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

    $('.update').on('click', function() {
      let uid = $(this).attr('id');
      Swal.fire({
        title: "¿Desea actualizar este registro?",
        text: "# Registro: " + uid,
        icon: "question",
        showCancelButton: true,
        cancelButtonText: "Cancelar",
        confirmButtonText: "Confirmar"
      }).then((result)=>{
        if(result.isConfirmed) {
          $.ajax({
            url: "/admin/reportes/actualizar",
            type: "GET",
            data: {id: uid},
            success: function(response) {
              Swal.fire({
                title: "¡Registro actualizado!",
                text: response,
                icon: "success"
              }).then(() => {
                window.location = "/admin/reportes";
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
      let semanainicio = $('#semanainicio').val();
      let semanafin = $('#semanafin').val();

      console.log(id);
      if (
        id === undefined ||
        usuario === undefined || usuario === null || usuario === "" ||
        semanainicio === undefined ||
        semanafin === undefined
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
              url: "/admin/reportes/guardar",
              type: "POST",
              data: $(this).serialize(),
              success: function(response) {
                Swal.fire({
                  title: "¡Guardado!",
                  text: response,
                  icon: "success"
                }).then(() => {
                  window.location = "/admin/reportes";
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