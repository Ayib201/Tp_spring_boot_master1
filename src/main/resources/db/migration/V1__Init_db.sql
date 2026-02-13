CREATE TABLE app_roles_entity
(
    id  INT AUTO_INCREMENT NOT NULL,
    nom VARCHAR(100) NOT NULL,
    CONSTRAINT pk_approlesentity PRIMARY KEY (id)
);

CREATE TABLE app_user_entity
(
    id       INT AUTO_INCREMENT NOT NULL,
    nom      VARCHAR(150) NOT NULL,
    prenom   VARCHAR(200) NOT NULL,
    email    VARCHAR(200) NOT NULL,
    password VARCHAR(200) NOT NULL,
    etat     INT          NOT NULL,
    CONSTRAINT pk_appuserentity PRIMARY KEY (id)
);

CREATE TABLE app_user_entity_app_role_entities
(
    app_role_entities_id INT NOT NULL,
    app_user_entity_id   INT NOT NULL
);

CREATE TABLE produit_entity
(
    id                 INT AUTO_INCREMENT NOT NULL,
    nom                VARCHAR(200) NOT NULL,
    qt_stock DOUBLE NOT NULL,
    app_user_entity_id INT NULL,
    CONSTRAINT pk_produitentity PRIMARY KEY (id)
);

ALTER TABLE app_roles_entity
    ADD CONSTRAINT uc_approlesentity_nom UNIQUE (nom);

ALTER TABLE produit_entity
    ADD CONSTRAINT uc_produitentity_nom UNIQUE (nom);

ALTER TABLE produit_entity
    ADD CONSTRAINT FK_PRODUITENTITY_ON_APPUSERENTITY FOREIGN KEY (app_user_entity_id) REFERENCES app_user_entity (id);

ALTER TABLE app_user_entity_app_role_entities
    ADD CONSTRAINT fk_appuseentapprolent_on_app_roles_entity FOREIGN KEY (app_role_entities_id) REFERENCES app_roles_entity (id);

ALTER TABLE app_user_entity_app_role_entities
    ADD CONSTRAINT fk_appuseentapprolent_on_app_user_entity FOREIGN KEY (app_user_entity_id) REFERENCES app_user_entity (id);