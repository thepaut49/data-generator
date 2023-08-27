<script>
export default {
  name: "SampleDataDetail",
  inheritAttrs: false,
};
</script>

<script setup>
import { useRouter } from "vue-router";
import { useSampleDataStore } from "../../stores/SampleDataStore";
import { ref, computed } from "vue";
import BaseLabel from "../../components/commons/BaseLabel.vue";
import BaseInput from "../../components/commons/BaseInput.vue";
import BaseCheckbox from "../../components/commons/BaseCheckbox.vue";
import BaseTextArea from "../../components/commons/BaseTextArea.vue";
import { isStringNullOrUndefinedOrEmpty } from "../../shared/StringUtils";

const emptyError = {
  name: "",
};

const props = defineProps({
  keyProp: {
    type: String,
    default: "",
  },
  categoryName: {
    type: String,
    default: "",
  },
});

const router = useRouter();
const store = useSampleDataStore();
const error = ref(emptyError);

const isAddMode = ref(props.keyProp === "");

const title = computed(() => {
  let titleVar = "";
  if (isAddMode.value) {
    titleVar = "Créer une donnée";
  } else {
    titleVar = "Modifier la donnée";
  }
  return titleVar;
});

if (isAddMode.value) {
  store.createNewSampleDataAction(props.categoryName);
}

const data = computed(() => {
  return store.selectedSampleData;
});

const pagePrecedent = function () {
  router.push({ name: "SampleDatas" });
};

const saveData = () => {
  error.value = validateData(data.value, error.value);
  console.log(errorObjectIsEmpty(error.value));
  if (errorObjectIsEmpty(error.value)) {
    if (isAddMode.value) {
      store
        .addSampleDataAction(data.value)
        .catch((errorCatch) => console.log(errorCatch));
    } else {
      store
        .updateSampleDataAction(data.value)
        .catch((errorCatch) => console.log(errorCatch));
    }
    router.push({ name: "SampleDatas" });
  }
};

const validateData = (data, error) => {
  error = emptyError;
  if (isStringNullOrUndefinedOrEmpty(data.key)) {
    error = {
      ...error,
      key: "La clé associé à la donnée est obligatoire",
    };
  }
  if (!data.isBlobValue && isStringNullOrUndefinedOrEmpty(data.value)) {
    error = {
      ...error,
      key: "La valeur de la donnée est obligatoire",
    };
  }
  if (data.isBlobValue && isStringNullOrUndefinedOrEmpty(data.blobValue)) {
    error = {
      ...error,
      key: "La valeur de type BLOB de la donnée est obligatoire",
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
          <BaseLabel v-model="data.id" label="Id" />
        </div>
        <div class="field">
          <BaseInput
            v-model="data.key"
            label="Clé"
            type="text"
            :error="error.key"
            :disabled="isVisuMode"
          />
        </div>
        <div class="field">
          <BaseCheckbox
            v-model="data.isBlobValue"
            label="Valeur de type BLOB"
            :error="error.isBlobValue"
            :disabled="isVisuMode"
            v-bind="$attrs"
          />
        </div>
        <div class="field" v-show="!data.isBlobValue">
          <BaseInput
            v-model="data.value"
            label="Valeur"
            type="text"
            :error="error.value"
            :disabled="isVisuMode"
          />
        </div>
        <div class="field" v-show="data.isBlobValue">
          <BaseTextArea
            v-model="data.blobValue"
            label="Valeur du BLOB"
            :error="error.blobValue"
            :disabled="isVisuMode"
          />
        </div>
        <div class="field-label">
          <BaseLabel v-model="data.modifiedAt" label="Modifié le" />
        </div>
        <div class="field-label">
          <BaseLabel v-model="data.modifiedBy" label="Modifié par" />
        </div>
        <div class="field-label">
          <BaseLabel v-model="data.version" label="Version" />
        </div>
      </section>
    </main>
    <footer class="entity-form-footer">
      <button @click="pagePrecedent()">
        <span>Annuler</span>
      </button>
      <button @click="saveData()">
        <span>Sauvegarder</span>
      </button>
    </footer>
  </section>
</template>

<style>
@import "../../assets/entityEditForm.css";
</style>
