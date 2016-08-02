var mh = require('./mongodbHelper');
var config = require('../config');

var getAll = function(callback) {
  mh.getAllSorted(config.database.server, config.database.port, config.database.tasksDb, 'tasks', {
    task: 1
  }, function(err, data) {
    callback(err, data);
  });
};

var insert = function(data, callback) {
  mh.insert(config.database.server, config.database.port, config.database.tasksDb, 'tasks', data, function(err, data) {
    if (!(err)) {
      callback(err, data.ops[0]);
    } else {
      callback(err, data);
    }
  });
};

var update = function(id, data, callback) {

  mh.update(config.database.server, config.database.port, config.database.tasksDb, 'tasks', id, data, function(err, data) {
    callback(err, data);
  });
};

var del = function(id, callback) {
  mh.getById(config.database.server, config.database.port, config.database.tasksDb, 'tasks', id, function(err, data) {
    if (!(err)) {
      // move task created date into a different field
      data[0].taskcreated = data[0].created;
      console.log(data);
      mh.insert(config.database.server, config.database.port, config.database.tasksDb, 'tasksDone', data[0], function(err, data) {
        if (!(err)) {
          mh.delete(config.database.server, config.database.port, config.database.tasksDb, 'tasks', id, function(err, data) {
            //    console.log(data);
            callback(err, data);
          });
        } else {
          callback(err, data);
        }
      });

    } else {
      callback(err, data);
    }
  });
};


module.exports.getAll = getAll;
module.exports.insert = insert;
module.exports.update = update;
module.exports.delete = del;
