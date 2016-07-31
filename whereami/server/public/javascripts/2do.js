$('#editableRows').on('keyup', '[id^=editableRow]', function() {

  //  console.log('edited...')
  //  console.log(this.getAttribute('data-id'));

  $('#saveButton-' + $(this).data("id")).show();
  $('#deleteButton-' + $(this).data("id")).hide();

});



$('#editableRows').on('click', '[id^=saveButton]', function() {
  //  console.log($(this).data("id"));

  var id = $(this).data("id");
  var d = "{ \"id\" : \"" + $(this).data("id") + "\"";

  $(this).closest('[id^=editableRow]').find("div").each(function() {

    if ($(this).data("name") !== undefined) {
      d += ", \"" + $(this).data("name") + "\" : \"" + $(this).text().trim() + "\"";
    }
  });

  d += " }";

  //  console.log(d);
  //  console.log(JSON.parse(d));

  $.ajax({
    url: '/api/2do',
    type: 'PUT',
    data: JSON.parse(d),
    success: function(result) {
      //  console.log(result);
      if (result.n > 0) {
        $('#saveButton-' + id).hide();
        $('#deleteButton-' + id).show();
      }
    },
    error: function(x, status, error) {
      var e = "An error occurred : ( " + x.status + " ) " + x.statusText;
      $('#errorAlert').append(e).show();
    }
  });
});

$('#editableRows').on('click', '[id^=deleteButton]', function() {
  // console.log($(this).data("id"));

  var id = $(this).data("id");
  var d = "{ \"id\" : \"" + id + "\" }";

  $.ajax({
    url: '/api/2do',
    type: 'DELETE',
    data: JSON.parse(d),
    success: function(result) {
      //  console.log(result.n);
      if (result.n > 0) {
        $('#editableRow-' + id).remove();
      }
    },
    error: function(x, status, error) {
      alert("An error occurred: " + status + "nError: " + error);
    }
  });
});

$('#addButton').click(function() {

  var d = "{";

  $(this).closest('[id^=addRow]').find("input").each(function() {

    if ($(this).data("name") !== undefined) {
      if (d.length > 1) {
        d += ",";
      }
      d += " \"" + $(this).data("name") + "\" : \"" + $(this).val().trim() + "\"";
      // clear out text that has been fetched
      $(this).val('');
    }
  });

  d += " }";

  //  console.log(d);
  //  console.log(JSON.parse(d));

  $.ajax({
    url: '/2do',
    type: 'POST',
    data: JSON.parse(d),
    success: function(result) {
      //    console.log(result);
      // add a new row to editable table
      $('#editableRows').append(result);

    },
    error: function(x, status, error) {
      alert("An error occurred: " + status + "nError: " + error);
    }
  });

});
