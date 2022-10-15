<template>
  <nav>
    <RouterLink to="/">Home</RouterLink>
    <RouterLink to="/Sample-data-categories">Sample data categories</RouterLink>
    <RouterLink to="/about">About</RouterLink>
  </nav>
</template>

<script setup></script>

<style scoped>
nav a.router-link-exact-active {
  color: var(--color-text);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}

nav a {
  display: inline-block;
  padding: 0 1rem;
  border-left: 1px solid var(--color-border);
}

nav a:first-of-type {
  border: 0;
}

nav {
  width: 100%;
  font-size: 12px;
  text-align: center;
  margin-top: 2rem;
  margin-bottom: 2rem;
}
</style>
