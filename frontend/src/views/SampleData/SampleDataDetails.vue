<script>
export default {
  name: "SampleDataDetails",
  inheritAttrs: false,
};
</script>

<script setup>
import { useRouter } from "vue-router";
import { useSampleDataStore } from "../../stores/SampleDataStore";
import { computed } from "vue";
import BaseLabel from "../../components/commons/BaseLabel.vue";
import BaseCheckbox from "../../components/commons/BaseCheckbox.vue";

const router = useRouter();
const store = useSampleDataStore();
const data = computed(() => store.selectedSampleData);

const title = "Détails de la donnée ";

const pagePrecedente = function () {
  router.push({ name: "SampleDatas" });
};
</script>

<template>
  <div class="entity-form-container">
    <section class="entity-form">
      <header class="entity-form-header">
        <h2>{{ title }}</h2>
      </header>
      <main>
        <div class="field-label">
          <BaseLabel v-bind="data.id" label="Id" />
        </div>
        <div class="field">
          <BaseLabel v-bind="data.key" label="Clé" :disabled="true" />
        </div>
        <div class="field">
          <BaseCheckbox
            v-bind="data.isBlobValue"
            label="Valeur de type BLOB"
            :disabled="true"
          />
        </div>
        <div class="field" v-show="!data.isBlobValue">
          <BaseLabel v-bind="data.value" label="Valeur" :disabled="true" />
        </div>
        <div class="field" v-show="data.isBlobValue">
          <BaseLabel
            v-bind="data.blobValue"
            label="Valeur du BLOB"
            :disabled="true"
          />
        </div>
        <div class="field-label">
          <BaseLabel v-bind="data.modifiedAt" label="Modifié le" />
        </div>
        <div class="field-label">
          <BaseLabel v-bind="data.modifiedBy" label="Modifié par" />
        </div>
        <div class="field-label">
          <BaseLabel v-bind="data.version" label="Version" />
        </div>
      </main>
      <footer class="entity-form-footer">
        <button @click="pagePrecedente()">
          <span>Retour</span>
        </button>
      </footer>
    </section>
  </div>
</template>

<style>
@import "../../assets/entityEditForm.css";
</style>
