import { defineStore } from "pinia";
import type { Ref } from "vue";
import { handleError } from "../utils/StoreUtils";
import { updateElement } from "../utils/CollectionUtils";

export const newSampleDataCategory = {
  id: undefined,
  name: "",
  version: 0,
  modifiedBy: "",
  modifiedAt: new Date().toISOString(),
  versions: [],
};

export const useSampleDataCategory = defineStore("SampleDataCategory", () => {
  const { getSession, signIn } = useAuth();
  const config = useRuntimeConfig();
  const BACKEND_API_URL = config.public.BACKEND_API_URL;
  const sampleDataCategories: Ref<SampleDataCategory[]> = ref([]);
  const filteredSampleDataCategories: Ref<SampleDataCategory[]> = ref([]);
  const selectedSampleDataCategory: Ref<SampleDataCategory | null> =
    ref<SampleDataCategory | null>(null);
  const loading = ref(false);
  const lastLoadingDate = ref(new Date(1970, 0, 1, 0, 0, 0));
  const nameCriteria = ref("");

  async function addSampleDataCategoryAction(
    sampleDataCategory: SampleDataCategory,
    retry: boolean
  ) {
    const session = await getSession();
    loading.value = true;
    try {
      const { data, error } = await useFetch<SampleDataCategory>(
        `${BACKEND_API_URL}/api/sample-data-categories`,
        {
          method: "POST",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session?.accessToken}`,
          },
          body: sampleDataCategory, // le type utilisé pour le corps doit correspondre à l'en-tête "Content-Type"
        }
      );
      if (data.value) {
        sampleDataCategories.value.unshift(data.value); // mutable addition
        filteredSampleDataCategories.value.unshift(data.value); // mutable addition
        selectedSampleDataCategory.value = data.value;
      } else if (error.value) {
        handleError(
          addSampleDataCategoryAction,
          signIn,
          error.value,
          retry,
          sampleDataCategory
        );
      }
    } catch (error) {
      throw error;
    } finally {
      loading.value = false;
    }
  }

  async function deleteSampleDataCategoryAction(
    sampleDataCategory: SampleDataCategory,
    retry: boolean
  ) {
    const session = await getSession();
    loading.value = true;
    try {
      const { data, error } = await useFetch<SampleDataCategory>(
        `${BACKEND_API_URL}/api/sample-data-categories/${sampleDataCategory.id}`,
        {
          method: "DELETE",
          headers: {
            Authorization: `Bearer ${session?.accessToken}`,
          },
        }
      );
      if (data.value) {
        sampleDataCategories.value = [
          ...sampleDataCategories.value.filter(
            (p: SampleDataCategory) => p.id !== sampleDataCategory.id
          ),
        ];
        filteredSampleDataCategories.value = [
          ...filteredSampleDataCategories.value.filter(
            (p: SampleDataCategory) => p.id !== sampleDataCategory.id
          ),
        ];
        selectedSampleDataCategory.value = { ...newSampleDataCategory };
      } else if (error.value) {
        handleError(
          deleteSampleDataCategoryAction,
          signIn,
          error.value,
          retry,
          sampleDataCategory
        );
      }
    } catch (error) {
      throw error;
    } finally {
      loading.value = false;
    }
  }
  async function getSampleDataCategoriesAction(
    name: string | null,
    retry: boolean
  ) {
    loading.value = true;
    const session = await getSession();

    let url: string = `${BACKEND_API_URL}/api/sample-data-categories`;
    if (name && name !== "") {
      url = url + "?categoryName=" + name;
    }

    try {
      const { data, error } = await useFetch<SampleDataCategory[]>(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${session?.accessToken}`,
        },
      });

      if (data.value) {
        filteredSampleDataCategories.value = data.value;
        selectedSampleDataCategory.value = { ...newSampleDataCategory };
      } else if (error.value) {
        handleError(
          getSampleDataCategoriesAction,
          signIn,
          error.value,
          retry,
          name
        );
      }
    } catch (error) {
      throw error;
    } finally {
      loading.value = false;
    }
  }

  async function getAllSampleDataCategoriesAction(retry: boolean) {
    loading.value = true;
    const session = await getSession();

    let url: string = `${BACKEND_API_URL}/api/sample-data-categories`;

    try {
      const { data, error } = await useFetch<SampleDataCategory[]>(url, {
        method: "GET",
        headers: {
          Authorization: `Bearer ${session?.accessToken}`,
        },
      });

      if (data.value) {
        sampleDataCategories.value = data.value;
        filteredSampleDataCategories.value = data.value;
        selectedSampleDataCategory.value = { ...newSampleDataCategory };
        lastLoadingDate.value = new Date();
      } else if (error.value) {
        handleError(
          getAllSampleDataCategoriesAction,
          signIn,
          error.value,
          retry
        );
      }
    } catch (error) {
      throw error;
    } finally {
      loading.value = false;
    }
  }

  async function updateSampleDataCategoryAction(
    sampleDataCategory: SampleDataCategory,
    retry: boolean
  ) {
    const session = await getSession();
    loading.value = true;
    try {
      const { data, error } = await useFetch<SampleDataCategory>(
        `${BACKEND_API_URL}/api/sample-data-categories/${sampleDataCategory.id}`,
        {
          method: "PUT",
          headers: {
            "Content-Type": "application/json",
            Authorization: `Bearer ${session?.accessToken}`,
          },
          body: sampleDataCategory,
        }
      );

      if (data.value) {
        updateElement(data.value, sampleDataCategories.value);
        updateElement(data.value, filteredSampleDataCategories.value);
        selectedSampleDataCategory.value = { ...newSampleDataCategory };
      } else if (error.value) {
        handleError(
          updateSampleDataCategoryAction,
          signIn,
          error.value,
          retry,
          sampleDataCategory
        );
      }
    } catch (error) {
      throw error;
    } finally {
      loading.value = false;
    }
  }

  async function getSampleDataCategoryAction(
    id: number | undefined,
    retry: boolean
  ) {
    if (id) {
      const existingSampleDataCategory = sampleDataCategories.value.find(
        (sampleDataCategory) => sampleDataCategory.id === id
      );
      if (existingSampleDataCategory) {
        selectedSampleDataCategory.value = existingSampleDataCategory;
      } else {
        const session = await getSession();
        loading.value = true;
        try {
          const { data, error } = await useFetch<SampleDataCategory>(
            `${BACKEND_API_URL}/api/sample-data-categories/${id}`,
            {
              method: "GET",
              headers: {
                Authorization: `Bearer ${session?.accessToken}`,
              },
            }
          );
          if (data.value) {
            updateElement(data.value, sampleDataCategories.value);
            updateElement(data.value, filteredSampleDataCategories.value);
            selectedSampleDataCategory.value = { ...data.value };
          } else if (error.value) {
            handleError(
              getSampleDataCategoryAction,
              signIn,
              error.value,
              retry,
              id
            );
          }
        } catch (error) {
          throw error;
        } finally {
          loading.value = false;
        }
      }
    }
  }

  function createNewSampleDataCategoryAction() {
    selectedSampleDataCategory.value = { ...newSampleDataCategory };
  }

  return {
    sampleDataCategories,
    filteredSampleDataCategories,
    selectedSampleDataCategory,
    loading,
    lastLoadingDate,
    nameCriteria,
    addSampleDataCategoryAction,
    deleteSampleDataCategoryAction,
    getSampleDataCategoriesAction,
    getAllSampleDataCategoriesAction,
    updateSampleDataCategoryAction,
    getSampleDataCategoryAction,
    createNewSampleDataCategoryAction,
  };
});
