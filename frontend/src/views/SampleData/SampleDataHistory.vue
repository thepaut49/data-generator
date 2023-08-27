<template>
  <section>
    <header>
      <h3>Historique</h3>
    </header>
    <main>{{ data.key }}</main>
  </section>
</template>

<script setup>
import { computed } from "vue";
import { useSampleDataStore } from "../../stores/SampleDataStore";

const store = useSampleDataStore();
const data = computed(() => {
  return store.selectedSampleData;
});
</script>
<style scoped></style>
