<template>
  <SampleDataCategoryUpdateCard
    title="Création d'une nouvelle catégorie"
    :category="category"
    :saveCategory="saveNewCategorie"
    :cancel="cancel"
    cssClass="page"
  ></SampleDataCategoryUpdateCard>
</template>
<script setup>
import { useSampleDataCategory } from "../../store/SampleDataCategory";
import SampleDataCategoryUpdateCard from "~~/components/SampleDataCategory/SampleDataCategoryUpdateCard.vue";
const store = useSampleDataCategory();
const router = useRouter();

const category = computed(() => {
  return store.selectedSampleDataCategory;
});

function saveNewCategorie() {
  store.addSampleDataCategoryAction(category.value);
  store.getSampleDataCategoriesAction(null);
  router.push("/sample-data-categories");
}

function cancel() {
  store.getSampleDataCategoriesAction(null);
  router.push("/sample-data-categories");
}
</script>

<style scoped>
.entity-page {
  background-color: blue;
  display: flex;
  flex-direction: row;
}

.entity-sidebar {
  padding: 0.5em;
  background-color: var(--third-bg-color);
  color: var(--fourth-text-color);
  display: flex;
  flex-direction: column;
}
.entity-content {
  padding: 0.5rem;
}
</style>
