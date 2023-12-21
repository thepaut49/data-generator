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
    </Accordion>

    <div v-if="!loading">
      <SampleDataCategoryCard
        v-for="(sampleDataCategory, index) in sampleDataCategories"
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
import { computed } from "vue";
import { AxiosError } from "axios";
import BaseInput from "~/components/commons/BaseInput.vue";
import Accordion from "~/components/commons/Accordion.vue";

const { signIn } = useAuth();
const router = useRouter();
const store = useSampleDataCategory();
const loading = computed(() => store.loading);
const nameCriteria = ref("");

function getData() {
  // load data
  store.getSampleDataCategoriesAction(null).catch((error) => {
    console.error(error);
    if (error instanceof AxiosError) {
      if (error.response?.status === 401) {
        signIn();
      }
    } else {
      throw createError({
        statusCode: 503,
        statusMessage:
          "Unable to fetch sampleDataCategorys at this time. Please try again.",
      });
    }
  });
}

getData();

const sampleDataCategories = computed(() => {
  return [...store.sampleDataCategories].filter((sampleDataCategory) =>
    sampleDataCategory.name
      .toLowerCase()
      .includes(nameCriteria.value.toLowerCase())
  );
});

function createNewCategorie() {
  store.createNewSampleDataCategoryAction();
  router.push("/sample-data-categories/create");
}

function reloadCategories() {
  store.getSampleDataCategoriesAction(null);
  router.push("/sample-data-categories");
}

const handleDeleteEvent = (sampleDataCategory: SampleDataCategory) => {
  store.deleteSampleDataCategoryAction(sampleDataCategory);
  router.push("/sample-data-categories");
};
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
