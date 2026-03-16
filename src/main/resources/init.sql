-- ============================================================
-- Tabla: usuarios
-- ============================================================
CREATE TABLE usuarios (
    email                VARCHAR(255)  NOT NULL PRIMARY KEY,
    pais                 VARCHAR(100)  NOT NULL,
    fecha_nacimiento     DATE          NOT NULL,
    identificacion       VARCHAR(50)   NOT NULL UNIQUE,
    nombre_completo      VARCHAR(150)  NOT NULL,
    celular              VARCHAR(20)   NOT NULL,
    fecha_ingreso        DATE          NOT NULL
);
ALTER TABLE usuarios ADD COLUMN provider VARCHAR(50) DEFAULT 'FORM';
ALTER TABLE usuarios ADD COLUMN provider_id VARCHAR(255);
-- ============================================================
-- Tabla: extension_packages
-- ============================================================
CREATE TABLE extension_packages (
    id                   SERIAL        PRIMARY KEY,
    edad_requerida       INT           NOT NULL,
    precio               DECIMAL(10,2) NOT NULL,
    nombre               VARCHAR(150)  NOT NULL,
    acerca_juego         TEXT          NOT NULL,
    plataformas          VARCHAR(255)  NOT NULL,
    idiomas              VARCHAR(255)  NOT NULL,
    distribuidor         VARCHAR(150)  NOT NULL,
    fecha_publicacion    DATE          NOT NULL,
    categoria            VARCHAR(100)  NOT NULL
);

-- ============================================================
-- Tabla: usuarios_extensiones (tabla relacional / pivote)
-- ============================================================
CREATE TABLE usuarios_extensiones (
    id                   SERIAL        PRIMARY KEY,
    id_extension         INT           NOT NULL,
    email_usuario        VARCHAR(255)  NOT NULL,
    fecha                DATE          NOT NULL,
    metodo_pago          VARCHAR(100)  NOT NULL,

    -- Llaves foráneas
    CONSTRAINT fk_extension
        FOREIGN KEY (id_extension)
        REFERENCES extension_packages (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,

    CONSTRAINT fk_usuario
        FOREIGN KEY (email_usuario)
        REFERENCES usuarios (email)
        ON DELETE CASCADE
        ON UPDATE CASCADE
);
