<script setup>
import { inject } from "vue";
import NavMenu from "./components/NavMenu.vue";
import { useUserStore } from "./stores/UserStore";

const keycloak = inject("keycloak");

if (keycloak) {
  console.log("token = " + keycloak);
}
const userStore = useUserStore();
userStore.initKeycloak(keycloak);
</script>

<template>
  <header>
    <NavMenu />
  </header>

  <RouterView />
</template>

<style scoped></style>
