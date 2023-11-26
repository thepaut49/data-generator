<template>
  <section class="entity-page">
    <EntitySideBar cssClass="entity-sidebar" :buttons="navigationButtons" />
    <NuxtPage />
  </section>
</template>
<script setup lang="ts">
import BaseLabel from "../../components/commons/BaseLabel.vue";
import { computed } from "vue";
import { useSampleDataCategory } from "../../store/SampleDataCategory";
import { useEntityNavigationButtons } from "../../composables/EntityNavigationButtons";

const store = useSampleDataCategory();
const sampleDataCategory = computed(() => {
  return { ...store.selectedSampleDataCategory };
});

const navigationButtons = useEntityNavigationButtons(
  "/sample-data-categories/",
  sampleDataCategory.value.name
);
</script>
<script lang="ts">
definePageMeta({
  layout: "entity",
});
</script>

<style scoped></style>
