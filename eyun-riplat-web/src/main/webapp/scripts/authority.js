/**
 * 根据权限分配相应资源
 */
var writePower = $("#writePower").val();	//写权限
var examinePower = $("#examinePower").val();	//审核权限
var releasePower = $("#releasePower").val();	//发布权限
var delegatePower = $("#delegatePower").val();//委派权限
var lockPower = $("#lockPower").val();//锁定权限
var recommendPower = $("#recommendPower").val();	//推荐权限

$(function() {
	if (writePower == "" || writePower == "false") {
		$("#addFunc").empty();
		$(".cell_border_clear a[btn='uptFunc']").empty();
		$(".cell_border_clear a[btn='delFunc']").empty();
		$(".cell_border_clear a[btn='allotFunc']").empty();
	}
	if (examinePower == "" || examinePower == "false") {
		$(".cell_border_clear a[btn='examineFunc']").empty();
	}
	if (releasePower == "" || releasePower == "false") {
		$(".cell_border_clear a[btn='releaseFunc']").empty();
	}
	if (delegatePower == "" || delegatePower == "false") {
		$(".cell_border_clear a[btn='delegateFunc']").empty();
	}
	if (lockPower == "" || lockPower == "false") {
		$(".cell_border_clear a[btn='lockFunc']").empty();
	}
	if (recommendPower == "" || recommendPower == "false") {
		$(".cell_border_clear a[btn='recommendFunc']").empty();
	}
});
