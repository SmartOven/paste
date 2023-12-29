server {
    server_name  panteleevya.ru;
    index index.html index.htm;
    access_log /var/log/nginx/panteleevya.log;
    error_log  /var/log/nginx/panteleevya-error.log error;

    location / {
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header X-Forwarded-Proto $scheme;
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header Host $http_host;
        proxy_pass http://127.0.0.1:5173;
        proxy_redirect off;
    }
}