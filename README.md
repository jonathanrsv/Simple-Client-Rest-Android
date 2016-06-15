# Rocket Chat - Simple Rest Client with okhttp


Installation
------------
Use the following snippet

    buildscript {
        dependencies {
            compile 'com.squareup.okhttp3:okhttp:3.3.1'
        }
    }


Usage
-----

``` java
RocketChatClientAndroid rocket = new RocketChatClientAndroid("https://your-company-url", "example@test.com.br", "abc123");

rocket.authenticate();

rocket.getPublicRooms();

```

