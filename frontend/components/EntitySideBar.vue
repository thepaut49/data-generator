<template>
  <aside :class="cssClass">
    <nuxt-link v-for="(link, index) in links" :key="index" :to="link.to">{{
      link.label
    }}</nuxt-link>
  </aside>
</template>

<script setup lang="ts">
const router = useRouter();
defineProps({
  links: {
    type: Array<NavigationButton>,
    required: true,
  },
  cssClass: {
    type: String,
    required: true,
  },
});
</script>

<style scoped>
a {
  justify-content: center;
  height: 2em;
  border-radius: 5px;
  background: linear-gradient(to right, #16c0b0, #84cf6a);
  font-size: 1em;
  color: white;
  border: none;
  width: 100%;
  margin-bottom: 0.5em;
  text-decoration: none;
}

a:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2), 0 1px 15px 0 rgba(0, 0, 0, 0.19);
  font-weight: bold;
  cursor: pointer;
}

nav a.router-link-exact-active {
  color: var(--third-text-color);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}
</style>
