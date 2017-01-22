var mh = require('./mongodbHelper');
var config = require('../config');
var mongo = require('mongodb').MongoClient;

var getAll = function(callback){
  mh.getAll(config.database.server,config.database.port,config.database.userDb,'user', function(err,data){
    callback(err,data);
  });
};

var insert = function(data, callback) {
  mh.insert(config.database.server, config.database.port, config.database.userDb, 'user', data, function(err, data) {
    if (!(err)) {
      callback(err, data.ops[0]);
    } else {
      callback(err, data);
    }
  });
};

var update = function(id, data, callback) {

  mh.update(config.database.server, config.database.port, config.database.userDb, 'user', id, data, function(err, data) {
    callback(err, data);
  });
};

var del = function(id, callback){

  mh.delete(config.database.server,config.database.port,config.database.userDb,'user',id, function(err,data){
    callback(err,data);
  });
};

var url = function(server, port, dbname) {
  return 'mongodb://' + server + ':' + port + '/' + dbname;
};

var getByName = function(searchCriteria, callback){

  mh.getByCriteria(config.database.server,config.database.port,config.database.userDb,'user',searchCriteria, function(err,data){
    callback(err,data);
  });

};

module.exports.getAll = getAll;
module.exports.insert = insert;
module.exports.update = update;
module.exports.delete = del;
module.exports.getByName = getByName;
