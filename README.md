<div align="center">
    <picture>
        <source media="(prefers-color-scheme: dark)" srcset=".assets/icon-dark.svg">
        <img src=".assets/icon.svg" height="132">
    </picture>
    <h1><samp>SHREDUCK</samp></h1>
    <p>Shreduck is a training platform where users can register and create their own workouts using a library of existing exercises. It features a premium system where ready-made workouts can be unlocked by watching ads. The application is a fullstack monorepo combining a Spring Boot REST API backend with an Ionic and Angular frontend, it delivers a responsive, cross-platform experience for both web and mobile users.</p>
</div>

---

### Mobile Application

<img src=".assets/01.png" width="24.0625%"/><img src=".assets/1x1.gif" width="1.25%"/><img src=".assets/02.png" width="24.0625%"/><img src=".assets/1x1.gif" width="1.25%"/><img src=".assets/03.gif" width="24.0625%"/><img src=".assets/1x1.gif" width="1.25%"/><img src=".assets/04.png" width="24.0625%"/>

---

### ER Diagram

![](.assets/erd.png)

---

### Debugging Server

###

```
idea src/server
```

---

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
