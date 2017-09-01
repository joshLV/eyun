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
		$("#addFunc").remove();
		$(".cell_border_clear a[btn='uptFunc']").remove();
		$(".cell_border_clear a[btn='delFunc']").remove();
		$(".cell_border_clear a[btn='allotFunc']").remove();
	}
	if (examinePower == "" || examinePower == "false") {
		$(".cell_border_clear a[btn='examineFunc']").remove();
	}
	if (releasePower == "" || releasePower == "false") {
		$(".cell_border_clear a[btn='releaseFunc']").remove();
	}
	if (delegatePower == "" || delegatePower == "false") {
		$(".cell_border_clear a[btn='delegateFunc']").remove();
	}
	if (lockPower == "" || lockPower == "false") {
		$(".cell_border_clear a[btn='lockFunc']").remove();
	}
	if (recommendPower == "" || recommendPower == "false") {
		$(".cell_border_clear a[btn='recommendFunc']").remove();
	}
});
