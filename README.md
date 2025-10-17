![](.assets/social.png)

# <samp>OVERVIEW</samp>

Shreduck is a training platform where users can register and create their own workouts using a library of existing exercises. It features a premium system where ready-made workouts can be unlocked by watching ads.

The application is a fullstack monorepo combining a Spring Boot REST API backend with an Ionic and Angular frontend. Built with a three-tier architecture (presentation, business logic, data access), it delivers a responsive, cross-platform experience for both web and mobile users.

### Mobile Application

<img src=".assets/img1.png" width="23.5%"/><img src=".assets/1x1.png" width="2%"/><img src=".assets/img2.png" width="23.5%"/><img src=".assets/1x1.png" width="2%"/><img src=".assets/img3.gif" width="23.5%"/><img src=".assets/1x1.png" width="2%"/><img src=".assets/img4.png" width="23.5%"/>

### ER Diagram

![](.assets/erd.png)

# <samp>GUIDANCE</samp>

### Debug Server

```
idea src/server
```

### Debug Mobile

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
