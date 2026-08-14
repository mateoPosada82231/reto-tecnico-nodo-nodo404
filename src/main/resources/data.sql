-- ============================================================
-- Inserción de datos: extensions (Hibernate naming)
-- Solo campos no traducibles. Los textos localizables van en
-- extension_translations (ver bloque más abajo).
-- Las imágenes están alojadas en Cloudinary.
-- Las inserciones son idempotentes: solo se crean filas que no existen.
-- ============================================================

INSERT INTO extensions (required_age, price, publication_date, image, is_public)
SELECT 13, 159900.00, '2022-07-28', 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/rancho_joiffx.jpg', TRUE
WHERE NOT EXISTS (SELECT 1 FROM extensions WHERE image = 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/rancho_joiffx.jpg');

INSERT INTO extensions (required_age, price, publication_date, image, is_public)
SELECT 13, 159900.00, '2019-11-15', 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822907/universidad_niieak.jpg', TRUE
WHERE NOT EXISTS (SELECT 1 FROM extensions WHERE image = 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822907/universidad_niieak.jpg');

INSERT INTO extensions (required_age, price, publication_date, image, is_public)
SELECT 13, 159900.00, '2016-11-01', 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822907/urbanitas_dxfr2h.jpg', TRUE
WHERE NOT EXISTS (SELECT 1 FROM extensions WHERE image = 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822907/urbanitas_dxfr2h.jpg');

INSERT INTO extensions (required_age, price, publication_date, image, is_public)
SELECT 13, 159900.00, '2018-11-13', 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/estaciones_vhzuar.jpg', TRUE
WHERE NOT EXISTS (SELECT 1 FROM extensions WHERE image = 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/estaciones_vhzuar.jpg');

INSERT INTO extensions (required_age, price, publication_date, image, is_public)
SELECT 13, 159900.00, '2015-04-01', 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822905/trabajo_tury19.jpg', TRUE
WHERE NOT EXISTS (SELECT 1 FROM extensions WHERE image = 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822905/trabajo_tury19.jpg');

INSERT INTO extensions (required_age, price, publication_date, image, is_public)
SELECT 13, 159900.00, '2017-11-10', 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822910/mascotas_yxggma.jpg', FALSE
WHERE NOT EXISTS (SELECT 1 FROM extensions WHERE image = 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822910/mascotas_yxggma.jpg');

INSERT INTO extensions (required_age, price, publication_date, image, is_public)
SELECT 13, 159900.00, '2019-06-21', 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/isla_mzfikd.jpg', FALSE
WHERE NOT EXISTS (SELECT 1 FROM extensions WHERE image = 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/isla_mzfikd.jpg');

INSERT INTO extensions (required_age, price, publication_date, image, is_public)
SELECT 13, 159900.00, '2021-11-23', 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/nieve_vovttv.jpg', FALSE
WHERE NOT EXISTS (SELECT 1 FROM extensions WHERE image = 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/nieve_vovttv.jpg');

INSERT INTO extensions (required_age, price, publication_date, image, is_public)
SELECT 13, 159900.00, '2023-09-14', 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822908/alquiler_ltzwy5.jpg', TRUE
WHERE NOT EXISTS (SELECT 1 FROM extensions WHERE image = 'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822908/alquiler_ltzwy5.jpg');

-- ============================================================
-- Inserción de datos: extension_translations (es | en)
-- ============================================================
-- Devuelve el id de la extensión dado su orden de inserción (1-based)
-- y solo insertará traducciones faltantes para cada (extension_id, language).

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'es', 'Los Sims 4: Vida en el Rancho', 'Crea tu propia vida rústica. Cría y entrena caballos, entabla amistad con mini-cabras y mini-ovejas, y elabora tu propio néctar casero para vender o compartir.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Español, Inglés', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%rancho_joiffx%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'es');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'en', 'The Sims 4: Horse Ranch', 'Build your own rustic life. Raise and train horses, befriend mini-goats and mini-sheep, and craft your own homemade nectar to sell or share.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Spanish, English', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%rancho_joiffx%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'en');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'es', 'Los Sims 4: Días de Universidad', 'Inscríbete en la universidad, explora experiencias académicas y asiste a actividades extracurriculares.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Español, Inglés', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%universidad_niieak%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'es');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'en', 'The Sims 4: Discover University', 'Enroll in university, explore academic experiences and attend extracurricular activities.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Spanish, English', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%universidad_niieak%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'en');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'es', 'Los Sims 4: Urbanitas', 'Lleva a tus Sims a la gran ciudad y experimente todo lo que ofrece, desde el bullicio exterior hasta situaciones exclusivas de los apartamentos.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Español, Inglés', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%urbanitas_dxfr2h%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'es');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'en', 'The Sims 4: City Living', 'Take your Sims to the big city and experience everything it offers, from the hustle and bustle outside to exclusive apartment situations.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Spanish, English', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%urbanitas_dxfr2h%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'en');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'es', 'Los Sims 4: Y Las Cuatro Estaciones', 'Añade el clima a la vida de tus Sims para contar nuevas historias, disfrutar de actividades estacionales y celebrar festividades.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Español, Inglés', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%estaciones_vhzuar%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'es');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'en', 'The Sims 4: Seasons', 'Add weather to your Sims lives to tell new stories, enjoy seasonal activities and celebrate festivities.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Spanish, English', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%estaciones_vhzuar%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'en');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'es', 'Los Sims 4: ¡A Trabajar!', 'Controla activamente a tus Sims en sus lugares de trabajo y determina si se dirigen hacia un gran ascenso o se convierten en una amenaza.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Español, Inglés', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%trabajo_tury19%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'es');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'en', 'The Sims 4: Get to Work', 'Actively control your Sims in their workplaces and determine whether they head toward a big promotion or become a menace.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Spanish, English', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%trabajo_tury19%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'en');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'es', 'Los Sims 4: Perros y Gatos', 'Crea una variedad de perros y gatos, añádelos a los hogares de tus Sims para cambiar sus vidas para siempre y cuida de las mascotas del vecindario.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Español, Inglés', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%mascotas_yxggma%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'es');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'en', 'The Sims 4: Cats & Dogs', 'Create a variety of cats and dogs, add them to your Sims homes to change their lives forever and care for neighborhood pets.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Spanish, English', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%mascotas_yxggma%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'en');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'es', 'Los Sims 4: Vida Isleña', 'Escapa de la rutina y disfruta de un estilo de vida relajado rodeado de playas en Sulani, donde el sol brilla con fuerza y las noches son refrescantes.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Español, Inglés', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%isla_mzfikd%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'es');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'en', 'The Sims 4: Island Living', 'Escape the routine and enjoy a relaxed lifestyle surrounded by beaches in Sulani, where the sun shines brightly and the nights are refreshing.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Spanish, English', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%isla_mzfikd%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'en');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'es', 'Los Sims 4: Escapada en la Nieve', 'Equípate para los deportes de invierno, relájate en aguas termales y diseña tu hogar de estilo japonés.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Español, Inglés', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%nieve_vovttv%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'es');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'en', 'The Sims 4: Snowy Escape', 'Gear up for winter sports, relax in hot springs and design your Japanese-style home.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Spanish, English', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%nieve_vovttv%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'en');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'es', 'Los Sims 4: Se Alquila', 'Experimenta la vida en vecindarios multifamiliares como inquilino o propietario de propiedades residenciales en alquiler.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Español, Inglés', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%alquiler_ltzwy5%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'es');

