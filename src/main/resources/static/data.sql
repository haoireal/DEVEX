use master
go
create database HB_Coffee
go
use HB_Coffee
go

-- create table
create table [Account] (
	[ID] int identity(101,1) primary key,
	[Fullname] nvarchar(50) not null,
	[Email] nvarchar(30) not null,
	[Password] nvarchar(30) not null,
	[Gender] bit,
	[Birthday] date,
	[Address] nvarchar(100),
	[Role] bit
)
go
create table [TypeOfDrink] (
	[ID] int identity(101,1) primary key,
	[Name] nvarchar(50) not null
)
go
create table [Drinks] (
	[ID] int identity(10001,1) primary key,
	[Name] nvarchar(100) not null,
	[Price] float not null,
	[Size] varchar(1) not null CHECK ([Size] IN('S', 'M', 'L')),
	[Image] nvarchar(100) not null,
	[Type_ID] int,
	FOREIGN KEY ([Type_ID]) REFERENCES [TypeOfDrink](ID)
)
go
create table [TypeOfTopping] (
	[ID] int identity(101,1) primary key,
	[Name] nvarchar(30) not null
)
go
create table [Topping] (
	[ID] int identity(101,1) primary key,
	[Name] nvarchar(50) not null,
	[Price] float not null,
	[Type_ID] int,
	FOREIGN KEY ([Type_ID]) REFERENCES [TypeOfTopping](ID)
)
go
create table [Voucher] (
	[ID] int identity(1001,1) primary key,
	[Name] nvarchar(100) not null,
	[Description] nvarchar(max) not null,
	[Code] nvarchar(10) not null,
	[Start_Date] date not null,
	[End_Date] date not null,
	[Discount] float not null
)
go
create table [DetailOfVoucher] (
	[ID] int identity(1001,1) primary key,
	[Customer_ID] int not null,
	[Voucher_ID] int not null,
	FOREIGN KEY ([Customer_ID]) REFERENCES [Account](ID),
	FOREIGN KEY ([Voucher_ID]) REFERENCES [Voucher](ID)
)
go
create table [Bill] (
	[ID] int identity(10001,1) primary key,
	[Total] float not null,
	[Date] date not null,
	[Payment] nvarchar(15) not null CHECK ([Payment] IN(N'Tiền mặt', N'Chuyển khoản')),
	[Customer_ID] int not null,
	[Voucher_ID] int null,
	FOREIGN KEY ([Customer_ID]) REFERENCES [Account](ID),
	FOREIGN KEY ([Voucher_ID]) REFERENCES [Voucher](ID)
)
go
create table [DetailOfBill] (
	[ID] int identity(10001,1) primary key,
	[Quantity] int not null,
	[Size] varchar(1) not null CHECK ([Size] IN('S', 'M', 'L')),
	[Price] float not null,
	[Bill_ID] int not null,
	[Drink_ID] int not null,
	[Topping_ID] int null,
	FOREIGN KEY ([Bill_ID]) REFERENCES [Bill](ID),
	FOREIGN KEY ([Drink_ID]) REFERENCES [Drinks](ID),
	FOREIGN KEY ([Topping_ID]) REFERENCES [Topping](ID)
)
go
-- Insert data
insert into Account
values	(N'Lê Huy Bảo', N'baolh106@gmail.com', '123', 1, '2003-06-10', N'Phú Nhuận', 0),
		(N'Phạm Gia Hào', N'haoireal@gmail.com', '123', 1, '2003-11-11', N'Gò Vấp', 0),
		(N'HB Coffee', N'hbcoffee@gmail.com', '123', 0, '2003-06-20', N'Quận 1', 1)

go
insert into TypeOfDrink
values	(N'Cà phê'),
		(N'CloudFee'),
		(N'CloudTea'),
		(N'Hi-Tea Healthy'),
		(N'Trà Trái Cây - Trà Sữa')

