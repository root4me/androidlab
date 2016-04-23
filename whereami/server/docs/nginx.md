
### Installation

`sudo apt-get install nginx`

Default file structure
- configuration files : /etc/nginx
- static html : /usr/share/nginx/html
- log : /var/log/nginx/
- cache : /var/cache

### Commands
Start
`sudo service nginx start`

Stop
`sudo service nginx stop`

Restart
`sudo service nginx restart`


### Reverse proxy configuration

```
server {
        listen 80 default_server;
        listen [::]:80 default_server ipv6only=on;

        root /usr/share/nginx/html;
        index index.html index.htm;

        server_name ENTER_DOMAIN_NAME www.ENTER_DOMAIN_NAME;

  location / {
        proxy_set_header X-Real-IP $remote_addr;
        proxy_set_header X-Forwarded-For $proxy_add_x_forwarded_for;
        proxy_set_header Host $http_host;
        proxy_set_header X-NginX-Proxy true;

        # Run the site at port 3000 or change the following line accordingly
        proxy_pass http://127.0.0.1:3000/;
        proxy_redirect off;

    }

    # custom_50x.html need to be created and placed inside /usr/share/nginx/html
    error_page 400 500 502 503 504 /custom_50x.html;
    location = /custom_50x.html {
        root /usr/share/nginx/html;
        internal;
      }

```
### Uninstall

If something gets rotten and you need to completely uninstall nginx

`sudo apt-get purge nginx nginx-common`
