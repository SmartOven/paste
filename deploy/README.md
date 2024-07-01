# Necessary env variables in .env file
```.env
# When changing MINIO_CONNECTION_PORT don't forget to also update `s3.service-port` in the vault
# When changing FRONTEND_PORT don't forget to also update `frontend.port` in the vault
# When changing any port don't forget to also update nginx config
BACKEND_PORT=8082
FRONTEND_PORT=3002
MINIO_CONNECTION_PORT=9000
MINIO_UI_PORT=9001
APP_ENVIRONMENT=production
APP_VAULT_ADDR=
APP_VAULT_TOKEN=
MINIO_ROOT_USER=
MINIO_ROOT_PASSWORD=
```