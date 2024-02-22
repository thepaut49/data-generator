<template>
  <section class="entity-content" v-if="sampleData">
    <h1 class="entity-title">{{ title }}</h1>

    <BaseLabel
      label="Catégorie de la donnée : "
      :modelValue="sampleData.categoryName"
      cssClass="form-field-horizontal"
    />

    <BaseLabel
      cssClass="form-field-horizontal"
      :modelValue="sampleData.key"
      label="Clé : "
    />

    <BaseCheckbox
      :modelValue="sampleData.isBlobTypeValue"
      cssClass="form-field-horizontal"
      label="Donnée volumineuse : "
      disabled
    />

    <BaseLabel
      v-if="sampleData.isBlobTypeValue"
      cssClass="form-field-horizontal"
      :modelValue="sampleData.blobValue"
      label="Valeur de la donnée volumineuse : "
    />

    <BaseLabel
      v-else
      cssClass="form-field-horizontal"
      :modelValue="sampleData.value"
      label="Valeur de la donnée : "
    />

    <BaseLabel
      cssClass="form-field-horizontal"
      :modelValue="sampleData.modifiedAt"
      label="Modifié le : "
    />

    <BaseLabel
      cssClass="form-field-horizontal"
      :modelValue="sampleData.modifiedBy"
      label="Modifié par : "
    />

    <BaseLabel
      cssClass="form-field-horizontal"
      :modelValue="sampleData.version"
      label="Version : "
    />
  </section>
</template>
<script setup lang="ts">
import BaseLabel from "../../../components/commons/BaseLabel.vue";
import BaseCheckbox from "../../../components/commons/BaseCheckbox.vue";
import { computed, ref } from "vue";
import { useSampleData } from "../../../store/SampleData";
import { useTitle } from "@vueuse/core";

definePageMeta({
  layout: "sample-data",
});

const title = ref("Détails de la donnée");

const store = useSampleData();
const sampleData = computed(() => {
  return { ...store.selectedSampleData };
});

useTitle(title);
</script>
