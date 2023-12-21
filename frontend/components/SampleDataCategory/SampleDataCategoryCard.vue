<template>
  <section class="card category-card">
    <h1>
      {{ category.name }}
    </h1>
    <main class="category-card-content">
      <BaseLabel
        cssClass="form-field-horizontal"
        :modelValue="category.modifiedAt"
        label="Modifié le : "
      />
      <BaseLabel
        cssClass="form-field-horizontal"
        :modelValue="category.modifiedBy"
        label="Modifié par : "
      />
    </main>
    <footer class="card-footer-buttons">
      <button @click="askToDelete">
        <span>Supprimer</span>
      </button>
      <button @click="goToElementDetails">
        <span>Sélectioner</span>
      </button>
    </footer>
  </section>
</template>

<script lang="ts">
// declare additional options
export default {
  name: "SampleDataCategoryCard",
};
</script>

<script setup lang="ts">
import BaseLabel from "../../components/commons/BaseLabel.vue";
import { useSampleDataCategory } from "../../store/SampleDataCategory";

const router = useRouter();
const store = useSampleDataCategory();

const props = defineProps({
  category: {
    type: Object as () => SampleDataCategory, // Type assertion
    required: true,
  },
});

const emit = defineEmits(["askToDeleteCategoryEvent"]);

const askToDelete = () => {
  emit("askToDeleteCategoryEvent", props.category);
};

const goToElementDetails = () => {
  const categoryName = props.category.name;
  store.getSampleDataCategoryAction(props.category.id);
  router.push("/sample-data-categories/" + categoryName);
};
</script>

<style scoped>
.category-card {
  display: flex;
  flex-direction: column;
}

.category-card-content {
  text-align: left;
  display: flex;
  flex-direction: column;
}
</style>
