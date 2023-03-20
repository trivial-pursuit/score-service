FROM quay.io/keycloak/keycloak:21.0 as builder

ENV KC_DB=postgres
ENV KC_HTTPS_KEY_STORE_FILE=/opt/keycloak/conf/cert/keycloak.local.p12
ENV KC_HTTPS_KEY_STORE_PASSWORD=password

WORKDIR /opt/keycloak
COPY cert /opt/keycloak/conf/cert
COPY import /opt/keycloak/data/import
RUN /opt/keycloak/bin/kc.sh build

FROM quay.io/keycloak/keycloak:21.0
COPY --from=builder /opt/keycloak/ /opt/keycloak/

ENV KC_DB=postgres
ENV KC_DB_URL=jdbc:postgresql://db-score:5432/keycloak
ENV KC_DB_USERNAME=admin
ENV KC_DB_PASSWORD=admin
ENV KC_HTTPS_KEY_STORE_FILE=/opt/keycloak/conf/cert/keycloak.local.p12
ENV KC_HTTPS_KEY_STORE_PASSWORD=password
ENV KC_HOSTNAME=localhost
ENTRYPOINT ["/opt/keycloak/bin/kc.sh"]
