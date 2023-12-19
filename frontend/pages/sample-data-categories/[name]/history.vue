<template>
  <section class="entity-content" v-if="sampleDataCategory">
    <h1 class="entity-title">
      {{ title }}
    </h1>
    <history-component :entity="sampleDataCategory"></history-component>
  </section>
</template>
<script setup lang="ts">
import { computed } from "vue";
import { useSampleDataCategory } from "../../../store/SampleDataCategory";
import { useTitle } from "@vueuse/core";
import HistoryComponent from "~~/components/commons/HistoryComponent.vue";

definePageMeta({
  layout: "sample-data-category",
});

const title = ref("Historique de la catégorie");

const store = useSampleDataCategory();
const sampleDataCategory = computed(() => {
  return { ...store.selectedSampleDataCategory };
});

useTitle(title);
</script>
