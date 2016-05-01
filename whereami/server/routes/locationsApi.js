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

  console.log(req.body);

  if (dat.longitude !== undefined && dat.latitude !== undefined)
  {
    locationsdata.insert(dat,function(err,data){
      // load partial and apply data

      res.send(dat);
    });
  }

});

router.put('/', function(req,res,next){

  var id = req.body.id;
  var dat = {
    longitude : req.body.longitude,
    latitude : req.body.latitude,
    captured : req.body.captured
  };

  console.log(id);

  if (id !== undefined)
  {
    locationsdata.update(id, dat,function(err,data){
      if (err) return next(err);
      res.send(data);
    });
  }
});

router.delete('/', function(req, res, next) {

  var id = req.body.id;

  // call data helper method
  if (id !== undefined)
  {
    locationsdata.delete(id,function(err,data){
      if (err) return next(err);
      console.log(data);
      res.send(data);
    });
  }
  else {
    res.send("id is empty");
  }
});

module.exports = router;
