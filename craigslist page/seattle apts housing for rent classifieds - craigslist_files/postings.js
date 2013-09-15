function initFlag(inppid,row){var mypID=pID||inppid;row=row||$(document);$(row).find(".flagChooser>a").click(function(e){$.get("/flag/?async=async&flagCode="+$(this).data('flag')+"&postingID="+mypID);$(this).parent().html("Thanks for flagging!");e.preventDefault();});}
$(document).ready(function(){if(typeof(pID)!=='undefined'){initFlag(pID);}
var maxWidth=$('section.body').innerWidth();$(window).resize(function(){if((window.innerWidth<maxWidth)&&(window.innerWidth>0)){$('#postingbody').css('width',window.innerWidth-16);$('aside.tsb').css('width',window.innerWidth-32);}}).resize();var format=get_format_cookie();if(typeof(bestOf)!=='undefined'&&!bestOf&&typeof(isPreview)!=='undefined'&&!isPreview&&format!=='mobile'&&format!=='tablet'&&$('.reply_button').length>0){$('.returnemail,.reply_button,.replytext').toggle();$('.reply_button').click(function(){if($('.reply_options').length){$('.returnemail').toggle();}else{$('.returnemail').load('/reply?v=2','',function(){$('.reply_options a, html').click(function(){$('.returnemail').hide();});$('.reply_options,.reply_button').click(function(e){e.stopPropagation();});$('.reply_options').each(function(){var $this=$(this);var c=$this.html()
.replace(/#PT#/g,encodeURIComponent(postingTitle))
.replace(/#URL#/g,postingURL)
.replace(/#DE#/g,encodeURIComponent(displayEmail))
.replace(/#DEP#/g,displayEmail);$this.html(c);});$('.anonemail').mouseup(function(e){e.preventDefault();});$('.reply_options,.returnemail').show();$('.anonemail').select();});}
$('.anonemail').select();});}
function get_format_cookie(){var mode='none';var C=document.cookie.split(';');for(i=0;i<C.length;i++){var c=$.trim(C[i]);if(c.indexOf('cl_fmt=')==0){mode=c.substring(7);}}
return mode;}
var $thumbs=$('#thumbs a');$thumbs
.on('mouseover',function(){var $this=$(this);var index=$this.index();$thumbs.find('img').removeClass('selected');$this.find('img').addClass('selected');$("img#iwi")
.attr({src:imgList[index],title:'image '+(index+1)});})
.on('click',function(e){e.preventDefault();})
.first().find('img').addClass('selected');});