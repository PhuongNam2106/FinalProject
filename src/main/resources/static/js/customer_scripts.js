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

function checkPasswordMatch(fieldConfirmPassword) {
        if (fieldConfirmPassword.value != document.getElementById("password").value) {
            fieldConfirmPassword.setCustomValidity("Passwords do not match!");
        } else {
            fieldConfirmPassword.setCustomValidity("");
        }
    }


var selectedSize = null;
function getSizeValue(button) {
  // Gỡ bỏ class "selected" từ button trước (nếu có)
  var prevSelected = document.querySelector('.size-button.selected');
  if (prevSelected) {
    prevSelected.classList.remove('selected');
  }

  // Thêm class "selected" vào button được chọn
  button.classList.add('selected');

  // Lấy giá trị từ button được chọn và gán cho biến selectedSize
  selectedSize = button.getAttribute('value');


}

// Thêm sản phẩm vào trong giỏ hàng
function AddToCart(baseUrl,productId, size, quanlity) {
	// javascript object.  data la du lieu ma day len action cua controller
//	console.log(baseUrl+":"+productId+":"+quanlity);
//	console.log(productId+":"+quanlity+":"+size);
    console.log("size đã chọn: "+selectedSize);

	let data = {
		productId: productId, // lay theo id
		quanlity: quanlity, // lay theo id
		size: selectedSize,
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
			console.log(jsonResult);
			console.log(size);
			console.log(size);
			console.log(size);
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


