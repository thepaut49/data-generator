import { createApp } from "vue";
import { createPinia } from "pinia";
import Keycloak from "keycloak-js";
import App from "./App.vue";
import router from "./router";
import "./assets/main.css";
import { library } from "@fortawesome/fontawesome-svg-core";
import { faSync, faPlus, faEdit } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/vue-fontawesome";
import { useUserStore } from "./stores/UserStore";

library.add(faSync, faPlus, faEdit);

let initOptions = {
  url: "http://31.187.76.109:8080/auth",
  realm: "myrealm",
  clientId: "frontend",
  onLoad: "login-required",
};

let keycloak = new Keycloak(initOptions);

console.log("origin = " + window.location.origin);

keycloak
  .init({
    onLoad: initOptions.onLoad,
    // silentCheckSsoRedirectUri:
    //  window.location.origin + "/silent-check-sso.html",
  })
  .then((auth) => {
    if (!auth) {
      window.location.reload();
    } else {
      const pinia = createPinia();
      const app = createApp(App);
      app.use(pinia);
      app.use(router);
      app.provide("keycloak", keycloak);
      app.component("font-awesome-icon", FontAwesomeIcon);
      app.mount("#app");
      const userStore = useUserStore();
      userStore.initKeycloak(keycloak);
    }

    //Refresh token every 70 seconds
    /*setInterval(() => {
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
    }, 290000);*/
  })
  .catch((error) => {
    console.error("Authenticated Failed : {}", error);
  });
