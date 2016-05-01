var mh = require('./mongodbHelper');
var config = require('../config');

var getAll = function(callback){
  mh.getAll(config.database.server,config.database.port,config.database.db,'locations', function(err,data){
    callback(err,data);
  });
};

var insert = function(data, callback){

  mh.insert(config.database.server,config.database.port,config.database.db,'locations',data, function(err,data){
    if (!(err))
    {
      callback(err,data.ops[0]);
    }
    else {
      callback(err,data);
    }
  });
};

var update = function(id, data, callback){

  mh.update(config.database.server,config.database.port,config.database.db,'locations', id, data, function(err,data){
    callback(err,data);
  });
};

var del = function(id, callback){

  mh.delete(config.database.server,config.database.port,config.database.db,'locations',id, function(err,data){
    console.log(data);
    callback(err,data);
  });
};


module.exports.getAll = getAll;
module.exports.insert = insert;
module.exports.update = update;
module.exports.delete = del;
