
CREATE DATABASE Tienda_Ropa;
GO
USE Tienda_Ropa;
GO


CREATE TABLE Roles (
    RoleId INT IDENTITY(1,1) PRIMARY KEY,
    Nombre NVARCHAR(50) NOT NULL UNIQUE
);


CREATE TABLE Clientes (
    ClienteId INT IDENTITY(1,1) PRIMARY KEY,
    Nombre NVARCHAR(150) NOT NULL,
    Email NVARCHAR(150) NULL,
    Telefono NVARCHAR(50) NULL
);


CREATE TABLE Proveedores (
    ProveedorId INT IDENTITY(1,1) PRIMARY KEY,
    Nombre NVARCHAR(150) NOT NULL,
    Contacto NVARCHAR(100) NULL,
    Telefono NVARCHAR(50) NULL
);


CREATE TABLE Categorias (
    CategoriaId INT IDENTITY(1,1) PRIMARY KEY,
    Nombre NVARCHAR(100) NOT NULL
);


CREATE TABLE Productos (
    ProductoId INT IDENTITY(1,1) PRIMARY KEY,
    CodigoBarra NVARCHAR(50) NULL UNIQUE,
    Nombre NVARCHAR(200) NOT NULL,
    CategoriaId INT NULL REFERENCES Categorias(CategoriaId),
    Talla NVARCHAR(20) NULL,
    Color NVARCHAR(50) NULL,
    ProveedorId INT NULL REFERENCES Proveedores(ProveedorId),
    PrecioVenta DECIMAL(10,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE Inventario (
    InventarioId INT IDENTITY(1,1) PRIMARY KEY,
    ProductoId INT NOT NULL REFERENCES Productos(ProductoId),
    Cantidad INT NOT NULL DEFAULT 0,
    Ubicacion NVARCHAR(100) NULL,
    CONSTRAINT UQ_Inventario_Producto UNIQUE (ProductoId)
);

CREATE TABLE Ventas (
    VentaId INT IDENTITY(1,1) PRIMARY KEY,
    ClienteId INT NULL REFERENCES Clientes(ClienteId),
    FechaVenta DATETIME2 NOT NULL DEFAULT SYSUTCDATETIME(),
    Total DECIMAL(12,2) NOT NULL DEFAULT 0.00
);

CREATE TABLE VentaItems (
    VentaItemId INT IDENTITY(1,1) PRIMARY KEY,
    VentaId INT NOT NULL REFERENCES Ventas(VentaId),
    ProductoId INT NOT NULL REFERENCES Productos(ProductoId),
    Cantidad INT NOT NULL,
    PrecioUnit DECIMAL(10,2) NOT NULL,
    Subtotal AS (Cantidad * PrecioUnit) PERSISTED
);


--Insercion de datos 


INSERT INTO Roles (Nombre) VALUES ('Administrador'),('Vendedor'),('Bodeguero');

INSERT INTO Clientes (Nombre, Email, Telefono) VALUES 
('Carlos López','carlos@mail.com','3001112222'),
('Ana Pérez','ana@mail.com','3002223333');

INSERT INTO Proveedores (Nombre, Contacto, Telefono) VALUES 
('Proveedor Textil S.A.','Luis','3104445555');

INSERT INTO Categorias (Nombre) VALUES ('Camisetas'),('Pantalones');

INSERT INTO Productos (CodigoBarra, Nombre, CategoriaId, Talla, Color, ProveedorId, PrecioVenta) VALUES
('750100000001','Camiseta Básica - M',1,'M','Blanco',1,35000),
('750100000002','Jean Slim - 32',2,'32','Azul',1,120000);

INSERT INTO Inventario (ProductoId, Cantidad, Ubicacion) VALUES
(1,100,'A1'),
(2,50,'B2');

INSERT INTO Ventas (ClienteId, Total) VALUES (1,155000);

INSERT INTO VentaItems (VentaId, ProductoId, Cantidad, PrecioUnit) VALUES
(1,1,1,35000),
(1,2,1,120000);
