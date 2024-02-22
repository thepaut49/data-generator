<template>
  <section class="page">
    <header>
      <h1>Catégories de données</h1>
      <button @click="createNewCategorie">
        <font-awesome-icon icon="fa-plus" />
      </button>
      <button @click="reloadCategories">
        <font-awesome-icon icon="fa-sync" />
      </button>
    </header>

    <Accordion
      class="sample-data-category-criteria"
      sectionTitle="Critères de recherche"
    >
      <BaseInput
        label="Nom de la catégorie"
        v-model="nameCriteria"
        cssClass="form-field-horizontal"
      />

      <footer class="card-footer-buttons">
        <button @click="searchCategory()">Rechercher</button>
      </footer>
    </Accordion>

    <ErrorComponent title="Erreur" :message="error" />

    <div
      v-if="
        !loading &&
        filteredSampleDataCategories &&
        filteredSampleDataCategories.length > 0
      "
    >
      <SampleDataCategoryCard
        v-for="(sampleDataCategory, index) in filteredSampleDataCategories"
        :key="index"
        :category="sampleDataCategory"
        :data-index="index"
        class="sample-data-category-card"
        @askToDeleteCategoryEvent="handleDeleteEvent(sampleDataCategory)"
      />
    </div>
  </section>
</template>

<script lang="ts">
// declare additional options
export default {
  name: "SampleDataCategories",
  head() {
    return {
      title: "SampleDataCategory Listing",
    };
  },
  components: {
    SampleDataCategoryCard,
  },
};
</script>

<script lang="ts" setup>
import { faPlus, faSync } from "@fortawesome/free-solid-svg-icons";
import SampleDataCategoryCard from "@/components/SampleDataCategory/SampleDataCategoryCard.vue";
import { useSampleDataCategory } from "../../store/SampleDataCategory";
import BaseInput from "~/components/commons/BaseInput.vue";
import Accordion from "~/components/commons/Accordion.vue";
import ErrorComponent from "~/components/commons/ErrorComponent.vue";
import { storeToRefs } from "pinia";

const router = useRouter();
const store = useSampleDataCategory();
let loading = ref(true);
let error: Ref<any> = ref(null);

async function getCategories() {
  try {
    loading.value = true;
    await store.getAllSampleDataCategoriesAction(false);
  } catch (errorStore) {
    error.value = errorStore;
  } finally {
    loading.value = false;
  }
}

await getCategories();

const { filteredSampleDataCategories } = storeToRefs(store);
const nameCriteria = ref("");

function createNewCategorie() {
  store.createNewSampleDataCategoryAction();
  router.push("/sample-data-categories/create");
}

async function reloadCategories() {
  nameCriteria.value = "";
  await store.getAllSampleDataCategoriesAction(false);
}

const handleDeleteEvent = (sampleDataCategory: SampleDataCategory) => {
  store.deleteSampleDataCategoryAction(sampleDataCategory, false);
  router.push("/sample-data-categories");
};

async function searchCategory() {
  try {
    loading.value = true;
    await store.getSampleDataCategoriesAction(nameCriteria.value, false);
  } catch (errorCatch) {
    error.value = errorCatch;
  } finally {
    loading.value = false;
  }
}
</script>
<style scoped>
/* Your other styles here */
.sample-data-category-criteria {
  margin: var(--min-margin); /* Adjust the margin value as needed */
}

.sample-data-category-card {
  margin-top: 0.5em; /* Adjust the margin value as needed */
}
</style>
