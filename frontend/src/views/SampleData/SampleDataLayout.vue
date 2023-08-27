<template>
  <section v-if="data">
    <h2>{{ data.key }}</h2>
    <aside class="entity-form-aside">
      <router-link :to="{ name: 'SampleDataDetails' }">Détails</router-link>
      <router-link :to="{ name: 'SampleDataHistory' }">Historique</router-link>
      <router-link :to="{ name: 'SampleDataEdit' }">Modifier</router-link>
    </aside>
    <router-view :data="data" />
  </section>
</template>

<script setup>
import { useSampleDataStore } from "../../stores/SampleDataStore";
import { computed } from "vue";

const props = defineProps({
  keyProp: {
    type: String,
    default: "",
  },
});

const store = useSampleDataStore();
store.getSampleDataAction(props.categoryName, props.keyProp).catch((error) => {
  console.error(error);
});

const data = computed(() => {
  return store.selectedSampleData;
});
</script>
