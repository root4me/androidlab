$(window).on("load", function(e) {
  $('#intro').scrollnfade();
  $('#sporty').scrollnfade();
});

$(window).on("resize", function(e) {
  $(document).scrollnfadeReinit(); // do this only once

  $('#intro').scrollnfade();
  $('#sporty').scrollnfade();
});

$('a').click(function() {

  if (($.attr(this, 'href') !== undefined) && (($.attr(this, 'href')).substring(0, 1) == "#")) {
    $('html, body').animate({
      scrollTop: $($.attr(this, 'href')).offset().top
    }, 800);
    return false;
  }
});
