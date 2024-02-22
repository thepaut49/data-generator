<template>
  <section class="entity-content" v-if="sampleData">
    <h1 class="entity-title">
      {{ title }}
    </h1>
    <history-component :entity="sampleData"></history-component>
  </section>
</template>
<script setup lang="ts">
import { computed } from "vue";
import { useSampleData } from "../../../store/SampleData";
import { useTitle } from "@vueuse/core";
import HistoryComponent from "~~/components/commons/HistoryComponent.vue";

definePageMeta({
  layout: "sample-data",
});

const title = ref("Historique de la catégorie");

const store = useSampleData();
const sampleData = computed(() => {
  return { ...store.selectedSampleData };
});

useTitle(title);
</script>
