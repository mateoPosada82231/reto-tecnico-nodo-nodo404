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
-- Solo campos no traducibles. Los campos localizables
-- (name, about_game, category, platforms, languages, distributor)
-- viven en extension_translations.
-- ============================================================
CREATE TABLE extension_packages (
    id                   SERIAL        PRIMARY KEY,
    edad_requerida       INT           NOT NULL,
    precio               DECIMAL(10,2) NOT NULL,
    fecha_publicacion    DATE          NOT NULL,
    image                VARCHAR(255)
);

-- ============================================================
-- Tabla: extension_translations
-- Una fila por (extension_id, language). UniqueConstraint.
-- Permite agregar idiomas nuevos sin alterar el esquema.
-- ============================================================
CREATE TABLE extension_translations (
    id                   SERIAL        PRIMARY KEY,
    extension_id         INT           NOT NULL,
    language             VARCHAR(5)    NOT NULL,
    name                 VARCHAR(150)  NOT NULL,
    about_game           TEXT          NOT NULL,
    category             VARCHAR(100),
    platforms            VARCHAR(255),
    languages            VARCHAR(255),
    distributor          VARCHAR(150),

    CONSTRAINT fk_extension_translation
        FOREIGN KEY (extension_id)
        REFERENCES extension_packages (id)
        ON DELETE CASCADE
        ON UPDATE CASCADE,
    CONSTRAINT uk_extension_language UNIQUE (extension_id, language)
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
