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

.input-group-addon a, .input-group-addon a:hover {
	color: #333
}

mark {
	position: absolute;
	background-color: transparent;
	top: 2rem;
	color: white;
}
</style>

<body>

	<div class="login-top">
		<img src="/PhamGiaHaoASM/views/img/logo.png"
			alt="Responsive image LOGO" class="img-fluid">
	</div>
	<div class="text-center pt-3 pb-3 d-flex justify-content-center"
		style="width: 100%">
		<mark class="p-3">${message}</mark>
	</div>
	<div class="d-flex justify-content-center align-items-center"
		style="width: 100vw;">
		<section class="login-box">
			<h2 class="text-white">Quên mật khẩu</h2>
			<form class="mt-4" action="/PhamGiaHaoASM/netflix/forgetpassword"
				method="post">
				<div class="mb-3 bg-white rounded px-2">
					<input type="email" id="inputEmail" placeholder="Địa chỉ email"
						pattern="[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$" maxlength="50"
						minlength="5" required class="form-control border-0 p-0" id=""
						name="email">
				</div>
				<div class="mb-3 bg-white rounded px-2 d-flex"
					id="show_hide_password_old">
					<div class="">
						<input type="password" placeholder="Mật khẩu cũ"
							class="form-control border-0 pr-5 pl-0" id="" name="oldpassword"
							required>
					</div>

				</div>
				<div class="mb-3 bg-white rounded px-2 d-flex"
					id="show_hide_password">
					<div class="">
						<input type="password" placeholder="Mật khẩu mới"
							class="form-control border-0 pr-5 pl-0" id="" name="password"
							required>
					</div>
					<div class="input-group-addon ml-5">
						<a href=""><i class="fa fa-eye-slash mt-2 pt-1 pb-1 ml-4"
							aria-hidden="true"></i></a>
					</div>
				</div>
				<div class="mb-3 bg-white rounded px-2 d-flex"
					id="show_hide_password_cf">
					<div class="">
						<input type="password" placeholder="Xác nhận mật khẩu"
							class="form-control border-0 pr-5 pl-0" id="" name="cfpassword">
					</div>
					<div onclick="hideShow()" class="input-group-addon ml-5">
						<a href=""><i class="fa fa-eye-slash mt-2 pt-1 pb-1 ml-4"
							aria-hidden="true"></i></a>
					</div>
				</div>
				<button type="submit" class="btn btn-danger mt-3"
					style="width: 100%;">Xác nhận</button>

				<div class="mt-3 ">
					<!-- <img width="20px" src="./images/fb.png" alt=""> -->
					<p class="m-0  text-white">
						<span style="color: rgba(212, 212, 212, 0.75);">Tạo tài
							khoản mới?</span> <a href="/PhamGiaHaoASM/netflix/signup">Đăng kí
							ngay</a>
					</p>
					<p class="m-0 small-text text-white  mt-2">
						<span style="color: rgba(212, 212, 212, 0.75);"><a
							href="/PhamGiaHaoASM/netflix/login">Trở về</a> trang đăng nhập</span>
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
		$(document)
				.ready(
						function() {
							$("#show_hide_password a")
									.on(
											'click',
											function(event) {
												event.preventDefault();
												if ($(
														'#show_hide_password input')
														.attr("type") == "text") {
													$(
															'#show_hide_password input')
															.attr('type',
																	'password');
													$('#show_hide_password i')
															.addClass(
																	"fa-eye-slash");
													$('#show_hide_password i')
															.removeClass(
																	"fa-eye");
												} else if ($(
														'#show_hide_password input')
														.attr("type") == "password") {
													$(
															'#show_hide_password input')
															.attr('type',
																	'text');
													$('#show_hide_password i')
															.removeClass(
																	"fa-eye-slash");
													$('#show_hide_password i')
															.addClass("fa-eye");
												}
											});
						}

				);
		function hideShow() {

			event.preventDefault();
			if ($('#show_hide_password_cf input').attr("type") == "text") {
				$('#show_hide_password_cf input').attr('type', 'password');
				$('#show_hide_password_cf i').addClass("fa-eye-slash");
				$('#show_hide_password_cf i').removeClass("fa-eye");
			} else if ($('#show_hide_password_cf input').attr("type") == "password") {
				$('#show_hide_password_cf input').attr('type', 'text');
				$('#show_hide_password_cf i').removeClass("fa-eye-slash");
				$('#show_hide_password_cf i').addClass("fa-eye");
			}
		};
	</script>

</body>
</html>