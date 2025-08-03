CREATE SEQUENCE IF NOT EXISTS gardens_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS plants_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 50;

CREATE TABLE garden_plants
(
    garden_id BIGINT NOT NULL,
    plant_id  BIGINT NOT NULL
);

CREATE TABLE gardens
(
    id          BIGINT                      NOT NULL,
    name        VARCHAR(255)                NOT NULL,
    description VARCHAR(255),
    created_on  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    garden_id   BIGINT,
    CONSTRAINT pk_gardens PRIMARY KEY (id)
);

CREATE TABLE plants
(
    id          BIGINT                      NOT NULL,
    common_name VARCHAR(255)                NOT NULL,
    sci_name    VARCHAR(255),
    description VARCHAR(255),
    created_on  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_plants PRIMARY KEY (id)
);

CREATE TABLE "user"
(
    id         BIGINT                      NOT NULL,
    username   VARCHAR(255)                NOT NULL,
    password   VARCHAR(255)                NOT NULL,
    email      VARCHAR(255)                NOT NULL,
    first_name VARCHAR(255)                NOT NULL,
    last_name  VARCHAR(255)                NOT NULL,
    created_on TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);

CREATE TABLE user_garden_list
(
    user_id        BIGINT NOT NULL,
    garden_list_id BIGINT NOT NULL
);

ALTER TABLE user_garden_list
    ADD CONSTRAINT uc_user_garden_list_gardenlist UNIQUE (garden_list_id);

ALTER TABLE "user"
    ADD CONSTRAINT uc_user_username UNIQUE (username);

ALTER TABLE gardens
    ADD CONSTRAINT FK_GARDENS_ON_GARDEN FOREIGN KEY (garden_id) REFERENCES "user" (id);

ALTER TABLE garden_plants
    ADD CONSTRAINT fk_garpla_on_garden FOREIGN KEY (garden_id) REFERENCES gardens (id);

ALTER TABLE garden_plants
    ADD CONSTRAINT fk_garpla_on_plant FOREIGN KEY (plant_id) REFERENCES plants (id);

ALTER TABLE user_garden_list
    ADD CONSTRAINT fk_usegarlis_on_garden FOREIGN KEY (garden_list_id) REFERENCES gardens (id);

ALTER TABLE user_garden_list
    ADD CONSTRAINT fk_usegarlis_on_user FOREIGN KEY (user_id) REFERENCES "user" (id);