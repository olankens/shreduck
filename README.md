<hr>

<div align="center">
  <img src=".assets/icon.svg" width="132">
  <h1><samp>SHREDUCK</samp></h1>
  <p>Shreduck is a training platform where users can register and create their own workouts using a library of existing exercises. It features a premium system where ready-made workouts can be unlocked by watching ads. The application is a fullstack monorepo combining a Spring Boot REST API backend with an Ionic and Angular frontend, it delivers a responsive, cross-platform experience for both web and mobile users.</p>
</div>

<hr>

### Mobile Application

<img src=".assets/01.png" width="24.0625%"/><img src=".assets/00.png" width="1.25%"/><img src=".assets/02.png" width="24.0625%"/><img src=".assets/00.png" width="1.25%"/><img src=".assets/03.gif" width="24.0625%"/><img src=".assets/00.png" width="1.25%"/><img src=".assets/04.png" width="24.0625%"/>

<hr>

### ER Diagram

![](.assets/erd.png)

<hr>

### Debugging Server

###

```
idea src/server
```

<hr>

### Debugging Mobile

```shell
# For Android
cd src/mobile
npx cap copy android
npx cap sync android
npx cap open android

# For iOS
cd src/mobile
npx cap copy ios
npx cap sync ios
npx cap open ios
```

<hr>
