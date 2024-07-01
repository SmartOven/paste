# Setup on VPS

Example for Ubuntu 20+

## System preparation

1. Update system with `apt update`
2. Install and set up `ufw`
    - `apt install ufw`
    - `ufw default deny incoming`
    - `ufw default allow outgoing`
    - `ufw allow 22/tcp`
    - `ufw allow 22`
    - `ufw allow 80/tcp`
    - `ufw allow 443`
    - `ufw allow 80,443/tcp`
    - `ufw enable`
    - `ufw status verbose`
    - Don't forget to check if new ssh connections are working
3. Install `nginx` with `sudo apt install nginx`
4. Add env variables in the beginning of the `~/.bashrc`
    - `export ENVIRONMENT="<env>"`
5. Install `docker` with this guide - https://docs.docker.com/engine/install/ubuntu/
6. Install `docker-compose`
    - `sudo curl -L "https://github.com/docker/compose/releases/download/1.29.2/docker-compose-$(uname -s)-$(uname -m)" -o /usr/local/bin/docker-compose`
    - `sudo chmod +x /usr/local/bin/docker-compose`
    - `docker-compose --version`
7. Install and set up `vault` with this guide - https://github.com/SmartOven/vault/blob/main/README.md
8. Reboot the server

## Set up the project

1. Clone repository from GitHub and navigate to its `deploy` folder
2. Create `.env` file and fill it with all necessary variables from `README.md`
3. Set up docker containers with `docker-compose build --no-cache`
4. Run docker containers with `docker-compose up -d`
5. Run `bash setup_nginx.sh` to set up `nginx` config
6. Enable access to minio ports
    - `ufw allow 9000`
    - `ufw allow 9000/tcp`
    - `ufw enable`
7. Open Minio UI, create Access token and save it with its secret key
8. Create bucket, save its name
9. Open Vault, create secret, put there access key, secret key and bucket name along with other
   variables: `frontend.host` is `http://localhost`, `frontend.port` is FRONTEND_PORT from `.env`, `s3.service-host`
   is `http://<server_ip>`, `s3.service-port` is MINIO_CONNECTION_PORT from `.env`
10. Stop docker containers with `docker-compose down`
11. Rerun docker containers with `docker-compose up -d`
12. Check if it is working by opening Paste in the browser
