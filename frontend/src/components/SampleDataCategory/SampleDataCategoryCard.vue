<template>
  <fieldset class="category-card">
    <legend>
      {{ category.name }}
    </legend>
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
      <!--router-link
        class="button link-button"
        :to="{ name: 'category-detail', params: { id: category.id } }"
      >
        <span>Sélectioner</span>
      </router-link-->
    </footer>
  </fieldset>
</template>

<script>
export default {
  name: "SampleDataCategoryCard",
  props: {
    category: {
      type: Object,
      default: () => {
        return {};
      },
    },
  },
  setup(props, { emit }) {
    const askToDelete = () => {
      emit("askToDeleteCategoryEvent", props.category);
    };

    return {
      askToDelete,
    };
  },
};
</script>

<style scoped>
.category-card {
  padding: 1em;
  border: solid 1px #2c3e50;
  cursor: pointer;
  transition: all 0.2s linear;
  display: flex;
  flex-direction: column;
}
.category-card:hover {
  transform: scale(1.01);
  box-shadow: 0 3px 12px 0 rgba(0, 0, 0, 0.2), 0 1px 15px 0 rgba(0, 0, 0, 0.19);
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
