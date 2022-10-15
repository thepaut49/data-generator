<template>
  <section class="listEntitiesSection">
    <header class="listEntitiesHeader">
      <h2>Sample data categories</h2>
      <!--router-link class="button addButton" :to="{ name: 'category-detail' }">
        <font-awesome-icon icon="plus" />
      </!--router-link-->
      <button @click="reloadCategories">
        <font-awesome-icon icon="sync" />
      </button>
    </header>

    <main class="listEntitiesMain" v-if="!isLoading">
      <fieldset style="display: flex">
        <legend>Filtres</legend>
        <BaseInput
          v-model="categoryName"
          label="Nom de la catégorie"
          type="text"
          v-bind="$attrs"
        />
      </fieldset>
      <SampleDataCategoryCard
        v-for="category in categories"
        :key="category.id"
        :category="category"
        @askToDeleteCategoryEvent="askToDelete(category)"
      />
    </main>
    <p v-else>Récupération des entreprises en cours, veuillez patienter</p>
    <ModalWindow
      :message="modalMessage"
      :isOpen="showModal"
      @handleNo="closeModal"
      @handleYes="deleteCategory"
    >
    </ModalWindow>
  </section>
</template>

<script>
import { ref, reactive, computed } from "vue";
import { useSampleDataCategoryStore } from "../../stores/SampleDataCategoryStore";
import { searchLike } from "../../shared/StringUtils";

export default {
  setup() {
    const sampleDataCategoryStore = useSampleDataCategoryStore();
    let isLoading = ref(true);
    let categoryToDelete = reactive({});
    let showModal = ref(false);
    let modalMessage = ref("");
    let categoryName = ref("");

    sampleDataCategoryStore
      .getSampleDataCategoriesAction(categoryName.value)
      .then(() => {
        isLoading.value = false;
      })
      .catch((error) => {
        isLoading.value = false;
        console.log(error);
      });

    const askToDelete = (category) => {
      categoryToDelete = category;
      showModal.value = true;
      modalMessage.value =
        "Voulez vous supprimer l'entreprise " + category.categoryName + " ?";
    };
    const closeModal = () => {
      showModal.value = false;
    };

    const deleteCategory = () => {
      closeModal();
      if (categoryToDelete) {
        sampleDataCategoryStore.deleteSampleDataCategoryAction(
          categoryToDelete
        );
      }
      sampleDataCategoryStore
        .getSampleDataCategoriesAction(categoryName.value)
        .then(() => {
          isLoading.value = false;
        })
        .catch((error) => {
          isLoading.value = false;
          console.log(error);
        });
    };

    const categories = computed(() => {
      if (categoryName.value && categoryName.value === "") {
        return sampleDataCategoryStore.sampleDataCategories;
      } else {
        return sampleDataCategoryStore.sampleDataCategories.filter(function (
          category
        ) {
          return searchLike(categoryName.value, category.name);
        });
      }
    });

    const reloadCategories = () => {
      categoryName.value = "";
      isLoading.value = true;
      sampleDataCategoryStore
        .getSampleDataCategoriesAction(categoryName.value)
        .then(() => {
          isLoading.value = false;
        })
        .catch((error) => {
          isLoading.value = false;
          console.log(error);
        });
    };

    return {
      categoryName,
      categories,
      sampleDataCategoryStore,
      isLoading,
      categoryToDelete,
      showModal,
      modalMessage,
      askToDelete,
      closeModal,
      deleteCategory,
      reloadCategories,
    };
  },
};
</script>

<style scoped></style>
