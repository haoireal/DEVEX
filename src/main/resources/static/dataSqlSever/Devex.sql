use master
go
create database DEVEX
go
use DEVEX
go
-- tạo bảng
create table [Roles] (
	[ID] int identity(101,1) PRIMARY KEY,
	[Name] nvarchar(50) not null
);
go
create table [Users] (
	[Username] VARCHAR(20) PRIMARY KEY,
	[Fullname] nvarchar(50) not null,
	[Email] nvarchar(50) not null UNIQUE,
	[Password] nvarchar(50) not null,
	[Avatar] nvarchar(100) null,
	[Gender] nvarchar(10) not null,
	[CreatedDay] date not null,
	[Active] bit not null,
	[Role_ID] int not null,
	FOREIGN KEY ([Role_ID]) REFERENCES [Roles]([ID])
);
go
create table [Customers] (
	[Username] VARCHAR(20) PRIMARY KEY,
	[Address] nvarchar(100)  null,
	[Phone] varchar(13)  null,
	FOREIGN KEY ([Username]) REFERENCES [Users]([Username])
);
go
create table [Sellers] (
	[Username] VARCHAR(20) PRIMARY KEY,
	[Address] nvarchar(100) not null,
	[Phone] varchar(13) not null,
	[Mall] bit null,
	[Active] bit not null,
	FOREIGN KEY ([Username]) REFERENCES [Users]([Username])
);
go
create table [Follow] (
	[ID] int identity(101,1) PRIMARY KEY,
	[UserFollow_ID] varchar(20) not null,
	[Shop_ID] varchar(20) not null,
	FOREIGN KEY ([Shop_ID]) REFERENCES [Sellers]([Username]),
	FOREIGN KEY ([UserFollow_ID]) REFERENCES [Customers]([Username])
);
go
create table [Category] (
	[ID] int identity(101,1) PRIMARY KEY,
	[Name] nvarchar(50) not null
);
go
create table [Category_Details] (
	[ID] int identity(101,1) PRIMARY KEY,
	[Name] nvarchar(50) not null,
	[Category_ID] int not null,
	FOREIGN KEY ([Category_ID]) REFERENCES [Category]([ID])
);
go
create table [Product] (
	[ID] VARCHAR(50) PRIMARY KEY,
	[Name] nvarchar(100) not null,
	[Distributor] varchar(50) null,
	[Description] nvarchar(max) null,
	[CreatedDay] date not null,
	[Active] bit not null,
	[Shop_ID] varchar(20) not null,
	[Category_ID] int not null,
	FOREIGN KEY ([Shop_ID]) REFERENCES [Sellers]([Username]),
	FOREIGN KEY ([Category_ID]) REFERENCES [Category_Details]([ID])
);
go
create table [Image_Product] (
	[ID] varchar(35) PRIMARY KEY,
	[Name] varchar(50) not null,
	[Product_ID] varchar(50) not null,
	FOREIGN KEY ([Product_ID]) REFERENCES [Product]([ID])
);
go
create table [Sizes] (
	[ID] int identity(101,1) PRIMARY KEY,
	[Name] nvarchar(10) not null
);
go
create table [Colors] (
	[ID] int identity(101,1) PRIMARY KEY,
	[Name] nvarchar(10) not null
);
go
create table [Product_Variant] (
	[ID] VARCHAR(35) PRIMARY KEY,
	[Quantity] int not null,
	[Price] float not null,
	[PriceSale] float not null,
	[Product_ID] varchar(50) not null,
	[Size_ID] int null,
	[Color_ID] int null,
	FOREIGN KEY ([Product_ID]) REFERENCES [Product]([ID]),
	FOREIGN KEY ([Size_ID]) REFERENCES [Sizes]([ID]),
	FOREIGN KEY ([Color_ID]) REFERENCES [Colors]([ID])
);
go
create table [Comment] (
	[ID] int identity(10001,1) PRIMARY KEY,
	[Content] nvarchar(max) null,
	[Rating] int not null,
	[CreatedAt] datetime not null,
	[Product_ID] varchar(50) not null,
	[Customer_ID] varchar(20) not null,
	FOREIGN KEY ([Product_ID]) REFERENCES [Product]([ID]),
	FOREIGN KEY ([Customer_ID]) REFERENCES [Customers]([Username])
);
go
create table [Comment_Reply] (
	[ID] int identity(10001,1) PRIMARY KEY,
	[Content] nvarchar(max) null,
	[CreatedAt] datetime not null,
	[IsCustomerReply] bit not null,
	[Comment_ID] int not null,
	FOREIGN KEY ([Comment_ID]) REFERENCES [Comment]([ID])
);
go
create table [Vouchers] (
	[ID] int identity(10001,1) PRIMARY KEY,
	[Name] nvarchar(100) not null,
	[Discount] float not null,
	[Note] nvarchar(100) not null,
	[StartDate] date null,
	[EndDate] date null,
	[Code] VARCHAR(15) UNIQUE,
	[Banner] varchar(50) not null,
);
go
create table [Voucher_Details] (
	[ID] int identity(100001,1) PRIMARY KEY,
	[Customer_ID] varchar(20) not null,
	[Voucher_ID] int not null,
	FOREIGN KEY ([Customer_ID]) REFERENCES [Customers]([Username]),
	FOREIGN KEY ([Voucher_ID]) REFERENCES [Vouchers]([ID])
);
go
create table [Payment] (
	[ID] int identity(1001,1) PRIMARY KEY,
	[Name] nvarchar(20) not null
);
go
create table [Order_Status] (
	[ID] int identity(1001,1) PRIMARY KEY,
	[Name] nvarchar(35) not null
);
go
create table [Orders] (
	[ID] varchar(35) PRIMARY KEY ,
	[Note] nvarchar(max) null,
	[CreatedDay] date not null,
	[Total] float not null,
	[PriceDiscount] float null,
	[Voucher_ID] int not null,
	[Customer_ID] varchar(20) not null,
	[Payment_ID] int not null,
	[Status_ID] int not null,
	FOREIGN KEY ([Voucher_ID]) REFERENCES [Vouchers]([ID]),
	FOREIGN KEY ([Customer_ID]) REFERENCES [Customers]([Username]),
	FOREIGN KEY ([Payment_ID]) REFERENCES [Payment]([ID]),
	FOREIGN KEY ([Status_ID]) REFERENCES [Order_Status]([ID])
);
go
create table [Order_Details] (
	[ID] VARCHAR(36) PRIMARY KEY,
	[Quantity] int not null,
	[Price] float not null,
	[Product_ID] varchar(35) not null,
	[Order_ID] varchar(35) not null,
	FOREIGN KEY ([Product_ID]) REFERENCES [Product_Variant]([ID]),
	FOREIGN KEY ([Order_ID]) REFERENCES [Orders]([ID])
);
go
-- tạo trigger để insert, update, delete
-- insert ProductID
CREATE TRIGGER trg_UpdateProductID
ON [Product]
AFTER INSERT
AS
BEGIN
    -- Khai báo các biến
    DECLARE @ProductID INT;
    DECLARE @CategoryID INT;
    DECLARE @ShopID VARCHAR(20);
    DECLARE @ID VARCHAR(50);

    -- Lấy dữ liệu từ bảng Inserted (bảng tạm chứa các bản ghi được chèn)
    SELECT @CategoryID = Category_ID, @ShopID = Shop_ID
    FROM Inserted;

    -- Tạo ID mới cho sản phẩm bằng cách kết hợp CategoryID, ShopID và số tự tăng
    SET @ProductID = (SELECT ISNULL(MAX(RIGHT(ID, LEN(ID) - CHARINDEX('-', ID))), 10000) + 1 FROM Product);
    SET @ID = CONCAT(@CategoryID, '-', @ShopID, '-', @ProductID);

    -- Cập nhật trường ID cho bản ghi được chèn
    UPDATE [Product]
    SET ID = @ID
    WHERE ID IN (SELECT ID FROM Inserted);
