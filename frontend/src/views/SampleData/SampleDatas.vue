<template>
  <section class="listEntitiesSection">
    <header class="listEntitiesHeader">
      <h2>Sample data datas</h2>
      <router-link class="button addButton" :to="{ name: 'SampleDataDetails' }">
        <font-awesome-icon icon="plus" />
      </router-link>
      <button @click="reloadDatas">
        <font-awesome-icon icon="sync" />
      </button>
    </header>

    <main class="listEntitiesMain" v-if="!isLoading">
      <fieldset style="display: flex">
        <legend>Filtres</legend>
        <div class="field-label">
          <BaseSelect
            v-model="dataCategoryName"
            label="Catégorie"
            v-bind="$attrs"
          />
        </div>
        <div class="field-label">
          <BaseInput
            v-model="dataKey"
            label="Clé"
            type="text"
            v-bind="$attrs"
          />
        </div>
        <div class="field-label">
          <BaseInput
            v-model="dataValue"
            label="Valeur"
            type="text"
            v-bind="$attrs"
          />
        </div>
        <div class="field-label">
          <BaseCheckbox
            v-model="dataIsBlobValue"
            label="Valeur de type BLOB"
            v-bind="$attrs"
          />
        </div>
      </fieldset>
      <SampleDataCard
        v-for="data in datas"
        :key="data.id"
        :data="data"
        @askToDeleteDataEvent="askToDelete(data)"
      />
    </main>
    <p v-else>Récupération des données en cours, veuillez patienter</p>
    <ModalWindow
      :message="modalMessage"
      :isOpen="showModal"
      @handleNo="closeModal"
      @handleYes="deleteData"
    >
    </ModalWindow>
  </section>
</template>

<script setup>
import { ref, reactive, computed } from "vue";
import { useSampleDataStore } from "../../stores/SampleDataStore";
import { searchLike, isStringNotEmpty } from "../../shared/StringUtils";
import SampleDataCard from "../../components/SampleData/SampleDataCard.vue";
import BaseInput from "../../components/commons/BaseInput.vue";
import BaseSelect from "../../components/commons/BaseSelect.vue";
import BaseCheckbox from "../../components/commons/BaseCheckbox.vue";
import ModalWindow from "../../components/commons/ModalWindow.vue";

const sampleDataStore = useSampleDataStore();
let isLoading = ref(true);
let dataToDelete = reactive({});
let showModal = ref(false);
let modalMessage = ref("");
let dataCategoryName = ref("");
let dataKey = ref("");
let dataValue = ref("");
let dataIsBlobValue = ref(false);

sampleDataStore
  .getSampleDatasAction(
    dataCategoryName.value,
    dataKey.value,
    dataValue.value,
    dataIsBlobValue.value
  )
  .then(() => {
    isLoading.value = false;
  })
  .catch((error) => {
    isLoading.value = false;
    console.log(error);
  });

const askToDelete = (data) => {
  dataToDelete = data;
  showModal.value = true;
  if (data.isBlobValue) {
    modalMessage.value = `Voulez vous supprimer la donnée ${data.key} ?`;
  } else {
    modalMessage.value = `Voulez vous supprimer la donnée ${data.key} qui a pour valeur ${data.value} ?`;
  }
};
const closeModal = () => {
  showModal.value = false;
};

const deleteData = () => {
  closeModal();
  if (dataToDelete) {
    sampleDataStore.deleteSampleDataAction(dataToDelete);
  }
  sampleDataStore
    .getSampleDatasAction(
      dataCategoryName.value,
      dataKey.value,
      dataValue.value,
      dataIsBlobValue.value
    )
    .then(() => {
      isLoading.value = false;
    })
    .catch((error) => {
      isLoading.value = false;
      console.log(error);
    });
};

const datas = computed(() => {
  if (
    isStringNotEmpty(dataKey.value) &&
    isStringNotEmpty(dataValue.value) &&
    dataIsBlobValue.value
  ) {
    return sampleDataStore.sampleDatas.filter(function (data) {
      return (
        searchLike(dataKey.value, data.key) &&
        searchLike(dataValue.value, data.value) &&
        data.isBlobValue
      );
    });
  } else if (
    isStringNotEmpty(dataKey.value) &&
    isStringNotEmpty(dataValue.value)
  ) {
    return sampleDataStore.sampleDatas.filter(function (data) {
      return (
        searchLike(dataKey.value, data.key) &&
        searchLike(dataValue.value, data.value)
      );
    });
  } else if (isStringNotEmpty(dataKey.value) && dataIsBlobValue.value) {
    return sampleDataStore.sampleDatas.filter(function (data) {
      return searchLike(dataKey.value, data.key) && data.isBlobValue;
    });
  } else if (dataIsBlobValue.value && isStringNotEmpty(dataValue.value)) {
    return sampleDataStore.sampleDatas.filter(function (data) {
      return searchLike(dataValue.value, data.value) && data.isBlobValue;
    });
  } else if (isStringNotEmpty(dataKey.value)) {
    return sampleDataStore.sampleDatas.filter(function (data) {
      return searchLike(dataKey.value, data.key);
    });
  } else if (isStringNotEmpty(dataValue.value)) {
    return sampleDataStore.sampleDatas.filter(function (data) {
      return searchLike(dataValue.value, data.value);
    });
  } else if (dataIsBlobValue.value) {
    return sampleDataStore.sampleDatas.filter(function (data) {
      return data.isBlobValue;
    });
  } else {
    return sampleDataStore.sampleDatas;
  }
});

const reloadDatas = () => {
  isLoading.value = true;
  sampleDataStore
    .getSampleDatasAction(
      dataCategoryName.value,
      dataKey.value,
      dataValue.value,
      dataIsBlobValue.value
    )
    .then(() => {
      isLoading.value = false;
    })
    .catch((error) => {
      isLoading.value = false;
      console.log(error);
    });
};
</script>

<style scoped></style>
