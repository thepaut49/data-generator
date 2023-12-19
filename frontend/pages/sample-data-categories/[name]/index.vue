<template>
  <section class="entity-content" v-if="sampleDataCategory">
    <h1 class="entity-title">{{ title }}</h1>

    <BaseLabel
      cssClass="form-field-horizontal"
      :modelValue="sampleDataCategory.name"
      label="Catégorie : "
    />

    <BaseLabel
      cssClass="form-field-horizontal"
      :modelValue="sampleDataCategory.modifiedAt"
      label="Modifié le : "
    />

    <BaseLabel
      cssClass="form-field-horizontal"
      :modelValue="sampleDataCategory.modifiedBy"
      label="Modifié par : "
    />

    <BaseLabel
      cssClass="form-field-horizontal"
      :modelValue="sampleDataCategory.version"
      label="Version : "
    />
  </section>
</template>
<script setup lang="ts">
import BaseLabel from "../../../components/commons/BaseLabel.vue";
import { computed, ref } from "vue";
import { useSampleDataCategory } from "../../../store/SampleDataCategory";
import { useTitle } from "@vueuse/core";

definePageMeta({
  layout: "sample-data-category",
});

const title = ref("Détails de la catégorie");

const store = useSampleDataCategory();
const sampleDataCategory = computed(() => {
  return { ...store.selectedSampleDataCategory };
});

useTitle(title);
</script>
