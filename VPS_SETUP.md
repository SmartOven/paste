# Setup on VPS

Example for Ubuntu 20+

## System preparation

1. Update system with `sudo apt update`
2. Install and set up `ufw`
    - `sudo apt install ufw`
    - `sudo ufw default deny incoming`
    - `sudo ufw default allow outgoing`
    - `sudo ufw allow 22/tcp`
    - `sudo ufw allow 22`
    - `sudo ufw allow 80/tcp`
    - `sudo ufw allow 443`
    - `sudo ufw allow 80,443/tcp`
    - `sudo ufw enable`
    - `sudo ufw status verbose`
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
2. Set up environment for `minio`
   - Create folder `secrets` in the current folder
   - Inside `secrets` create two files: `minio_root_user.txt` and `minio_root_password.txt` 
   - Create yourself `user` and `password` and put them in these files
3. Set up docker containers with `docker-compose up -d`
4. Run `bash setup_nginx.sh` to set up `nginx` config
