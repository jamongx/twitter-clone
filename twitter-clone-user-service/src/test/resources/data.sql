-- INSERT INTO roles (id, role, created_at) VALUES ('1', 'ROLE_ADMIN', now());
-- INSERT INTO roles (id, role, created_at) VALUES ('2', 'ROLE_USER', now());

INSERT INTO roles (id, role, created_at) VALUES ('1', 'ROLE_ADMIN', now())
    ON DUPLICATE KEY UPDATE id=id;

INSERT INTO roles (id, role, created_at) VALUES ('2', 'ROLE_USER', now())
    ON DUPLICATE KEY UPDATE id=id;
