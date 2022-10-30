<script>
export default {
  name: "SampleDataCategoryDetail",
  inheritAttrs: false,
};
</script>

<script setup>
import { useRouter } from "vue-router";
import { useSampleDataCategoryStore } from "../../stores/SampleDataCategoryStore";
import { ref, computed } from "vue";
import BaseLabel from "../../components/commons/BaseLabel.vue";
import BaseInput from "../../components/commons/BaseInput.vue";

const emptyError = {
  name: "",
};

const props = defineProps({
  name: {
    type: String,
    default: "",
  },
  visuMode: {
    type: Boolean,
    default: false,
  },
});

const router = useRouter();
const store = useSampleDataCategoryStore();
const sectionToShow = ref("currentState");
const error = ref(emptyError);

const isAddMode = computed(() => {
  return !props.name;
});

const isVisuMode = ref(props.visuMode);

const title = computed(() => {
  let titleVar = "";
  if (isAddMode.value) {
    titleVar = "Créer une catégorie";
  } else if (isVisuMode.value) {
    titleVar = "Détails de la catégorie ";
  } else {
    titleVar = "Modifier la catégorie";
  }
  return titleVar;
});

const showSectionCurrentState = computed(() => {
  return sectionToShow.value === "currentState";
});
const showSectionPreviousChanges = computed(() => {
  return sectionToShow.value === "previousChanges";
});

const showSection = function (sectionName) {
  return (sectionToShow.value = sectionName);
};

const editData = () => {
  isVisuMode.value = false;
};

if (isAddMode.value) {
  store.createNewSampleDataCategoryAction();
} else {
  store
    .getSampleDataCategoryAction(props.name)
    .catch((error) => console.log(error));
}

const category = computed(() => {
  return store.selectedSampleDataCategory;
});

const cancelCategory = function () {
  router.push({ name: "SampleDataCategories" });
};

const saveCategory = () => {
  error.value = validateCategory(category.value, error.value);
  console.log(errorObjectIsEmpty(error.value));
  if (errorObjectIsEmpty(error.value)) {
    if (isAddMode.value) {
      store
        .addSampleDataCategoryAction(category.value)
        .catch((errorCatch) => console.log(errorCatch));
    } else {
      store
        .updateSampleDataCategoryAction(category.value)
        .catch((errorCatch) => console.log(errorCatch));
    }
    router.push({ name: "SampleDataCategories" });
  }
};

const validateCategory = (category, error) => {
  error = emptyError;
  if (!category.name || category.name.length === 0) {
    error = {
      ...error,
      name: "Le nom de la catégorie est obligatoire",
    };
  }
  return error;
};

const errorObjectIsEmpty = (error) => {
  return Object.values(error).every((value) => value.length === 0);
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
        <button @click="editData()" v-show="isVisuMode" disabled>
          <font-awesome-icon icon="edit" />
        </button>
      </header>
      <main>
        <fieldset class="entity-form-content" v-show="showSectionCurrentState">
          <legend>Etat actuel</legend>
          <div class="field-label">
            <BaseLabel v-model="category.id" label="Id" />
          </div>
          <div class="field">
            <BaseInput
              v-model="category.name"
              label="Nom de la catégorie"
              type="text"
              :error="error.name"
              :disabled="isVisuMode"
            />
          </div>
          <div class="field-label">
            <BaseLabel v-model="category.modifiedAt" label="Modifié le" />
          </div>
          <div class="field-label">
            <BaseLabel v-model="category.modifiedBy" label="Modifié par" />
          </div>
          <div class="field-label">
            <BaseLabel v-model="category.version" label="Version" />
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
      <footer class="entity-form-footer" v-if="isVisuMode">
        <button @click="cancelCategory()">
          <span>Retour</span>
        </button>
      </footer>
      <footer class="entity-form-footer" v-else>
        <button @click="cancelCategory()">
          <span>Cancel</span>
        </button>
        <button @click="saveCategory()">
          <span>Save</span>
        </button>
      </footer>
    </section>
  </div>
</template>

<style>
@import "../../assets/entityEditForm.css";
</style>
