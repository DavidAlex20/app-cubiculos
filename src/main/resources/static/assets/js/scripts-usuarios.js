$(document).ready(function() {
    console.log('Usuarios Script - Funcional!');

    $('#password').val('App@' + new Date().getFullYear());

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
            url: "/admin/usuarios/eliminar",
            type: "GET",
            data: {id: uid},
            success: function(response) {
              Swal.fire({
                title: "¡Registro eliminado!",
                text: response,
                icon: "success"
              }).then(() => {
                window.location = "/admin/usuarios";
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

      let username = $('#username').val();
      let password = $('#password').val();
      let role = $('#role').val();
      let nombres = $('#nombres').val();
      let apellidos = $('#apellidos').val();
      let numempleado = $('#numempleado').val();
      let status = $('#status').val();
      let activo = $('#activo').val();
      let email = $('#email').val();

      if (
        username === undefined || username === null || username === "" ||
        password === undefined || password === null || password === "" ||
        role === undefined || role === null || role === "" ||
        nombres === undefined || nombres === null || nombres === "" ||
        apellidos === undefined || apellidos === null || apellidos === "" ||
        numempleado === undefined || numempleado === null || numempleado === "" ||
        status === undefined || status === null || status === "" ||
        activo === undefined || activo === null || activo === "" ||
        email === undefined || email === null || email === ""
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
              url: "/admin/usuarios/guardar",
              type: "POST",
              data: $(this).serialize(),
              success: function(response) {
                Swal.fire({
                  title: "¡Guardado!",
                  text: response,
                  icon: "success"
                }).then(() => {
                  window.location = "/admin/usuarios";
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