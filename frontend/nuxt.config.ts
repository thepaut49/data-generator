// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  modules: ["@sidebase/nuxt-auth", "@pinia/nuxt"],
  auth: {
    isEnabled: true,
    baseUrl: process.env.AUTH_ORIGIN,
    provider: {
      type: "authjs",
    },
    globalAppMiddleware: {
      isEnabled: true,
    },
  },
  css: [
    "~/assets/css/global.css",
    "@fortawesome/fontawesome-svg-core/styles.css",
  ],
  devtools: { enabled: true },
  imports: {
    dirs: ["./stores"],
  },
  pinia: {
    storesDirs: ["./stores/**"],
  },
});
