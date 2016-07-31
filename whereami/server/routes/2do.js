/**
* /2do routes
*
*/
var express = require('express');
var router = express.Router();

// Include the data helper
var tododata = require('../data/2do');

router.get('/', function(req, res, next) {

  tododata.getAll(function(err,data){
    // Going to get an array of rows from getAll. wrap it around a selector before supplying it to the view
    res.render('2do/index', {
      tasks : data,
      scripts : [{'script' : '/javascripts/2do.js'}]
    } );
  });
});


router.post('/', function(req,res,next){

  var dat = {
    task : req.body.task,
    comment : req.body.comment
  };

  if (dat.task !== undefined)
  {
    tododata.insert(dat,function(err,data){
      if (err) return next(err);

      try {
        res.app.render('partials/2do', {
          _id : data._id ,
          task : data.task,
          comment : data.comment,
          created : data.created,
          updated : data.updated,
          layout: 'none'
        }, function(err, html){
          if (err) return next(err);
//          console.log(html);

          res.send(html);
        });
      } catch (e) {
//        console.log(e);
      }
    });
  }

});

module.exports = router;
