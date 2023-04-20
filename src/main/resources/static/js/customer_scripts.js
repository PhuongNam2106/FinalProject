contact = function(baseUrl) {
	// tạo javascript object.  
	// data là dữ liệu cùng kiểu với RequestMapping
	let data = {
		name: jQuery("#name").val(), // lay theo id
		email: jQuery("#email").val(), // lay theo id
	};
	
	// $ === jQuery
	// json == javascript object
	jQuery.ajax({
		url: baseUrl + "/ajax/contact", //->action
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(data),

		dataType: "json", // kieu du lieu tra ve tu controller la json vì controlelr action trả về kiểu Map
		success: function(jsonResult) {
			/*alert(JSON.stringify(jsonResult));
			alert(jsonResult.message);*/
			jQuery("#TB_AJAX").html(jsonResult.message);
		},
		error: function(jqXhr, textStatus, errorMessage) {
			
		}
	});
}



// Thêm sản phẩm vào trong giỏ hàng
function AddToCart(baseUrl,productId, quanlity) {
	// javascript object.  data la du lieu ma day len action cua controller
//	console.log(baseUrl+":"+productId+":"+quanlity);
	console.log(productId+":"+quanlity);
	let data = {
		productId: productId, // lay theo id
		quanlity: quanlity, // lay theo id
	};
	
	// $ === jQuery
	// json == javascript object
	jQuery.ajax({
//	    console.log(baseUrl + "/ajax/addToCart");
		url: baseUrl, //->action
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(data),

		dataType: "json", // kieu du lieu tra ve tu controller la json
		success: function(jsonResult) {
			// tăng số lượng sản phẩm trong giỏ hàng trong icon
			console.log(jsonResult);
			$( "#iconShowTotalItemsInCart" ).html(jsonResult.totalItems);
			$([document.documentElement, document.body]).animate({
			    scrollTop: $("#iconShowTotalItemsInCart").offset().top
			}, 2000);
		},
		error: function(jqXhr, textStatus, errorMessage) {
			
		}
	});
}

// Cập nhật số lượng sản phẩm trong giỏ hàng
function plusQuanlityCart(baseUrl, productId) {
	
	// javascript object.  data la du lieu ma day len action cua controller
	let data = {
		productId: productId, // lay theo id		
	};
	
	// $ === jQuery
	// json == javascript object
	jQuery.ajax({
		url: baseUrl, //->action
		type: "post",
		contentType: "application/json",
		data: JSON.stringify(data),

		dataType: "json", // kieu du lieu tra ve tu controller la json
		success: function(jsonResult) {
			// tăng số lượng sản phẩm trong giỏ hàng trong icon
			$("#quanlity_" + productId).html(jsonResult.currentProductQuality);
		},
		error: function(jqXhr, textStatus, errorMessage) {
			
		}
	});
	
	
}

function minusQuanlityCart(baseUrl, productId) {
		
		// javascript object.  data la du lieu ma day len action cua controller
		let data = {
			productId: productId, // lay theo id		
		};
		
		// $ === jQuery
		// json == javascript object
		jQuery.ajax({
			url: baseUrl, //->action
			type: "post",
			contentType: "application/json",
			data: JSON.stringify(data),

			dataType: "json", // kieu du lieu tra ve tu controller la json
			success: function(jsonResult) {
				// tăng số lượng sản phẩm trong giỏ hàng trong icon
				$("#quanlity_" + productId).html(jsonResult.currentProductQuality);
			},
			error: function(jqXhr, textStatus, errorMessage) {
				
			}
		});
	}


