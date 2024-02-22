<template>
  <section class="card data-card">
    <header class="card-header">
      <h1>
        {{ data.key }}
      </h1>
      <button class="close" @click="askToDelete">&times;</button>
    </header>

    <main class="data-card-content">
      <BaseLabel
        label="Catégorie de la donnée : "
        :modelValue="data.categoryName"
        cssClass="form-field-horizontal"
      />

      <BaseCheckbox
        :modelValue="data.isBlobTypeValue"
        cssClass="form-field-horizontal"
        label="Donnée volumineuse"
        disabled
      />

      <BaseLabel
        v-if="data.isBlobTypeValue"
        cssClass="form-field-horizontal"
        :modelValue="data.blobValue"
        label="Valeur de la donnée volumineuse : "
      />

      <BaseLabel
        v-else
        cssClass="form-field-horizontal"
        :modelValue="data.value"
        label="Valeur de la donnée : "
      />

      <BaseLabel
        cssClass="form-field-horizontal"
        :modelValue="data.modifiedAt"
        label="Modifié le : "
      />
      <BaseLabel
        cssClass="form-field-horizontal"
        :modelValue="data.modifiedBy"
        label="Modifié par : "
      />
    </main>
    <footer class="card-footer-buttons">
      <button @click="goToElementDetails">
        <span>Sélectioner</span>
      </button>
    </footer>
  </section>
</template>

<script lang="ts">
// declare additional options
export default {
  name: "SampleDataCard",
};
</script>

<script setup lang="ts">
import BaseLabel from "../../components/commons/BaseLabel.vue";
import BaseCheckbox from "../../components/commons/BaseCheckbox.vue";
import { useSampleData } from "../../store/SampleData";
import { isAPIError } from "../../utils/InterfaceUtils";

const router = useRouter();
const store = useSampleData();

const props = defineProps({
  data: {
    type: Object as () => SampleData, // Type assertion
    required: true,
  },
});

const emit = defineEmits(["askToDeleteDataEvent"]);

const askToDelete = () => {
  emit("askToDeleteDataEvent", props.data);
};

const goToElementDetails = () => {
  const id = props.data.id;
  try {
    store.getSampleDataAction(props.data.id, false);
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
  router.push("/sample-datas/" + id);
};
</script>

<style scoped>
.data-card {
  display: flex;
  flex-direction: column;
}

.data-card-content {
  text-align: left;
  display: flex;
  flex-direction: column;
}
</style>
