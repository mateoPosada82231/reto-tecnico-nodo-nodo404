-- ============================================================
-- Inserción de datos: extensions (Hibernate naming)
-- Las imágenes están alojadas en Cloudinary
-- Los IDs se generan automáticamente (IDENTITY)
-- ============================================================

INSERT INTO extensions (
    required_age,
    price,
    name,
    about_game,
    platforms,
    languages,
    distributor,
    publication_date,
    category,
    image
) VALUES
(
    13,
    159900.00,
    'Los Sims 4: Vida en el Rancho',
    'Crea tu propia vida rústica. Cría y entrena caballos, entabla amistad con mini-cabras y mini-ovejas, y elabora tu propio néctar casero para vender o compartir.',
    'PC, Mac, PlayStation, Xbox',
    'Español, Inglés',
    'Electronic Arts',
    '2022-07-28',
    'Expansion Pack',
    'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/rancho_joiffx.jpg'
),
(
    13,
    159900.00,
    'Los Sims 4: Días de Universidad',
    'Inscríbete en la universidad, explora experiencias académicas y asiste a actividades extracurriculares.',
    'PC, Mac, PlayStation, Xbox',
    'Español, Inglés',
    'Electronic Arts',
    '2019-11-15',
    'Expansion Pack',
    'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822907/universidad_niieak.jpg'
),
(
    13,
    159900.00,
    'Los Sims 4: Urbanitas',
    'Lleva a tus Sims a la gran ciudad y experimenta todo lo que ofrece, desde el bullicio exterior hasta situaciones exclusivas de los apartamentos.',
    'PC, Mac, PlayStation, Xbox',
    'Español, Inglés',
    'Electronic Arts',
    '2016-11-01',
    'Expansion Pack',
    'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822907/urbanitas_dxfr2h.jpg'
),
(
    13,
    159900.00,
    'Los Sims 4: Y Las Cuatro Estaciones',
    'Añade el clima a la vida de tus Sims para contar nuevas historias, disfrutar de actividades estacionales y celebrar festividades.',
    'PC, Mac, PlayStation, Xbox',
    'Español, Inglés',
    'Electronic Arts',
    '2018-11-13',
    'Expansion Pack',
    'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/estaciones_vhzuar.jpg'
),
(
    13,
    159900.00,
    'Los Sims 4: ¡A Trabajar!',
    'Controla activamente a tus Sims en sus lugares de trabajo y determina si se dirigen hacia un gran ascenso o se convierten en una amenaza.',
    'PC, Mac, PlayStation, Xbox',
    'Español, Inglés',
    'Electronic Arts',
    '2015-04-01',
    'Expansion Pack',
    'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822905/trabajo_tury19.jpg'
),
(
    13,
    159900.00,
    'Los Sims 4: Perros y Gatos',
    'Crea una variedad de perros y gatos, añádelos a los hogares de tus Sims para cambiar sus vidas para siempre y cuida de las mascotas del vecindario.',
    'PC, Mac, PlayStation, Xbox',
    'Español, Inglés',
    'Electronic Arts',
    '2017-11-10',
    'Expansion Pack',
    'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822910/mascotas_yxggma.jpg'
),
(
    13,
    159900.00,
    'Los Sims 4: Vida Isleña',
    'Escapa de la rutina y disfruta de un estilo de vida relajado rodeado de playas en Sulani, donde el sol brilla con fuerza y las noches son refrescantes.',
    'PC, Mac, PlayStation, Xbox',
    'Español, Inglés',
    'Electronic Arts',
    '2019-06-21',
    'Expansion Pack',
    'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/isla_mzfikd.jpg'
),
(
    13,
    159900.00,
    'Los Sims 4: Escapada en la Nieve',
    'Equípate para los deportes de invierno, relájate en aguas termales y diseña tu hogar de estilo japonés.',
    'PC, Mac, PlayStation, Xbox',
    'Español, Inglés',
    'Electronic Arts',
    '2021-11-23',
    'Expansion Pack',
    'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822911/nieve_vovttv.jpg'
),
(
    13,
    159900.00,
    'Los Sims 4: Se Alquila',
    'Experimenta la vida en vecindarios multifamiliares como inquilino o propietario de propiedades residenciales en alquiler.',
    'PC, Mac, PlayStation, Xbox',
    'Español, Inglés',
    'Electronic Arts',
    '2023-09-14',
    'Expansion Pack',
    'https://res.cloudinary.com/gvpm2ptm/image/upload/v1784822908/alquiler_ltzwy5.jpg'
);

