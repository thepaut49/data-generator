<template>
  <div>
    <h3>Authentication Overview</h3>
    <p>See all available authentication & session information below.</p>
    <pre v-if="status"><span>Status:</span> {{ status }}</pre>
    <pre v-if="data"><span>Data:</span> {{ data }}</pre>
    <pre v-if="csrfToken"><span>CSRF Token:</span> {{ csrfToken }}</pre>
    <pre v-if="providers"><span>Providers:</span> {{ providers }}</pre>
    <div>{{ token || "no token present, are you logged in?" }}</div>
  </div>
</template>

<script lang="ts" setup>
definePageMeta({ auth: false });
const { data, status, getCsrfToken, getProviders } = useAuth();
const providers = await getProviders();
const csrfToken = await getCsrfToken();
const headers = useRequestHeaders(["cookie"]) as HeadersInit;
const { data: token } = await useFetch("/api/token", { headers });
</script>

<style scoped></style>
