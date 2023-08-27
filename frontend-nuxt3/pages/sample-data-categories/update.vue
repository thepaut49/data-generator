<template>
  <section class="entity-page">
    <h1 class="title">Création d'une nouvelle Catégorie</h1>

    <div class="field-label">
      <BaseInput v-model="category.name" label="Nom de la catégorie : " />
    </div>

    <footer>
      <button @click="saveNewCategorie">Créer</button>
      <button @click="retour">Retour</button>
    </footer>
  </section>
</template>
<script setup>
import BaseInput from "../../components/commons/BaseInput.vue";
import { useSampleDataCategory } from "../../store/SampleDataCategory";
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

function retour() {
  store.getSampleDataCategoriesAction(null);
  router.push("/sample-data-categories");
}
</script>
<script>
definePageMeta({
  layout: "entity",
});
</script>

<style scoped></style>
