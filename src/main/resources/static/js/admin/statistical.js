        var ctx = document.getElementById('statisticalrevenuemonthline').getContext('2d');
		var ctx1 = document.getElementById('statisticalrevenueyearline').getContext('2d');
		var ctx2 = document.getElementById('statisticalcategoryyearpie').getContext('2d');
		var myChart = null;
		var myChart1 = null;
		var myChart2 = null;
		// code lấy đường dẫn icon month line
		var iconrevenuemonthline = document.querySelector('.description-block .iconrevenuemonthline i');
		var iconrevenueyearline = document.querySelector('.description-block .iconrevenueyearline i');
		// code lấy đường dẫn html vào progress thẻ span biểu đồ month line
		var addcartprogressmonthline = document.querySelector('.addcartprogressmonthline span');
		var ordersuccessprogressmonthline = document.querySelector('.ordersuccessprogressmonthline span');
		var orderwaitingprogressmonthline = document.querySelector('.orderwaitingprogressmonthline span');
		var orderfalseprogressmonthline = document.querySelector('.orderfalseprogressmonthline span');
		// code lấy đường dẫn html vào progress thẻ span biểu đồ year line
		var addcartprogressyearline = document.querySelector('.addcartprogressmonthyearline span');
		var ordersuccessprogressyearline = document.querySelector('.ordersuccessprogressmonthyearline span');
		var orderwaitingprogressyearline = document.querySelector('.orderwaitingprogressmonthyearline span');
		var orderfalseprogressyearline = document.querySelector('.orderfalseprogressmonthyearline span');
		// code lấy đường dẫn html vào progress bar biểu đồ month line
		var pbaddcartprogressmonthline = document.querySelector('.addcartprogressmonthline .progress .progress-bar');
		var pbordersuccessprogressmonthline = document.querySelector(
			'.ordersuccessprogressmonthline .progress .progress-bar');
		var pborderwaitingprogressmonthline = document.querySelector(
			'.orderwaitingprogressmonthline .progress .progress-bar');
		var pborderfalseprogressmonthline = document.querySelector('.orderfalseprogressmonthline .progress .progress-bar');
		// code lấy đường dẫn html vào progress bar biểu đồ year line
		var pbaddcartprogressmonthyearline = document.querySelector('.addcartprogressmonthyearline .progress .progress-bar');
		var pbordersuccessprogressmonthyearline = document.querySelector(
			'.ordersuccessprogressmonthyearline .progress .progress-bar');
		var pborderwaitingprogressmonthyearline = document.querySelector(
			'.orderwaitingprogressmonthyearline .progress .progress-bar');
		var pborderfalseprogressmonthyearline = document.querySelector(
			'.orderfalseprogressmonthyearline .progress .progress-bar');
		// code tìm thẻ input month line
		var monthlyGoalByMonthLine = document.getElementById('monthlyGoalByMonthLine');
		// cookie value input month line
		var cookieValueMonthlyGoalByMonthLine;
		// code tìm thẻ input year line
		var monthlyGoalByYearLine = document.getElementById('monthlyGoalByYearLine');
		// cookie value input year line
		var cookieValueMonthlyGoalByYearLine;
		// giá trị cho thẻ b progress month line
		var amountCart;
		var amountOrderSuccess;
		var amountOrderWaiting;
		var amountOrderFalse;
		// giá trị cho thẻ b progress month line
		var amountCart1;
		var amountOrderSuccess1;
		var amountOrderWaiting1;
		var amountOrderFalse1;

		//cookie
		function setCookie(cname, cvalue, exdays) {
			const d = new Date();
			d.setTime(d.getTime() + (exdays * 24 * 60 * 60 * 1000));
			let expires = "expires=" + d.toUTCString();
			document.cookie = cname + "=" + cvalue + ";" + expires + ";path=/";
		}

		function getCookie(cname) {
			let name = cname + "=";
			let ca = document.cookie.split(';');
			for (let i = 0; i < ca.length; i++) {
				let c = ca[i];
				while (c.charAt(0) == ' ') {
					c = c.substring(1);
				}
				if (c.indexOf(name) == 0) {
					return c.substring(name.length, c.length);
				}
			}
			return "";
		}

		function checkCookie() {
			let user = getCookie("username");
			if (user != "") {
				alert("Welcome again " + user);
			} else {
				user = prompt("Please enter your name:", "");
				if (user != "" && user != null) {
					setCookie("username", user, 365);
				}
			}
		}

		// Hàm cập nhật biểu đồ month line 
		function updateChartrevenuemonthline(year, month) {
			fetch('/api/ad/revenue/line/month?year=' + year + '&month=' + month)
				.then(response => response.json())
				.then(data => {
					// Lấy giá trị 
					const liststatis = data.liststatis;
					amountCart = data.amountCart;
					amountOrderSuccess = data.amountOrderSuccess;
					amountOrderWaiting = data.amountOrderWaiting;
					amountOrderFalse = data.amountOrderFalse;
					// Cập nhật dữ liệu cho biểu đồ
					myChart.data.labels = data.liststatis.map(item => item.day);
					myChart.data.datasets[0].data = data.liststatis.map(item => item.price);
					myChart.data.datasets[1].data = data.liststatis.map(item => item.priceCompare);
					myChart.update();
					// Tính tổng giá trị của trường 'price' trong danh sách
					let priceChartrevenuemonthline = data.liststatis.reduce((sum, item) => sum + item.price, 0);
					// Tính tổng giá trị của trường 'priceCompare' trong danh sách
					let pricecompareChartrevenuemonthline = data.liststatis.reduce((sum, item) => sum + item.priceCompare,
						0);
					let percentage;
					if (priceChartrevenuemonthline > pricecompareChartrevenuemonthline) {
						if (pricecompareChartrevenuemonthline !== 0) {
							percentage = (priceChartrevenuemonthline / pricecompareChartrevenuemonthline) * 100;
						} else {
							percentage = 100;
						}
					} else if (priceChartrevenuemonthline < pricecompareChartrevenuemonthline) {
						if (priceChartrevenuemonthline !== 0) {
							percentage = (pricecompareChartrevenuemonthline / priceChartrevenuemonthline) * 100;
						} else {
							percentage = 100;
						}
					} else if (priceChartrevenuemonthline == pricecompareChartrevenuemonthline) {
						percentage = 0;
					}
					let percentageElement = document.querySelector(
						'.description-block .iconrevenuemonthline .numberrevenuemonthline');
					let headerElement = document.querySelector('.description-block .totalrevenuemonthline');
					let formattedPrice = priceChartrevenuemonthline.toLocaleString('vi-VN', {
						style: 'currency',
						currency: 'VND'
					});
					if (priceChartrevenuemonthline > pricecompareChartrevenuemonthline) {
						iconrevenuemonthline.className = "fa-solid fa-caret-up";
						percentageElement.textContent = percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					} else if (priceChartrevenuemonthline < pricecompareChartrevenuemonthline) {
						iconrevenuemonthline.className = "fa-solid fa-caret-down text-danger";
						percentageElement.textContent = '-' + percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					} else {
						iconrevenuemonthline.className = "";
						percentageElement.textContent = percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					}
					getCookiemonthlyGoalByMonthLineAndSetInputIdmonthlyGoalByMonthLine();
				});
		}

		// Hàm cập nhật biểu đồ month line 
		function updateChartrevenueyearline(year) {
			fetch('/api/ad/revenue/line/year?year=' + year)
				.then(response => response.json())
				.then(data => {
					// Lấy giá trị 
					const liststatis = data.liststatis;
					amountCart1 = data.amountCart;
					amountOrderSuccess1 = data.amountOrderSuccess;
					amountOrderWaiting1 = data.amountOrderWaiting;
					amountOrderFalse1 = data.amountOrderFalse;
					// Cập nhật dữ liệu cho biểu đồ
					myChart1.data.labels = data.liststatis.map(item => item.day);
					myChart1.data.datasets[0].data = data.liststatis.map(item => item.price);
					myChart1.data.datasets[1].data = data.liststatis.map(item => item.priceCompare);
					myChart1.update();
					// Tính tổng giá trị của trường 'price' trong danh sách
					let priceChartrevenuemonthline = data.liststatis.reduce((sum, item) => sum + item.price, 0);
					// Tính tổng giá trị của trường 'priceCompare' trong danh sách
					let pricecompareChartrevenuemonthline = data.liststatis.reduce((sum, item) => sum + item.priceCompare,
						0);
					let percentage;
					if (priceChartrevenuemonthline > pricecompareChartrevenuemonthline) {
						if (pricecompareChartrevenuemonthline !== 0) {
							percentage = (priceChartrevenuemonthline / pricecompareChartrevenuemonthline) * 100;
						} else {
							percentage = 100;
						}
					} else if (priceChartrevenuemonthline < pricecompareChartrevenuemonthline) {
						if (priceChartrevenuemonthline !== 0) {
							percentage = (pricecompareChartrevenuemonthline / priceChartrevenuemonthline) * 100;
						} else {
							percentage = 100;
						}
					} else if (priceChartrevenuemonthline == pricecompareChartrevenuemonthline) {
						percentage = 0;
					}
					let percentageElement = document.querySelector(
						'.description-block .iconrevenueyearline .numberrevenueyearline');
					let headerElement = document.querySelector('.description-block .totalrevenueyearline');
					let formattedPrice = priceChartrevenuemonthline.toLocaleString('vi-VN', {
						style: 'currency',
						currency: 'VND'
					});
					if (priceChartrevenuemonthline > pricecompareChartrevenuemonthline) {
						iconrevenueyearline.className = "fa-solid fa-caret-up";
						percentageElement.textContent = percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					} else if (priceChartrevenuemonthline < pricecompareChartrevenuemonthline) {
						iconrevenueyearline.className = "fa-solid fa-caret-down text-danger";
						percentageElement.textContent = '-' + percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					} else {
						iconrevenueyearline.className = "";
						percentageElement.textContent = percentage.toFixed(0) + '%';
						headerElement.textContent = formattedPrice;
					}
					getCookiemonthlyGoalByYearLineAndSetInputIdmonthlyGoalByYearLine();
				});
		}

		// Hàm lấy giá trị cookie và đưa lên input statisticalcategoryyearpie
		function updateChartordermonthpie(year) {
			fetch('/api/ad/statistical/pie/year?year=' + year)
				.then(response => response.json())
				.then(data => {
                    var check = 0;
                    var categorySelectHTML;
					// Sử dụng map để chuyển đổi danh sách thành mảng
					var id = data.map(item => item.id);
					var labels = data.map(item => item.name);
					var values = data.map(item => item.countProductSell);
					console.log(check);
					// Cập nhật dữ liệu cho biểu đồ
					myChart2.data.labels = labels;
					myChart2.data.datasets[0].data = values;
					myChart2.update();
                    
                    data.forEach(item => {
                        if (check == 0) {
                            categorySelectHTML += `<option value="${item.id}" selected>${item.name}</option>`
                        } else {
                            categorySelectHTML += `<option value="${item.id}">${item.name}</option>`
                        }
						check++;
                    });
                    
                    categorySelect.innerHTML = categorySelectHTML;
					updateListProductByStatus(yearSelectcategoryyearpie.value, categorySelectedValue.value);
				});
		}

		// Hàm lấy giá trị cookie và đưa lên input monthlyGoalByMonthLine
		function getCookiemonthlyGoalByMonthLineAndSetInputIdmonthlyGoalByMonthLine() {
			// đưa giá trị vào form
			cookieValueMonthlyGoalByMonthLine = getCookie('monthlyGoalByMonthLine');
			// tinh phần trăm progress bar
			var percentaddcartprogressmonthline = (amountCart / cookieValueMonthlyGoalByMonthLine) * 100;
			var percentordersuccessprogressmonthline = (amountOrderSuccess / cookieValueMonthlyGoalByMonthLine) * 100;
			var percentorderwaitingprogressmonthline = (amountOrderWaiting / cookieValueMonthlyGoalByMonthLine) * 100;
			var percentorderfalseprogressmonthline = (amountOrderFalse / cookieValueMonthlyGoalByMonthLine) * 100;
			monthlyGoalByMonthLine.value = cookieValueMonthlyGoalByMonthLine;
			addcartprogressmonthline.innerHTML = `<b>${amountCart}</b>/${cookieValueMonthlyGoalByMonthLine}`;
			ordersuccessprogressmonthline.innerHTML = `<b>${amountOrderSuccess}</b>/${cookieValueMonthlyGoalByMonthLine}`;
			orderwaitingprogressmonthline.innerHTML = `<b>${amountOrderWaiting}</b>/${cookieValueMonthlyGoalByMonthLine}`;
			orderfalseprogressmonthline.innerHTML = `<b>${amountOrderFalse}</b>/${cookieValueMonthlyGoalByMonthLine}`;
			pbaddcartprogressmonthline.style.width = percentaddcartprogressmonthline + '%';
			pbordersuccessprogressmonthline.style.width = percentordersuccessprogressmonthline + '%';
			pborderwaitingprogressmonthline.style.width = percentorderwaitingprogressmonthline + '%';
			pborderfalseprogressmonthline.style.width = percentorderfalseprogressmonthline + '%';
		}

		// Hàm lấy giá trị cookie và đưa lên input monthlyGoalByYearLine
		function getCookiemonthlyGoalByYearLineAndSetInputIdmonthlyGoalByYearLine() {
			// đưa giá trị vào form
			cookieValueMonthlyGoalByYearLine = getCookie('monthlyGoalByYearLine');
			// tinh phần trăm progress bar
			var percentaddcartprogressmonthline1 = (amountCart1 / cookieValueMonthlyGoalByYearLine) * 100;
			var percentordersuccessprogressmonthline1 = (amountOrderSuccess1 / cookieValueMonthlyGoalByYearLine) * 100;
			var percentorderwaitingprogressmonthline1 = (amountOrderWaiting1 / cookieValueMonthlyGoalByYearLine) * 100;
			var percentorderfalseprogressmonthline1 = (amountOrderFalse1 / cookieValueMonthlyGoalByYearLine) * 100;
			monthlyGoalByYearLine.value = cookieValueMonthlyGoalByYearLine;
			addcartprogressyearline.innerHTML = `<b>${amountCart1}</b>/${cookieValueMonthlyGoalByYearLine}`;
			ordersuccessprogressyearline.innerHTML = `<b>${amountOrderSuccess1}</b>/${cookieValueMonthlyGoalByYearLine}`;
			orderwaitingprogressyearline.innerHTML = `<b>${amountOrderWaiting1}</b>/${cookieValueMonthlyGoalByYearLine}`;
			orderfalseprogressyearline.innerHTML = `<b>${amountOrderFalse1}</b>/${cookieValueMonthlyGoalByYearLine}`;
			pbaddcartprogressmonthyearline.style.width = percentaddcartprogressmonthline1 + '%';
			pbordersuccessprogressmonthyearline.style.width = percentordersuccessprogressmonthline1 + '%';
			pborderwaitingprogressmonthyearline.style.width = percentorderwaitingprogressmonthline1 + '%';
			pborderfalseprogressmonthyearline.style.width = percentorderfalseprogressmonthline1 + '%';
		}

		function updateListProductByStatus(year, id) {
			fetch('/api/admin/listproductcate?year=' + year + '&id=' + id)
				.then(response => response.json())
				.then(data => {
					console.log(id);
					console.log(year);
					const tableBody = document.getElementById('productTableBody');
					tableBody.innerHTML = '';

					// Populate the table with the received data
					data.forEach(product => {
						const row = document.createElement('tr');
						row.innerHTML = `
							<td class="product-name">${product.id}</td>
							<td class="product-name">${product.name}</td>
							<td class="text-center">${product.soldCount}</td>
							<td>${product.sellerProduct.username}</td>
							<td><a href="#"><i class="fas fa-eye"></i></a></td>
						`;
						tableBody.appendChild(row);
					});
				});
		}

		// Lấy thẻ select theo id
		var monthSelectrevenuemonthline = document.getElementById('monthSelectrevenuemonthline');
		var yearSelectrevenuemonthline = document.getElementById('yearSelectrevenuemonthline');
		var yearSelectrevenueyearline = document.getElementById('yearSelectrevenueyearline');
		var yearSelectcategoryyearpie = document.getElementById('yearSelectcategoryyearpie');
		var categorySelectedValue = document.getElementById('categorySelect');

		// Tạo các phần tử option cho tháng và năm
		for (var i = 1; i <= 12; i++) {
			var option = document.createElement('option');
			option.value = i;
			option.textContent = i;
			monthSelectrevenuemonthline.appendChild(option);
		}

		var currentYearMonthline = new Date().getFullYear();
		for (var i = 0; i < 5; i++) {
			var year = currentYearMonthline - i;
			var option = document.createElement('option');
			option.value = year;
			option.textContent = year;
			yearSelectrevenuemonthline.appendChild(option);
		}

		var currentYearYearline = new Date().getFullYear();
		for (var i = 0; i < 5; i++) {
			var year = currentYearYearline - i;
			var option = document.createElement('option');
			option.value = year;
			option.textContent = year;
			yearSelectrevenueyearline.appendChild(option);
		}

		var currentYearYearpie = new Date().getFullYear();
		for (var i = 0; i < 5; i++) {
			var year = currentYearYearline - i;
			var option = document.createElement('option');
			option.value = year;
			option.textContent = year;
			yearSelectcategoryyearpie.appendChild(option);
		}

		// Gắn sự kiện change cho các thẻ select
		// sự kiện tháng của biểu đồ line
		monthSelectrevenuemonthline.addEventListener('change', function () {
			updateChartrevenuemonthline(yearSelectrevenuemonthline.value, monthSelectrevenuemonthline.value);
		});
		//sự kiện năm của biểu đồ line
		yearSelectrevenuemonthline.addEventListener('change', function () {
			updateChartrevenuemonthline(yearSelectrevenuemonthline.value, monthSelectrevenuemonthline.value);
		});
		//sự kiện năm của biểu đồ line
		yearSelectrevenueyearline.addEventListener('change', function () {
			updateChartrevenueyearline(yearSelectrevenueyearline.value);
		});
		//sự kiện năm của biểu đồ pie
		yearSelectcategoryyearpie.addEventListener('change', function () {
			updateChartordermonthpie(yearSelectcategoryyearpie.value);
		});
		// sự kiện nhập chỉ tiêu theo tháng biểu đồ line
		monthlyGoalByMonthLine.addEventListener("change", function () {
			// Lấy giá trị mới từ input
			cookieValueMonthlyGoalByMonthLine = monthlyGoalByMonthLine.value;
			// Lưu giá trị mới vào cookie
			setCookie("monthlyGoalByMonthLine", cookieValueMonthlyGoalByMonthLine,
				365); // 365 là số ngày tồn tại của cookie
			getCookiemonthlyGoalByMonthLineAndSetInputIdmonthlyGoalByMonthLine();
		});
		// sự kiện nhập chỉ tiêu theo năm biểu đồ line
		monthlyGoalByYearLine.addEventListener("change", function () {
			// Lấy giá trị mới từ input
			cookieValueMonthlyGoalByYearLine = monthlyGoalByYearLine.value;
			// Lưu giá trị mới vào cookie
			setCookie("monthlyGoalByYearLine", cookieValueMonthlyGoalByYearLine,
				365); // 365 là số ngày tồn tại của cookie
			getCookiemonthlyGoalByYearLineAndSetInputIdmonthlyGoalByYearLine();
		});

		// sự kiện chọn category
		categorySelectedValue.addEventListener('change', function () {
			updateListProductByStatus(yearSelectcategoryyearpie.value, categorySelectedValue.value);
		});

		// Khởi tạo biểu đồ ban đầu
		// Biểu đồ 1
		myChart = new Chart(ctx, {
			type: 'line',
			data: {
				labels: [],
				datasets: [{
					label: 'Doanh thu tháng này',
					data: [],
					borderColor: 'red',
					fill: false,
					cubicInterpolationMode: 'monotone',
					tension: 0.4
				}, {
					label: 'Doanh thu tháng trước',
					data: [],
					borderColor: 'blue',
					fill: false,
					tension: 0.4
				}]
			},
			options: {
				responsive: true,
				plugins: {
					title: {
						display: false,
						text: 'Chart.js Line Chart - Cubic interpolation mode'
					},
				},
				interaction: {
					intersect: false,
				},
				scales: {
					x: {
						display: true,
						title: {
							display: true,
							text: 'Ngày'
						}
					},
					y: {
						display: true,
						title: {
							display: true,
							text: 'Doanh thu'
						},
						suggestedMin: 0,
						suggestedMax: 200
					}
				}
			}
		});

		// Biểu đồ 2
		myChart1 = new Chart(ctx1, {
			type: 'line',
			data: {
				labels: [],
				datasets: [{
					label: 'Doanh thu năm nay',
					data: [],
					borderColor: 'red',
					fill: false,
					cubicInterpolationMode: 'monotone',
					tension: 0.4
				}, {
					label: 'Doanh thu năm trước',
					data: [],
					borderColor: 'blue',
					fill: false,
					tension: 0.4
				}]
			},
			options: {
				responsive: true,
				plugins: {
					title: {
						display: false,
						text: 'Chart.js Line Chart - Cubic interpolation mode'
					},
				},
				interaction: {
					intersect: false,
				},
				scales: {
					x: {
						display: true,
						title: {
							display: true,
							text: 'Ngày'
						}
					},
					y: {
						display: true,
						title: {
							display: true,
							text: 'Doanh thu'
						},
						suggestedMin: 0,
						suggestedMax: 200
					}
				}
			}
		});

		// biểu đồ 3
		myChart2 = new Chart(ctx2, {
			type: 'doughnut',
			data: {
				labels: [],
				datasets: [{
					data: [],
					backgroundColor: [
						'#34495E', // Màu xám đậm
						'#F39C12', // Màu cam nhạt
						'#F1C40F', // Màu vàng đậm
						'#3498DB', // Màu xanh dương đậm
						'#9B59B6', // Màu tím đậm
					],
				}],
			},
			options: {
				responsive: true,
				plugins: {
					title: {
						display: false,
						text: 'Biểu đồ thống kê hóa đơn theo tháng',
					},
				},
			},
		});

		// Tự động chọn tháng và năm hiện tại
		monthSelectrevenuemonthline.value = new Date().getMonth() + 1;
		yearSelectrevenuemonthline.value = currentYearMonthline;
		yearSelectrevenueyearline.value = currentYearYearline;
		yearSelectcategoryyearpie.value = currentYearYearpie;
		categorySelectedValue.value = document.getElementById('categorySelect').value;

		// Gọi hàm cập nhật ban đầu để hiển thị dữ liệu
		updateChartrevenuemonthline(yearSelectrevenuemonthline.value, monthSelectrevenuemonthline.value);
		updateChartrevenueyearline(yearSelectrevenueyearline.value);
		updateChartordermonthpie(yearSelectcategoryyearpie.value);
	