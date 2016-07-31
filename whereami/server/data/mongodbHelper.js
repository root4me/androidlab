/**
* Generic CRUD calls to mongodb using node js mongodb driver
* package.json need to have "mongodb": "^2.1.16",
**/

var mongo = require('mongodb').MongoClient;
var assert = require('assert');
var ObjectID = require('mongodb').ObjectID;

var url = function(server,port,dbname){
  return 'mongodb://' + server + ':' + port + '/' + dbname;
};

/**
* [function description]
* @param  {String}   server         [description]
* @param  {Number}   port           [description]
* @param  {[type]}   dbName         [description]
* @param  {[type]}   collectionName [description]
* @param  {Function} callback       [description]
* @return {[type]}                  [description]
*/
var getAll = function(server,port, dbName, collectionName, callback){

  mongo.connect(url(server,port, dbName), function(err, db) {

    if (err != null)
    {
      callback(err,null);
      return;
    }
//    console.log("Connected to mongodb server");
    var collection = db.collection(collectionName);

    collection.find().toArray(function(err, docs) {
      db.close();
      callback(err,docs);
    });
  });

};

/**
* [function description]
* @param  {[type]}   server         [description]
* @param  {[type]}   port           [description]
* @param  {[type]}   dbName         [description]
* @param  {[type]}   collectionName [description]
* @param  {[type]}   data           [description]
* @param  {Function} callback       [description]
* @return {[type]}                  [description]
*/
var insert = function(server, port, dbName, collectionName, data, callback){

  mongo.connect(url(server,port, dbName), function(err, db) {
//    console.log("Connected correctly to server");
    var collection = db.collection(collectionName);

    data.created = Date();
//    console.log(data);

    collection.insertOne(data , function(err,docs){
      callback(err,docs);
    });
  });
};

var update = function(server, port, dbName, collectionName, id, data, callback){
  mongo.connect(url(server,port, dbName), function(err, db) {
//    console.log("Connected correctly to server for update");
    var collection = db.collection(collectionName);

    data.updated = Date();
//    console.log(data);

    collection.update(
      { '_id' : ObjectID(id) },
      { $set: data },
      function(err,docs){
        callback(err,docs);
    });
  });
};

var del = function(server, port, dbName, collectionName, id, callback){

  mongo.connect(url(server,port, dbName), function(err, db) {
//    console.log("Connected correctly to server for delete");
    var collection = db.collection(collectionName);
    var objid = new ObjectID(id);

    collection.deleteOne({_id: ObjectID(id)},function(err, docs) {
//      console.log(docs);
//      console.log(err);
      callback(err,docs);
    });
  });
};

module.exports.getAll = getAll;
module.exports.insert = insert;
module.exports.update = update;
module.exports.delete = del;
