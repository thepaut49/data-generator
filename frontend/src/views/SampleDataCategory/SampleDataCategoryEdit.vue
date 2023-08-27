<script>
export default {
  name: "SampleDataCategoryEdit",
  inheritAttrs: false,
};
</script>

<script setup>
import { useRouter } from "vue-router";
import { useSampleDataCategoryStore } from "../../stores/SampleDataCategoryStore";
import { ref, computed, reactive } from "vue";
import BaseLabel from "../../components/commons/BaseLabel.vue";
import BaseInput from "../../components/commons/BaseInput.vue";
import { isStringNullOrUndefinedOrEmpty } from "../../shared/StringUtils";

const emptyError = {
  name: "",
};

const props = defineProps({
  categoryName: {
    type: String,
    default: "",
  },
  category: {
    type: Object,
  },
});

const router = useRouter();
const store = useSampleDataCategoryStore();
const error = ref(emptyError);

const isAddMode = ref(props.categoryName === "");

const title = computed(() => {
  let titleVar = "";
  if (isAddMode.value) {
    titleVar = "Créer une catégorie";
  } else {
    titleVar = "Modifier la catégorie";
  }
  return titleVar;
});

if (isAddMode.value) {
  store.createNewSampleDataCategoryAction();
}

const category = reactive({ ...props.category });

const pagePrecedent = function () {
  router.push({ name: "SampleDataCategories" });
};

const saveDataCategory = () => {
  error.value = validateDataCategory(category, error.value);
  console.log(errorObjectIsEmpty(error.value));
  if (errorObjectIsEmpty(error.value)) {
    if (isAddMode.value) {
      store
        .addSampleDataCategoryAction(category)
        .catch((errorCatch) => console.log(errorCatch));
    } else {
      store
        .updateSampleDataCategoryAction(category)
        .catch((errorCatch) => console.log(errorCatch));
    }
    router.push({ name: "SampleDataCategories" });
  }
};

const validateDataCategory = (category, error) => {
  error = emptyError;
  if (isStringNullOrUndefinedOrEmpty(category.name)) {
    error = {
      ...error,
      key: "Le nom de la catégorie est obligatoire",
    };
  }
  return error;
};

const errorObjectIsEmpty = (error) => {
  return Object.values(error).every((value) => value.length === 0);
};
</script>

<template>
  <section class="entity-form">
    <header class="entity-form-header">
      <h2>{{ title }}</h2>
    </header>
    <main>
      <section class="entity-form-content">
        <div class="field-label">
          <BaseLabel v-model="category.id" label="Id" />
        </div>
        <div class="field">
          <BaseInput
            v-model="category.name"
            label="Nom de la catégorie"
            type="text"
            :error="error.name"
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
      </section>
    </main>
    <footer class="entity-form-footer">
      <button @click="pagePrecedent()">
        <span>Annuler</span>
      </button>
      <button @click="saveDataCategory()">
        <span>Sauvegarder</span>
      </button>
    </footer>
  </section>
</template>

<style>
@import "../../assets/entityEditForm.css";
</style>
