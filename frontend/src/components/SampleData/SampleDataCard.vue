<template>
  <fieldset class="data-card">
    <legend>
      {{ data.key }}
    </legend>
    <main class="data-card-content">
      <div class="field-label" v-if="data.isBlobValue">
        <BaseLabel :modelValue="data.value" label="Valeur : " />
      </div>
      <div class="field-label" v-else>
        <p>Valeur de type blob</p>
      </div>
      <div class="field-label">
        <BaseLabel :modelValue="data.modifiedAt" label="Modifié le : " />
      </div>
      <div class="field-label">
        <BaseLabel :modelValue="data.modifiedBy" label="Modifié par : " />
      </div>
    </main>
    <footer>
      <button @click="askToDelete(data)">
        <span>Supprimer</span>
      </button>
      <router-link
        class="button link-button"
        :to="{
          name: 'SampleDataLayout',
          params: {
            categoryName: data.categoryName,
            keyProp: data.key,
          },
        }"
      >
        <span>Sélectioner</span>
      </router-link>
    </footer>
  </fieldset>
</template>

<script setup>
import BaseLabel from "../../components/commons/BaseLabel.vue";

const props = defineProps({
  data: {
    type: Object,
    default: () => {
      return {};
    },
  },
});

const emit = defineEmits(["askToDeleteDataEvent"]);

const askToDelete = () => {
  emit("askToDeleteDataEvent", props.data);
};
</script>

<style scoped>
.data-card {
  padding: 1em;
  border: solid 1px #2c3e50;
  cursor: pointer;
  transition: all 0.2s linear;
  display: flex;
  flex-direction: column;
}
.data-card:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2), 0 1px 15px 0 rgba(0, 0, 0, 0.19);
}

.data-card-content {
  text-align: left;
  display: flex;
  flex-direction: column;
}

.data-card footer {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
}
</style>
