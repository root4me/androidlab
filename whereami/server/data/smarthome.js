var mh = require('./mongodbHelper');
var config = require('../config');

var getAll = function(callback) {
  mh.getAllSorted(config.database.server, config.database.port, config.database.smarthomeDb, 'devices', {
    task: 1
  }, function(err, data) {
    callback(err, data);
  });
};

var insert = function(data, callback) {
  mh.insert(config.database.server, config.database.port, config.database.smarthomeDb, 'devices', data, function(err, data) {
    if (!(err)) {
      callback(err, data.ops[0]);
    } else {
      callback(err, data);
    }
  });
};

var update = function(id, data, callback) {

  mh.update(config.database.server, config.database.port, config.database.smarthomeDb, 'devices', id, data, function(err, data) {
    callback(err, data);
  });
};


module.exports.getAll = getAll;
module.exports.insert = insert;
module.exports.update = update;
