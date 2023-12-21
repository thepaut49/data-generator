<script setup lang="ts">
import Navbar from "~/components/Navbar.vue";
</script>
<template>
  <section class="app-section">
    <Navbar />
    <slot />
  </section>
</template>

<style>
.app-section {
  position: relative;
  --tw-bg-opacity: 1;
  background-color: rgb(243 244 246 / var(--tw-bg-opacity));
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

.page {
  padding-left: 0.5rem;
  padding-right: 0.5rem;
  flex: 2;
  background-color: var(--fifth-bg-color);
}
</style>
