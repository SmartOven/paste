# Setup VM
Example for Ubuntu 20+

Update system
```bash
sudo apt update
```

Install Java 18
```bash
sudo apt install openjdk-18-jdk
```

Setup UFW
```bash
sudo apt install ufw
```
```bash
sudo ufw default deny incoming
```
```bash
sudo ufw default allow outgoing
```
```bash
sudo ufw allow 22/tcp
```
```bash
sudo ufw allow 22
```
```bash
sudo ufw allow 80/tcp
```
```bash
sudo ufw allow 443
```
```bash
sudo ufw allow 80,443/tcp
```
```bash
sudo ufw enable
```
```bash
sudo ufw status verbose
```
Dont forget to check if ssh connections are working, then reboot

Install nginx
```bash
sudo apt install nginx
```
Edit default nginx config
```bash
sudo nano /etc/nginx/sites-enabled/default
```
Check if it is valid
```bash
sudo nginx -t
```
Reload nginx service
```bash
sudo service nginx reload
```

Add this lines to `~/.bashrc`
```bash
export ENVIRONMENT="<env>"
export YC_OAUTH_TOKEN="<oauth_token>"
```

Install docker if needed - https://docs.docker.com/engine/install/ubuntu/

Install mongosh
```bash
wget -qO- https://www.mongodb.org/static/pgp/server-7.0.asc | sudo tee /etc/apt/trusted.gpg.d/server-7.0.asc
```
```bash
echo "deb [ arch=amd64,arm64 ] https://repo.mongodb.org/apt/ubuntu jammy/mongodb-org/7.0 multiverse" | sudo tee /etc/apt/sources.list.d/mongodb-org-7.0.list
```
```bash
sudo apt-get update
```
```bash
sudo apt-get install -y mongodb-mongosh
```
Create mongo user
```bash
mongosh mongodb://<user>:<password>@localhost:<port>
```

```mongosh
use <db>
```
```mongosh
db.createUser({
    user: "<username>",
    pwd: "<password>",
    roles: [
        { role: "readWrite", db: "<your_db>" }
    ]
});
```
```mongosh
exit
```
