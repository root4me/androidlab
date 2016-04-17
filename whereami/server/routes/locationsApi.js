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

if (req.body.longitude != undefined && req.body.longitude.trim().length>0)
{
  locationsdata.insert(dat,function(err,data){
    res.send(dat);
  });
}

});

router.delete('/', function(req, res, next) {

  var id = req.body.id;

    // call data helper method
  locationsdata.delete(id,function(err,data){
    res.send(data);
  });
});

module.exports = router;
