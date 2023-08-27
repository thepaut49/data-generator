<template>
  <section class="entity-page" v-if="sampleDataCategory">
    <h1 class="title">Catégorie : {{ sampleDataCategory.name }}</h1>

    <div class="field-label">
      <BaseLabel
        :modelValue="sampleDataCategory.modifiedAt"
        label="Modifié le : "
      />
    </div>
    <div class="field-label">
      <BaseLabel
        :modelValue="sampleDataCategory.modifiedBy"
        label="Modifié par : "
      />
    </div>
    <div class="field-label">
      <BaseLabel :modelValue="sampleDataCategory.version" label="Version : " />
    </div>
  </section>
</template>
<script setup>
import BaseLabel from "../../components/commons/BaseLabel.vue";
import { computed } from "vue";
import { useSampleDataCategory } from "../../store/SampleDataCategory";

const store = useSampleDataCategory();
const sampleDataCategory = computed(() => {
  return { ...store.selectedSampleDataCategory };
});
</script>
<script>
definePageMeta({
  layout: "entity",
});
</script>

<style scoped></style>
