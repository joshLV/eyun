$(function () {
    Choice();
    Equal();
})

function Choice() {
    var Choice = $(".select");
    Choice.hover(function () {
        $(this).find("ul").stop().fadeIn(150);
    }, function () {
        $(this).find("ul").stop().fadeOut(150);
    })
}

function Equal() {
    var left = $(".left");
    var leftHeight = left.outerHeight(true);
    var rightHeight = $(".right").outerHeight(true);
    if (rightHeight > leftHeight) {
        left.css("height", rightHeight);
    }
}

//下拉框选中事件
function bindEvent() {
    $(".select ul[id!='mainPlaceSel'] li").click(function () {
        var obj = $(this).parent("ul").prevAll("input");
        var idName = obj.attr("id");
        obj.attr(idName + "Value", $(this).attr("value"));
        obj.val(this.innerText.trim());
        $(this).parent("ul").hide();
    });
}

