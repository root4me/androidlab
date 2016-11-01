/**
 * /sh routes
 *
 */
var express = require('express');
var router = express.Router();

var Client = require('node-rest-client').Client;
var client = new Client();

// Include the data helper
var smarthomedata = require('../data/smarthome');
var authenticator = require('./authenticator');

// Get status for all sensors
router.get('/', authenticator.isAuthenticatedWeb, function(req, res, next) {

  smarthomedata.getAll(function(err, data) {
    res.render('smarthome/index', {
      gd_status: 'close',
      gd_updated: ' ',
      scripts: [{
        'script': '/javascripts/sh.js'
      }]
    });
  });
});


module.exports = router;
