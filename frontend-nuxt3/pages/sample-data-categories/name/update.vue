<template>
  <SampleDataCategoryUpdateCard
    title="Modification d'une catégorie"
    :category="category"
    :saveCategory="saveCategory"
    :cancel="cancel"
    cssClass="entity-content"
  ></SampleDataCategoryUpdateCard>
</template>
<script setup>
import { useSampleDataCategory } from "../../../store/SampleDataCategory";
import SampleDataCategoryUpdateCard from "~~/components/SampleDataCategory/SampleDataCategoryUpdateCard.vue";
import { useEntityNavigationButtons } from "../../../composables/EntityNavigationButtons";

const store = useSampleDataCategory();
const router = useRouter();

const category = computed(() => {
  return store.selectedSampleDataCategory;
});

function saveCategory() {
  store.updateSampleDataCategoryAction(category.value);
  store.getSampleDataCategoriesAction(null);
  router.push("/sample-data-categories");
}

function cancel() {
  store.getSampleDataCategoriesAction(null);
  router.push("/sample-data-categories");
}

const navigationButtons = useEntityNavigationButtons(
  "/sample-data-categories/",
  category.value.name
);
</script>
<script>
definePageMeta({
  layout: "entity",
});
</script>

<style scoped></style>
