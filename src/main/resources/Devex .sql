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
	[Email] nvarchar(50) null UNIQUE,
	[Password] nvarchar(50) not null,
	[Phone] varchar(13) null UNIQUE,
	[Avatar] nvarchar(100) null,
	[Gender] nvarchar(10) not null,
	[Createdday] date not null,
	[Active] bit not null,
	[Role_ID] int not null,
	FOREIGN KEY ([Role_ID]) REFERENCES [Roles]([ID])
);
go
create table [Customers] (
	[Username] VARCHAR(20) PRIMARY KEY,
	[Address] nvarchar(150)  null,
	[Phoneaddress] varchar(13)  null,
	FOREIGN KEY ([Username]) REFERENCES [Users]([Username])
);
go
create table [Sellers] (
	[Username] VARCHAR(20) PRIMARY KEY,
	[Address] nvarchar(150) not null,
	[Phoneaddress] varchar(13) not null,
	[Mall] bit null,
	[Active] bit not null,
	FOREIGN KEY ([Username]) REFERENCES [Users]([Username])
);
go
create table [Follow] (
	[ID] int identity(101,1) PRIMARY KEY,
	[Userfollow_ID] varchar(20) not null,
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
	[Name] nvarchar(255) not null,
	[Brand] varchar(50) null,
	[Description] nvarchar(max) null,
	[Createdday] date not null,
	[Active] bit not null,
	[Shop_ID] varchar(20) not null,
	[Category_ID] int not null,
	FOREIGN KEY ([Shop_ID]) REFERENCES [Sellers]([Username]),
	FOREIGN KEY ([Category_ID]) REFERENCES [Category_Details]([ID])
);
go
create table [Image_Product] (
	[ID] varchar(35) PRIMARY KEY,
	[Name] varchar(200) not null,
	[Product_ID] varchar(50) not null,
	FOREIGN KEY ([Product_ID]) REFERENCES [Product]([ID])
);
go
create table [Product_Variant] (
	[ID] int identity(1000001,1) PRIMARY KEY,
	[Quantity] int not null,
	[Price] float not null,
	[Pricesale] float not null,
	[Size] NVARCHAR(25) null,
	[Color] NVARCHAR(25) null,
	[Product_ID] varchar(50) not null,
	FOREIGN KEY ([Product_ID]) REFERENCES [Product]([ID])
);
go
create table [Comment] (
	[ID] int identity(10001,1) PRIMARY KEY,
	[Content] nvarchar(max) null,
	[Rating] int not null,
	[Createdat] datetime not null,
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
	[Iscustomerreply] bit not null,
	[Comment_ID] int not null,
	FOREIGN KEY ([Comment_ID]) REFERENCES [Comment]([ID])
);
go
create table [Vouchers] (
	[ID] int identity(10001,1) PRIMARY KEY,
	[Name] nvarchar(100) not null,
	[Discount] float not null,
	[Payment] nvarchar(20) not null,
	[Description] nvarchar(150) not null,
	[Startdate] date null,
	[Enddate] date null,
	[Code] VARCHAR(35) UNIQUE not null,
	[Banner] varchar(150) not null,
);
go
create table [Voucher_Details] (
	[ID] int identity(100001,1) PRIMARY KEY,
	[Customer_ID] varchar(20) not null,
	[Voucher_ID] int not null,
	[Quantity] int not null,
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
	[Createdday] date not null,
	[Total] float not null,
	[Pricediscount] float null,
	[Address] nvarchar(255) not null,
	[Voucher_ID] int null,
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
	[ID] VARCHAR(35) PRIMARY KEY,
	[Quantity] int not null,
	[Price] float not null,
	[Product_ID] int not null,
	[Order_ID] varchar(35) not null,
	FOREIGN KEY ([Product_ID]) REFERENCES [Product_Variant]([ID]),
	FOREIGN KEY ([Order_ID]) REFERENCES [Orders]([ID])
);
go
-- tạo trigger để insert, update, delete
-- insert ProductID
CREATE TRIGGER tr_InsertProduct
ON [Product]
AFTER INSERT
AS
BEGIN
    -- Declare variables
    DECLARE @CategoryID VARCHAR(10)
    DECLARE @ShopID VARCHAR(50)
    DECLARE @NextValue INT
    DECLARE @NewID VARCHAR(50)

    -- Get the Category_ID and Shop_ID from the inserted rows
    SELECT @CategoryID = Category_ID, @ShopID = Shop_ID
    FROM (SELECT DISTINCT Category_ID, Shop_ID FROM inserted) AS I

    -- Calculate the maximum existing value for the current Shop_ID
    SET @NextValue = COALESCE((SELECT MAX(CAST(SUBSTRING(ID, LEN(@CategoryID) + 2 + LEN(@ShopID) + 1, LEN(ID) - LEN(@CategoryID) - 2 - LEN(@ShopID)) AS INT))
                              FROM [Product]
                              WHERE SUBSTRING(ID, 1, LEN(@CategoryID)) = @CategoryID
                                AND SUBSTRING(ID, LEN(@CategoryID) + 2, LEN(@ShopID)) = @ShopID), 10000) + 1

    -- Iterate through the inserted rows
    DECLARE @InsertedIDs TABLE (ID VARCHAR(50), RowNumber INT IDENTITY(1, 1))
    INSERT INTO @InsertedIDs (ID)
    SELECT ID FROM inserted

    -- Update the inserted rows with the new ID
    UPDATE P
    SET ID = CONCAT(@CategoryID, '-', @ShopID, '-', @NextValue + I.RowNumber - 1)
    FROM [Product] AS P
    INNER JOIN @InsertedIDs AS I ON P.ID = I.ID
