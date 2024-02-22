<template>
  <section class="page">
    <header>
      <h1>Liste de données</h1>
      <button @click="createNewData">
        <font-awesome-icon icon="fa-plus" />
      </button>
      <button @click="reloadNewData">
        <font-awesome-icon icon="fa-sync" />
      </button>
    </header>

    <Accordion
      class="sample-data-criteria"
      sectionTitle="Critères de recherche"
      v-if="!loadingCategory"
    >
      <BaseInput
        label="Nom de la donnée"
        v-model="keyCriteria"
        cssClass="form-field-horizontal"
      />

      <div class="form-field-horizontal">
        <label for="categoryCriteriaId">Catégorie de la donnée</label>
        <select v-model="categoryCriteria" id="categoryCriteriaId">
          <option
            v-for="option in options"
            :key="option.label"
            :value="option.value"
            :selected="isSelected(option)"
          >
            {{ option.label }}
          </option>
        </select>
      </div>

      <BaseCheckbox
        label="Donnée volumineuse"
        v-model="isBlobTypeValueCriteria"
        cssClass="form-field-horizontal"
      />

      <BaseInput
        label="Valeur de la donnée"
        v-model="valueCriteria"
        cssClass="form-field-horizontal"
      />

      <footer class="card-footer-buttons">
        <button @click="searchData()">Rechercher</button>
      </footer>
    </Accordion>

    <ErrorComponent title="Erreur" :message="error" />

    <div
      v-if="!loading && filteredSampleDatas && filteredSampleDatas.length > 0"
    >
      <SampleDataCard
        v-for="(sampleData, index) in filteredSampleDatas"
        :key="index"
        :data="sampleData"
        :data-index="index"
        class="sample-data-card"
        @askToDeleteDataEvent="handleDeleteEvent(sampleData)"
      />
    </div>
  </section>
</template>

<script lang="ts">
// declare additional options
export default {
  name: "SampleDatas",
  head() {
    return {
      title: "SampleData Listing",
    };
  },
  components: {
    SampleDataCard,
  },
};
</script>

<script lang="ts" setup>
import { faPlus, faSync } from "@fortawesome/free-solid-svg-icons";
import SampleDataCard from "@/components/SampleData/SampleDataCard.vue";
import { useSampleData } from "../../store/SampleData";
import { useSampleDataCategory } from "../../store/SampleDataCategory";
import { computed } from "vue";
import BaseInput from "~/components/commons/BaseInput.vue";
import BaseCheckbox from "~/components/commons/BaseCheckbox.vue";
import Accordion from "~/components/commons/Accordion.vue";
import ErrorComponent from "~/components/commons/ErrorComponent.vue";
import { storeToRefs } from "pinia";

const router = useRouter();
const store = useSampleData();
const storeCategory = useSampleDataCategory();
let loading = ref(true);
let loadingCategory = ref(true);
let error: Ref<any> = ref(null);

async function getCategories() {
  try {
    loadingCategory.value = true;
    await storeCategory.getAllSampleDataCategoriesAction(false);
  } catch (errorStore) {
    error.value = errorStore;
  } finally {
    loadingCategory.value = false;
  }
}

async function getDatas() {
  try {
    loading.value = true;
    await store.getAllSampleDatasAction(false);
  } catch (errorStore) {
    error.value = errorStore;
  } finally {
    loading.value = false;
  }
}

await getCategories();
await getDatas();

const { sampleDatas, filteredSampleDatas } = storeToRefs(store);

const keyCriteria = ref("");
const categoryCriteria = ref(-1);
const valueCriteria = ref("");
const isBlobTypeValueCriteria = ref(false);
const localSampleDatas = ref([...sampleDatas.value]);

const options = computed(() => {
  const categories = [...storeCategory.sampleDataCategories];
  let optionList: Array<Option> = categories.map((sampleDataCategory) => ({
    label: sampleDataCategory.name,
    value: sampleDataCategory.id,
  }));
  optionList.unshift({
    label: "",
    value: -1,
  });
  return optionList;
});

function createNewData() {
  store.createNewSampleDataAction();
  router.push("/sample-datas/create");
}

async function reloadNewData() {
  keyCriteria.value = "";
  isBlobTypeValueCriteria.value = false;
  valueCriteria.value = "";
  categoryCriteria.value = -1;
  await getCategories();
  await getDatas();
}

async function searchData() {
  try {
    loading.value = true;
    await store.getSampleDatasAction(
      categoryCriteria.value,
      keyCriteria.value,
      isBlobTypeValueCriteria.value,
      valueCriteria.value,
      false
    );
  } catch (errorCatch) {
    error.value = errorCatch;
  } finally {
    loading.value = false;
  }
}

function handleDeleteEvent(sampleData: SampleData) {
  store.deleteSampleDataAction(sampleData, false);
}

function isSelected(option: Option) {
  return (
    categoryCriteria.value != undefined &&
    option.value === categoryCriteria.value
  );
}
</script>
<style scoped>
/* Your other styles here */
.sample-data-criteria {
  margin: var(--min-margin); /* Adjust the margin value as needed */
}

.sample-data-card {
  margin-top: 0.5em; /* Adjust the margin value as needed */
}
</style>
