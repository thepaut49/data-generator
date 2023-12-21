<script setup lang="ts">
import { faUser } from "@fortawesome/free-solid-svg-icons";

const { status, data, signOut, signIn } = useAuth();
</script>

<template>
  <section class="login-section">
    <p style="margin-right: 0.1em" v-if="status === 'authenticated'">
      {{ data?.user?.name }} <font-awesome-icon :icon="['fas', 'user']" />
    </p>
    <button
      v-if="status === 'authenticated'"
      @click="signOut({ callbackUrl: '/' })"
    >
      <span>Logout</span>
    </button>
    <button v-else @click="signIn('keycloak')">
      <span>Login</span>
    </button>
  </section>
</template>

<style scoped>
.login-section {
  display: flex;
  background-color: green;
  color: white;
}
</style>
