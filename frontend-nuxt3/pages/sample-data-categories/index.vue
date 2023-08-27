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

    <div v-if="sampleDataCategories">
      <SampleDataCategoryCard
        v-for="(sampleDataCategory, index) in sampleDataCategories"
        :key="index"
        :category="sampleDataCategory"
        :data-index="index"
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
let loading = ref(false);

function getData() {
  const now = new Date();
  const oneHoursInMilliseconds = 3600000;
  const numberOfMillisecondsFromLastLoading =
    now.getTime() - store.lastLoadingDate.getTime();
  if (true || numberOfMillisecondsFromLastLoading > oneHoursInMilliseconds) {
    // load data
    store.getSampleDataCategoriesAction(null).catch((e) => {
      throw createError({
        statusCode: 503,
        statusMessage:
          "Unable to fetch sampleDataCategorys at this time. Please try again.",
      });
    });
  }
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
</script>
