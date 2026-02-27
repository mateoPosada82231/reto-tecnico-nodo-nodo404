-- ============================================================
-- Tabla: usuarios
-- ============================================================
CREATE TABLE usuarios (
    email_usuarios           VARCHAR(255)  NOT NULL PRIMARY KEY,
    pais_usuarios            VARCHAR(100)  NOT NULL,
    fecha_nacimiento_usuarios DATE         NOT NULL,
    identificacion_usuarios  VARCHAR(50)   NOT NULL UNIQUE,
    nombre_completo_usuarios VARCHAR(150)  NOT NULL,
    celular_usuarios         VARCHAR(20)   NOT NULL,
    fecha_ingreso_usuarios   DATE          NOT NULL
);

-- ============================================================
-- Tabla: extension_packages
-- ============================================================
CREATE TABLE extension_packages (
    id_extension_packages              SERIAL        PRIMARY KEY,
    edad_requerida_extension_packages  INT           NOT NULL,
    precio_extension_packages          DECIMAL(10,2) NOT NULL,
    nombre_extension_packages          VARCHAR(150)  NOT NULL,
    acerca_juego_extension_packages    TEXT          NOT NULL,
    plataformas_extension_packages     VARCHAR(255)  NOT NULL,
    idiomas_extension_packages         VARCHAR(255)  NOT NULL,
    distribuidor_extension_packages    VARCHAR(150)  NOT NULL,
    fecha_publicacion_extension_packages DATE        NOT NULL,
    categoria_extension_packages       VARCHAR(100)  NOT NULL
);

-- ============================================================
-- Tabla: usuarios_extensiones (tabla relacional / pivote)
-- ============================================================
CREATE TABLE usuarios_extensiones (
    id_usuarios_extensiones          SERIAL        PRIMARY KEY,
    id_extension_usuarios_extensiones INT          NOT NULL,
    email_usuario_usuarios_extensiones VARCHAR(255) NOT NULL,
    fecha_usuarios_extensiones        DATE         NOT NULL,
    metodo_pago_usuarios_extensiones  VARCHAR(100) NOT NULL,

    -- Llaves foráneas
    CONSTRAINT fk_extension
        FOREIGN KEY (id_extension_usuarios_extensiones)
        REFERENCES extension_packages (id_extension_packages)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_usuario
        FOREIGN KEY (email_usuario_usuarios_extensiones)
        REFERENCES usuarios (email_usuarios)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
