<template>
  <section :class="cssClass">
    <h1 class="entity-title">{{ title }}</h1>

    <div class="form-field-horizontal">
      <label for="sampleDataCategoryId">Catégorie de la donnée</label>
      <select v-model="data.categoryId" id="sampleDataCategoryId">
        <option
          v-for="option in options"
          :key="option.label"
          :value="option.value"
          :selected="isSelected(option)"
        >
          {{ option.label }}
        </option>
      </select>
    </div>

    <BaseInput
      cssClass="form-field-horizontal"
      v-model="data.key"
      label="Nom de la donnée "
    />

    <BaseCheckbox
      :modelValue="data.isBlobTypeValue"
      cssClass="form-field-horizontal"
      label="Donnée volumineuse"
    />

    <BaseTextArea
      v-show="data.isBlobTypeValue"
      cssClass="form-field-horizontal"
      :modelValue="data.blobValue"
      label="Valeur de la donnée volumineuse : "
    />

    <BaseInput
      v-show="!data.isBlobTypeValue"
      cssClass="form-field-horizontal"
      v-model="data.value"
      label="Valeur de la donnée : "
    />
    <footer class="card-footer-buttons">
      <button @click="cancel()">Retour</button>
      <button @click="saveData()">Sauvegarder</button>
    </footer>
  </section>
</template>
<script setup lang="ts">
import BaseInput from "../commons/BaseInput.vue";
import BaseCheckbox from "../commons/BaseCheckbox.vue";
import BaseTextArea from "../commons/BaseTextArea.vue";
import { useTitle } from "@vueuse/core";
import { useSampleDataCategory } from "../../store/SampleDataCategory";
import { isAPIError } from "../../utils/InterfaceUtils";

const storeCategory = useSampleDataCategory();

const props = defineProps({
  title: {
    type: String,
    required: true,
  },
  saveData: {
    type: Function,
    required: true,
  },
  cancel: {
    type: Function,
    required: true,
  },
  data: {
    type: Object as () => SampleData, // Type assertion
    required: true,
  },
  cssClass: {
    type: String,
    required: true,
  },
});

useTitle(props.title);

async function getData() {
  // load data
  try {
    await storeCategory.getAllSampleDataCategoriesAction(false);
  } catch (errorStore) {
    if (isAPIError(errorStore)) {
      throw createError({
        statusCode: errorStore.status,
        statusMessage: JSON.stringify(errorStore),
      });
    } else {
      throw createError({
        statusCode: 500,
        statusMessage: JSON.stringify(errorStore),
      });
    }
  }
}

await getData();

const options = computed(() => {
  const categories = [...storeCategory.sampleDataCategories];
  return categories.map((sampleDataCategory) => ({
    label: sampleDataCategory.name,
    value: sampleDataCategory.id,
  }));
});

function isSelected(option: Option) {
  return (
    props.data.categoryId != undefined && option.value === props.data.categoryId
  );
}
</script>
