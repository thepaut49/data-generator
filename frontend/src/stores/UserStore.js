import { defineStore } from "pinia";

export const useUserStore = defineStore("UserStore", {
  state() {
    return {
      keycloak: {},
    };
  },
  getters: {
    getToken(state) {
      let keycloak = state.keycloak;
      return keycloak ? keycloak.token : "";
    },
    getRefreshToken(state) {
      let keycloak = state.keycloak;
      return keycloak ? keycloak.refreshToken : "";
    },
    getTokenParsed(state) {
      let keycloak = state.keycloak;
      return keycloak ? keycloak.tokenParsed : "";
    },
    getRefreshTokenParsed(state) {
      let keycloak = state.keycloak;
      return keycloak ? keycloak.refreshTokenParsed : "";
    },
    getSubject(state) {
      let keycloak = state.keycloak;
      return keycloak ? keycloak.subject : "";
    },
    getAuthenticated(state) {
      let keycloak = state.keycloak;
      return keycloak ? keycloak.authenticated : false;
    },
    getRealmAccess(state) {
      let keycloak = state.keycloak;
      return keycloak ? keycloak.realmAccess : [];
    },
    isTokenAlive(state) {
      let keycloak = state.keycloak;
      if (
        keycloak &&
        keycloak.tokenParsed &&
        keycloak.tokenParsed.exp &&
        keycloak.timeSkew
      ) {
        return (
          Math.round(
            keycloak.tokenParsed.exp +
              keycloak.timeSkew -
              new Date().getTime() / 1000
          ) > 0
        );
      }
      return false;
    },
    isRefreshTokenAlive(state) {
      let keycloak = state.keycloak;
      if (
        keycloak &&
        keycloak.refreshTokenParsed &&
        keycloak.refreshTokenParsed.exp &&
        keycloak.timeSkew
      ) {
        return (
          Math.round(
            keycloak.refreshTokenParsed.exp +
              keycloak.timeSkew -
              new Date().getTime() / 1000
          ) > 0
        );
      }
      return false;
    },
  },
  actions: {
    initKeycloak(keycloak) {
      this.keycloak = keycloak;
    },
    refreshToken() {
      const keycloak = this.keycloak;
      keycloak
        .updateToken(180)
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
    },
    login(redirectUri) {
      const keycloak = this.keycloak;
      keycloak.login({ redirectUri });
    },
  },
});
