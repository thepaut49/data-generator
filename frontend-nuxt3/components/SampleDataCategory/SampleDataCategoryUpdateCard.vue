<template>
  <section :class="cssClass">
    <h1 class="entity-title">{{ title }}</h1>

    <div class="field-label">
      <BaseInput v-model="category.name" label="Nom de la catégorie " />
    </div>

    <footer class="card-footer-buttons">
      <button @click="saveCategory()">Sauvegarder</button>
      <button @click="cancel()">Retour</button>
    </footer>
  </section>
</template>
<script setup lang="ts">
import BaseInput from "../commons/BaseInput.vue";
import { useTitle } from "@vueuse/core";

const props = defineProps({
  title: {
    type: String,
    required: true,
  },
  saveCategory: {
    type: Function,
    required: true,
  },
  cancel: {
    type: Function,
    required: true,
  },
  category: {
    type: Object as () => SampleDataCategory, // Type assertion
    required: true,
  },
  cssClass: {
    type: String,
    required: true,
  },
});

useTitle(props.title);
</script>
