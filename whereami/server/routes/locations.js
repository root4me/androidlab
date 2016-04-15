/**
* /locations routes
*
*/
var express = require('express');
var router = express.Router();

// Include the data helper
var locationsdata = require('../data/locations');

router.get('/', function(req, res, next) {

  locationsdata.getAll(function(err,data){
    // Going to get an array of rows from getAll. wrap it around a selector before supplying it to the view
    res.render('locations/index', {
      countries : data
    } );
  });
});

router.post('/', function(req,res,next){

  // gather values from data element and send it to  insert method
  var dat = {
    longitude : req.body.txtLongitude,
    latitude : req.body.txtLatitude,
    captured : req.body.txtCaptured
  };

  locationsdata.insert(dat, function(err,data){
    res.send(dat);
  });

});


module.exports = router;
