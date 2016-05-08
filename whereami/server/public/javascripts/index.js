

$.ajax({
  url: 'https://hacker-news.firebaseio.com/v0/topstories.json',
  type: 'GET',
  success: function (result) {
    console.log(result.length);
    console.log(result);
    for (var i=0; i < 10; i++) {
      // Append a row inside article section and fire an ajax request to populate the details
      getHnNewsById(result[i]);
    }
  },
  error: function (x, status, error) {
    alert("An error occurred: " + status + "nError: " + error);
  }
});


var newsFeedTemplate = "<a href=\"{{ url }}\" target=\"_blank\"> <div class=\"card\"><h5 class=\"title\">{{ title }}</h5> \
<span class=\"postedby\">posted at hn by {{ postedby}} on {{ postedat }}</span> \
<p></p><span class=\"readmore\"></span></div></a>";

console.log(newsFeedTemplate);

var getHnNewsById = function(id) {
  console.log(id);

  $.ajax({
    url: 'https://hacker-news.firebaseio.com/v0/item/' + id + '.json',
    type: 'GET',
    success: function (result) {
      console.log(result);
      var p = newsFeedTemplate.replace('{{ url }}' , result.url ).replace('{{ title }}',result.title).replace('{{ postedby}}',result.by).replace('{{ postedat }}',new Date(result.time*1000));
      $('#news-article-feed').append(p);
    },
    error: function (x, status, error) {
      alert("An error occurred: " + status + "nError: " + error);
    }
  });

}
