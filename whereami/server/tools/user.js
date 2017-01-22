var userdata = require('../data/user');

var activities = [],
commands = ['exit', 'list', 'get', 'add', 'delete'];

var readline = require('readline'),
rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout,
});

rl.setPrompt('>', 1);
rl.write('...');
rl.write("commands : 'exit', 'list', 'get', 'add', 'delete' \r\n");
rl.prompt();

var user = {
  username: '',
  password: '',
  created: new Date(),
  updated: new Date(),
}

rl.on('line', function(input) {
  if (commands.indexOf(input.trim()) < 0) {
    console.log(user);
    //activities.push(activity);
    rl.prompt();
  }
  else {
    //completeCurrentactivity();
    processCommand(commands[commands.indexOf(input.trim())]);
  }
});

function processCommand(command) {

  switch (command) {
    case 'exit':
    console.log('exit');
    rl.pause();
    break;

    case 'delete':
    rl.question('user name : ', function(data) {
      user.username = data.trim();
      //rl.prompt();
      console.log('getting ' + user.username);

      var query = {
        username: user.username
      }
      userdata.getByName(query, function(err, u) {
        //            res.send(dat);
        console.log(u);
        console.log(u.length);
        if (u.length == 1)
        {
          console.log(u[0].username);
          console.log(u[0]._id);
          rl.question('Delete ' + u[0].username + ' y/n : ', function(d) {
            if (d.trim() == 'y')
            {
              userdata.delete(u[0]._id,function(err,data){
                console.log(data);
                rl.prompt();
              });
            }
            else {
              rl.prompt();
            }
          });
          rl.prompt();
        }
        else {
          console.log('unique user not found');
          rl.prompt();
        }
      });
    });
    break;

    case 'add':
    rl.question('user name : ', function(data) {
      user.username = data.trim();
      //rl.prompt();
      rl.question('password : ', function(data) {
        user.password = data.trim();
        console.log('save ........');

        var dat = user;
        dat.created =  new Date();
        console.log(dat.username.trim().length);
        console.log(dat.password.trim().length);
        if ((dat.username.trim().length > 4) && (dat.password.trim().length > 4))
        {
          userdata.insert(dat, function(err, data) {
            //            res.send(dat);
            console.log(data);
            rl.prompt();
          });
        }
        else {
          console.log('User not accepatable ...');
          rl.prompt();
        }
      });
    });
    break;

    case 'get':
    rl.question('user name : ', function(data) {
      user.username = data.trim();
      //rl.prompt();
      console.log('getting ' + user.username);

      var query = {
        username: user.username
      }
      userdata.getByName(query, function(err, data) {
        //            res.send(dat);
        console.log(data);
        rl.prompt();
      });
    });
    break;

    case 'list':
    console.log('listing ...')

    userdata.getAll(function(err, data) {
      // res.send(data);
      console.log(data);
      rl.prompt();
    });
    break;
  }

  //    rl.prompt();
}
