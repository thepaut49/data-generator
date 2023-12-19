<template>
  <nav>
    <section class="nav-section">
      <nuxt-link href="/">Home</nuxt-link>
      <nuxt-link href="/sample-data-categories">Catégorie de données</nuxt-link>
      <!--nuxt-link href="/protected/globally">Globally Protected</!--nuxt-link>
      <nuxt-link-- href="/protected/locally">Locally Unprotected</nuxt-link-->
    </section>
    <AuthenticationStatus />
  </nav>
</template>

<style scoped>
nav {
  width: 100%;
  font-size: large;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: space-between;
  flex-wrap: wrap;
  background-color: var(--main-bg-color);
}

.nav-section {
  display: flex;
  align-items: center;
  justify-content: space-around;
  flex-wrap: wrap;
  flex-grow: 1;
}

a {
  color: var(--main-text-color);
  border-radius: 5px;
  padding: 0.2rem;
  text-decoration: none;
  flex-grow: 1;
}

a:hover {
  background-color: aqua;
}

nav a.router-link-exact-active {
  color: var(--third-text-color);
}

nav a.router-link-exact-active:hover {
  background-color: transparent;
}
</style>
