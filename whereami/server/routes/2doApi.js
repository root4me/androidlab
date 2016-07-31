/**
 * /2do api routes
 *
 */
var express = require('express');
var router = express.Router();

// Include the data helper
var tododata = require('../data/2do');

router.get('/', function(req, res, next) {

  // call data helper method
  tododata.getAll(function(err, data) {
    res.send(data);
  });
});

router.post('/', function(req, res, next) {

  var dat = {
    task: req.body.task,
    comment: req.body.comment
  };

  //  console.log(req.body);

  if (dat.task !== undefined && dat.comment !== undefined) {
    tododata.insert(dat, function(err, data) {
      // load partial and apply data

      res.send(dat);
    });
  }

});


router.put('/', function(req, res, next) {

  var id = req.body.id;
  var dat = {
    task: req.body.task,
    comment: req.body.comment
  };

  //  console.log(id);

  if (id !== undefined) {
    tododata.update(id, dat, function(err, data) {
      if (err) return next(err);
      res.send(data);
    });
  }
});


router.delete('/', function(req, res, next) {

  var id = req.body.id;

  // call data helper method
  if (id !== undefined) {
    tododata.delete(id, function(err, data) {
      if (err) return next(err);
      //      console.log(data);
      res.send(data);
    });
  } else {
    res.send("id is empty");
  }
});

module.exports = router;