go
insert into Drinks
values	(N'Cà phê sữa đá', 25000, 'S', N'ca-phe-sua-da.png', 101),
		(N'Cà phê sữa đá', 30000, 'M', N'ca-phe-sua-da.png', 101),
		(N'Cà phê sữa đá', 35000, 'L', N'ca-phe-sua-da.png', 101),
		(N'Cà phê đen đá', 23000, 'S', N'ca-phe-den-da.jpg', 101),
		(N'Cà phê đen đá', 38000, 'M', N'ca-phe-den-da.jpg', 101),
		(N'Cà phê đen đá', 33000, 'L', N'ca-phe-den-da.jpg', 101),
		(N'Bạc xỉu', 39000, 'S', N'bac-siu.jpg', 101),
		(N'Bạc xỉu', 34000, 'M', N'bac-siu.jpg', 101),
		(N'Bạc xỉu', 39000, 'L', N'bac-siu.jpg', 101),
		(N'Đường đen sữa đá', 35000, 'S', N'dd-suada.jpg', 101),
		(N'Đường đen sữa đá', 40000, 'M', N'dd-suada.jpg', 101),
		(N'Đường đen sữa đá', 45000, 'L', N'dd-suada.jpg', 101),
		(N'Đường đen Marble Latte', 40000, 'M', N'dd-latte.jpg', 101),
		(N'Đường đen Marble Latte', 45000, 'L', N'dd-latte.jpg', 101),
		(N'Caramel Macchiato đá', 40000, 'M', N'caramel-macchiato.jpg', 101),
		(N'Caramel Macchiato đá', 45000, 'L', N'caramel-macchiato.jpg', 101),
		(N'Latte - đá', 40000, 'M', N'latte-da.jpg', 101),
		(N'Latte - đá', 45000, 'L', N'latte-da.jpg', 101),
		(N'Latte - nóng', 40000, 'M', N'latte_nong.jpg', 101),
		(N'Latte - nóng', 45000, 'L', N'latte_nong.jpg', 101),
		(N'Americano - đá', 38000, 'M', N'arme-da.jpg', 101),
		(N'Americano - đá', 43000, 'L', N'arme-da.jpg', 101),
		(N'Americano - nóng', 38000, 'M', N'arme-nong.jpg', 101),
		(N'Americano - nóng', 43000, 'L', N'arme-nong.jpg', 101),
		(N'Cappuccino - đá', 40000, 'M', N'Capu-da.jpg', 101),
		(N'Cappuccino - đá', 45000, 'L', N'Capu-da.jpg', 101),
		(N'Cappuccino - nóng', 40000, 'M', N'capu-nong.jpg', 101),
		(N'Cappuccino - nóng', 45000, 'L', N'capu-nong.jpg', 101),
		(N'Espresso - đá', 39000, 'M', N'espressoDa.jpg', 101),
		(N'Espresso - đá', 44000, 'L', N'espressoDa.jpg', 101),
		(N'Espresso - nóng', 39000, 'M', N'espressoNong.jpg', 101),
		(N'Espresso - nóng', 44000, 'L', N'espressoNong.jpg', 101),
		(N'CloudFee hạnh nhân nướng', 50000, 'M', N'cloudfee-hanh-nhan-nuong-min.png', 102),
		(N'CloudFee hạnh nhân nướng', 55000, 'L', N'cloudfee-hanh-nhan-nuong-min.png', 102),
		(N'CloudFee caramel', 50000, 'M', N'cloudfee-caramel.jpg', 102),
		(N'CloudFee caramel', 55000, 'L', N'cloudfee-caramel.jpg', 102),
		(N'CloudFee Sài Gòn', 50000, 'M', N'cloudfee-classic.jpg', 102),
		(N'CloudFee Sài Gòn', 55000, 'L', N'cloudfee-classic.jpg', 102),
		(N'CloudTea Oolong nướng kem dừa', 49000, 'M', N'cloudtea-oolong-nuong-kem-dua-min.png', 103),
		(N'CloudTea Oolong nướng kem dừa', 54000, 'L', N'cloudtea-oolong-nuong-kem-dua-min.png', 103),
		(N'CloudTea Oolong nướng caramel', 49000, 'M', N'cloudtea-caramel.jpg', 103),
		(N'CloudTea Oolong nướng caramel', 54000, 'L', N'cloudtea-caramel.jpg', 103),
		(N'CloudTea Oolong nướng kem cheese', 49000, 'M', N'cloudtea_cheese.jpg', 103),
		(N'CloudTea Oolong nướng kem cheese', 54000, 'L', N'cloudtea_cheese.jpg', 103),
		(N'CloudTea Oolong nướng kem dừa đá xay', 49000, 'M', N'cloudtea-daxay.jpg', 103),
		(N'CloudTea Oolong nướng kem dừa đá xay', 54000, 'L', N'cloudtea-daxay.jpg', 103),
		(N'Hi-Tea vải', 45000, 'S', N'hi-tea-vai.png', 104),
		(N'Hi-Tea vải', 50000, 'M', N'hi-tea-vai.png', 104),
		(N'Hi-Tea vải', 55000, 'L', N'hi-tea-vai.png', 104),
		(N'Hi-Tea đào', 45000, 'S', N'hi-tea-dao.jpg', 104),
		(N'Hi-Tea đào', 50000, 'M', N'hi-tea-dao.jpg', 104),
		(N'Hi-Tea đào', 55000, 'L', N'hi-tea-dao.jpg', 104),
		(N'Hi-Tea thơm trân châu', 49000, 'S', N'hitea-thom.jpg', 104),
		(N'Hi-Tea thơm trân châu', 54000, 'M', N'hitea-thom.jpg', 104),
		(N'Hi-Tea thơm trân châu', 59000, 'L', N'hitea-thom.jpg', 104),
		(N'Hi-Tea phúc bồn tử', 45000, 'S', N'hitea-phuc-bon-tu.jpg', 104),
		(N'Hi-Tea phúc bồn tử', 50000, 'M', N'hitea-phuc-bon-tu.jpg', 104),
		(N'Hi-Tea phúc bồn tử', 55000, 'L', N'hitea-phuc-bon-tu.jpg', 104),
		(N'Hi-Tea đá tuyết Yuzu', 49000, 'S', N'hitea-da-tuyet-vai.jpg', 104),
		(N'Hi-Tea đá tuyết Yuzu', 54000, 'M', N'hitea-da-tuyet-vai.jpg', 104),
		(N'Hi-Tea đá tuyết Yuzu', 59000, 'L', N'hitea-da-tuyet-vai.jpg', 104),
		(N'Trà sữa Oolong nướng trân châu', 49000, 'S', N'tra-sua-oolong-nuong-tran-chau.png', 105),
		(N'Trà sữa Oolong nướng trân châu', 54000, 'M', N'tra-sua-oolong-nuong-tran-chau.png', 105),
		(N'Trà sữa Oolong nướng trân châu', 59000, 'L', N'tra-sua-oolong-nuong-tran-chau.png', 105),
		(N'Hồng trà sữa trân châu', 45000, 'S', N'hong-tra-sua-tran-chau.jpg', 105),
		(N'Hồng trà sữa trân châu', 50000, 'M', N'hong-tra-sua-tran-chau.jpg', 105),
		(N'Hồng trà sữa trân châu', 55000, 'L', N'hong-tra-sua-tran-chau.jpg', 105),
		(N'Trà đào cam sả', 49000, 'S', N'tra-dao-cam-sa-da.png', 105),
		(N'Trà đào cam sả', 54000, 'M', N'tra-dao-cam-sa-da.png', 105),
		(N'Trà đào cam sả', 59000, 'L', N'tra-dao-cam-sa-da.png', 105),
		(N'Trà hạt sen', 49000, 'S', N'tra-sen.jpg', 105),
		(N'Trà hạt sen', 54000, 'M', N'tra-sen.jpg', 105),
		(N'Trà hạt sen', 59000, 'L', N'tra-sen.jpg', 105),
		(N'Trà đen Macchiato', 45000, 'S', N'tra-den-matchiato.jpg', 105),
		(N'Trà đen Macchiato', 50000, 'M', N'tra-den-matchiato.jpg', 105),
		(N'Trà đen Macchiato', 55000, 'L', N'tra-den-matchiato.jpg', 105),
		(N'Trà sữa mắc ca trân châu', 49000, 'S', N'tra-sua-mac-ca.jpg', 105),
		(N'Trà sữa mắc ca trân châu', 54000, 'M', N'tra-sua-mac-ca.jpg', 105),
		(N'Trà sữa mắc ca trân châu', 59000, 'L', N'tra-sua-mac-ca.jpg', 105)

