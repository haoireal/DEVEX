<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Netflix Việt Nam – Xem chương trình truyền hình trực
	tuyến, Xem phim trực tuyến</title>
<link rel="shortcut icon"
	href="https://assets.nflxext.com/us/ffe/siteui/common/icons/nficon2016.ico">
<link rel="canonical"
	href="https://getbootstrap.com/docs/4.5/examples/floating-labels/">
<link href="../assets/dist/css/bootstrap.css" rel="stylesheet">
<link href="floating-labels.css" rel="stylesheet">

<!-- BootStrap4 Import -->
<!-- JQWERY -->
<script
	src="https://cdn.jsdelivr.net/npm/jquery@3.6.1/dist/jquery.slim.min.js"></script>
<!-- Angular -->
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular.min.js"></script>
<script
	src="https://ajax.googleapis.com/ajax/libs/angularjs/1.6.9/angular-route.js"></script>
<!-- CSS -->
<link rel="stylesheet"
	href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/css/bootstrap.min.css"
	integrity="sha384-xOolHFLEh07PJGoPkLv1IbcEPTNtaed2xpHsD9ESMhqIYd0nLMwNLD69Npy4HI+N"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
	crossorigin="anonymous"></script>
<!-- ICON -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">

</head>

<style>
body {
	background-image: linear-gradient(rgba(0, 0, 0, 0.491),
		rgba(0, 0, 0, 0.416)), url(/PhamGiaHaoASM/views/img/bg-hero.jpg);
	height: 100vh;
}

.login-top {
	width: 100vw;
	height: 70px;
	background-color: rgba(0, 0, 0, 0);
	padding: 0px 20px;
	display: flex;
	align-items: center;
}

.login-top img {
	width: 150px;
}

.login-box {
	background-color: rgba(0, 0, 0, 0.899);
	width: 430px;
	border-radius: 5px;
	padding: 50px;
}

.small-text {
	font-size: 13px;
}

.form-control {
	box-shadow: none !important;
}

.otp-input {
	border: none;
	border-bottom: 2px solid transparent;
	border-radius: 0px;
	font-size: 1.1rem;
	padding: 5px;
	outline: none !important;
	font-weight: 600;
	text-align: center;
}

input:active, input:focus {
	outline: 0px !important;
	box-shadow: none !important;
	border-bottom: 2px solid red !important;
	outline: none !important;
}

/* Chrome, Safari, Edge, Opera */
input::-webkit-outer-spin-button, input::-webkit-inner-spin-button {
	-webkit-appearance: none;
	margin: 0;
}

/* Firefox */
input[type=number] {
	-moz-appearance: textfield;
}

#OTP_Div {
	display: grid;
	grid-template-columns: repeat(5, 20%);
	grid-gap: 10px;
	padding: 5%;
}
</style>

<body>
	<c:url var="url" value="/netflix" />
	<div class="login-top">
		<img src="/PhamGiaHaoASM/views/img/logo.png"
			alt="Responsive image LOGO" class="img-fluid">
	</div>

	<div class="d-flex justify-content-center align-items-center"
		style="width: 100vw;">
		<section class="login-box">
			<h2 class="text-white">Xác thực tài khoản</h2>
			<form class="m-0 mt-4" action="/PhamGiaHaoASM/netflix/verifi"
				method="post">
				<div class="container w-100 text-center rounded-lg shadow-lg p-3">
					<div class="alert alert-danger" role="alert" style="display: none"
						id='alert_box'>All fields are required!</div>
					<div id="OTP_Div" class="col-lg-10 col-sm-12 col-md-10">
						<input style="background-color: rgba(122, 240, 255, 0.1);"
							name="o1" autofocus class="form-control otp-input"
							onkeyup="alter_box(this.id)" maxlength="1" required type="number"
							id="o1" /> <input name="o2"
							style="background-color: rgba(122, 240, 255, 0.1);"
							class="form-control otp-input" required maxlength="1"
							type="number" id="o2" onkeyup="alter_box(this.id)" /> <input
							name="o3" style="background-color: rgba(122, 240, 255, 0.1);"
							class="form-control otp-input" required maxlength="1"
							type="number" id="o3" onkeyup="alter_box(this.id)" /> <input
							name="o4" style="background-color: rgba(122, 240, 255, 0.1);"
							class="form-control otp-input" required maxlength="1"
							type="number" id="o4" onkeyup="alter_box(this.id)" /> <input
							name="o5" style="background-color: rgba(122, 240, 255, 0.1);"
							class="form-control otp-input" required maxlength="1"
							type="number" id="o5" onkeyup="alter_box(this.id)" />
					</div>

				</div>

				<button type="submit" class="btn btn-danger mt-3" name="submit_button" value="Submit"
					style="width: 100%;">Xác nhận</button>

				<div class="mt-3 ">
					<!-- <img width="20px" src="./images/fb.png" alt=""> -->
					<p class="m-0 small-text text-white  mb-3">
						<span style="color: rgba(212, 212, 212, 0.75);">Chưa nhận
							được mã thì bấm vào</span> <button formaction="${url}/send_verifi"><a href="">Giử lại mã</a></button>
					</p>
					<p class="m-0  text-white">
						<span style="color: rgba(212, 212, 212, 0.75);"><a
							href="/PhamGiaHaoASM/netflix/login">Trở về</a> Trang đăng nhập</span>
					</p>
				</div>
			</form>
		</section>
	</div>


	<script
		src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
		crossorigin="anonymous"></script>

	<script type="text/javascript">
		function alter_box(id) {
			var id_num = parseInt(id.split('')[1]);
			var key = event.keyCode || event.charCode;
			if (key === 8 || key === 46) {
				if (id_num != 1) {
					var prev = 'o' + (id_num - 1).toString();
					console.log(id_num, prev);
					document.getElementById(prev).focus();

				}
			} else {
				var id_num = parseInt(id.split('')[1]);
				if (id_num != 5) {

					var next = 'o' + (id_num + 1).toString();

					document.getElementById(next).focus();
					id_num++;
				}
			}
		}
	</script>

</body>
</html>