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
      locations : data,
      scripts : [{'script' : 'locations.js'}]
    } );
  });
});

router.post('/', function(req,res,next){

  var dat = {
    longitude : req.body.longitude,
    latitude : req.body.latitude,
    captured : req.body.captured
  };

  if (dat.longitude !== undefined && dat.latitude !== undefined)
  {
    locationsdata.insert(dat,function(err,data){
      if (err) return next(err);

      try {
        res.app.render('partials/location', {
          _id : data._id ,
          longitude : data.longitude,
          latitude : data.latitude,
          captured : data.captured,
          created : data.created,
          layout: 'none'
        }, function(err, html){
          if (err) return next(err);
          console.log(html);
          //res.send(data);
          res.send(html);
        });
      } catch (e) {
        console.log(e);
      }
    });
  }

});

module.exports = router;
