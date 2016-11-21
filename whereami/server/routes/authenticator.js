/**
 * authenticator
 *
 */
var express = require('express');
var router = express.Router();
var jwt = require('jsonwebtoken');

var config = require('../config');

var isAuthenticatedWeb = function(req, res, next) {

  var token = req.body.token || req.query.token || req.headers['x-access-token'] || req.cookies.token;

  if (token) {

    jwt.verify(token, config.tokenSecret, function(err, decoded) {
      if (err) {
        console.log(err);
        res.redirect('login', {
          originalUrl: req.originalUrl,
          scripts: [{
            'script': '/javascripts/login.js'
          }]
        });

      } else {
        req.decoded = decoded;
        console.log(decoded);
        next();
      }
    });

  } else {

    res.status(403).render('login', {
      originalUrl: req.originalUrl,
      scripts: [{
        'script': '/javascripts/login.js'
      }]
    });

  }
};

var isAuthenticatedApi = function(req, res, next) {

  var token = req.body.token || req.query.token || req.headers['x-access-token'] || req.cookies.token;

  console.log("token : " + token);

  if (token) {
    jwt.verify(token, config.tokenSecret, function(err, decoded) {
      console.log("inside jwt verify");
      if (err) {
        console.log(err);
        return res.json({
          authenticated: false,
          errorCode: 401,
          message: 'Failed to authenticate token.',
        });
      } else {
        // if everything is good, save to request for use in other routes
        req.decoded = decoded;
        console.log(decoded);
        next();
      }
    });
  } else {

    console.log("inside no token");

    return res.status(401).send({
      authenticated: false,
      errorCode: 401,
      message: 'No token provided.'
    });
  }
};

var authenticateWeb = function(req, res, next) {

  var user = req.body.user;
  var pwd = req.body.pwd;
  var originalUrl = req.body.originalUrl;

  if ((user === 'admin') && (pwd == 'pass')) {
    // success criteria . Set token
    var token = jwt.sign({
      user: 'admin',
      editLocation: true
    }, config.tokenSecret, {
      expiresIn: 60 * config.tokenExpiresInMin
    });

    res.cookie('token', token, {
      maxAge: 1000 * 60 * config.tokenExpiresInMin
    });
    console.log('cookie set ...');
    //next();
    res.redirect(originalUrl);
  } else {
    res.render('login', {
      originalUrl: req.originalUrl,
      scripts: [{
        'script': '/javascripts/login.js'
      }]
    });
  }
};

var authenticateApi = function(req, res, next) {

  var user = req.body.user;
  var pwd = req.body.pwd;

  if ((user === 'admin') && (pwd = 'pass')) {
    // success criteria . Set token
    var token = jwt.sign({
      user: 'admin'
    }, config.tokenSecret, {
      expiresIn: '2m'
    });

    //next();
    res.json({
      success: true,
      message: 'Authentication successful',
      token: token
    });

  } else {

    res.json({
      success: false,
      message: 'Authentication failed'
    });
  }
};

module.exports.isAuthenticatedWeb = isAuthenticatedWeb;
module.exports.authenticateWeb = authenticateWeb;

module.exports.isAuthenticatedApi = isAuthenticatedApi;
module.exports.authenticateApi = authenticateApi;
