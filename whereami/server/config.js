/**
* [local description]
*
*/

var local = {
  environment: 'local',
  server: 'localhost',
  port: 3000,
  database : {
    server: 'localhost',
    port: 27017,
    db: 'whereamiDb'
  }
};

var production = {
  environment: 'production',
  server: 'productionURL',
  port: 3000,
  database : {
    server: 'localhost',
    port: 27017,
    db: 'prod_whereamiDb'
  }
};

if (process.env.NODE_ENV === 'development') {
  module.exports = local;
}
else if (process.env.NODE_ENV === 'production'){
  module.exports = production;
}
else {
  module.exports = local;
}
