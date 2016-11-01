$('#gd_refresh').click(function() {

  //  console.log(d);
  //  console.log(JSON.parse(d));

  $.ajax({
    url: '/api/sh/gd',
    type: 'GET',
    success: function(result) {
//      console.log(result);
      $('#gd_status').text(result.status);
      $('#gd_updated').text(result.updated);

    },
    error: function(x, status, error) {
      alert("An error occurred: " + status + "nError: " + error);
    }
  });

});
