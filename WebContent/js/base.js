var nowuser = null;
$(document).ready(function() {
	if (nowuser == null) {
		$.getJSON(contextPath + "/basePage/iptoname.json", function(json) {
			nowuser = json[0][addr];
			if (!nowuser) {
				nowuser = addr;
			}
		});
	}
});
