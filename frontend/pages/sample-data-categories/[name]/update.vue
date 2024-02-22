<template>
  <SampleDataCategoryUpdateCard
    title="Modification d'une catégorie"
    :category="category"
    :saveCategory="saveCategory"
    :cancel="cancel"
    cssClass="entity-content"
  ></SampleDataCategoryUpdateCard>
</template>
<script setup lang="ts">
import { useSampleDataCategory } from "../../../store/SampleDataCategory";
import SampleDataCategoryUpdateCard from "~~/components/SampleDataCategory/SampleDataCategoryUpdateCard.vue";

definePageMeta({
  layout: "sample-data-category",
});

const store = useSampleDataCategory();
const router = useRouter();

const category = computed(() => {
  return store.selectedSampleDataCategory;
});

function saveCategory() {
  store.updateSampleDataCategoryAction(category.value, false);
  router.push("/sample-data-categories");
}

function cancel() {
  router.push("/sample-data-categories");
}
</script>

<style scoped></style>
