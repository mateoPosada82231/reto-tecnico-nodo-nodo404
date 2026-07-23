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
