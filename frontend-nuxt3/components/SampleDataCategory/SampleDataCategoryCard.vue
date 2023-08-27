<template>
  <section class="card category-card">
    <h1>
      {{ category.name }}
    </h1>
    <main class="category-card-content">
      <div class="field-label">
        <BaseLabel :modelValue="category.modifiedAt" label="Modifié le : " />
      </div>
      <div class="field-label">
        <BaseLabel :modelValue="category.modifiedBy" label="Modifié par : " />
      </div>
    </main>
    <footer>
      <button @click="askToDelete(category)">
        <span>Supprimer</span>
      </button>
      <button @click="goToElementDetails">
        <span>Sélectioner</span>
      </button>
    </footer>
  </section>
</template>

<script>
// declare additional options
export default {
  name: "SampleDataCategoryCard",
};
</script>

<script setup>
import BaseLabel from "../../components/commons/BaseLabel.vue";
import { useSampleDataCategory } from "../../store/SampleDataCategory";

const router = useRouter();
const store = useSampleDataCategory();

const props = defineProps({
  category: {
    type: Object,
    default: () => {
      return {};
    },
  },
});

const emit = defineEmits(["askToDeleteCategoryEvent"]);

const askToDelete = () => {
  emit("askToDeleteCategoryEvent", props.category);
};

const goToElementDetails = () => {
  const categoryName = props.category.name;
  store.getSampleDataCategoryAction(categoryName);
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

.category-card footer {
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: space-around;
}
</style>