-- ============================================================
-- Inserción de datos: site_content (CMS textos de UI)
-- ============================================================

-- landing.hero (5 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('landing.hero', 'title', 'Bienvenido a Nodo Store', 'text', 'es'),
('landing.hero', 'subtitle', 'Descubre los mejores juegos y expansiones', 'text', 'es'),
('landing.hero', 'cta_text', 'Explorar Ahora', 'text', 'es'),
('landing.hero', 'image_alt', 'Banner principal de Nodo Store', 'text', 'es'),
('landing.hero', 'badge', 'Novedades', 'text', 'es');

-- landing.grid (3 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('landing.grid', 'title', 'Expansiones Populares', 'text', 'es'),
('landing.grid', 'cta_view_all', 'Ver Todo', 'text', 'es'),
('landing.grid', 'cta_view_detail', 'Ver Detalle', 'text', 'es');

-- landing.welcome (4 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('landing.welcome', 'title', 'Bienvenido a Nodo', 'text', 'es'),
('landing.welcome', 'subtitle', 'Tu tienda de confianza para expansiones de juegos', 'text', 'es'),
('landing.welcome', 'cta_accept', 'Entendido', 'text', 'es'),
('landing.welcome', 'description', 'Explora nuestro catálogo de expansiones y contenido adicional para tus juegos favoritos.', 'text', 'es');

-- auth.login (7 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('auth.login', 'title', 'Iniciar Sesión', 'text', 'es'),
('auth.login', 'subtitle', 'Accede con tu cuenta para continuar', 'text', 'es'),
('auth.login', 'email_label', 'Correo Electrónico', 'text', 'es'),
('auth.login', 'password_label', 'Contraseña', 'text', 'es'),
('auth.login', 'cta_text', 'Iniciar Sesión', 'text', 'es'),
('auth.login', 'forgot_password', '¿Olvidaste tu contraseña?', 'text', 'es'),
('auth.login', 'no_account', '¿No tienes cuenta? Regístrate', 'text', 'es');

-- auth.register (14 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('auth.register', 'title', 'Crear Cuenta', 'text', 'es'),
('auth.register', 'subtitle', 'Regístrate para acceder a toda la tienda', 'text', 'es'),
('auth.register', 'full_name_label', 'Nombre Completo', 'text', 'es'),
('auth.register', 'email_label', 'Correo Electrónico', 'text', 'es'),
('auth.register', 'password_label', 'Contraseña', 'text', 'es'),
('auth.register', 'confirm_password_label', 'Confirmar Contraseña', 'text', 'es'),
('auth.register', 'mobile_label', 'Número de Celular', 'text', 'es'),
('auth.register', 'country_label', 'País', 'text', 'es'),
('auth.register', 'id_label', 'Número de Identificación', 'text', 'es'),
('auth.register', 'birth_date_label', 'Fecha de Nacimiento', 'text', 'es'),
('auth.register', 'cta_text', 'Crear Cuenta', 'text', 'es'),
('auth.register', 'has_account', '¿Ya tienes cuenta? Inicia sesión', 'text', 'es'),
('auth.register', 'terms_prefix', 'Al registrarte aceptas nuestros', 'text', 'es'),
('auth.register', 'terms_link', 'Términos y Condiciones', 'text', 'es');

-- auth.social (2 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('auth.social', 'divider_text', 'o continúa con', 'text', 'es'),
('auth.social', 'or_text', 'o', 'text', 'es');

-- auth.oauth (1 item)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('auth.oauth', 'loading_text', 'Iniciando sesión con proveedor...', 'text', 'es');

-- header (6 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('header', 'logo_alt', 'Nodo Store Logo', 'text', 'es'),
('header', 'nav_home', 'Inicio', 'text', 'es'),
('header', 'nav_extensions', 'Expansiones', 'text', 'es'),
('header', 'nav_cart', 'Carrito', 'text', 'es'),
('header', 'nav_profile', 'Mi Cuenta', 'text', 'es'),
('header', 'beta_badge', 'Beta', 'text', 'es');

-- beta_modal (9 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('beta_modal', 'title', 'Únete al Programa Beta', 'text', 'es'),
('beta_modal', 'subtitle', 'Sé de los primeros en probar nuevas funcionalidades', 'text', 'es'),
('beta_modal', 'description', 'Como tester beta, tendrás acceso anticipado a nuevas características y podrás ayudarnos a mejorar la experiencia de compra.', 'text', 'es'),
('beta_modal', 'feature_1', 'Acceso anticipado a nuevos juegos', 'text', 'es'),
('beta_modal', 'feature_2', 'Descuentos exclusivos para testers', 'text', 'es'),
('beta_modal', 'feature_3', 'Participación en encuestas prioritarias', 'text', 'es'),
('beta_modal', 'cta_text', 'Unirme al Beta', 'text', 'es'),
('beta_modal', 'cta_later', 'Quizás después', 'text', 'es'),
('beta_modal', 'email_label', 'Correo Electrónico', 'text', 'es');

-- footer (1 item)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('footer', 'copyright', '© 2026 Nodo Store. Todos los derechos reservados.', 'text', 'es');

-- common (1 item)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('common', 'loading', 'Cargando...', 'text', 'es');

-- profile.page (12 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
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
('profile.page', 'error_message', 'No se pudo actualizar el perfil', 'text', 'es');

-- header (3 nuevos)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('header', 'profile_link_aria', 'Ver perfil', 'text', 'es'),
('header', 'beta_badge_label', 'Beta', 'text', 'es'),
('header', 'mobile_profile_link', 'Perfil', 'text', 'es');

-- theme.toggle (2 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('theme.toggle', 'light_aria', 'Cambiar a tema claro', 'text', 'es'),
('theme.toggle', 'dark_aria', 'Cambiar a tema oscuro', 'text', 'es');

-- validation.login (3 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('validation.login', 'email_required', 'El correo es obligatorio', 'text', 'es'),
('validation.login', 'email_invalid', 'Correo inválido', 'text', 'es'),
('validation.login', 'password_required', 'Ingrese una contraseña', 'text', 'es');

-- validation.register (15 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
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
('validation.register', 'confirm_match', 'Las contraseñas no coinciden', 'text', 'es');

-- validation.profile (1 item)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('validation.profile', 'name_required', 'El nombre es obligatorio', 'text', 'es');

-- errors.common (13 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
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
('errors.common', 'already_purchased', 'You have already purchased this extension', 'text', 'en');

-- cart (26 items por idioma es/en)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
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
('cart', 'language_label', 'Language', 'text', 'en');

-- landing.detail (26 items por idioma es/en)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
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
('landing.detail', 'add_to_cart_success', 'Added to cart successfully', 'text', 'en');

-- placeholders (4 items)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('placeholders', 'email', 'tu@email.com', 'text', 'es'),
('placeholders', 'password', '••••••••', 'text', 'es'),
('placeholders', 'id', '123456789', 'text', 'es'),
('placeholders', 'phone', '+57 300 123 4567', 'text', 'es');

-- select.default (1 item)
INSERT INTO site_content (section_key, content_key, content_value, content_type, language) VALUES
('select.default', 'placeholder', 'Seleccione...', 'text', 'es');

-- ============================================================
-- Inserción de datos: site_config (configuraciones)
-- ============================================================

INSERT INTO site_config (config_key, config_value) VALUES
('countries', '[{"code":"CO","name":"Colombia"},{"code":"MX","name":"México"},{"code":"AR","name":"Argentina"},{"code":"CL","name":"Chile"},{"code":"PE","name":"Perú"},{"code":"EC","name":"Ecuador"},{"code":"ES","name":"España"},{"code":"US","name":"Estados Unidos"}]');
