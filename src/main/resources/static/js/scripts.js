$(".answer-write input[type=submit]").click(addAnswer);

function addAnswer(e) {
    e.preventDefault();
    console.log('click me ');

  let queryString = $(".answer-write").serialize();
  let url = $(".answer-write").attr('action');
  console.log('url : ',url);
  console.log('queryString : ',queryString);

  $.ajax({
      type : 'post',
      url : url,
      data : queryString,
      dataType : 'json' ,
      })
      .done(function(data){
        let answerTemplate = $("#answerTemplate").html();
        let template = answerTemplate.format(data.writer.userId, data.createDate,data.contents,data.id,data.id);
        $(".qna-comment-slipp-articles").prepend(template);
        $("textarea[name=contents]").val('');
      })
      .fail(function(xhr, status, errorThrown){
          console.log('xhr : ',xhr);
          console.log('status : ',status);
          console.log('errorThrown : ',errorThrown);

      })
}

String.prototype.format = function() {
    var args = arguments;
    return this.replace(/{(\d+)}/g, function(match, number) {
        return typeof args[number] != 'undefined'
            ? args[number]
            : match
            ;
    });
};