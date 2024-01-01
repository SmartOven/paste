# Setup VM
Example for Ubuntu 20+

```bash
# Update system
sudo apt update
```

```bash
# Install Java 18
sudo apt install openjdk-18-jdk
```

```bash
# Setup UFW
sudo apt install ufw

sudo ufw default deny incoming
sudo ufw default allow outgoing

sudo ufw allow 22/tcp
sudo ufw allow 22
sudo ufw allow 80/tcp
sudo ufw allow 443
sudo ufw allow 80,443/tcp

sudo ufw enable

sudo ufw status verbose

# Dont forget to check if ssh connections are working
```

```bash
# Install nginx
sudo apt install nginx

# Edit default nginx config
sudo nano /etc/nginx/sites-enabled/default

# Check if it is valid
sudo nginx -t

# Reload nginx service
sudo service nginx reload
```

```bash
# Add this lines to ~/.bashrc
export ENVIRONMENT="<env>"
export YC_OAUTH_TOKEN="<oauth_token>"
```

```bash
# Install docker if needed
# https://docs.docker.com/engine/install/ubuntu/
```

```bash
# Install mongosh
wget -qO- https://www.mongodb.org/static/pgp/server-7.0.asc | sudo tee /etc/apt/trusted.gpg.d/server-7.0.asc

echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/7.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-7.0.list

sudo apt-get update

sudo apt-get install -y mongodb-mongosh

# Create mongo user
mongosh mongodb://<user>:<password>@localhost:<port>

use <db>
db.createUser({
    user: <username>,
    pwd: <password>,
    roles: [
        { role: "readWrite", db: "miallo" }
    ]
});
exit
```
