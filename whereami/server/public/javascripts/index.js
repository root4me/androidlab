

$.ajax({
  url: 'https://hacker-news.firebaseio.com/v0/topstories.json',
  type: 'GET',
  success: function (result) {
    for (var i=0; i < 10; i++) {
      getHnNewsById(result[i]);
    }
  },
  error: function (x, status, error) {
    var e = "<p> error connecting to hacker-news.firebaseio.com to fetch news list</p>";
    $('#errorAlert').append(e).show();
  }
});


var newsFeedTemplate = "<a href=\"{{ url }}\" target=\"_blank\"> <div class=\"card-with-leftborder\"><h5 class=\"title\">{{ title }}</h5> \
<span class=\"postedby\">posted at hn by {{ postedby}} on {{ postedat }}</span> \
<p></p><span class=\"readmore\"></span></div></a>";

var getHnNewsById = function(id) {

  $.ajax({
    url: 'https://hacker-news.firebaseio.com/v0/item/' + id + '.json',
    type: 'GET',
    success: function (result) {
      //console.log(result);
      var p = newsFeedTemplate.replace('{{ url }}' , result.url ).replace('{{ title }}',result.title).replace('{{ postedby}}',result.by).replace('{{ postedat }}',new Date(result.time*1000));
      $('#news-article-feed').append(p);
    },
    error: function (x, status, error) {
      var e = "<p> error connecting to hacker-news.firebaseio.com to fetch details </p>";
      $('#errorAlert').append(e).show();
    }
  });

};
