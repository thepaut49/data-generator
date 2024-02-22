<template>
  <SampleDataUpdateCard
    title="Création d'une nouvelle donnée"
    :data="data"
    :saveData="saveNewData"
    :cancel="cancel"
    cssClass="page"
  />
</template>
<script setup lang="ts">
import { useSampleData } from "../../store/SampleData";
import SampleDataUpdateCard from "~~/components/SampleData/SampleDataUpdateCard.vue";
import { handleErrorFromStore } from "../../utils/StoreUtils";

const store = useSampleData();
const router = useRouter();

const data = computed(() => {
  return store.selectedSampleData;
});

async function saveNewData() {
  if (data.value) {
    try {
      await store.addSampleDataAction(data.value, false);
    } catch (errorStore) {
      handleErrorFromStore(errorStore);
    }
    router.push("/sample-datas");
  }
}

async function cancel() {
  try {
    await store.getAllSampleDatasAction(false);
  } catch (errorStore) {
    handleErrorFromStore(errorStore);
  }
  router.push("/sample-datas");
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
