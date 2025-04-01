$(document).ready(function() {
    console.log('Cubiculos Script - Funcional!');

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
            url: "/admin/cubiculos/eliminar",
            type: "GET",
            data: {id: uid},
            success: function(response) {
              Swal.fire({
                title: "¡Registro eliminado!",
                text: response,
                icon: "success"
              }).then(() => {
                window.location = "/admin/cubiculos";
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

    $('.disponible').on('click', function() {
      let uid = $(this).attr('id');
      Swal.fire({
        title: "¿Desea cambiar este registro?",
        text: "# Registro: " + uid,
        icon: "question",
        showCancelButton: true,
        cancelButtonText: "Cancelar",
        confirmButtonText: "Confirmar"
      }).then((result)=>{
        if(result.isConfirmed) {
          $.ajax({
            url: "/admin/cubiculos/disponible",
            type: "GET",
            data: {id: uid},
            success: function(response) {
              Swal.fire({
                title: "¡Registro cambiado!",
                text: response,
                icon: "success"
              }).then(() => {
                window.location = "/admin/cubiculos";
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

    $('.cubid').on('click', function() {
      let id = $(this).attr('id');
      $('#cubid').val(id);
    });

    $('.asignar').on('click', function() {
      let uid = $(this).attr('id');
      let cid = $('#cubid').val();

      Swal.fire({
        title: "¿Desea cambiar este registro?",
        text: "# Cubiculo: " + cid,
        icon: "question",
        showCancelButton: true,
        cancelButtonText: "Cancelar",
        confirmButtonText: "Confirmar"
      }).then((result)=>{
        if(result.isConfirmed) {
          $.ajax({
            url: "/admin/cubiculos/asignar",
            type: "GET",
            data: {id: cid, usuario: uid},
            success: function(response) {
              Swal.fire({
                title: "¡Registro cambiado!",
                text: response,
                icon: "success"
              }).then(() => {
                window.location = "/admin/cubiculos";
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
      let numero = $('#numero').val();
      let edificio = $('#edificio').val();
      let disponible = $('#disponible').val();
      let asignacion = $('#asignacion').val(null);

      if (
        id === undefined || id === null || id === "" ||
        numero === undefined || numero === null || numero === "" ||
        edificio === undefined || edificio === null || edificio === "" ||
        disponible === undefined || disponible === null || disponible === "" ||
        asignacion === undefined || asignacion === ""
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
              url: "/admin/cubiculos/guardar",
              type: "POST",
              data: $(this).serialize(),
              success: function(response) {
                Swal.fire({
                  title: "¡Guardado!",
                  text: response,
                  icon: "success"
                }).then(() => {
                  window.location = "/admin/cubiculos";
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