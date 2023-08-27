<template>
  <aside class="sidebar">
    <button class="sidebar-button">
      <span>Détails</span>
    </button>
    <button class="sidebar-button">
      <span>Modifier</span>
    </button>
    <button class="sidebar-button">
      <span>Historique</span>
    </button>
  </aside>
</template>

<style scoped>
.sidebar-button {
  justify-content: center;
  height: 2em;
  border-radius: 5px;
  background: linear-gradient(to right, #16c0b0, #84cf6a);
  font-size: 1em;
  color: white;
  border: none;
  width: 100%;
  margin-bottom: 0.5em;
}

.sidebar-button:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2), 0 1px 15px 0 rgba(0, 0, 0, 0.19);
  font-weight: bold;
  cursor: pointer;
}
</style>
