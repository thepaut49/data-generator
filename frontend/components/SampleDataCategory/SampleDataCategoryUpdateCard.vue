<template>
  <section :class="cssClass">
    <h1 class="entity-title">{{ title }}</h1>
    <BaseInput
      cssClass="form-field-horizontal"
      v-model="category.name"
      label="Nom de la catégorie "
    />
    <footer class="card-footer-buttons">
      <button @click="cancel()">Retour</button>
      <button @click="saveCategory()">Sauvegarder</button>
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
