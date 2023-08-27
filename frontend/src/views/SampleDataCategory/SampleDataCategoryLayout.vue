<template>
  <section v-if="category">
    <h2>{{ category.name }}</h2>
    <aside class="entity-form-aside">
      <router-link :to="{ name: 'SampleDataCategoryDetails' }"
        >Détails</router-link
      >
      <router-link :to="{ name: 'SampleDataCategoryHistory' }"
        >Historique</router-link
      >
      <router-link :to="{ name: 'SampleDataCategoryEdit' }"
        >Modifier</router-link
      >
    </aside>
    <router-view :category="category" />
  </section>
</template>

<script setup>
import { useSampleDataCategoryStore } from "../../stores/SampleDataCategoryStore";
import { reactive } from "vue";

const props = defineProps({
  categoryName: {
    type: String,
    default: "",
  },
});

let category = reactive({});

const store = useSampleDataCategoryStore();
store
  .getSampleDataCategoryAction(props.categoryName)
  .then((categoryItem) => (category = categoryItem))
  .catch((error) => {
    console.error(error);
  });
</script>
