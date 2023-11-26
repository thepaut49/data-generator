<template>
  <section class="app-section-entity">
    <Navbar />
    <slot />
  </section>
</template>

<style>
.app-section-entity {
  width: 100%;
  height: 100%;
  display: flex;
  flex-direction: column;
}

nav {
  width: 100%;
}

.entity-page {
  background-color: blue;
  display: flex;
  flex-direction: row;
}

.entity-sidebar {
  padding: 0.5em;
  background-color: var(--third-bg-color);
  color: var(--fourth-text-color);
  display: flex;
  flex-direction: column;
  flex: 1;
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
