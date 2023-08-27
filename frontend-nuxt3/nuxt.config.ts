// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  modules: ["@sidebase/nuxt-auth", "@pinia/nuxt"],
  auth: {
    origin: "http://127.0.0.1:3000",
    enableGlobalAppMiddleware: true,
  },
  css: [
    "~/assets/css/global.css",
    "@fortawesome/fontawesome-svg-core/styles.css",
  ],
  imports: {
    dirs: ["./stores"],
  },
  pinia: {
    autoImports: ["defineStore", "acceptHMRUpdate"],
  },
});
