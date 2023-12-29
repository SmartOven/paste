# Setup VM
Example for Ubuntu 20+

```bash
# Update system
sudo apt update
```

```bash
# Install Java 18
sudo apt install openjdk-17-jdk
```

```bash
# Setup UFW
sudo apt install ufw

sudo ufw default deny incoming
sudo ufw default allow outgoing

sudo ufw allow ssh
sudo ufw allow http
sudo ufw allow https
sudo ufw allow 8080

sudo ufw enable

sudo ufw status verbose

# Dont forget to check if ssh connections are working
```

```bash
# Install nvm
curl https://raw.githubusercontent.com/creationix/nvm/master/install.sh | bash

# Add this to the ~/.bashrc:
# export NVM_DIR="$HOME/.nvm"
# [ -s "$NVM_DIR/nvm.sh" ] && \. "$NVM_DIR/nvm.sh"
# [ -s "$NVM_DIR/bash_completion" ] && \. "$NVM_DIR/bash_completion"

# After that renew the session to load ~/.bashrc changes

# Install node
nvm install node
```

```bash
# Clone project
git clone https://<token>@github.com/<account>/<project>.git
```

```bash
# Install nginx
sudo apt install nginx

# Create nginx config
sudo nano /etc/nginx/conf.d/react.conf

# Check if it is valid
sudo nginx -t

# Reload nginx service
sudo service nginx reload
```
