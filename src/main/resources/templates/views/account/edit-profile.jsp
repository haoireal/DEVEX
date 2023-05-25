<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="UTF-8">
<title>Document</title>
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
<!-- ICON -->
<link rel="stylesheet"
	href="https://use.fontawesome.com/releases/v5.7.0/css/all.css"
	integrity="sha384-lZN37f5QGtY3VHgisS14W3ExzMWZxybE1SJSEsQp9S+oqd12jhcu+A56Ebc1zFSJ"
	crossorigin="anonymous">
</head>



<body>
	<div id="content">
		<div class="container">
			<h1>Edit Profile</h1>
			<hr>
			<div class="row">
				<!-- left column -->
				<div class="col-md-3">
					<div class="text-center">
						<img src="//placehold.it/100" class="avatar img-circle"
							alt="avatar">
						<h6>Upload a different photo...</h6>

						<input type="file" class="form-control">
					</div>
				</div>

				<!-- edit form column -->
				<div class="col-md-9 personal-info">
					<div class="alert alert-info alert-dismissable">
						<a class="panel-close close" data-dismiss="alert">×</a> <i
							class="fa fa-coffee"></i> This is an <strong>.alert</strong>. Use
						this to show important messages to the user.
					</div>
					<h3>Personal info</h3>

					<form class="form-horizontal" role="form">
						<div class="form-group">
							<label class="col-lg-3 control-label">First name:</label>
							<div class="col-lg-8">
								<input class="form-control" type="text" value="Jane">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">Last name:</label>
							<div class="col-lg-8">
								<input class="form-control" type="text" value="Bishop">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">Company:</label>
							<div class="col-lg-8">
								<input class="form-control" type="text" value="">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">Email:</label>
							<div class="col-lg-8">
								<input class="form-control" type="text"
									value="janesemail@gmail.com">
							</div>
						</div>
						<div class="form-group">
							<label class="col-lg-3 control-label">Time Zone:</label>
							<div class="col-lg-8">
								<div class="ui-select">
									<select id="user_time_zone" class="form-control">
										<option value="Hawaii">(GMT-10:00) Hawaii</option>
										<option value="Alaska">(GMT-09:00) Alaska</option>
										<option value="Pacific Time (US &amp; Canada)">(GMT-08:00)
											Pacific Time (US &amp; Canada)</option>
										<option value="Arizona">(GMT-07:00) Arizona</option>
										<option value="Mountain Time (US &amp; Canada)">(GMT-07:00)
											Mountain Time (US &amp; Canada)</option>
										<option value="Central Time (US &amp; Canada)"
											selected="selected">(GMT-06:00) Central Time (US
											&amp; Canada)</option>
										<option value="Eastern Time (US &amp; Canada)">(GMT-05:00)
											Eastern Time (US &amp; Canada)</option>
										<option value="Indiana (East)">(GMT-05:00) Indiana
											(East)</option>
									</select>
								</div>
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">Username:</label>
							<div class="col-md-8">
								<input class="form-control" type="text" value="janeuser">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">Password:</label>
							<div class="col-md-8">
								<input class="form-control" type="password" value="11111122333">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label">Confirm password:</label>
							<div class="col-md-8">
								<input class="form-control" type="password" value="11111122333">
							</div>
						</div>
						<div class="form-group">
							<label class="col-md-3 control-label"></label>
							<div class="col-md-8">
								<input type="button" class="btn btn-primary"
									value="Save Changes"> <span></span> <input type="reset"
									class="btn btn-default" value="Cancel">
							</div>
						</div>
					</form>
				</div>
			</div>
		</div>
		<hr>
	</div>
	<script
		src="https://cdn.jsdelivr.net/npm/jquery@3.5.1/dist/jquery.slim.min.js"
		integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
		crossorigin="anonymous"></script>
	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.2/dist/js/bootstrap.bundle.min.js"
		integrity="sha384-Fy6S3B9q64WdZWQUiU+q4/2Lc9npb8tCaSX9FK7E8HnRr0Jz8D6OP9dO5Vg3Q9ct"
		crossorigin="anonymous"></script>
</body>
</html>