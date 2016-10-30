
/*
$('#login').click(function() {

  var d = "{";

  $(this).closest('[id^=addRow]').find("input").each(function() {

    if ($(this).data("name") !== undefined)
    {
          if (d.length > 1) { d += ","; }
      d += " \"" + $(this).data("name") + "\" : \"" + $(this).val().trim() + "\"";
    }
  });

  d += " }";

  console.log(d);
  console.log(JSON.parse(d));

  $.ajax({
    url: '/locations',
    type: 'POST',
    data: JSON.parse(d),
    success: function (result) {
    console.log(result);
    // add a new row to editable table
    $('#editableRows').append(result);
    },
    error: function (x, status, error) {
      alert("An error occurred: " + status + "nError: " + error);
    }
  });

});
*/