END;
go
-- insert OrderID
CREATE TRIGGER trg_UpdateOrderID
ON [Orders]
AFTER INSERT
AS
BEGIN
    DECLARE @NewID VARCHAR(36);
    DECLARE @Exists INT;

    -- Tạo chuỗi ngẫu nhiên
    SET @NewID = CONVERT(VARCHAR(36), CRYPT_GEN_RANDOM(15), 2);

    -- Kiểm tra chuỗi ngẫu nhiên đã tồn tại hay chưa
    SELECT @Exists = COUNT(*) FROM [Orders] WHERE ID = @NewID;

    -- Nếu chuỗi đã tồn tại, tiếp tục tạo chuỗi ngẫu nhiên mới cho đến khi không trùng lặp
    WHILE @Exists > 0
    BEGIN
        SET @NewID = CONVERT(VARCHAR(36), CRYPT_GEN_RANDOM(15), 2);
        SELECT @Exists = COUNT(*) FROM [Orders] WHERE ID = @NewID;
    END;

    -- Cập nhật trường ID cho bản ghi được chèn
    UPDATE [Orders]
    SET ID = @NewID
    WHERE ID IN (SELECT ID FROM Inserted);
END;
go
-- insert Order-Details-ID
CREATE TRIGGER trg_UpdateOrderDetailsID
ON [Order_Details]
AFTER INSERT
AS
BEGIN
    DECLARE @NewID VARCHAR(36);
    DECLARE @Exists INT;

    -- Tạo chuỗi ngẫu nhiên
    SET @NewID = CONVERT(VARCHAR(36), CRYPT_GEN_RANDOM(15), 2);

    -- Kiểm tra chuỗi ngẫu nhiên đã tồn tại hay chưa
    SELECT @Exists = COUNT(*) FROM [Order_Details] WHERE ID = @NewID;

    -- Nếu chuỗi đã tồn tại, tiếp tục tạo chuỗi ngẫu nhiên mới cho đến khi không trùng lặp
    WHILE @Exists > 0
    BEGIN
        SET @NewID = CONVERT(VARCHAR(36), CRYPT_GEN_RANDOM(15), 2);
        SELECT @Exists = COUNT(*) FROM [Order_Details] WHERE ID = @NewID;
    END;

    -- Cập nhật trường ID cho bản ghi được chèn
    UPDATE [Order_Details]
    SET ID = @NewID
    WHERE ID IN (SELECT ID FROM Inserted);
END;
go
-- insert image product id
CREATE TRIGGER trg_UpdateImageProductID
ON [Image_Product]
AFTER INSERT
AS
BEGIN
    DECLARE @NewID VARCHAR(36);
    DECLARE @Exists INT;

    -- Tạo chuỗi ngẫu nhiên
    SET @NewID = CONVERT(VARCHAR(36), CRYPT_GEN_RANDOM(20), 2);

    -- Kiểm tra chuỗi ngẫu nhiên đã tồn tại hay chưa
    SELECT @Exists = COUNT(*) FROM [Image_Product] WHERE ID = @NewID;

    -- Nếu chuỗi đã tồn tại, tiếp tục tạo chuỗi ngẫu nhiên mới cho đến khi không trùng lặp
    WHILE @Exists > 0
    BEGIN
        SET @NewID = CONVERT(VARCHAR(36), CRYPT_GEN_RANDOM(20), 2);
        SELECT @Exists = COUNT(*) FROM [Image_Product] WHERE ID = @NewID;
    END;

    -- Cập nhật trường ID cho bản ghi được chèn
    UPDATE [Image_Product]
    SET ID = @NewID
    WHERE ID IN (SELECT ID FROM Inserted);
END;
go
















