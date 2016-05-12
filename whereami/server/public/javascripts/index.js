

$.ajax({
  url: 'https://hacker-news.firebaseio.com/v0/topstories.json',
  type: 'GET',
  success: function (result) {
    for (var i=0; i < 20; i++) {
      getHnNewsById(result[i]);
    }
  },
  error: function (x, status, error) {
    var e = "<p> error connecting to hacker-news.firebaseio.com to fetch news list</p>";
    $('#errorAlert').append(e).show();
  }
});



var getHnNewsById = function(id) {

  var newsFeedTemplate = "<a href=\"{{ url }}\" target=\"_blank\"> <div class=\"card-with-leftborder\"><h5 class=\"title\">{{ title }}</h5> \
  <span class=\"postedby\">posted on {{ postedat }}</span> \
  </div></a>";

  $.ajax({
    url: 'https://hacker-news.firebaseio.com/v0/item/' + id + '.json',
    type: 'GET',
    success: function (result) {
      //console.log(result);
      var p = newsFeedTemplate.replace('{{ url }}' , result.url ).replace('{{ title }}',result.title).replace('{{ postedby}}',result.by).replace('{{ postedat }}',new Date(result.time*1000));

      //var d = new Date(result.time*1000);
      //console.log(new Date(result.time*1000));
      //console.log(d.getDate() + '/' + (d.getMonth()+1) + '/' + d.getFullYear() + '  ' + d.getHours() + ':' + d.getMinutes() );

      $('#news-article-feed').append(p);
    },
    error: function (x, status, error) {
      var e = "<p> error connecting to hacker-news.firebaseio.com to fetch details </p>";
      $('#errorAlert').append(e).show();
    }
  });

};


var fetchWeatherForIp = function(ip, callback) {
  if (ip == '127.0.0.1') { ip = '2601:249:200:bf00:41e2:c284:d288:8abc'; }

  $.ajax({
    url: 'http://freegeoip.net/json/' + ip,
    type: 'GET',
    success: function (result) {
      /*
      console.log(result.country_code);
      console.log(result.city);
      console.log(result.region_code);
      console.log(result.latitude);
      console.log(result.longitude);
      */

      if (result.city.trim() === '')  { fetchWeatherForIp('127.0.0.1'); }

      currentWeather(result.city,result.country_code,result.latitude,result.longitude);

    },
    error: function (x, status, error) {
      var e = "<p> error connecting to freegeoip.net to fetch location info </p>";
      $('#errorAlert').append(e).show();
    }
  });
};


var currentWeather = function(city, countyCode,lat,lng) {
  $.ajax({
    url: 'http://api.openweathermap.org/data/2.5/weather?lat=' + lat + '&lon=' + lng + '&appid=f5bce934dd8fc4e3c49544dfd0f72840&units=imperial' ,
    type: 'GET',
    success: function (result) {
      $('#wo_city').text(city + ', ' + countyCode);
      var weather = populateWeather(result);

      $('#wo_temperature').text(weather.temp + '°F');
      //console.log(result.main.temp);

      $('#wo_weather').text(weather.weath);
      $('#wo_wind').text('wind ' + weather.windspeed + ',  ' + weather.winddeg + '°');
      //console.log(result.wind.speed);

      weatherForecast(city,countyCode,lat,lng);
    },
    error: function (x, status, error) {
      var e = "<p> error connecting to freegeoip.net to fetch location info </p>";
      $('#errorAlert').append(e).show();
    }
  });
};

var weatherForecast = function(city, countyCode,lat,lng) {

  var forecastTemplate =  "<div class=\"forecast\">{{ tod }}</div> \
  <div class=\"temperature\"> \
  <span class=\"temperatureValue\" > {{ temperature }}</span> \
  </div> \
  <div> \
  <span >{{ weather }}</span> \
  </div> \
  <div class=\"additionaInfo\"> \
  <span >{{ wind }}</span> \
  </div>";

  $.ajax({
    url: 'http://api.openweathermap.org/data/2.5/forecast?lat=' + lat + '&lon=' + lng + '&appid=f5bce934dd8fc4e3c49544dfd0f72840&units=imperial' ,
    type: 'GET',
    success: function (result) {
      var w = {};
      var now = new Date();
      var tomorrow = now.getDate() + 1;
      var morningWeather;
      var eveningWeather, eveningMsg;


      for (var i = 0 ; i < result.list.length; i++)
      {

        w = populateWeather(result.list[i]);
        if (now.getHours() >= 20)
        {
          if (w.time.getDate() == tomorrow && (w.time.getHours() >= 6 && w.time.getHours() < 9))
          {
            morningWeather = w;
          }
          if (w.time.getDate() == tomorrow && (w.time.getHours() >= 17 && w.time.getHours() < 20))
          {
            eveningWeather = w;
            eveningMsg = "Tomorrow evening";
          }
        }
        else {
          if (w.time.getDate() == now.getDate() && (w.time.getHours() >= 17 && w.time.getHours() < 20))
          {
            eveningWeather = w;
            eveningMsg = "This evening";
          }
        }
      }



      if (morningWeather !== undefined)
      {
        $('#wo_forecast').append("  <hr />");
        $('#wo_forecast').append(forecastTemplate.replace('{{ tod }}' , "Tomorrow Morning" )
        .replace('{{ temperature }}',morningWeather.temp + '°F')
        .replace('{{ weather }}',morningWeather.weath)
        .replace('{{ wind }}','wind ' + morningWeather.windspeed + ', ' + morningWeather.winddeg + '&deg;'));
      }

      if (eveningWeather !== undefined)
      {
        $('#wo_forecast').append("  <hr />");
        $('#wo_forecast').append(forecastTemplate.replace('{{ tod }}' , eveningMsg )
        .replace('{{ temperature }}',eveningWeather.temp + '°F')
        .replace('{{ weather }}',eveningWeather.weath)
        .replace('{{ wind }}','wind ' + eveningWeather.windspeed + ', ' + eveningWeather.winddeg + '&deg;'));
      }

      /*
      console.log(morningWeather.temp);
      console.log(morningWeather.time);
      console.log(eveninigWeather.temp);
      console.log(eveninigWeather.time);
      */
    },
    error: function (x, status, error) {
      var e = "<p> error connecting to openweathermap to fetch weather info </p>";
      $('#errorAlert').append(e).show();
    }
  });
};

var populateWeather = function(obj) {
  var weather = {
    temp: '',
    weath: '',
    windspeed: '',
    winddeg: '',
    time: ''
  };

  weather.temp  = obj.main.temp;
  var w='';
  for (var i = 0 ; i < obj.weather.length; i++)
  {
    if (w.length > 0) { w = w + ', '; }
    w = w + obj.weather[i].main;
    //console.log(result.weather[i].main);
  }
  weather.weath = w;
  weather.windspeed = obj.wind.speed;
  weather.winddeg = obj.wind.deg;
  weather.time = new Date(obj.dt*1000);

  /*
  console.log(weather.temp);
  console.log(weather.weath);
  console.log(weather.windspeed);
  console.log(weather.winddeg);
  console.log(weather.time.toString());
  */

  return weather;
};


fetchWeatherForIp($('#ip').val());
