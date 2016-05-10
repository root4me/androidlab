var express = require('express');
var router = express.Router();


/* GET home page. */
router.get('/api/', function(req, res, next) {

  var status = { status: "alive" };
  res.json(status);

});

router.get('/home', function(req, res, next) {

  res.render("home" ,{
    scripts : [{'script' : 'scrollnfade.js'} , {'script' : 'home.js'} ]
  });
});

router.get('/', function(req, res, next) {
  console.log(req.ip);
  console.log(req.ips);

  res.render("index" ,{
    ip: (req.ip).replace('::ffff:',''),
    scripts : [ {'script' : '/javascripts/index.js'} , {'script' : '/javascripts/site.js'}  ]
  });
});

module.exports = router;
