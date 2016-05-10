

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
  <span class=\"postedby\">posted at hn by {{ postedby}} on {{ postedat }}</span> \
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


var findCityfromIp = function(ip) {
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
      currentWeather(result.city,result.country_code,result.latitude,result.longitude);

    },
    error: function (x, status, error) {
      var e = "<p> error connecting to freegeoip.net to fetch location info </p>";
      $('#errorAlert').append(e).show();
    }
  });
};


// http://api.openweathermap.org/data/2.5/forecast?lat=41.85&lon=-87.65&appid=f5bce934dd8fc4e3c49544dfd0f72840&units=imperial

// console.log(new Date(1462847050*1000));

findCityfromIp($('#ip').val());

var currentWeather = function(city, countyCode,lat,lng) {
  $.ajax({
    url: 'http://api.openweathermap.org/data/2.5/weather?lat=' + lat + '&lon=' + lng + '&appid=f5bce934dd8fc4e3c49544dfd0f72840&units=imperial' ,
    type: 'GET',
    success: function (result) {
      $('#wo_city').text(city + ', ' + countyCode);
      $('#wo_temperature').text(result.main.temp + '°F');
      //console.log(result.main.temp);
      var w='';
      for (var i = 0 ; i < result.weather.length; i++)
      {
        if (w.length > 0) { w = w + ', '; }
        w = w + result.weather[i].main;
        //console.log(result.weather[i].main);
      }
      $('#wo_weather').text(w);
      $('#wo_wind').text('wind ' + result.wind.speed + ',  ' + result.wind.deg + '°');
      //console.log(result.wind.speed);
    },
    error: function (x, status, error) {
      var e = "<p> error connecting to freegeoip.net to fetch location info </p>";
      $('#errorAlert').append(e).show();
    }
  });
}
