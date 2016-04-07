// Database transactions related to location
var MongoClient = require('mongodb').MongoClient;
var config = require('./config').config;
var server = config.dbserver,
dbName = config.dbname,
taskTable = "Locations";

// Create a location
module.exports.create = new function(data, callback)
{
  console.log(data.latitude)
  console.log(data.longitude);

  MongoClient.connect(server + dbName, function(err, db) {
    var collection = db.collection(taskTable);
    collection.save({
      _id: data.id,
      longitude: data.longitude,
      latitude: data.latitude,
      clientCreated: data.createdTime,
      clientCaptured: data.capturedTime,
      created: Date.now();
    }, function(err, result) {
      if (!err) {
        callback(null, result)
      }
      else {
        callback(err, null);
      }

    });
  });
}

// Update a location
module.exports.update = new function(data, callback){

}


// Get one location based on Id
module.exports.getLocationById = new function(Id, callback)
{

}

module.exports.getLocations = new function(starDate, endDate, callback)
{

}
