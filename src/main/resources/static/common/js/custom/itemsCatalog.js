    /**
 * Created by felipe on 24/08/17.
 */
$(document).ready(function() {
    $('.cui-ecommerce--catalog--item').mouseenter(function(){
        $(this).find('.cui-ecommerce-catalog--item--infobox').removeClass('slideOutDown hidden');
        $(this).find('.cui-ecommerce-catalog--item--infobox').addClass('slideInUp');

    });
    $('.cui-ecommerce--catalog--item').mouseleave(function(){
        $(this).find('.cui-ecommerce-catalog--item--infobox').removeClass('slideInUp');
        $(this).find('.cui-ecommerce-catalog--item--infobox').addClass('slideOutDown');
    });
});