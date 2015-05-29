var express = require('express');
var router = express.Router();

var net = require('net');

// template data for robot
var robot_data = {
  x: 5,
  y: 5,
  rotated: 'up',
  manual: false,
  req_temp: 20,
  req_light: 5,
  pack_size: 0
};

var talk_with_server = function (command, callback) {
  var client = net.connect({port: 4444}, function() { //'connect' listener
    console.log('connected to server!');
    client.write(command + "\r\n");
    client.on('data', function(data) {
      console.log(data.toString());
      client.end();
      if (data.error) {
        console.log('ERROR' + data.error);
      } else {
        callback(data);
      }
    });
    client.on('end', function() {
      console.log('disconnected from server');
    });
  });
};

var convert_robot_data = function (r) {
  var robots = [];
  for (var i in r.robots) {
    if (!robots[i]) robots[i] = {};

    robots[i].x = r.robots[i].position_x;
    robots[i].y = r.robots[i].position_y;

    switch (r.robots[i].direction) {
      case 'east': robots[i].rotated = 'right'; break;
      case 'west': robots[i].rotated = 'left'; break;
      case 'north': robots[i].rotated = 'up'; break;
      case 'south': robots[i].rotated = 'down'; break;
    }
  }

  return robots;
}

var convert_sensor_data = function (r) {
  var sensors = [];
  for (var i in r.sensors) {
    if (!sensors[i]) sensors[i] = {};

    sensors[i].x = r.sensors[i].position_x;
    sensors[i].y = r.sensors[i].position_y;

    sensors[i].name = r.sensors[i].name;
    sensors[i].id = r.sensors[i].id;

    sensors[i].light = r.sensors[i].light;
    sensors[i].temperature = r.sensors[i].temperature;
  }

  return sensors;
}

/* GET home page. */
router.get('/', function(req, res, next) {
  talk_with_server("map", function(data) {
    var map = JSON.parse(data);

    talk_with_server("robot", function(data) {
      var robots = convert_robot_data(JSON.parse(data));
      robot_data = robots[0];
      talk_with_server ("sensor", function(data) {
        var sensors = convert_sensor_data(JSON.parse(data));

        res.render('index', {
          title: 'Express',
          map: map,
          data: JSON.stringify({
            sensors: sensors,
            robots: robots
          })
        });
      });
    });
  });


});

router.post('/robot/move', function(req, res, next) {
  var x = req.body.x;
  var y = req.body.y;

  res.setHeader('Content-Type', 'application/json');

  talk_with_server("goto," + x + "," + y, function(data) {
    data = JSON.parse(data);
    if (data.status && data.status == "processing") {
      res.end(JSON.stringify({complete: true}));
    } else {
      res.end(JSON.stringify({complete: false}));
    }
  });
});

router.post('/robot/release', function(req, res, next) {
  talk_with_server("release", function(data) {
    data = JSON.parse(data);console.log(data);

    res.setHeader('Content-Type', 'application/json');
    if (data.success) {
      res.end(JSON.stringify({complete: true}));
    } else {
      res.end(JSON.stringify({complete: false}));
    }
  });
});

router.post('/robot/schedule', function(req, res, next) {
  var temperature = req.body.temperature;
  var light = req.body.light;
  var size = req.body.pack_size;

  res.setHeader('Content-Type', 'application/json');

  talk_with_server("goto," + temperature + "," + light + "," + size, function(data){
    data = JSON.parse(data);
    if (data.status && data.status == "processing") {
      res.end(JSON.stringify({complete: true}));
    } else {
      res.end(JSON.stringify({complete: false}));
    }
  });
});

router.get('/sensordata', function(req, res, next) {
  talk_with_server ("robot", function(data) {
    var robots = convert_robot_data(JSON.parse(data));
    robot_data = robots[0];

    talk_with_server ("sensor", function(data) {
      var sensors = convert_sensor_data(JSON.parse(data));

      res.setHeader('Content-Type', 'application/json');
      res.end(JSON.stringify({
        sensors: sensors,
        robots: robots
      }));
    });
  });

})

module.exports = router;
