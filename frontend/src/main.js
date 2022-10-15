import { createApp } from "vue";
import { createPinia } from "pinia";
import Keycloak from "keycloak-js";
import App from "./App.vue";
import router from "./router";
import "./assets/main.css";

let initOptions = {
  url: "https://31.187.76.109:8443/",
  realm: "myrealm",
  clientId: "myclient",
  onLoad: "login-required",
};

let keycloak = new Keycloak(initOptions);

keycloak
  .init({ onLoad: initOptions.onLoad })
  .then((auth) => {
    if (!auth) {
      window.location.reload();
    } else {
      const app = createApp(App);
      app.use(createPinia());
      app.use(router);
      app.provide("keycloak", keycloak);
      app.mount("#app");
    }

    //Refresh token every 70 seconds
    setInterval(() => {
      keycloak
        .updateToken(70)
        .then((refreshed) => {
          if (refreshed) {
            console.log("Token refreshed" + refreshed);
          } else {
            console.log(
              "Token not refreshed, valid for " +
                Math.round(
                  keycloak.tokenParsed.exp +
                    keycloak.timeSkew -
                    new Date().getTime() / 1000
                ) +
                " seconds"
            );
          }
        })
        .catch(() => {
          console.error("Failed to refresh token");
        });
    }, 6000);
  })
  .catch(() => {
    console.error("Authenticated Failed");
  });
