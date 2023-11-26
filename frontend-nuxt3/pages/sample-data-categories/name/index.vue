<template>
  <section class="entity-content" v-if="sampleDataCategory">
    <h1 class="entity-title">{{ title }}</h1>

    <div class="field-label">
      <BaseLabel :modelValue="sampleDataCategory.name" label="Catégorie : " />
    </div>

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
<script setup lang="ts">
import BaseLabel from "../../../components/commons/BaseLabel.vue";
import { computed } from "vue";
import { useSampleDataCategory } from "../../../store/SampleDataCategory";
import { useTitle } from "@vueuse/core";

const title = ref("Détails de la catégorie");

const store = useSampleDataCategory();
const sampleDataCategory = computed(() => {
  return { ...store.selectedSampleDataCategory };
});

useTitle(title);
</script>
