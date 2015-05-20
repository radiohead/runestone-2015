var express = require('express');
var router = express.Router();

/* GET home page. */
router.get('/', function(req, res, next) {
  res.render('index', { title: 'Express' });
});

// template data for robot
var robot_data = {
  x: 5,
  y: 5,
  rotated: 'up',
  manual: false
};

router.get('/robot/manual/true', function(req, res, next) {
  robot_data.manual = true;
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify({complete: true}));
});

router.get('/robot/manual/false', function(req, res, next) {
  robot_data.manual = false;
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify({complete: true}));
});

router.post('/robot/move', function(req, res, next) {
  robot_data = req.body;
  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify({complete: true}));
});

router.get('/sensordata', function(req, res, next) {
  //there will be code to get data from sensors and robot
  var sensors = [
    {
      x: 0,
      y: 0,
      temperature: 36,
      light: 30
    },
    {
      x: 0,
      y: 9,
      temperature: 35,
      light: 20
    },
    {
      x: 9,
      y: 0,
      temperature: 32,
      light: 10
    },
    {
      x: 9,
      y: 9,
      temperature: 20,
      light: 5
    }
  ];

  //there will be code to get robots data
  if (robot_data.manual == false) {
    if (Math.random() > 0.5) {
      if (Math.random() > 0.5) {
        if (robot_data.x > 0) {
          robot_data.x--;
          robot_data.rotated = 'left';
        }
      } else {
        if (robot_data.x < 9) {
          robot_data.x++;
          robot_data.rotated = 'right';
        }
      }
    } else {
      if (Math.random() > 0.5) {
        if (robot_data.y > 0) {
          robot_data.y--;
          robot_data.rotated = 'up';
        }
      } else {
        if (robot_data.y < 9) {
          robot_data.y++;
          robot_data.rotated = 'down';
        }
      }
    }
  }

  var robots = [
    robot_data
  ];

  res.setHeader('Content-Type', 'application/json');
  res.end(JSON.stringify({
    sensors: sensors,
    robots: robots
  }));
})

module.exports = router;
