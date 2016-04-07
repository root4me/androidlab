var express = require('express');
var app = express();
var path = require('path');

console.log(app.get('env'));

app.use('/css', express.static('app/css'));
app.use('/bower_components',express.static('bower_components'));
app.use('/js', express.static('app/js'));

app.get('/', function (req, res) {
  res.json({ status: 'alive' });
});

app.post('/location', function(req, res) {

  var data = {
    _id: 1,
    longitude: "100.123456",
    latitude: "-80.123456",
    clientCreated: "04/06/2016 6:30:00 PM",
    clientCaptured: "04/06/2016 6:29:00 PM",
    created: "04/06/2016 6:40:00 PM";
  };

  dbUtils.save(data, function(err, result) {
    if (!err) {
      console.log(result);
    }
    else {
      console.log(err);
    }
  });
});

app.listen(9000, function () {
  console.log('listening at http://localhost:9000');
});
