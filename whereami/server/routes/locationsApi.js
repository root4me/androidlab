/**
* /locations routes
*
*/
var express = require('express');
var router = express.Router();

// Include the data helper
var locationsdata = require('../data/locations');

router.get('/', function(req, res, next) {

    // call data helper method
  locationsdata.getAll(function(err,data){
    res.send(data);
  });
});

router.post('/', function(req,res,next){

  var dat = {
    longitude : req.body.longitude,
    latitude : req.body.latitude,
    captured : req.body.captured
  };

/*
  locationsdata.insert(function(err,data){
    res.send(data);
  });
*/

if (req.body.longitude != undefined && req.body.longitude.trim().length>0)
{
  console.log(req.body.longitude.trim().length);
}

res.send(dat);

});

module.exports = router;
