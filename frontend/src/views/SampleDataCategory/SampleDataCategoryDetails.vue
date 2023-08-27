<script>
export default {
  name: "SampleDataCategoryDetail",
  inheritAttrs: false,
};
</script>

<script setup>
import { useRouter } from "vue-router";
import { ref, computed } from "vue";
import BaseLabel from "../../components/commons/BaseLabel.vue";
//import BaseInput from "../../components/commons/BaseInput.vue";

const router = useRouter();
const sectionToShow = ref("currentState");

const title = "Détails de la catégorie";

const showSectionCurrentState = computed(() => {
  return sectionToShow.value === "currentState";
});
const showSectionPreviousChanges = computed(() => {
  return sectionToShow.value === "previousChanges";
});

const showSection = function (sectionName) {
  return (sectionToShow.value = sectionName);
};

const pagePrecedente = function () {
  router.push({ name: "SampleDataCategories" });
};
</script>

<template>
  <div class="entity-form-container">
    <aside class="entity-form-aside">
      <button @click="showSection('currentState')">Etat actuel</button>
      <button @click="showSection('previousChanges')">
        Derniers changements
      </button>
    </aside>
    <section class="entity-form">
      <header class="entity-form-header">
        <h2>{{ title }}</h2>
      </header>
      <main>
        <fieldset class="entity-form-content" v-show="showSectionCurrentState">
          <legend>Etat actuel</legend>
          <div class="field-label">
            <BaseLabel :modelValue="category.id" label="Id" />
          </div>
          <div class="field">
            <BaseLabel
              :modelValue="category.name"
              label="Nom de la catégorie"
            />
          </div>
          <div class="field-label">
            <BaseLabel :modelValue="category.modifiedAt" label="Modifié le" />
          </div>
          <div class="field-label">
            <BaseLabel :modelValue="category.modifiedBy" label="Modifié par" />
          </div>
          <div class="field-label">
            <BaseLabel :modelValue="category.version" label="Version" />
          </div>
        </fieldset>
        <fieldset
          class="category-form-content"
          v-show="showSectionPreviousChanges"
        >
          <legend>Derniers changements</legend>
          <p>TODO</p>
        </fieldset>
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