go
insert into TypeOfTopping
values	(N'Coffee-Cloud'),
		(N'Tea')

go
insert into Topping
values	(N'Kem phô mai Macchiato', 10000, 101),
		(N'Sốt Caramel', 5000, 101),
		(N'Thạch cafe', 10000, 101),
		(N'Kem trứng', 10000, 101),
		(N'Đào miếng', 8000, 102),
		(N'Trái vải', 8000, 102),
		(N'Trân châu trắng', 10000, 102),
		(N'Hạt sen', 8000, 102),
		(N'Milk foam', 10000, 102)

go
insert into Voucher
values	(N'Voucher Nice Day', N'Giảm 20% cho hoá đơn trên 120K khi uống tại quán', 'DONFLOW20', '2023-5-1', '2023-5-30', 0.2),
		(N'Voucher Happy Birthday H&B Coffee', N'Giảm 30K cho hoá đơn trên 70K duy nhất trong ngày khi đến quán uống trực tiếp và đã đăng kí thành viên H&B Coffee', 'BIRTHDAY30', '2023-3-20', '2023-3-21', -30000),
		(N'Voucher Couple Festival', N'Giảm 15% cho các cặp đôi nhân ngày 14-2 khi order hoá đơn trên 80K kéo dài đến hết ngày 16-2', 'COUPLE142', '2023-2-14', '2023-2-16', 0.15),
		(N'Voucher Tết Dương Lịch 1-1', N'Giảm 11% cho hoá đơn trên 111K khi uống tại quán duy nhất trong ngày 1-1', 'TETTET11', '2023-1-1', '2023-1-2', 0.11),
		(N'Voucher Combo 30-4 & 1-5', N' Giảm 25K cho hoá đơn trên 80K khi uống tại quán trong hai ngày 30-4 và 1-5', 'HOLIDAY25', '2023-4-30', '2023-5-1', -25000),
		(N'Voucher Quốc Tế Thiếu Nhi', N' Giảm 16% cho hoá đơn trên 70K chỉ áp dụng cho khách hàng dưới 18 tuổi', 'YOUNG16', '2023-6-1', '2023-6-2', 0.16)

go
insert into DetailOfVoucher
values	(101, 1006),
		(101, 1002),
		(102, 1003),
		(102, 1001)

go
insert into Bill
values	(70000, '2023-4-16', N'Chuyển khoản', 101, null),
		(65000, '2023-4-30', N'Tiền mặt', 102, 1005),
		(106000, '2023-5-15', N'Tiền mặt', 101, null)

go
insert into DetailOfBill
values	(1, 'M', 30000, 10001, 10002, null),
		(1, 'M', 40000, 10001, 10017, null),
		(1, 'S', 45000, 10002, 10047, null),
		(1, 'S', 45000, 10002, 10050, null),
		(1, 'M', 60000, 10003, 10048, 107),
		(2, 'S', 46000, 10003, 10004, null)
