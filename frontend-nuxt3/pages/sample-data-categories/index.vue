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

const router = useRouter();
const store = useSampleDataCategory();
const loading = computed(() => store.loading);

function getData() {
  // load data
  store.getSampleDataCategoriesAction(null).catch((e) => {
    throw createError({
      statusCode: 503,
      statusMessage:
        "Unable to fetch sampleDataCategorys at this time. Please try again.",
    });
  });
}

getData();

const sampleDataCategories = computed(() => [...store.sampleDataCategories]);

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

.sample-data-category-card {
  margin-top: 0.5em; /* Adjust the margin value as needed */
}
</style>
