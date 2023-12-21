<template>
  <section class="app-section">
    <Navbar />
    <section class="app-section-entity">
      <EntitySideBar :links="sidebarLinks" cssClass="entity-sidebar" />
      <slot />
    </section>
  </section>
</template>

<script setup lang="ts">
import EntitySideBar from "~/components/EntitySideBar.vue";
import { useRoute } from "vue-router";

const route = useRoute();

const sidebarLinks = ref([
  {
    to: `/sample-data-categories/${route.params.name}`,
    label: "Details",
  },
  {
    to: `/sample-data-categories/${route.params.name}/update`,
    label: "Update",
  },
  {
    to: `/sample-data-categories/${route.params.name}/history`,
    label: "History",
  },
]);
</script>

<style>
.app-section {
  /*position: relative;*/
  --tw-bg-opacity: 1;
  background-color: rgb(243 244 246 / var(--tw-bg-opacity));
  /*width: 100%;*/
  height: 100%;
  display: flex;
  flex-direction: column;
}

.app-section-entity {
  width: 100%;
  height: 100%;
  display: flex;
}

nav {
  width: 100%;
}

.entity-page {
  background-color: blue;
  display: flex;
}

.entity-sidebar {
  padding: 0.5em;
  background-color: var(--third-bg-color);
  color: var(--fourth-text-color);
  display: flex;
  flex-direction: column;
  flex: 1;
  text-align: center;
}
.entity-content {
  padding: 0.5rem;
  flex: 4;
}

.entity-title {
  margin: auto;
  font-size: x-large;
}
</style>
