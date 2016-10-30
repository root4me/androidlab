var express = require('express');
var router = express.Router();
var authenticator = require('./authenticator');

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
    scripts : [ {'script' : '/javascripts/index.js'} ]
  });
});

router.get('/login',function(req, res, next){
  res.render('login',{
    scripts : [{'script' : '/javascripts/login.js'}]
  });
});

router.post('/login',authenticator.authenticateWeb, function(req, res, next){
  //res.redirect('/');
});

module.exports = router;
