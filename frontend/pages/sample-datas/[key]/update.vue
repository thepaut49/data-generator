<template>
  <SampleDataUpdateCard
    title="Modification d'une donnée"
    :data="data"
    :saveData="saveData"
    :cancel="cancel"
    cssClass="entity-content"
  ></SampleDataUpdateCard>
</template>
<script setup lang="ts">
import { useSampleData } from "../../../store/SampleData";
import SampleDataUpdateCard from "~~/components/SampleData/SampleDataUpdateCard.vue";

definePageMeta({
  layout: "sample-data",
});

const store = useSampleData();
const router = useRouter();

const data = computed(() => {
  return store.selectedSampleData;
});

function saveData() {
  store.updateSampleDataAction(data.value, false);
  router.push("/sample-datas");
}

function cancel() {
  router.push("/sample-datas");
}
</script>

<style scoped></style>
