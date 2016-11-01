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

/*
{
  device: 'Garage Door Monitor',
  status: 'open', // open, close or unknown
  updated: '10/30/2016 10:00:00 AM'
}
*/

// Get current status of garage door
router.get('/gd', authenticator.isAuthenticatedWeb, function(req, res, next) {

  var request = client.get("http://10.0.0.11/", function(data, response) {
    console.log(JSON.parse(data));
    var d = JSON.parse(data);
    d.updated = (new Date()).toLocaleString();
    res.send(d);
  });

  request.on('error', function(err) {
    res.send({
      status: 'unknown',
      updated: (new Date()).toLocaleString()
    });
  });
});

// Let device notify server when current status change.
// Device will be doing so inside the network.
// No need to make this call over internet and hence no authentication necessary.
// Instead, we will do IP based restrictions
route.put('/gd', function(req, res, next) {
  // Check if call orignated from a specific IP
  // Save status to db
  console.log(req.ip);
  console.log(req.ips);
  console.log((req.ip).replace('::ffff:', ''));

  console.log("{ device: 'Garage Door Monitor',  status: 'open',  updated: '10/30/2016 10:00:00 AM' }");

});

// Open the garage
route.post('/gd/open', authenticator.isAuthenticatedWeb, function(req, res, next) {
  // Open the garage if it is closed. That logic should be in the device (not in the server)
});

// Open the garage
route.post('/gd/close', authenticator.isAuthenticatedWeb, function(req, res, next) {
  // Close the garage if it is open. That logic should be in the device (not in the server)
});

module.exports = router;
