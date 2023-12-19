<script setup lang="ts">
const { status, data, signOut, signIn } = useAuth();
</script>

<template>
  <section class="login-section">
    <p v-if="status === 'authenticated'">
      Authenticated as {{ data?.user?.name }}!
    </p>
    <p v-else>Not logged in</p>
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