INSERT INTO extension_translations (extension_id, language, name, about_game, category, platforms, languages, distributor)
SELECT e.id, 'en', 'The Sims 4: For Rent', 'Experience life in multi-family neighborhoods as a tenant or owner of residential rental properties.', 'Expansion Pack', 'PC, Mac, PlayStation, Xbox', 'Spanish, English', 'Electronic Arts'
FROM extensions e
WHERE e.image LIKE '%alquiler_ltzwy5%'
  AND NOT EXISTS (SELECT 1 FROM extension_translations et WHERE et.extension_id = e.id AND et.language = 'en');


-- ============================================================
-- Inserción de datos: site_content (CMS textos de UI)
-- Canon de claves: las que consume el frontend (src/locales/*.json + useContent).
-- Idiomas soportados: es | en
-- Inserciones idempotentes: solo se crean filas que no existen.
-- ============================================================

-- landing.hero (5 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('landing.hero', 'cta_text', 'Comprar ahora', 'text', 'es'),
    ('landing.hero', 'error_prefix', 'Error al cargar extensiones: ', 'text', 'es'),
    ('landing.hero', 'prev_aria', 'Paquete anterior', 'text', 'es'),
    ('landing.hero', 'next_aria', 'Paquete siguiente', 'text', 'es'),
    ('landing.hero', 'slide_aria_prefix', 'Ir al paquete', 'text', 'es'),
    ('landing.hero', 'cta_text', 'Buy Now', 'text', 'en'),
    ('landing.hero', 'error_prefix', 'Error loading expansion packs: ', 'text', 'en'),
    ('landing.hero', 'prev_aria', 'Previous pack', 'text', 'en'),
    ('landing.hero', 'next_aria', 'Next pack', 'text', 'en'),
    ('landing.hero', 'slide_aria_prefix', 'Go to pack', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- landing.grid (3 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('landing.grid', 'title', 'Paquetes de Expansión', 'text', 'es'),
    ('landing.grid', 'error_prefix', 'Error al cargar extensiones: ', 'text', 'es'),
    ('landing.grid', 'cta_text', 'Ver más', 'text', 'es'),
    ('landing.grid', 'beta_badge_label', 'Beta', 'text', 'es'),
    ('landing.grid', 'title', 'Expansion Packs', 'text', 'en'),
    ('landing.grid', 'error_prefix', 'Error loading expansion packs: ', 'text', 'en'),
    ('landing.grid', 'cta_text', 'See More', 'text', 'en'),
    ('landing.grid', 'beta_badge_label', 'Beta', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- landing.welcome (4 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('landing.welcome', 'title', '¡Bienvenido a Los Sims 4!', 'text', 'es'),
    ('landing.welcome', 'subtitle', 'Explora todos los paquetes de expansión y descubre nuevas aventuras para tus Sims.', 'text', 'es'),
    ('landing.welcome', 'cta_text', 'Explorar', 'text', 'es'),
    ('landing.welcome', 'close_aria', 'Cerrar', 'text', 'es'),
    ('landing.welcome', 'title', 'Welcome to The Sims 4!', 'text', 'en'),
    ('landing.welcome', 'subtitle', 'Explore all expansion packs and discover new adventures for your Sims.', 'text', 'en'),
    ('landing.welcome', 'cta_text', 'Explore', 'text', 'en'),
    ('landing.welcome', 'close_aria', 'Close', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- landing.detail (30 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('landing.detail', 'loading_text', 'Cargando expansión...', 'text', 'es'),
    ('landing.detail', 'not_found', 'Expansión no encontrada.', 'text', 'es'),
    ('landing.detail', 'back_text', 'Volver', 'text', 'es'),
    ('landing.detail', 'category_label', 'Categoría', 'text', 'es'),
    ('landing.detail', 'price_label', 'Precio', 'text', 'es'),
    ('landing.detail', 'about_label', 'Acerca del juego', 'text', 'es'),
    ('landing.detail', 'platforms_label', 'Plataformas', 'text', 'es'),
    ('landing.detail', 'languages_label', 'Idiomas', 'text', 'es'),
    ('landing.detail', 'distributor_label', 'Distribuidor', 'text', 'es'),
    ('landing.detail', 'publication_date_label', 'Fecha de publicación', 'text', 'es'),
    ('landing.detail', 'required_age_label', 'Edad requerida', 'text', 'es'),
    ('landing.detail', 'years_text', 'años', 'text', 'es'),
    ('landing.detail', 'buy_button', 'Comprar', 'text', 'es'),
    ('landing.detail', 'login_required', 'Debes iniciar sesión para comprar.', 'text', 'es'),
    ('landing.detail', 'login_link', 'Ir a login', 'text', 'es'),
    ('landing.detail', 'success_message', '¡Compra realizada con éxito!', 'text', 'es'),
    ('landing.detail', 'payment_method_label', 'Método de pago', 'text', 'es'),
    ('landing.detail', 'language_label', 'Idioma', 'text', 'es'),
    ('landing.detail', 'platform_label', 'Plataforma', 'text', 'es'),
    ('landing.detail', 'confirm_button', 'Confirmar compra', 'text', 'es'),
    ('landing.detail', 'cancel_button', 'Cancelar', 'text', 'es'),
    ('landing.detail', 'processing_text', 'Comprando...', 'text', 'es'),
    ('landing.detail', 'add_to_cart_button', 'Agregar al carrito', 'text', 'es'),
    ('landing.detail', 'add_to_cart_confirm', 'Agregar', 'text', 'es'),
    ('landing.detail', 'adding_to_cart_text', 'Agregando...', 'text', 'es'),
    ('landing.detail', 'add_to_cart_success', 'Agregado al carrito correctamente', 'text', 'es'),
    ('landing.detail', 'payment_method_card', 'Tarjeta', 'text', 'es'),
    ('landing.detail', 'payment_method_paypal', 'PayPal', 'text', 'es'),
    ('landing.detail', 'language_es', 'Español', 'text', 'es'),
    ('landing.detail', 'language_en', 'Inglés', 'text', 'es'),
    ('landing.detail', 'beta_badge_label', 'Beta', 'text', 'es'),
    ('landing.detail', 'beta_only_notice', 'Esta extensión es exclusiva para beta testers. Conviértete en beta para adquirirla.', 'text', 'es'),
    ('landing.detail', 'beta_only_cta', 'Quiero ser beta tester', 'text', 'es'),
    ('landing.detail', 'loading_text', 'Loading expansion...', 'text', 'en'),
    ('landing.detail', 'not_found', 'Expansion pack not found.', 'text', 'en'),
    ('landing.detail', 'back_text', 'Back', 'text', 'en'),
    ('landing.detail', 'category_label', 'Category', 'text', 'en'),
    ('landing.detail', 'price_label', 'Price', 'text', 'en'),
    ('landing.detail', 'about_label', 'About the game', 'text', 'en'),
    ('landing.detail', 'platforms_label', 'Platforms', 'text', 'en'),
    ('landing.detail', 'languages_label', 'Languages', 'text', 'en'),
    ('landing.detail', 'distributor_label', 'Distributor', 'text', 'en'),
    ('landing.detail', 'publication_date_label', 'Publication date', 'text', 'en'),
    ('landing.detail', 'required_age_label', 'Required age', 'text', 'en'),
    ('landing.detail', 'years_text', 'years', 'text', 'en'),
    ('landing.detail', 'buy_button', 'Buy', 'text', 'en'),
    ('landing.detail', 'login_required', 'You must log in to buy.', 'text', 'en'),
    ('landing.detail', 'login_link', 'Go to login', 'text', 'en'),
    ('landing.detail', 'success_message', 'Purchase successful!', 'text', 'en'),
    ('landing.detail', 'payment_method_label', 'Payment method', 'text', 'en'),
    ('landing.detail', 'language_label', 'Language', 'text', 'en'),
    ('landing.detail', 'platform_label', 'Platform', 'text', 'en'),
    ('landing.detail', 'confirm_button', 'Confirm purchase', 'text', 'en'),
    ('landing.detail', 'cancel_button', 'Cancel', 'text', 'en'),
    ('landing.detail', 'processing_text', 'Processing...', 'text', 'en'),
    ('landing.detail', 'add_to_cart_button', 'Add to cart', 'text', 'en'),
    ('landing.detail', 'add_to_cart_confirm', 'Add', 'text', 'en'),
    ('landing.detail', 'adding_to_cart_text', 'Adding...', 'text', 'en'),
    ('landing.detail', 'add_to_cart_success', 'Added to cart successfully', 'text', 'en'),
    ('landing.detail', 'payment_method_card', 'Card', 'text', 'en'),
    ('landing.detail', 'payment_method_paypal', 'PayPal', 'text', 'en'),
    ('landing.detail', 'language_es', 'Spanish', 'text', 'en'),
    ('landing.detail', 'language_en', 'English', 'text', 'en'),
    ('landing.detail', 'beta_badge_label', 'Beta', 'text', 'en'),
    ('landing.detail', 'beta_only_notice', 'This extension is exclusive to beta testers. Become one to purchase it.', 'text', 'en'),
    ('landing.detail', 'beta_only_cta', 'Become a beta tester', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- auth.login (8 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('auth.login', 'title', 'Iniciar Sesión', 'text', 'es'),
    ('auth.login', 'subtitle', 'Accede con tu cuenta para gestionar tus compras.', 'text', 'es'),
    ('auth.login', 'email_label', 'Correo electrónico', 'text', 'es'),
    ('auth.login', 'password_label', 'Contraseña', 'text', 'es'),
    ('auth.login', 'submit_text', 'Iniciar Sesión', 'text', 'es'),
    ('auth.login', 'loading_text', 'Iniciando sesión...', 'text', 'es'),
    ('auth.login', 'success_message', 'Inicio de sesión exitoso. Redirigiendo...', 'text', 'es'),
    ('auth.login', 'forgot_password_link', '¿Olvidaste tu contraseña?', 'text', 'es'),
    ('auth.login', 'title', 'Log In', 'text', 'en'),
    ('auth.login', 'subtitle', 'Log in with your account to manage your purchases.', 'text', 'en'),
    ('auth.login', 'email_label', 'Email Address', 'text', 'en'),
    ('auth.login', 'password_label', 'Password', 'text', 'en'),
    ('auth.login', 'submit_text', 'Log In', 'text', 'en'),
    ('auth.login', 'loading_text', 'Logging in...', 'text', 'en'),
    ('auth.login', 'success_message', 'Login successful. Redirecting...', 'text', 'en'),
    ('auth.login', 'forgot_password_link', 'Forgot your password?', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- auth.register (16 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('auth.register', 'title', 'Crear Cuenta', 'text', 'es'),
    ('auth.register', 'subtitle', 'Regístrate para acceder a todas las funcionalidades.', 'text', 'es'),
    ('auth.register', 'fullname_label', 'Nombre completo', 'text', 'es'),
    ('auth.register', 'fullname_placeholder', 'Juan Pérez', 'text', 'es'),
    ('auth.register', 'email_label', 'Correo electrónico', 'text', 'es'),
    ('auth.register', 'country_label', 'País', 'text', 'es'),
    ('auth.register', 'birthdate_label', 'Fecha de nacimiento', 'text', 'es'),
    ('auth.register', 'id_label', 'Número de identificación', 'text', 'es'),
    ('auth.register', 'phone_label', 'Número de celular', 'text', 'es'),
    ('auth.register', 'password_label', 'Contraseña', 'text', 'es'),
    ('auth.register', 'password_placeholder', 'Mayúscula, número y carácter especial', 'text', 'es'),
    ('auth.register', 'confirm_password_label', 'Confirmar contraseña', 'text', 'es'),
    ('auth.register', 'confirm_password_placeholder', 'Repite tu contraseña', 'text', 'es'),
    ('auth.register', 'submit_text', 'Crear Cuenta', 'text', 'es'),
    ('auth.register', 'loading_text', 'Creando cuenta...', 'text', 'es'),
    ('auth.register', 'success_message', 'Cuenta creada con éxito. Ya puedes iniciar sesión.', 'text', 'es'),
    ('auth.register', 'title', 'Create Account', 'text', 'en'),
    ('auth.register', 'subtitle', 'Sign up to access all features.', 'text', 'en'),
    ('auth.register', 'fullname_label', 'Full Name', 'text', 'en'),
    ('auth.register', 'fullname_placeholder', 'John Doe', 'text', 'en'),
    ('auth.register', 'email_label', 'Email Address', 'text', 'en'),
    ('auth.register', 'country_label', 'Country', 'text', 'en'),
    ('auth.register', 'birthdate_label', 'Date of Birth', 'text', 'en'),
    ('auth.register', 'id_label', 'ID Number', 'text', 'en'),
    ('auth.register', 'phone_label', 'Phone Number', 'text', 'en'),
    ('auth.register', 'password_label', 'Password', 'text', 'en'),
    ('auth.register', 'password_placeholder', 'Uppercase, number, and special character', 'text', 'en'),
    ('auth.register', 'confirm_password_label', 'Confirm Password', 'text', 'en'),
    ('auth.register', 'confirm_password_placeholder', 'Repeat your password', 'text', 'en'),
    ('auth.register', 'submit_text', 'Create Account', 'text', 'en'),
    ('auth.register', 'loading_text', 'Creating account...', 'text', 'en'),
    ('auth.register', 'success_message', 'Account created successfully. You can now log in.', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- auth.social (2 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('auth.social', 'divider_login', 'O inicia sesión con', 'text', 'es'),
    ('auth.social', 'divider_register', 'O regístrate con', 'text', 'es'),
    ('auth.social', 'divider_login', 'Or log in with', 'text', 'en'),
    ('auth.social', 'divider_register', 'Or sign up with', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- auth.oauth (1 item × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('auth.oauth', 'loading_text', 'Iniciando sesión...', 'text', 'es'),
    ('auth.oauth', 'loading_text', 'Logging in...', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- header (11 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('header', 'profile_warning_prefix', 'Completa tu información', 'text', 'es'),
    ('header', 'profile_warning_link', 'aquí', 'text', 'es'),
    ('header', 'nav_home', 'Inicio', 'text', 'es'),
    ('header', 'nav_register', 'Registro', 'text', 'es'),
    ('header', 'nav_login', 'Login', 'text', 'es'),
    ('header', 'beta_cta', 'Ser Beta Tester', 'text', 'es'),
    ('header', 'logout_aria', 'Cerrar sesión', 'text', 'es'),
    ('header', 'menu_aria', 'Menú', 'text', 'es'),
    ('header', 'profile_link_aria', 'Ver perfil', 'text', 'es'),
    ('header', 'beta_badge_label', 'Beta', 'text', 'es'),
    ('header', 'mobile_profile_link', 'Perfil', 'text', 'es'),
    ('header', 'profile_warning_prefix', 'Complete your profile information', 'text', 'en'),
    ('header', 'profile_warning_link', 'here', 'text', 'en'),
    ('header', 'nav_home', 'Home', 'text', 'en'),
    ('header', 'nav_register', 'Register', 'text', 'en'),
    ('header', 'nav_login', 'Log In', 'text', 'en'),
    ('header', 'beta_cta', 'Become a Beta Tester', 'text', 'en'),
    ('header', 'logout_aria', 'Log Out', 'text', 'en'),
    ('header', 'menu_aria', 'Menu', 'text', 'en'),
    ('header', 'profile_link_aria', 'View profile', 'text', 'en'),
    ('header', 'beta_badge_label', 'Beta', 'text', 'en'),
    ('header', 'mobile_profile_link', 'Profile', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- beta_modal (8 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('beta_modal', 'close_aria', 'Cerrar', 'text', 'es'),
    ('beta_modal', 'already_title', 'Ya eres Beta Tester', 'text', 'es'),
    ('beta_modal', 'already_description', 'Ahora tienes acceso anticipado a nuevas extensiones y funciones exclusivas.', 'text', 'es'),
    ('beta_modal', 'already_cta', 'Entendido', 'text', 'es'),
    ('beta_modal', 'confirm_title', 'Ser Beta Tester', 'text', 'es'),
    ('beta_modal', 'confirm_description', '¿Seguro que quieres unirte al programa beta? Tendrás acceso anticipado a nuevas extensiones antes que nadie.', 'text', 'es'),
    ('beta_modal', 'cancel_text', 'Cancelar', 'text', 'es'),
    ('beta_modal', 'processing_text', 'Procesando', 'text', 'es'),
    ('beta_modal', 'confirm_cta', 'Sí, quiero ser beta', 'text', 'es'),
    ('beta_modal', 'close_aria', 'Close', 'text', 'en'),
    ('beta_modal', 'already_title', 'You are already a Beta Tester', 'text', 'en'),
    ('beta_modal', 'already_description', 'You now have early access to new expansion packs and exclusive features.', 'text', 'en'),
    ('beta_modal', 'already_cta', 'Got it', 'text', 'en'),
    ('beta_modal', 'confirm_title', 'Become a Beta Tester', 'text', 'en'),
    ('beta_modal', 'confirm_description', 'Are you sure you want to join the beta program? You will get early access to new expansion packs before anyone else.', 'text', 'en'),
    ('beta_modal', 'cancel_text', 'Cancel', 'text', 'en'),
    ('beta_modal', 'processing_text', 'Processing', 'text', 'en'),
    ('beta_modal', 'confirm_cta', 'Yes, I want to join', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- footer (1 item × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('footer', 'copyright', 'Todos los derechos reservados.', 'text', 'es'),
    ('footer', 'copyright', 'All rights reserved.', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- common (3 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('common', 'loading_aria', 'Cargando', 'text', 'es'),
    ('common', 'close_aria', 'Cerrar', 'text', 'es'),
    ('common', 'loading_router', 'Cargando…', 'text', 'es'),
    ('common', 'loading_aria', 'Loading', 'text', 'en'),
    ('common', 'close_aria', 'Close', 'text', 'en'),
    ('common', 'loading_router', 'Loading…', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- profile.page (19 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('profile.page', 'name_fallback', 'Sin nombre', 'text', 'es'),
    ('profile.page', 'beta_badge', 'Beta tester', 'text', 'es'),
    ('profile.page', 'fullname_label', 'Nombre completo', 'text', 'es'),
    ('profile.page', 'country_label', 'País', 'text', 'es'),
    ('profile.page', 'identification_label', 'Número de identificación', 'text', 'es'),
    ('profile.page', 'phone_label', 'Número de celular', 'text', 'es'),
    ('profile.page', 'birthdate_label', 'Fecha de nacimiento', 'text', 'es'),
    ('profile.page', 'edit_button', 'Editar perfil', 'text', 'es'),
    ('profile.page', 'cancel_button', 'Cancelar', 'text', 'es'),
    ('profile.page', 'save_button', 'Guardar cambios', 'text', 'es'),
    ('profile.page', 'success_message', 'Perfil actualizado con éxito', 'text', 'es'),
    ('profile.page', 'error_message', 'No se pudo actualizar el perfil', 'text', 'es'),
    ('profile.page', 'security_title', 'Seguridad', 'text', 'es'),
    ('profile.page', 'security_subtitle', 'Actualiza tu contraseña periódicamente para mantener tu cuenta protegida.', 'text', 'es'),
    ('profile.page', 'change_password_button', 'Cambiar contraseña', 'text', 'es'),
    ('profile.page', 'purchases_title', 'Mis compras', 'text', 'es'),
    ('profile.page', 'purchases_loading', 'Cargando compras...', 'text', 'es'),
    ('profile.page', 'purchases_empty', 'Aún no has comprado ninguna expansión.', 'text', 'es'),
    ('profile.page', 'purchases_item_meta', 'Comprado el {{date}} · {{paymentMethod}}', 'text', 'es'),
    ('profile.page', 'beta_extensions_title', 'Mis extensiones beta', 'text', 'es'),
    ('profile.page', 'beta_extensions_loading', 'Cargando extensiones beta...', 'text', 'es'),
    ('profile.page', 'beta_extensions_empty', 'Aún no tienes extensiones beta.', 'text', 'es'),
    ('profile.page', 'beta_extensions_item_meta', 'Comprado el {{date}}', 'text', 'es'),
    ('profile.page', 'name_fallback', 'No name', 'text', 'en'),
    ('profile.page', 'beta_badge', 'Beta tester', 'text', 'en'),
    ('profile.page', 'fullname_label', 'Full Name', 'text', 'en'),
    ('profile.page', 'country_label', 'Country', 'text', 'en'),
    ('profile.page', 'identification_label', 'ID Number', 'text', 'en'),
    ('profile.page', 'phone_label', 'Phone Number', 'text', 'en'),
    ('profile.page', 'birthdate_label', 'Date of Birth', 'text', 'en'),
    ('profile.page', 'edit_button', 'Edit profile', 'text', 'en'),
    ('profile.page', 'cancel_button', 'Cancel', 'text', 'en'),
    ('profile.page', 'save_button', 'Save changes', 'text', 'en'),
    ('profile.page', 'success_message', 'Profile updated successfully', 'text', 'en'),
    ('profile.page', 'error_message', 'Failed to update profile', 'text', 'en'),
    ('profile.page', 'security_title', 'Security', 'text', 'en'),
    ('profile.page', 'security_subtitle', 'Update your password periodically to keep your account protected.', 'text', 'en'),
    ('profile.page', 'change_password_button', 'Change password', 'text', 'en'),
    ('profile.page', 'purchases_title', 'My purchases', 'text', 'en'),
    ('profile.page', 'purchases_loading', 'Loading purchases...', 'text', 'en'),
    ('profile.page', 'purchases_empty', 'You have not purchased any expansion yet.', 'text', 'en'),
    ('profile.page', 'purchases_item_meta', 'Purchased on {{date}} · {{paymentMethod}}', 'text', 'en'),
    ('profile.page', 'beta_extensions_title', 'My beta extensions', 'text', 'en'),
    ('profile.page', 'beta_extensions_loading', 'Loading beta extensions...', 'text', 'en'),
    ('profile.page', 'beta_extensions_empty', 'You have no beta extensions yet.', 'text', 'en'),
    ('profile.page', 'beta_extensions_item_meta', 'Purchased on {{date}}', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- theme.toggle (2 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('theme.toggle', 'light_aria', 'Cambiar a tema claro', 'text', 'es'),
    ('theme.toggle', 'dark_aria', 'Cambiar a tema oscuro', 'text', 'es'),
    ('theme.toggle', 'light_aria', 'Switch to light theme', 'text', 'en'),
    ('theme.toggle', 'dark_aria', 'Switch to dark theme', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- validation.login (3 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('validation.login', 'email_required', 'El correo es obligatorio', 'text', 'es'),
    ('validation.login', 'email_invalid', 'Correo inválido', 'text', 'es'),
    ('validation.login', 'password_required', 'Ingrese una contraseña', 'text', 'es'),
    ('validation.login', 'email_required', 'Email is required', 'text', 'en'),
    ('validation.login', 'email_invalid', 'Invalid email', 'text', 'en'),
    ('validation.login', 'password_required', 'Please enter a password', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- validation.register (15 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('validation.register', 'name_required', 'El nombre es obligatorio', 'text', 'es'),
    ('validation.register', 'email_required', 'El correo es obligatorio', 'text', 'es'),
    ('validation.register', 'email_invalid', 'Correo inválido', 'text', 'es'),
    ('validation.register', 'email_already_registered', 'Este correo ya está registrado', 'text', 'es'),
    ('validation.register', 'country_required', 'Seleccione un país', 'text', 'es'),
    ('validation.register', 'birthdate_required', 'Seleccione una fecha', 'text', 'es'),
    ('validation.register', 'id_required', 'Ingrese su identificación', 'text', 'es'),
    ('validation.register', 'phone_required', 'Ingrese su celular', 'text', 'es'),
    ('validation.register', 'password_required', 'Ingrese una contraseña', 'text', 'es'),
    ('validation.register', 'password_min_length', 'La contraseña debe tener mínimo 8 caracteres', 'text', 'es'),
    ('validation.register', 'password_uppercase', 'Debe contener al menos una mayúscula', 'text', 'es'),
    ('validation.register', 'password_number', 'Debe contener al menos un número', 'text', 'es'),
    ('validation.register', 'password_special', 'Debe contener al menos un carácter especial', 'text', 'es'),
    ('validation.register', 'confirm_required', 'Confirme su contraseña', 'text', 'es'),
    ('validation.register', 'confirm_match', 'Las contraseñas no coinciden', 'text', 'es'),
    ('validation.register', 'name_required', 'Name is required', 'text', 'en'),
    ('validation.register', 'email_required', 'Email is required', 'text', 'en'),
    ('validation.register', 'email_invalid', 'Invalid email', 'text', 'en'),
    ('validation.register', 'email_already_registered', 'This email is already registered', 'text', 'en'),
    ('validation.register', 'country_required', 'Please select a country', 'text', 'en'),
    ('validation.register', 'birthdate_required', 'Please select a date', 'text', 'en'),
    ('validation.register', 'id_required', 'Enter your ID', 'text', 'en'),
    ('validation.register', 'phone_required', 'Enter your phone number', 'text', 'en'),
    ('validation.register', 'password_required', 'Enter a password', 'text', 'en'),
    ('validation.register', 'password_min_length', 'Password must be at least 8 characters', 'text', 'en'),
    ('validation.register', 'password_uppercase', 'Must contain at least one uppercase letter', 'text', 'en'),
    ('validation.register', 'password_number', 'Must contain at least one number', 'text', 'en'),
    ('validation.register', 'password_special', 'Must contain at least one special character', 'text', 'en'),
    ('validation.register', 'confirm_required', 'Confirm your password', 'text', 'en'),
    ('validation.register', 'confirm_match', 'Passwords do not match', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- validation.profile (1 item × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('validation.profile', 'name_required', 'El nombre es obligatorio', 'text', 'es'),
    ('validation.profile', 'name_required', 'Name is required', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- validation.password (8 items × 2 idiomas) — usado por useChangePassword
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('validation.password', 'current_required', 'Ingrese su contraseña actual', 'text', 'es'),
    ('validation.password', 'password_required', 'Ingrese una contraseña', 'text', 'es'),
    ('validation.password', 'password_min_length', 'La contraseña debe tener mínimo 8 caracteres', 'text', 'es'),
    ('validation.password', 'password_uppercase', 'Debe contener al menos una mayúscula', 'text', 'es'),
    ('validation.password', 'password_number', 'Debe contener al menos un número', 'text', 'es'),
    ('validation.password', 'password_special', 'Debe contener al menos un carácter especial', 'text', 'es'),
    ('validation.password', 'confirm_required', 'Confirme su contraseña', 'text', 'es'),
    ('validation.password', 'confirm_match', 'Las contraseñas no coinciden', 'text', 'es'),
    ('validation.password', 'current_required', 'Enter your current password', 'text', 'en'),
    ('validation.password', 'password_required', 'Please enter a password', 'text', 'en'),
    ('validation.password', 'password_min_length', 'Password must be at least 8 characters', 'text', 'en'),
    ('validation.password', 'password_uppercase', 'Must contain at least one uppercase letter', 'text', 'en'),
    ('validation.password', 'password_number', 'Must contain at least one number', 'text', 'en'),
    ('validation.password', 'password_special', 'Must contain at least one special character', 'text', 'en'),
    ('validation.password', 'confirm_required', 'Confirm your password', 'text', 'en'),
    ('validation.password', 'confirm_match', 'Passwords do not match', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- profile.password (10 items × 2 idiomas) — modal Cambiar contraseña
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('profile.password', 'title', 'Cambiar contraseña', 'text', 'es'),
    ('profile.password', 'current_label', 'Contraseña actual', 'text', 'es'),
    ('profile.password', 'new_label', 'Nueva contraseña', 'text', 'es'),
    ('profile.password', 'new_placeholder', 'Mayúscula, número y carácter especial', 'text', 'es'),
    ('profile.password', 'confirm_label', 'Confirmar nueva contraseña', 'text', 'es'),
    ('profile.password', 'submit_text', 'Cambiar contraseña', 'text', 'es'),
    ('profile.password', 'cancel_text', 'Cancelar', 'text', 'es'),
    ('profile.password', 'success_text', 'Contraseña cambiada correctamente.', 'text', 'es'),
    ('profile.password', 'success_cta', 'Entendido', 'text', 'es'),
    ('profile.password', 'error_text', 'Error al cambiar la contraseña.', 'text', 'es'),
    ('profile.password', 'title', 'Change password', 'text', 'en'),
    ('profile.password', 'current_label', 'Current password', 'text', 'en'),
    ('profile.password', 'new_label', 'New password', 'text', 'en'),
    ('profile.password', 'new_placeholder', 'Uppercase, number, and special character', 'text', 'en'),
    ('profile.password', 'confirm_label', 'Confirm new password', 'text', 'en'),
    ('profile.password', 'submit_text', 'Change password', 'text', 'en'),
    ('profile.password', 'cancel_text', 'Cancel', 'text', 'en'),
    ('profile.password', 'success_text', 'Password changed successfully.', 'text', 'en'),
    ('profile.password', 'success_cta', 'Got it', 'text', 'en'),
    ('profile.password', 'error_text', 'Error changing password.', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- errors.common (14 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('errors.common', 'duplicate_email', 'Este correo ya está registrado', 'text', 'es'),
    ('errors.common', 'invalid_credentials', 'Correo o contraseña incorrectos', 'text', 'es'),
    ('errors.common', 'session_expired', 'Sesión expirada, inicia sesión de nuevo', 'text', 'es'),
    ('errors.common', 'unauthorized', 'No tienes permisos para esta acción', 'text', 'es'),
    ('errors.common', 'required_field', 'Completa todos los campos obligatorios', 'text', 'es'),
    ('errors.common', 'validation_failed', 'Revisa los datos ingresados', 'text', 'es'),
    ('errors.common', 'server_error', 'Error del servidor, intenta más tarde', 'text', 'es'),
    ('errors.common', 'service_unavailable', 'Servicio no disponible, intenta más tarde', 'text', 'es'),
    ('errors.common', 'bad_request', 'Datos inválidos', 'text', 'es'),
    ('errors.common', 'not_found', 'Recurso no encontrado', 'text', 'es'),
    ('errors.common', 'network_error', 'Sin conexión al servidor', 'text', 'es'),
    ('errors.common', 'unexpected_error', 'Error inesperado, intenta de nuevo', 'text', 'es'),
    ('errors.common', 'already_purchased', 'Ya has comprado esta extensión', 'text', 'es'),
    ('errors.common', 'extension_beta_only', 'Esta extensión es exclusiva para beta testers', 'text', 'es'),
    ('errors.common', 'user_not_found', 'El usuario no existe', 'text', 'es'),
    ('errors.common', 'duplicate_email', 'This email is already registered', 'text', 'en'),
    ('errors.common', 'invalid_credentials', 'Incorrect email or password', 'text', 'en'),
    ('errors.common', 'session_expired', 'Session expired, please log in again', 'text', 'en'),
    ('errors.common', 'unauthorized', 'You do not have permission for this action', 'text', 'en'),
    ('errors.common', 'required_field', 'Please fill in all required fields', 'text', 'en'),
    ('errors.common', 'validation_failed', 'Please check the entered data', 'text', 'en'),
    ('errors.common', 'server_error', 'Server error, please try again later', 'text', 'en'),
    ('errors.common', 'service_unavailable', 'Service unavailable, please try again later', 'text', 'en'),
    ('errors.common', 'bad_request', 'Invalid data', 'text', 'en'),
    ('errors.common', 'not_found', 'Resource not found', 'text', 'en'),
    ('errors.common', 'network_error', 'No connection to server', 'text', 'en'),
    ('errors.common', 'unexpected_error', 'Unexpected error, please try again', 'text', 'en'),
    ('errors.common', 'already_purchased', 'You have already purchased this extension', 'text', 'en'),
    ('errors.common', 'extension_beta_only', 'This extension is exclusive to beta testers', 'text', 'en'),
    ('errors.common', 'user_not_found', 'The user does not exist', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- placeholders (4 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('placeholders', 'email', 'tu@email.com', 'text', 'es'),
    ('placeholders', 'password', '••••••••', 'text', 'es'),
    ('placeholders', 'id', '123456789', 'text', 'es'),
    ('placeholders', 'phone', '+57 300 123 4567', 'text', 'es'),
    ('placeholders', 'email', 'your@email.com', 'text', 'en'),
    ('placeholders', 'password', '••••••••', 'text', 'en'),
    ('placeholders', 'id', '123456789', 'text', 'en'),
    ('placeholders', 'phone', '+1 555 123 4567', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- select.default (1 item × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('select.default', 'placeholder', 'Seleccione...', 'text', 'es'),
    ('select.default', 'placeholder', 'Select...', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- cart (27 items × 2 idiomas)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('cart', 'title', 'Tu carrito', 'text', 'es'),
    ('cart', 'close_aria', 'Cerrar carrito', 'text', 'es'),
    ('cart', 'aria_label', 'Carrito de compras', 'text', 'es'),
    ('cart', 'empty_title', 'Tu carrito está vacío', 'text', 'es'),
    ('cart', 'empty_subtitle', 'Agrega paquetes de expansión para verlos aquí.', 'text', 'es'),
    ('cart', 'explore_cta', 'Explorar paquetes', 'text', 'es'),
    ('cart', 'remove_aria', 'Quitar producto', 'text', 'es'),
    ('cart', 'clear_cta', 'Vaciar carrito', 'text', 'es'),
    ('cart', 'total_label', 'Total', 'text', 'es'),
    ('cart', 'checkout_cta', 'Proceder al pago', 'text', 'es'),
    ('cart', 'checkout_processing', 'Procesando compra...', 'text', 'es'),
    ('cart', 'checkout_success', '¡Compra realizada con éxito!', 'text', 'es'),
    ('cart', 'checkout_success_title', '¡Gracias por tu compra!', 'text', 'es'),
    ('cart', 'checkout_success_subtitle', 'Tus paquetes de expansión ya están disponibles.', 'text', 'es'),
    ('cart', 'checkout_success_items', '{{count}} paquete(s) comprado(s)', 'text', 'es'),
    ('cart', 'checkout_success_total', 'Total pagado', 'text', 'es'),
    ('cart', 'checkout_success_close', 'Cerrar', 'text', 'es'),
    ('cart', 'checkout_success_explore', 'Seguir explorando', 'text', 'es'),
    ('cart', 'checkout_error_title', 'Error en la compra', 'text', 'es'),
    ('cart', 'checkout_error_subtitle', 'No se pudo completar el pedido.', 'text', 'es'),
    ('cart', 'checkout_error_retry', 'Reintentar', 'text', 'es'),
    ('cart', 'checkout_error_continue', 'Seguir comprando', 'text', 'es'),
    ('cart', 'login_required', 'Inicia sesión para ver tu carrito', 'text', 'es'),
    ('cart', 'login_link', 'Ir a login', 'text', 'es'),
    ('cart', 'platform_label', 'Plataforma', 'text', 'es'),
    ('cart', 'language_label', 'Idioma', 'text', 'es'),
    ('cart', 'title', 'Your cart', 'text', 'en'),
    ('cart', 'close_aria', 'Close cart', 'text', 'en'),
    ('cart', 'aria_label', 'Shopping cart', 'text', 'en'),
    ('cart', 'empty_title', 'Your cart is empty', 'text', 'en'),
    ('cart', 'empty_subtitle', 'Add expansion packs to see them here.', 'text', 'en'),
    ('cart', 'explore_cta', 'Explore packs', 'text', 'en'),
    ('cart', 'remove_aria', 'Remove item', 'text', 'en'),
    ('cart', 'clear_cta', 'Clear cart', 'text', 'en'),
    ('cart', 'total_label', 'Total', 'text', 'en'),
    ('cart', 'checkout_cta', 'Proceed to checkout', 'text', 'en'),
    ('cart', 'checkout_processing', 'Processing purchase...', 'text', 'en'),
    ('cart', 'checkout_success', 'Purchase successful!', 'text', 'en'),
    ('cart', 'checkout_success_title', 'Thank you for your purchase!', 'text', 'en'),
    ('cart', 'checkout_success_subtitle', 'Your expansion packs are now available.', 'text', 'en'),
    ('cart', 'checkout_success_items', '{{count}} pack(s) purchased', 'text', 'en'),
    ('cart', 'checkout_success_total', 'Total paid', 'text', 'en'),
    ('cart', 'checkout_success_close', 'Close', 'text', 'en'),
    ('cart', 'checkout_success_explore', 'Keep exploring', 'text', 'en'),
    ('cart', 'checkout_error_title', 'Purchase Error', 'text', 'en'),
    ('cart', 'checkout_error_subtitle', 'Could not complete the order.', 'text', 'en'),
    ('cart', 'checkout_error_retry', 'Retry', 'text', 'en'),
    ('cart', 'checkout_error_continue', 'Keep Shopping', 'text', 'en'),
    ('cart', 'login_required', 'Log in to view your cart', 'text', 'en'),
    ('cart', 'login_link', 'Go to login', 'text', 'en'),
    ('cart', 'platform_label', 'Platform', 'text', 'en'),
    ('cart', 'language_label', 'Language', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);

-- ============================================================
-- Inserción de datos: site_config (configuraciones)
-- site_config no depende de idioma (es estática). La estructura que espera el FE
-- es [{ value, label }] para 'countries', según ContentProvider.jsx.
-- ============================================================

INSERT INTO site_config (config_key, config_value)
SELECT v.config_key, v.config_value
FROM (VALUES
    ('countries', '[{"value":"CO","label":"Colombia"},{"value":"MX","label":"México"},{"value":"AR","label":"Argentina"},{"value":"CL","label":"Chile"},{"value":"PE","label":"Perú"},{"value":"EC","label":"Ecuador"},{"value":"ES","label":"España"},{"value":"US","label":"Estados Unidos"}]')
) AS v(config_key, config_value)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_config sc
  WHERE sc.config_key = v.config_key
);

-- ============================================================
-- Inserción de datos: site_content — Sección admin.page y header.nav_admin
-- ============================================================

INSERT INTO site_content (section_key, content_key, content_value, content_type, language)
SELECT v.section_key, v.content_key, v.content_value, v.content_type, v.language
FROM (VALUES
    ('admin.page', 'title', 'Panel de Administrador', 'text', 'es'),
    ('admin.page', 'beta_users_tab', 'Usuarios Beta', 'text', 'es'),
    ('admin.page', 'stats_tab', 'Estadísticas', 'text', 'es'),
    ('admin.page', 'broadcast_tab', 'Correo Broadcast', 'text', 'es'),
    ('admin.page', 'promote_tab', 'Promover Admin', 'text', 'es'),
    ('admin.page', 'table_email', 'Email', 'text', 'es'),
    ('admin.page', 'table_name', 'Nombre', 'text', 'es'),
    ('admin.page', 'table_country', 'País', 'text', 'es'),
    ('admin.page', 'table_extension', 'Extensión', 'text', 'es'),
    ('admin.page', 'table_count', 'Compras', 'text', 'es'),
    ('admin.page', 'table_public', 'Público', 'text', 'es'),
    ('admin.page', 'table_private', 'Beta', 'text', 'es'),
    ('admin.page', 'broadcast_subject_label', 'Asunto', 'text', 'es'),
    ('admin.page', 'broadcast_body_label', 'Cuerpo', 'text', 'es'),
    ('admin.page', 'broadcast_send', 'Enviar', 'text', 'es'),
    ('admin.page', 'broadcast_success', 'Correo enviado exitosamente', 'text', 'es'),
    ('admin.page', 'broadcast_error', 'Error al enviar correo', 'text', 'es'),
    ('admin.page', 'broadcast_confirm', '¿Estás seguro de enviar el broadcast a todos los beta testers?', 'text', 'es'),
    ('admin.page', 'promote_search_placeholder', 'Buscar usuario por email', 'text', 'es'),
    ('admin.page', 'promote_button', 'Hacer admin', 'text', 'es'),
    ('admin.page', 'promote_success', 'Usuario promovido a administrador', 'text', 'es'),
    ('admin.page', 'promote_error', 'Error al promover usuario', 'text', 'es'),
    ('admin.page', 'no_perms', 'No tienes permisos de administrador', 'text', 'es'),
    ('admin.page', 'loading_text', 'Cargando...', 'text', 'es'),
    ('admin.page', 'loading_error', 'Error al cargar datos', 'text', 'es'),
    ('admin.page', 'admin_badge', 'Administrador', 'text', 'es'),
    ('admin.page', 'cancel_text', 'Cancelar', 'text', 'es'),
    ('admin.page', 'empty_beta', 'No hay usuarios beta registrados', 'text', 'es'),
    ('admin.page', 'empty_stats', 'Aún no hay compras registradas', 'text', 'es'),
    ('header', 'nav_admin', 'Admin', 'text', 'es'),
    ('extensions.search', 'placeholder', 'Buscar extensiones...', 'text', 'es'),
    ('extensions.search', 'search_aria', 'Buscar extensiones', 'text', 'es'),
    ('extensions.search', 'clear_aria', 'Limpiar búsqueda', 'text', 'es'),
    ('extensions.search', 'empty_results', 'No se encontraron resultados para "{{query}}".', 'text', 'es'),
    ('admin.page', 'title', 'Admin Panel', 'text', 'en'),
    ('admin.page', 'beta_users_tab', 'Beta Users', 'text', 'en'),
    ('admin.page', 'stats_tab', 'Stats', 'text', 'en'),
    ('admin.page', 'broadcast_tab', 'Broadcast Email', 'text', 'en'),
    ('admin.page', 'promote_tab', 'Promote Admin', 'text', 'en'),
    ('admin.page', 'table_email', 'Email', 'text', 'en'),
    ('admin.page', 'table_name', 'Name', 'text', 'en'),
    ('admin.page', 'table_country', 'Country', 'text', 'en'),
    ('admin.page', 'table_extension', 'Extension', 'text', 'en'),
    ('admin.page', 'table_count', 'Purchases', 'text', 'en'),
    ('admin.page', 'table_public', 'Public', 'text', 'en'),
    ('admin.page', 'table_private', 'Beta', 'text', 'en'),
    ('admin.page', 'broadcast_subject_label', 'Subject', 'text', 'en'),
    ('admin.page', 'broadcast_body_label', 'Body', 'text', 'en'),
    ('admin.page', 'broadcast_send', 'Send', 'text', 'en'),
    ('admin.page', 'broadcast_success', 'Email sent successfully', 'text', 'en'),
    ('admin.page', 'broadcast_error', 'Error sending email', 'text', 'en'),
    ('admin.page', 'broadcast_confirm', 'Are you sure you want to send the broadcast to all beta testers?', 'text', 'en'),
    ('admin.page', 'promote_search_placeholder', 'Search user by email', 'text', 'en'),
    ('admin.page', 'promote_button', 'Make admin', 'text', 'en'),
    ('admin.page', 'promote_success', 'User promoted to admin', 'text', 'en'),
    ('admin.page', 'promote_error', 'Error promoting user', 'text', 'en'),
    ('admin.page', 'no_perms', 'You do not have admin permissions', 'text', 'en'),
    ('admin.page', 'loading_text', 'Loading...', 'text', 'en'),
    ('admin.page', 'loading_error', 'Error loading data', 'text', 'en'),
    ('admin.page', 'admin_badge', 'Administrator', 'text', 'en'),
    ('admin.page', 'cancel_text', 'Cancel', 'text', 'en'),
    ('admin.page', 'empty_beta', 'No beta users registered', 'text', 'en'),
    ('admin.page', 'empty_stats', 'No purchases recorded yet', 'text', 'en'),
    ('header', 'nav_admin', 'Admin', 'text', 'en'),
    ('extensions.search', 'placeholder', 'Search extensions...', 'text', 'en'),
    ('extensions.search', 'search_aria', 'Search extensions', 'text', 'en'),
    ('extensions.search', 'clear_aria', 'Clear search', 'text', 'en'),
    ('extensions.search', 'empty_results', 'No results found for "{{query}}".', 'text', 'en')
) AS v(section_key, content_key, content_value, content_type, language)
WHERE NOT EXISTS (
  SELECT 1
  FROM site_content sc
  WHERE sc.section_key = v.section_key
    AND sc.content_key = v.content_key
    AND sc.language = v.language
);
