<template>
  <section class="app-section-entity">
    <Navbar />
    <EntitySideBar />
    <slot />
  </section>
</template>

<style scoped>
.app-section-entity {
  width: 100%;
  height: 100%;
  display: grid;
  align-content: stretch;
  grid-template-rows: min-content 1fr;
  grid-template-columns: 1fr 1fr 10fr;
  grid-template-areas:
    "navbar navbar navbar navbar navbar navbar navbar navbar navbar navbar navbar navbar"
    "sidebar sidebar entity-page entity-page entity-page entity-page entity-page entity-page entity-page entity-page entity-page entity-page";
}

nav {
  grid-area: navbar;
}
.sidebar {
  padding: 0.5em;
  grid-area: sidebar;
  background-color: var(--third-bg-color);
  color: var(--fourth-text-color);
  display: flex;
  flex-direction: column;
}
.entity-page {
  grid-area: entity-page;
  padding: 0.5rem;
}
</style>
