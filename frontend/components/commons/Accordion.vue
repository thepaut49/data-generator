<template>
  <section class="accordion-section">
    <button
      class="accordion"
      @click="toggleSection"
      :class="{ active: isActive }"
    >
      {{ sectionTitle }}
    </button>
    <div class="panel" v-show="isActive">
      <slot></slot>
    </div>
  </section>
</template>

<script setup>
import { ref, defineProps } from "vue";

const { sectionTitle } = defineProps(["sectionTitle"]);
const isActive = ref(false);

const toggleSection = () => {
  isActive.value = !isActive.value;
};
</script>

<style>
.accordion-section {
  border-radius: var(--border-radius);
  box-shadow: 0 4px 4px -2px rgba(0, 0, 0, 0.5);
  overflow: hidden;
}

.accordion {
  background-color: #eee;
  color: var(--third-text-color) #444;
  cursor: pointer;
  padding: 18px;
  width: 100%;
  border: none;
  text-align: left;
  outline: none;
  font-size: 15px;
  transition: 0.4s;
  font-weight: bold;
}

.active,
.accordion:hover {
  background-color: #ccc;
}

.panel {
  padding: 0 18px;
  background-color: white;
  overflow: hidden;
}

.panel > * {
  margin: 0;
}
</style>
