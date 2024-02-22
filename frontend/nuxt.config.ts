// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  runtimeConfig: {
    // Public keys that are exposed to the client
    public: {
      BACKEND_API_URL: process.env.BACKEND_API_URL || "http://localhost:9090",
    },
  },
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
  build: {
    transpile: [
      "@fortawesome/fontawesome-svg-core",
      "@fortawesome/free-solid-svg-icons",
      "@fortawesome/vue-fontawesome",
    ],
  },
});