END;
go
-- insert OrderID
CREATE TRIGGER trg_UpdateOrderID
ON [Orders]
AFTER INSERT
AS
BEGIN
    DECLARE @NewID VARCHAR(35);
    DECLARE @Exists INT;

    -- Tạo chuỗi ngẫu nhiên
    SET @NewID = CONVERT(VARCHAR(35), CRYPT_GEN_RANDOM(15), 2);

    -- Kiểm tra chuỗi ngẫu nhiên đã tồn tại hay chưa
    SELECT @Exists = COUNT(*) FROM [Orders] WHERE ID = @NewID;

    -- Nếu chuỗi đã tồn tại, tiếp tục tạo chuỗi ngẫu nhiên mới cho đến khi không trùng lặp
    WHILE @Exists > 0
    BEGIN
        SET @NewID = CONVERT(VARCHAR(35), CRYPT_GEN_RANDOM(15), 2);
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
    DECLARE @NewID VARCHAR(35);
    DECLARE @Exists INT;

    -- Tạo chuỗi ngẫu nhiên
    SET @NewID = CONVERT(VARCHAR(35), CRYPT_GEN_RANDOM(15), 2);

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
    DECLARE @NewID VARCHAR(35);
    DECLARE @Exists INT;

    -- Tạo chuỗi ngẫu nhiên
    SET @NewID = CONVERT(VARCHAR(35), CRYPT_GEN_RANDOM(20), 2);

    -- Kiểm tra chuỗi ngẫu nhiên đã tồn tại hay chưa
    SELECT @Exists = COUNT(*) FROM [Image_Product] WHERE ID = @NewID;

    -- Nếu chuỗi đã tồn tại, tiếp tục tạo chuỗi ngẫu nhiên mới cho đến khi không trùng lặp
    WHILE @Exists > 0
    BEGIN
        SET @NewID = CONVERT(VARCHAR(35), CRYPT_GEN_RANDOM(20), 2);
        SELECT @Exists = COUNT(*) FROM [Image_Product] WHERE ID = @NewID;
    END;

    -- Cập nhật trường ID cho bản ghi được chèn
    UPDATE [Image_Product]
    SET ID = @NewID
    WHERE ID IN (SELECT ID FROM Inserted);
END;
go
















